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

package br.com.netbrasoft.gnuob.generic.offer;

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
import br.com.netbrasoft.gnuob.generic.offer.Offer;
import br.com.netbrasoft.gnuob.generic.offer.OfferRecord;
import br.com.netbrasoft.gnuob.generic.offer.OfferWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class OfferWebServiceImplTest {

  private Contract mockContract;
  private MetaData mockCredentials;
  private Customer mockCustomer;
  private OfferRecord mockOfferRecord;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Contract> mockSecuredGenericContractService;
  private ISecuredGenericTypeService<Customer> mockSecuredGenericCustomerService;
  private ISecuredGenericTypeService<Offer> mockSecuredGenericOfferService;
  private IGenericTypeWebService<Offer> offerWebServiceImpl = new OfferWebServiceImpl<>();
  private Offer spyOffer;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockPaging = mock(Paging.class);
    mockCustomer = mock(Customer.class);
    mockContract = mock(Contract.class);
    mockOfferRecord = mock(OfferRecord.class);
    spyOffer = spy(Offer.class);
    spyOffer.setContract(mockContract);
    spyOffer.setRecords(newSet(mockOfferRecord));
    mockSecuredGenericCustomerService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericContractService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericOfferService = mock(ISecuredGenericTypeService.class);
    offerWebServiceImpl = new OfferWebServiceImpl<>(mockSecuredGenericOfferService, mockSecuredGenericContractService,
        mockSecuredGenericCustomerService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountOfferGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOfferService.count(any(), any(), anyVararg())).thenThrow(new RuntimeException());
    offerWebServiceImpl.count(mockCredentials, spyOffer);
  }

  @Test
  public void testCountOfferWithContractIsNull() {
    when(spyOffer.getContract()).thenReturn(null);
    assertEquals("Count", 0, offerWebServiceImpl.count(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOfferWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertEquals("Count", 0, offerWebServiceImpl.count(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOfferWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertEquals("Count", 0, offerWebServiceImpl.count(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOfferWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertEquals("Count", 0, offerWebServiceImpl.count(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOfferWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertEquals("Count", 0, offerWebServiceImpl.count(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountOfferWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertEquals("Count", 0, offerWebServiceImpl.count(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).count(any(), any(), anyVararg());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindOfferGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOfferService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    offerWebServiceImpl.find(mockCredentials, spyOffer);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindOfferPagingGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOfferService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    offerWebServiceImpl.find(mockCredentials, spyOffer, mockPaging, OrderByEnum.NONE);
  }

  @Test
  public void testFindOfferPagingWithContractIsNull() {
    when(spyOffer.getContract()).thenReturn(null);
    assertTrue("Find", offerWebServiceImpl.find(mockCredentials, spyOffer, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOfferPagingWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertTrue("Find", offerWebServiceImpl.find(mockCredentials, spyOffer, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOfferPagingWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertTrue("Find", offerWebServiceImpl.find(mockCredentials, spyOffer, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOfferPagingWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertTrue("Find", offerWebServiceImpl.find(mockCredentials, spyOffer, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOfferPagingWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertTrue("Find", offerWebServiceImpl.find(mockCredentials, spyOffer, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }


  @Test
  public void testFindOfferPagingWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertTrue("Find", offerWebServiceImpl.find(mockCredentials, spyOffer, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindOfferWithContractIsNull() {
    when(spyOffer.getContract()).thenReturn(null);
    assertNull("Find", offerWebServiceImpl.find(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOfferWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Find", offerWebServiceImpl.find(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOfferWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Find", offerWebServiceImpl.find(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOfferWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Find", offerWebServiceImpl.find(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOfferWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Find", offerWebServiceImpl.find(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindOfferWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNull("Find", offerWebServiceImpl.find(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testMergedOfferWithContractIsNull() {
    when(spyOffer.getContract()).thenReturn(null);
    assertNull("Merge", offerWebServiceImpl.merge(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeOfferGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOfferService.merge(any(), any())).thenThrow(new RuntimeException());
    offerWebServiceImpl.merge(mockCredentials, spyOffer);
  }

  @Test
  public void testMergeOfferWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Merge", offerWebServiceImpl.merge(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeOfferWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Merge", offerWebServiceImpl.merge(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeOfferWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Merge", offerWebServiceImpl.merge(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).merge(any(), any());
  }


  @Test
  public void testMergeOfferWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Merge", offerWebServiceImpl.merge(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeOfferWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNull("Merge", offerWebServiceImpl.merge(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistOfferGettingGNUOpenBusinessServiceException() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockSecuredGenericOfferService.merge(any(), any())).thenThrow(new RuntimeException());
    offerWebServiceImpl.persist(mockCredentials, spyOffer);
  }

  @Test
  public void testPersistOfferWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Persist", offerWebServiceImpl.persist(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, never()).persist(any(), any());
  }

  @Test
  public void testPersistOfferWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Persist", offerWebServiceImpl.persist(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, times(1)).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, never()).persist(any(), any());
  }

  @Test
  public void testPersistOfferWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Persist", offerWebServiceImpl.persist(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, never()).persist(any(), any());
  }


  @Test
  public void testPersistOfferWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Persist", offerWebServiceImpl.persist(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, times(1)).update(any(), any());
    verify(mockSecuredGenericCustomerService, times(1)).create(any(), any());
    verify(mockSecuredGenericContractService, never()).create(any(), any());
    verify(mockSecuredGenericOfferService, never()).persist(any(), any());
  }

  @Test
  public void testPersistOfferWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNotNull("Persist", offerWebServiceImpl.persist(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).update(any(), any());
    verify(mockSecuredGenericContractService, never()).update(any(), any());
    verify(mockSecuredGenericCustomerService, never()).create(any(), any());
    verify(mockSecuredGenericContractService, times(1)).create(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshOfferGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericOfferService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    offerWebServiceImpl.refresh(mockCredentials, spyOffer);
  }

  @Test
  public void testRefreshOfferWithContractIsNull() {
    when(spyOffer.getContract()).thenReturn(null);
    assertNull("Refresh", offerWebServiceImpl.refresh(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOfferWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    assertNull("Refresh", offerWebServiceImpl.refresh(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOfferWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertNull("Refresh", offerWebServiceImpl.refresh(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, times(1)).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOfferWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    assertNull("Refresh", offerWebServiceImpl.refresh(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOfferWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertNull("Refresh", offerWebServiceImpl.refresh(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, times(1)).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshOfferWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    assertNull("Refresh", offerWebServiceImpl.refresh(mockCredentials, spyOffer));
    verify(mockSecuredGenericCustomerService, never()).read(any(), any());
    verify(mockSecuredGenericContractService, never()).read(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRemoveOfferGettingGNUOpenBusinessServiceException() {
    when(mockContract.isDetached()).thenThrow(new RuntimeException());
    offerWebServiceImpl.remove(mockCredentials, spyOffer);
  }

  @Test
  public void testRemoveOfferWithContractIsNull() {
    when(spyOffer.getContract()).thenReturn(null);
    offerWebServiceImpl.remove(mockCredentials, spyOffer);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, never()).delete(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOfferWithDetachedContract() {
    when(mockContract.isDetached()).thenReturn(true);
    offerWebServiceImpl.remove(mockCredentials, spyOffer);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOfferWithDetachedContractAndCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(true);
    offerWebServiceImpl.remove(mockCredentials, spyOffer);
    verify(mockSecuredGenericCustomerService, times(1)).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOfferWithDetachedContractAndCustomerInNull() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(null);
    offerWebServiceImpl.remove(mockCredentials, spyOffer);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOfferWithDetachedContractAndNoDetachedCustomer() {
    when(mockContract.isDetached()).thenReturn(true);
    when(mockContract.getCustomer()).thenReturn(mockCustomer);
    when(mockCustomer.isDetached()).thenReturn(false);
    offerWebServiceImpl.remove(mockCredentials, spyOffer);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, times(1)).delete(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveOfferWithNoDetachedContract() {
    when(mockContract.isDetached()).thenReturn(false);
    offerWebServiceImpl.remove(mockCredentials, spyOffer);
    verify(mockSecuredGenericCustomerService, never()).delete(any(), any());
    verify(mockSecuredGenericContractService, never()).delete(any(), any());
    verify(mockSecuredGenericOfferService, times(1)).remove(any(), any());
  }

}
