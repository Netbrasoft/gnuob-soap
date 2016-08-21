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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.BILLING_AGREEMENT_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CHECKOUT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CHECKOUT_STATUS_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CUSTOM_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DISCOUNT_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.EXTRA_AMOUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GIFT_MESSAGE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GIFT_MESSAGE_ENABLE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GIFT_RECEIPT_ENABLE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GIFT_WRAP_AMOUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GIFT_WRAP_ENABLE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GIFT_WRAP_NAME_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_ORDERS_GNUOB_ORDER_RECORDS_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_ORDERS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.HANDLING_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INSURANCE_OPTION_OFFERED_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INSURANCE_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MAX_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.NOTE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.NOTE_TEXT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_DATE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_DESCRIPTION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PENDING;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_ASC;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.RECORDS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SHIPPING_DISCOUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SHIPPING_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.START_POSITION_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TAX_TOTAL_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TOKEN_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TRANSACTION_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.counting;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.velocity.context.Context;

import br.com.netbrasoft.gnuob.generic.content.contexts.IContextVisitor;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.security.AbstractAccess;

@Cacheable(value = false)
@Entity(name = ORDER_ENTITY_NAME)
@Table(name = ORDER_TABLE_NAME)
@XmlRootElement(name = ORDER_ENTITY_NAME)
public class Order extends AbstractAccess {

  private static final long serialVersionUID = -43318034570501796L;

  private String billingAgreementId;
  private String checkout;
  private String checkoutStatus;
  private Contract contract;
  private String custom;
  private BigDecimal discountTotal;
  private BigDecimal extraAmount;
  private String giftMessage;
  private Boolean giftMessageEnable;
  private Boolean giftReceiptEnable;
  private BigDecimal giftWrapAmount;
  private Boolean giftWrapEnable;
  private String giftWrapName;
  private BigDecimal handlingTotal;
  private Boolean insuranceOptionOffered;
  private BigDecimal insuranceTotal;
  private Invoice invoice;
  private BigDecimal itemTotal;
  private BigDecimal maxTotal;
  private String note;
  private String noteText;
  private String notificationId;
  private Date orderDate;
  private String orderDescription;
  private String orderId;
  private BigDecimal orderTotal;
  private Set<OrderRecord> records;
  private Shipment shipment;
  private BigDecimal shippingDiscount;
  private BigDecimal shippingTotal;
  private BigDecimal taxTotal;
  private String token;
  private String transactionId;

  public Order() {
    records = newHashSet();
  }

  @Override
  @Transient
  public boolean isDetached() {
    return newArrayList(isAbstractTypeDetached(), isContractDetached(), isInvoiceDetached(), isShipmentDetachted(),
        isOrderRecordsDetached()).stream().filter(e -> e.booleanValue()).count() > ZERO;
  }

  @Transient
  private boolean isContractDetached() {
    return contract != null && contract.isDetached();
  }

