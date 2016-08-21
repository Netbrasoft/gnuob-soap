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

package br.com.netbrasoft.gnuob.generic.content;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_ENTITY_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_TABLE_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_XML_ELEMENT_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FORMAT_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MEDIUMBLOB_COLUMN_DEFINITION;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.NAME_COLUMN_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_COLUMN_NAME;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.velocity.context.Context;

import br.com.netbrasoft.gnuob.generic.content.contexts.IContextVisitor;
import br.com.netbrasoft.gnuob.generic.security.AbstractAccess;

@Cacheable(value = true)
@Entity(name = CONTENT_ENTITY_NAME)
@Table(name = CONTENT_TABLE_NAME)
@XmlRootElement(name = CONTENT_ENTITY_NAME)
public class Content extends AbstractAccess {

  private static final long serialVersionUID = -6963744731098668340L;

  private byte[] data;
  private String format;
  private String name;
  private Integer position;

  public static Content getInstance() {
    final Content content = new Content();
    content.setActive(true);
    return content;
  }

  public static Content getInstance(final String name) {
    final Content content = getInstance();
    content.setName(name);
    return content;
  }

  @Override
  @Transient
  public boolean isDetached() {
    return isAbstractTypeDetached();
  }

  @Override
  public Context accept(final IContextVisitor visitor) {
    return visitor.visit(this);
  }

  @XmlElement(name = CONTENT_XML_ELEMENT_NAME, required = true)
  @Column(name = CONTENT_COLUMN_NAME, columnDefinition = MEDIUMBLOB_COLUMN_DEFINITION, nullable = false)
  public byte[] getData() {
    return data;
  }

  @XmlElement(required = true)
  @Column(name = FORMAT_COLUMN_NAME, nullable = false)
  public String getFormat() {
    return format;
  }

  @XmlElement(required = true)
  @Column(name = NAME_COLUMN_NAME, nullable = false)
  public String getName() {
    return name;
  }

  @XmlTransient
  @Column(name = POSITION_COLUMN_NAME)
  public Integer getPosition() {
    return position;
  }

  public void setData(final byte[] data) {
    this.data = data;
  }

  public void setFormat(final String format) {
    this.format = format;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setPosition(final Integer position) {
    this.position = position;
  }
}
