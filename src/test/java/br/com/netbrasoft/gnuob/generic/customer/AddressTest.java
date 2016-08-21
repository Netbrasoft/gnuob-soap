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

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.customer.Address;

public class AddressTest {

  private Address spyAddress;

  @Before
  public void setUp() throws Exception {
    spyAddress = spy(Address.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetCityName() {
    assertNull("CityName", spyAddress.getCityName());
    verify(spyAddress, times(1)).getCityName();
  }

  @Test
  public void testGetComplement() {
    assertNull("Complement", spyAddress.getComplement());
    verify(spyAddress, times(1)).getComplement();
  }

  @Test
  public void testGetCountry() {
    assertNull("Country", spyAddress.getCountry());
    verify(spyAddress, times(1)).getCountry();
  }

  @Test
  public void testGetCountryName() {
    assertNull("CountryName", spyAddress.getCountryName());
    verify(spyAddress, times(1)).getCountryName();
  }

  @Test
  public void testGetDistrict() {
    assertNull("District", spyAddress.getDistrict());
    verify(spyAddress, times(1)).getDistrict();
  }

  @Test
  public void testGetInternationalStateAndCity() {
    assertNull("InternationalStateAndCity", spyAddress.getInternationalStateAndCity());
    verify(spyAddress, times(1)).getInternationalStateAndCity();
  }

  @Test
  public void testGetInternationalStreet() {
    assertNull("InternationalStreet", spyAddress.getInternationalStreet());
    verify(spyAddress, times(1)).getInternationalStreet();
  }

  @Test
  public void testGetNumber() {
    assertNull("Number", spyAddress.getNumber());
    verify(spyAddress, times(1)).getNumber();
  }

  @Test
  public void testGetPhone() {
    assertNull("Phone", spyAddress.getPhone());
    verify(spyAddress, times(1)).getPhone();
  }

  @Test
  public void testGetPostalCode() {
    assertNull("PostalCode", spyAddress.getPostalCode());
    verify(spyAddress, times(1)).getPostalCode();
  }

  @Test
  public void testGetStateOrProvince() {
    assertNull("StateOrProvince", spyAddress.getStateOrProvince());
    verify(spyAddress, times(1)).getStateOrProvince();
  }

  @Test
  public void testGetStreet1() {
    assertNull("Street1", spyAddress.getStreet1());
    verify(spyAddress, times(1)).getStreet1();
  }

  @Test
  public void testGetStreet2() {
    assertNull("Street2", spyAddress.getStreet2());
    verify(spyAddress, times(1)).getStreet2();
  }

  @Test
  public void testAddressIsDetached() {
    spyAddress.setId(1L);
    assertTrue("Detached", spyAddress.isDetached());
    verify(spyAddress, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyAddress.prePersist();
    verify(spyAddress, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyAddress.preUpdate();
    verify(spyAddress, times(1)).preUpdate();
  }

  @Test
  public void testSetCityName() {
    spyAddress.setCityName("Folly words widow one downs few age every seven.");
    assertEquals("CityName", "Folly words widow one downs few age every seven.", spyAddress.getCityName());
    verify(spyAddress, times(1)).setCityName(anyString());
  }

  @Test
  public void testSetComplement() {
    spyAddress.setComplement("Folly words widow one downs few age every seven.");
    assertEquals("Complement", "Folly words widow one downs few age every seven.", spyAddress.getComplement());
    verify(spyAddress, times(1)).setComplement(anyString());
  }

  @Test
  public void testSetCountry() {
    spyAddress.setCountry("Folly words widow one downs few age every seven.");
    assertEquals("Country", "Folly words widow one downs few age every seven.", spyAddress.getCountry());
    verify(spyAddress, times(1)).setCountry(anyString());
  }

  @Test
  public void testSetCountryName() {
    spyAddress.setCountryName("Folly words widow one downs few age every seven.");
    assertEquals("CountryName", "Folly words widow one downs few age every seven.", spyAddress.getCountryName());
    verify(spyAddress, times(1)).setCountryName(anyString());
  }

  @Test
  public void testSetDistrict() {
    spyAddress.setDistrict("Folly words widow one downs few age every seven.");
    assertEquals("District", "Folly words widow one downs few age every seven.", spyAddress.getDistrict());
    verify(spyAddress, times(1)).setDistrict(anyString());
  }

  @Test
  public void testSetInternationalStateAndCity() {
    spyAddress.setInternationalStateAndCity("Folly words widow one downs few age every seven.");
    assertEquals("InternationalStateAndCity", "Folly words widow one downs few age every seven.",
        spyAddress.getInternationalStateAndCity());
    verify(spyAddress, times(1)).setInternationalStateAndCity(anyString());
  }

  @Test
  public void testSetInternationalStreet() {
    spyAddress.setInternationalStreet("Folly words widow one downs few age every seven.");
    assertEquals("InternationalStreet", "Folly words widow one downs few age every seven.",
        spyAddress.getInternationalStreet());
    verify(spyAddress, times(1)).setInternationalStreet(anyString());
  }

  @Test
  public void testSetNumber() {
    spyAddress.setNumber("Folly words widow one downs few age every seven.");
    assertEquals("Number", "Folly words widow one downs few age every seven.", spyAddress.getNumber());
    verify(spyAddress, times(1)).setNumber(anyString());
  }

  @Test
  public void testSetPhone() {
    spyAddress.setPhone("Folly words widow one downs few age every seven.");
    assertEquals("Phone", "Folly words widow one downs few age every seven.", spyAddress.getPhone());
    verify(spyAddress, times(1)).setPhone(anyString());
  }

  @Test
  public void testSetPostalCode() {
    spyAddress.setPostalCode("Folly words widow one downs few age every seven.");
    assertEquals("PostalCode", "Folly words widow one downs few age every seven.", spyAddress.getPostalCode());
    verify(spyAddress, times(1)).setPostalCode(anyString());
  }

  @Test
  public void testSetStateOrProvince() {
    spyAddress.setStateOrProvince("Folly words widow one downs few age every seven.");
    assertEquals("StateOrProvince", "Folly words widow one downs few age every seven.",
        spyAddress.getStateOrProvince());
    verify(spyAddress, times(1)).setStateOrProvince(anyString());
  }

  @Test
  public void testSetStreet1() {
    spyAddress.setStreet1("Folly words widow one downs few age every seven.");
    assertEquals("Street1", "Folly words widow one downs few age every seven.", spyAddress.getStreet1());
    verify(spyAddress, times(1)).setStreet1(anyString());
  }

  @Test
  public void testSetStreet2() {
    spyAddress.setStreet2("Folly words widow one downs few age every seven.");
    assertEquals("Street2", "Folly words widow one downs few age every seven.", spyAddress.getStreet2());
    verify(spyAddress, times(1)).setStreet2(anyString());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyAddress, SHORT_PREFIX_STYLE), spyAddress.toString());
  }
}
