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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GROUP_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OTHERS_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OWNER_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PERMISSION_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PERMISSION_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.security.Rule.DELETE_ACCESS;
import static br.com.netbrasoft.gnuob.generic.security.Rule.READ_ACCESS;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import br.com.netbrasoft.gnuob.generic.AbstractType;

@Cacheable(value = true)
@Entity(name = PERMISSION_ENTITY_NAME)
@Table(name = PERMISSION_TABLE_NAME)
@Inheritance(strategy = SINGLE_TABLE)
@XmlRootElement(name = PERMISSION_ENTITY_NAME)
public class Permission extends AbstractType {

  private static final long serialVersionUID = 3108374497171836688L;

  private Rule group;
  private Rule others;
  private Rule owner;

  public static Permission getInstance() {
    return new Permission();
  }

  @Override
  @Transient
  public boolean isDetached() {
    return isAbstractTypeDetached();
  }

  @Override
  public void prePersist() {
    getGroup();
    getOwner();
    getOthers();
  }

  @Override
  public void preUpdate() {
    prePersist();
  }

  @XmlElement(required = true)
  @Column(name = GROUP_COLUMN_NAME, nullable = false)
  @Enumerated(STRING)
  public Rule getGroup() {
    if (group == null) {
      group = READ_ACCESS;
    }
    return group;
  }

  @XmlElement(required = true)
  @Column(name = OTHERS_COLUMN_NAME, nullable = false)
  @Enumerated(STRING)
  public Rule getOthers() {
    if (others == null) {
      others = READ_ACCESS;
    }
    return others;
  }

  @XmlElement(required = true)
  @Column(name = OWNER_COLUMN_NAME, nullable = false)
  @Enumerated(STRING)
  public Rule getOwner() {
    if (owner == null) {
      owner = DELETE_ACCESS;
    }
    return owner;
  }

  public void setGroup(final Rule group) {
    this.group = group;
  }

  public void setOthers(final Rule others) {
    this.others = others;
  }

  public void setOwner(final Rule owner) {
    this.owner = owner;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
