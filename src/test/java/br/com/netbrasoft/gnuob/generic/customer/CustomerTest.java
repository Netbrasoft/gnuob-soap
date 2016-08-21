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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GROUP;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;

public class CustomerTest {

  private Customer spyCustomer;
  private Address mockAddress;

  @Before
  public void setUp() throws Exception {
    mockAddress = mock(Address.class);
    spyCustomer = spy(Customer.class);
    spyCustomer.setAddress(mockAddress);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyCustomer.accept(new ContextVisitorImpl()));
    verify(spyCustomer, times(1)).accept(any());
  }

  @Test
  public void testGetAddress() {
    assertNotNull("Address", spyCustomer.getAddress());
    verify(spyCustomer, times(1)).getAddress();
  }

  @Test
  public void testGetBuyerEmail() {
    assertNull("BuyerEmail", spyCustomer.getBuyerEmail());
    verify(spyCustomer, times(1)).getBuyerEmail();
  }

  @Test
  public void testGetBuyerMarketingEmail() {
    assertNull("BuyerMarketingEmail", spyCustomer.getBuyerMarketingEmail());
    verify(spyCustomer, times(1)).getBuyerMarketingEmail();
  }

  @Test
  public void testGetContactPhone() {
    assertNull("ContactPhone", spyCustomer.getContactPhone());
    verify(spyCustomer, times(1)).getContactPhone();
  }

  @Test
  public void testGetDateOfBirth() {
    assertNull("DateOfBirth", spyCustomer.getDateOfBirth());
    verify(spyCustomer, times(1)).getDateOfBirth();
  }

  @Test
  public void testGetFirstName() {
    assertNull("FirstName", spyCustomer.getFirstName());
    verify(spyCustomer, times(1)).getFirstName();
  }

  @Test
  public void testGetFriendlyNameWithMiddleNameIsNull() {
    when(spyCustomer.getMiddleName()).thenReturn(null);
    when(spyCustomer.getFirstName()).thenReturn("First");
    when(spyCustomer.getLastName()).thenReturn("Last");
    assertEquals("FriendlyName", "First Last", spyCustomer.getFriendlyName());
    verify(spyCustomer, times(1)).getFriendlyName();
  }

  @Test
  public void testGetFriendlyNameWithMiddleNameIsEmpty() {
    when(spyCustomer.getMiddleName()).thenReturn(" ");
    when(spyCustomer.getFirstName()).thenReturn("First");
    when(spyCustomer.getLastName()).thenReturn("Last");
    assertEquals("FriendlyName", "First Last", spyCustomer.getFriendlyName());
    verify(spyCustomer, times(1)).getFriendlyName();
  }

  @Test
  public void testGetFriendlyNameWithMiddleNameIsNotEmpty() {
    when(spyCustomer.getMiddleName()).thenReturn("Middle");
    when(spyCustomer.getFirstName()).thenReturn("First");
    when(spyCustomer.getLastName()).thenReturn("Last");
    assertEquals("FriendlyName", "First Middle Last", spyCustomer.getFriendlyName());
    verify(spyCustomer, times(1)).getFriendlyName();
  }

  @Test
  public void testGetLastName() {
    assertNull("LastName", spyCustomer.getLastName());
    verify(spyCustomer, times(1)).getLastName();
  }

  @Test
  public void testGetMiddleName() {
    assertNull("MiddleName", spyCustomer.getMiddleName());
    verify(spyCustomer, times(1)).getMiddleName();
  }

  @Test
  public void testGetPayer() {
    assertNull("Payer", spyCustomer.getPayer());
    verify(spyCustomer, times(1)).getPayer();
  }

  @Test
  public void testGetPayerBusiness() {
    assertNull("PayerBusiness", spyCustomer.getPayerBusiness());
    verify(spyCustomer, times(1)).getPayerBusiness();
  }

  @Test
  public void testGetPayerId() {
    assertNull("PayerId", spyCustomer.getPayerId());
    verify(spyCustomer, times(1)).getPayerId();
  }

  @Test
  public void testGetPayerStatus() {
    assertNull("PayerStatus", spyCustomer.getPayerStatus());
    verify(spyCustomer, times(1)).getPayerStatus();
  }

  @Test
  public void testGetSalutation() {
    assertNull("Salutation", spyCustomer.getSalutation());
    verify(spyCustomer, times(1)).getSalutation();
  }

  @Test
  public void testGetSuffix() {
    assertNull("Suffix", spyCustomer.getSuffix());
    verify(spyCustomer, times(1)).getSuffix();
  }

  @Test
  public void testGetTaxId() {
    assertNull("TaxId", spyCustomer.getTaxId());
    verify(spyCustomer, times(1)).getTaxId();
  }

  @Test
  public void testGetTaxIdType() {
    assertNull("TaxIdType", spyCustomer.getTaxIdType());
    verify(spyCustomer, times(1)).getTaxIdType();
  }

  @Test
  public void testCustomerIsDetached() {
    spyCustomer.setId(1L);
    when(mockAddress.isDetached()).thenReturn(false);
    assertTrue("Contract", spyCustomer.isDetached());
    verify(spyCustomer, times(1)).isDetached();
  }

  @Test
  public void testCustomerIsDetachedByAddress() {
    spyCustomer.setId(0L);
    when(mockAddress.isDetached()).thenReturn(true);
    assertTrue("Contract", spyCustomer.isDetached());
    verify(spyCustomer, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
  }

  @Test
  public void testCustomerIsNotDetached() {
    spyCustomer.setId(0L);
    when(mockAddress.isDetached()).thenReturn(false);
    assertFalse("Contract", spyCustomer.isDetached());
    verify(spyCustomer, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
  }

  @Test
  public void testCustomerIsNotDetachedWithAddressIsNull() {
    spyCustomer.setId(0L);
    spyCustomer.setAddress(null);
    assertFalse("Contract", spyCustomer.isDetached());
    verify(spyCustomer, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyCustomer.prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyCustomer.preUpdate();
  }

  @Test
  public void testSetAddress() {
    spyCustomer.setAddress(null);
    assertNull("Address", spyCustomer.getAddress());
    verify(spyCustomer, times(1)).setAddress(null);
  }

  @Test
  public void testSetBuyerEmail() {
    spyCustomer.setBuyerEmail("Folly words widow one downs few age every seven.");
    assertEquals("BuyerEmail", "Folly words widow one downs few age every seven.", spyCustomer.getBuyerEmail());
    verify(spyCustomer, times(1)).setBuyerEmail(anyString());
  }

  @Test
  public void testSetBuyerMarketingEmail() {
    spyCustomer.setBuyerMarketingEmail("Folly words widow one downs few age every seven.");
    assertEquals("BuyerMarketingEmail", "Folly words widow one downs few age every seven.",
        spyCustomer.getBuyerMarketingEmail());
    verify(spyCustomer, times(1)).setBuyerMarketingEmail(anyString());
  }

  @Test
  public void testSetContactPhone() {
    spyCustomer.setContactPhone("Folly words widow one downs few age every seven.");
    assertEquals("ContactPhone", "Folly words widow one downs few age every seven.", spyCustomer.getContactPhone());
    verify(spyCustomer, times(1)).setContactPhone(anyString());
  }

  @Test
  public void testSetDateOfBirth() {
    spyCustomer.setDateOfBirth(new Date());
    assertNotNull("DateOfBirth", spyCustomer.getDateOfBirth());
    verify(spyCustomer, times(1)).setDateOfBirth(any());
  }

  @Test
  public void testSetFirstName() {
    spyCustomer.setFirstName("Folly words widow one downs few age every seven.");
    assertEquals("FirstName", "Folly words widow one downs few age every seven.", spyCustomer.getFirstName());
    verify(spyCustomer, times(1)).setFirstName(anyString());
  }

  @Test
  public void testSetFriendlyName() {
    spyCustomer.setFriendlyName("Folly words widow one downs few age every seven.");
    assertEquals("FriendlyName", "Folly words widow one downs few age every seven.", spyCustomer.getFriendlyName());
    verify(spyCustomer, times(1)).setFriendlyName(anyString());
  }

  @Test
  public void testSetLastName() {
    spyCustomer.setLastName("Folly words widow one downs few age every seven.");
    assertEquals("LastName", "Folly words widow one downs few age every seven.", spyCustomer.getLastName());
    verify(spyCustomer, times(1)).setLastName(anyString());
  }

  @Test
  public void testSetMiddleName() {
    spyCustomer.setMiddleName("Folly words widow one downs few age every seven.");
    assertEquals("MiddleName", "Folly words widow one downs few age every seven.", spyCustomer.getMiddleName());
    verify(spyCustomer, times(1)).setMiddleName(anyString());
  }

  @Test
  public void testSetPayer() {
    spyCustomer.setPayer("Folly words widow one downs few age every seven.");
    assertEquals("Payer", "Folly words widow one downs few age every seven.", spyCustomer.getPayer());
    verify(spyCustomer, times(1)).setPayer(anyString());
  }

  @Test
  public void testSetPayerBusiness() {
    spyCustomer.setPayerBusiness("Folly words widow one downs few age every seven.");
    assertEquals("PayerBusiness", "Folly words widow one downs few age every seven.", spyCustomer.getPayerBusiness());
    verify(spyCustomer, times(1)).setPayerBusiness(anyString());
  }

  @Test
  public void testSetPayerId() {
    spyCustomer.setPayerId("Folly words widow one downs few age every seven.");
    assertEquals("PayerId", "Folly words widow one downs few age every seven.", spyCustomer.getPayerId());
    verify(spyCustomer, times(1)).setPayerId(anyString());
  }

  @Test
  public void testSetPayerStatus() {
    spyCustomer.setPayerStatus("Folly words widow one downs few age every seven.");
    assertEquals("PayerStatus", "Folly words widow one downs few age every seven.", spyCustomer.getPayerStatus());
    verify(spyCustomer, times(1)).setPayerStatus(anyString());
  }

  @Test
  public void testSetSalutation() {
    spyCustomer.setSalutation("Folly words widow one downs few age every seven.");
    assertEquals("Salutation", "Folly words widow one downs few age every seven.", spyCustomer.getSalutation());
    verify(spyCustomer, times(1)).setSalutation(anyString());
  }

  @Test
  public void testSetSuffix() {
    spyCustomer.setSuffix("Folly words widow one downs few age every seven.");
    assertEquals("Suffix", "Folly words widow one downs few age every seven.", spyCustomer.getSuffix());
    verify(spyCustomer, times(1)).setSuffix(anyString());
  }

  @Test
  public void testSetTaxId() {
    spyCustomer.setTaxId("Folly words widow one downs few age every seven.");
    assertEquals("TaxId", "Folly words widow one downs few age every seven.", spyCustomer.getTaxId());
    verify(spyCustomer, times(1)).setTaxId(anyString());
  }

  @Test
  public void testSetTaxIdType() {
    spyCustomer.setTaxIdType("Folly words widow one downs few age every seven.");
    assertEquals("TaxIdType", "Folly words widow one downs few age every seven.", spyCustomer.getTaxIdType());
    verify(spyCustomer, times(1)).setTaxIdType(anyString());
  }

  @Test
  public void testToString() {
    assertEquals(new ReflectionToStringBuilder(spyCustomer, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP)
        .toString(), spyCustomer.toString());
  }
}
