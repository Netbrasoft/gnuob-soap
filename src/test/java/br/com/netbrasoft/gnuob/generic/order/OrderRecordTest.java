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
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.order.OrderRecord;
import br.com.netbrasoft.gnuob.generic.product.Option;
import br.com.netbrasoft.gnuob.generic.product.Product;

public class OrderRecordTest {

  private Option mockOption;
  private Product mockProduct;
  private OrderRecord spyOrderRecord;

  @Before
  public void setUp() throws Exception {
    mockOption = mock(Option.class);
    mockProduct = mock(Product.class);
    spyOrderRecord = spy(OrderRecord.class);
    spyOrderRecord.setProduct(mockProduct);
    spyOrderRecord.setOptions(newSet(mockOption));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetAmount() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("Amount", spyOrderRecord.getAmount());
    verify(spyOrderRecord, times(1)).getAmount();
  }

  @Test
  public void testGetAmountWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("Amount", spyOrderRecord.getAmount());
    verify(spyOrderRecord, times(1)).getAmount();
  }

  @Test
  public void testGetDeliveryDate() {
    spyOrderRecord.setDeliveryDate(null);
    assertNull("DeliveryDate", spyOrderRecord.getDeliveryDate());
    verify(spyOrderRecord, times(1)).getDeliveryDate();
  }

  @Test
  public void testGetDescription() {
    when(mockProduct.getDescription()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("Description", spyOrderRecord.getDescription());
    verify(spyOrderRecord, times(1)).getDescription();
  }

  @Test
  public void testGetDescriptionWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("Description", spyOrderRecord.getDescription());
    verify(spyOrderRecord, times(1)).getDescription();
  }

  @Test
  public void testGetDiscount() {
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("Discount", spyOrderRecord.getDiscount());
    verify(spyOrderRecord, times(1)).getDiscount();
  }

  @Test
  public void testGetDiscountTotal() {
    spyOrderRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("DiscountTotal", spyOrderRecord.getDiscountTotal());
    verify(spyOrderRecord, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetDiscountTotalWithDiscountAndQuantityNull() {
    when(mockProduct.getDiscount()).thenReturn(null);
    assertNotNull("DiscountTotal", spyOrderRecord.getDiscountTotal());
    verify(spyOrderRecord, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetDiscountTotalWithQuantityNull() {
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("DiscountTotal", spyOrderRecord.getDiscountTotal());
    verify(spyOrderRecord, times(1)).getDiscountTotal();
  }

  @Test
  public void testGetDiscountWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("Discount", spyOrderRecord.getDiscount());
    verify(spyOrderRecord, times(1)).getDiscount();
  }

  @Test
  public void testGetItemHeight() {
    when(mockProduct.getItemHeight()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemHeight", spyOrderRecord.getItemHeight());
    verify(spyOrderRecord, times(1)).getItemHeight();
  }

  @Test
  public void testGetItemHeightUnit() {
    when(mockProduct.getItemHeightUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemHeightUnit", spyOrderRecord.getItemHeightUnit());
    verify(spyOrderRecord, times(1)).getItemHeightUnit();
  }

  @Test
  public void testGetItemHeightUnitWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemHeightUnit", spyOrderRecord.getItemHeightUnit());
    verify(spyOrderRecord, times(1)).getItemHeightUnit();
  }

  @Test
  public void testGetItemHeightWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemHeight", spyOrderRecord.getItemHeight());
    verify(spyOrderRecord, times(1)).getItemHeight();
  }

  @Test
  public void testGetItemLength() {
    when(mockProduct.getItemLength()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemLength", spyOrderRecord.getItemLength());
    verify(spyOrderRecord, times(1)).getItemLength();
  }

  @Test
  public void testGetItemLengthUnit() {
    when(mockProduct.getItemLengthUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemLengthUnit", spyOrderRecord.getItemLengthUnit());
    verify(spyOrderRecord, times(1)).getItemLengthUnit();
  }

  @Test
  public void testGetItemLengthUnitWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemLengthUnit", spyOrderRecord.getItemLengthUnit());
    verify(spyOrderRecord, times(1)).getItemLengthUnit();
  }

  @Test
  public void testGetItemLengthWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemLength", spyOrderRecord.getItemLength());
    verify(spyOrderRecord, times(1)).getItemLength();
  }

  @Test
  public void testGetItemTotal() {
    spyOrderRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemTotal", spyOrderRecord.getItemTotal());
    verify(spyOrderRecord, times(1)).getItemTotal();
  }

  @Test
  public void testGetItemTotalWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNotNull("ItemTotal", spyOrderRecord.getItemTotal());
    verify(spyOrderRecord, times(1)).getItemTotal();
  }

  @Test
  public void testGetItemTotalWithQuantityIsNull() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemTotal", spyOrderRecord.getItemTotal());
    verify(spyOrderRecord, times(1)).getItemTotal();
  }

  @Test
  public void testGetItemUrl() {
    when(mockProduct.getItemUrl()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemUrl", spyOrderRecord.getItemUrl());
    verify(spyOrderRecord, times(1)).getItemUrl();
  }

  @Test
  public void testGetItemUrlWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemUrl", spyOrderRecord.getItemUrl());
    verify(spyOrderRecord, times(1)).getItemUrl();
  }

  @Test
  public void testGetItemWeight() {
    when(mockProduct.getItemWeight()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemWeight", spyOrderRecord.getItemWeight());
    verify(spyOrderRecord, times(1)).getItemWeight();
  }

  @Test
  public void testGetItemWeightUnit() {
    when(mockProduct.getItemWeightUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemWeightUnit", spyOrderRecord.getItemWeightUnit());
    verify(spyOrderRecord, times(1)).getItemWeightUnit();
  }

  @Test
  public void testGetItemWeightUnitWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemWeightUnit", spyOrderRecord.getItemWeightUnit());
    verify(spyOrderRecord, times(1)).getItemWeightUnit();
  }

  @Test
  public void testGetItemWeightWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemWeight", spyOrderRecord.getItemWeight());
    verify(spyOrderRecord, times(1)).getItemWeight();
  }

  @Test
  public void testGetItemWidth() {
    when(mockProduct.getItemWidth()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ItemWidth", spyOrderRecord.getItemWidth());
    verify(spyOrderRecord, times(1)).getItemWidth();
  }

  @Test
  public void testGetItemWidthUnit() {
    when(mockProduct.getItemWidthUnit()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ItemWidthUnit", spyOrderRecord.getItemWidthUnit());
    verify(spyOrderRecord, times(1)).getItemWidthUnit();
  }

  @Test
  public void testGetItemWidthUnitWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemWidthUnit", spyOrderRecord.getItemWidthUnit());
    verify(spyOrderRecord, times(1)).getItemWidthUnit();
  }

  @Test
  public void testGetItemWidthWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ItemWidth", spyOrderRecord.getItemWidth());
    verify(spyOrderRecord, times(1)).getItemWidth();
  }

  @Test
  public void testGetName() {
    when(mockProduct.getName()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("Name", spyOrderRecord.getName());
    verify(spyOrderRecord, times(1)).getName();
  }

  @Test
  public void testGetNameWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("Name", spyOrderRecord.getName());
    verify(spyOrderRecord, times(1)).getName();
  }

  @Test
  public void testGetOption() {
    assertNull("Option", spyOrderRecord.getOption());
    verify(spyOrderRecord, times(1)).getOption();
  }

  @Test
  public void testGetOptions() {
    assertNotNull("Options", spyOrderRecord.getOptions());
    verify(spyOrderRecord, times(1)).getOptions();
  }

  @Test
  public void testGetOrderRecordId() {
    assertNotNull("OrderRecordId", spyOrderRecord.getOrderRecordId());
    verify(spyOrderRecord, times(1)).getOrderRecordId();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spyOrderRecord.getPosition());
    verify(spyOrderRecord, times(1)).getPosition();
  }

  @Test
  public void testGetProduct() {
    assertNotNull("Product", spyOrderRecord.getProduct());
    verify(spyOrderRecord, times(1)).getProduct();
  }

  @Test
  public void testGetProductNumber() {
    when(mockProduct.getNumber()).thenReturn("Folly words widow one downs few age every seven.");
    assertNotNull("ProductNumber", spyOrderRecord.getProductNumber());
    verify(spyOrderRecord, times(1)).getProductNumber();
  }

  @Test
  public void testGetProductNumberWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ProductNumber", spyOrderRecord.getProductNumber());
    verify(spyOrderRecord, times(1)).getProductNumber();
  }

  @Test
  public void testGetQuantity() {
    assertNull("Quantity", spyOrderRecord.getQuantity());
    verify(spyOrderRecord, times(1)).getQuantity();
  }

  @Test
  public void testGetShippingCost() {
    spyOrderRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getShippingCost()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingCost", spyOrderRecord.getShippingCost());
    verify(spyOrderRecord, times(1)).getShippingCost();
  }

  @Test
  public void testGetShippingCostWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("ShippingCost", spyOrderRecord.getShippingCost());
    verify(spyOrderRecord, times(1)).getShippingCost();
  }

  @Test
  public void testGetShippingTotal() {
    spyOrderRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getShippingCost()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingTotal", spyOrderRecord.getShippingTotal());
    verify(spyOrderRecord, times(1)).getShippingTotal();
  }

  @Test
  public void testGetShippingTotalWithQuantityNull() {
    when(mockProduct.getShippingCost()).thenReturn(BigDecimal.ZERO);
    assertNotNull("ShippingTotal", spyOrderRecord.getShippingTotal());
    verify(spyOrderRecord, times(1)).getShippingTotal();
  }

  @Test
  public void testGetShippingTotalWithShippingCostAndQuantityNull() {
    when(mockProduct.getShippingCost()).thenReturn(null);
    assertNotNull("ShippingTotal", spyOrderRecord.getShippingTotal());
    verify(spyOrderRecord, times(1)).getShippingTotal();
  }

  @Test
  public void testGetTax() {
    when(mockProduct.getTax()).thenReturn(BigDecimal.ZERO);
    assertNotNull("Tax", spyOrderRecord.getTax());
    verify(spyOrderRecord, times(1)).getTax();
  }

  @Test
  public void testGetTaxTotal() {
    spyOrderRecord.setQuantity(BigInteger.ZERO);
    when(mockProduct.getTax()).thenReturn(BigDecimal.ZERO);
    assertNotNull("TaxTotal", spyOrderRecord.getTaxTotal());
    verify(spyOrderRecord, times(1)).getTaxTotal();
  }

  @Test
  public void testGetTaxTotalWithQuantityNull() {
    when(mockProduct.getTax()).thenReturn(BigDecimal.ZERO);
    assertNotNull("TaxTotal", spyOrderRecord.getTaxTotal());
    verify(spyOrderRecord, times(1)).getTaxTotal();
  }

  @Test
  public void testGetTaxTotalWithTaxAndQuantityNull() {
    when(mockProduct.getTax()).thenReturn(null);
    assertNotNull("TaxTotal", spyOrderRecord.getTaxTotal());
    verify(spyOrderRecord, times(1)).getTaxTotal();
  }

  @Test
  public void testGetTaxWithProductIsNull() {
    spyOrderRecord.setProduct(null);
    assertNull("Tax", spyOrderRecord.getTax());
    verify(spyOrderRecord, times(1)).getTax();
  }

  @Test
  public void testOrderRecordIsDetached() {
    spyOrderRecord.setId(1L);
    when(mockOption.isDetached()).thenReturn(false);
    assertTrue("Order", spyOrderRecord.isDetached());
    verify(spyOrderRecord, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testOrderRecordIsDetachedByOptions() {
    spyOrderRecord.setId(0L);
    when(mockOption.isDetached()).thenReturn(true);
    assertTrue("Order", spyOrderRecord.isDetached());
    verify(spyOrderRecord, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testOrderRecordIsNotDetached() {
    spyOrderRecord.setId(0L);
    when(mockOption.isDetached()).thenReturn(false);
    assertFalse("Order", spyOrderRecord.isDetached());
    verify(spyOrderRecord, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testOrderRecordIsNotDetachedByOptionsIsNull() {
    spyOrderRecord.setId(0L);
    spyOrderRecord.setOptions(null);
    assertFalse("Order", spyOrderRecord.isDetached());
    verify(spyOrderRecord, times(1)).isDetached();
    verify(mockOption, never()).isDetached();
  }

  @Test
  public void testPrePersist() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    spyOrderRecord.prePersist();
    verify(spyOrderRecord, times(1)).getOrderRecordId();
    verify(spyOrderRecord, times(1)).getName();
    verify(spyOrderRecord, times(1)).getDescription();
    verify(spyOrderRecord, times(1)).getAmount();
    verify(spyOrderRecord, times(1)).getProductNumber();
    verify(spyOrderRecord, times(1)).getTax();
    verify(spyOrderRecord, times(1)).getShippingCost();
    verify(spyOrderRecord, times(1)).getItemWeight();
    verify(spyOrderRecord, times(1)).getItemWeightUnit();
    verify(spyOrderRecord, times(1)).getItemLength();
    verify(spyOrderRecord, times(1)).getItemLengthUnit();
    verify(spyOrderRecord, times(1)).getItemHeight();
    verify(spyOrderRecord, times(1)).getItemHeightUnit();
    verify(spyOrderRecord, times(1)).getItemUrl();
    verify(mockOption, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    when(mockProduct.getAmount()).thenReturn(BigDecimal.ZERO);
    when(mockProduct.getDiscount()).thenReturn(BigDecimal.ZERO);
    spyOrderRecord.preUpdate();
    verify(spyOrderRecord, times(1)).prePersist();
  }

  @Test
  public void testSetAmount() {
    spyOrderRecord.setAmount(BigDecimal.ZERO);
    assertEquals("Amount", BigDecimal.ZERO, spyOrderRecord.getAmount());
    verify(spyOrderRecord, times(1)).setAmount(any());
  }

  @Test
  public void testSetDeliveryDate() {
    spyOrderRecord.setDeliveryDate(new Date());
    assertNotNull("DeliveryDate", spyOrderRecord.getDeliveryDate());
    verify(spyOrderRecord, times(1)).setDeliveryDate(any());
  }

  @Test
  public void testSetDescription() {
    spyOrderRecord.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("OrderDescription", "Folly words widow one downs few age every seven.",
        spyOrderRecord.getDescription());
    verify(spyOrderRecord, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetDiscount() {
    spyOrderRecord.setDiscount(BigDecimal.ZERO);
    assertEquals("Discount", BigDecimal.ZERO, spyOrderRecord.getDiscount());
    verify(spyOrderRecord, times(1)).setDiscount(any());
  }

  @Test
  public void testSetItemHeight() {
    spyOrderRecord.setItemHeight(BigDecimal.ZERO);
    assertEquals("ItemHeight", BigDecimal.ZERO, spyOrderRecord.getItemHeight());
    verify(spyOrderRecord, times(1)).setItemHeight(any());
  }

  @Test
  public void testSetItemHeightUnit() {
    spyOrderRecord.setItemHeightUnit("Folly words widow one downs few age every seven.");
    assertEquals("OrderDescription", "Folly words widow one downs few age every seven.",
        spyOrderRecord.getItemHeightUnit());
    verify(spyOrderRecord, times(1)).setItemHeightUnit(anyString());
  }

  @Test
  public void testSetItemLength() {
    spyOrderRecord.setItemLength(BigDecimal.ZERO);
    assertEquals("ItemLength", BigDecimal.ZERO, spyOrderRecord.getItemLength());
    verify(spyOrderRecord, times(1)).setItemLength(any());
  }


  @Test
  public void testSetItemLengthUnit() {
    spyOrderRecord.setItemLengthUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemLengthUnit", "Folly words widow one downs few age every seven.",
        spyOrderRecord.getItemLengthUnit());
    verify(spyOrderRecord, times(1)).setItemLengthUnit(anyString());
  }

  @Test
  public void testSetItemUrl() {
    spyOrderRecord.setItemUrl("Folly words widow one downs few age every seven.");
    assertEquals("ItemUrl", "Folly words widow one downs few age every seven.", spyOrderRecord.getItemUrl());
    verify(spyOrderRecord, times(1)).setItemUrl(anyString());
  }

  @Test
  public void testSetItemWeight() {
    spyOrderRecord.setItemWeight(BigDecimal.ZERO);
    assertEquals("ItemWeight", BigDecimal.ZERO, spyOrderRecord.getItemWeight());
    verify(spyOrderRecord, times(1)).setItemWeight(any());
  }

  @Test
  public void testSetItemWeightUnit() {
    spyOrderRecord.setItemWeightUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemWeightUnit", "Folly words widow one downs few age every seven.",
        spyOrderRecord.getItemWeightUnit());
    verify(spyOrderRecord, times(1)).setItemWeightUnit(anyString());
  }

  @Test
  public void testSetItemWidth() {
    spyOrderRecord.setItemWidth(BigDecimal.ZERO);
    assertEquals("ItemWeight", BigDecimal.ZERO, spyOrderRecord.getItemWidth());
    verify(spyOrderRecord, times(1)).setItemWidth(any());
  }

  @Test
  public void testSetItemWidthUnit() {
    spyOrderRecord.setItemWidthUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemWidthUnit", "Folly words widow one downs few age every seven.",
        spyOrderRecord.getItemWidthUnit());
    verify(spyOrderRecord, times(1)).setItemWidthUnit(anyString());
  }

  @Test
  public void testSetName() {
    spyOrderRecord.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spyOrderRecord.getName());
    verify(spyOrderRecord, times(1)).setName(anyString());
  }

  @Test
  public void testSetOption() {
    spyOrderRecord.setOption("Folly words widow one downs few age every seven.");
    assertEquals("Option", "Folly words widow one downs few age every seven.", spyOrderRecord.getOption());
    verify(spyOrderRecord, times(1)).setOption(anyString());
  }

  @Test
  public void testSetOptions() {
    spyOrderRecord.setOptions(null);
    assertNull("Options", spyOrderRecord.getOptions());
    verify(spyOrderRecord, times(1)).setOptions(null);
  }

  @Test
  public void testSetOrderRecordId() {
    spyOrderRecord.setOrderRecordId("Folly words widow one downs few age every seven.");
    assertEquals("OrderRecordId", "Folly words widow one downs few age every seven.",
        spyOrderRecord.getOrderRecordId());
    verify(spyOrderRecord, times(1)).setOrderRecordId(anyString());
  }

  @Test
  public void testSetOrderRecordIdWithEmptyString() {
    spyOrderRecord.setOrderRecordId("");
    assertNotEquals("OrderRecordId", " ", spyOrderRecord.getOrderRecordId());
    verify(spyOrderRecord, times(1)).setOrderRecordId(anyString());
  }

  @Test
  public void testSetPosition() {
    spyOrderRecord.setPosition(null);
    assertNull("Position", spyOrderRecord.getPosition());
    verify(spyOrderRecord, times(1)).setPosition(null);
  }

  @Test
  public void testSetProduct() {
    spyOrderRecord.setProduct(null);
    assertNull("Product", spyOrderRecord.getProduct());
    verify(spyOrderRecord, times(1)).setProduct(null);
  }

  @Test
  public void testSetProductNumber() {
    spyOrderRecord.setProductNumber("Folly words widow one downs few age every seven.");
    assertEquals("ProductNumber", "Folly words widow one downs few age every seven.",
        spyOrderRecord.getProductNumber());
    verify(spyOrderRecord, times(1)).setProductNumber(anyString());
  }

  @Test
  public void testSetQuantity() {
    spyOrderRecord.setQuantity(BigInteger.ZERO);
    assertEquals("Quantity", BigInteger.ZERO, spyOrderRecord.getQuantity());
    verify(spyOrderRecord, times(1)).setQuantity(any());
  }

  @Test
  public void testSetShippingCost() {
    spyOrderRecord.setShippingCost(BigDecimal.ZERO);
    assertEquals("ShippingCost", BigDecimal.ZERO, spyOrderRecord.getShippingCost());
    verify(spyOrderRecord, times(1)).setShippingCost(any());
  }

  @Test
  public void testSetTax() {
    spyOrderRecord.setTax(BigDecimal.ZERO);
    assertEquals("Tax", BigDecimal.ZERO, spyOrderRecord.getTax());
    verify(spyOrderRecord, times(1)).setTax(any());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyOrderRecord, SHORT_PREFIX_STYLE), spyOrderRecord.toString());
  }
}
