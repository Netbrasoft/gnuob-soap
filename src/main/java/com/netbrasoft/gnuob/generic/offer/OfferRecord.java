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

package com.netbrasoft.gnuob.generic.offer;

import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.AMOUNT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DESCRIPTION_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.DISCOUNT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_OFFER_RECORDS_GNUOB_OPTIONS_TABLE_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_OFFER_RECORDS_ID_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ID_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_HEIGHT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_HEIGHT_UNIT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_LENGTH_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_LENGTH_UNIT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_URL_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_WEIGHT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_WEIGHT_UNIT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_WIDTH_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ITEM_WIDTH_UNIT_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.NAME_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OFFER_RECORD_ENTITY_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OFFER_RECORD_ID_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OFFER_RECORD_TABLE_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OPTIONS_ID_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OPTION_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_ASC;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.POSITION_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PRODUCT_NUMBER_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.QUANTITY_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SHIPPING_COST_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.START_POSITION_VALUE;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TAX_COLUMN_NAME;
import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ZERO;
import static java.util.stream.Collectors.counting;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.netbrasoft.gnuob.generic.AbstractType;
import com.netbrasoft.gnuob.generic.product.Option;
import com.netbrasoft.gnuob.generic.product.Product;

@Cacheable(value = false)
@Entity(name = OFFER_RECORD_ENTITY_NAME)
@Table(name = OFFER_RECORD_TABLE_NAME)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement(name = OFFER_RECORD_ENTITY_NAME)
public class OfferRecord extends AbstractType {

  private static final long serialVersionUID = 710723043281502969L;

  private BigDecimal amount;
  private String description;
  private BigDecimal discount;
  private BigDecimal itemHeight;
  private String itemHeightUnit;
  private BigDecimal itemLength;
  private String itemLengthUnit;
  private String itemUrl;
  private BigDecimal itemWeight;
  private String itemWeightUnit;
  private BigDecimal itemWidth;
  private String itemWidthUnit;
  private String name;
  private String offerRecordId;
  private String option;
  private Set<Option> options;
  private Integer position;
  private Product product;
  private String productNumber;
  private BigInteger quantity;
  private BigDecimal shippingCost;
  private BigDecimal tax;

  public OfferRecord() {
    options = new HashSet<>(0);
  }

  @Override
  @Transient
  public boolean isDetached() {
    return Arrays.asList(new Boolean[] {isAbstractTypeDetached(), isOptionsDetached()}).stream()
        .filter(e -> e.booleanValue()).count() > 0;
  }

  @Transient
  private boolean isOptionsDetached() {
    return options != null && options.stream().filter(e -> e.isDetached()).collect(counting()).intValue() > ZERO;
  }

  @Override
  public void prePersist() {
    getOfferRecordId();
    getName();
    getDescription();
    getAmount();
    getProductNumber();
    getTax();
    getShippingCost();
    getItemWeight();
    getItemWeightUnit();
    getItemLength();
    getItemLengthUnit();
    getItemHeight();
    getItemHeightUnit();
    getItemUrl();
    reinitAllPositionOptions();
  }

  @Override
  public void preUpdate() {
    prePersist();
  }

  private void reinitAllPositionOptions() {
    int startPosition = START_POSITION_VALUE;
    for (final Option opt : options) {
      opt.setPosition(Integer.valueOf(startPosition++));
    }
  }

  @XmlElement
  @Column(name = AMOUNT_COLUMN_NAME)
  public BigDecimal getAmount() {
    if (product != null && amount == null) {
      amount = product.getAmount().subtract(getDiscount());
    }
    return amount;
  }

  @XmlElement
  @Column(name = DESCRIPTION_COLUMN_NAME)
  public String getDescription() {
    if (product != null && StringUtils.isBlank(description)) {
      description = product.getDescription();
    }
    return description;
  }

  @XmlElement
  @Column(name = DISCOUNT_COLUMN_NAME)
  public BigDecimal getDiscount() {
    if (product != null && discount == null) {
      discount = product.getDiscount();
    }
    return discount;
  }

