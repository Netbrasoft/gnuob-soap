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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GROUP;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;
import br.com.netbrasoft.gnuob.generic.contract.Contract;

public class OrderTest {

  private Contract mockContract;
  private Invoice mockInvoice;
  private OrderRecord mockOrderRecord;
  private Shipment mockShipment;
  private Order spyOrder;

  @Before
  public void setUp() throws Exception {
    mockContract = mock(Contract.class);
    mockOrderRecord = mock(OrderRecord.class);
    mockShipment = mock(Shipment.class);
    mockInvoice = mock(Invoice.class);
    spyOrder = spy(Order.class);
    spyOrder.setContract(mockContract);
    spyOrder.setInvoice(mockInvoice);
    spyOrder.setShipment(mockShipment);
    spyOrder.setRecords(newSet(mockOrderRecord));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyOrder.accept(new ContextVisitorImpl()));
    verify(spyOrder, times(1)).accept(any());
  }

  @Test
  public void testCheckoutStatus() {
    assertNotNull("CheckoutStatus", spyOrder.getCheckoutStatus());
    verify(spyOrder, times(1)).getCheckoutStatus();
  }

  @Test
  public void testGetBillingAgreementId() {
    assertNull("BillingAgreementId", spyOrder.getBillingAgreementId());
    verify(spyOrder, times(1)).getBillingAgreementId();
  }

  @Test
  public void testGetCheckout() {
    assertNull("Checkout", spyOrder.getCheckout());
    verify(spyOrder, times(1)).getCheckout();
  }

  @Test
  public void testGetContract() {
    assertNotNull("Contract", spyOrder.getContract());
    verify(spyOrder, times(1)).getContract();
  }

  @Test
  public void testGetCustom() {
    assertNotNull("Custom", spyOrder.getCustom());
    verify(spyOrder, times(1)).getCustom();
  }

  @Test
  public void testGetDiscountTotal() {
    when(mockOrderRecord.getDiscountTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("DiscountTotal", spyOrder.getDiscountTotal());
    verify(spyOrder, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetExtraAmount() {
    when(mockOrderRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ExtraAmount", spyOrder.getExtraAmount());
    verify(spyOrder, times(1)).getExtraAmount();
  }

  @Test
  public void testGetGiftMessage() {
    assertNull("GiftMessage", spyOrder.getGiftMessage());
    verify(spyOrder, times(1)).getGiftMessage();
  }

  @Test
  public void testGetGiftMessageEnable() {
    assertNull("GiftMessageEnable", spyOrder.getGiftMessageEnable());
    verify(spyOrder, times(1)).getGiftMessageEnable();
  }

  @Test
  public void testGetGiftReceiptEnable() {
    assertNull("GiftReceiptEnable", spyOrder.getGiftReceiptEnable());
    verify(spyOrder, times(1)).getGiftReceiptEnable();
  }

  @Test
  public void testGetHandlingTotal() {
    assertNotNull("HandlingTotal", spyOrder.getHandlingTotal());
    verify(spyOrder, times(1)).getHandlingTotal();
  }

  @Test
  public void testGetInsuranceTotal() {
    assertNotNull("InsuranceTotal", spyOrder.getInsuranceTotal());
    verify(spyOrder, times(1)).getInsuranceTotal();
  }

  @Test
  public void testGetInvoice() {
    assertNotNull("Invoice", spyOrder.getInvoice());
    verify(spyOrder, times(1)).getInvoice();
  }


  @Test
  public void testGetItemTotal() {
    when(mockOrderRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemTotal", spyOrder.getItemTotal());
    verify(spyOrder, times(1)).getItemTotal();
  }

  @Test
  public void testGetMaxTotal() {
    when(mockOrderRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOrderRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOrderRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("MaxTotal", spyOrder.getMaxTotal());
    verify(spyOrder, times(1)).getMaxTotal();
  }

  @Test
  public void testGetNote() {
    assertNull("Note", spyOrder.getNote());
    verify(spyOrder, times(1)).getNote();
  }

  @Test
  public void testGetNoteText() {
    assertNull("NoteText", spyOrder.getNoteText());
    verify(spyOrder, times(1)).getNoteText();
  }

  @Test
  public void testGetNotificationId() {
    assertNull("NotificationId", spyOrder.getNotificationId());
    verify(spyOrder, times(1)).getNotificationId();
  }

  @Test
  public void testGetOrderDate() {
    assertNull("OrderDate", spyOrder.getOrderDate());
    verify(spyOrder, times(1)).getOrderDate();
  }


  @Test
  public void testGetOrderDescription() {
    assertNull("OrderDescription", spyOrder.getOrderDescription());
    verify(spyOrder, times(1)).getOrderDescription();
  }

  @Test
  public void testGetOrderId() {
    assertNotNull("OrderId", spyOrder.getOrderId());
    verify(spyOrder, times(1)).getOrderId();
  }


  @Test
  public void testGetOrderTotal() {
    when(mockOrderRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOrderRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOrderRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("OrderTotal", spyOrder.getOrderTotal());
    verify(spyOrder, times(1)).getOrderTotal();
  }

  @Test
  public void testGetRecords() {
    assertNotNull("Records", spyOrder.getRecords());
    verify(spyOrder, times(1)).getRecords();
  }

  @Test
  public void testGetShipment() {
    assertNotNull("Shipment", spyOrder.getShipment());
    verify(spyOrder, times(1)).getShipment();
  }

  @Test
  public void testGetShippingDiscount() {
    assertNotNull("ShippingDiscount", spyOrder.getShippingDiscount());
    verify(spyOrder, times(1)).getShippingDiscount();
  }

  @Test
  public void testGetShippingTotal() {
    when(mockOrderRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingTotal", spyOrder.getShippingTotal());
    verify(spyOrder, times(1)).getShippingTotal();
  }

  @Test
  public void testGetTaxTotal() {
    when(mockOrderRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("TaxTotal", spyOrder.getTaxTotal());
    verify(spyOrder, times(1)).getTaxTotal();
  }

  @Test
  public void testGetToken() {
    assertNull("Token", spyOrder.getToken());
    verify(spyOrder, times(1)).getToken();
  }

  @Test
  public void testGetTransactionId() {
    assertNull("TransactionId", spyOrder.getTransactionId());
    verify(spyOrder, times(1)).getTransactionId();
  }

  @Test
  public void testGiftWrapAmount() {
    assertNull("GiftWrapAmount", spyOrder.getGiftWrapAmount());
    verify(spyOrder, times(1)).getGiftWrapAmount();
  }

  @Test
  public void testGiftWrapEnable() {
    assertNull("GiftWrapEnable", spyOrder.getGiftWrapEnable());
    verify(spyOrder, times(1)).getGiftWrapEnable();
  }

  @Test
  public void testGiftWrapName() {
    assertNull("GiftWrapName", spyOrder.getGiftWrapName());
    verify(spyOrder, times(1)).getGiftWrapName();
  }

  @Test
  public void testInsuranceOptionOffered() {
    assertNull("InsuranceOptionOffered", spyOrder.getInsuranceOptionOffered());
    verify(spyOrder, times(1)).getInsuranceOptionOffered();
  }

  @Test
  public void testOrderIsDetached() {
    spyOrder.setId(1L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockInvoice.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertTrue("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsDetachedByContract() {
    spyOrder.setId(0L);
    when(mockContract.isDetached()).thenReturn(true);
    when(mockInvoice.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertTrue("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsDetachedByInvoice() {
    spyOrder.setId(0L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockInvoice.isDetached()).thenReturn(true);
    when(mockShipment.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertTrue("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsDetachedByRecords() {
    spyOrder.setId(0L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockInvoice.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(true);
    assertTrue("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsDetachedByShipment() {
    spyOrder.setId(0L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockInvoice.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(true);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertTrue("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsNotDetached() {
    spyOrder.setId(0L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockInvoice.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertFalse("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsNotDetachedByRecordsIsNull() {
    spyOrder.setId(0L);
    spyOrder.setRecords(null);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(false);
    assertFalse("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, never()).isDetached();
  }

  @Test
  public void testOrderIsNotDetachedWithContractIsNull() {
    spyOrder.setId(0L);
    spyOrder.setContract(null);
    when(mockInvoice.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertFalse("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, never()).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsNotDetachedWithInvoiceIsNull() {
    spyOrder.setId(0L);
    spyOrder.setInvoice(null);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockShipment.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertFalse("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, never()).isDetached();
    verify(mockShipment, times(1)).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testOrderIsNotDetachedWithShipmentIsNull() {
    spyOrder.setId(0L);
    spyOrder.setShipment(null);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockInvoice.isDetached()).thenReturn(false);
    when(mockOrderRecord.isDetached()).thenReturn(false);
    assertFalse("Detached", spyOrder.isDetached());
    verify(spyOrder, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockInvoice, times(1)).isDetached();
    verify(mockShipment, never()).isDetached();
    verify(mockOrderRecord, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    when(mockOrderRecord.getDiscountTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOrderRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOrderRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOrderRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    spyOrder.prePersist();
    verify(spyOrder, times(1)).getOrderId();
    verify(spyOrder, times(2)).getTaxTotal();
    verify(spyOrder, times(2)).getShippingTotal();
    verify(spyOrder, times(2)).getOrderTotal();
    verify(spyOrder, times(2)).getItemTotal();
    verify(spyOrder, times(1)).getMaxTotal();
    verify(spyOrder, times(1)).getDiscountTotal();
    verify(mockOrderRecord, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    spyOrder.preUpdate();
    verify(mockOrderRecord, times(1)).setPosition(anyInt());
  }

  @Test
  public void testSetBillingAgreementId() {
    spyOrder.setBillingAgreementId("Folly words widow one downs few age every seven.");
    assertEquals("BillingAgreementId", "Folly words widow one downs few age every seven.",
        spyOrder.getBillingAgreementId());
    verify(spyOrder, times(1)).setBillingAgreementId(anyString());
  }

  @Test
  public void testSetCheckout() {
    spyOrder.setCheckout("Folly words widow one downs few age every seven.");
    assertEquals("Checkout", "Folly words widow one downs few age every seven.", spyOrder.getCheckout());
    verify(spyOrder, times(1)).setCheckout(anyString());
  }

  @Test
  public void testSetCheckoutStatus() {
    spyOrder.setCheckoutStatus("Folly words widow one downs few age every seven.");
    assertEquals("CheckoutStatus", "Folly words widow one downs few age every seven.", spyOrder.getCheckoutStatus());
    verify(spyOrder, times(1)).setCheckoutStatus(anyString());
  }

  @Test
  public void testSetCheckoutStatusWithEmptyString() {
    spyOrder.setCheckoutStatus(" ");
    assertEquals("CheckoutStatus", "PENDING", spyOrder.getCheckoutStatus());
    verify(spyOrder, times(1)).setCheckoutStatus(anyString());
  }

  @Test
  public void testSetContract() {
    spyOrder.setContract(null);
    assertNull("Contract", spyOrder.getContract());
    verify(spyOrder, times(1)).setContract(null);
  }

  @Test
  public void testSetCustom() {
    spyOrder.setCustom("Folly words widow one downs few age every seven.");
    assertEquals("Custom", "Folly words widow one downs few age every seven.", spyOrder.getCustom());
    verify(spyOrder, times(1)).setCustom(anyString());
  }

  @Test
  public void testSetCustomWithEmptyString() {
    spyOrder.setCustom(" ");
    assertNotEquals("Custom", " ", spyOrder.getCustom());
    verify(spyOrder, times(1)).setCustom(anyString());
  }

  @Test
  public void testSetDiscountTotal() {
    spyOrder.setDiscountTotal(BigDecimal.ZERO);
    assertEquals("DiscountTotal", BigDecimal.ZERO, spyOrder.getDiscountTotal());
    verify(spyOrder, times(1)).setDiscountTotal(any());
  }

  @Test
  public void testSetExtraAmount() {
    spyOrder.setExtraAmount(BigDecimal.ZERO);
    assertEquals("ExtraAmount", BigDecimal.ZERO, spyOrder.getExtraAmount());
    verify(spyOrder, times(1)).setExtraAmount(any());
  }

  @Test
  public void testSetGiftMessage() {
    spyOrder.setGiftMessage("Folly words widow one downs few age every seven.");
    assertEquals("GiftMessage", "Folly words widow one downs few age every seven.", spyOrder.getGiftMessage());
    verify(spyOrder, times(1)).setGiftMessage(anyString());
  }

  @Test
  public void testSetGiftMessageEnable() {
    spyOrder.setGiftMessageEnable(Boolean.FALSE);
    assertEquals("GiftMessageEnable", Boolean.FALSE, spyOrder.getGiftMessageEnable());
    verify(spyOrder, times(1)).setGiftMessageEnable(Boolean.FALSE);
  }

  @Test
  public void testSetGiftReceiptEnable() {
    spyOrder.setGiftReceiptEnable(Boolean.FALSE);
    assertEquals("GiftReceiptEnable", Boolean.FALSE, spyOrder.getGiftReceiptEnable());
    verify(spyOrder, times(1)).setGiftReceiptEnable(any());
  }

  @Test
  public void testSetGiftWrapAmount() {
    spyOrder.setGiftWrapAmount(BigDecimal.ZERO);
    assertEquals("GiftWrapAmount", BigDecimal.ZERO, spyOrder.getGiftWrapAmount());
    verify(spyOrder, times(1)).setGiftWrapAmount(any());
  }

  @Test
  public void testSetGiftWrapEnable() {
    spyOrder.setGiftWrapEnable(Boolean.FALSE);
    assertEquals("GiftWrapEnable", Boolean.FALSE, spyOrder.getGiftWrapEnable());
    verify(spyOrder, times(1)).setGiftWrapEnable(any());
  }

  @Test
  public void testSetGiftWrapName() {
    spyOrder.setGiftWrapName("Folly words widow one downs few age every seven.");
    assertEquals("GiftWrapName", "Folly words widow one downs few age every seven.", spyOrder.getGiftWrapName());
    verify(spyOrder, times(1)).setGiftWrapName(any());
  }

  @Test
  public void testSetHandlingTotal() {
    spyOrder.setHandlingTotal(BigDecimal.ZERO);
    assertEquals("HandlingTotal", BigDecimal.ZERO, spyOrder.getHandlingTotal());
    verify(spyOrder, times(1)).setHandlingTotal(any());
  }

  @Test
  public void testSetInsuranceOptionOffered() {
    spyOrder.setInsuranceOptionOffered(Boolean.FALSE);
    assertEquals("InsuranceOptionOffered", Boolean.FALSE, spyOrder.getInsuranceOptionOffered());
    verify(spyOrder, times(1)).setInsuranceOptionOffered(any());
  }

  @Test
  public void testSetInsuranceTotal() {
    spyOrder.setInsuranceTotal(BigDecimal.ZERO);
    assertEquals("InsuranceTotal", BigDecimal.ZERO, spyOrder.getInsuranceTotal());
    verify(spyOrder, times(1)).setInsuranceTotal(any());
  }

  @Test
  public void testSetItemTotal() {
    spyOrder.setItemTotal(BigDecimal.ZERO);
    assertEquals("ItemTotal", BigDecimal.ZERO, spyOrder.getItemTotal());
    verify(spyOrder, times(1)).setItemTotal(any());
  }

  @Test
  public void testSetMaxTotal() {
    spyOrder.setMaxTotal(BigDecimal.ZERO);
    assertEquals("MaxTotal", BigDecimal.ZERO, spyOrder.getMaxTotal());
    verify(spyOrder, times(1)).setMaxTotal(any());
  }

  @Test
  public void testSetNote() {
    spyOrder.setNote("Folly words widow one downs few age every seven.");
    assertEquals("Note", "Folly words widow one downs few age every seven.", spyOrder.getNote());
    verify(spyOrder, times(1)).setNote(anyString());
  }

  @Test
  public void testSetNoteText() {
    spyOrder.setNoteText("Folly words widow one downs few age every seven.");
    assertEquals("NoteText", "Folly words widow one downs few age every seven.", spyOrder.getNoteText());
    verify(spyOrder, times(1)).setNoteText(anyString());
  }

  @Test
  public void testSetNotificationId() {
    spyOrder.setNotificationId("Folly words widow one downs few age every seven.");
    assertEquals("NotificationId", "Folly words widow one downs few age every seven.", spyOrder.getNotificationId());
    verify(spyOrder, times(1)).setNotificationId(anyString());
  }

  @Test
  public void testSetOrderDate() {
    spyOrder.setOrderDate(new Date());
    assertNotNull("OrderDate", spyOrder.getOrderDate());
    verify(spyOrder, times(1)).setOrderDate(any(Date.class));
  }

  @Test
  public void testSetOrderDateIsNull() {
    spyOrder.setOrderDate(null);
    assertNull(spyOrder.getOrderDate());
    verify(spyOrder, times(1)).setOrderDate(null);
  }

  @Test
  public void testSetOrderDescription() {
    spyOrder.setOrderDescription("Folly words widow one downs few age every seven.");
    assertEquals("OrderDescription", "Folly words widow one downs few age every seven.",
        spyOrder.getOrderDescription());
    verify(spyOrder, times(1)).setOrderDescription(anyString());
  }

  @Test
  public void testSetOrderId() {
    spyOrder.setOrderId("Folly words widow one downs few age every seven.");
    assertEquals("OrderId", "Folly words widow one downs few age every seven.", spyOrder.getOrderId());
    verify(spyOrder, times(1)).setOrderId(anyString());
  }

  @Test
  public void testSetOrderIdWithEmptyString() {
    spyOrder.setOrderId(" ");
    assertNotEquals("OrderId", " ", spyOrder.getOrderId());
    verify(spyOrder, times(1)).setOrderId(anyString());
  }

  @Test
  public void testSetOrderTotal() {
    spyOrder.setOrderTotal(BigDecimal.ZERO);
    assertEquals("OrderTotal", BigDecimal.ZERO, spyOrder.getOrderTotal());
    verify(spyOrder, times(1)).setOrderTotal(any());
  }

  @Test
  public void testSetRecords() {
    spyOrder.setRecords(null);
    assertNull("Records", spyOrder.getRecords());
    verify(spyOrder, times(1)).setRecords(null);
  }

  @Test
  public void testSetShippingDiscount() {
    spyOrder.setShippingDiscount(BigDecimal.ZERO);
    assertEquals("ShippingDiscount", BigDecimal.ZERO, spyOrder.getShippingDiscount());
    verify(spyOrder, times(1)).setShippingDiscount(any());
  }

  @Test
  public void testSetShippingTotal() {
    spyOrder.setShippingTotal(BigDecimal.ZERO);
    assertEquals("ShippingTotal", BigDecimal.ZERO, spyOrder.getShippingTotal());
    verify(spyOrder, times(1)).setShippingTotal(any());
  }

  @Test
  public void testSetTaxTotal() {
    spyOrder.setTaxTotal(BigDecimal.ZERO);
    assertEquals("TaxTotal", BigDecimal.ZERO, spyOrder.getTaxTotal());
    verify(spyOrder, times(1)).setTaxTotal(any());
  }

  @Test
  public void testSetToken() {
    spyOrder.setToken("Folly words widow one downs few age every seven.");
    assertEquals("Token", "Folly words widow one downs few age every seven.", spyOrder.getToken());
    verify(spyOrder, times(1)).setToken(anyString());
  }

  @Test
  public void testSetTransactionId() {
    spyOrder.setTransactionId("Folly words widow one downs few age every seven.");
    assertEquals("TransactionId", "Folly words widow one downs few age every seven.", spyOrder.getTransactionId());
    verify(spyOrder, times(1)).setTransactionId(anyString());
  }

  @Test
  public void testToString() {
    assertEquals(
        new ReflectionToStringBuilder(spyOrder, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP).toString(),
        spyOrder.toString());
  }
}
