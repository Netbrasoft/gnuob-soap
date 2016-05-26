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

package com.netbrasoft.gnuob.generic.security;

import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNT_SITE_OPERATION_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_SITE_BY_ID_OPERATION_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_SITE_OPERATION_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_WEB_SERVICE_TARGET_NAMESPACE;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MERGE_SITE_OPERATION_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.META_DATA_PARAM_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_BY_PARAM_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAGING_PARAM_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PERSIST_SITE_OPERATION_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REFRESH_SITE_OPERATION_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REMOVE_SITE_OPERATION_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE_PARAM_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE_WEB_SERVICE_IMPL_NAME;
import static com.netbrasoft.gnuob.generic.factory.MessageCreaterFactory.createMessage;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import com.netbrasoft.gnuob.generic.OrderByEnum;
import com.netbrasoft.gnuob.generic.Paging;
import com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@WebService(targetNamespace = GNUOB_WEB_SERVICE_TARGET_NAMESPACE)
@Stateless(name = SITE_WEB_SERVICE_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class SiteWebServiceImpl<T extends Site> implements IGenericTypeWebService<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SiteWebServiceImpl.class);

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<T> securedGenericSiteService;

  public SiteWebServiceImpl() {
    // This constructor will be used by the EBJ container.
  }

  SiteWebServiceImpl(final ISecuredGenericTypeService<T> securedGenericSiteService) {
    this.securedGenericSiteService = securedGenericSiteService;
  }

  @Override
  @WebMethod(operationName = COUNT_SITE_OPERATION_NAME)
  public long count(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = SITE_PARAM_NAME) final T type) {
    try {
      return securedGenericSiteService.count(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(COUNT_SITE_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = FIND_SITE_BY_ID_OPERATION_NAME)
  public T find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = SITE_PARAM_NAME) final T type) {
    try {
      return securedGenericSiteService.find(credentials, type, type.getId());
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_SITE_BY_ID_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = FIND_SITE_OPERATION_NAME)
  public List<T> find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = SITE_PARAM_NAME) final T type, @WebParam(name = PAGING_PARAM_NAME) final Paging paging,
      @WebParam(name = ORDER_BY_PARAM_NAME) final OrderByEnum orderingProperty) {
    try {
      return securedGenericSiteService.find(credentials, type, paging, orderingProperty);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_SITE_OPERATION_NAME, credentials, type, paging, orderingProperty));
    }
  }

  @Override
  @WebMethod(operationName = MERGE_SITE_OPERATION_NAME)
  public T merge(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = SITE_PARAM_NAME) final T type) {
    try {
      return securedGenericSiteService.merge(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(MERGE_SITE_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = PERSIST_SITE_OPERATION_NAME)
  public T persist(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = SITE_PARAM_NAME) final T type) {
    try {
      return processPersist(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(PERSIST_SITE_OPERATION_NAME, credentials, type));
    }
  }

  private T processPersist(final MetaData credentials, final T type) {
    if (type.isDetached()) {
      return securedGenericSiteService.merge(credentials, type);
    }
    securedGenericSiteService.persist(credentials, type);
    return type;
  }

  @Override
  @WebMethod(operationName = REFRESH_SITE_OPERATION_NAME)
  public T refresh(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = SITE_PARAM_NAME) final T type) {
    try {
      return securedGenericSiteService.refresh(credentials, type, type.getId());
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REFRESH_SITE_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = REMOVE_SITE_OPERATION_NAME)
  public void remove(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = SITE_PARAM_NAME) final T type) {
    try {
      securedGenericSiteService.remove(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REMOVE_SITE_OPERATION_NAME, credentials, type));
    }
  }
}
