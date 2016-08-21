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

package br.com.netbrasoft.gnuob.generic.setting;

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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;

public class SettingTest {

  private Setting spySetting;

  @Before
  public void setUp() throws Exception {
    spySetting = spy(Setting.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testSettingIsDetached() {
    spySetting.setId(1L);
    assertTrue("Detached", spySetting.isDetached());
    verify(spySetting, times(1)).isDetached();
  }

  @Test
  public void testSettingIsNotDetached() {
    spySetting.setId(0L);
    assertFalse("Detached", spySetting.isDetached());
    verify(spySetting, times(1)).isDetached();
  }

  @Test
  public void testPrePersist() {
    spySetting.prePersist();
    verify(spySetting, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spySetting.preUpdate();
    verify(spySetting, times(1)).preUpdate();
  }

  @Test
  public void testAccept() {
    assertNotNull("Context", spySetting.accept(new ContextVisitorImpl()));
    verify(spySetting, times(1)).accept(any());
  }

  @Test
  public void testGetDescription() {
    assertNull("Description", spySetting.getDescription());
    verify(spySetting, times(1)).getDescription();
  }

  @Test
  public void testGetProperty() {
    assertNull("Property", spySetting.getProperty());
    verify(spySetting, times(1)).getProperty();
  }

  @Test
  public void testGetValue() {
    assertNull("Value", spySetting.getValue());
    verify(spySetting, times(1)).getValue();
  }

  @Test
  public void testSetDescription() {
    spySetting.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spySetting.getDescription());
    verify(spySetting, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetProperty() {
    spySetting.setProperty("Folly words widow one downs few age every seven.");
    assertEquals("Property", "Folly words widow one downs few age every seven.", spySetting.getProperty());
    verify(spySetting, times(1)).setProperty(anyString());
  }

  @Test
  public void testSetValue() {
    spySetting.setValue("Folly words widow one downs few age every seven.");
    assertEquals("Value", "Folly words widow one downs few age every seven.", spySetting.getValue());
    verify(spySetting, times(1)).setValue(anyString());
  }

  @Test
  public void testToString() {
    assertEquals(new ReflectionToStringBuilder(spySetting, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP)
        .toString(), spySetting.toString());
  }
}
