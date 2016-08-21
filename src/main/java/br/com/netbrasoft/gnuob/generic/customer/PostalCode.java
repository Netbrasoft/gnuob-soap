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

package br.com.netbrasoft.gnuob.generic.customer;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ACCURACY_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADMIN_CODE_1_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADMIN_CODE_2_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADMIN_CODE_3_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADMIN_NAME_1_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADMIN_NAME_2_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADMIN_NAME_3_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNTRY_CODE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.LATITUDE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.LONGITUDE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PLACE_NAME_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSTAL_CODE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSTAL_CODE_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSTAL_CODE_TABLE_NAME;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import br.com.netbrasoft.gnuob.generic.AbstractType;

@Cacheable(value = false)
@Entity(name = POSTAL_CODE_ENTITY_NAME)
@Table(name = POSTAL_CODE_TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement(name = POSTAL_CODE_ENTITY_NAME)
public class PostalCode extends AbstractType {

  private static final long serialVersionUID = -3917024445691566656L;

  private String accuracy;
  private String adminCode1;
  private String adminCode2;
  private String adminCode3;
  private String adminName1;
  private String adminName2;
  private String adminName3;
  private String countryCode;
  private BigDecimal latitude;
  private BigDecimal longitude;
  private String placeName;
  private String postalCode;

  @Override
  @Transient
  public boolean isDetached() {
    return isAbstractTypeDetached();
  }

  @XmlElement
  @Column(name = ACCURACY_COLUMN_NAME)
  public String getAccuracy() {
    return accuracy;
  }

  @XmlElement(required = true)
  @Column(name = ADMIN_CODE_1_COLUMN_NAME, nullable = false)
  public String getAdminCode1() {
    return adminCode1;
  }

  @XmlElement
  @Column(name = ADMIN_CODE_2_COLUMN_NAME)
  public String getAdminCode2() {
    return adminCode2;
  }

  @XmlElement
  @Column(name = ADMIN_CODE_3_COLUMN_NAME)
  public String getAdminCode3() {
    return adminCode3;
  }

  @XmlElement(required = true)
  @Column(name = ADMIN_NAME_1_COLUMN_NAME, nullable = false)
  public String getAdminName1() {
    return adminName1;
  }

  @XmlElement
  @Column(name = ADMIN_NAME_2_COLUMN_NAME)
  public String getAdminName2() {
    return adminName2;
  }

  @XmlElement
  @Column(name = ADMIN_NAME_3_COLUMN_NAME)
  public String getAdminName3() {
    return adminName3;
  }

  @XmlElement(required = true)
  @Column(name = COUNTRY_CODE_COLUMN_NAME, nullable = false)
  public String getCountryCode() {
    return countryCode;
  }

  @XmlElement(required = true)
  @Column(name = LATITUDE_COLUMN_NAME, nullable = false)
  public BigDecimal getLatitude() {
    return latitude;
  }

  @XmlElement(required = true)
  @Column(name = LONGITUDE_COLUMN_NAME, nullable = false)
  public BigDecimal getLongitude() {
    return longitude;
  }

  @XmlElement(required = true)
  @Column(name = PLACE_NAME_COLUMN_NAME, nullable = false)
  public String getPlaceName() {
    return placeName;
  }

  @XmlElement(required = true)
  @Column(name = POSTAL_CODE_COLUMN_NAME, nullable = false)
  public String getPostalCode() {
    return postalCode;
  }

  public void setAccuracy(final String accuracy) {
    this.accuracy = accuracy;
  }

  public void setAdminCode1(final String adminCode1) {
    this.adminCode1 = adminCode1;
  }

  public void setAdminCode2(final String adminCode2) {
    this.adminCode2 = adminCode2;
  }

  public void setAdminCode3(final String adminCode3) {
    this.adminCode3 = adminCode3;
  }

  public void setAdminName1(final String adminName1) {
    this.adminName1 = adminName1;
  }

  public void setAdminName2(final String adminName2) {
    this.adminName2 = adminName2;
  }

  public void setAdminName3(final String adminName3) {
    this.adminName3 = adminName3;
  }

  public void setCountryCode(final String countryCode) {
    this.countryCode = countryCode;
  }

  public void setLatitude(final BigDecimal latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(final BigDecimal longitude) {
    this.longitude = longitude;
  }

  public void setPlaceName(final String placeName) {
    this.placeName = placeName;
  }

  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
