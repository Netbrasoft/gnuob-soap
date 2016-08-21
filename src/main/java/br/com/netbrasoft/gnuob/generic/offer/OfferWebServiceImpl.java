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

package br.com.netbrasoft.gnuob.generic.offer;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNT_OFFER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_OFFER_BY_ID_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FIND_OFFER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_WEB_SERVICE_TARGET_NAMESPACE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MERGE_OFFER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.META_DATA_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OFFER_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OFFER_WEB_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_BY_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAGING_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PERSIST_OFFER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REFRESH_OFFER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.REMOVE_OFFER_OPERATION_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.factory.MessageCreaterFactory.createMessage;
import static org.hibernate.criterion.Example.create;
import static org.hibernate.criterion.Restrictions.or;
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
import br.com.netbrasoft.gnuob.generic.Parameter;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@WebService(targetNamespace = GNUOB_WEB_SERVICE_TARGET_NAMESPACE)
@Stateless(name = OFFER_WEB_SERVICE_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class OfferWebServiceImpl<T extends Offer> implements IGenericTypeWebService<T> {

  private static final Logger LOGGER = getLogger(OfferWebServiceImpl.class);

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<T> securedGenericOfferService;

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<Contract> securedGenericContractService;

  @EJB(beanName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
  private ISecuredGenericTypeService<Customer> securedGenericCustomerService;

  public OfferWebServiceImpl() {
    // This constructor will be used by the EJB container.
  }

  OfferWebServiceImpl(final ISecuredGenericTypeService<T> securedGenericOfferService,
      final ISecuredGenericTypeService<Contract> securedGenericContractService,
      final ISecuredGenericTypeService<Customer> securedGenericCustomerService) {
    this.securedGenericOfferService = securedGenericOfferService;
    this.securedGenericContractService = securedGenericContractService;
    this.securedGenericCustomerService = securedGenericCustomerService;
  }

  @Override
  @WebMethod(operationName = COUNT_OFFER_OPERATION_NAME)
  public long count(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = OFFER_PARAM_NAME) final T type) {
    try {
      return processCount(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(COUNT_OFFER_OPERATION_NAME, credentials, type));
    }
  }

  private long processCount(final MetaData credentials, final T type) {
    readContract(credentials, type.getContract());
    return countByOffer(credentials, type);
  }

  private void readContract(final MetaData credentials, final Contract contract) {
    if (contract != null) {
      if (contract.isDetached()) {
        securedGenericContractService.read(credentials, contract);
      }
      readCustomer(credentials, contract.getCustomer());
    }
  }

  private void readCustomer(final MetaData credentials, final Customer customer) {
    if (customer != null && customer.isDetached()) {
      securedGenericCustomerService.read(credentials, customer);
    }
  }

  private long countByOffer(final MetaData credentials, final T type) {
    if (type.getContract() != null) {
      return countByOfferAndContract(credentials, type);
    }
    return securedGenericOfferService.count(credentials, type);
  }

  private long countByOfferAndContract(final MetaData credentials, final T type) {
    return securedGenericOfferService.count(credentials, type,
        Parameter.getInstance(CONTRACT_PARAM_NAME, or(create(type.getContract()))));
  }

  @Override
  @WebMethod(operationName = FIND_OFFER_OPERATION_NAME)
  public List<T> find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = OFFER_PARAM_NAME) final T type, @WebParam(name = PAGING_PARAM_NAME) final Paging paging,
      @WebParam(name = ORDER_BY_PARAM_NAME) final OrderByEnum orderingProperty) {
    try {
      return processFind(credentials, type, paging, orderingProperty);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_OFFER_OPERATION_NAME, credentials, type, paging, orderingProperty));
    }
  }

  private List<T> processFind(final MetaData credentials, final T type, final Paging paging,
      final OrderByEnum orderBy) {
    readContract(credentials, type.getContract());
    return findByOffer(credentials, type, paging, orderBy);
  }

  private List<T> findByOffer(final MetaData credentials, final T type, final Paging paging,
      final OrderByEnum orderBy) {
    if (type.getContract() != null) {
      return findByOfferAndContract(credentials, type, paging, orderBy);
    }
    return securedGenericOfferService.find(credentials, type, paging, orderBy);
  }

  private List<T> findByOfferAndContract(final MetaData credentials, final T type, final Paging paging,
      final OrderByEnum orderingProperty) {
    return securedGenericOfferService.find(credentials, type, paging, orderingProperty,
        Parameter.getInstance(CONTRACT_PARAM_NAME, or(create(type.getContract()))));
  }

  @Override
  @WebMethod(operationName = FIND_OFFER_BY_ID_OPERATION_NAME)
  public T find(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = OFFER_PARAM_NAME) final T type) {
    try {
      return processFindById(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(FIND_OFFER_BY_ID_OPERATION_NAME, credentials, type));
    }
  }

  private T processFindById(final MetaData credentials, final T type) {
    readContract(credentials, type.getContract());
    return securedGenericOfferService.find(credentials, type, type.getId());
  }

  @Override
  @WebMethod(operationName = MERGE_OFFER_OPERATION_NAME)
  public T merge(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = OFFER_PARAM_NAME) final T type) {
    try {
      return processMerge(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(MERGE_OFFER_OPERATION_NAME, credentials, type));
    }
  }

  private T processMerge(final MetaData credentials, final T type) {
    createUpdateContract(credentials, type.getContract());
    return securedGenericOfferService.merge(credentials, type);
  }

  private void createUpdateContract(final MetaData credentials, final Contract contract) {
    if (contract != null) {
      if (contract.isDetached()) {
        securedGenericContractService.update(credentials, contract);
      } else {
        securedGenericContractService.create(credentials, contract);
      }
      createUpdateCustomer(credentials, contract.getCustomer());
    }
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
  @WebMethod(operationName = PERSIST_OFFER_OPERATION_NAME)
  public T persist(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = OFFER_PARAM_NAME) final T type) {
    try {
      return processPersist(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(PERSIST_OFFER_OPERATION_NAME, credentials, type));
    }
  }

  private T processPersist(final MetaData credentials, final T type) {
    createUpdateContract(credentials, type.getContract());
    return mergePersistOffer(credentials, type);
  }

  private T mergePersistOffer(final MetaData credentials, final T type) {
    if (type.isDetached()) {
      return securedGenericOfferService.merge(credentials, type);
    }
    securedGenericOfferService.persist(credentials, type);
    return type;
  }

  @Override
  @WebMethod(operationName = REFRESH_OFFER_OPERATION_NAME)
  public T refresh(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = OFFER_PARAM_NAME) final T type) {
    try {
      return processRefresh(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REFRESH_OFFER_OPERATION_NAME, credentials, type));
    }
  }

  private T processRefresh(final MetaData credentials, final T type) {
    readContract(credentials, type.getContract());
    return securedGenericOfferService.refresh(credentials, type, type.getId());
  }

  @Override
  @WebMethod(operationName = REMOVE_OFFER_OPERATION_NAME)
  public void remove(@WebParam(name = META_DATA_PARAM_NAME, header = true) final MetaData credentials,
      @WebParam(name = OFFER_PARAM_NAME) final T type) {
    try {
      processRemove(credentials, type);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      LOGGER.debug(createMessage(REMOVE_OFFER_OPERATION_NAME, credentials, type));
    }
  }

  private void processRemove(final MetaData credentials, final T type) {
    deleteContract(credentials, type.getContract());
    securedGenericOfferService.remove(credentials, type);
  }

  private void deleteContract(final MetaData credentials, final Contract contract) {
    if (contract != null) {
      if (contract.isDetached()) {
        securedGenericContractService.delete(credentials, contract);
      }
      deleteCustomer(credentials, contract.getCustomer());
    }
  }

  private void deleteCustomer(final MetaData credentials, final Customer customer) {
    if (customer != null && customer.isDetached()) {
      securedGenericCustomerService.delete(credentials, customer);
    }
  }
}
