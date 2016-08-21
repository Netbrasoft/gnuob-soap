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

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.order.Payment;

public class PaymentTest {

  private Payment spyPayment;

  @Before
  public void setUp() throws Exception {
    spyPayment = spy(Payment.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetExchangeRate() {
    assertNull("ExchangeRate", spyPayment.getExchangeRate());
    verify(spyPayment, times(1)).getExchangeRate();
  }

  @Test
  public void testGetFeeAmount() {
    assertNull("FeeAmount", spyPayment.getFeeAmount());
    verify(spyPayment, times(1)).getFeeAmount();
  }

  @Test
  public void testGetGrossAmount() {
    assertNull("GrossAmount", spyPayment.getGrossAmount());
    verify(spyPayment, times(1)).getGrossAmount();
  }

  @Test
  public void testGetHoldDecision() {
    assertNull("HoldDecision", spyPayment.getHoldDecision());
    verify(spyPayment, times(1)).getHoldDecision();
  }

  @Test
  public void testGetInstallmentCount() {
    assertNull("InstallmentCount", spyPayment.getInstallmentCount());
    verify(spyPayment, times(1)).getInstallmentCount();
  }

  @Test
  public void testGetPaymentDate() {
    assertNull("PaymentDate", spyPayment.getPaymentDate());
    verify(spyPayment, times(1)).getPaymentDate();
  }

  @Test
  public void testGetPaymentRequestId() {
    assertNull("PaymentRequestId", spyPayment.getPaymentRequestId());
    verify(spyPayment, times(1)).getPaymentRequestId();
  }

  @Test
  public void testGetPaymentStatus() {
    assertNull("PaymentStatus", spyPayment.getPaymentStatus());
    verify(spyPayment, times(1)).getPaymentStatus();
  }

  @Test
  public void testGetPaymentType() {
    assertNull("PaymentType", spyPayment.getPaymentType());
    verify(spyPayment, times(1)).getPaymentType();
  }

  @Test
  public void testGetPendingReason() {
    assertNull("PendingReason", spyPayment.getPendingReason());
    verify(spyPayment, times(1)).getPendingReason();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spyPayment.getPosition());
    verify(spyPayment, times(1)).getPosition();
  }

  @Test
  public void testGetProtectionEligibilityType() {
    assertNull("ProtectionEligibilityType", spyPayment.getProtectionEligibilityType());
    verify(spyPayment, times(1)).getProtectionEligibilityType();
  }

  @Test
  public void testGetReasonCode() {
    assertNull("ReasonCode", spyPayment.getReasonCode());
    verify(spyPayment, times(1)).getReasonCode();
  }

  @Test
  public void testGetSettleAmount() {
    assertNull("SettleAmount", spyPayment.getSettleAmount());
    verify(spyPayment, times(1)).getSettleAmount();
  }

  @Test
  public void testGetStoreId() {
    assertNull("StoreId", spyPayment.getStoreId());
    verify(spyPayment, times(1)).getStoreId();
  }

  @Test
  public void testGetTaxAmount() {
    assertNull("TaxAmount", spyPayment.getTaxAmount());
    verify(spyPayment, times(1)).getTaxAmount();
  }

  @Test
  public void testGetTerminalId() {
    assertNull("TerminalId", spyPayment.getTerminalId());
    verify(spyPayment, times(1)).getTerminalId();
  }

  @Test
  public void testGetTransactionId() {
    assertNull("TransactionId", spyPayment.getTransactionId());
    verify(spyPayment, times(1)).getTransactionId();
  }

  @Test
  public void testGetTransactionType() {
    assertNull("TransactionType", spyPayment.getTransactionType());
    verify(spyPayment, times(1)).getTransactionType();
  }

  @Test
  public void testIsDetached() {
    spyPayment.setId(1L);
    assertTrue("Detached", spyPayment.isDetached());
    verify(spyPayment, times(1)).isDetached();
  }

  @Test
  public void testIsNotDetached() {
    spyPayment.setId(0L);
    assertFalse("Detached", spyPayment.isDetached());
    verify(spyPayment, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyPayment.prePersist();
    verify(spyPayment, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyPayment.preUpdate();
    verify(spyPayment, times(1)).preUpdate();
  }

  @Test
  public void testSetExchangeRate() {
    spyPayment.setExchangeRate("Folly words widow one downs few age every seven.");
    assertEquals("ExchangeRate", "Folly words widow one downs few age every seven.", spyPayment.getExchangeRate());
    verify(spyPayment, times(1)).setExchangeRate(anyString());
  }

  @Test
  public void testSetFeeAmount() {
    spyPayment.setFeeAmount(BigDecimal.ZERO);
    assertEquals("FeeAmount", BigDecimal.ZERO, spyPayment.getFeeAmount());
    verify(spyPayment, times(1)).setFeeAmount(any());
  }

  @Test
  public void testSetGrossAmount() {
    spyPayment.setGrossAmount(BigDecimal.ZERO);
    assertEquals("GrossAmount", BigDecimal.ZERO, spyPayment.getGrossAmount());
    verify(spyPayment, times(1)).setGrossAmount(any());
  }

  @Test
  public void testSetHoldDecision() {
    spyPayment.setHoldDecision("Folly words widow one downs few age every seven.");
    assertEquals("HoldDecision", "Folly words widow one downs few age every seven.", spyPayment.getHoldDecision());
    verify(spyPayment, times(1)).setHoldDecision(anyString());
  }

  @Test
  public void testSetInstallmentCount() {
    spyPayment.setInstallmentCount(BigInteger.ZERO);
    assertEquals("InstallmentCount", BigInteger.ZERO, spyPayment.getInstallmentCount());
    verify(spyPayment, times(1)).setInstallmentCount(any());
  }

  @Test
  public void testSetPaymentDate() {
    spyPayment.setPaymentDate(new Date());
    assertNotNull("PaymentDate", spyPayment.getPaymentDate());
    verify(spyPayment, times(1)).setPaymentDate(any());
  }

  @Test
  public void testSetPaymentRequestId() {
    spyPayment.setPaymentRequestId("Folly words widow one downs few age every seven.");
    assertEquals("PaymentRequestId", "Folly words widow one downs few age every seven.",
        spyPayment.getPaymentRequestId());
    verify(spyPayment, times(1)).setPaymentRequestId(anyString());
  }

  @Test
  public void testSetPaymentStatus() {
    spyPayment.setPaymentStatus("Folly words widow one downs few age every seven.");
    assertEquals("PaymentStatus", "Folly words widow one downs few age every seven.", spyPayment.getPaymentStatus());
    verify(spyPayment, times(1)).setPaymentStatus(anyString());
  }

  @Test
  public void testSetPaymentType() {
    spyPayment.setPaymentType("Folly words widow one downs few age every seven.");
    assertEquals("PaymentType", "Folly words widow one downs few age every seven.", spyPayment.getPaymentType());
    verify(spyPayment, times(1)).setPaymentType(anyString());
  }

  @Test
  public void testSetPendingReason() {
    spyPayment.setPendingReason("Folly words widow one downs few age every seven.");
    assertEquals("PendingReason", "Folly words widow one downs few age every seven.", spyPayment.getPendingReason());
    verify(spyPayment, times(1)).setPendingReason(anyString());
  }

  @Test
  public void testSetPosition() {
    spyPayment.setPosition(Integer.MAX_VALUE);
    assertEquals("Position", Integer.MAX_VALUE, spyPayment.getPosition().intValue());
    verify(spyPayment, times(1)).setPosition(anyInt());
  }

  @Test
  public void testSetProtectionEligibilityType() {
    spyPayment.setProtectionEligibilityType("Folly words widow one downs few age every seven.");
    assertEquals("ProtectionEligibilityType", "Folly words widow one downs few age every seven.",
        spyPayment.getProtectionEligibilityType());
    verify(spyPayment, times(1)).setProtectionEligibilityType(anyString());
  }

  @Test
  public void testSetReasonCode() {
    spyPayment.setReasonCode("Folly words widow one downs few age every seven.");
    assertEquals("ReasonCode", "Folly words widow one downs few age every seven.", spyPayment.getReasonCode());
    verify(spyPayment, times(1)).setReasonCode(anyString());
  }

  @Test
  public void testSetSettleAmount() {
    spyPayment.setSettleAmount(BigDecimal.ZERO);
    assertEquals("SettleAmount", BigDecimal.ZERO, spyPayment.getSettleAmount());
    verify(spyPayment, times(1)).setSettleAmount(any());
  }

  @Test
  public void testSetStoreId() {
    spyPayment.setStoreId("Folly words widow one downs few age every seven.");
    assertEquals("StoreId", "Folly words widow one downs few age every seven.", spyPayment.getStoreId());
    verify(spyPayment, times(1)).setStoreId(anyString());
  }

  @Test
  public void testSetTaxAmount() {
    spyPayment.setTaxAmount(BigDecimal.ZERO);
    assertEquals("TaxAmount", BigDecimal.ZERO, spyPayment.getTaxAmount());
    verify(spyPayment, times(1)).setTaxAmount(any());
  }

  @Test
  public void testSetTerminalId() {
    spyPayment.setTerminalId("Folly words widow one downs few age every seven.");
    assertEquals("TerminalId", "Folly words widow one downs few age every seven.", spyPayment.getTerminalId());
    verify(spyPayment, times(1)).setTerminalId(anyString());
  }

  @Test
  public void testSetTransactionId() {
    spyPayment.setTransactionId("Folly words widow one downs few age every seven.");
    assertEquals("TransactionId", "Folly words widow one downs few age every seven.", spyPayment.getTransactionId());
    verify(spyPayment, times(1)).setTransactionId(anyString());
  }

  @Test
  public void testSetTransactionType() {
    spyPayment.setTransactionType("Folly words widow one downs few age every seven.");
    assertEquals("TransactionType", "Folly words widow one downs few age every seven.",
        spyPayment.getTransactionType());
    verify(spyPayment, times(1)).setTransactionType(anyString());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyPayment, SHORT_PREFIX_STYLE), spyPayment.toString());
  }
}
