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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;

public class UserTest {

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();
  private Group mockGroup;
  private Site mockSite;

  private User spyUser;

  @Before
  public void setUp() throws Exception {
    mockSite = mock(Site.class);
    mockGroup = mock(Group.class);
    spyUser = spy(User.class);
    spyUser.setSites(newSet(mockSite));
    spyUser.setGroups(newSet(mockGroup));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyUser.accept(new ContextVisitorImpl()));
    verify(spyUser, times(1)).accept(any());
  }

  @Test
  public void testGetAccess() {
    assertNull("Access", spyUser.getAccess());
    verify(spyUser, times(1)).getAccess();
  }

  @Test
  public void testGetDescription() {
    assertNull("Description", spyUser.getDescription());
    verify(spyUser, times(1)).getDescription();
  }

  @Test
  public void testGetGroups() {
    assertNotNull("Groups", spyUser.getGroups());
    verify(spyUser, times(1)).getGroups();
  }

  @Test
  public void testGetInstance() {
    final User instance = User.getInstance();
    assertNotNull("Instance", instance);
    assertNull("Access", instance.getAccess());
    assertNull("Name", instance.getName());
    assertNull("Password", instance.getPassword());
    assertNotNull("Groups", instance.getGroups());
    assertNotNull("Roles", instance.getRoles());
    assertNull("Root", instance.getRoot());
    assertNotNull("Sites", instance.getSites());
  }

  @Test
  public void testGetInstanceByName() {
    final User instance = User.getInstance("Name");
    assertNotNull("Instance", instance);
    assertNull("Access", instance.getAccess());
    assertEquals("Name", "Name", instance.getName());
    assertNull("Password", instance.getPassword());
    assertNotNull("Groups", instance.getGroups());
    assertNotNull("Roles", instance.getRoles());
    assertNull("Root", instance.getRoot());
    assertNotNull("Sites", instance.getSites());
  }

  @Test
  public void testGetName() {
    assertNull("Name", spyUser.getName());
    verify(spyUser, times(1)).getName();
  }

  @Test
  public void testGetPassword() {
    assertNull("Password", spyUser.getPassword());
    verify(spyUser, times(1)).getPassword();
  }

  @Test
  public void testGetRoles() {
    assertNotNull("Roles", spyUser.getRoles());
    verify(spyUser, times(1)).getRoles();
  }

  @Test
  public void testGetRoot() {
    assertNull("Root", spyUser.getRoot());
    verify(spyUser, times(1)).getRoot();
  }

  @Test
  public void testGetSites() {
    assertNotNull("Sites", spyUser.getSites());
    verify(spyUser, times(1)).getSites();
  }

  @Test
  public void testGroupIsNotDetached() {
    spyUser.setId(0L);
    when(mockSite.isDetached()).thenReturn(false);
    when(mockGroup.isDetached()).thenReturn(false);
    assertFalse("Detached", spyUser.isDetached());
    verify(spyUser, times(1)).isDetached();
    verify(mockSite, times(1)).isDetached();
    verify(mockGroup, times(1)).isDetached();
  }

  @Test
  public void testGroupIsNotDetachedWithGroupIsNull() {
    spyUser.setId(0L);
    spyUser.setGroups(null);
    when(mockSite.isDetached()).thenReturn(false);
    assertFalse("Detached", spyUser.isDetached());
    verify(spyUser, times(1)).isDetached();
    verify(mockSite, times(1)).isDetached();
    verify(mockGroup, never()).isDetached();
  }

  @Test
  public void testGroupIsNotDetachedWithSiteIsNull() {
    spyUser.setId(0L);
    spyUser.setSites(null);
    when(mockGroup.isDetached()).thenReturn(false);
    assertFalse("Detached", spyUser.isDetached());
    verify(spyUser, times(1)).isDetached();
    verify(mockSite, never()).isDetached();
    verify(mockGroup, times(1)).isDetached();
  }

  @Test
  public void testPrePersistWithCorrectPassword() {
    spyUser.setPassword("F1E29598E01173FF:1000:260846CA84D462CCB5F93C76B1241B2CAB89BB16");
    spyUser.prePersist();
    verify(spyUser, times(1)).prePersist();
  }

  @Test
  public void testPrePersistWithWrongPassword() {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException
        .expectMessage("Given user [null] doesn't contain a valid password, verify that the given password is valid");
    spyUser.setPassword("WrongPassword");
    spyUser.prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyUser.setPassword("F1E29598E01173FF:1000:260846CA84D462CCB5F93C76B1241B2CAB89BB16");
    spyUser.preUpdate();
    verify(spyUser, times(1)).preUpdate();
    verify(spyUser, times(1)).prePersist();
  }

  @Test
  public void testSetAccess() {
    spyUser.setAccess(null);
    assertNull("Description", spyUser.getAccess());
    verify(spyUser, times(1)).setAccess(null);
  }

  @Test
  public void testSetDescription() {
    spyUser.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spyUser.getDescription());
    verify(spyUser, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetGroups() {
    spyUser.setGroups(null);
    assertNull("Groups", spyUser.getGroups());
    verify(spyUser, times(1)).setGroups(null);
  }

  @Test
  public void testSetName() {
    spyUser.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spyUser.getName());
    verify(spyUser, times(1)).setName(anyString());
  }

  @Test
  public void testSetPassword() {
    spyUser.setPassword("Folly words widow one downs few age every seven.");
    assertEquals("Password", "Folly words widow one downs few age every seven.", spyUser.getPassword());
    verify(spyUser, times(1)).setPassword(anyString());
  }

  @Test
  public void testSetRoles() {
    spyUser.setRoles(null);
    assertNull("Roles", spyUser.getRoles());
    verify(spyUser, times(1)).setRoles(null);
  }

  @Test
  public void testSetRoot() {
    spyUser.setRoot(null);
    assertNull("Root", spyUser.getRoot());
    verify(spyUser, times(1)).setRoot(null);
  }

  @Test
  public void testSetSites() {
    spyUser.setSites(null);
    assertNull("Sites", spyUser.getSites());
    verify(spyUser, times(1)).setSites(null);
  }

  @Test
  public void testUserIsDetached() {
    spyUser.setId(1L);
    when(mockSite.isDetached()).thenReturn(false);
    when(mockGroup.isDetached()).thenReturn(false);
    assertTrue("Detached", spyUser.isDetached());
    verify(spyUser, times(1)).isDetached();
    verify(mockSite, times(1)).isDetached();
    verify(mockGroup, times(1)).isDetached();
  }

  @Test
  public void testUserIsDetachedByGroup() {
    spyUser.setId(0L);
    when(mockSite.isDetached()).thenReturn(false);
    when(mockGroup.isDetached()).thenReturn(true);
    assertTrue("Detached", spyUser.isDetached());
    verify(spyUser, times(1)).isDetached();
    verify(mockSite, times(1)).isDetached();
    verify(mockGroup, times(1)).isDetached();
  }

  @Test
  public void testUserIsDetachedBySite() {
    spyUser.setId(0L);
    when(mockSite.isDetached()).thenReturn(true);
    when(mockGroup.isDetached()).thenReturn(false);
    assertTrue("Detached", spyUser.isDetached());
    verify(spyUser, times(1)).isDetached();
    verify(mockSite, times(1)).isDetached();
    verify(mockGroup, times(1)).isDetached();
  }

  @Test
  public void testToString() {
    assertEquals(
        new ReflectionToStringBuilder(spyUser, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP).toString(),
        spyUser.toString());
  }
}
