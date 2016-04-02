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

package com.netbrasoft.gnuob.generic.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.netbrasoft.gnuob.generic.content.Content;
import com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;

public class CategoryTest {

  private Content mockContent;
  private SubCategory mockSubCategory;
  private Category spyCategory;

  @Before
  public void setUp() throws Exception {
    mockSubCategory = mock(SubCategory.class);
    mockContent = mock(Content.class);
    spyCategory = spy(Category.class);
    spyCategory.setSubCategories(newSet(mockSubCategory));
    spyCategory.setContents(newSet(mockContent));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyCategory.accept(new ContextVisitorImpl()));
    verify(spyCategory, times(1)).accept(any());
  }

  @Test
  public void testCategoryIsDetached() {
    spyCategory.setId(1L);
    when(mockContent.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    assertTrue("Detached", spyCategory.isDetached());
    verify(spyCategory, times(1)).isDetached();
    verify(mockContent, times(0)).isDetached();
    verify(mockSubCategory, times(0)).isDetached();
  }

  @Test
  public void testCategoryIsDetachedByContents() {
    when(mockContent.isDetached()).thenReturn(true);
    when(mockSubCategory.isDetached()).thenReturn(false);
    spyCategory.setId(0L);
    assertTrue("Detached", spyCategory.isDetached());
    verify(spyCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockSubCategory, times(0)).isDetached();
  }

  @Test
  public void testCategoryIsDetachedBySubCategories() {
    when(mockContent.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(true);
    spyCategory.setId(0L);
    assertTrue("Detached", spyCategory.isDetached());
    verify(spyCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
  }

  @Test
  public void testCategoryIsNotDetached() {
    when(mockContent.isDetached()).thenReturn(false);
    when(mockSubCategory.isDetached()).thenReturn(false);
    spyCategory.setId(0L);
    assertFalse("Detached", spyCategory.isDetached());
    verify(spyCategory, times(1)).isDetached();
    verify(mockContent, times(1)).isDetached();
    verify(mockSubCategory, times(1)).isDetached();
  }

  @Test
  public void testGetContents() {
    assertNotNull("Contents", spyCategory.getContents());
    verify(spyCategory, times(1)).getContents();
  }

  @Test
  public void testGetDescription() {
    assertNull("Description", spyCategory.getDescription());
    verify(spyCategory, times(1)).getDescription();
  }

  @Test
  public void testGetName() {
    assertNull("Name", spyCategory.getName());
    verify(spyCategory, times(1)).getName();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spyCategory.getPosition());
    verify(spyCategory, times(1)).getPosition();
  }

  @Test
  public void testGetSubCategories() {
    assertNotNull("SubCategories", spyCategory.getSubCategories());
    verify(spyCategory, times(1)).getSubCategories();
  }

  @Test
  public void testPrePersist() {
    spyCategory.prePersist();
    assertNotNull("Content position", mockContent.getPosition());
    assertNotNull("SubCategory position", mockSubCategory.getPosition());
    verify(mockContent, times(1)).setPosition(anyInt());
    verify(mockSubCategory, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    spyCategory.preUpdate();
    assertNotNull("Content position", mockContent.getPosition());
    assertNotNull("SubCategory position", mockSubCategory.getPosition());
    verify(mockContent, times(1)).setPosition(anyInt());
    verify(mockSubCategory, times(1)).setPosition(anyInt());
  }

  @Test
  public void testSetContents() {
    final Set<Content> mockContents = newSet(mockContent);
    spyCategory.setContents(mockContents);
    assertEquals("Contents", mockContents, spyCategory.getContents());
    verify(spyCategory, atLeastOnce()).setContents(any());
  }

  @Test
  public void testSetDescription() {
    spyCategory.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spyCategory.getDescription());
    verify(spyCategory, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetName() {
    spyCategory.setName("Holding");
    assertEquals("Name", "Holding", spyCategory.getName());
    verify(spyCategory, times(1)).setName(anyString());
  }

  @Test
  public void testSetPosition() {
    spyCategory.setPosition(Integer.MAX_VALUE);
    assertEquals("Position", Integer.MAX_VALUE, spyCategory.getPosition().intValue());
    verify(spyCategory, times(1)).setPosition(any());
  }

  @Test
  public void testSetSubCategories() {
    final Set<SubCategory> mockSubCategories = newSet(mockSubCategory);
    spyCategory.setSubCategories(mockSubCategories);
    assertEquals("SubCategories", mockSubCategories, spyCategory.getSubCategories());
    verify(spyCategory, atLeastOnce()).setSubCategories(any());
  }
}