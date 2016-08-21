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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.generic.setting.Setting;
import br.com.netbrasoft.gnuob.generic.setting.SettingWebServiceImpl;

public class SettingWebServiceImplTest {

  private IGenericTypeWebService<Setting> settingWebServiceImpl = new SettingWebServiceImpl<>();
  private MetaData mockCredentials;
  private Paging mockPaging;
  private ISecuredGenericTypeService<Setting> mockSecuredGenericSettingService;
  private Setting spySetting;

  @SuppressWarnings(UNCHECKED_VALUE)
  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    mockPaging = mock(Paging.class);
    spySetting = spy(Setting.class);
    mockSecuredGenericSettingService = mock(ISecuredGenericTypeService.class);
    settingWebServiceImpl = new SettingWebServiceImpl<Setting>(mockSecuredGenericSettingService);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testCountSetting() {
    assertEquals("Count", 0, settingWebServiceImpl.count(mockCredentials, spySetting));
    verify(mockSecuredGenericSettingService, times(1)).count(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testCountSettingGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericSettingService.count(any(), any())).thenThrow(new RuntimeException());
    settingWebServiceImpl.count(mockCredentials, spySetting);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testDeleteCategoryGettingGNUOpenBusinessServiceException() {
    settingWebServiceImpl = new SettingWebServiceImpl<>();
    settingWebServiceImpl.remove(mockCredentials, spySetting);
  }

  @Test
  public void testDeleteCategoryWithNullSettingsAndSubCategories() {
    settingWebServiceImpl.remove(mockCredentials, spySetting);
    verify(mockSecuredGenericSettingService, times(1)).remove(any(), any());
  }

  @Test
  public void testFindSetting() {
    assertNull("Find", settingWebServiceImpl.find(mockCredentials, spySetting));
    verify(mockSecuredGenericSettingService, times(1)).find(any(), any(), anyLong());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindSettingGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericSettingService.find(any(), any(), anyLong())).thenThrow(new RuntimeException());
    settingWebServiceImpl.find(mockCredentials, spySetting);
  }

  @Test
  public void testFindSettingPaging() {
    assertTrue("Find", settingWebServiceImpl.find(mockCredentials, spySetting, mockPaging, OrderByEnum.NONE).isEmpty());
    verify(mockSecuredGenericSettingService, times(1)).find(any(), any(), any(), any(), anyVararg());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testFindSettingPagingGetttingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericSettingService.find(any(), any(), any(), any(), anyVararg()))
        .thenThrow(new RuntimeException());
    settingWebServiceImpl.find(mockCredentials, spySetting, mockPaging, OrderByEnum.NONE);
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testMergeSettingGettingGNUOpenBusinessServiceException() {
    when(spySetting.isDetached()).thenReturn(true);
    when(mockSecuredGenericSettingService.merge(any(), any())).thenThrow(new RuntimeException());
    settingWebServiceImpl.merge(mockCredentials, spySetting);
  }

  @Test
  public void testMergeWithNoDetachedSettings() {
    when(spySetting.isDetached()).thenReturn(false);
    assertNull("Merge", settingWebServiceImpl.merge(mockCredentials, spySetting));
    verify(mockSecuredGenericSettingService, times(1)).merge(any(), any());
  }

  @Test
  public void testPersistWithDetachedSettings() {
    when(spySetting.isDetached()).thenReturn(true);
    assertNull("Persist", settingWebServiceImpl.persist(mockCredentials, spySetting));
    verify(mockSecuredGenericSettingService, times(1)).merge(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testPersistCategoryGettingGNUOpenBusinessServiceException() {
    when(spySetting.isDetached()).thenReturn(true);
    when(mockSecuredGenericSettingService.merge(any(), any())).thenThrow(new RuntimeException());
    settingWebServiceImpl.persist(mockCredentials, spySetting);
  }

  @Test
  public void testPersistWithNoDetachedSettings() {
    when(spySetting.isDetached()).thenReturn(false);
    assertNotNull("Persist", settingWebServiceImpl.persist(mockCredentials, spySetting));
    verify(mockSecuredGenericSettingService, times(1)).persist(any(), any());
  }

  @Test(expected = GNUOpenBusinessServiceException.class)
  public void testRefreshCategoryGettingGNUOpenBusinessServiceException() {
    when(mockSecuredGenericSettingService.refresh(any(), any(), anyLong())).thenThrow(new RuntimeException());
    settingWebServiceImpl.refresh(mockCredentials, spySetting);
  }

  @Test
  public void testRefreshCategoryWithDetachedSettings() {
    assertNull("Refresh", settingWebServiceImpl.refresh(mockCredentials, spySetting));
    verify(mockSecuredGenericSettingService, times(1)).refresh(any(), any(), anyLong());
  }
}
