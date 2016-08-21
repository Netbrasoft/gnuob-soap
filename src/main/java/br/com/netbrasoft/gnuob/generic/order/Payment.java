/*
 * . * Copyright 2016 Netbrasoft
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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.EXCHANGE_RATE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FEE_AMOUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GROSS_AMOUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.HOLD_DECISION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INSTALLMENT_COUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAYMENT_DATE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAYMENT_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAYMENT_REQUEST_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAYMENT_STATUS_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAYMENT_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAYMENT_TYPE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PENDING_REASON_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PROTECTION_ELIGIBILITY_TYPE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REASON_CODE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SETTLE_AMOUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.STORE_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TAX_AMOUNT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TERMINAL_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TRANSACTION_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TRANSACTION_TYPE_COLUMN_NAME;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import br.com.netbrasoft.gnuob.generic.AbstractType;

@Cacheable(value = false)
@Entity(name = PAYMENT_ENTITY_NAME)
@Table(name = PAYMENT_TABLE_NAME)
@Inheritance(strategy = SINGLE_TABLE)
@XmlRootElement(name = PAYMENT_ENTITY_NAME)
public class Payment extends AbstractType {

  private static final long serialVersionUID = -4794906337826218748L;

  private String exchangeRate;
  private BigDecimal feeAmount;
  private BigDecimal grossAmount;
  private String holdDecision;
  private BigInteger installmentCount;
  private Date paymentDate;
  private String paymentRequestId;
  private String paymentStatus;
  private String paymentType;
  private String pendingReason;
  private Integer position;
  private String protectionEligibilityType;
  private String reasonCode;
  private BigDecimal settleAmount;
  private String storeId;
  private BigDecimal taxAmount;
  private String terminalId;
  private String transactionId;
  private String transactionType;

  @Override
  @Transient
  public boolean isDetached() {
    return isAbstractTypeDetached();
  }

  @XmlElement
  @Column(name = EXCHANGE_RATE_COLUMN_NAME)
  public String getExchangeRate() {
    return exchangeRate;
  }

  @XmlElement
  @Column(name = FEE_AMOUNT_COLUMN_NAME)
  public BigDecimal getFeeAmount() {
    return feeAmount;
  }

  @XmlElement(required = true)
  @Column(name = GROSS_AMOUNT_COLUMN_NAME, nullable = false)
  public BigDecimal getGrossAmount() {
    return grossAmount;
  }

  @XmlElement
  @Column(name = HOLD_DECISION_COLUMN_NAME)
  public String getHoldDecision() {
    return holdDecision;
  }

  @XmlElement
  @Column(name = INSTALLMENT_COUNT_COLUMN_NAME)
  public BigInteger getInstallmentCount() {
    return installmentCount;
  }

  @XmlElement
  @Column(name = PAYMENT_DATE_COLUMN_NAME)
  public Date getPaymentDate() {
    return paymentDate;
  }

  @XmlElement
  @Column(name = PAYMENT_REQUEST_ID_COLUMN_NAME)
  public String getPaymentRequestId() {
    return paymentRequestId;
  }

  @XmlElement(required = true)
  @Column(name = PAYMENT_STATUS_COLUMN_NAME, nullable = false)
  public String getPaymentStatus() {
    return paymentStatus;
  }

  @XmlElement(required = true)
  @Column(name = PAYMENT_TYPE_COLUMN_NAME, nullable = false)
  public String getPaymentType() {
    return paymentType;
  }

  @XmlElement
  @Column(name = PENDING_REASON_COLUMN_NAME)
  public String getPendingReason() {
    return pendingReason;
  }

  @XmlTransient
  @Column(name = POSITION_COLUMN_NAME)
  public Integer getPosition() {
    return position;
  }

  @XmlElement
  @Column(name = PROTECTION_ELIGIBILITY_TYPE_COLUMN_NAME)
  public String getProtectionEligibilityType() {
    return protectionEligibilityType;
  }

  @XmlElement
  @Column(name = REASON_CODE_COLUMN_NAME)
  public String getReasonCode() {
    return reasonCode;
  }

  @XmlElement
  @Column(name = SETTLE_AMOUNT_COLUMN_NAME)
  public BigDecimal getSettleAmount() {
    return settleAmount;
  }

  @XmlElement
  @Column(name = STORE_ID_COLUMN_NAME)
  public String getStoreId() {
    return storeId;
  }

  @XmlElement
  @Column(name = TAX_AMOUNT_COLUMN_NAME)
  public BigDecimal getTaxAmount() {
    return taxAmount;
  }

  @XmlElement
  @Column(name = TERMINAL_ID_COLUMN_NAME)
  public String getTerminalId() {
    return terminalId;
  }

  @XmlElement(required = true)
  @Column(name = TRANSACTION_ID_COLUMN_NAME, nullable = false)
  public String getTransactionId() {
    return transactionId;
  }

  @XmlElement(required = true)
  @Column(name = TRANSACTION_TYPE_COLUMN_NAME, nullable = false)
  public String getTransactionType() {
    return transactionType;
  }

  public void setExchangeRate(final String exchangeRate) {
    this.exchangeRate = exchangeRate;
  }

  public void setFeeAmount(final BigDecimal feeAmount) {
    this.feeAmount = feeAmount;
  }

  public void setGrossAmount(final BigDecimal grossAmount) {
    this.grossAmount = grossAmount;
  }

  public void setHoldDecision(final String holdDecision) {
    this.holdDecision = holdDecision;
  }

  public void setInstallmentCount(final BigInteger installmentCount) {
    this.installmentCount = installmentCount;
  }

  public void setPaymentDate(final Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public void setPaymentRequestId(final String paymentRequestId) {
    this.paymentRequestId = paymentRequestId;
  }

  public void setPaymentStatus(final String paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public void setPaymentType(final String paymentType) {
    this.paymentType = paymentType;
  }

  public void setPendingReason(final String pendingReason) {
    this.pendingReason = pendingReason;
  }

  public void setPosition(final Integer position) {
    this.position = position;
  }

  public void setProtectionEligibilityType(final String protectionEligibilityType) {
    this.protectionEligibilityType = protectionEligibilityType;
  }

  public void setReasonCode(final String reasonCode) {
    this.reasonCode = reasonCode;
  }

  public void setSettleAmount(final BigDecimal settleAmount) {
    this.settleAmount = settleAmount;
  }

  public void setStoreId(final String storeId) {
    this.storeId = storeId;
  }

  public void setTaxAmount(final BigDecimal taxAmount) {
    this.taxAmount = taxAmount;
  }

  public void setTerminalId(final String terminalId) {
    this.terminalId = terminalId;
  }

  public void setTransactionId(final String transactionId) {
    this.transactionId = transactionId;
  }

  public void setTransactionType(final String transactionType) {
    this.transactionType = transactionType;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
