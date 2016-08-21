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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;

public class GroupTest {

  private Group spyGroup;

  @Before
  public void setUp() throws Exception {
    spyGroup = spy(Group.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGroupIsDetached() {
    spyGroup.setId(1L);
    assertTrue("Detached", spyGroup.isDetached());
    verify(spyGroup, times(1)).isDetached();
  }

  public void testGroupIsNotDetached() {
    spyGroup.setId(0L);
    assertTrue("Detached", spyGroup.isDetached());
    verify(spyGroup, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spyGroup.prePersist();
    verify(spyGroup, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyGroup.preUpdate();
    verify(spyGroup, times(1)).preUpdate();
  }

  @Test
  public void testAccept() {
    assertNotNull("Context", spyGroup.accept(new ContextVisitorImpl()));
    verify(spyGroup, times(1)).accept(any());
  }

  @Test
  public void testGetDescription() {
    assertNull("Description", spyGroup.getDescription());
    verify(spyGroup, times(1)).getDescription();
  }

  @Test
  public void testGetName() {
    assertNull("Name", spyGroup.getName());
    verify(spyGroup, times(1)).getName();
  }

  @Test
  public void testSetDescription() {
    spyGroup.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spyGroup.getDescription());
    verify(spyGroup, times(1)).setDescription(any());
  }

  @Test
  public void testSetName() {
    spyGroup.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spyGroup.getName());
    verify(spyGroup, times(1)).setName(any());
  }

  @Test
  public void testToString() {
    assertEquals(
        new ReflectionToStringBuilder(spyGroup, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP).toString(),
        spyGroup.toString());
  }
}
