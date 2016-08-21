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
import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.offer.OfferRecord;
import br.com.netbrasoft.gnuob.generic.product.Option;
import br.com.netbrasoft.gnuob.generic.product.Product;

public class OfferRecordTest {

  private Option mockOption;
  private Product mockProduct;
  private OfferRecord spyOfferRecord;

  @Before
  public void setUp() throws Exception {
    mockOption = mock(Option.class);
    mockProduct = mock(Product.class);
    spyOfferRecord = spy(OfferRecord.class);
    spyOfferRecord.setProduct(mockProduct);
    spyOfferRecord.setOptions(newSet(mockOption));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetAmount() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("Amount", spyOfferRecord.getAmount());
    verify(spyOfferRecord, times(1)).getAmount();
  }

  @Test
  public void testGetAmountWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("Amount", spyOfferRecord.getAmount());
    verify(spyOfferRecord, times(1)).getAmount();
  }

  @Test
  public void testGetDescription() {
    when(mockProduct.getDescription()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("Description", spyOfferRecord.getDescription());
    verify(spyOfferRecord, times(1)).getDescription();
  }

  @Test
  public void testGetDescriptionWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("Description", spyOfferRecord.getDescription());
    verify(spyOfferRecord, times(1)).getDescription();
  }

  @Test
  public void testGetDiscount() {
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("Discount", spyOfferRecord.getDiscount());
    verify(spyOfferRecord, times(1)).getDiscount();
  }

  @Test
  public void testGetDiscountTotal() {
    spyOfferRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("DiscountTotal", spyOfferRecord.getDiscountTotal());
    verify(spyOfferRecord, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetDiscountTotalWithDiscountAndQuantityNull() {
    when(mockProduct.getDiscount()).thenReturn(null);
    assertNotNull("DiscountTotal", spyOfferRecord.getDiscountTotal());
    verify(spyOfferRecord, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetDiscountTotalWithQuantityNull() {
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("DiscountTotal", spyOfferRecord.getDiscountTotal());
    verify(spyOfferRecord, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetDiscountWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("Discount", spyOfferRecord.getDiscount());
    verify(spyOfferRecord, times(1)).getDiscount();
  }

  @Test
  public void testGetItemHeight() {
    when(mockProduct.getItemHeight()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemHeight", spyOfferRecord.getItemHeight());
    verify(spyOfferRecord, times(1)).getItemHeight();
  }

  @Test
  public void testGetItemHeightUnit() {
    when(mockProduct.getItemHeightUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemHeightUnit", spyOfferRecord.getItemHeightUnit());
    verify(spyOfferRecord, times(1)).getItemHeightUnit();
  }

  @Test
  public void testGetItemHeightUnitWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemHeightUnit", spyOfferRecord.getItemHeightUnit());
    verify(spyOfferRecord, times(1)).getItemHeightUnit();
  }

  @Test
  public void testGetItemHeightWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemHeight", spyOfferRecord.getItemHeight());
    verify(spyOfferRecord, times(1)).getItemHeight();
  }

  @Test
  public void testGetItemLength() {
    when(mockProduct.getItemLength()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemLength", spyOfferRecord.getItemLength());
    verify(spyOfferRecord, times(1)).getItemLength();
  }

  @Test
  public void testGetItemLengthUnit() {
    when(mockProduct.getItemLengthUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemLengthUnit", spyOfferRecord.getItemLengthUnit());
    verify(spyOfferRecord, times(1)).getItemLengthUnit();
  }

  @Test
  public void testGetItemLengthUnitWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemLengthUnit", spyOfferRecord.getItemLengthUnit());
    verify(spyOfferRecord, times(1)).getItemLengthUnit();
  }

  @Test
  public void testGetItemLengthWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemLength", spyOfferRecord.getItemLength());
    verify(spyOfferRecord, times(1)).getItemLength();
  }

  @Test
  public void testGetItemTotal() {
    spyOfferRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemTotal", spyOfferRecord.getItemTotal());
    verify(spyOfferRecord, times(1)).getItemTotal();
  }

  @Test
  public void testGetItemTotalWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNotNull("ItemTotal", spyOfferRecord.getItemTotal());
    verify(spyOfferRecord, times(1)).getItemTotal();
  }

  @Test
  public void testGetItemTotalWithQuantityIsNull() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemTotal", spyOfferRecord.getItemTotal());
    verify(spyOfferRecord, times(1)).getItemTotal();
  }

  @Test
  public void testGetItemUrl() {
    when(mockProduct.getItemUrl()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemUrl", spyOfferRecord.getItemUrl());
    verify(spyOfferRecord, times(1)).getItemUrl();
  }

  @Test
  public void testGetItemUrlWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemUrl", spyOfferRecord.getItemUrl());
    verify(spyOfferRecord, times(1)).getItemUrl();
  }

  @Test
  public void testGetItemWeight() {
    when(mockProduct.getItemWeight()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemWeight", spyOfferRecord.getItemWeight());
    verify(spyOfferRecord, times(1)).getItemWeight();
  }

  @Test
  public void testGetItemWeightUnit() {
    when(mockProduct.getItemWeightUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemWeightUnit", spyOfferRecord.getItemWeightUnit());
    verify(spyOfferRecord, times(1)).getItemWeightUnit();
  }

  @Test
  public void testGetItemWeightUnitWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemWeightUnit", spyOfferRecord.getItemWeightUnit());
    verify(spyOfferRecord, times(1)).getItemWeightUnit();
  }

  @Test
  public void testGetItemWeightWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemWeight", spyOfferRecord.getItemWeight());
    verify(spyOfferRecord, times(1)).getItemWeight();
  }

  @Test
  public void testGetItemWidth() {
    when(mockProduct.getItemWidth()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemWidth", spyOfferRecord.getItemWidth());
    verify(spyOfferRecord, times(1)).getItemWidth();
  }

  @Test
  public void testGetItemWidthUnit() {
    when(mockProduct.getItemWidthUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemWidthUnit", spyOfferRecord.getItemWidthUnit());
    verify(spyOfferRecord, times(1)).getItemWidthUnit();
  }

  @Test
  public void testGetItemWidthUnitWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemWidthUnit", spyOfferRecord.getItemWidthUnit());
    verify(spyOfferRecord, times(1)).getItemWidthUnit();
  }

  @Test
  public void testGetItemWidthWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ItemWidth", spyOfferRecord.getItemWidth());
    verify(spyOfferRecord, times(1)).getItemWidth();
  }

  @Test
  public void testGetName() {
    when(mockProduct.getName()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("Name", spyOfferRecord.getName());
    verify(spyOfferRecord, times(1)).getName();
  }

  @Test
  public void testGetNameWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("Name", spyOfferRecord.getName());
    verify(spyOfferRecord, times(1)).getName();
  }

  @Test
  public void testGetOfferRecordId() {
    assertNotNull("OfferRecordId", spyOfferRecord.getOfferRecordId());
    verify(spyOfferRecord, times(1)).getOfferRecordId();
  }

  @Test
  public void testGetOfferRecordIdWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNotNull("OfferRecordId", spyOfferRecord.getOfferRecordId());
    verify(spyOfferRecord, times(1)).getOfferRecordId();
  }

  @Test
  public void testGetOption() {
    assertNull("Option", spyOfferRecord.getOption());
    verify(spyOfferRecord, times(1)).getOption();
  }

  @Test
  public void testGetOptions() {
    assertNotNull("Options", spyOfferRecord.getOptions());
    verify(spyOfferRecord, times(1)).getOptions();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spyOfferRecord.getPosition());
    verify(spyOfferRecord, times(1)).getPosition();
  }

  @Test
  public void testGetProduct() {
    assertNotNull("Product", spyOfferRecord.getProduct());
    verify(spyOfferRecord, times(1)).getProduct();
  }

  @Test
  public void testGetProductNumber() {
    when(mockProduct.getNumber()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ProductNumber", spyOfferRecord.getProductNumber());
    verify(spyOfferRecord, times(1)).getProductNumber();
  }

  @Test
  public void testGetProductNumberWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ProductNumber", spyOfferRecord.getProductNumber());
    verify(spyOfferRecord, times(1)).getProductNumber();
  }

  @Test
  public void testGetQuantity() {
    assertNull("Quantity", spyOfferRecord.getQuantity());
    verify(spyOfferRecord, times(1)).getQuantity();
  }

  @Test
  public void testGetShippingCost() {
    spyOfferRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getShippingCost()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingCost", spyOfferRecord.getShippingCost());
    verify(spyOfferRecord, times(1)).getShippingCost();
  }

  @Test
  public void testGetShippingCostWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("ShippingCost", spyOfferRecord.getShippingCost());
    verify(spyOfferRecord, times(1)).getShippingCost();
  }

  @Test
  public void testGetShippingTotal() {
    spyOfferRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getShippingCost()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingTotal", spyOfferRecord.getShippingTotal());
    verify(spyOfferRecord, times(1)).getShippingTotal();
  }

  @Test
  public void testGetShippingTotalWithQuantityNull() {
    when(mockProduct.getShippingCost()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingTotal", spyOfferRecord.getShippingTotal());
    verify(spyOfferRecord, times(1)).getShippingTotal();
  }

  @Test
  public void testGetShippingTotalWithShippingCostAndQuantityNull() {
    when(mockProduct.getShippingCost()).thenReturn(null);
    assertNotNull("ShippingTotal", spyOfferRecord.getShippingTotal());
    verify(spyOfferRecord, times(1)).getShippingTotal();
  }

  @Test
  public void testGetTax() {
    when(mockProduct.getTax()).thenReturn(BigDecimal.ZERO);
    assertNotNull("Tax", spyOfferRecord.getTax());
    verify(spyOfferRecord, times(1)).getTax();
  }

  @Test
  public void testGetTaxTotal() {
    spyOfferRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getTax()).thenReturn(BigDecimal.ZERO);
    assertNotNull("TaxTotal", spyOfferRecord.getTaxTotal());
    verify(spyOfferRecord, times(1)).getTaxTotal();
  }

  @Test
  public void testGetTaxTotalWithQuantityNull() {
    when(mockProduct.getTax()).thenReturn(BigDecimal.ZERO);
    assertNotNull("TaxTotal", spyOfferRecord.getTaxTotal());
    verify(spyOfferRecord, times(1)).getTaxTotal();
  }

  @Test
  public void testGetTaxTotalWithTaxAndQuantityNull() {
    when(mockProduct.getTax()).thenReturn(null);
    assertNotNull("TaxTotal", spyOfferRecord.getTaxTotal());
    verify(spyOfferRecord, times(1)).getTaxTotal();
  }

  @Test
  public void testGetTaxWithProductIsNull() {
    spyOfferRecord.setProduct(null);
    assertNull("Tax", spyOfferRecord.getTax());
    verify(spyOfferRecord, times(1)).getTax();
  }

  @Test
  public void testOfferRecordIsDetached() {
    spyOfferRecord.setId(1L);
    when(mockOption.isDetached()).thenReturn(false);
    assertTrue("Offer", spyOfferRecord.isDetached());
    verify(spyOfferRecord, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testOfferRecordIsDetachedByOptions() {
    spyOfferRecord.setId(0L);
    when(mockOption.isDetached()).thenReturn(true);
    assertTrue("Offer", spyOfferRecord.isDetached());
    verify(spyOfferRecord, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testOfferRecordIsNotDetached() {
    spyOfferRecord.setId(0L);
    when(mockOption.isDetached()).thenReturn(false);
    assertFalse("Offer", spyOfferRecord.isDetached());
    verify(spyOfferRecord, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testOfferRecordIsNotDetachedByOptionsIsNull() {
    spyOfferRecord.setId(0L);
    spyOfferRecord.setOptions(null);
    assertFalse("Offer", spyOfferRecord.isDetached());
    verify(spyOfferRecord, times(1)).isDetached();
    verify(mockOption, never()).isDetached();
  }

  @Test
  public void testPrePersist() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    spyOfferRecord.prePersist();
    verify(spyOfferRecord, times(1)).getOfferRecordId();
    verify(spyOfferRecord, times(1)).getName();
    verify(spyOfferRecord, times(1)).getDescription();
    verify(spyOfferRecord, times(1)).getAmount();
    verify(spyOfferRecord, times(1)).getProductNumber();
    verify(spyOfferRecord, times(1)).getTax();
    verify(spyOfferRecord, times(1)).getShippingCost();
    verify(spyOfferRecord, times(1)).getItemWeight();
    verify(spyOfferRecord, times(1)).getItemWeightUnit();
    verify(spyOfferRecord, times(1)).getItemLength();
    verify(spyOfferRecord, times(1)).getItemLengthUnit();
    verify(spyOfferRecord, times(1)).getItemHeight();
    verify(spyOfferRecord, times(1)).getItemHeightUnit();
    verify(spyOfferRecord, times(1)).getItemUrl();
    verify(mockOption, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    spyOfferRecord.preUpdate();
    verify(spyOfferRecord, times(1)).prePersist();
  }

  @Test
  public void testSetAmount() {
    spyOfferRecord.setAmount(BigDecimal.ZERO);
    assertEquals("Amount", BigDecimal.ZERO, spyOfferRecord.getAmount());
    verify(spyOfferRecord, times(1)).setAmount(any());
  }

  @Test
  public void testSetDescription() {
    spyOfferRecord.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("OfferDescription", "Folly words widow one downs few age every seven.",
        spyOfferRecord.getDescription());
    verify(spyOfferRecord, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetDiscount() {
    spyOfferRecord.setDiscount(BigDecimal.ZERO);
    assertEquals("Discount", BigDecimal.ZERO, spyOfferRecord.getDiscount());
    verify(spyOfferRecord, times(1)).setDiscount(any());
  }

  @Test
  public void testSetItemHeight() {
    spyOfferRecord.setItemHeight(BigDecimal.ZERO);
    assertEquals("ItemHeight", BigDecimal.ZERO, spyOfferRecord.getItemHeight());
    verify(spyOfferRecord, times(1)).setItemHeight(any());
  }

  @Test
  public void testSetItemHeightUnit() {
    spyOfferRecord.setItemHeightUnit("Folly words widow one downs few age every seven.");
    assertEquals("OfferDescription", "Folly words widow one downs few age every seven.",
        spyOfferRecord.getItemHeightUnit());
    verify(spyOfferRecord, times(1)).setItemHeightUnit(anyString());
  }


  @Test
  public void testSetItemLength() {
    spyOfferRecord.setItemLength(BigDecimal.ZERO);
    assertEquals("ItemLength", BigDecimal.ZERO, spyOfferRecord.getItemLength());
    verify(spyOfferRecord, times(1)).setItemLength(any());
  }

  @Test
  public void testSetItemLengthUnit() {
    spyOfferRecord.setItemLengthUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemLengthUnit", "Folly words widow one downs few age every seven.",
        spyOfferRecord.getItemLengthUnit());
    verify(spyOfferRecord, times(1)).setItemLengthUnit(anyString());
  }

  @Test
  public void testSetItemUrl() {
    spyOfferRecord.setItemUrl("Folly words widow one downs few age every seven.");
    assertEquals("ItemUrl", "Folly words widow one downs few age every seven.", spyOfferRecord.getItemUrl());
    verify(spyOfferRecord, times(1)).setItemUrl(anyString());
  }

  @Test
  public void testSetItemWeight() {
    spyOfferRecord.setItemWeight(BigDecimal.ZERO);
    assertEquals("ItemWeight", BigDecimal.ZERO, spyOfferRecord.getItemWeight());
    verify(spyOfferRecord, times(1)).setItemWeight(any());
  }

  @Test
  public void testSetItemWeightUnit() {
    spyOfferRecord.setItemWeightUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemWeightUnit", "Folly words widow one downs few age every seven.",
        spyOfferRecord.getItemWeightUnit());
    verify(spyOfferRecord, times(1)).setItemWeightUnit(anyString());
  }

  @Test
  public void testSetItemWidth() {
    spyOfferRecord.setItemWidth(BigDecimal.ZERO);
    assertEquals("ItemWeight", BigDecimal.ZERO, spyOfferRecord.getItemWidth());
    verify(spyOfferRecord, times(1)).setItemWidth(any());
  }

  @Test
  public void testSetItemWidthUnit() {
    spyOfferRecord.setItemWidthUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemWidthUnit", "Folly words widow one downs few age every seven.",
        spyOfferRecord.getItemWidthUnit());
    verify(spyOfferRecord, times(1)).setItemWidthUnit(anyString());
  }

  @Test
  public void testSetName() {
    spyOfferRecord.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spyOfferRecord.getName());
    verify(spyOfferRecord, times(1)).setName(anyString());
  }

  @Test
  public void testSetOfferRecordId() {
    spyOfferRecord.setOfferRecordId("Folly words widow one downs few age every seven.");
    assertEquals("OfferRecordId", "Folly words widow one downs few age every seven.",
        spyOfferRecord.getOfferRecordId());
    verify(spyOfferRecord, times(1)).setOfferRecordId(anyString());
  }

  @Test
  public void testSetOfferRecordIdWithEmptyString() {
    spyOfferRecord.setOfferRecordId(" ");
    assertNotEquals("OfferRecordId", " ", spyOfferRecord.getOfferRecordId());
    verify(spyOfferRecord, times(1)).setOfferRecordId(anyString());
  }

  @Test
  public void testSetOption() {
    spyOfferRecord.setOption("Folly words widow one downs few age every seven.");
    assertEquals("Option", "Folly words widow one downs few age every seven.", spyOfferRecord.getOption());
    verify(spyOfferRecord, times(1)).setOption(anyString());
  }

  @Test
  public void testSetOptions() {
    spyOfferRecord.setOptions(null);
    assertNull("Options", spyOfferRecord.getOptions());
    verify(spyOfferRecord, times(1)).setOptions(null);
  }

  @Test
  public void testSetPosition() {
    spyOfferRecord.setPosition(null);
    assertNull("Position", spyOfferRecord.getPosition());
    verify(spyOfferRecord, times(1)).setPosition(null);
  }

  @Test
  public void testSetProduct() {
    spyOfferRecord.setProduct(null);
    assertNull("Product", spyOfferRecord.getProduct());
    verify(spyOfferRecord, times(1)).setProduct(null);
  }

  @Test
  public void testSetProductNumber() {
    spyOfferRecord.setProductNumber("Folly words widow one downs few age every seven.");
    assertEquals("ProductNumber", "Folly words widow one downs few age every seven.",
        spyOfferRecord.getProductNumber());
    verify(spyOfferRecord, times(1)).setProductNumber(anyString());
  }

  @Test
  public void testSetQuantity() {
    spyOfferRecord.setQuantity(BigInteger.ZERO);
    assertEquals("Quantity", BigInteger.ZERO, spyOfferRecord.getQuantity());
    verify(spyOfferRecord, times(1)).setQuantity(any());
  }

  @Test
  public void testSetShippingCost() {
    spyOfferRecord.setShippingCost(BigDecimal.ZERO);
    assertEquals("ShippingCost", BigDecimal.ZERO, spyOfferRecord.getShippingCost());
    verify(spyOfferRecord, times(1)).setShippingCost(any());
  }

  @Test
  public void testSetTax() {
    spyOfferRecord.setTax(BigDecimal.ZERO);
    assertEquals("Tax", BigDecimal.ZERO, spyOfferRecord.getTax());
    verify(spyOfferRecord, times(1)).setTax(any());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyOfferRecord, SHORT_PREFIX_STYLE), spyOfferRecord.toString());
  }
}
