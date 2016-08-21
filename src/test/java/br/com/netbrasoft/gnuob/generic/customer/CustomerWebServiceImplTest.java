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

package br.com.netbrasoft.gnuob.generic.customer;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.customer.CustomerWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class CustomerWebServiceImplTest {

  private IGenericTypeWebService<Customer> customerWebServiceImpl = new CustomerWebServiceImpl<>();
  private MetaData mockCredentials;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Customer> mockSecuredGenericCustomerService;
  private Customer spyCustomer;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockPaging = mock(Paging.class);
    spyCustomer = spy(Customer.class);
    mockSecuredGenericCustomerService = mock(ISecuredGenericTypeService.class);
    customerWebServiceImpl = new CustomerWebServiceImpl<Customer>(mockSecuredGenericCustomerService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testCountCustomer() {
    assertEquals("Count", 0, customerWebServiceImpl.count(mockCredentials, spyCustomer));
    verify(mockSecuredGenericCustomerService, times(1)).count(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountCustomerGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCustomerService.count(any(), any())).thenThrow(new RuntimeException());
    customerWebServiceImpl.count(mockCredentials, spyCustomer);
  }

  @Test
  public void testDeleteCategoryWithNullCustomersAndSubCategories() {
    customerWebServiceImpl.remove(mockCredentials, spyCustomer);
    verify(mockSecuredGenericCustomerService, times(1)).remove(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDeleteCategoryGettingGNUOpenBusinessServiceException() {
    customerWebServiceImpl = new CustomerWebServiceImpl<>();
    customerWebServiceImpl.remove(mockCredentials, spyCustomer);
  }

  @Test
  public void testFindCustomer() {
    assertNull("Find", customerWebServiceImpl.find(mockCredentials, spyCustomer));
    verify(mockSecuredGenericCustomerService, times(1)).find(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindCustomerGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCustomerService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    customerWebServiceImpl.find(mockCredentials, spyCustomer);
  }

  @Test
  public void testFindCustomerPaging() {
    assertTrue("Find",
        customerWebServiceImpl.find(mockCredentials, spyCustomer, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindCustomerPagingGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCustomerService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    customerWebServiceImpl.find(mockCredentials, spyCustomer, mockPaging, OrderByEnum.NONE);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeCustomerGettingGNUOpenBusinessServiceException() {
    when(spyCustomer.isDetached()).thenReturn(true);
    when(mockSecuredGenericCustomerService.merge(any(), any())).thenThrow(new RuntimeException());
    customerWebServiceImpl.merge(mockCredentials, spyCustomer);
  }

  @Test
  public void testMergeWithNoDetachedCustomers() {
    when(spyCustomer.isDetached()).thenReturn(false);
    assertNull("Merge", customerWebServiceImpl.merge(mockCredentials, spyCustomer));
    verify(mockSecuredGenericCustomerService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistWithDetachedCustomers() {
    when(spyCustomer.isDetached()).thenReturn(true);
    assertNull("Persist", customerWebServiceImpl.persist(mockCredentials, spyCustomer));
    verify(mockSecuredGenericCustomerService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistCategoryGettingGNUOpenBusinessServiceException() {
    when(spyCustomer.isDetached()).thenReturn(true);
    when(mockSecuredGenericCustomerService.merge(any(), any())).thenThrow(new RuntimeException());
    customerWebServiceImpl.persist(mockCredentials, spyCustomer);
  }

  @Test
  public void testPersistWithNoDetachedCustomers() {
    when(spyCustomer.isDetached()).thenReturn(false);
    assertNotNull("Persist", customerWebServiceImpl.persist(mockCredentials, spyCustomer));
    verify(mockSecuredGenericCustomerService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshCategoryGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCustomerService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    customerWebServiceImpl.refresh(mockCredentials, spyCustomer);
  }

  @Test
  public void testRefreshCategoryWithDetachedCustomers() {
    assertNull("Refresh", customerWebServiceImpl.refresh(mockCredentials, spyCustomer));
    verify(mockSecuredGenericCustomerService, times(1)).refresh(any(), any(), anyLong());
  }
}
