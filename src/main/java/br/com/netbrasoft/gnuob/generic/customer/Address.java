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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADDRESS_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ADDRESS_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CITY_NAME_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COMPLEMENT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNTRY_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.COUNTRY_NAME_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DISTRICT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INTERNATIONAL_STATE_AND_CITY_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.INTERNATIONAL_STREET_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.NUMBER_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PHONE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSTAL_CODE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.STATE_OR_PROVINCE_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.STREET1_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.STREET2_COLUMN_NAME;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

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
@Entity(name = ADDRESS_ENTITY_NAME)
@Table(name = ADDRESS_TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement(name = ADDRESS_ENTITY_NAME)
public class Address extends AbstractType {

  private static final long serialVersionUID = 1448149897957104670L;

  private String cityName;
  private String complement;
  private String country;
  private String countryName;
  private String district;
  private String internationalStateAndCity;
  private String internationalStreet;
  private String number;
  private String phone;
  private String postalCode;
  private String stateOrProvince;
  private String street1;
  private String street2;

  @Override
  @Transient
  public boolean isDetached() {
    return isAbstractTypeDetached();
  }

  @XmlElement(required = true)
  @Column(name = CITY_NAME_COLUMN_NAME, nullable = false)
  public String getCityName() {
    return cityName;
  }

  @XmlElement
  @Column(name = COMPLEMENT_COLUMN_NAME)
  public String getComplement() {
    return complement;
  }

  @XmlElement(required = true)
  @Column(name = COUNTRY_COLUMN_NAME, nullable = false)
  public String getCountry() {
    return country;
  }

  @XmlElement
  @Column(name = COUNTRY_NAME_COLUMN_NAME)
  public String getCountryName() {
    return countryName;
  }

  @XmlElement
  @Column(name = DISTRICT_COLUMN_NAME)
  public String getDistrict() {
    return district;
  }

  @XmlElement
  @Column(name = INTERNATIONAL_STATE_AND_CITY_COLUMN_NAME)
  public String getInternationalStateAndCity() {
    return internationalStateAndCity;
  }

  @XmlElement
  @Column(name = INTERNATIONAL_STREET_COLUMN_NAME)
  public String getInternationalStreet() {
    return internationalStreet;
  }

  @XmlElement
  @Column(name = NUMBER_COLUMN_NAME)
  public String getNumber() {
    return number;
  }

  @XmlElement
  @Column(name = PHONE_COLUMN_NAME)
  public String getPhone() {
    return phone;
  }

  @XmlElement(required = true)
  @Column(name = POSTAL_CODE_COLUMN_NAME, nullable = false)
  public String getPostalCode() {
    return postalCode;
  }

  @XmlElement(required = true)
  @Column(name = STATE_OR_PROVINCE_COLUMN_NAME, nullable = false)
  public String getStateOrProvince() {
    return stateOrProvince;
  }

  @XmlElement(required = true)
  @Column(name = STREET1_COLUMN_NAME, nullable = false)
  public String getStreet1() {
    return street1;
  }

  @XmlElement
  @Column(name = STREET2_COLUMN_NAME)
  public String getStreet2() {
    return street2;
  }

  public void setCityName(final String cityName) {
    this.cityName = cityName;
  }

  public void setComplement(final String complement) {
    this.complement = complement;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public void setCountryName(final String countryName) {
    this.countryName = countryName;
  }

  public void setDistrict(final String district) {
    this.district = district;
  }

  public void setInternationalStateAndCity(final String internationalStateAndCity) {
    this.internationalStateAndCity = internationalStateAndCity;
  }

  public void setInternationalStreet(final String internationalStreet) {
    this.internationalStreet = internationalStreet;
  }

  public void setNumber(final String number) {
    this.number = number;
  }

  public void setPhone(final String phone) {
    this.phone = phone;
  }

  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  public void setStateOrProvince(final String stateOrProvince) {
    this.stateOrProvince = stateOrProvince;
  }

  public void setStreet1(final String street1) {
    this.street1 = street1;
  }

  public void setStreet2(final String street2) {
    this.street2 = street2;
  }

  @Override
  public String toString() {
    return reflectionToString(this, SHORT_PREFIX_STYLE);
  }
}
