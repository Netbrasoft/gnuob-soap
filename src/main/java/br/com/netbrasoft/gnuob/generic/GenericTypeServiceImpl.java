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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CREATION_PROPERTY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GENERIC_TYPE_DAO_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GENERIC_TYPE_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ID_PROPERTY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MODIFICATION_PROPERTY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.NONE;
import static javax.persistence.LockModeType.OPTIMISTIC;
import static org.hibernate.criterion.CriteriaSpecification.DISTINCT_ROOT_ENTITY;
import static org.hibernate.criterion.Example.create;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Projections.rowCount;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.in;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.LockModeType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.security.OperationAccess;
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@Stateless(name = GENERIC_TYPE_SERVICE_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class GenericTypeServiceImpl<T> implements IGenericTypeService<T> {

  @EJB(beanName = GENERIC_TYPE_DAO_IMPL_NAME)
  private IGenericTypeDao<T> genericTypeDao;

  public GenericTypeServiceImpl() {
    // This constructor will be used by the EJB container.
  }

  GenericTypeServiceImpl(final IGenericTypeDao<T> genericTypeDao) {
    this.genericTypeDao = genericTypeDao;
  }

  @Override
  @OperationAccess(operation = NONE)
  public long count(final T exampleType, final Parameter... param) {
    return (long) setParameters(
        createCriteriaByType(exampleType).add(
            create(exampleType).excludeProperty(CREATION_PROPERTY_NAME).excludeProperty(MODIFICATION_PROPERTY_NAME)),
        param).setProjection(projectionList().add(property(ID_PROPERTY_NAME)).add(rowCount()))
            .setResultTransformer(DISTINCT_ROOT_ENTITY).setCacheable(true).uniqueResult();
  }

  private Criteria createCriteriaByType(final T type) {
    return genericTypeDao.getDelegate().createCriteria(type.getClass());
  }

  private Criteria setParameters(final Criteria criteria, final Parameter... param) {
    Arrays.asList(param).stream().forEach(e -> criteria.createCriteria(e.getName()).add((Criterion) e.getValue()));
    return criteria;
  }

  @Override
  @SuppressWarnings(UNCHECKED_VALUE)
  @OperationAccess(operation = NONE)
  public List<T> find(final T type, final Paging paging, final OrderByEnum orderingProperty, final Parameter... param) {
    return setOrderingProperty(orderingProperty,
        createCriteriaByType(type).add(in(ID_PROPERTY_NAME, findIds(type, paging, param))))
            .setResultTransformer(DISTINCT_ROOT_ENTITY).setCacheable(true).list();
  }

  private Criteria setOrderingProperty(final OrderByEnum orderingProperty, final Criteria criteria) {
    switch (orderingProperty.getOrder()) {
      case ASC:
        return orderingProperty.getAlias() != null ? criteria.addOrder(asc(orderingProperty.getColumn()))
            .createAlias(orderingProperty.getAssociationPath(), orderingProperty.getAlias())
            : criteria.addOrder(asc(orderingProperty.getColumn()));
      case DESC:
        return orderingProperty.getAlias() != null ? criteria.addOrder(desc(orderingProperty.getColumn()))
            .createAlias(orderingProperty.getAssociationPath(), orderingProperty.getAlias())
            : criteria.addOrder(desc(orderingProperty.getColumn()));
      case DEFAULT:
      default:
        return criteria;
    }
  }

  @SuppressWarnings(UNCHECKED_VALUE)
  @OperationAccess(operation = NONE)
  private List<Long> findIds(final T exampleType, final Paging paging, final Parameter... param) {
    return setParameters(setFirst(paging,
        getMax(paging,
            createCriteriaByType(exampleType).add(create(exampleType).excludeZeroes().enableLike()
                .excludeProperty(CREATION_PROPERTY_NAME).excludeProperty(MODIFICATION_PROPERTY_NAME)))),
        param).setProjection(projectionList().add(property(ID_PROPERTY_NAME)))
            .setResultTransformer(DISTINCT_ROOT_ENTITY).setCacheable(true).list();
  }

  private Criteria setFirst(final Paging paging, final Criteria criteria) {
    return paging.getFirst() > ZERO ? criteria.setFirstResult(paging.getFirst()) : criteria;
  }

  private Criteria getMax(final Paging paging, final Criteria criteria) {
    return paging.getMax() > ZERO ? criteria.setMaxResults(paging.getMax()) : criteria;
  }

  @Override
  @OperationAccess(operation = NONE)
  public T find(final T type, final long id, final LockModeType lockModeType) {
    return genericTypeDao.find(type, id, lockModeType);
  }

  @Override
  @OperationAccess(operation = NONE)
  public T find(final T type, final long id) {
    final List<T> list = findTypeById(type, id);
    if (list.isEmpty()) {
      throw new GNUOpenBusinessServiceException("No entity found by the given id and type (ERR01)");
    }
    return list.iterator().next();
  }

  @SuppressWarnings(UNCHECKED_VALUE)
  private List<T> findTypeById(final T type, final long id) {
    return createCriteriaByType(type).add(eq(ID_PROPERTY_NAME, id)).setResultTransformer(DISTINCT_ROOT_ENTITY)
        .setCacheable(true).list();
  }

  @Override
  @OperationAccess(operation = NONE)
  public T merge(final T type) {
    return genericTypeDao.merge(type);
  }

  @Override
  @OperationAccess(operation = NONE)
  public void persist(final T type) {
    genericTypeDao.persist(type);
  }

  @Override
  @OperationAccess(operation = NONE)
  public T refresh(final T type, final long id) {
    return genericTypeDao.refresh(type, id, OPTIMISTIC);
  }

  @Override
  @OperationAccess(operation = NONE)
  public void remove(final T type) {
    genericTypeDao.remove(type);
  }

  @Override
  @OperationAccess(operation = NONE)
  public void flush() {
    genericTypeDao.flush();
  }

  @Override
  @OperationAccess(operation = NONE)
  public void disableFilter(final String filterName) {
    genericTypeDao.disableFilter(filterName);
  }

  @Override
  @OperationAccess(operation = NONE)
  public void enableFilter(final String filterName, final Parameter... param) {
    genericTypeDao.enableFilter(filterName, param);
  }
}
