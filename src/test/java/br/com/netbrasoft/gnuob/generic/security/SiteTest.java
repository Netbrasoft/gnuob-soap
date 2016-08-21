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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;

public class SiteTest {

  private Site spySite;

  @Before
  public void setUp() throws Exception {
    spySite = spy(Site.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spySite.accept(new ContextVisitorImpl()));
    verify(spySite, times(1)).accept(any());
  }

  @Test
  public void testGetDescription() {
    assertNull("Description", spySite.getDescription());
    verify(spySite, times(1)).getDescription();
  }

  @Test
  public void testGetInstance() {
    final Site instance = Site.getInstance();
    assertNotNull("Instance", instance);
    assertNull("Instance", instance.getName());
    assertNull("Desciption", instance.getDescription());
  }

  @Test
  public void testGetInstanceByName() {
    final Site instance = Site.getInstance("Name");
    assertNotNull("Instance", instance);
    assertEquals("Name", "Name", instance.getName());
    assertNull("Desciption", instance.getDescription());
  }

  @Test
  public void testGetName() {
    assertNull("Name", spySite.getName());
    verify(spySite, times(1)).getName();
  }

  @Test
  public void testPrePersist() {
    spySite.prePersist();
    verify(spySite, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spySite.preUpdate();
    verify(spySite, times(1)).preUpdate();
  }

  @Test
  public void testSetDescription() {
    spySite.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spySite.getDescription());
    verify(spySite, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetName() {
    spySite.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spySite.getName());
    verify(spySite, times(1)).setName(anyString());
  }

  @Test
  public void testSiteIsDetached() {
    spySite.setId(1L);
    assertTrue("Detached", spySite.isDetached());
    verify(spySite, times(1)).isDetached();
  }

  public void testSiteIsNotDetached() {
    spySite.setId(0L);
    assertTrue("Detached", spySite.isDetached());
    verify(spySite, times(1)).isDetached();
  }

  @Test
  public void testToString() {
    assertEquals(
        new ReflectionToStringBuilder(spySite, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP).toString(),
        spySite.toString());
  }
}
