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
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_OPTIONS_GNUOB_SUB_OPTIONS_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_OPTIONS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OPTION_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OPTION_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_ASC;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.START_POSITION_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SUB_OPTIONS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.VALUE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.counting;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import br.com.netbrasoft.gnuob.generic.AbstractType;

@Cacheable(value = false)
@Entity(name = OPTION_ENTITY_NAME)
@Table(name = OPTION_TABLE_NAME)
@Inheritance(strategy = SINGLE_TABLE)
@XmlRootElement(name = OPTION_ENTITY_NAME)
public class Option extends AbstractType {

  private static final long serialVersionUID = -4350389615614303733L;

  private String description;
  private boolean disabled;
  private Integer position;
  private Set<SubOption> subOptions;
  private String value;

  public Option() {
    subOptions = newHashSet();
  }

  @Override
  @Transient
  public boolean isDetached() {
    return newArrayList(isAbstractTypeDetached(), isSubOptionsDetached()).stream().filter(e -> e.booleanValue())
        .count() > ZERO;
  }

  @Transient
  private boolean isSubOptionsDetached() {
    return subOptions != null && subOptions.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Override
  public void prePersist() {
    reinitAllPositionSubOptions();
  }

  @Override
  public void preUpdate() {
    prePersist();
  }

  private void reinitAllPositionSubOptions() {
    int startPosition = START_POSITION_VALUE;
    for (final SubOption subOption : subOptions) {
      subOption.setPosition(Integer.valueOf(startPosition++));
    }
  }

  @XmlElement(required = true)
  @Column(name = DESCRIPTION_COLUMN_NAME, nullable = false)
  public String getDescription() {
    return description;
  }

  @XmlElement
  @Column(name = POSITION_COLUMN_NAME)
  public Integer getPosition() {
    return position;
  }

  @OrderBy(POSITION_ASC)
  @OneToMany(cascade = {PERSIST, MERGE, REMOVE, REFRESH}, fetch = EAGER)
  @JoinTable(name = GNUOB_OPTIONS_GNUOB_SUB_OPTIONS_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_OPTIONS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = SUB_OPTIONS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<SubOption> getSubOptions() {
    return subOptions;
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

  public void setSubOptions(final Set<SubOption> subOptions) {
    this.subOptions = subOptions;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
