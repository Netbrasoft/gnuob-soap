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

package br.com.netbrasoft.gnuob.generic.contract;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_WEB_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNT_CONTRACT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_CONTRACT_BY_ID_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_CONTRACT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_WEB_SERVICE_TARGET_NAMESPACE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MERGE_CONTRACT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.META_DATA_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_BY_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAGING_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PERSIST_CONTRACT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REFRESH_CONTRACT_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REMOVE_CONTRACT_OPERATION_NAME;
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
import br.com.netbrasoft.gnuob.generic.content.mail.MailAction;
import br.com.netbrasoft.gnuob.generic.content.mail.MailEnum;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@WebService(targetNamespace = GNUOB_WEB_SERVICE_TARGET_NAMESPACE)
@Stateless(name = CONTRACT_WEB_SERVICE_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class ContractWebServiceImpl<T extends Contract> implements IGenericTypeWebService<T> {

  private static final Logger LOGGER = getLogger(ContractWebServiceImpl.class);

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<T> securedGenericContractService;

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<Customer> securedGenericCustomerService;

  public ContractWebServiceImpl() {
    // This constructor will be used by the EJB container.
  }

  ContractWebServiceImpl(final ISecuredGenericTypeService<T> securedGenericContractService,
      final ISecuredGenericTypeService<Customer> securedGenericCustomerService) {
    this.securedGenericContractService = securedGenericContractService;
    this.securedGenericCustomerService = securedGenericCustomerService;
  }

  @Override
  @WebMethod(operationName = COUNT_CONTRACT_OPERATION_NAME)
  public long count(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTRACT_PARAM_NAME) final T type) {
    try {
      return processCount(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(COUNT_CONTRACT_OPERATION_NAME, credentials, type));
    }
  }

  private long processCount(final MetaData credentials, final T type) {
    readCustomer(credentials, type.getCustomer());
    return securedGenericContractService.count(credentials, type);
  }

  private void readCustomer(final MetaData credentials, final Customer customer) {
    if (customer != null && customer.isDetached()) {
      securedGenericCustomerService.read(credentials, customer);
    }
  }

  @Override
  @WebMethod(operationName = FIND_CONTRACT_OPERATION_NAME)
  public List<T> find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTRACT_PARAM_NAME) final T type, @WebParam(name = PAGING_PARAM_NAME) final Paging paging,
      @WebParam(name = ORDER_BY_PARAM_NAME) final OrderByEnum orderingProperty) {
    try {
      return processFind(credentials, type, paging, orderingProperty);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_CONTRACT_OPERATION_NAME, credentials, type, paging, orderingProperty));
    }
  }

  private List<T> processFind(final MetaData credentials, final T type, final Paging paging,
      final OrderByEnum orderingProperty) {
    readCustomer(credentials, type.getCustomer());
    return securedGenericContractService.find(credentials, type, paging, orderingProperty);
  }

  @Override
  @WebMethod(operationName = FIND_CONTRACT_BY_ID_OPERATION_NAME)
  public T find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTRACT_PARAM_NAME) final T type) {
    try {
      return processFindById(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_CONTRACT_BY_ID_OPERATION_NAME, credentials, type));
    }
  }

  private T processFindById(final MetaData credentials, final T type) {
    readCustomer(credentials, type.getCustomer());
    return securedGenericContractService.find(credentials, type, type.getId());
  }

  @Override
  @WebMethod(operationName = MERGE_CONTRACT_OPERATION_NAME)
  public T merge(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTRACT_PARAM_NAME) final T type) {
    try {
      return processMerge(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(MERGE_CONTRACT_OPERATION_NAME, credentials, type));
    }
  }

  private T processMerge(final MetaData credentials, final T type) {
    createUpdateCustomer(credentials, type.getCustomer());
    return securedGenericContractService.merge(credentials, type);
  }

  private void createUpdateCustomer(final MetaData credentials, final Customer customer) {
    if (customer != null) {
      if (customer.isDetached()) {
        securedGenericCustomerService.update(credentials, customer);
      } else {
        securedGenericCustomerService.create(credentials, customer);
      }
    }
  }

  @Override
  @WebMethod(operationName = PERSIST_CONTRACT_OPERATION_NAME)
  @MailAction(operation = MailEnum.WELCOME_NEW_CUSTOMER_MAIL)
  public T persist(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTRACT_PARAM_NAME) final T type) {
    try {
      return processPersist(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(PERSIST_CONTRACT_OPERATION_NAME, credentials, type));
    }
  }

  private T processPersist(final MetaData credentials, final T type) {
    createUpdateCustomer(credentials, type.getCustomer());
    return mergePersistContract(credentials, type);
  }

  private T mergePersistContract(final MetaData credentials, final T type) {
    if (type.isDetached()) {
      return securedGenericContractService.merge(credentials, type);
    }
    securedGenericContractService.persist(credentials, type);
    return type;
  }

  @Override
  @WebMethod(operationName = REFRESH_CONTRACT_OPERATION_NAME)
  public T refresh(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTRACT_PARAM_NAME) final T type) {
    try {
      return processRefresh(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REFRESH_CONTRACT_OPERATION_NAME, credentials, type));
    }
  }

  private T processRefresh(final MetaData credentials, final T type) {
    readCustomer(credentials, type.getCustomer());
    return securedGenericContractService.refresh(credentials, type, type.getId());
  }

  @Override
  @WebMethod(operationName = REMOVE_CONTRACT_OPERATION_NAME)
  public void remove(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = CONTRACT_PARAM_NAME) final T type) {
    try {
      processRemove(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REMOVE_CONTRACT_OPERATION_NAME, credentials, type));
    }
  }

  private void processRemove(final MetaData credentials, final T type) {
    deleteCustomer(credentials, type.getCustomer());
    securedGenericContractService.remove(credentials, type);
  }

  private void deleteCustomer(final MetaData credentials, final Customer customer) {
    if (customer != null && customer.isDetached()) {
      securedGenericCustomerService.delete(credentials, customer);
    }
  }
}
