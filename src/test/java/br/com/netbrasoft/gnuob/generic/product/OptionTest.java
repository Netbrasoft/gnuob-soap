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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.collections.Sets.newSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.product.Option;
import br.com.netbrasoft.gnuob.generic.product.SubOption;

public class OptionTest {

  private SubOption mockSubOption;
  private Option spyOption;

  @Before
  public void setUp() throws Exception {
    mockSubOption = mock(SubOption.class);
    spyOption = spy(Option.class);
    spyOption.setSubOptions(newSet(mockSubOption));
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetDescription() {
    assertNull("Description", spyOption.getDescription());
    verify(spyOption, times(1)).getDescription();
  }

  @Test
  public void testGetPosition() {
    assertNull("Position", spyOption.getPosition());
    verify(spyOption, times(1)).getPosition();
  }

  @Test
  public void testGetSubOptions() {
    assertNotNull("SubOptions", spyOption.getSubOptions());
    verify(spyOption, times(1)).getSubOptions();
  }

  @Test
  public void testGetValue() {
    assertNull("Value", spyOption.getValue());
    verify(spyOption, times(1)).getValue();
  }

  @Test
  public void testOptionIsDetached() {
    spyOption.setId(1L);
    when(mockSubOption.isDetached()).thenReturn(false);
    assertTrue("Detached", spyOption.isDetached());
    verify(spyOption, times(1)).isDetached();
    verify(mockSubOption, times(1)).isDetached();
  }

  @Test
  public void testOptionIsDetachedBySubOption() {
    spyOption.setId(0L);
    when(mockSubOption.isDetached()).thenReturn(true);
    assertTrue("Detached", spyOption.isDetached());
    verify(spyOption, times(1)).isDetached();
    verify(mockSubOption, times(1)).isDetached();
  }

  @Test
  public void testOptionIsNotDetachedBySubOptionIsNull() {
    spyOption.setId(0L);
    spyOption.setSubOptions(null);
    assertFalse("Detached", spyOption.isDetached());
    verify(spyOption, times(1)).isDetached();
    verify(mockSubOption, never()).isDetached();
  }

  @Test
  public void testOptionIsNotDetached() {
    spyOption.setId(0L);
    when(mockSubOption.isDetached()).thenReturn(false);
    assertFalse("Detached", spyOption.isDetached());
    verify(spyOption, times(1)).isDetached();
    verify(mockSubOption, times(1)).isDetached();
  }

  @Test
  public void testIsDisabled() {
    assertNotNull("Disabled(", spyOption.isDisabled());
    verify(spyOption, times(1)).isDisabled();
  }

  @Test
  public void testPrePersist() {
    spyOption.prePersist();
    verify(spyOption, times(1)).prePersist();
    verify(mockSubOption, times(1)).setPosition(anyInt());
  }

  @Test
  public void testPreUpdate() {
    spyOption.preUpdate();
    verify(spyOption, times(1)).preUpdate();
    verify(spyOption, times(1)).prePersist();
  }

  @Test
  public void testSetDescription() {
    spyOption.setDescription("Folly words widow one downs few age every seven.");
    assertEquals("Description", "Folly words widow one downs few age every seven.", spyOption.getDescription());
    verify(spyOption, times(1)).setDescription(anyString());
  }

  @Test
  public void testSetDisabled() {
    spyOption.setDisabled(Boolean.FALSE);
    assertEquals("Disabled", Boolean.FALSE, spyOption.isDisabled());
    verify(spyOption, times(1)).setDisabled(Boolean.FALSE);
  }

  @Test
  public void testSetPosition() {
    spyOption.setPosition(Integer.MAX_VALUE);
    assertEquals("Position", Integer.MAX_VALUE, spyOption.getPosition().intValue());
    verify(spyOption, times(1)).setPosition(anyInt());
  }

  @Test
  public void testSetSubOptions() {
    spyOption.setSubOptions(null);
    assertNull("SubOptions", spyOption.getSubOptions());
    verify(spyOption, times(1)).setSubOptions(null);
  }

  @Test
  public void testSetValue() {
    spyOption.setValue("Folly words widow one downs few age every seven.");
    assertEquals("Value", "Folly words widow one downs few age every seven.", spyOption.getValue());
    verify(spyOption, times(1)).setValue(any());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyOption, SHORT_PREFIX_STYLE), spyOption.toString());
  }
}
