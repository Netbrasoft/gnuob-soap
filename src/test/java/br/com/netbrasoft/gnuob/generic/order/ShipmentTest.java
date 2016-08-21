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

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.customer.Address;
import br.com.netbrasoft.gnuob.generic.order.Shipment;

public class ShipmentTest {

  private Shipment spyShipment;
  private Address mockAddress;

  @Before
  public void setUp() throws Exception {
    mockAddress = mock(Address.class);
    spyShipment = spy(Shipment.class);
    spyShipment.setAddress(mockAddress);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetAddress() {
    assertNotNull("Address", spyShipment.getAddress());
    verify(spyShipment, times(1)).getAddress();
  }

  @Test
  public void testGetShipmentType() {
    assertNotNull("ShipmentType", spyShipment.getShipmentType());
    verify(spyShipment, times(1)).getShipmentType();
  }

  @Test
  public void testShipmentIsDetached() {
    spyShipment.setId(1L);
    when(mockAddress.isDetached()).thenReturn(false);
    assertTrue("Detached", spyShipment.isDetached());
    verify(spyShipment, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
  }

  @Test
  public void testShipmentIsDetachedByAddress() {
    spyShipment.setId(0L);
    when(mockAddress.isDetached()).thenReturn(true);
    assertTrue("Detached", spyShipment.isDetached());
    verify(spyShipment, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
  }

  @Test
  public void testShipmentIsNotDetached() {
    spyShipment.setId(0L);
    when(mockAddress.isDetached()).thenReturn(false);
    assertFalse("Detached", spyShipment.isDetached());
    verify(spyShipment, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
  }

  @Test
  public void testShipmentIsNotDetachedByAddressIsNull() {
    spyShipment.setId(0L);
    spyShipment.setAddress(null);
    assertFalse("Detached", spyShipment.isDetached());
    verify(spyShipment, times(1)).isDetached();
    verify(mockAddress, never()).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyShipment.prePersist();
    verify(spyShipment, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyShipment.preUpdate();
    verify(spyShipment, times(1)).preUpdate();
  }

  @Test
  public void testSetAddress() {
    spyShipment.setAddress(null);
    assertNull("Address", spyShipment.getAddress());
    verify(spyShipment, times(1)).setAddress(null);
  }

  @Test
  public void testSetShipmentType() {
    spyShipment.setShipmentType("Folly words widow one downs few age every seven.");
    assertEquals("ShipmentType", "Folly words widow one downs few age every seven.", spyShipment.getShipmentType());
    verify(spyShipment, times(1)).setShipmentType(anyString());
  }

  @Test
  public void testSetShipmentTypeWithEmptyString() {
    spyShipment.setShipmentType(" ");
    assertEquals("ShipmentType", "NOT_SPECIFIED", spyShipment.getShipmentType());
    verify(spyShipment, times(1)).setShipmentType(anyString());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyShipment, SHORT_PREFIX_STYLE), spyShipment.toString());
  }
}
