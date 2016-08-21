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

package br.com.netbrasoft.gnuob.generic;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.AbstractType;

public class AbstractTypeTest {

  private AbstractType spyAbstractType;

  @Before
  public void setUp() throws Exception {
    spyAbstractType = spy(AbstractType.class);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetCreation() {
    assertNull("Creation", spyAbstractType.getCreation());
    verify(spyAbstractType, times(1)).getCreation();
  }

  @Test
  public void testIsAbstractTypeDetachedIsFalse() {
    assertFalse("IsAbstractTypeDetached", spyAbstractType.isAbstractTypeDetached());
    verify(spyAbstractType, times(1)).isAbstractTypeDetached();
  }

  @Test
  public void testIsAbstractTypeDetachedIsTrue() {
    spyAbstractType.setId(1L);
    assertTrue("IsAbstractTypeDetached", spyAbstractType.isAbstractTypeDetached());
    verify(spyAbstractType, times(1)).isAbstractTypeDetached();
  }

  @Test
  public void testGetId() {
    assertNotNull("Id", spyAbstractType.getId());
    verify(spyAbstractType, times(1)).getId();
  }

  @Test
  public void testGetModification() {
    assertNull("Modification", spyAbstractType.getModification());
    verify(spyAbstractType, times(1)).getModification();
  }

  @Test
  public void testGetVersion() {
    assertNotNull("Version", spyAbstractType.getVersion());
    verify(spyAbstractType, times(1)).getVersion();
  }

  @Test
  public void testPrePersistType() {
    spyAbstractType.prePersistType();
    assertNotNull("Creation", spyAbstractType.getCreation());
    assertNotNull("Modification", spyAbstractType.getModification());
    verify(spyAbstractType, times(1)).prePersistType();
    verify(spyAbstractType, times(1)).prePersist();
  }

  @Test
  public void testPreUpdateType() {
    spyAbstractType.preUpdateType();
    assertNotNull("Modification", spyAbstractType.getModification());
    verify(spyAbstractType, times(1)).preUpdateType();
    verify(spyAbstractType, times(1)).preUpdate();
  }

  @Test
  public void testSetCreation() {
    spyAbstractType.setCreation(new Timestamp(System.currentTimeMillis()));
    assertNotNull("Creation", spyAbstractType.getCreation());
    verify(spyAbstractType, times(1)).setCreation(any());
  }

  @Test
  public void testSetId() {
    spyAbstractType.setId(1L);
    assertEquals("Id", 1L, spyAbstractType.getId());
    verify(spyAbstractType, times(1)).setId(anyLong());
  }

  @Test
  public void testSetModification() {
    spyAbstractType.setModification(new Timestamp(System.currentTimeMillis()));
    assertNotNull("Modification", spyAbstractType.getModification());
    verify(spyAbstractType, times(1)).setModification(any());
  }

  @Test
  public void testSetVersion() {
    spyAbstractType.setVersion(1);
    assertEquals("Version", 1, spyAbstractType.getVersion());
    verify(spyAbstractType, times(1)).setVersion(anyInt());
  }

  @Test
  public void testToString() {
    assertEquals(ToStringBuilder.reflectionToString(spyAbstractType, SHORT_PREFIX_STYLE), spyAbstractType.toString());
  }
}
