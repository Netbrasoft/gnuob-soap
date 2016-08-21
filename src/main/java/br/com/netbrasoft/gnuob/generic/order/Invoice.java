/*
 * Copyright 2016 Netbrasoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package br.com.netbrasoft.gnuob.generic.order;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_INVOICES_GNUOB_PAYMENTS_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_INVOICES_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INVOICE_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INVOICE_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INVOICE_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAYMENTS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_ASC;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.START_POSITION_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.valueOf;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.counting;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import br.com.netbrasoft.gnuob.generic.AbstractType;
import br.com.netbrasoft.gnuob.generic.customer.Address;

@Cacheable(value = false)
@Entity(name = INVOICE_ENTITY_NAME)
@Table(name = INVOICE_TABLE_NAME)
@Inheritance(strategy = SINGLE_TABLE)
@XmlRootElement(name = INVOICE_ENTITY_NAME)
public class Invoice extends AbstractType {

  private static final long serialVersionUID = 5609152324488531802L;

  private Address address;
  private String invoiceId;
  private Set<Payment> payments;

  public Invoice() {
    payments = newHashSet();
  }

  @Override
  @Transient
  public boolean isDetached() {
    return newArrayList(isAbstractTypeDetached(), isAddressDetached(), isPaymentsDetached()).stream()
        .filter(e -> e.booleanValue()).count() > ZERO;
  }

  @Transient
  private boolean isAddressDetached() {
    return address != null && address.isDetached();
  }

  @Transient
  private boolean isPaymentsDetached() {
    return payments != null && payments.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Override
  public void prePersist() {
    getInvoiceId();
    reinitAllPositionPayments();
  }

  @Override
  public void preUpdate() {
    reinitAllPositionPayments();
  }

  private void reinitAllPositionPayments() {
    int startPosition = START_POSITION_VALUE;
    for (final Payment payment : payments) {
      payment.setPosition(valueOf(startPosition++));
    }
  }

  @XmlElement(required = true)
  @OneToOne(cascade = {PERSIST, MERGE, REMOVE, REFRESH}, orphanRemoval = true, optional = false)
  public Address getAddress() {
    return address;
  }

  @XmlElement
  @Column(name = INVOICE_ID_COLUMN_NAME, nullable = false)
  public String getInvoiceId() {
    if (invoiceId == null || isBlank(invoiceId)) {
      invoiceId = randomUUID().toString();
    }
    return invoiceId;
  }

  @XmlElement
  @OrderBy(POSITION_ASC)
  @OneToMany(cascade = {PERSIST, MERGE, REMOVE, REFRESH}, orphanRemoval = true, fetch = EAGER)
  @JoinTable(name = GNUOB_INVOICES_GNUOB_PAYMENTS_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_INVOICES_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = PAYMENTS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<Payment> getPayments() {
    return payments;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  public void setInvoiceId(final String invoiceId) {
    this.invoiceId = invoiceId;
  }

  public void setPayments(final Set<Payment> payments) {
    this.payments = payments;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
