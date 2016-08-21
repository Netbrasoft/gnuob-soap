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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_WEB_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNT_CONTENT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_CONTENT_BY_ID_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_CONTENT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_WEB_SERVICE_TARGET_NAMESPACE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MERGE_CONTENT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.META_DATA_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_BY_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAGING_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PERSIST_CONTENT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REFRESH_CONTENT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REMOVE_CONTENT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.factory.MessageCreaterFactory.createMessage;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeWebService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@WebService(targetNamespace = GNUOB_WEB_SERVICE_TARGET_NAMESPACE)
@Stateless(name = CONTENT_WEB_SERVICE_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class ContentWebServiceImpl<T extends Content> implements IGenericTypeWebService<T> {

  private static final Logger LOGGER = getLogger(ContentWebServiceImpl.class);

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<T> securedGenericContentService;

  public ContentWebServiceImpl() {
    // This constructor will be used by the EJB container.
  }

  ContentWebServiceImpl(final ISecuredGenericTypeService<T> securedGenericContentService) {
    this.securedGenericContentService = securedGenericContentService;
  }

  @Override
  @WebMethod(operationName = COUNT_CONTENT_OPERATION_NAME)
  public long count(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTENT_PARAM_NAME) final T type) {
    try {
      return securedGenericContentService.count(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(COUNT_CONTENT_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = FIND_CONTENT_BY_ID_OPERATION_NAME)
  public T find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTENT_PARAM_NAME) final T type) {
    try {
      return securedGenericContentService.find(credentials, type, type.getId());
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_CONTENT_BY_ID_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = FIND_CONTENT_OPERATION_NAME)
  public List<T> find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTENT_PARAM_NAME) final T type, @WebParam(name = PAGING_PARAM_NAME) final Paging paging,
      @WebParam(name = ORDER_BY_PARAM_NAME) final OrderByEnum orderingProperty) {
    try {
      return securedGenericContentService.find(credentials, type, paging, orderingProperty);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_CONTENT_OPERATION_NAME, credentials, type, paging, orderingProperty));
    }
  }

  @Override
  @WebMethod(operationName = MERGE_CONTENT_OPERATION_NAME)
  public T merge(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTENT_PARAM_NAME) final T type) {
    try {
      return securedGenericContentService.merge(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(MERGE_CONTENT_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = PERSIST_CONTENT_OPERATION_NAME)
  public T persist(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTENT_PARAM_NAME) final T type) {
    try {
      return persitMergeContent(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(PERSIST_CONTENT_OPERATION_NAME, credentials, type));
    }
  }

  private T persitMergeContent(final MetaData credentials, final T type) {
    if (type.isDetached()) {
      return securedGenericContentService.merge(credentials, type);
    }
    securedGenericContentService.persist(credentials, type);
    return type;
  }

  @Override
  @WebMethod(operationName = REFRESH_CONTENT_OPERATION_NAME)
  public T refresh(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTENT_PARAM_NAME) final T type) {
    try {
      return securedGenericContentService.refresh(credentials, type, type.getId());
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REFRESH_CONTENT_OPERATION_NAME, credentials, type));
    }
  }

  @Override
  @WebMethod(operationName = REMOVE_CONTENT_OPERATION_NAME)
  public void remove(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTENT_PARAM_NAME) final T type) {
    try {
      securedGenericContentService.remove(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REMOVE_CONTENT_OPERATION_NAME, credentials, type));
    }
  }
}
