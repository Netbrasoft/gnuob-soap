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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.category.SubCategory;
import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.product.Option;
import br.com.netbrasoft.gnuob.generic.product.Product;
import br.com.netbrasoft.gnuob.generic.product.ProductWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.product.Stock;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class ProductWebServiceImplTest {

  private Content mockContent;
  private MetaData mockCredentials;
  private Option mockOption;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Content> mockSecuredGenericContentService;
  private ISecuredGenericTypeService<Product> mockSecuredGenericProductService;
  private Stock mockStock;
  private SubCategory mockSubCategory;
  private IGenericTypeWebService<Product> productWebServiceImpl = new ProductWebServiceImpl<Product>();
  private Product spyProduct;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockContent = mock(Content.class);
    mockOption = mock(Option.class);
    mockStock = mock(Stock.class);
    mockContent = mock(Content.class);
    mockSubCategory = mock(SubCategory.class);
    mockPaging = mock(Paging.class);
    spyProduct = spy(Product.class);
    spyProduct.setContents(newSet(mockContent));
    spyProduct.setOptions(newSet(mockOption));
    spyProduct.setSubCategories(newSet(mockSubCategory));
    spyProduct.setStock(mockStock);
    mockSecuredGenericProductService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericContentService = mock(ISecuredGenericTypeService.class);
    productWebServiceImpl =
        new ProductWebServiceImpl<Product>(mockSecuredGenericProductService, mockSecuredGenericContentService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountProductGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericProductService.count(any(), any(), anyVararg())).thenThrow(new RuntimeException());
    productWebServiceImpl.count(mockCredentials, spyProduct);
  }

  @Test
  public void testCountProductWithContentsIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    assertEquals("Count", 0, productWebServiceImpl.count(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountProductWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertEquals("Count", 0, productWebServiceImpl.count(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountProductWithEmptyContents() {
    when(spyProduct.getContents()).thenReturn(newSet());
    assertEquals("Count", 0, productWebServiceImpl.count(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountProductWithEmptySubcategories() {
    when(spyProduct.getSubCategories()).thenReturn(newSet());
    assertEquals("Count", 0, productWebServiceImpl.count(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).count(any(), any());
  }

  @Test
  public void testCountProductWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertEquals("Count", 0, productWebServiceImpl.count(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountProductWithSubCategories() {
    assertEquals("Count", 0, productWebServiceImpl.count(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountProductWithSubCategoriesIsNull() {
    when(spyProduct.getSubCategories()).thenReturn(null);
    assertEquals("Count", 0, productWebServiceImpl.count(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).count(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDeleteProductGettingGNUOpenBusinessServiceException() {
    when(mockContent.isDetached()).thenReturn(true);
    when(spyProduct.getContents()).thenThrow(new RuntimeException());
    productWebServiceImpl.remove(mockCredentials, spyProduct);
  }

  @Test
  public void testDeleteProductWithContentsAndSubCategoriesIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    when(spyProduct.getSubCategories()).thenReturn(null);
    productWebServiceImpl.remove(mockCredentials, spyProduct);
    verify(mockSecuredGenericContentService, never()).delete(any(), any());
    verify(mockSecuredGenericProductService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteProductWithContentsIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    productWebServiceImpl.remove(mockCredentials, spyProduct);
    verify(mockSecuredGenericContentService, never()).delete(any(), any());
    verify(mockSecuredGenericProductService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteProductWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    productWebServiceImpl.remove(mockCredentials, spyProduct);
    verify(mockSecuredGenericContentService, times(1)).delete(any(), any());
    verify(mockSecuredGenericProductService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteProductWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    productWebServiceImpl.remove(mockCredentials, spyProduct);
    verify(mockSecuredGenericContentService, never()).delete(any(), any());
    verify(mockSecuredGenericProductService, times(1)).remove(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindProductGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericProductService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    productWebServiceImpl.find(mockCredentials, spyProduct);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindProductPagingGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericProductService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE);
  }

  @Test
  public void testFindProductPagingWithContentsIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    assertTrue("Find", productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindProductPagingWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertTrue("Find", productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindProductPagingWithEmptyContents() {
    when(spyProduct.getContents()).thenReturn(newSet());
    assertTrue("Find", productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindProductPagingWithEmptySubCategories() {
    when(spyProduct.getSubCategories()).thenReturn(newSet());
    assertTrue("Find", productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindProductPagingWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertTrue("Find", productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindProductPagingWithSubCategories() {
    assertTrue("Find", productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindProductPagingWithSubCategoriesIsNull() {
    when(spyProduct.getSubCategories()).thenReturn(null);
    assertTrue("Find", productWebServiceImpl.find(mockCredentials, spyProduct, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindProductWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Find", productWebServiceImpl.find(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindProductWithEmptySubCategories() {
    when(spyProduct.getSubCategories()).thenReturn(newSet());
    assertNull("Find", productWebServiceImpl.find(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindProductWithEmpyContents() {
    when(spyProduct.getContents()).thenReturn(newSet());
    assertNull("Find", productWebServiceImpl.find(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindProductWithSubCategories() {
    assertNull("Find", productWebServiceImpl.find(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).find(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeProductGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericProductService.merge(any(), any())).thenThrow(new RuntimeException());
    productWebServiceImpl.merge(mockCredentials, spyProduct);
  }

  @Test
  public void testMergeProductWithContentsAndSubCategoriesIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    when(spyProduct.getSubCategories()).thenReturn(null);
    assertNull("Merge", productWebServiceImpl.merge(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericProductService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeProductWithContentsIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    assertNull("Merge", productWebServiceImpl.merge(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericProductService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeProductWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Merge", productWebServiceImpl.merge(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, times(1)).update(any(), any());
    verify(mockSecuredGenericProductService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeProductWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertNull("Merge", productWebServiceImpl.merge(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, times(1)).create(any(), any());
    verify(mockSecuredGenericProductService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistProductGettingGNUOpenBusinessServiceException() {
    when(mockContent.isDetached()).thenReturn(true);
    when(mockSecuredGenericProductService.merge(any(), any())).thenThrow(new RuntimeException());
    productWebServiceImpl.persist(mockCredentials, spyProduct);
  }

  @Test
  public void testPersistProductWithContentsAndSubCategoriesIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    when(spyProduct.getSubCategories()).thenReturn(null);
    assertNotNull("Persist", productWebServiceImpl.persist(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericProductService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistProductWithContentsIsNull() {
    when(spyProduct.getContents()).thenReturn(null);
    assertNotNull("Persist", productWebServiceImpl.persist(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericProductService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistProductWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Persist", productWebServiceImpl.persist(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, times(1)).update(any(), any());
    verify(mockSecuredGenericProductService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistProductWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertNotNull("Persist", productWebServiceImpl.persist(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, times(1)).create(any(), any());
    verify(mockSecuredGenericProductService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshProductGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericProductService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    productWebServiceImpl.refresh(mockCredentials, spyProduct);
  }

  @Test
  public void testRefreshProductWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Refresh", productWebServiceImpl.refresh(mockCredentials, spyProduct));
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericProductService, times(1)).refresh(any(), any(), anyLong());
  }
}
