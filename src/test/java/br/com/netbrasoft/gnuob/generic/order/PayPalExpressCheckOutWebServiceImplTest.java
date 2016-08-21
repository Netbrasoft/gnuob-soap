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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.order.ICheckOutWebService;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.order.OrderRecord;
import br.com.netbrasoft.gnuob.generic.order.PayPalExpressCheckOutWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeCheckOutService;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class PayPalExpressCheckOutWebServiceImplTest {

  private Contract mockContract;
  private MetaData mockCredentials;
  private Customer mockCustomer;
  private OrderRecord mockOrderRecord;
  private ISecuredGenericTypeService<Contract> mockSecuredGenericContractService;
  private ISecuredGenericTypeService<Customer> mockSecuredGenericCustomerService;
  private ISecuredGenericTypeService<Order> mockSecuredGenericOrderService;
  private ISecuredGenericTypeCheckOutService<Order> mockSecuredGenericTypeCheckOutService;
  private ICheckOutWebService<Order> pagseguroCheckOutWebServiceImpl = new PayPalExpressCheckOutWebServiceImpl<>();
  private Order spyOrder;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockCustomer = mock(Customer.class);
    mockContract = mock(Contract.class);
    mockOrderRecord = mock(OrderRecord.class);
    spyOrder = spy(Order.class);
    spyOrder.setContract(mockContract);
    spyOrder.setRecords(newSet(mockOrderRecord));
    mockSecuredGenericTypeCheckOutService = mock(ISecuredGenericTypeCheckOutService.class);
    mockSecuredGenericCustomerService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericContractService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericOrderService = mock(ISecuredGenericTypeService.class);
    pagseguroCheckOutWebServiceImpl = new PayPalExpressCheckOutWebServiceImpl<>(mockSecuredGenericTypeCheckOutService,
        mockSecuredGenericOrderService, mockSecuredGenericContractService, mockSecuredGenericCustomerService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDoCheckoutDetailsOrderGettingGNUOpenBusinessServiceException() {
    pagseguroCheckOutWebServiceImpl = new PayPalExpressCheckOutWebServiceImpl<Order>();
    pagseguroCheckOutWebServiceImpl.doCheckoutDetails(mockCredentials, spyOrder);
  }

  @Test
  public void testDoCheckoutDetailsOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNotNull("CheckoutDetails", pagseguroCheckOutWebServiceImpl.doCheckoutDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).persist(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutDetails(any(), any());
  }

  @Test
  public void testDoCheckoutDetailsOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("CheckoutDetails", pagseguroCheckOutWebServiceImpl.doCheckoutDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutDetails(any(), any());
  }

  @Test
  public void testDoCheckoutDetailsOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("CheckoutDetails", pagseguroCheckOutWebServiceImpl.doCheckoutDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutDetails(any(), any());
  }

  @Test
  public void testDoCheckoutDetailsOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("CheckoutDetails", pagseguroCheckOutWebServiceImpl.doCheckoutDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutDetails(any(), any());
  }

  @Test
  public void testDoCheckoutDetailsOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("CheckoutDetails", pagseguroCheckOutWebServiceImpl.doCheckoutDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutDetails(any(), any());
  }

  @Test
  public void testDoCheckoutDetailsOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("CheckoutDetails", pagseguroCheckOutWebServiceImpl.doCheckoutDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutDetails(any(), any());
  }


  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDoCheckoutOrderGettingGNUOpenBusinessServiceException() {
    pagseguroCheckOutWebServiceImpl = new PayPalExpressCheckOutWebServiceImpl<Order>();
    pagseguroCheckOutWebServiceImpl.doCheckout(mockCredentials, spyOrder);
  }

  @Test
  public void testDoCheckoutOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNotNull("Checkout", pagseguroCheckOutWebServiceImpl.doCheckout(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).persist(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckout(any(), any());
  }

  @Test
  public void testDoCheckoutOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Checkout", pagseguroCheckOutWebServiceImpl.doCheckout(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckout(any(), any());
  }

  @Test
  public void testDoCheckoutOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Checkout", pagseguroCheckOutWebServiceImpl.doCheckout(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckout(any(), any());
  }

  @Test
  public void testDoCheckoutOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Checkout", pagseguroCheckOutWebServiceImpl.doCheckout(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckout(any(), any());
  }

  @Test
  public void testDoCheckoutOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Checkout", pagseguroCheckOutWebServiceImpl.doCheckout(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckout(any(), any());
  }

  @Test
  public void testDoCheckoutOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("Checkout", pagseguroCheckOutWebServiceImpl.doCheckout(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckout(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDoCheckoutPaymentOrderGettingGNUOpenBusinessServiceException() {
    pagseguroCheckOutWebServiceImpl = new PayPalExpressCheckOutWebServiceImpl<Order>();
    pagseguroCheckOutWebServiceImpl.doCheckoutPayment(mockCredentials, spyOrder);
  }

  @Test
  public void testDoCheckoutPaymentOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNotNull("CheckoutPayment", pagseguroCheckOutWebServiceImpl.doCheckoutPayment(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).persist(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutPayment(any(), any());
  }

  @Test
  public void testDoCheckoutPaymentOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("CheckoutPayment", pagseguroCheckOutWebServiceImpl.doCheckoutPayment(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutPayment(any(), any());
  }

  @Test
  public void testDoCheckoutPaymentOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("CheckoutPayment", pagseguroCheckOutWebServiceImpl.doCheckoutPayment(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutPayment(any(), any());
  }

  @Test
  public void testDoCheckoutPaymentOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("CheckoutPayment", pagseguroCheckOutWebServiceImpl.doCheckoutPayment(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutPayment(any(), any());
  }

  @Test
  public void testDoCheckoutPaymentOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("CheckoutPayment", pagseguroCheckOutWebServiceImpl.doCheckoutPayment(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutPayment(any(), any());
  }

  @Test
  public void testDoCheckoutPaymentOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("CheckoutPayment", pagseguroCheckOutWebServiceImpl.doCheckoutPayment(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doCheckoutPayment(any(), any());
  }


  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDoNotificationOrderGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericTypeCheckOutService.doNotification(any(), any())).thenReturn(spyOrder);
    when(mockSecuredGenericOrderService.count(any(), any())).thenReturn(0L);
    pagseguroCheckOutWebServiceImpl.doNotification(mockCredentials, spyOrder);
  }

  @Test
  public void testDoNotificationOrderWithContractIsNull() {
    when(mockSecuredGenericTypeCheckOutService.doNotification(any(), any())).thenReturn(spyOrder);
    when(mockSecuredGenericOrderService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericOrderService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(spyOrder));
    when(spyOrder.getContract()).thenReturn(null);
    assertNotNull("Notification", pagseguroCheckOutWebServiceImpl.doNotification(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).persist(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doNotification(any(), any());
  }

  @Test
  public void testDoNotificationOrderWithDetachedContract() {
    when(mockSecuredGenericTypeCheckOutService.doNotification(any(), any())).thenReturn(spyOrder);
    when(mockSecuredGenericOrderService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericOrderService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(spyOrder));
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Notification", pagseguroCheckOutWebServiceImpl.doNotification(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doNotification(any(), any());
  }

  @Test
  public void testDoNotificationOrderWithDetachedContractAndCustomer() {
    when(mockSecuredGenericTypeCheckOutService.doNotification(any(), any())).thenReturn(spyOrder);
    when(mockSecuredGenericOrderService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericOrderService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(spyOrder));
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Notification", pagseguroCheckOutWebServiceImpl.doNotification(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doNotification(any(), any());
  }

  @Test
  public void testDoNotificationOrderWithDetachedContractAndCustomerInNull() {
    when(mockSecuredGenericTypeCheckOutService.doNotification(any(), any())).thenReturn(spyOrder);
    when(mockSecuredGenericOrderService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericOrderService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(spyOrder));
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Notification", pagseguroCheckOutWebServiceImpl.doNotification(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doNotification(any(), any());
  }

  @Test
  public void testDoNotificationOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockSecuredGenericTypeCheckOutService.doNotification(any(), any())).thenReturn(spyOrder);
    when(mockSecuredGenericOrderService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericOrderService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(spyOrder));
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Notification", pagseguroCheckOutWebServiceImpl.doNotification(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doNotification(any(), any());
  }

  @Test
  public void testDoNotificationOrderWithNoDetachedContract() {
    when(mockSecuredGenericTypeCheckOutService.doNotification(any(), any())).thenReturn(spyOrder);
    when(mockSecuredGenericOrderService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericOrderService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(spyOrder));
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("Notification", pagseguroCheckOutWebServiceImpl.doNotification(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doNotification(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDoRefundTransactionOrderGettingGNUOpenBusinessServiceException() {
    pagseguroCheckOutWebServiceImpl = new PayPalExpressCheckOutWebServiceImpl<Order>();
    pagseguroCheckOutWebServiceImpl.doRefundTransaction(mockCredentials, spyOrder);
  }

  @Test
  public void testDoRefundTransactionOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNotNull("RefundTransaction", pagseguroCheckOutWebServiceImpl.doRefundTransaction(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).persist(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doRefundTransaction(any(), any());
  }

  @Test
  public void testDoRefundTransactionOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("RefundTransaction", pagseguroCheckOutWebServiceImpl.doRefundTransaction(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doRefundTransaction(any(), any());
  }

  @Test
  public void testDoRefundTransactionOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("RefundTransaction", pagseguroCheckOutWebServiceImpl.doRefundTransaction(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doRefundTransaction(any(), any());
  }

  @Test
  public void testDoRefundTransactionOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("RefundTransaction", pagseguroCheckOutWebServiceImpl.doRefundTransaction(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doRefundTransaction(any(), any());
  }

  @Test
  public void testDoRefundTransactionOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("RefundTransaction", pagseguroCheckOutWebServiceImpl.doRefundTransaction(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doRefundTransaction(any(), any());
  }

  @Test
  public void testDoRefundTransactionOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("RefundTransaction", pagseguroCheckOutWebServiceImpl.doRefundTransaction(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doRefundTransaction(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDoTransactionDetailsOrderGettingGNUOpenBusinessServiceException() {
    pagseguroCheckOutWebServiceImpl = new PayPalExpressCheckOutWebServiceImpl<Order>();
    pagseguroCheckOutWebServiceImpl.doTransactionDetails(mockCredentials, spyOrder);
  }

  @Test
  public void testDoTransactionDetailsOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNotNull("TransactionDetails",
        pagseguroCheckOutWebServiceImpl.doTransactionDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).persist(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doTransactionDetails(any(), any());
  }

  @Test
  public void testDoTransactionDetailsOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("TransactionDetails", pagseguroCheckOutWebServiceImpl.doTransactionDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doTransactionDetails(any(), any());
  }

  @Test
  public void testDoTransactionDetailsOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("TransactionDetails", pagseguroCheckOutWebServiceImpl.doTransactionDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doTransactionDetails(any(), any());
  }

  @Test
  public void testDoTransactionDetailsOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("TransactionDetails", pagseguroCheckOutWebServiceImpl.doTransactionDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doTransactionDetails(any(), any());
  }

  @Test
  public void testDoTransactionDetailsOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("TransactionDetails", pagseguroCheckOutWebServiceImpl.doTransactionDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doTransactionDetails(any(), any());
  }

  @Test
  public void testDoTransactionDetailsOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("TransactionDetails",
        pagseguroCheckOutWebServiceImpl.doTransactionDetails(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).merge(any(), any());
    verify(mockSecuredGenericTypeCheckOutService, times(1)).doTransactionDetails(any(), any());
  }
}
