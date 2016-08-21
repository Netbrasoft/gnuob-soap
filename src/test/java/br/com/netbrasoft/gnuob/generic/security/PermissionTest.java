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

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.security.Permission;
import br.com.netbrasoft.gnuob.generic.security.Rule;

public class PermissionTest {

  private Permission spyPermission;

  @Before
  public void setUp() throws Exception {
    spyPermission = spy(Permission.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetGroup() {
    assertEquals("Group", Rule.READ_ACCESS, spyPermission.getGroup());
    verify(spyPermission, times(1)).getGroup();
  }

  @Test
  public void testGetOthers() {
    assertEquals("Others", Rule.READ_ACCESS, spyPermission.getOthers());
    verify(spyPermission, times(1)).getOthers();
  }

  @Test
  public void testGetOwner() {
    assertEquals("Others", Rule.DELETE_ACCESS, spyPermission.getOwner());
    verify(spyPermission, times(1)).getOwner();
  }

  @Test
  public void testPermissionIsDetached() {
    spyPermission.setId(1L);
    assertTrue("Detached", spyPermission.isDetached());
    verify(spyPermission, times(1)).isDetached();
  }

  @Test
  public void testPermissionIsNotDetached() {
    spyPermission.setId(0L);
    assertFalse("Detached", spyPermission.isDetached());
    verify(spyPermission, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyPermission.prePersist();
    verify(spyPermission, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyPermission.preUpdate();
    verify(spyPermission, times(1)).preUpdate();
  }

  @Test
  public void testSetGroup() {
    spyPermission.setGroup(null);
    assertNotNull("Group", spyPermission.getGroup());
    verify(spyPermission, times(1)).setGroup(null);
  }

  @Test
  public void testSetOthers() {
    spyPermission.setOthers(null);
    assertNotNull("Others", spyPermission.getOthers());
    verify(spyPermission, times(1)).setOthers(null);
  }

  @Test
  public void testSetOwner() {
    spyPermission.setOwner(null);
    assertNotNull("Owner", spyPermission.getOwner());
    verify(spyPermission, times(1)).setOwner(null);
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyPermission, SHORT_PREFIX_STYLE), spyPermission.toString());
  }
}
