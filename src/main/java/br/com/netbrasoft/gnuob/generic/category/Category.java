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

package br.com.netbrasoft.gnuob.generic.category;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CATEGORY_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CATEGORY_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENTS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DESCRIPTION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_CATEGORIES_GNUOB_CONTENTS_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_CATEGORIES_GNUOB_SUB_CATEGORIES_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_CATEGORIES_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.NAME_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_BY_PROPERTY_POSITION_ASC;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.START_POSITION_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SUB_CATEGORIES_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.valueOf;
import static java.util.stream.Collectors.counting;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.velocity.context.Context;
import org.hibernate.annotations.Cache;

import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.content.contexts.IContextVisitor;
import br.com.netbrasoft.gnuob.generic.security.AbstractAccess;

@Cacheable(value = true)
@Entity(name = CATEGORY_ENTITY_NAME)
@Table(name = CATEGORY_TABLE_NAME)
@XmlRootElement(name = CATEGORY_ENTITY_NAME)
public class Category extends AbstractAccess {

  private static final long serialVersionUID = 8531470310780646179L;

  private Set<Content> contents;
  private String description;
  private String name;
  private Integer position;
  private Set<SubCategory> subCategories;

  public Category() {
    contents = newHashSet();
    subCategories = newHashSet();
  }

  @Override
  @Transient
  public boolean isDetached() {
    return newArrayList(isAbstractTypeDetached(), isContentsDetached(), isSubCategoriesDetached()).stream()
        .filter(e -> e.booleanValue()).count() > ZERO;
  }

  @Transient
  private boolean isContentsDetached() {
    return contents.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Transient
  private boolean isSubCategoriesDetached() {
    return subCategories.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Override
  public void prePersist() {
    reinitAllSubCategoryPositions();
    reinitAllContentPositions();
  }

  @Override
  public void preUpdate() {
    prePersist();
  }

  private void reinitAllContentPositions() {
    int startingByPosition = START_POSITION_VALUE;
    for (final Content content : contents) {
      content.setPosition(valueOf(startingByPosition++));
    }
  }

  private void reinitAllSubCategoryPositions() {
    int startingByPosition = START_POSITION_VALUE;
    for (final SubCategory subCategory : subCategories) {
      subCategory.setPosition(valueOf(startingByPosition++));
    }
  }

  @Override
  public Context accept(final IContextVisitor visitor) {
    return visitor.visit(this);
  }

  @Cache(usage = READ_ONLY)
  @OrderBy(ORDER_BY_PROPERTY_POSITION_ASC)
  @OneToMany(cascade = {PERSIST, MERGE}, fetch = EAGER)
  @JoinTable(name = GNUOB_CATEGORIES_GNUOB_CONTENTS_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_CATEGORIES_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = CONTENTS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<Content> getContents() {
    return contents;
  }

  @XmlElement(required = true)
  @Column(name = DESCRIPTION_COLUMN_NAME, nullable = false)
  public String getDescription() {
    return description;
  }

  @XmlElement(required = true)
  @Column(name = NAME_COLUMN_NAME, nullable = false)
  public String getName() {
    return name;
  }

  @XmlElement
  @Column(name = POSITION_COLUMN_NAME)
  public Integer getPosition() {
    return position;
  }

  @OneToMany(cascade = {PERSIST, MERGE, REMOVE}, fetch = EAGER)
  @OrderBy(ORDER_BY_PROPERTY_POSITION_ASC)
  @JoinTable(name = GNUOB_CATEGORIES_GNUOB_SUB_CATEGORIES_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_CATEGORIES_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = SUB_CATEGORIES_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<SubCategory> getSubCategories() {
    return subCategories;
  }

  public void setContents(final Set<Content> contents) {
    this.contents = contents;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPosition(final Integer position) {
    this.position = position;
  }

  public void setSubCategories(final Set<SubCategory> subCategories) {
    this.subCategories = subCategories;
  }
}
