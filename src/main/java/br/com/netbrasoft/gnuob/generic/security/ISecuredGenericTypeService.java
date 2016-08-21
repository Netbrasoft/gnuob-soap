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

import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.CREATE;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.DELETE;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.READ;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.UPDATE;

import java.util.List;

import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.Parameter;

public abstract interface ISecuredGenericTypeService<T> {

  public abstract long count(MetaData credentials, T type, Parameter... param);

  @OperationAccess(operation = CREATE)
  public default void create(MetaData credentials, T type) {}

  @OperationAccess(operation = DELETE)
  public default void delete(MetaData credentials, T type) {}

  public abstract T find(MetaData credentials, T type, long id);

  public abstract List<T> find(MetaData credentials, T type, Paging paging, OrderByEnum orderingProperty,
      Parameter... param);

  public abstract void flush();

  public abstract T merge(MetaData credentials, T type);

  public abstract void persist(MetaData credentials, T type);

  @OperationAccess(operation = READ)
  public default void read(MetaData credentials, T type) {}

  public abstract T refresh(MetaData credentials, T type, long id);

  public abstract void remove(MetaData credentials, T type);

  @OperationAccess(operation = UPDATE)
  public default void update(MetaData credentials, T type) {}
}
