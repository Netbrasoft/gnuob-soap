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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.customer.PostalCode;

public class PostalCodeTest {

  private PostalCode spyPostalCode;

  @Before
  public void setUp() throws Exception {
    spyPostalCode = spy(PostalCode.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetAccuracy() {
    assertNull("Accuracy", spyPostalCode.getAccuracy());
    verify(spyPostalCode, times(1)).getAccuracy();
  }

  @Test
  public void testGetAdminCode1() {
    assertNull("AdminCode1", spyPostalCode.getAdminCode1());
    verify(spyPostalCode, times(1)).getAdminCode1();
  }

  @Test
  public void testGetAdminCode2() {
    assertNull("AdminCode2", spyPostalCode.getAdminCode2());
    verify(spyPostalCode, times(1)).getAdminCode2();
  }

  @Test
  public void testGetAdminCode3() {
    assertNull("AdminCode3", spyPostalCode.getAdminCode3());
    verify(spyPostalCode, times(1)).getAdminCode3();
  }

  @Test
  public void testGetAdminName1() {
    assertNull("AdminName1", spyPostalCode.getAdminName1());
    verify(spyPostalCode, times(1)).getAdminName1();
  }

  @Test
  public void testGetAdminName2() {
    assertNull("AdminName2", spyPostalCode.getAdminName2());
    verify(spyPostalCode, times(1)).getAdminName2();
  }

  @Test
  public void testGetAdminName3() {
    assertNull("AdminName3", spyPostalCode.getAdminName3());
    verify(spyPostalCode, times(1)).getAdminName3();
  }

  @Test
  public void testGetCountryCode() {
    assertNull("CountryCode", spyPostalCode.getCountryCode());
    verify(spyPostalCode, times(1)).getCountryCode();
  }

  @Test
  public void testGetLatitude() {
    assertNull("Latitude", spyPostalCode.getLatitude());
    verify(spyPostalCode, times(1)).getLatitude();
  }

  @Test
  public void testGetLongitude() {
    assertNull("Longitude", spyPostalCode.getLongitude());
    verify(spyPostalCode, times(1)).getLongitude();
  }

  @Test
  public void testGetPlaceName() {
    assertNull("PlaceName", spyPostalCode.getPlaceName());
    verify(spyPostalCode, times(1)).getPlaceName();
  }

  @Test
  public void testGetPostalCode() {
    assertNull("PostalCode", spyPostalCode.getPostalCode());
    verify(spyPostalCode, times(1)).getPostalCode();
  }

  @Test
  public void testPostalCodeIsDetached() {
    spyPostalCode.setId(1L);
    assertTrue("Contract", spyPostalCode.isDetached());
    verify(spyPostalCode, times(1)).isDetached();
  }

  @Test
  public void testPostalCodeIsNotDetached() {
    spyPostalCode.setId(0L);
    assertFalse("Contract", spyPostalCode.isDetached());
    verify(spyPostalCode, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyPostalCode.prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyPostalCode.preUpdate();
  }

  @Test
  public void testSetAccuracy() {
    spyPostalCode.setAccuracy("Folly words widow one downs few age every seven.");
    assertEquals("Accuracy", "Folly words widow one downs few age every seven.", spyPostalCode.getAccuracy());
    verify(spyPostalCode, times(1)).setAccuracy(anyString());
  }

  @Test
  public void testSetAdminCode1() {
    spyPostalCode.setAdminCode1("Folly words widow one downs few age every seven.");
    assertEquals("AdminCode1", "Folly words widow one downs few age every seven.", spyPostalCode.getAdminCode1());
    verify(spyPostalCode, times(1)).setAdminCode1(anyString());
  }

  @Test
  public void testSetAdminCode2() {
    spyPostalCode.setAdminCode2("Folly words widow one downs few age every seven.");
    assertEquals("AdminCode2", "Folly words widow one downs few age every seven.", spyPostalCode.getAdminCode2());
    verify(spyPostalCode, times(1)).setAdminCode2(anyString());
  }

  @Test
  public void testSetAdminCode3() {
    spyPostalCode.setAdminCode3("Folly words widow one downs few age every seven.");
    assertEquals("AdminCode3", "Folly words widow one downs few age every seven.", spyPostalCode.getAdminCode3());
    verify(spyPostalCode, times(1)).setAdminCode3(anyString());
  }

  @Test
  public void testSetAdminName1() {
    spyPostalCode.setAdminName1("Folly words widow one downs few age every seven.");
    assertEquals("AdminName1", "Folly words widow one downs few age every seven.", spyPostalCode.getAdminName1());
    verify(spyPostalCode, times(1)).setAdminName1(anyString());
  }

  @Test
  public void testSetAdminName2() {
    spyPostalCode.setAdminName2("Folly words widow one downs few age every seven.");
    assertEquals("AdminName2", "Folly words widow one downs few age every seven.", spyPostalCode.getAdminName2());
    verify(spyPostalCode, times(1)).setAdminName2(anyString());
  }

  @Test
  public void testSetAdminName3() {
    spyPostalCode.setAdminName3("Folly words widow one downs few age every seven.");
    assertEquals("AdminName3", "Folly words widow one downs few age every seven.", spyPostalCode.getAdminName3());
    verify(spyPostalCode, times(1)).setAdminName3(anyString());
  }

  @Test
  public void testSetCountryCode() {
    spyPostalCode.setCountryCode("Folly words widow one downs few age every seven.");
    assertEquals("CountryCode", "Folly words widow one downs few age every seven.", spyPostalCode.getCountryCode());
    verify(spyPostalCode, times(1)).setCountryCode(anyString());
  }

  @Test
  public void testSetLatitude() {
    spyPostalCode.setLatitude(BigDecimal.ZERO);
    assertEquals("Latitude", BigDecimal.ZERO, spyPostalCode.getLatitude());
    verify(spyPostalCode, times(1)).setLatitude(any());
  }

  @Test
  public void testSetLongitude() {
    spyPostalCode.setLongitude(BigDecimal.ZERO);
    assertEquals("Longitude", BigDecimal.ZERO, spyPostalCode.getLongitude());
    verify(spyPostalCode, times(1)).setLongitude(any());
  }

  @Test
  public void testSetPlaceName() {
    spyPostalCode.setPlaceName("Folly words widow one downs few age every seven.");
    assertEquals("PlaceName", "Folly words widow one downs few age every seven.", spyPostalCode.getPlaceName());
    verify(spyPostalCode, times(1)).setPlaceName(anyString());
  }

  @Test
  public void testSetPostalCode() {
    spyPostalCode.setPostalCode("Folly words widow one downs few age every seven.");
    assertEquals("PostalCode", "Folly words widow one downs few age every seven.", spyPostalCode.getPostalCode());
    verify(spyPostalCode, times(1)).setPostalCode(anyString());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyPostalCode, SHORT_PREFIX_STYLE), spyPostalCode.toString());
  }
}
