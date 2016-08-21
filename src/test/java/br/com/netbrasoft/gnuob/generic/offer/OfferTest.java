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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;
import br.com.netbrasoft.gnuob.generic.contract.Contract;

public class OfferTest {

  private Contract mockContract;
  private OfferRecord mockOfferRecord;
  private Offer spyOffer;

  @Before
  public void setUp() throws Exception {
    mockContract = mock(Contract.class);
    mockOfferRecord = mock(OfferRecord.class);
    spyOffer = spy(Offer.class);
    spyOffer.setContract(mockContract);
    spyOffer.setRecords(newSet(mockOfferRecord));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyOffer.accept(new ContextVisitorImpl()));
    verify(spyOffer, times(1)).accept(any());
  }

  @Test
  public void testGetContract() {
    assertNotNull("Contract", spyOffer.getContract());
    verify(spyOffer, times(1)).getContract();
  }

  @Test
  public void testGetDiscountTotal() {
    when(mockOfferRecord.getDiscountTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("DiscountTotal", spyOffer.getDiscountTotal());
    verify(spyOffer, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetExtraAmount() {
    when(mockOfferRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ExtraAmount", spyOffer.getExtraAmount());
    verify(spyOffer, times(1)).getExtraAmount();
  }

  @Test
  public void testGetHandlingTotal() {
    assertNotNull("HandlingTotal", spyOffer.getHandlingTotal());
    verify(spyOffer, times(1)).getHandlingTotal();
  }

  @Test
  public void testGetInsuranceTotal() {
    assertNotNull("InsuranceTotal", spyOffer.getInsuranceTotal());
    verify(spyOffer, times(1)).getInsuranceTotal();
  }

  @Test
  public void testGetItemTotal() {
    when(mockOfferRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemTotal", spyOffer.getItemTotal());
    verify(spyOffer, times(1)).getItemTotal();
  }

  @Test
  public void testGetMaxTotal() {
    when(mockOfferRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOfferRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOfferRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("MaxTotal", spyOffer.getMaxTotal());
    verify(spyOffer, times(1)).getMaxTotal();
  }

  @Test
  public void testGetOfferDescription() {
    assertNull("OfferDescription", spyOffer.getOfferDescription());
    verify(spyOffer, times(1)).getOfferDescription();
  }

  @Test
  public void testGetOfferId() {
    assertNotNull("OfferId", spyOffer.getOfferId());
    verify(spyOffer, times(1)).getOfferId();
  }

  @Test
  public void testGetOfferTotal() {
    when(mockOfferRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOfferRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOfferRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("OfferTotal", spyOffer.getOfferTotal());
    verify(spyOffer, times(1)).getOfferTotal();
  }

  @Test
  public void testGetRecords() {
    assertNotNull("Records", spyOffer.getRecords());
    verify(spyOffer, times(1)).getRecords();
  }

  @Test
  public void testGetShippingDiscount() {
    assertNotNull("ShippingDiscount", spyOffer.getShippingDiscount());
    verify(spyOffer, times(1)).getShippingDiscount();
  }

  @Test
  public void testGetShippingTotal() {
    when(mockOfferRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingTotal", spyOffer.getShippingTotal());
    verify(spyOffer, times(1)).getShippingTotal();
  }

  @Test
  public void testGetTaxTotal() {
    when(mockOfferRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    assertNotNull("TaxTotal", spyOffer.getTaxTotal());
    verify(spyOffer, times(1)).getTaxTotal();
  }

  @Test
  public void testOfferIsDetached() {
    spyOffer.setId(1L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockOfferRecord.isDetached()).thenReturn(false);
    assertTrue("Offer", spyOffer.isDetached());
    verify(spyOffer, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockOfferRecord, times(1)).isDetached();
  }

  @Test
  public void testOfferIsDetachedByContract() {
    spyOffer.setId(0L);
    when(mockContract.isDetached()).thenReturn(true);
    when(mockOfferRecord.isDetached()).thenReturn(false);
    assertTrue("Offer", spyOffer.isDetached());
    verify(spyOffer, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockOfferRecord, times(1)).isDetached();
  }

  @Test
  public void testOfferIsDetachedByRecords() {
    spyOffer.setId(0L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockOfferRecord.isDetached()).thenReturn(true);
    assertTrue("Offer", spyOffer.isDetached());
    verify(spyOffer, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockOfferRecord, times(1)).isDetached();
  }

  @Test
  public void testOfferIsNotDetached() {
    spyOffer.setId(0L);
    when(mockContract.isDetached()).thenReturn(false);
    when(mockOfferRecord.isDetached()).thenReturn(false);
    assertFalse("Offer", spyOffer.isDetached());
    verify(spyOffer, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockOfferRecord, times(1)).isDetached();
  }

  @Test
  public void testOfferIsNotDetachedByRecordsIsNull() {
    spyOffer.setId(0L);
    spyOffer.setRecords(null);
    when(mockContract.isDetached()).thenReturn(false);
    assertFalse("Offer", spyOffer.isDetached());
    verify(spyOffer, times(1)).isDetached();
    verify(mockContract, times(1)).isDetached();
    verify(mockOfferRecord, never()).isDetached();
  }

  @Test
  public void testOfferIsNotDetachedWithContractIsNull() {
    spyOffer.setId(0L);
    spyOffer.setContract(null);
    when(mockOfferRecord.isDetached()).thenReturn(false);
    assertFalse("Offer", spyOffer.isDetached());
    verify(spyOffer, times(1)).isDetached();
    verify(mockContract, never()).isDetached();
    verify(mockOfferRecord, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    when(mockOfferRecord.getDiscountTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOfferRecord.getItemTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOfferRecord.getShippingTotal()).thenReturn(BigDecimal.ZERO);
    when(mockOfferRecord.getTaxTotal()).thenReturn(BigDecimal.ZERO);
    spyOffer.prePersist();
    verify(spyOffer, times(1)).getOfferId();
    verify(spyOffer, times(2)).getTaxTotal();
    verify(spyOffer, times(2)).getShippingTotal();
    verify(spyOffer, times(2)).getOfferTotal();
    verify(spyOffer, times(2)).getItemTotal();
    verify(spyOffer, times(1)).getMaxTotal();
    verify(spyOffer, times(1)).getDiscountTotal();
    verify(mockOfferRecord, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    spyOffer.preUpdate();
    verify(mockOfferRecord, times(1)).setPosition(anyInt());
  }

  @Test
  public void testSetContract() {
    spyOffer.setContract(null);
    assertNull("Contract", spyOffer.getContract());
    verify(spyOffer, times(1)).setContract(null);
  }

  @Test
  public void testSetDiscountTotal() {
    spyOffer.setDiscountTotal(BigDecimal.ZERO);
    assertEquals("DiscountTotal", BigDecimal.ZERO, spyOffer.getDiscountTotal());
    verify(spyOffer, times(1)).setDiscountTotal(any());
  }

  @Test
  public void testSetExtraAmount() {
    spyOffer.setExtraAmount(BigDecimal.ZERO);
    assertEquals("ExtraAmount", BigDecimal.ZERO, spyOffer.getExtraAmount());
    verify(spyOffer, times(1)).setExtraAmount(any());
  }

  @Test
  public void testSetHandlingTotal() {
    spyOffer.setHandlingTotal(BigDecimal.ZERO);
    assertEquals("HandlingTotal", BigDecimal.ZERO, spyOffer.getHandlingTotal());
    verify(spyOffer, times(1)).setHandlingTotal(any());
  }

  @Test
  public void testSetInsuranceTotal() {
    spyOffer.setInsuranceTotal(BigDecimal.ZERO);
    assertEquals("InsuranceTotal", BigDecimal.ZERO, spyOffer.getInsuranceTotal());
    verify(spyOffer, times(1)).setInsuranceTotal(any());
  }

  @Test
  public void testSetItemTotal() {
    spyOffer.setItemTotal(BigDecimal.ZERO);
    assertEquals("ItemTotal", BigDecimal.ZERO, spyOffer.getItemTotal());
    verify(spyOffer, times(1)).setItemTotal(any());
  }

  @Test
  public void testSetMaxTotal() {
    spyOffer.setMaxTotal(BigDecimal.ZERO);
    assertEquals("MaxTotal", BigDecimal.ZERO, spyOffer.getMaxTotal());
    verify(spyOffer, times(1)).setMaxTotal(any());
  }

  @Test
  public void testSetOfferDescription() {
    spyOffer.setOfferDescription("Folly words widow one downs few age every seven.");
    assertEquals("OfferDescription", "Folly words widow one downs few age every seven.",
        spyOffer.getOfferDescription());
    verify(spyOffer, times(1)).setOfferDescription(anyString());
  }

  @Test
  public void testSetOfferId() {
    spyOffer.setOfferId("Folly words widow one downs few age every seven.");
    assertEquals("OfferId", "Folly words widow one downs few age every seven.", spyOffer.getOfferId());
    verify(spyOffer, times(1)).setOfferId(anyString());
  }

  @Test
  public void testSetOfferIdWithEmptyString() {
    spyOffer.setOfferId("");
    assertNotEquals("OfferId", " ", spyOffer.getOfferId());
    verify(spyOffer, times(1)).setOfferId(anyString());
  }

  @Test
  public void testSetOfferTotal() {
    spyOffer.setOfferTotal(BigDecimal.ZERO);
    assertEquals("OfferTotal", BigDecimal.ZERO, spyOffer.getOfferTotal());
    verify(spyOffer, times(1)).setOfferTotal(any());
  }

  @Test
  public void testSetRecords() {
    spyOffer.setRecords(null);
    assertNull("Records", spyOffer.getRecords());
    verify(spyOffer, times(1)).setRecords(null);
  }

  @Test
  public void testSetShippingDiscount() {
    spyOffer.setShippingDiscount(BigDecimal.ZERO);
    assertEquals("ShippingDiscount", BigDecimal.ZERO, spyOffer.getShippingDiscount());
    verify(spyOffer, times(1)).setShippingDiscount(any());
  }

  @Test
  public void testSetShippingTotal() {
    spyOffer.setShippingTotal(BigDecimal.ZERO);
    assertEquals("ShippingTotal", BigDecimal.ZERO, spyOffer.getShippingTotal());
    verify(spyOffer, times(1)).setShippingTotal(any());
  }

  @Test
  public void testSetTaxTotal() {
    spyOffer.setTaxTotal(BigDecimal.ZERO);
    assertEquals("TaxTotal", BigDecimal.ZERO, spyOffer.getTaxTotal());
    verify(spyOffer, times(1)).setTaxTotal(any());
  }

  @Test
  public void testToString() {
    assertEquals(
        new ReflectionToStringBuilder(spyOffer, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP).toString(),
        spyOffer.toString());
  }
}
