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

import javax.persistence.LockModeType;

public abstract interface IGenericTypeService<T> {

  public abstract long count(T type, Parameter... param);

  public abstract List<T> find(T type, Paging paging, OrderByEnum orderingProperty, Parameter... param);

  public abstract T find(T type, long id);

  public abstract T find(T type, long id, LockModeType lockModeType);

  public abstract void persist(T type);

  public abstract T merge(T type);

  public abstract T refresh(T type, long id);

  public abstract void remove(T type);

  public abstract void flush();

  public abstract void disableFilter(String filterName);

  public abstract void enableFilter(String filterName, Parameter... param);
}
