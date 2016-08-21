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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.RULE_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.CREATE;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.DELETE;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.NONE;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.READ;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.UPDATE;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@XmlRootElement(name = RULE_ENTITY_NAME)
public enum Rule {
//@formatter:off
  CREATE_ACCESS(newArrayList(CREATE, READ)),
  DELETE_ACCESS(newArrayList(CREATE, READ, UPDATE, DELETE)),
  NONE_ACCESS(newArrayList(NONE)),
  READ_ACCESS(newArrayList(READ)),
  UPDATE_ACCESS(newArrayList(CREATE, READ, UPDATE));
//@formatter:on

  public enum Operation {
    CREATE, DELETE, NONE, READ, UPDATE;
  }

  private List<Operation> operations;

  private Rule(final List<Operation> operations) {
    this.operations = operations;
  }

  public List<Operation> getOperations() {
    return operations;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
