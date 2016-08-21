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

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.product.Product;
import br.com.netbrasoft.gnuob.generic.product.Stock;

public class StockTest {

  private Stock spyStock;
  private Product mockProduct;

  @Before
  public void setUp() throws Exception {
    mockProduct = mock(Product.class);
    spyStock = spy(Stock.class);
    spyStock.setProduct(mockProduct);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetMaxQuantity() {
    assertNull("MaxQuantity", spyStock.getMaxQuantity());
    verify(spyStock, times(1)).getMaxQuantity();
  }

  @Test
  public void testGetMinQuantity() {
    assertNull("MaxQuantity", spyStock.getMinQuantity());
    verify(spyStock, times(1)).getMinQuantity();
  }

  @Test
  public void testGetProduct() {
    assertNotNull("Product", spyStock.getProduct());
    verify(spyStock, times(1)).getProduct();
  }

  @Test
  public void testGetQuantity() {
    assertNull("Quantity", spyStock.getQuantity());
    verify(spyStock, times(1)).getQuantity();
  }

  @Test
  public void testStockIsDetached() {
    spyStock.setId(1L);
    assertTrue("Detached", spyStock.isDetached());
    verify(spyStock, times(1)).isDetached();
  }

  @Test
  public void testStockIsNotDetached() {
    spyStock.setId(0L);
    assertFalse("Detached", spyStock.isDetached());
    verify(spyStock, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyStock.prePersist();
    verify(spyStock, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyStock.preUpdate();
    verify(spyStock, times(1)).preUpdate();
  }

  @Test
  public void testSetMaxQuantity() {
    spyStock.setMaxQuantity(BigInteger.ZERO);
    assertEquals("MaxQuantity", BigInteger.ZERO, spyStock.getMaxQuantity());
    verify(spyStock, times(1)).setMaxQuantity(any());
  }

  @Test
  public void testSetMinQuantity() {
    spyStock.setMinQuantity(BigInteger.ZERO);
    assertEquals("MinQuantity", BigInteger.ZERO, spyStock.getMinQuantity());
    verify(spyStock, times(1)).setMinQuantity(any());
  }

  @Test
  public void testSetProduct() {
    spyStock.setProduct(null);
    assertNull("Product", spyStock.getProduct());
    verify(spyStock, times(1)).setProduct(null);
  }

  @Test
  public void testSetQuantity() {
    spyStock.setQuantity(BigInteger.ZERO);
    assertEquals("Quantity", BigInteger.ZERO, spyStock.getQuantity());
    verify(spyStock, times(1)).setQuantity(any());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyStock, SHORT_PREFIX_STYLE), spyStock.toString());
  }
}
