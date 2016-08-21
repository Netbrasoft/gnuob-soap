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

package br.com.netbrasoft.gnuob.generic.content;

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

public class ContentTest {

  private Content spyContent;

  @Before
  public void setUp() throws Exception {
    spyContent = spy(Content.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testAccept() {
    assertNotNull("Context", spyContent.accept(new ContextVisitorImpl()));
    verify(spyContent, times(1)).accept(any());
  }

  @Test
  public void testContentIsDetached() {
    spyContent.setId(1L);
    assertTrue("Detached", spyContent.isDetached());
    verify(spyContent, times(1)).isDetached();
  }

  @Test
  public void testContentIsNotDetached() {
    spyContent.setId(0L);
    assertFalse("Detached", spyContent.isDetached());
    verify(spyContent, times(1)).isDetached();
  }

  @Test
  public void testGetData() {
    assertNull("Data", spyContent.getData());
    verify(spyContent, times(1)).getData();
  }

  @Test
  public void testGetFormat() {
    assertNull("Format", spyContent.getFormat());
    verify(spyContent, times(1)).getFormat();
  }

  @Test
  public void testGetName() {
    assertNull("Name", spyContent.getName());
    verify(spyContent, times(1)).getName();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spyContent.getPosition());
    verify(spyContent, times(1)).getPosition();
  }

  @Test
  public void testPrePersist() {
    spyContent.prePersist();
  }

  @Test
  public void testPreUpdate() {
    spyContent.preUpdate();
  }

  @Test
  public void testSetData() {
    spyContent.setData(new byte[0]);
    assertEquals("Data", 0, spyContent.getData().length);
    verify(spyContent, times(1)).setData(new byte[0]);
  }

  @Test
  public void testSetFormat() {
    spyContent.setFormat("Folly words widow one downs few age every seven.");
    assertEquals("Format", "Folly words widow one downs few age every seven.", spyContent.getFormat());
    verify(spyContent, times(1)).setFormat(anyString());
  }

  @Test
  public void testSetName() {
    spyContent.setName("Folly words widow one downs few age every seven.");
    assertEquals("Name", "Folly words widow one downs few age every seven.", spyContent.getName());
    verify(spyContent, times(1)).setName(anyString());
  }

  @Test
  public void testSetPosition() {
    spyContent.setPosition(Integer.MAX_VALUE);
    assertEquals("Position", Integer.MAX_VALUE, spyContent.getPosition().intValue());
    verify(spyContent, times(1)).setPosition(any());
  }

  @Test
  public void testToString() {
    assertEquals(new ReflectionToStringBuilder(spyContent, SHORT_PREFIX_STYLE).setExcludeFieldNames(SITE, USER, GROUP)
        .toString(), spyContent.toString());
  }
}
