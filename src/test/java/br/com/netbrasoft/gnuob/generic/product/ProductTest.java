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

package br.com.netbrasoft.gnuob.generic.product;

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

import br.com.netbrasoft.gnuob.generic.category.SubCategory;
import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;

public class ProductTest {

  private Content mockContent;
  private Option mockOption;
  private Stock mockStock;
  private SubCategory mockSubCategory;
  private Product spyProduct;

  @Before
  public void setUp() throws Exception {
    mockContent = mock(Content.class);
    mockOption = mock(Option.class);
    mockStock = mock(Stock.class);
    mockContent = mock(Content.class);
    mockSubCategory = mock(SubCategory.class);
    spyProduct = spy(Product.class);
    spyProduct.setContents(newSet(mockContent));
    spyProduct.setOptions(newSet(mockOption));
    spyProduct.setSubCategories(newSet(mockSubCategory));
    spyProduct.setStock(mockStock);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyProduct.accept(new ContextVisitorImpl()));
    verify(spyProduct, times(1)).accept(any());
  }

  @Test
  public void testGetAmount() {
    assertNull("Amount", spyProduct.getAmount());
    verify(spyProduct, times(1)).getAmount();
  }

  @Test
  public void testGetBestsellers() {
    assertNull("Bestsellers", spyProduct.getBestsellers());
    verify(spyProduct, times(1)).getBestsellers();
  }

  @Test
  public void testGetContents() {
    assertNotNull("Contents", spyProduct.getContents());
    verify(spyProduct, times(1)).getContents();
  }

  @Test
  public void testGetDescription() {
    assertNull("Description", spyProduct.getDescription());
    verify(spyProduct, times(1)).getDescription();
  }

  @Test
  public void testGetDiscount() {
    assertNull("Discount", spyProduct.getDiscount());
    verify(spyProduct, times(1)).getDiscount();
  }

  @Test
  public void testGetItemHeight() {
    assertNull("ItemHeight", spyProduct.getItemHeight());
    verify(spyProduct, times(1)).getItemHeight();
  }

  @Test
  public void testGetItemHeightUnit() {
    assertNull("ItemHeightUnit", spyProduct.getItemHeightUnit());
    verify(spyProduct, times(1)).getItemHeightUnit();
  }

  @Test
  public void testGetItemLength() {
    assertNull("ItemLength", spyProduct.getItemLength());
    verify(spyProduct, times(1)).getItemLength();
  }

  @Test
  public void testGetItemLengthUnit() {
    assertNull("ItemLengthUnit", spyProduct.getItemLengthUnit());
    verify(spyProduct, times(1)).getItemLengthUnit();
  }

  @Test
  public void testGetItemUrl() {
    assertNull("ItemUrl", spyProduct.getItemUrl());
    verify(spyProduct, times(1)).getItemUrl();
  }

  @Test
  public void testGetItemWeight() {
    assertNull("ItemWeight", spyProduct.getItemWeight());
    verify(spyProduct, times(1)).getItemWeight();
  }

  @Test
  public void testGetItemWeightUnit() {
    assertNull("ItemWeightUnit", spyProduct.getItemWeightUnit());
    verify(spyProduct, times(1)).getItemWeightUnit();
  }

  @Test
  public void testGetItemWidth() {
    assertNull("ItemWidth", spyProduct.getItemWidth());
    verify(spyProduct, times(1)).getItemWidth();
  }

  @Test
  public void testGetItemWidthUnit() {
    assertNull("ItemWidthUnit", spyProduct.getItemWidthUnit());
    verify(spyProduct, times(1)).getItemWidthUnit();
  }

  @Test
  public void testGetLatestCollection() {
    assertNull("LatestCollection", spyProduct.getLatestCollection());
    verify(spyProduct, times(1)).getLatestCollection();
  }

  @Test
  public void testGetName() {
    assertNull("Name", spyProduct.getName());
    verify(spyProduct, times(1)).getName();
  }

  @Test
  public void testGetNumber() {
    assertNull("Number", spyProduct.getNumber());
    verify(spyProduct, times(1)).getNumber();
  }

  @Test
  public void testGetOptions() {
    assertNotNull("Options", spyProduct.getOptions());
    verify(spyProduct, times(1)).getOptions();
  }

  @Test
  public void testGetRating() {
    assertNull("Rating", spyProduct.getRating());
    verify(spyProduct, times(1)).getRating();
  }

  @Test
  public void testGetRecommended() {
    assertNull("Recommended((ng", spyProduct.getRecommended());
    verify(spyProduct, times(1)).getRecommended();
  }

  @Test
  public void testGetShippingCost() {
    assertNull("ShippingCost((ng", spyProduct.getShippingCost());
    verify(spyProduct, times(1)).getShippingCost();
  }

  @Test
  public void testGetStock() {
    assertNotNull("Stock((ng", spyProduct.getStock());
    verify(spyProduct, times(1)).getStock();
  }

  @Test
  public void testGetSubCategories() {
    assertNotNull("SubCategories((ng", spyProduct.getSubCategories());
    verify(spyProduct, times(1)).getSubCategories();
  }

  @Test
  public void testGetTax() {
    assertNull("Tax", spyProduct.getTax());
    verify(spyProduct, times(1)).getTax();
  }

  @Test
  public void testPrePersist() {
    spyProduct.prePersist();
    verify(spyProduct, times(1)).prePersist();
    verify(mockContent, times(1)).setPosition(anyInt());
    verify(mockOption, times(1)).setPosition(anyInt());
    verify(mockSubCategory, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    spyProduct.preUpdate();
    verify(spyProduct, times(1)).preUpdate();
    verify(spyProduct, times(1)).prePersist();
  }

  @Test
  public void testProductIsDetached() {
    spyProduct.setId(1L);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(false);
    assertTrue("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsDetachedByContent() {
    spyProduct.setId(0L);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(true);
    when(mockOption.isDetached()).thenReturn(false);
    assertTrue("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsDetachedByOption() {
    spyProduct.setId(0L);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(true);
    assertTrue("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsDetachedByStock() {
    spyProduct.setId(0L);
    when(mockStock.isDetached()).thenReturn(true);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(false);
    assertTrue("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsDetachedBySubCategory() {
    spyProduct.setId(0L);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(true);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(false);
    assertTrue("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsNotDetached() {
    spyProduct.setId(0L);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(false);
    assertFalse("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsNotDetachedByContentIsNull() {
    spyProduct.setId(0L);
    spyProduct.setContents(null);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(false);
    assertFalse("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, never()).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsNotDetachedByOptionIsNull() {
    spyProduct.setId(0L);
    spyProduct.setOptions(null);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(false);
    assertFalse("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, never()).isDetached();
  }

  @Test
  public void testProductIsNotDetachedByStockIsNull() {
    spyProduct.setId(0L);
    spyProduct.setStock(null);
    when(mockSubCategory.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(false);
    assertFalse("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, never()).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testProductIsNotDetachedBySubCategoryIsNull() {
    spyProduct.setId(0L);
    spyProduct.setSubCategories(null);
    when(mockStock.isDetached()).thenReturn(false);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockOption.isDetached()).thenReturn(false);
    assertFalse("Detached", spyProduct.isDetached());
    verify(spyProduct, times(1)).isDetached();
    verify(mockStock, times(1)).isDetached();
    verify(mockSubCategory, never()).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockOption, times(1)).isDetached();
  }

  @Test
  public void testSetAmount() {
    spyProduct.setAmount(BigDecimal.ZERO);
    assertEquals("Amount", BigDecimal.ZERO, spyProduct.getAmount());
    verify(spyProduct, times(1)).setAmount(any());
  }

  @Test
  public void testSetBestsellers() {
    spyProduct.setBestsellers(Boolean.FALSE);
    assertEquals("Bestsellers", Boolean.FALSE, spyProduct.getBestsellers());
    verify(spyProduct, times(1)).setBestsellers(any());
  }

  @Test
  public void testSetContents() {
    spyProduct.setContents(null);
    assertNull("Contents", spyProduct.getContents());
    verify(spyProduct, times(1)).setContents(null);
  }

  @Test
  public void testSetDescription() {
    spyProduct.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spyProduct.getDescription());
    verify(spyProduct, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetDiscount() {
    spyProduct.setDiscount(BigDecimal.ZERO);
    assertEquals("Discount", BigDecimal.ZERO, spyProduct.getDiscount());
    verify(spyProduct, times(1)).setDiscount(any());
  }

  @Test
  public void testSetItemHeight() {
    spyProduct.setItemHeight(BigDecimal.ZERO);
    assertEquals("ItemHeight", BigDecimal.ZERO, spyProduct.getItemHeight());
    verify(spyProduct, times(1)).setItemHeight(any());
  }

  @Test
  public void testSetItemHeightUnit() {
    spyProduct.setItemHeightUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemHeightUnit", "Folly words widow one downs few age every seven.", spyProduct.getItemHeightUnit());
    verify(spyProduct, times(1)).setItemHeightUnit(anyString());
  }

  @Test
  public void testSetItemLength() {
    spyProduct.setItemLength(BigDecimal.ZERO);
    assertEquals("ItemLength", BigDecimal.ZERO, spyProduct.getItemLength());
    verify(spyProduct, times(1)).setItemLength(any());
  }

  @Test
  public void testSetItemLengthUnit() {
    spyProduct.setItemLengthUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemLengthUnit", "Folly words widow one downs few age every seven.", spyProduct.getItemLengthUnit());
    verify(spyProduct, times(1)).setItemLengthUnit(anyString());
  }

  @Test
  public void testSetItemUrl() {
    spyProduct.setItemUrl("Folly words widow one downs few age every seven.");
    assertEquals("ItemUrl", "Folly words widow one downs few age every seven.", spyProduct.getItemUrl());
    verify(spyProduct, times(1)).setItemUrl(anyString());
  }

  @Test
  public void testSetItemWeight() {
    spyProduct.setItemWeight(BigDecimal.ZERO);
    assertEquals("ItemWeight", BigDecimal.ZERO, spyProduct.getItemWeight());
    verify(spyProduct, times(1)).setItemWeight(any());
  }

  @Test
  public void testSetItemWeightUnit() {
    spyProduct.setItemWeightUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemWeightUnit", "Folly words widow one downs few age every seven.", spyProduct.getItemWeightUnit());
    verify(spyProduct, times(1)).setItemWeightUnit(anyString());
  }

  @Test
  public void testSetItemWidth() {
    spyProduct.setItemWidth(BigDecimal.ZERO);
    assertEquals("ItemWidth", BigDecimal.ZERO, spyProduct.getItemWidth());
    verify(spyProduct, times(1)).setItemWidth(any());
  }

  @Test
  public void testSetItemWidthUnit() {
    spyProduct.setItemWidthUnit("Folly words widow one downs few age every seven.");
    assertEquals("ItemWidthUnit", "Folly words widow one downs few age every seven.", spyProduct.getItemWidthUnit());
    verify(spyProduct, times(1)).setItemWidthUnit(anyString());
  }

  @Test
  public void testSetLatestCollection() {
    spyProduct.setLatestCollection(Boolean.FALSE);
    assertEquals("LatestCollection", Boolean.FALSE, spyProduct.getLatestCollection());
    verify(spyProduct, times(1)).setLatestCollection(any());
  }

  @Test
  public void testSetName() {
    spyProduct.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spyProduct.getName());
    verify(spyProduct, times(1)).setName(anyString());
  }

  @Test
  public void testSetNumber() {
    spyProduct.setNumber("Folly words widow one downs few age every seven.");
    assertEquals("Number", "Folly words widow one downs few age every seven.", spyProduct.getNumber());
    verify(spyProduct, times(1)).setNumber(anyString());
  }

  @Test
  public void testSetOptions() {
    spyProduct.setOptions(null);
    assertNull("Options", spyProduct.getOptions());
    verify(spyProduct, times(1)).setOptions(null);
  }

  @Test
  public void testSetRating() {
    spyProduct.setRating(Integer.MAX_VALUE);
    assertEquals("Rating", Integer.MAX_VALUE, spyProduct.getRating().intValue());
    verify(spyProduct, times(1)).setRating(any());
  }

  @Test
  public void testSetRecommended() {
    spyProduct.setRecommended(Boolean.FALSE);
    assertEquals("Recommended", Boolean.FALSE, spyProduct.getRecommended());
    verify(spyProduct, times(1)).setRecommended(any());
  }

  @Test
  public void testSetShippingCost() {
    spyProduct.setShippingCost(BigDecimal.ZERO);
    assertEquals("ShippingCost", BigDecimal.ZERO, spyProduct.getShippingCost());
    verify(spyProduct, times(1)).setShippingCost(any());
  }

  @Test
  public void testSetStock() {
    spyProduct.setStock(null);
    assertNull("Stock", spyProduct.getStock());
    verify(spyProduct, times(1)).setStock(null);
  }

  @Test
  public void testSetSubCategories() {
    spyProduct.setSubCategories(null);
    assertNull("SubCategories", spyProduct.getSubCategories());
    verify(spyProduct, times(1)).setSubCategories(null);
  }

  @Test
  public void testSetTax() {
    spyProduct.setTax(BigDecimal.ZERO);
    assertEquals("Tax", BigDecimal.ZERO, spyProduct.getTax());
    verify(spyProduct, times(1)).setTax(any());
  }

  @Test
  public void testToString() {
    assertEquals(new ReflectionToStringBuilder(spyProduct, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP)
        .toString(), spyProduct.toString());
  }
}
