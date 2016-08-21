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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.CREATE;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.DELETE;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.READ;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.UPDATE;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import br.com.netbrasoft.gnuob.generic.GenericTypeServiceImpl;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.Parameter;
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@Stateless(name = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME, mappedName = SECURED_GENERIC_TYPE_SERVICE_IMPL_NAME)
@Interceptors(value = {AccessControl.class, AppSimonInterceptor.class})
public class SecuredGenericTypeServiceImpl<T> extends GenericTypeServiceImpl<T>
    implements ISecuredGenericTypeService<T> {

  @Override
  @OperationAccess(operation = READ)
  public long count(final MetaData credentials, final T type, final Parameter... param) {
    return super.count(type, param);
  }

  @Override
  @OperationAccess(operation = READ)
  public T find(final MetaData credentials, final T type, final long id) {
    return super.find(type, id);
  }

  @Override
  @OperationAccess(operation = READ)
  public List<T> find(final MetaData credentials, final T type, final Paging paging, final OrderByEnum orderingProperty,
      final Parameter... param) {
    return super.find(type, paging, orderingProperty, param);
  }

  @Override
  @OperationAccess(operation = UPDATE)
  public T merge(final MetaData credentials, final T type) {
    return super.merge(type);
  }

  @Override
  @OperationAccess(operation = CREATE)
  public void persist(final MetaData credentials, final T type) {
    super.persist(type);
  }

  @Override
  @OperationAccess(operation = READ)
  public T refresh(final MetaData credentials, final T type, final long id) {
    return super.refresh(type, id);
  }

  @Override
  @OperationAccess(operation = DELETE)
  public void remove(final MetaData credentials, final T type) {
    super.remove(type);
  }
}
