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

import java.util.List;

import br.com.netbrasoft.gnuob.generic.security.MetaData;

public abstract interface IGenericTypeWebService<T> {

  public abstract long count(MetaData credentials, T type);

  public abstract List<T> find(MetaData credentials, T type, Paging paging, OrderByEnum orderingProperty);

  public abstract T find(MetaData credentials, T type);

  public abstract T persist(MetaData credentials, T type);

  public abstract T merge(MetaData credentials, T type);

  public abstract T refresh(MetaData credentials, T type);

  public abstract void remove(MetaData credentials, T type);
}
