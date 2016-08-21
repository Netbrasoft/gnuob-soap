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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.order.OrderRecord;
import br.com.netbrasoft.gnuob.generic.order.OrderWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class OrderWebServiceImplTest {

  private Contract mockContract;
  private MetaData mockCredentials;
  private Customer mockCustomer;
  private OrderRecord mockOrderRecord;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Contract> mockSecuredGenericContractService;
  private ISecuredGenericTypeService<Customer> mockSecuredGenericCustomerService;
  private ISecuredGenericTypeService<Order> mockSecuredGenericOrderService;
  private IGenericTypeWebService<Order> orderWebServiceImpl = new OrderWebServiceImpl<>();
  private Order spyOrder;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockPaging = mock(Paging.class);
    mockCustomer = mock(Customer.class);
    mockContract = mock(Contract.class);
    mockOrderRecord = mock(OrderRecord.class);
    spyOrder = spy(Order.class);
    spyOrder.setContract(mockContract);
    spyOrder.setRecords(newSet(mockOrderRecord));
    mockSecuredGenericCustomerService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericContractService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericOrderService = mock(ISecuredGenericTypeService.class);
    orderWebServiceImpl = new OrderWebServiceImpl<>(mockSecuredGenericOrderService, mockSecuredGenericContractService,
        mockSecuredGenericCustomerService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountOrderGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOrderService.count(any(), any(), anyVararg())).thenThrow(new RuntimeException());
    orderWebServiceImpl.count(mockCredentials, spyOrder);
  }

  @Test
  public void testCountOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertEquals("Count", 0, orderWebServiceImpl.count(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertEquals("Count", 0, orderWebServiceImpl.count(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertEquals("Count", 0, orderWebServiceImpl.count(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertEquals("Count", 0, orderWebServiceImpl.count(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertEquals("Count", 0, orderWebServiceImpl.count(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertEquals("Count", 0, orderWebServiceImpl.count(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).count(any(), any(), anyVararg());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindOrderGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOrderService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    orderWebServiceImpl.find(mockCredentials, spyOrder);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindOrderPagingGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOrderService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    orderWebServiceImpl.find(mockCredentials, spyOrder, mockPaging, OrderByEnum.NONE);
  }

  @Test
  public void testFindOrderPagingWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertTrue("Find", orderWebServiceImpl.find(mockCredentials, spyOrder, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOrderPagingWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertTrue("Find", orderWebServiceImpl.find(mockCredentials, spyOrder, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOrderPagingWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertTrue("Find", orderWebServiceImpl.find(mockCredentials, spyOrder, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOrderPagingWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertTrue("Find", orderWebServiceImpl.find(mockCredentials, spyOrder, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOrderPagingWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertTrue("Find", orderWebServiceImpl.find(mockCredentials, spyOrder, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }


  @Test
  public void testFindOrderPagingWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertTrue("Find", orderWebServiceImpl.find(mockCredentials, spyOrder, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNull("Find", orderWebServiceImpl.find(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Find", orderWebServiceImpl.find(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Find", orderWebServiceImpl.find(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Find", orderWebServiceImpl.find(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Find", orderWebServiceImpl.find(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNull("Find", orderWebServiceImpl.find(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testMergedOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNull("Merge", orderWebServiceImpl.merge(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeOrderGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOrderService.merge(any(), any())).thenThrow(new RuntimeException());
    orderWebServiceImpl.merge(mockCredentials, spyOrder);
  }

  @Test
  public void testMergeOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Merge", orderWebServiceImpl.merge(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Merge", orderWebServiceImpl.merge(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Merge", orderWebServiceImpl.merge(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
  }


  @Test
  public void testMergeOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Merge", orderWebServiceImpl.merge(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNull("Merge", orderWebServiceImpl.merge(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistOrderGettingGNUOpenBusinessServiceException() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockSecuredGenericOrderService.merge(any(), any())).thenThrow(new RuntimeException());
    orderWebServiceImpl.persist(mockCredentials, spyOrder);
  }

  @Test
  public void testPersistOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Persist", orderWebServiceImpl.persist(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).persist(any(), any());
  }

  @Test
  public void testPersistOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Persist", orderWebServiceImpl.persist(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).persist(any(), any());
  }

  @Test
  public void testPersistOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Persist", orderWebServiceImpl.persist(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).persist(any(), any());
  }


  @Test
  public void testPersistOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Persist", orderWebServiceImpl.persist(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOrderService, never()).persist(any(), any());
  }

  @Test
  public void testPersistOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("Persist", orderWebServiceImpl.persist(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshOrderGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOrderService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    orderWebServiceImpl.refresh(mockCredentials, spyOrder);
  }

  @Test
  public void testRefreshOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    assertNull("Refresh", orderWebServiceImpl.refresh(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Refresh", orderWebServiceImpl.refresh(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Refresh", orderWebServiceImpl.refresh(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Refresh", orderWebServiceImpl.refresh(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Refresh", orderWebServiceImpl.refresh(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNull("Refresh", orderWebServiceImpl.refresh(mockCredentials, spyOrder));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRemoveOrderGettingGNUOpenBusinessServiceException() {
    when(mockContract.isDetached()).thenThrow(new RuntimeException());
    orderWebServiceImpl.remove(mockCredentials, spyOrder);
  }

  @Test
  public void testRemoveOrderWithContractIsNull() {
    when(spyOrder.getContract()).thenReturn(null);
    orderWebServiceImpl.remove(mockCredentials, spyOrder);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, never()).delete(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOrderWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    orderWebServiceImpl.remove(mockCredentials, spyOrder);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOrderWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    orderWebServiceImpl.remove(mockCredentials, spyOrder);
    verify(mockSecuredGenericCustomerService, times(1)).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOrderWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    orderWebServiceImpl.remove(mockCredentials, spyOrder);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOrderWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    orderWebServiceImpl.remove(mockCredentials, spyOrder);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOrderWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    orderWebServiceImpl.remove(mockCredentials, spyOrder);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, never()).delete(any(), any());
    verify(mockSecuredGenericOrderService, times(1)).remove(any(), any());
  }

}
