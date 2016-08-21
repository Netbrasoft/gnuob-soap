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
package br.com.netbrasoft.gnuob.generic.security;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.security.Group;
import br.com.netbrasoft.gnuob.generic.security.GroupWebServiceImpl;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;


public class GroupWebServiceImplTest {

  private IGenericTypeWebService<Group> groupWebServiceImpl = new GroupWebServiceImpl<>();
  private MetaData mockCredentials;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Group> mockSecuredGenericGroupService;
  private Group spyGroup;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockPaging = mock(Paging.class);
    spyGroup = spy(Group.class);
    mockSecuredGenericGroupService = mock(ISecuredGenericTypeService.class);
    groupWebServiceImpl = new GroupWebServiceImpl<Group>(mockSecuredGenericGroupService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testCountGroup() {
    assertEquals("Count", 0, groupWebServiceImpl.count(mockCredentials, spyGroup));
    verify(mockSecuredGenericGroupService, times(1)).count(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountGroupGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericGroupService.count(any(), any())).thenThrow(new RuntimeException());
    groupWebServiceImpl.count(mockCredentials, spyGroup);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDeleteCategoryGettingGNUOpenBusinessServiceException() {
    groupWebServiceImpl = new GroupWebServiceImpl<>();
    groupWebServiceImpl.remove(mockCredentials, spyGroup);
  }

  @Test
  public void testDeleteCategoryWithNullGroupsAndSubCategories() {
    groupWebServiceImpl.remove(mockCredentials, spyGroup);
    verify(mockSecuredGenericGroupService, times(1)).remove(any(), any());
  }

  @Test
  public void testFindGroup() {
    assertNull("Find", groupWebServiceImpl.find(mockCredentials, spyGroup));
    verify(mockSecuredGenericGroupService, times(1)).find(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindGroupGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericGroupService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    groupWebServiceImpl.find(mockCredentials, spyGroup);
  }

  @Test
  public void testFindGroupPaging() {
    assertTrue("Find", groupWebServiceImpl.find(mockCredentials, spyGroup, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindGroupPagingGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericGroupService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    groupWebServiceImpl.find(mockCredentials, spyGroup, mockPaging, OrderByEnum.NONE);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeGroupGettingGNUOpenBusinessServiceException() {
    when(spyGroup.isDetached()).thenReturn(true);
    when(mockSecuredGenericGroupService.merge(any(), any())).thenThrow(new RuntimeException());
    groupWebServiceImpl.merge(mockCredentials, spyGroup);
  }

  @Test
  public void testMergeWithNoDetachedGroups() {
    when(spyGroup.isDetached()).thenReturn(false);
    assertNull("Merge", groupWebServiceImpl.merge(mockCredentials, spyGroup));
    verify(mockSecuredGenericGroupService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistCategoryGettingGNUOpenBusinessServiceException() {
    when(spyGroup.isDetached()).thenReturn(true);
    when(mockSecuredGenericGroupService.merge(any(), any())).thenThrow(new RuntimeException());
    groupWebServiceImpl.persist(mockCredentials, spyGroup);
  }

  @Test
  public void testPersistWithDetachedGroups() {
    when(spyGroup.isDetached()).thenReturn(true);
    assertNull("Persist", groupWebServiceImpl.persist(mockCredentials, spyGroup));
    verify(mockSecuredGenericGroupService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistWithNoDetachedGroups() {
    when(spyGroup.isDetached()).thenReturn(false);
    assertNotNull("Persist", groupWebServiceImpl.persist(mockCredentials, spyGroup));
    verify(mockSecuredGenericGroupService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshCategoryGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericGroupService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    groupWebServiceImpl.refresh(mockCredentials, spyGroup);
  }

  @Test
  public void testRefreshCategoryWithDetachedGroups() {
    assertNull("Refresh", groupWebServiceImpl.refresh(mockCredentials, spyGroup));
    verify(mockSecuredGenericGroupService, times(1)).refresh(any(), any(), anyLong());
  }
}
