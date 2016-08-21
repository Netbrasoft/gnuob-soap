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

package br.com.netbrasoft.gnuob.generic.category;

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
import br.com.netbrasoft.gnuob.generic.category.Category;
import br.com.netbrasoft.gnuob.generic.category.CategoryWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.category.SubCategory;
import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class CategoryWebServiceImplTest {

  private IGenericTypeWebService<Category> categoryWebServiceImpl = new CategoryWebServiceImpl<>();
  private Content mockContent;
  private MetaData mockCredentials;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Category> mockSecuredGenericCategoryService;
  private ISecuredGenericTypeService<Content> mockSecuredGenericContentService;
  private SubCategory mockSubCategory;
  private Category spyCategory;


  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockSubCategory = mock(SubCategory.class);
    mockContent = mock(Content.class);
    mockPaging = mock(Paging.class);
    spyCategory = spy(Category.class);
    spyCategory.setSubCategories(newSet(mockSubCategory));
    spyCategory.setContents(newSet(mockContent));
    mockSecuredGenericCategoryService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericContentService = mock(ISecuredGenericTypeService.class);
    categoryWebServiceImpl =
        new CategoryWebServiceImpl<>(mockSecuredGenericCategoryService, mockSecuredGenericContentService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountCategoryGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCategoryService.count(any(), any(), anyVararg())).thenThrow(new RuntimeException());
    categoryWebServiceImpl.count(mockCredentials, spyCategory);
  }

  @Test
  public void testCountCategoryWithContentsIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    assertEquals("Count", 0, categoryWebServiceImpl.count(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountCategoryWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertEquals("Count", 0, categoryWebServiceImpl.count(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountCategoryWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertEquals("Count", 0, categoryWebServiceImpl.count(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountCategoryWithEmptyContents() {
    when(spyCategory.getContents()).thenReturn(newSet());
    assertEquals("Count", 0, categoryWebServiceImpl.count(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountCategoryWithEmptySubcategories() {
    when(spyCategory.getSubCategories()).thenReturn(newSet());
    assertEquals("Count", 0, categoryWebServiceImpl.count(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).count(any(), any());
  }

  @Test
  public void testCountCategoryWithSubCategories() {
    assertEquals("Count", 0, categoryWebServiceImpl.count(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountCategoryWithSubCategoriesIsNull() {
    when(spyCategory.getSubCategories()).thenReturn(null);
    assertEquals("Count", 0, categoryWebServiceImpl.count(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).count(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDeleteCategoryGettingGNUOpenBusinessServiceException() {
    when(mockContent.isDetached()).thenReturn(true);
    when(spyCategory.getContents()).thenThrow(new RuntimeException());
    categoryWebServiceImpl.remove(mockCredentials, spyCategory);
  }

  @Test
  public void testDeleteCategoryWithContentsAndSubCategoriesIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    when(spyCategory.getSubCategories()).thenReturn(null);
    categoryWebServiceImpl.remove(mockCredentials, spyCategory);
    verify(mockSecuredGenericContentService, never()).delete(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteCategoryWithContentsIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    categoryWebServiceImpl.remove(mockCredentials, spyCategory);
    verify(mockSecuredGenericContentService, never()).delete(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteCategoryWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    categoryWebServiceImpl.remove(mockCredentials, spyCategory);
    verify(mockSecuredGenericContentService, times(1)).delete(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).remove(any(), any());
  }

  @Test
  public void testDeleteCategoryWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    categoryWebServiceImpl.remove(mockCredentials, spyCategory);
    verify(mockSecuredGenericContentService, never()).delete(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).remove(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindCategoryGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCategoryService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    categoryWebServiceImpl.find(mockCredentials, spyCategory);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindCategoryPagingGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCategoryService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE);
  }

  @Test
  public void testFindCategoryPagingWithContentsIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    assertTrue("Find",
        categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindCategoryPagingWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertTrue("Find",
        categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindCategoryPagingWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertTrue("Find",
        categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindCategoryPagingWithEmptyContents() {
    when(spyCategory.getContents()).thenReturn(newSet());
    assertTrue("Find",
        categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindCategoryPagingWithEmptySubCategories() {
    when(spyCategory.getSubCategories()).thenReturn(newSet());
    assertTrue("Find",
        categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindCategoryPagingWithSubCategories() {
    assertTrue("Find",
        categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindCategoryPagingWithSubCategoriesIsNull() {
    when(spyCategory.getSubCategories()).thenReturn(null);
    assertTrue("Find",
        categoryWebServiceImpl.find(mockCredentials, spyCategory, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindCategoryWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Find", categoryWebServiceImpl.find(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindCategoryWithEmptySubCategories() {
    when(spyCategory.getSubCategories()).thenReturn(newSet());
    assertNull("Find", categoryWebServiceImpl.find(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindCategoryWithEmpyContents() {
    when(spyCategory.getContents()).thenReturn(newSet());
    assertNull("Find", categoryWebServiceImpl.find(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindCategoryWithSubCategories() {
    assertNull("Find", categoryWebServiceImpl.find(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).find(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeCategoryGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCategoryService.merge(any(), any())).thenThrow(new RuntimeException());
    categoryWebServiceImpl.merge(mockCredentials, spyCategory);
  }

  @Test
  public void testMergeCategoryWithContentsAndSubCategoriesIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    when(spyCategory.getSubCategories()).thenReturn(null);
    assertNull("Merge", categoryWebServiceImpl.merge(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeCategoryWithContentsIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    assertNull("Merge", categoryWebServiceImpl.merge(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeCategoryWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Merge", categoryWebServiceImpl.merge(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, times(1)).update(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeCategoryWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertNull("Merge", categoryWebServiceImpl.merge(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, times(1)).create(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistCategoryGettingGNUOpenBusinessServiceException() {
    when(mockContent.isDetached()).thenReturn(true);
    when(mockSecuredGenericCategoryService.merge(any(), any())).thenThrow(new RuntimeException());
    categoryWebServiceImpl.persist(mockCredentials, spyCategory);
  }

  @Test
  public void testPersistCategoryWithContentsAndSubCategoriesIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    when(spyCategory.getSubCategories()).thenReturn(null);
    assertNotNull("Persist", categoryWebServiceImpl.persist(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistCategoryWithContentsIsNull() {
    when(spyCategory.getContents()).thenReturn(null);
    assertNotNull("Persist", categoryWebServiceImpl.persist(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, never()).create(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistCategoryWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Persist", categoryWebServiceImpl.persist(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, times(1)).update(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistCategoryWithNoDetachedContents() {
    when(mockContent.isDetached()).thenReturn(false);
    assertNotNull("Persist", categoryWebServiceImpl.persist(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, times(1)).create(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshCategoryGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericCategoryService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    categoryWebServiceImpl.refresh(mockCredentials, spyCategory);
  }

  @Test
  public void testRefreshCategoryWithDetachedContents() {
    when(mockContent.isDetached()).thenReturn(true);
    assertNull("Refresh", categoryWebServiceImpl.refresh(mockCredentials, spyCategory));
    verify(mockSecuredGenericContentService, times(1)).read(any(), any());
    verify(mockSecuredGenericCategoryService, times(1)).refresh(any(), any(), anyLong());
  }
}
