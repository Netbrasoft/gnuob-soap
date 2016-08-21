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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.offer.Offer;
import br.com.netbrasoft.gnuob.generic.order.Order;

public class ContractTest {

  private Customer mockCustomer;
  private Offer mockOffer;
  private Order mockOrder;
  private Contract spyContract;

  @Before
  public void setUp() throws Exception {
    mockCustomer = mock(Customer.class);
    mockOffer = mock(Offer.class);
    mockOrder = mock(Order.class);
    spyContract = spy(Contract.class);
    spyContract.setCustomer(mockCustomer);
    spyContract.setOffers(newSet(mockOffer));
    spyContract.setOrders(newSet(mockOrder));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyContract.accept(new ContextVisitorImpl()));
    verify(spyContract, times(1)).accept(any());
  }

  @Test
  public void testContractIsDetached() {
    spyContract.setId(1L);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertTrue("Detached", spyContract.isDetached());
    verify(spyContract, times(1)).isDetached();
  }

  @Test
  public void testContractIsDetachedByCustomer() {
    spyContract.setId(0L);
    when(mockCustomer.isDetached()).thenReturn(true);
    assertTrue("Detached", spyContract.isDetached());
    verify(spyContract, times(1)).isDetached();
    verify(mockCustomer, times(1)).isDetached();
  }

  @Test
  public void testContractIsNotDetached() {
    spyContract.setId(0L);
    when(mockCustomer.isDetached()).thenReturn(false);
    assertFalse("Detached", spyContract.isDetached());
    verify(spyContract, times(1)).isDetached();
    verify(mockCustomer, times(1)).isDetached();
  }

  @Test
  public void testContractIsNotDetachedWithCustomerIsNull() {
    spyContract.setId(0L);
    spyContract.setCustomer(null);
    assertFalse("Detached", spyContract.isDetached());
    verify(spyContract, times(1)).isDetached();
  }

  @Test
  public void testGetContractId() {
    assertNotNull("ContractId", spyContract.getContractId());
    verify(spyContract, times(1)).getContractId();
  }

  @Test
  public void testGetCustomer() {
    assertNotNull("Customer", spyContract.getCustomer());
    verify(spyContract, times(1)).getCustomer();
  }

  @Test
  public void testGetOffers() {
    assertNotNull("Offers", spyContract.getOffers());
    verify(spyContract, times(1)).getOffers();
  }

  @Test
  public void testGetOrders() {
    assertNotNull("Orders", spyContract.getOrders());
    verify(spyContract, times(1)).getOrders();
  }

  @Test
  public void testPrePersist() {
    spyContract.prePersist();
    verify(spyContract, times(1)).getContractId();
  }

  @Test
  public void testPreUpdate() {
    spyContract.preUpdate();
    verify(spyContract, times(1)).prePersist();
  }

  @Test
  public void testSetContractIdWithEmptyString() {
    spyContract.setContractId(" ");
    assertNotEquals("ContractId", "", spyContract.getContractId());
    verify(spyContract, times(1)).setContractId(anyString());
  }

  @Test
  public void testSetContractId() {
    spyContract.setContractId("Folly words widow one downs few age every seven.");
    assertEquals("ContractId", "Folly words widow one downs few age every seven.", spyContract.getContractId());
    verify(spyContract, times(1)).setContractId(anyString());
  }

  @Test
  public void testSetCustomer() {
    spyContract.setCustomer(null);
    assertNull("Customer", spyContract.getCustomer());
    verify(spyContract, times(1)).setCustomer(null);
  }

  @Test
  public void testSetOffers() {
    spyContract.setOffers(null);
    assertNull("Offers", spyContract.getOffers());
    verify(spyContract, times(1)).setOffers(null);
  }

  @Test
  public void testSetOrders() {
    spyContract.setOrders(null);
    assertNull("Orders", spyContract.getOrders());
    verify(spyContract, times(1)).setOrders(null);
  }

  @Test
  public void testToString() {
    assertEquals(new ReflectionToStringBuilder(spyContract, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP)
        .toString(), spyContract.toString());
  }
}
