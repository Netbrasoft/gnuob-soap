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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.customer.Address;
import br.com.netbrasoft.gnuob.generic.order.Invoice;
import br.com.netbrasoft.gnuob.generic.order.Payment;

public class InvoiceTest {

  private Address mockAddress;
  private Payment mockPayment;
  private Invoice spyInvoice;

  @Before
  public void setUp() throws Exception {
    mockAddress = mock(Address.class);
    mockPayment = mock(Payment.class);
    spyInvoice = spy(Invoice.class);
    spyInvoice.setAddress(mockAddress);
    spyInvoice.setPayments(newSet(mockPayment));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetAddress() {
    assertNotNull("Address", spyInvoice.getAddress());
    verify(spyInvoice, times(1)).getAddress();
  }

  @Test
  public void testGetInvoiceId() {
    assertNotNull("InvoiceId", spyInvoice.getInvoiceId());
    verify(spyInvoice, times(1)).getInvoiceId();
  }

  @Test
  public void testGetPayments() {
    assertNotNull("Payments", spyInvoice.getPayments());
    verify(spyInvoice, times(1)).getPayments();
  }

  @Test
  public void testInvoiceIsDetached() {
    spyInvoice.setId(1L);
    when(mockAddress.isDetached()).thenReturn(false);
    when(mockPayment.isDetached()).thenReturn(false);
    assertTrue("Order", spyInvoice.isDetached());
    verify(spyInvoice, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
    verify(mockPayment, times(1)).isDetached();
  }

  @Test
  public void testInvoiceIsDetachedByAddress() {
    spyInvoice.setId(0L);
    when(mockAddress.isDetached()).thenReturn(true);
    when(mockPayment.isDetached()).thenReturn(false);
    assertTrue("Order", spyInvoice.isDetached());
    verify(spyInvoice, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
    verify(mockPayment, times(1)).isDetached();
  }

  @Test
  public void testInvoiceIsDetachedByPayment() {
    spyInvoice.setId(0L);
    when(mockAddress.isDetached()).thenReturn(false);
    when(mockPayment.isDetached()).thenReturn(true);
    assertTrue("Order", spyInvoice.isDetached());
    verify(spyInvoice, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
    verify(mockPayment, times(1)).isDetached();
  }

  @Test
  public void testInvoiceIsNotDetached() {
    spyInvoice.setId(0L);
    when(mockAddress.isDetached()).thenReturn(false);
    when(mockPayment.isDetached()).thenReturn(false);
    assertFalse("Order", spyInvoice.isDetached());
    verify(spyInvoice, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
    verify(mockPayment, times(1)).isDetached();
  }

  @Test
  public void testInvoiceIsNotDetachedByAddressIsNull() {
    spyInvoice.setId(0L);
    spyInvoice.setAddress(null);
    when(mockPayment.isDetached()).thenReturn(false);
    assertFalse("Order", spyInvoice.isDetached());
    verify(spyInvoice, times(1)).isDetached();
    verify(mockAddress, never()).isDetached();
    verify(mockPayment, times(1)).isDetached();
  }

  @Test
  public void testInvoiceIsNotDetachedByPaymentIsNull() {
    spyInvoice.setId(0L);
    spyInvoice.setPayments(null);
    when(mockPayment.isDetached()).thenReturn(false);
    assertFalse("Order", spyInvoice.isDetached());
    verify(spyInvoice, times(1)).isDetached();
    verify(mockAddress, times(1)).isDetached();
    verify(mockPayment, never()).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyInvoice.prePersist();
    verify(spyInvoice, times(1)).prePersist();
    verify(spyInvoice, times(1)).getInvoiceId();
    verify(mockPayment, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    spyInvoice.preUpdate();
    verify(spyInvoice, times(1)).preUpdate();
    verify(mockPayment, times(1)).setPosition(anyInt());
  }

  @Test
  public void testSetAddress() {
    spyInvoice.setAddress(null);
    assertNull("Address", spyInvoice.getAddress());
    verify(spyInvoice, times(1)).setAddress(null);
  }

  @Test
  public void testSetInvoiceId() {
    spyInvoice.setInvoiceId("Folly words widow one downs few age every seven.");
    assertEquals("InvoiceId", "Folly words widow one downs few age every seven.", spyInvoice.getInvoiceId());
    verify(spyInvoice, times(1)).setInvoiceId(anyString());
  }

  @Test
  public void testSetInvoiceIdWithEmptyString() {
    spyInvoice.setInvoiceId(" ");
    assertNotEquals("InvoiceId", " ", spyInvoice.getInvoiceId());
    verify(spyInvoice, times(1)).setInvoiceId(anyString());
  }

  @Test
  public void testSetPayments() {
    spyInvoice.setPayments(null);
    assertNull("Payments", spyInvoice.getPayments());
    verify(spyInvoice, times(1)).setPayments(null);
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyInvoice, SHORT_PREFIX_STYLE), spyInvoice.toString());
  }
}
