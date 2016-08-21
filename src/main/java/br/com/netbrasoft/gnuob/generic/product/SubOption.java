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

package br.com.netbrasoft.gnuob.generic.product;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DESCRIPTION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DISABLED_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SUB_OPTION_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SUB_OPTION_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.VALUE_COLUMN_NAME;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import br.com.netbrasoft.gnuob.generic.AbstractType;

@Cacheable(value = false)
@Entity(name = SUB_OPTION_ENTITY_NAME)
@Table(name = SUB_OPTION_TABLE_NAME)
@Inheritance(strategy = SINGLE_TABLE)
@XmlRootElement(name = SUB_OPTION_ENTITY_NAME)
public class SubOption extends AbstractType {

  private static final long serialVersionUID = -4350389615614303733L;

  private String description;
  private boolean disabled;
  private Integer position;
  private String value;

  @Override
  @Transient
  public boolean isDetached() {
    return isAbstractTypeDetached();
  }

  @XmlElement(required = true)
  @Column(name = DESCRIPTION_COLUMN_NAME, nullable = false)
  public String getDescription() {
    return description;
  }

  @XmlTransient
  @Column(name = POSITION_COLUMN_NAME)
  public Integer getPosition() {
    return position;
  }

  @XmlElement(required = true)
  @Column(name = VALUE_COLUMN_NAME, nullable = false)
  public String getValue() {
    return value;
  }

  @XmlElement(required = true)
  @Column(name = DISABLED_COLUMN_NAME, nullable = false)
  public boolean isDisabled() {
    return disabled;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public void setDisabled(final boolean disabled) {
    this.disabled = disabled;
  }

  public void setPosition(final Integer position) {
    this.position = position;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
