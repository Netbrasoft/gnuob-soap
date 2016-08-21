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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GENERIC_TYPE_DAO_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PRIMARY_UNIT_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.hibernate.Filter;
import org.hibernate.Session;

import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@Stateless(name = GENERIC_TYPE_DAO_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class GenericTypeDaoImpl<T> implements IGenericTypeDao<T> {

  @PersistenceContext(unitName = PRIMARY_UNIT_NAME)
  private EntityManager entityManager;

  @Override
  public LockModeType getLockModeTypeOfType(final T type) {
    return entityManager.getLockMode(type);
  }

  @Override
  public void lock(final T type, final LockModeType lockModeType) {
    entityManager.lock(type, lockModeType);
  }

  @SuppressWarnings(UNCHECKED_VALUE)
  @Override
  public T find(final T type, final long id, final LockModeType lockModeType) {
    return (T) entityManager.find(type.getClass(), id, lockModeType);
  }

  @Override
  public void persist(final T type) {
    entityManager.persist(type);
  }

  @Override
  public T merge(final T type) {
    return entityManager.merge(type);
  }

  @Override
  public T refresh(final T type, final long id, final LockModeType lockModeType) {
    if (!entityManager.contains(type)) {
      return find(type, id, lockModeType);
    }
    entityManager.refresh(type);
    return type;
  }

  @Override
  public void remove(final T type) {
    entityManager.remove(entityManager.contains(type) ? type : entityManager.merge(type));
  }

  @Override
  public boolean contains(final T type) {
    return entityManager.contains(type);
  }

  @Override
  public void flush() {
    entityManager.flush();
  }

  @Override
  public void disableFilter(final String filterName) {
    getDelegate().disableFilter(filterName);
  }

  @Override
  public void enableFilter(final String fiterName, final Parameter... param) {
    setFilterParameters(getDelegate().enableFilter(fiterName), param);
  }

  private void setFilterParameters(final Filter filter, final Parameter... param) {
    Arrays.asList(param).stream().forEach(e -> filter.setParameter(e.getName(), e.getValue()));
  }

  @Override
  public Session getDelegate() {
    return (Session) entityManager.getDelegate();
  }

  @Override
  public boolean isOpen() {
    return entityManager.isOpen();
  }
}
