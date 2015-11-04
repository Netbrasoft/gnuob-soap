package com.netbrasoft.gnuob.generic.content;

import java.util.Base64;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.velocity.context.Context;

import com.netbrasoft.gnuob.generic.content.contexts.ContextVisitor;
import com.netbrasoft.gnuob.generic.security.AbstractAccess;

@Cacheable(value = true)
@Entity(name = Content.ENTITY)
@Table(name = Content.TABLE)
@XmlRootElement(name = Content.ENTITY)
public class Content extends AbstractAccess {

  private static final long serialVersionUID = -6963744731098668340L;
  protected static final String ENTITY = "Content";
  protected static final String TABLE = "GNUOB_CONTENTS";

  @Column(name = "POSITION")
  private Integer position = 0;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "FORMAT", nullable = false)
  private String format;

  @Column(name = "CONTENT", columnDefinition = "MEDIUMBLOB", nullable = false)
  private byte[] data;

  @Override
  public Context accept(final ContextVisitor visitor) {
    return visitor.visit(this);
  }

  @XmlElement(name = "content", required = true)
  @XmlMimeType("application/octet-stream")
  public byte[] getData() {
    return Base64.getDecoder().decode(data);
  }

  @XmlElement(name = "format", required = true)
  public String getFormat() {
    return format;
  }

  @XmlElement(name = "name", required = true)
  public String getName() {
    return name;
  }

  @XmlTransient
  public Integer getPosition() {
    return position;
  }

  @Override
  public boolean isDetached() {
    return getId() > 0;
  }

  @Override
  public void prePersist() {
    return;
  }

  @Override
  public void preUpdate() {
    return;
  }

  public void setData(final byte[] data) {
    this.data = Base64.getEncoder().encode(data);
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
