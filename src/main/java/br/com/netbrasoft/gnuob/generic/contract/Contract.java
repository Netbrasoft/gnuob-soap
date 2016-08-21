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

package br.com.netbrasoft.gnuob.generic.contract;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.UUID.randomUUID;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.velocity.context.Context;

import br.com.netbrasoft.gnuob.generic.content.contexts.IContextVisitor;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.offer.Offer;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.security.AbstractAccess;

@Cacheable(value = true)
@Entity(name = CONTRACT_ENTITY_NAME)
@Table(name = CONTRACT_TABLE_NAME)
@XmlRootElement(name = CONTRACT_ENTITY_NAME)
public class Contract extends AbstractAccess {

  private static final long serialVersionUID = -2215842699700777956L;

  private String contractId;
  private Customer customer;
  private Set<Offer> offers;
  private Set<Order> orders;

  public Contract() {
    offers = newHashSet();
    orders = newHashSet();
  }

  @Override
  @Transient
  public boolean isDetached() {
    return newArrayList(isAbstractTypeDetached(), isCustomerAttached()).stream().filter(e -> e.booleanValue())
        .count() > ZERO;
  }

  @Transient
  private boolean isCustomerAttached() {
    return customer != null && customer.isDetached();
  }

  @Override
  public void prePersist() {
    getContractId();
  }

  @Override
  public void preUpdate() {
    prePersist();
  }

  @Override
  public Context accept(final IContextVisitor visitor) {
    return visitor.visit(this);
  }

  @XmlElement(required = true)
  @Column(name = CONTRACT_ID_COLUMN_NAME, nullable = false)
  public String getContractId() {
    if (isBlank(contractId)) {
      contractId = randomUUID().toString();
    }
    return contractId;
  }

  @XmlElement(required = true)
  @OneToOne(cascade = {PERSIST, MERGE, REFRESH}, optional = false, fetch = EAGER)
  public Customer getCustomer() {
    return customer;
  }

  @XmlTransient
  @OneToMany(cascade = {REFRESH}, fetch = EAGER, mappedBy = CONTRACT_PARAM_NAME)
  public Set<Offer> getOffers() {
    return offers;
  }

  @XmlTransient
  @OneToMany(cascade = {REFRESH}, fetch = EAGER, mappedBy = CONTRACT_PARAM_NAME)
  public Set<Order> getOrders() {
    return orders;
  }

  public void setContractId(final String contractId) {
    this.contractId = contractId;
  }

  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }

  public void setOffers(final Set<Offer> offers) {
    this.offers = offers;
  }

  public void setOrders(final Set<Order> orders) {
    this.orders = orders;
  }
}
