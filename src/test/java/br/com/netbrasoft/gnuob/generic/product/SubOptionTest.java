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

package br.com.netbrasoft.gnuob.generic.product;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.product.SubOption;

public class SubOptionTest {

  private SubOption spySubOption;

  @Before
  public void setUp() throws Exception {
    spySubOption = spy(SubOption.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetDescription() {
    assertNull("Description", spySubOption.getDescription());
    verify(spySubOption, times(1)).getDescription();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spySubOption.getPosition());
    verify(spySubOption, times(1)).getPosition();
  }

  @Test
  public void testGetValue() {
    assertNull("Value", spySubOption.getValue());
    verify(spySubOption, times(1)).getValue();
  }

  @Test
  public void testSubOptionIsDetached() {
    spySubOption.setId(1L);
    assertTrue("Detached", spySubOption.isDetached());
    verify(spySubOption, times(1)).isDetached();
  }

  @Test
  public void testSubOptionIsNotDetached() {
    spySubOption.setId(0L);
    assertFalse("Detached", spySubOption.isDetached());
    verify(spySubOption, times(1)).isDetached();
  }

  @Test
  public void testIsDisabled() {
    assertNotNull("Disabled(", spySubOption.isDisabled());
    verify(spySubOption, times(1)).isDisabled();
  }

  @Test
  public void testPrePersist() {
    spySubOption.prePersist();
    verify(spySubOption, times(1)).prePersist();
  }

  @Test
  public void testPreUpdate() {
    spySubOption.preUpdate();
    verify(spySubOption, times(1)).preUpdate();
  }

  @Test
  public void testSetDescription() {
    spySubOption.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spySubOption.getDescription());
    verify(spySubOption, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetDisabled() {
    spySubOption.setDisabled(Boolean.FALSE);
    assertEquals("Disabled", Boolean.FALSE, spySubOption.isDisabled());
    verify(spySubOption, times(1)).setDisabled(Boolean.FALSE);
  }

  @Test
  public void testSetPosition() {
    spySubOption.setPosition(Integer.MAX_VALUE);
    assertEquals("Position", Integer.MAX_VALUE, spySubOption.getPosition().intValue());
    verify(spySubOption, times(1)).setPosition(anyInt());
  }

  @Test
  public void testSetValue() {
    spySubOption.setValue("Folly words widow one downs few age every seven.");
    assertEquals("Value", "Folly words widow one downs few age every seven.", spySubOption.getValue());
    verify(spySubOption, times(1)).setValue(any());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spySubOption, SHORT_PREFIX_STYLE), spySubOption.toString());
  }
}
