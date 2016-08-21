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

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.category.SubCategory;
import br.com.netbrasoft.gnuob.generic.content.Content;

public class SubCategoryTest {

  private Content mockContent;
  private SubCategory mockSubCategory;
  private SubCategory spySubCategory;

  @Before
  public void setUp() throws Exception {
    mockSubCategory = mock(SubCategory.class);
    mockContent = mock(Content.class);
    spySubCategory = spy(SubCategory.class);
    spySubCategory.setSubCategories(newSet(mockSubCategory));
    spySubCategory.setContents(newSet(mockContent));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetContents() {
    assertNotNull("Contents", spySubCategory.getContents());
    verify(spySubCategory, times(1)).getContents();
  }

  @Test
  public void testGetDescription() {
    assertNull("Description", spySubCategory.getDescription());
    verify(spySubCategory, times(1)).getDescription();
  }

  @Test
  public void testGetName() {
    assertNull("Name", spySubCategory.getName());
    verify(spySubCategory, times(1)).getName();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spySubCategory.getPosition());
    verify(spySubCategory, times(1)).getPosition();
  }

  @Test
  public void testGetSubCategories() {
    assertNotNull("SubCategories", spySubCategory.getSubCategories());
    verify(spySubCategory, times(1)).getSubCategories();
  }

  @Test
  public void testPrePersist() {
    spySubCategory.prePersist();
    verify(spySubCategory, times(1)).prePersist();
    verify(mockContent, times(1)).setPosition(anyInt());
    verify(mockSubCategory, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    spySubCategory.preUpdate();
    verify(spySubCategory, times(1)).preUpdate();
  }

  @Test
  public void testSetContents() {
    spySubCategory.setContents(null);
    assertNull("Contents", spySubCategory.getContents());
    verify(spySubCategory, times(1)).setContents(null);
  }

  @Test
  public void testSetDescription() {
    spySubCategory.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spySubCategory.getDescription());
    verify(spySubCategory, times(1)).setDescription(any());
  }

  @Test
  public void testSetName() {
    spySubCategory.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spySubCategory.getName());
    verify(spySubCategory, times(1)).setName(any());
  }

  @Test
  public void testSetPosition() {
    spySubCategory.setPosition(Integer.MAX_VALUE);
    assertEquals("Position", Integer.MAX_VALUE, spySubCategory.getPosition().intValue());
    verify(spySubCategory, times(1)).setPosition(any());
  }

  @Test
  public void testSetSubCategories() {
    spySubCategory.setSubCategories(null);
    assertNull("SubCategories", spySubCategory.getSubCategories());
    verify(spySubCategory, atLeastOnce()).setSubCategories(null);
  }

  @Test
  public void testSubCategoryIsDetached() {
    spySubCategory.setId(1L);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    assertTrue("Detached", spySubCategory.isDetached());
    verify(spySubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
  }

  @Test
  public void testSubCategoryIsDetachedByContents() {
    spySubCategory.setId(0L);
    when(mockContent.isDetached()).thenReturn(true);
    when(mockSubCategory.isDetached()).thenReturn(false);
    assertTrue("Detached", spySubCategory.isDetached());
    verify(spySubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
  }

  @Test
  public void testSubCategoryIsDetachedByhSubCategories() {
    spySubCategory.setId(0L);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(true);
    assertTrue("Detached", spySubCategory.isDetached());
    verify(spySubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
  }

  @Test
  public void testSubCategoryIsNotDetached() {
    spySubCategory.setId(0L);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    assertFalse("Detached", spySubCategory.isDetached());
    verify(spySubCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spySubCategory, SHORT_PREFIX_STYLE), spySubCategory.toString());
  }
}
