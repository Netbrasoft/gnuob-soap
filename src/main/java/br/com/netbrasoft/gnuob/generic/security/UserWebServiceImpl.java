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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNT_USER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_USER_BY_ID_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_USER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_WEB_SERVICE_TARGET_NAMESPACE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MERGE_USER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.META_DATA_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_BY_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAGING_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PERSIST_USER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REFRESH_USER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REMOVE_USER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER_WEB_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.factory.MessageCreaterFactory.createMessage;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Set;

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
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@WebService(targetNamespace = GNUOB_WEB_SERVICE_TARGET_NAMESPACE)
@Stateless(name = USER_WEB_SERVICE_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class UserWebServiceImpl<T extends User> implements IGenericTypeWebService<T> {

  private static final Logger LOGGER = getLogger(UserWebServiceImpl.class);

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<T> securedGenericUserService;

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<Group> securedGenericGroupService;

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<Site> securedGenericSiteService;

  public UserWebServiceImpl() {
    // This constructor will be used by the EJB container.
  }

  UserWebServiceImpl(final ISecuredGenericTypeService<T> securedGenericUserService,
      final ISecuredGenericTypeService<Group> securedGenericGroupService,
      final ISecuredGenericTypeService<Site> securedGenericSiteService) {
    this.securedGenericUserService = securedGenericUserService;
    this.securedGenericGroupService = securedGenericGroupService;
    this.securedGenericSiteService = securedGenericSiteService;
  }

  @Override
  @WebMethod(operationName = COUNT_USER_OPERATION_NAME)
  public long count(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = USER_PARAM_NAME) final T type) {
    try {
      return processCount(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(COUNT_USER_OPERATION_NAME, credentials, type));
    }
  }

  private long processCount(final MetaData credentials, final T type) {
    readSites(credentials, type.getSites());
    readGroups(credentials, type.getGroups());
    return securedGenericUserService.count(credentials, type);
  }

  private void readSites(final MetaData credentials, final Set<Site> sites) {
    if (sites != null) {
      sites.stream().filter(e -> e.isDetached()).forEach(e -> securedGenericSiteService.read(credentials, e));
    }
  }

  private void readGroups(final MetaData credentials, final Set<Group> groups) {
    if (groups != null) {
      groups.stream().filter(e -> e.isDetached()).forEach(e -> securedGenericGroupService.read(credentials, e));
    }
  }

  @Override
  @WebMethod(operationName = FIND_USER_BY_ID_OPERATION_NAME)
  public T find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = USER_PARAM_NAME) final T type) {
    try {
      return processFindById(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_USER_BY_ID_OPERATION_NAME, credentials, type));
    }
  }

  private T processFindById(final MetaData credentials, final T type) {
    readSites(credentials, type.getSites());
    readGroups(credentials, type.getGroups());
    return securedGenericUserService.find(credentials, type, type.getId());
  }

  @Override
  @WebMethod(operationName = FIND_USER_OPERATION_NAME)
  public List<T> find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = USER_PARAM_NAME) final T type, @WebParam(name = PAGING_PARAM_NAME) final Paging paging,
      @WebParam(name = ORDER_BY_PARAM_NAME) final OrderByEnum orderingProperty) {
    try {
      return processFind(credentials, type, paging, orderingProperty);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_USER_OPERATION_NAME, credentials, type, paging, orderingProperty));
    }
  }

  private List<T> processFind(final MetaData credentials, final T type, final Paging paging,
      final OrderByEnum orderingProperty) {
    readSites(credentials, type.getSites());
    readGroups(credentials, type.getGroups());
    return securedGenericUserService.find(credentials, type, paging, orderingProperty);
  }

  @Override
  @WebMethod(operationName = MERGE_USER_OPERATION_NAME)
  public T merge(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = USER_PARAM_NAME) final T type) {
    try {
      return processMerge(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(MERGE_USER_OPERATION_NAME, credentials, type));
    }
  }

  private T processMerge(final MetaData credentials, final T type) {
    createUpdateSites(credentials, type.getSites());
    createUpdateGroups(credentials, type.getGroups());
    return securedGenericUserService.merge(credentials, type);
  }

  private void createUpdateSites(final MetaData credentials, final Set<Site> sites) {
    if (sites != null) {
      sites.stream().filter(e -> !e.isDetached()).forEach(e -> securedGenericSiteService.create(credentials, e));
      sites.stream().filter(e -> e.isDetached()).forEach(e -> securedGenericSiteService.update(credentials, e));
    }
  }

  private void createUpdateGroups(final MetaData credentials, final Set<Group> groups) {
    if (groups != null) {
      groups.stream().filter(e -> !e.isDetached()).forEach(e -> securedGenericGroupService.create(credentials, e));
      groups.stream().filter(e -> e.isDetached()).forEach(e -> securedGenericGroupService.update(credentials, e));
    }
  }

  @Override
  @WebMethod(operationName = PERSIST_USER_OPERATION_NAME)
  public T persist(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = USER_PARAM_NAME) final T type) {
    try {
      return processPersist(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(PERSIST_USER_OPERATION_NAME, credentials, type));
    }
  }

  private T processPersist(final MetaData credentials, final T type) {
    createUpdateSites(credentials, type.getSites());
    createUpdateGroups(credentials, type.getGroups());
    return mergeOrPersistUser(credentials, type);
  }

  private T mergeOrPersistUser(final MetaData credentials, final T type) {
    if (type.isDetached()) {
      return securedGenericUserService.merge(credentials, type);
    }
    securedGenericUserService.persist(credentials, type);
    return type;
  }

  @Override
  @WebMethod(operationName = REFRESH_USER_OPERATION_NAME)
  public T refresh(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = USER_PARAM_NAME) final T type) {
    try {
      return processRefresh(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REFRESH_USER_OPERATION_NAME, credentials, type));
    }
  }

  private T processRefresh(final MetaData credentials, final T type) {
    readSites(credentials, type.getSites());
    readGroups(credentials, type.getGroups());
    return securedGenericUserService.refresh(credentials, type, type.getId());
  }

  @Override
  @WebMethod(operationName = REMOVE_USER_OPERATION_NAME)
  public void remove(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = USER_PARAM_NAME) final T type) {
    try {
      processRemove(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REMOVE_USER_OPERATION_NAME, credentials, type));
    }
  }

  private void processRemove(final MetaData credentials, final T type) {
    deleteSites(credentials, type.getSites());
    deleteGroups(credentials, type.getGroups());
    securedGenericUserService.remove(credentials, type);
  }

  private void deleteSites(final MetaData credentials, final Set<Site> sites) {
    if (sites != null) {
      sites.stream().filter(e -> e.isDetached()).forEach(e -> securedGenericSiteService.delete(credentials, e));
    }
  }

  private void deleteGroups(final MetaData credentials, final Set<Group> groups) {
    if (groups != null) {
      groups.stream().filter(e -> e.isDetached()).forEach(e -> securedGenericGroupService.delete(credentials, e));
    }
  }
}