  @Transient
  @XmlTransient
  public BigDecimal getDiscountTotal() {
    if (getDiscount() != null && getQuantity() != null) {
      return getDiscount().multiply(new BigDecimal(getQuantity()));
    }
    return BigDecimal.ZERO;
  }

  @XmlElement
  @Column(name = ITEM_HEIGHT_COLUMN_NAME)
  public BigDecimal getItemHeight() {
    if (product != null && itemHeight == null) {
      itemHeight = product.getItemHeight();
    }
    return itemHeight;
  }

  @XmlElement
  @Column(name = ITEM_HEIGHT_UNIT_COLUMN_NAME)
  public String getItemHeightUnit() {
    if (product != null && StringUtils.isBlank(itemHeightUnit)) {
      itemHeightUnit = product.getItemHeightUnit();
    }
    return itemHeightUnit;
  }

  @XmlElement
  @Column(name = ITEM_LENGTH_COLUMN_NAME)
  public BigDecimal getItemLength() {
    if (product != null && itemLength == null) {
      itemLength = product.getItemLength();
    }
    return itemLength;
  }

  @XmlElement
  @Column(name = ITEM_LENGTH_UNIT_COLUMN_NAME)
  public String getItemLengthUnit() {
    if (product != null && StringUtils.isBlank(itemLengthUnit)) {
      itemLengthUnit = product.getItemLengthUnit();
    }
    return itemLengthUnit;
  }

  @Transient
  @XmlTransient
  public BigDecimal getItemTotal() {
    if (getAmount() != null && getQuantity() != null) {
      return getAmount().multiply(new BigDecimal(getQuantity()));
    }
    return BigDecimal.ZERO;
  }

  @XmlElement
  @Column(name = ITEM_URL_COLUMN_NAME)
  public String getItemUrl() {
    if (product != null && StringUtils.isBlank(itemUrl)) {
      itemUrl = product.getItemUrl();
    }
    return itemUrl;
  }

  @XmlElement
  @Column(name = ITEM_WEIGHT_COLUMN_NAME)
  public BigDecimal getItemWeight() {
    if (product != null && itemWeight == null) {
      itemWeight = product.getItemWeight();
    }
    return itemWeight;
  }

  @XmlElement
  @Column(name = ITEM_WEIGHT_UNIT_COLUMN_NAME)
  public String getItemWeightUnit() {
    if (product != null && StringUtils.isBlank(itemWeightUnit)) {
      itemWeightUnit = product.getItemWeightUnit();
    }
    return itemWeightUnit;
  }

  @XmlElement
  @Column(name = ITEM_WIDTH_COLUMN_NAME)
  public BigDecimal getItemWidth() {
    if (product != null && itemWidth == null) {
      itemWidth = product.getItemWidth();
    }
    return itemWidth;
  }

  @XmlElement
  @Column(name = ITEM_WIDTH_UNIT_COLUMN_NAME)
  public String getItemWidthUnit() {
    if (product != null && StringUtils.isBlank(itemWidthUnit)) {
      itemWidthUnit = product.getItemWidthUnit();
    }
    return itemWidthUnit;
  }

  @XmlElement
  @Column(name = NAME_COLUMN_NAME)
  public String getName() {
    if (product != null && StringUtils.isBlank(name)) {
      name = product.getName();
    }
    return name;
  }

  @XmlElement
  @Column(name = OFFER_RECORD_ID_COLUMN_NAME, nullable = false)
  public String getOfferRecordId() {
    if (StringUtils.isBlank(offerRecordId)) {
      offerRecordId = UUID.randomUUID().toString();
    }
    return offerRecordId;
  }

  @XmlElement
  @Column(name = OPTION_COLUMN_NAME)
  public String getOption() {
    return option;
  }

