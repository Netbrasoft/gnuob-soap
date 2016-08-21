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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.META_DATA_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PASSWORD_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER_PARAM_NAME;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@XmlRootElement(name = META_DATA_ENTITY_NAME)
@XmlType(propOrder = {SITE_PARAM_NAME, USER_PARAM_NAME, PASSWORD_PARAM_NAME})
public class MetaData {

  private String password;
  private String site;
  private String user;

  public MetaData() {
    this(null, null, null);
  }

  private MetaData(final String site, final String user, final String password) {
    this.site = site;
    this.user = user;
    this.password = password;
  }

  public static MetaData getInstance() {
    return new MetaData();
  }

  public static MetaData getInstance(final String site, final String user, final String password) {
    return new MetaData(site, user, password);
  }

  @XmlElement(required = true)
  public String getPassword() {
    return password;
  }

  @XmlElement(required = true)
  public String getSite() {
    return site;
  }

  @XmlElement(required = true)
  public String getUser() {
    return user;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public void setSite(final String site) {
    this.site = site;
  }

  public void setUser(final String user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
