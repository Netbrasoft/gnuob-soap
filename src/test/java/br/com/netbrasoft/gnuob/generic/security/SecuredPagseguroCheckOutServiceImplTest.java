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

package br.com.netbrasoft.gnuob.generic.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Address;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.order.IPagseguroCheckOutService;
import br.com.netbrasoft.gnuob.generic.order.Invoice;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.order.OrderRecord;
import br.com.netbrasoft.gnuob.generic.order.Payment;
import br.com.netbrasoft.gnuob.generic.order.Shipment;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeCheckOutService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.generic.security.SecuredPagseguroCheckOutServiceImpl;
import br.com.netbrasoft.gnuob.generic.security.Site;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.PaymentMethod;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.Shipping;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.enums.PaymentMethodType;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.enums.TransactionStatus;
import br.com.uol.pagseguro.enums.TransactionType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;

public class SecuredPagseguroCheckOutServiceImplTest {

  private MetaData mockCredentials;
  private IPagseguroCheckOutService mockPagseguroCheckOutService;
  private ISecuredGenericTypeCheckOutService<Order> securedGenericTypeCheckOutService =
      new SecuredPagseguroCheckOutServiceImpl<>();
  private Order spyOrder;
  private OrderRecord spyOrderRecord;
  private Transaction mockTransaction;
  private Sender mockSender;
  private Shipping mockShipping;
  private br.com.uol.pagseguro.domain.Address mockAddress;
  private Item mockItem;
  private PaymentMethod mockPaymentMethod;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    Locale.setDefault(new Locale("pt", "BR"));
    mockCredentials = mock(MetaData.class);
    mockPagseguroCheckOutService = mock(IPagseguroCheckOutService.class);
    securedGenericTypeCheckOutService = new SecuredPagseguroCheckOutServiceImpl<>(mockPagseguroCheckOutService);
    spyOrderRecord = spy(OrderRecord.class);
    spyOrder = spy(Order.class);
    spyOrder.setContract(spy(Contract.class));
    spyOrder.getContract().setCustomer(spy(Customer.class));
    spyOrder.setShipment(spy(Shipment.class));
    spyOrder.getShipment().setAddress(spy(Address.class));
    spyOrder.getRecords().add(spyOrderRecord);
    spyOrder.setSite(spy(Site.class));
    spyOrder.setInvoice(spy(Invoice.class));
    mockTransaction = mock(Transaction.class);
    mockSender = mock(Sender.class);
    mockShipping = mock(Shipping.class);
    mockAddress = mock(br.com.uol.pagseguro.domain.Address.class);
    mockItem = mock(Item.class);
    mockPaymentMethod = mock(PaymentMethod.class);
  }

  @After
  public void tearDown() throws Exception {
    Locale.setDefault(Locale.US);
  }

  @Test
  public void testDoCheckoutSucessfully() throws PagSeguroServiceException {
    spyOrder.setExtraAmount(BigDecimal.valueOf(100.00));
    spyOrder.setOrderId("ORDER ID");
    spyOrder.setShippingTotal(BigDecimal.valueOf(100.00));
    spyOrder.setShippingDiscount(BigDecimal.ZERO);
    spyOrder.getShipment().setShipmentType("PAC");
    spyOrder.getShipment().getAddress().setCityName("CITY NAME");
    spyOrder.getShipment().getAddress().setStreet1("STREET 1");
    spyOrder.getShipment().getAddress().setComplement("COMPLEMENT");
    spyOrder.getShipment().getAddress().setCountry("BR");
    spyOrder.getShipment().getAddress().setDistrict("DISTRICT");
    spyOrder.getShipment().getAddress().setNumber("NUMBER");
    spyOrder.getShipment().getAddress().setPostalCode("POSTAL CODE");
    spyOrder.getShipment().getAddress().setStateOrProvince("STATE OR PROVINCE");
    spyOrderRecord.setAmount(BigDecimal.valueOf(100.00));
    spyOrderRecord.setDescription("DESCRIPTION");
    spyOrderRecord.setOrderRecordId("NUMBER");
    spyOrderRecord.setQuantity(BigInteger.valueOf(100));
    spyOrderRecord.setShippingCost(BigDecimal.valueOf(10.00));
    spyOrderRecord.setItemWeight(BigDecimal.valueOf(1.00));
    spyOrder.getContract().getCustomer().setBuyerEmail("BUYER EMAIL");
    spyOrder.getContract().getCustomer().setFriendlyName("FRIENDLY NAME");
    when(mockPagseguroCheckOutService.createCheckoutRequest(any(), anyBoolean())).thenReturn("code=1234567890");
    securedGenericTypeCheckOutService.doCheckout(mockCredentials, spyOrder);
    assertEquals("1234567890", spyOrder.getToken());
    verify(mockPagseguroCheckOutService).createCheckoutRequest(any(), anyBoolean());
  }

  @Test
  public void testDoCheckoutUnSucessfully() throws PagSeguroServiceException {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage("Exception from Pagseguro Checkout, please try again.");
    spyOrder.setExtraAmount(BigDecimal.valueOf(100.00));
    spyOrder.setOrderId("ORDER ID");
    spyOrder.setShippingTotal(BigDecimal.valueOf(100.00));
    spyOrder.setShippingDiscount(BigDecimal.ZERO);
    spyOrder.getShipment().setShipmentType("PAC");
    spyOrder.getShipment().getAddress().setCityName("CITY NAME");
    spyOrder.getShipment().getAddress().setStreet1("STREET 1");
    spyOrder.getShipment().getAddress().setComplement("COMPLEMENT");
    spyOrder.getShipment().getAddress().setCountry("BR");
    spyOrder.getShipment().getAddress().setDistrict("DISTRICT");
    spyOrder.getShipment().getAddress().setNumber("NUMBER");
    spyOrder.getShipment().getAddress().setPostalCode("POSTAL CODE");
    spyOrder.getShipment().getAddress().setStateOrProvince("STATE OR PROVINCE");
    spyOrderRecord.setAmount(BigDecimal.valueOf(100.00));
    spyOrderRecord.setDescription("DESCRIPTION");
    spyOrderRecord.setOrderRecordId("NUMBER");
    spyOrderRecord.setQuantity(BigInteger.valueOf(100));
    spyOrderRecord.setShippingCost(BigDecimal.valueOf(10.00));
    spyOrderRecord.setItemWeight(BigDecimal.valueOf(1.00));
    spyOrder.getContract().getCustomer().setBuyerEmail("BUYER EMAIL");
    spyOrder.getContract().getCustomer().setFriendlyName("FRIENDLY NAME");
    when(mockPagseguroCheckOutService.createCheckoutRequest(any(), anyBoolean()))
        .thenThrow(new PagSeguroServiceException(StringUtils.EMPTY));
    securedGenericTypeCheckOutService.doCheckout(mockCredentials, spyOrder);
  }

  @Test
  public void testDoCheckoutDetailsSucessfully() throws PagSeguroServiceException {
    spyOrderRecord.setOrderRecordId("NUMBER");
    when(mockPagseguroCheckOutService.searchByCode(anyString())).thenReturn(mockTransaction);
    when(mockTransaction.getSender()).thenReturn(mockSender);
    when(mockSender.getEmail()).thenReturn("PAYER");
    when(mockSender.getName()).thenReturn("FRIENDLY NAME");
    when(mockSender.getPhone()).thenReturn(mock(Phone.class));
    when(mockTransaction.getCode()).thenReturn("TRANSACTION ID");
    when(mockTransaction.getReference()).thenReturn("ORDER ID");
    when(mockTransaction.getDiscountAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getExtraAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getShipping()).thenReturn(mockShipping);
    when(mockShipping.getCost()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockShipping.getType()).thenReturn(ShippingType.PAC);
    when(mockShipping.getAddress()).thenReturn(mockAddress);
    when(mockAddress.getCountry()).thenReturn("BR");
    when(mockAddress.getState()).thenReturn("STATE OR PROVINCE");
    when(mockAddress.getCity()).thenReturn("CITY NAME");
    when(mockAddress.getPostalCode()).thenReturn("POSTAL CODE");
    when(mockAddress.getDistrict()).thenReturn("DISTRICT");
    when(mockAddress.getStreet()).thenReturn("STREET 1");
    when(mockAddress.getNumber()).thenReturn("NUMBER");
    when(mockAddress.getComplement()).thenReturn("COMPLEMENT");
    when(mockItem.getId()).thenReturn("NUMBER");
    when(mockItem.getDescription()).thenReturn("DESCRIPTION");
    when(mockItem.getAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockItem.getQuantity()).thenReturn(100);
    when(mockTransaction.getItems()).thenReturn(Lists.newArrayList(mockItem));
    when(mockTransaction.getStatus()).thenReturn(TransactionStatus.PAID);
    securedGenericTypeCheckOutService.doCheckoutDetails(mockCredentials, spyOrder);
    assertEquals("Payer", "PAYER", spyOrder.getContract().getCustomer().getPayer());
    assertEquals("FriendlyName", "FRIENDLY NAME", spyOrder.getContract().getCustomer().getFriendlyName());
    assertEquals("TransactionId", "TRANSACTION ID", spyOrder.getTransactionId());
    assertEquals("OrderId", "ORDER ID", spyOrder.getOrderId());
    assertEquals("DiscountTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getDiscountTotal()));
    assertEquals("ExtraAmount", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getExtraAmount()));
    assertEquals("ShippingTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getShippingTotal()));
    assertEquals("ShipmentType", "PAC", spyOrder.getShipment().getShipmentType());
    assertEquals("Country", "BR", spyOrder.getShipment().getAddress().getCountry());
    assertEquals("StateOrProvince", "STATE OR PROVINCE", spyOrder.getShipment().getAddress().getStateOrProvince());
    assertEquals("CityName", "CITY NAME", spyOrder.getShipment().getAddress().getCityName());
    assertEquals("PostalCode", "POSTAL CODE", spyOrder.getShipment().getAddress().getPostalCode());
    assertEquals("District", "DISTRICT", spyOrder.getShipment().getAddress().getDistrict());
    assertEquals("Street1", "STREET 1", spyOrder.getShipment().getAddress().getStreet1());
    assertEquals("Number", "NUMBER", spyOrder.getShipment().getAddress().getNumber());
    assertEquals("Complement", "COMPLEMENT", spyOrder.getShipment().getAddress().getComplement());
    assertEquals("OrderRecordId", "NUMBER", spyOrderRecord.getOrderRecordId());
    assertEquals("Description", "DESCRIPTION", spyOrderRecord.getDescription());
    assertEquals("Amount", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getAmount()));
    assertEquals("Quantity", 0, BigInteger.valueOf(100).compareTo(spyOrderRecord.getQuantity()));
    assertEquals("CheckoutStatus", "PAID", spyOrder.getCheckoutStatus());
    verify(mockPagseguroCheckOutService).searchByCode(anyString());
  }

  @Test
  public void testDoCheckoutDetailsUnSucessfully() throws PagSeguroServiceException {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage("Exception from Pagseguro Checkout, please try again.");
    when(mockPagseguroCheckOutService.searchByCode(anyString()))
        .thenThrow(new PagSeguroServiceException(StringUtils.EMPTY));
    securedGenericTypeCheckOutService.doCheckoutDetails(mockCredentials, spyOrder);
  }

  @Test
  public void testDoCheckoutPaymentSucessfully() throws PagSeguroServiceException {
    when(mockPagseguroCheckOutService.searchByCode(anyString())).thenReturn(mockTransaction);
    when(mockTransaction.getCode()).thenReturn("TRANSACTION ID");
    when(mockTransaction.getType()).thenReturn(TransactionType.PAYMENT);
    when(mockPaymentMethod.getType()).thenReturn(PaymentMethodType.CREDIT_CARD);
    when(mockTransaction.getPaymentMethod()).thenReturn(mockPaymentMethod);
    when(mockTransaction.getGrossAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getNetAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getEscrowEndDate()).thenReturn(new Date());
    when(mockTransaction.getCancellationSource()).thenReturn("HOLD DECISION");
    when(mockTransaction.getInstallmentCount()).thenReturn(100);
    when(mockTransaction.getFeeAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getStatus()).thenReturn(TransactionStatus.PAID);
    securedGenericTypeCheckOutService.doCheckoutPayment(mockCredentials, spyOrder);
    final Payment payment = spyOrder.getInvoice().getPayments().iterator().next();
    assertEquals("TransactionId", "TRANSACTION ID", payment.getTransactionId());
    assertEquals("TransactionType", "PAYMENT", payment.getTransactionType());
    assertEquals("PaymentType", "CREDIT CARD", payment.getPaymentType());
    assertEquals("GrossAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getGrossAmount()));
    assertEquals("SettleAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getSettleAmount()));
    assertEquals("HoldDecision", "HOLD DECISION", payment.getHoldDecision());
    assertEquals("InstallmentCount", 0, BigInteger.valueOf(100).compareTo(payment.getInstallmentCount()));
    assertEquals("FeeAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getFeeAmount()));
    assertEquals("PaymentStatus", "PAID", payment.getPaymentStatus());
    assertEquals("CheckoutStatus", "PAID", spyOrder.getCheckoutStatus());
    verify(mockPagseguroCheckOutService).searchByCode(anyString());
  }

  @Test
  public void testDoCheckoutPaymentUnSucessfully() throws PagSeguroServiceException {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage("Exception from Pagseguro Checkout, please try again.");
    when(mockPagseguroCheckOutService.searchByCode(anyString()))
        .thenThrow(new PagSeguroServiceException(StringUtils.EMPTY));
    securedGenericTypeCheckOutService.doCheckoutPayment(mockCredentials, spyOrder);
  }

  @Test
  public void testDoNotificationSucessfully() throws PagSeguroServiceException {
    when(mockPagseguroCheckOutService.checkTransaction(anyString())).thenReturn(mockTransaction);
    when(mockTransaction.getCode()).thenReturn("TRANSACTION ID");
    when(mockTransaction.getReference()).thenReturn("ORDER ID");
    securedGenericTypeCheckOutService.doNotification(mockCredentials, spyOrder);
    assertEquals("TransactionId", "TRANSACTION ID", spyOrder.getTransactionId());
    assertEquals("OrderId", "ORDER ID", spyOrder.getOrderId());
    assertNull("NotificationId", spyOrder.getNotificationId());
    verify(mockPagseguroCheckOutService).checkTransaction(anyString());
  }

  @Test
  public void testDoNotificationUnSucessfully() throws PagSeguroServiceException {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage("Exception from Pagseguro Notification, please try again.");
    when(mockPagseguroCheckOutService.checkTransaction(anyString()))
        .thenThrow(new PagSeguroServiceException(StringUtils.EMPTY));
    securedGenericTypeCheckOutService.doNotification(mockCredentials, spyOrder);
  }

  @Test
  public void testDoRefundTransaction() {
    expectedException.expect(UnsupportedOperationException.class);
    expectedException.expectMessage("Refund transaction is not supported for PagSeguro.");
    securedGenericTypeCheckOutService.doRefundTransaction(mockCredentials, spyOrder);
  }

  @Test
  public void testDoTransactionDetailsSucessfully() throws PagSeguroServiceException {
    spyOrderRecord.setOrderRecordId("NUMBER");
    when(mockPagseguroCheckOutService.searchByCode(anyString())).thenReturn(mockTransaction);
    when(mockTransaction.getSender()).thenReturn(mockSender);
    when(mockSender.getEmail()).thenReturn("PAYER");
    when(mockSender.getName()).thenReturn("FRIENDLY NAME");
    when(mockSender.getPhone()).thenReturn(mock(Phone.class));
    when(mockTransaction.getCode()).thenReturn("TRANSACTION ID");
    when(mockTransaction.getReference()).thenReturn("ORDER ID");
    when(mockTransaction.getDiscountAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getExtraAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getShipping()).thenReturn(mockShipping);
    when(mockShipping.getCost()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockShipping.getType()).thenReturn(ShippingType.PAC);
    when(mockShipping.getAddress()).thenReturn(mockAddress);
    when(mockAddress.getCountry()).thenReturn("BR");
    when(mockAddress.getState()).thenReturn("STATE OR PROVINCE");
    when(mockAddress.getCity()).thenReturn("CITY NAME");
    when(mockAddress.getPostalCode()).thenReturn("POSTAL CODE");
    when(mockAddress.getDistrict()).thenReturn("DISTRICT");
    when(mockAddress.getStreet()).thenReturn("STREET 1");
    when(mockAddress.getNumber()).thenReturn("NUMBER");
    when(mockAddress.getComplement()).thenReturn("COMPLEMENT");
    when(mockItem.getId()).thenReturn("NUMBER");
    when(mockItem.getDescription()).thenReturn("DESCRIPTION");
    when(mockItem.getAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockItem.getQuantity()).thenReturn(100);
    when(mockTransaction.getItems()).thenReturn(Lists.newArrayList(mockItem));
    when(mockTransaction.getStatus()).thenReturn(TransactionStatus.PAID);
    when(mockTransaction.getType()).thenReturn(TransactionType.PAYMENT);
    when(mockPaymentMethod.getType()).thenReturn(PaymentMethodType.CREDIT_CARD);
    when(mockTransaction.getPaymentMethod()).thenReturn(mockPaymentMethod);
    when(mockTransaction.getGrossAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getNetAmount()).thenReturn(BigDecimal.valueOf(100.00));
    when(mockTransaction.getEscrowEndDate()).thenReturn(new Date());
    when(mockTransaction.getCancellationSource()).thenReturn("HOLD DECISION");
    when(mockTransaction.getInstallmentCount()).thenReturn(100);
    when(mockTransaction.getFeeAmount()).thenReturn(BigDecimal.valueOf(100.00));
    securedGenericTypeCheckOutService.doTransactionDetails(mockCredentials, spyOrder);
    assertEquals("Payer", "PAYER", spyOrder.getContract().getCustomer().getPayer());
    assertEquals("FriendlyName", "FRIENDLY NAME", spyOrder.getContract().getCustomer().getFriendlyName());
    assertEquals("TransactionId", "TRANSACTION ID", spyOrder.getTransactionId());
    assertEquals("OrderId", "ORDER ID", spyOrder.getOrderId());
    assertEquals("DiscountTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getDiscountTotal()));
    assertEquals("ExtraAmount", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getExtraAmount()));
    assertEquals("ShippingTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getShippingTotal()));
    assertEquals("ShipmentType", "PAC", spyOrder.getShipment().getShipmentType());
    assertEquals("Country", "BR", spyOrder.getShipment().getAddress().getCountry());
    assertEquals("StateOrProvince", "STATE OR PROVINCE", spyOrder.getShipment().getAddress().getStateOrProvince());
    assertEquals("CityName", "CITY NAME", spyOrder.getShipment().getAddress().getCityName());
    assertEquals("PostalCode", "POSTAL CODE", spyOrder.getShipment().getAddress().getPostalCode());
    assertEquals("District", "DISTRICT", spyOrder.getShipment().getAddress().getDistrict());
    assertEquals("Street1", "STREET 1", spyOrder.getShipment().getAddress().getStreet1());
    assertEquals("Number", "NUMBER", spyOrder.getShipment().getAddress().getNumber());
    assertEquals("Complement", "COMPLEMENT", spyOrder.getShipment().getAddress().getComplement());
    assertEquals("OrderRecordId", "NUMBER", spyOrderRecord.getOrderRecordId());
    assertEquals("Description", "DESCRIPTION", spyOrderRecord.getDescription());
    assertEquals("Amount", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getAmount()));
    assertEquals("Quantity", 0, BigInteger.valueOf(100).compareTo(spyOrderRecord.getQuantity()));
    final Payment payment = spyOrder.getInvoice().getPayments().iterator().next();
    assertEquals("TransactionId", "TRANSACTION ID", payment.getTransactionId());
    assertEquals("TransactionType", "PAYMENT", payment.getTransactionType());
    assertEquals("PaymentType", "CREDIT CARD", payment.getPaymentType());
    assertEquals("GrossAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getGrossAmount()));
    assertEquals("SettleAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getSettleAmount()));
    assertEquals("HoldDecision", "HOLD DECISION", payment.getHoldDecision());
    assertEquals("InstallmentCount", 0, BigInteger.valueOf(100).compareTo(payment.getInstallmentCount()));
    assertEquals("FeeAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getFeeAmount()));
    assertEquals("PaymentStatus", "PAID", payment.getPaymentStatus());
    assertEquals("CheckoutStatus", "PAID", spyOrder.getCheckoutStatus());
    verify(mockPagseguroCheckOutService).searchByCode(anyString());
  }

  @Test
  public void testDoTransactionDetailsUnSucessfully() throws PagSeguroServiceException {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage("Exception from Pagseguro Transaction Details, please try again.");
    when(mockPagseguroCheckOutService.searchByCode(anyString()))
        .thenThrow(new PagSeguroServiceException(StringUtils.EMPTY));
    securedGenericTypeCheckOutService.doTransactionDetails(mockCredentials, spyOrder);
  }
}
