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
import br.com.netbrasoft.gnuob.generic.security.Group;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.generic.security.Site;
import br.com.netbrasoft.gnuob.generic.security.User;
import br.com.netbrasoft.gnuob.generic.security.UserWebServiceImpl;

public class UserWebServiceImplTest {

  private MetaData mockCredentials;
  private Group mockGroup;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Group> mockSecuredGenericGroupsService;
  private ISecuredGenericTypeService<Site> mockSecuredGenericSiteService;
  private ISecuredGenericTypeService<User> mockSecuredGenericUserService;
  private Site mockSite;
  private User spyUser;
  private IGenericTypeWebService<User> userWebServiceImpl = new UserWebServiceImpl<>();

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockPaging = mock(Paging.class);
    mockSite = mock(Site.class);
    mockGroup = mock(Group.class);
    mockSite = mock(Site.class);
    spyUser = spy(User.class);
    spyUser.setGroups(newSet(mockGroup));
    spyUser.setSites(newSet(mockSite));
    mockSecuredGenericSiteService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericGroupsService = mock(ISecuredGenericTypeService.class);
    mockSecuredGenericUserService = mock(ISecuredGenericTypeService.class);
    userWebServiceImpl = new UserWebServiceImpl<>(mockSecuredGenericUserService, mockSecuredGenericGroupsService,
        mockSecuredGenericSiteService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountUserGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericUserService.count(any(), any(), anyVararg())).thenThrow(new RuntimeException());
    userWebServiceImpl.count(mockCredentials, spyUser);
  }

  @Test
  public void testCountUserWithDetachedGroupsAndNoDetachedSites() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(true);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithDetachedGroupsAndSiteIsNull() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(spyUser.getSites()).thenReturn(null);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithDetachedGroupsAndSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(true);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithDetachedSitesAndGroupIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(true);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithDetachedSitesAndNoDetachedGroups() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(false);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithGroupsAndSitesIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(spyUser.getSites()).thenReturn(null);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithNoDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(spyUser.getSites()).thenReturn(null);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithNoDetachedSitesAndGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(false);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test
  public void testCountUserWithNoDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(false);
    assertEquals("Count", 0, userWebServiceImpl.count(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).count(any(), any(), anyVararg());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindUserGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericUserService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    userWebServiceImpl.find(mockCredentials, spyUser);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindUserPagingGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericUserService.find(any(), any(), any(), any(), anyVararg())).thenThrow(new RuntimeException());
    userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE);
  }

  @Test
  public void testFindUserPagingWithDetachedGroupsAndNoDetachedSites() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(true);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithDetachedGroupsAndSiteIsNull() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(spyUser.getSites()).thenReturn(null);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithDetachedGroupsAndSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(true);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithDetachedSitesAndGroupIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(true);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithDetachedSitesAndNoDetachedGroups() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(false);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithGroupsAndSitesIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(spyUser.getSites()).thenReturn(null);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithNoDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(spyUser.getSites()).thenReturn(null);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithNoDetachedSitesAndGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(false);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserPagingWithNoDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(false);
    assertTrue("Find", userWebServiceImpl.find(mockCredentials, spyUser, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test
  public void testFindUserWithDetachedGroupsAndNoDetachedSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithDetachedGroupsAndSiteIsNull() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithDetachedGroupsAndSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithDetachedSitesAndGroupIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithDetachedSitesAndNoDetachedGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithGroupsAndSitesIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithNoDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithNoDetachedSitesAndGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testFindUserWithNoDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Find", userWebServiceImpl.find(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).find(any(), any(), anyLong());
  }

  @Test
  public void testMergedUserWithDetachedGroupsAndSiteIsNull() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergedUserWithDetachedSitesAndGroupIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, times(1)).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergedUserWithGroupsAndSitesIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergedUserWithNoDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergedUserWithNoDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeUserGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericUserService.merge(any(), any())).thenThrow(new RuntimeException());
    userWebServiceImpl.merge(mockCredentials, spyUser);
  }

  @Test
  public void testMergeUserWithDetachedGroupsAndNoDetachedSites() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, times(1)).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeUserWithDetachedGroupsAndSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, times(1)).update(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeUserWithDetachedSitesAndNoDetachedGroups() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).update(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testMergeUserWithNoDetachedSitesAndGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Merge", userWebServiceImpl.merge(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).create(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistUserGettingGNUOpenBusinessServiceException() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSecuredGenericUserService.merge(any(), any())).thenThrow(new RuntimeException());
    userWebServiceImpl.persist(mockCredentials, spyUser);
  }

  @Test
  public void testPersistUserWithDetachedGroupsAndNoDetachedSites() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, times(1)).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).create(any(), any());
    verify(mockSecuredGenericUserService, never()).persist(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistUserWithDetachedGroupsAndSiteIsNull() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, never()).persist(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistUserWithDetachedGroupsAndSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, times(1)).update(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, never()).persist(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistUserWithDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, times(1)).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, never()).persist(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistUserWithDetachedSitesAndNoDetachedGroups() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).update(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, never()).persist(any(), any());
    verify(mockSecuredGenericUserService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistUserWithGroupsAndSitesIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(spyUser.getSites()).thenReturn(null);
    assertNotNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistUserWithNoDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(spyUser.getSites()).thenReturn(null);
    assertNotNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, never()).create(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistUserWithNoDetachedSitesAndGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(false);
    assertNotNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).create(any(), any());
    verify(mockSecuredGenericGroupsService, times(1)).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).persist(any(), any());
  }

  @Test
  public void testPersistUserWithNoDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(false);
    assertNotNull("Persist", userWebServiceImpl.persist(mockCredentials, spyUser));
    verify(mockSecuredGenericSiteService, never()).update(any(), any());
    verify(mockSecuredGenericGroupsService, never()).update(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).create(any(), any());
    verify(mockSecuredGenericGroupsService, never()).create(any(), any());
    verify(mockSecuredGenericUserService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshUserGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericUserService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    userWebServiceImpl.refresh(mockCredentials, spyUser);
  }

  @Test
  public void testRefreshUserWithDetachedGroupsAndNoDetachedSites() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithDetachedGroupsAndSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(true);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithDetachedSitesAndNoDetachedGroups() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, times(1)).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithGroupsAndSitesIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithNoDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(spyUser.getSites()).thenReturn(null);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithNoDetachedSitesAndGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test
  public void testRefreshUserWithNoDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(false);
    assertNull("Refresh", userWebServiceImpl.refresh(mockCredentials, spyUser));
    verify(mockSecuredGenericGroupsService, never()).read(any(), any());
    verify(mockSecuredGenericSiteService, never()).read(any(), any());
    verify(mockSecuredGenericUserService, times(1)).refresh(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRemoveUserGettingGNUOpenBusinessServiceException() {
    when(mockGroup.isDetached()).thenThrow(new RuntimeException());
    userWebServiceImpl.remove(mockCredentials, spyUser);
  }

  @Test
  public void testRemoveUserWithDetachedGroupsAndNoDetachedSites() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(true);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, never()).delete(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithDetachedGroupsAndSites() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(true);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, times(1)).delete(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(spyUser.getSites()).thenReturn(null);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, times(1)).delete(any(), any());
    verify(mockSecuredGenericSiteService, never()).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(true);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, never()).delete(any(), any());
    verify(mockSecuredGenericSiteService, times(1)).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithDetachedSitesAndNoDetachedGroups() {
    when(mockGroup.isDetached()).thenReturn(true);
    when(mockSite.isDetached()).thenReturn(false);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, times(1)).delete(any(), any());
    verify(mockSecuredGenericSiteService, never()).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithGroupsAndSitesIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(spyUser.getSites()).thenReturn(null);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, never()).delete(any(), any());
    verify(mockSecuredGenericSiteService, never()).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithNoDetachedGroupsAndSitesIsNull() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(spyUser.getSites()).thenReturn(null);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, never()).delete(any(), any());
    verify(mockSecuredGenericSiteService, never()).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithNoDetachedSitesAndGroups() {
    when(mockGroup.isDetached()).thenReturn(false);
    when(mockSite.isDetached()).thenReturn(false);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, never()).delete(any(), any());
    verify(mockSecuredGenericSiteService, never()).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }

  @Test
  public void testRemoveUserWithNoDetachedSitesAndGroupsIsNull() {
    when(spyUser.getGroups()).thenReturn(null);
    when(mockSite.isDetached()).thenReturn(false);
    userWebServiceImpl.remove(mockCredentials, spyUser);
    verify(mockSecuredGenericGroupsService, never()).delete(any(), any());
    verify(mockSecuredGenericSiteService, never()).delete(any(), any());
    verify(mockSecuredGenericUserService, times(1)).remove(any(), any());
  }
}
