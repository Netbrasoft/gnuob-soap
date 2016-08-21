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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractAccessTest {

  private Permission mockPermission;
  private User mockUser;
  private Group mockGroup;
  private Site mockSite;
  private AbstractAccess spyAbstractAccess;

  @Before
  public void setUp() throws Exception {
    mockPermission = mock(Permission.class);
    mockUser = mock(User.class);
    mockGroup = mock(Group.class);
    mockSite = mock(Site.class);
    spyAbstractAccess = spy(AbstractAccess.class);
    spyAbstractAccess.setPermission(mockPermission);
    spyAbstractAccess.setOwner(mockUser);
    spyAbstractAccess.setSite(mockSite);
    spyAbstractAccess.setGroup(mockGroup);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetActive() {
    assertNotNull("Active", spyAbstractAccess.getActive());
    verify(spyAbstractAccess, times(1)).getActive();
  }

  @Test
  public void testGetGroup() {
    assertNotNull("Group", spyAbstractAccess.getGroup());
    verify(spyAbstractAccess, times(1)).getGroup();
  }

  @Test
  public void testGetOwner() {
    assertNotNull("Owner", spyAbstractAccess.getOwner());
    verify(spyAbstractAccess, times(1)).getOwner();
  }

  @Test
  public void testGetPermission() {
    assertNotNull("Permission", spyAbstractAccess.getPermission());
    verify(spyAbstractAccess, times(1)).getPermission();
  }

  @Test
  public void testGetSite() {
    assertNotNull("Site", spyAbstractAccess.getSite());
    verify(spyAbstractAccess, times(1)).getSite();
  }

  @Test
  public void testSetActive() {
    spyAbstractAccess.setActive(null);
    assertNull("Active", spyAbstractAccess.getActive());
    verify(spyAbstractAccess, times(1)).setActive(null);
  }

  @Test
  public void testSetGroup() {
    spyAbstractAccess.setGroup(null);
    assertNull("Group", spyAbstractAccess.getGroup());
    verify(spyAbstractAccess, times(1)).setGroup(null);
  }

  @Test
  public void testSetOwner() {
    spyAbstractAccess.setOwner(null);
    assertNull("Owner", spyAbstractAccess.getOwner());
    verify(spyAbstractAccess, times(1)).setOwner(null);
  }

  @Test
  public void testSetPermission() {
    spyAbstractAccess.setPermission(null);
    assertNull("Permission", spyAbstractAccess.getPermission());
    verify(spyAbstractAccess, times(1)).setPermission(null);
  }

  @Test
  public void testSetSite() {
    spyAbstractAccess.setSite(null);
    assertNull("Site", spyAbstractAccess.getSite());
    verify(spyAbstractAccess, times(1)).setSite(null);
  }

  @Test
  public void testToString() {
    assertEquals(new ReflectionToStringBuilder(spyAbstractAccess, SHORT_PREFIX_STYLE)
        .setExcludeFieldNames(SITE, USER, GROUP).toString(), spyAbstractAccess.toString());
  }
}
