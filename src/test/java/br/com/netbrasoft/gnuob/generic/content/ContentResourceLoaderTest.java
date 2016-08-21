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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Arrays;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.content.ContentResourceLoader;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;

public class ContentResourceLoaderTest {

  private ResourceLoader contentResourceLoader;
  private Content mockContent;
  private ExtendedProperties mockExtendedProperties;
  private Resource mockResource;
  private ISecuredGenericTypeService<Content> mockSecuredGenericContentService;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockContent = mock(Content.class);
    mockResource = mock(Resource.class);
    mockExtendedProperties = mock(ExtendedProperties.class);
    mockSecuredGenericContentService = mock(ISecuredGenericTypeService.class);
    contentResourceLoader = new ContentResourceLoader<Content>(mockSecuredGenericContentService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetLastModifiedResource() {
    when(mockContent.getModification()).thenReturn(mock(Timestamp.class));
    when(mockResource.getName()).thenReturn("Mock");
    when(mockSecuredGenericContentService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericContentService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(mockContent));
    assertTrue("Last modified", contentResourceLoader.getLastModified(mockResource) == 0);
    verify(mockSecuredGenericContentService, times(1)).count(any(), any());
    verify(mockSecuredGenericContentService, times(1)).find(any(), any(), any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testGetLastModifiedResourceGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericContentService.count(any(), any())).thenReturn(0L);
    contentResourceLoader.getLastModified(mockResource);
  }

  @Test
  public void testGetResourceStream() {
    when(mockContent.getData()).thenReturn(new byte[] {});
    when(mockSecuredGenericContentService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericContentService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(mockContent));
    assertNotNull("Resource stream", contentResourceLoader.getResourceStream("Mock"));
    verify(mockSecuredGenericContentService, times(1)).count(any(), any());
    verify(mockSecuredGenericContentService, times(1)).find(any(), any(), any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testGetResourceStreamGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericContentService.count(any(), any())).thenReturn(0L);
    contentResourceLoader.getResourceStream("Mock");
  }

  @Test
  public void testInitExtendedProperties() {
    when(mockExtendedProperties.getString(any())).thenReturn(anyString());
    contentResourceLoader.init(mockExtendedProperties);
    verify(mockExtendedProperties, times(3)).getString(any());
  }

  @Test
  public void testIsSourceModifiedFalse() {
    when(mockContent.getModification()).thenReturn(mock(Timestamp.class));
    when(mockResource.getLastModified()).thenReturn(0L);
    when(mockResource.getName()).thenReturn("Mock");
    when(mockSecuredGenericContentService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericContentService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(mockContent));
    assertFalse("Source modified", contentResourceLoader.isSourceModified(mockResource));
    verify(mockSecuredGenericContentService, times(1)).count(any(), any());
    verify(mockSecuredGenericContentService, times(1)).find(any(), any(), any(), any());
  }

  @Test
  public void testIsSourceModifiedTrue() {
    when(mockContent.getModification()).thenReturn(mock(Timestamp.class));
    when(mockResource.getLastModified()).thenReturn(-1L);
    when(mockResource.getName()).thenReturn("Mock");
    when(mockSecuredGenericContentService.count(any(), any())).thenReturn(1L);
    when(mockSecuredGenericContentService.find(any(), any(), any(), any())).thenReturn(Arrays.asList(mockContent));
    assertTrue("Source modified", contentResourceLoader.isSourceModified(mockResource));
    verify(mockSecuredGenericContentService, times(1)).count(any(), any());
    verify(mockSecuredGenericContentService, times(1)).find(any(), any(), any(), any());
  }
}