  @Transient
  private boolean isOrderRecordsDetached() {
    return records != null && records.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Transient
  private boolean isInvoiceDetached() {
    return invoice != null && invoice.isDetached();
  }

  @Transient
  private boolean isShipmentDetachted() {
    return shipment != null && shipment.isDetached();
  }

  @Override
  public void prePersist() {
    getOrderId();
    getCustom();
    getCheckoutStatus();
    getTaxTotal();
    getShippingTotal();
    getOrderTotal();
    getItemTotal();
    getMaxTotal();
    getDiscountTotal();
    reinitAllPositionRecords();
  }

  @Override
  public void preUpdate() {
    reinitAllPositionRecords();
  }

  private void reinitAllPositionRecords() {
    int startPosition = START_POSITION_VALUE;
    for (final OrderRecord record : records) {
      record.setPosition(Integer.valueOf(startPosition++));
    }
  }

  @Override
  public Context accept(final IContextVisitor visitor) {
    return visitor.visit(this);
  }

  @XmlElement
  @Column(name = BILLING_AGREEMENT_ID_COLUMN_NAME)
  public String getBillingAgreementId() {
    return billingAgreementId;
  }

  @XmlElement
  @Column(name = CHECKOUT_COLUMN_NAME)
  public String getCheckout() {
    return checkout;
  }

  @XmlElement
  @Column(name = CHECKOUT_STATUS_COLUMN_NAME)
  public String getCheckoutStatus() {
    if (isBlank(checkoutStatus)) {
      checkoutStatus = PENDING;
    }
    return checkoutStatus;
  }

  @XmlElement(required = true)
  @OneToOne(cascade = {PERSIST, MERGE, REFRESH})
  public Contract getContract() {
    return contract;
  }

  @XmlElement
  @Column(name = CUSTOM_COLUMN_NAME)
  public String getCustom() {
    if (isBlank(custom)) {
      custom = randomUUID().toString();
    }
    return custom;
  }

  @XmlElement
  @Column(name = DISCOUNT_TOTAL_COLUMN_NAME)
  public BigDecimal getDiscountTotal() {
    if (discountTotal == null) {
      discountTotal = BigDecimal.ZERO;
      records.stream().forEach(e -> discountTotal.add(e.getDiscountTotal()));
    }
    return discountTotal;
  }

  @XmlElement(required = true)
  @Column(name = EXTRA_AMOUNT_COLUMN_NAME, nullable = false)
  public BigDecimal getExtraAmount() {
    if (extraAmount == null) {
      extraAmount = getHandlingTotal().add(getTaxTotal()).add(getInsuranceTotal());
    }
    return extraAmount;
  }

  @XmlElement
  @Column(name = GIFT_MESSAGE_COLUMN_NAME)
  public String getGiftMessage() {
    return giftMessage;
  }

  @XmlElement
  @Column(name = GIFT_MESSAGE_ENABLE_COLUMN_NAME)
  public Boolean getGiftMessageEnable() {
    return giftMessageEnable;
  }

  @XmlElement
  @Column(name = GIFT_RECEIPT_ENABLE_COLUMN_NAME)
  public Boolean getGiftReceiptEnable() {
    return giftReceiptEnable;
  }

  @XmlElement
  @Column(name = GIFT_WRAP_AMOUNT_COLUMN_NAME)
  public BigDecimal getGiftWrapAmount() {
    return giftWrapAmount;
  }

  @XmlElement
  @Column(name = GIFT_WRAP_ENABLE_COLUMN_NAME)
  public Boolean getGiftWrapEnable() {
    return giftWrapEnable;
  }

  @XmlElement
  @Column(name = GIFT_WRAP_NAME_COLUMN_NAME)
  public String getGiftWrapName() {
    return giftWrapName;
  }

  @XmlElement(required = true)
  @Column(name = HANDLING_TOTAL_COLUMN_NAME, nullable = false)
  public BigDecimal getHandlingTotal() {
    if (handlingTotal == null) {
      handlingTotal = BigDecimal.ZERO;
    }
    return handlingTotal;
  }

  @XmlElement
  @Column(name = INSURANCE_OPTION_OFFERED_COLUMN_NAME)
  public Boolean getInsuranceOptionOffered() {
    return insuranceOptionOffered;
  }

  @XmlElement(required = true)
  @Column(name = INSURANCE_TOTAL_COLUMN_NAME, nullable = false)
  public BigDecimal getInsuranceTotal() {
    if (insuranceTotal == null) {
      insuranceTotal = BigDecimal.ZERO;
    }
    return insuranceTotal;
  }

  @XmlElement(required = true)
  @OneToOne(cascade = {PERSIST, MERGE, REMOVE, REFRESH}, orphanRemoval = true, optional = false)
  public Invoice getInvoice() {
    return invoice;
  }

  @XmlElement
  @Column(name = ITEM_TOTAL_COLUMN_NAME)
  public BigDecimal getItemTotal() {
    if (itemTotal == null) {
      itemTotal = BigDecimal.ZERO;
      records.stream().forEach(e -> itemTotal.add(e.getItemTotal()));
    }
    return itemTotal;
  }

  @XmlElement
  @Column(name = MAX_TOTAL_COLUMN_NAME)
  public BigDecimal getMaxTotal() {
    if (maxTotal == null) {
      maxTotal = getOrderTotal();
    }
    return maxTotal;
  }

  @XmlElement
  @Column(name = NOTE_COLUMN_NAME)
  public String getNote() {
    return note;
  }

  @XmlElement
  @Column(name = NOTE_TEXT_COLUMN_NAME)
  public String getNoteText() {
    return noteText;
  }

  @XmlElement
  @Transient
  public String getNotificationId() {
    return notificationId;
  }

  @XmlElement
  @Column(name = ORDER_DATE_COLUMN_NAME)
  public Date getOrderDate() {
    return orderDate;
  }

  @XmlElement
  @Column(name = ORDER_DESCRIPTION_COLUMN_NAME)
  public String getOrderDescription() {
    return orderDescription;
  }

  @XmlElement(required = true)
  @Column(name = ORDER_ID_COLUMN_NAME, nullable = false)
  public String getOrderId() {
    if (isBlank(orderId)) {
      orderId = randomUUID().toString();
    }
    return orderId;
  }

  @XmlElement
  @Column(name = ORDER_TOTAL_COLUMN_NAME)
  public BigDecimal getOrderTotal() {
    if (orderTotal == null) {
      orderTotal = getItemTotal().add(getShippingTotal()).subtract(getShippingDiscount()).add(getExtraAmount());
    }
    return orderTotal;
  }

  @OrderBy(POSITION_ASC)
  @OneToMany(cascade = {PERSIST, MERGE, REMOVE, REFRESH}, orphanRemoval = true, fetch = EAGER)
  @JoinTable(name = GNUOB_ORDERS_GNUOB_ORDER_RECORDS_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_ORDERS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = RECORDS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<OrderRecord> getRecords() {
    return records;
  }

  @XmlElement
  @OneToOne(cascade = {PERSIST, MERGE, REMOVE, REFRESH}, orphanRemoval = true)
  public Shipment getShipment() {
    return shipment;
  }

  @XmlElement(required = true)
  @Column(name = SHIPPING_DISCOUNT_COLUMN_NAME, nullable = false)
  public BigDecimal getShippingDiscount() {
    if (shippingDiscount == null) {
      shippingDiscount = BigDecimal.ZERO;
    }
    return shippingDiscount;
  }

  @XmlElement
  @Column(name = SHIPPING_TOTAL_COLUMN_NAME)
  public BigDecimal getShippingTotal() {
    if (shippingTotal == null) {
      shippingTotal = BigDecimal.ZERO;
      records.stream().forEach(e -> shippingTotal.add(e.getShippingTotal()));
    }
    return shippingTotal;
  }

  @XmlElement
  @Column(name = TAX_TOTAL_COLUMN_NAME)
  public BigDecimal getTaxTotal() {
    if (taxTotal == null) {
      taxTotal = BigDecimal.ZERO;
      records.forEach(e -> taxTotal.add(e.getTaxTotal()));
    }
    return taxTotal;
  }

  @XmlElement
  @Column(name = TOKEN_COLUMN_NAME)
  public String getToken() {
    return token;
  }

  @XmlElement
  @Column(name = TRANSACTION_ID_COLUMN_NAME)
  public String getTransactionId() {
    return transactionId;
  }

  public void setBillingAgreementId(final String billingAgreementId) {
    this.billingAgreementId = billingAgreementId;
  }

  public void setCheckout(final String checkout) {
    this.checkout = checkout;
  }

  public void setCheckoutStatus(final String checkoutStatus) {
    this.checkoutStatus = checkoutStatus;
  }

  public void setContract(final Contract contract) {
    this.contract = contract;
  }

  public void setCustom(final String custom) {
    this.custom = custom;
  }

  public void setDiscountTotal(final BigDecimal discountTotal) {
    this.discountTotal = discountTotal;
  }

  public void setExtraAmount(final BigDecimal extraAmount) {
    this.extraAmount = extraAmount;
  }

  public void setGiftMessage(final String giftMessage) {
    this.giftMessage = giftMessage;
  }

  public void setGiftMessageEnable(final Boolean giftMessageEnable) {
    this.giftMessageEnable = giftMessageEnable;
  }

  public void setGiftReceiptEnable(final Boolean giftReceiptEnable) {
    this.giftReceiptEnable = giftReceiptEnable;
  }

  public void setGiftWrapAmount(final BigDecimal giftWrapAmount) {
    this.giftWrapAmount = giftWrapAmount;
  }

  public void setGiftWrapEnable(final Boolean giftWrapEnable) {
    this.giftWrapEnable = giftWrapEnable;
  }

  public void setGiftWrapName(final String giftWrapName) {
    this.giftWrapName = giftWrapName;
  }

  public void setHandlingTotal(final BigDecimal handlingTotal) {
    this.handlingTotal = handlingTotal;
  }

  public void setInsuranceOptionOffered(final Boolean insuranceOptionOffered) {
    this.insuranceOptionOffered = insuranceOptionOffered;
  }

  public void setInsuranceTotal(final BigDecimal insuranceTotal) {
    this.insuranceTotal = insuranceTotal;
  }

  public void setInvoice(final Invoice invoice) {
    this.invoice = invoice;
  }

  public void setItemTotal(final BigDecimal itemTotal) {
    this.itemTotal = itemTotal;
  }

  public void setMaxTotal(final BigDecimal maxTotal) {
    this.maxTotal = maxTotal;
  }

  public void setNote(final String note) {
    this.note = note;
  }

  public void setNoteText(final String noteText) {
    this.noteText = noteText;
  }

  public void setNotificationId(final String notificationId) {
    this.notificationId = notificationId;
  }

  public void setOrderDate(final Date orderDate) {
    this.orderDate = orderDate;
  }

  public void setOrderDate(final Timestamp orderDate) {
    this.orderDate = orderDate;
  }

  public void setOrderDescription(final String orderDescription) {
    this.orderDescription = orderDescription;
  }

  public void setOrderId(final String orderId) {
    this.orderId = orderId;
  }

  public void setOrderTotal(final BigDecimal orderTotal) {
    this.orderTotal = orderTotal;
  }

  public void setRecords(final Set<OrderRecord> records) {
    this.records = records;
  }

  public void setShipment(final Shipment shipment) {
    this.shipment = shipment;
  }

  public void setShippingDiscount(final BigDecimal shippingDiscount) {
    this.shippingDiscount = shippingDiscount;
  }

  public void setShippingTotal(final BigDecimal shippingTotal) {
    this.shippingTotal = shippingTotal;
  }

  public void setTaxTotal(final BigDecimal taxTotal) {
    this.taxTotal = taxTotal;
  }

  public void setToken(final String token) {
    this.token = token;
  }

  public void setTransactionId(final String transactionId) {
    this.transactionId = transactionId;
  }
}
