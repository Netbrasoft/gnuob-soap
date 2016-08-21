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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ACCESS_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DESCRIPTION_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_ROLES_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_USERS_GNUOB_GROUPS_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_USERS_GNUOB_SITES_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_USERS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GROUPS_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.NAME_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PASSWORD_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PASSWORD_REGEX;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ROLE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ROOT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITES_ID_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.stream.Collectors.counting;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.velocity.context.Context;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.content.contexts.IContextVisitor;

@Cacheable(value = true)
@Entity(name = USER_ENTITY_NAME)
@Table(name = USER_TABLE_NAME)
@XmlRootElement(name = USER_ENTITY_NAME)
public class User extends AbstractAccess {

  private static final long serialVersionUID = 2439569681567208145L;

  private Rule access;
  private String description;
  private Set<Group> groups;
  private String name;
  private String password;
  private Set<Role> roles;
  private Boolean root;
  private Set<Site> sites;

  public User() {
    groups = newHashSet();
    roles = newHashSet();
    sites = newHashSet();
  }

  private User(final String name) {
    this();
    this.name = name;
  }

  public static User getInstance() {
    return new User();
  }

  public static User getInstance(final String name) {
    return new User(name);
  }

  @Transient
  @Override
  public boolean isDetached() {
    return newArrayList(isAbstractTypeDetached(), isSitesDetached(), isGroupsDetached()).stream()
        .filter(e -> e.booleanValue()).count() > ZERO;
  }

  @Transient
  private boolean isSitesDetached() {
    return sites != null && sites.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Transient
  private boolean isGroupsDetached() {
    return groups != null && groups.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Override
  public void prePersist() {
    if (!password.matches(PASSWORD_REGEX)) {
      throw new GNUOpenBusinessServiceException(
          format("Given user [%s] doesn't contain a valid password, verify that the given password is valid", name));
    }
  }

  @Override
  public void preUpdate() {
    prePersist();
  }

  @Override
  public Context accept(final IContextVisitor visitor) {
    return visitor.visit(this);
  }

  @Column(name = ACCESS_COLUMN_NAME, nullable = false)
  @Enumerated(STRING)
  public Rule getAccess() {
    return access;
  }

  @XmlElement
  @Column(name = DESCRIPTION_COLUMN_NAME)
  public String getDescription() {
    return description;
  }

  @ManyToMany(cascade = {PERSIST}, fetch = EAGER)
  @JoinTable(name = GNUOB_USERS_GNUOB_GROUPS_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_USERS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = GROUPS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<Group> getGroups() {
    return groups;
  }

  @XmlElement(required = true)
  @Column(name = NAME_COLUMN_NAME, nullable = false, unique = true)
  public String getName() {
    return name;
  }

  @XmlElement(required = true)
  @Column(name = PASSWORD_COLUMN_NAME, nullable = false)
  public String getPassword() {
    return password;
  }

  @ElementCollection(targetClass = Role.class, fetch = EAGER)
  @JoinTable(name = GNUOB_ROLES_COLUMN_NAME, joinColumns = @JoinColumn(name = GNUOB_USERS_ID_COLUMN_NAME))
  @Column(name = ROLE)
  @Enumerated(STRING)
  public Set<Role> getRoles() {
    return roles;
  }

  @XmlTransient
  @Column(name = ROOT_COLUMN_NAME)
  public Boolean getRoot() {
    return root;
  }

  @ManyToMany(cascade = {PERSIST}, fetch = EAGER)
  @JoinTable(name = GNUOB_USERS_GNUOB_SITES_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_USERS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = SITES_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<Site> getSites() {
    return sites;
  }

  public void setAccess(final Rule access) {
    this.access = access;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public void setGroups(final Set<Group> groups) {
    this.groups = groups;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public void setRoles(final Set<Role> roles) {
    this.roles = roles;
  }

  public void setRoot(final Boolean root) {
    this.root = root;
  }

  public void setSites(final Set<Site> sites) {
    this.sites = sites;
  }
}