  @OrderBy(POSITION_ASC)
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @JoinTable(name = GNUOB_OFFER_RECORDS_GNUOB_OPTIONS_TABLE_NAME,
      joinColumns = {@JoinColumn(name = GNUOB_OFFER_RECORDS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)},
      inverseJoinColumns = {@JoinColumn(name = OPTIONS_ID_COLUMN_NAME, referencedColumnName = ID_COLUMN_NAME)})
  public Set<Option> getOptions() {
    return options;
  }

  @XmlTransient
  @Column(name = POSITION_COLUMN_NAME)
  public Integer getPosition() {
    return position;
  }

  @Transient
  public Product getProduct() {
    return product;
  }

  @XmlElement
  @Column(name = PRODUCT_NUMBER_COLUMN_NAME, nullable = false)
  public String getProductNumber() {
    if (product != null && StringUtils.isBlank(productNumber)) {
      productNumber = product.getNumber();
    }
    return productNumber;
  }

  @XmlElement(required = true)
  @Column(name = QUANTITY_COLUMN_NAME, nullable = false)
  public BigInteger getQuantity() {
    return quantity;
  }

  @XmlElement
  @Column(name = SHIPPING_COST_COLUMN_NAME)
  public BigDecimal getShippingCost() {
    if (product != null && shippingCost == null) {
      shippingCost = product.getShippingCost();
    }
    return shippingCost;
  }

  @XmlTransient
  @Transient
  public BigDecimal getShippingTotal() {
    if (getShippingCost() != null && getQuantity() != null) {
      return getShippingCost().multiply(new BigDecimal(getQuantity()));
    }
    return BigDecimal.ZERO;
  }

  @XmlElement
  public BigDecimal getTax() {
    if (product != null && tax == null) {
      tax = product.getTax();
    }
    return tax;
  }

  @XmlTransient
  @Transient
  @Column(name = TAX_COLUMN_NAME)
  public BigDecimal getTaxTotal() {
    if (getTax() != null && getQuantity() != null) {
      return getTax().multiply(new BigDecimal(getQuantity()));
    }
    return BigDecimal.ZERO;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public void setDiscount(final BigDecimal discount) {
    this.discount = discount;
  }

  public void setItemHeight(final BigDecimal itemHeight) {
    this.itemHeight = itemHeight;
  }

  public void setItemHeightUnit(final String itemHeightUnit) {
    this.itemHeightUnit = itemHeightUnit;
  }

  public void setItemLength(final BigDecimal itemLength) {
    this.itemLength = itemLength;
  }

  public void setItemLengthUnit(final String itemLengthUnit) {
    this.itemLengthUnit = itemLengthUnit;
  }

  public void setItemUrl(final String itemUrl) {
    this.itemUrl = itemUrl;
  }

  public void setItemWeight(final BigDecimal itemWeight) {
    this.itemWeight = itemWeight;
  }

  public void setItemWeightUnit(final String itemWeightUnit) {
    this.itemWeightUnit = itemWeightUnit;
  }

  public void setItemWidth(final BigDecimal itemWidth) {
    this.itemWidth = itemWidth;
  }

  public void setItemWidthUnit(final String itemWidthUnit) {
    this.itemWidthUnit = itemWidthUnit;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setOfferRecordId(final String offerRecordId) {
    this.offerRecordId = offerRecordId;
  }

  public void setOption(final String option) {
    this.option = option;
  }

  public void setOptions(final Set<Option> options) {
    this.options = options;
  }

  public void setPosition(final Integer position) {
    this.position = position;
  }

  public void setProduct(final Product product) {
    this.product = product;
  }

  public void setProductNumber(final String productNumber) {
    this.productNumber = productNumber;
  }

  public void setQuantity(final BigInteger quantity) {
    this.quantity = quantity;
  }

  public void setShippingCost(final BigDecimal shippingCost) {
    this.shippingCost = shippingCost;
  }

  public void setTax(final BigDecimal tax) {
    this.tax = tax;
  }

  @Override
  public String toString() {
    return new ReflectionToStringBuilder(this, SHORT_PREFIX_STYLE).toString();
  }
}
