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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.contract.ContractWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class ContractWebServiceImplTest {

  private IGenericTypeWebService<Contract> contractWebServiceImpl = new ContractWebServiceImpl<>();
  private MetaData mockCredentials;
  private Customer mockCustomer;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Contract> mockSecuredGenericContractService;
  private ISecuredGenericTypeService<Customer> mockSecuredGenericCustomerService;
  private Contract spyContract;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockPaging = mock(Paging.class);
    mockCustomer = mock(Customer.class);
    spyContract = spy(Contract.class);
    spyContract.setCustomer(mockCustomer);
    mockSecuredGenericContractService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericCustomerService = mock(ISecuredGenericTypeService.class);
    contractWebServiceImpl =
        new ContractWebServiceImpl<Contract>(mockSecuredGenericContractService, mockSecuredGenericCustomerService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountContractGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericContractService.count(any(), any(), anyVararg())).thenThrow(new RuntimeException());
    contractWebServiceImpl.count(mockCredentials, spyContract);
  }

  @Test
  public void testCountContractWithCustomerIsNull() {
    when(spyContract.getCustomer()).thenReturn(null);
    assertEquals("Count", 0, contractWebServiceImpl.count(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountContractWithDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(true);
    assertEquals("Count", 0, contractWebServiceImpl.count(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountContractWithNoDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(false);
    assertEquals("Count", 0, contractWebServiceImpl.count(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).count(any(), any(), anyVararg());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDeleteContractGettingGNUOpenBusinessServiceException() {
    when(mockCustomer.isDetached()).thenReturn(true);
    when(spyContract.getCustomer()).thenThrow(new RuntimeException());
    contractWebServiceImpl.remove(mockCredentials, spyContract);
  }

  @Test
  public void testDeleteContractWithCustomerIsNull() {
    when(spyContract.getCustomer()).thenReturn(null);
    contractWebServiceImpl.remove(mockCredentials, spyContract);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteContractWithDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(true);
    contractWebServiceImpl.remove(mockCredentials, spyContract);
    verify(mockSecuredGenericCustomerService, times(1)).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteContractWithNoDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(false);
    contractWebServiceImpl.remove(mockCredentials, spyContract);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).remove(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindContractGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericContractService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    contractWebServiceImpl.find(mockCredentials, spyContract);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindContractPagingGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericContractService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    contractWebServiceImpl.find(mockCredentials, spyContract, mockPaging, OrderByEnum.NONE);
  }

  @Test
  public void testFindContractPagingWithCustomerIsNull() {
    when(spyContract.getCustomer()).thenReturn(null);
    assertTrue("Find",
        contractWebServiceImpl.find(mockCredentials, spyContract, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindContractPagingWithDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(true);
    assertTrue("Find",
        contractWebServiceImpl.find(mockCredentials, spyContract, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindContractPagingWithNoDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(false);
    assertTrue("Find",
        contractWebServiceImpl.find(mockCredentials, spyContract, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindContractWithCustomerIsNull() {
    when(spyContract.getCustomer()).thenReturn(null);
    assertNull("Find", contractWebServiceImpl.find(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindContractWithDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Find", contractWebServiceImpl.find(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindContractWithNoDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Find", contractWebServiceImpl.find(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).find(any(), any(), anyLong());
  }


  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeContractGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericContractService.merge(any(), any())).thenThrow(new RuntimeException());
    contractWebServiceImpl.merge(mockCredentials, spyContract);
  }

  @Test
  public void testMergeContractWithCustomerIsNull() {
    when(spyContract.getCustomer()).thenReturn(null);
    assertNull("Merge", contractWebServiceImpl.merge(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeContractWithDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Merge", contractWebServiceImpl.merge(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeContractWithNoDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Merge", contractWebServiceImpl.merge(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistContractGettingGNUOpenBusinessServiceException() {
    when(mockCustomer.isDetached()).thenReturn(true);
    when(mockSecuredGenericContractService.merge(any(), any())).thenThrow(new RuntimeException());
    contractWebServiceImpl.persist(mockCredentials, spyContract);
  }

  @Test
  public void testPersistContractWithCustomerIsNull() {
    when(spyContract.getCustomer()).thenReturn(null);
    assertNotNull("Persist", contractWebServiceImpl.persist(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistContractWithDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Persist", contractWebServiceImpl.persist(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistContractWithNoDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNotNull("Persist", contractWebServiceImpl.persist(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshContractGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericContractService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    contractWebServiceImpl.refresh(mockCredentials, spyContract);
  }

  @Test
  public void testRefreshContractWithCustomerIsNull() {
    when(spyContract.getCustomer()).thenReturn(null);
    assertNull("Refresh", contractWebServiceImpl.refresh(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshContractWithDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Refresh", contractWebServiceImpl.refresh(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshContractWithNoDetachedCustomer() {
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Refresh", contractWebServiceImpl.refresh(mockCredentials, spyContract));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).refresh(any(), any(), anyLong());
  }
}
