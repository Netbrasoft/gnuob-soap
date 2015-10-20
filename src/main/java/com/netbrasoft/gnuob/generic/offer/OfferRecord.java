package com.netbrasoft.gnuob.generic.offer;

import java.math.BigDecimal;
import java.math.BigInteger;
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

import com.netbrasoft.gnuob.generic.Type;
import com.netbrasoft.gnuob.generic.product.Option;
import com.netbrasoft.gnuob.generic.product.Product;

@Cacheable(value = false)
@Entity(name = OfferRecord.ENTITY)
@Table(name = OfferRecord.TABLE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@XmlRootElement(name = OfferRecord.ENTITY)
public class OfferRecord extends Type {

  private static final long serialVersionUID = 710723043281502969L;
  protected static final String ENTITY = "OfferRecord";
  protected static final String TABLE = "GNUOB_OFFER_RECORDS";

  @Column(name = "NAME")
  private String name;

  @Column(name = "AMOUNT")
  private BigDecimal amount;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PRODUCT_NUMBER", nullable = false)
  private String productNumber;

  @Column(name = "TAX")
  private BigDecimal tax;

  @Column(name = "QUANTITY", nullable = false)
  private BigInteger quantity;

  @Column(name = "SHIPPING_COST")
  private BigDecimal shippingCost;

  @Column(name = "DISCOUNT")
  private BigDecimal discount;

  @Column(name = "ITEM_WEIGHT")
  private BigDecimal itemWeight;

  @Column(name = "ITEM_WEIGHT_UNIT")
  private String itemWeightUnit;

  @Column(name = "ITEM_LENGTH")
  private BigDecimal itemLength;

  @Column(name = "ITEM_LENGTH_UNIT")
  private String itemLengthUnit;

  @Column(name = "ITEM_WIDTH")
  private BigDecimal itemWidth;

  @Column(name = "ITEM_WIDTH_UNIT")
  private String itemWidthUnit;

  @Column(name = "ITEM_HEIGHT")
  private BigDecimal itemHeight;

  @Column(name = "ITEM_HEIGHT_UNIT")
  private String itemHeightUnit;

  @Column(name = "ITEM_URL")
  private String itemUrl;

  @Column(name = "`OPTION`")
  private String option;

  @Column(name = "POSITION")
  private Integer position;

  @Column(name = "OFFER_RECORD_ID", nullable = false)
  private String offerRecordId = UUID.randomUUID().toString();

  @OrderBy("position asc")
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  @JoinTable(name = "gnuob_offer_records_gnuob_options", joinColumns = {@JoinColumn(name = "GNUOB_OFFER_RECORDS_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "options_ID", referencedColumnName = "ID")})
  private Set<Option> options = new HashSet<Option>();

  @Transient
  private Product product;

  public OfferRecord() {}

  @XmlElement(name = "amount")
  public BigDecimal getAmount() {
    if (product != null && amount == null) {
      amount = product.getAmount().subtract(getDiscount());
    }
    return amount;
  }

  @XmlElement(name = "description")
  public String getDescription() {
    if (product != null && description == null) {
      description = product.getDescription();
    }
    return description;
  }

  @XmlElement(name = "discount")
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

  @XmlElement(name = "itemHeight")
  public BigDecimal getItemHeight() {
    if (product != null && itemHeight == null) {
      itemHeight = product.getItemHeight();
    }
    return itemHeight;
  }

  @XmlElement(name = "itemHeightUnit")
  public String getItemHeightUnit() {
    if (product != null && itemHeightUnit == null) {
      itemHeightUnit = product.getItemHeightUnit();
    }
    return itemHeightUnit;
  }

  @XmlElement(name = "itemLength")
  public BigDecimal getItemLength() {
    if (product != null && itemLength == null) {
      itemLength = product.getItemLength();
    }
    return itemLength;
  }

  @XmlElement(name = "itemLengthUnit")
  public String getItemLengthUnit() {
    if (product != null && itemLengthUnit == null) {
      itemHeightUnit = product.getItemHeightUnit();
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

  @XmlElement(name = "itemUrl")
  public String getItemUrl() {
    if (product != null && itemUrl == null) {
      itemUrl = product.getItemUrl();
    }
    return itemUrl;
  }

  @XmlElement(name = "itemWeight")
  public BigDecimal getItemWeight() {
    if (product != null && itemWeight == null) {
      itemWeight = product.getItemWeight();
    }
    return itemWeight;
  }

  @XmlElement(name = "itemWeightUnit")
  public String getItemWeightUnit() {
    if (product != null && itemWeightUnit == null) {
      itemWeightUnit = product.getItemWeightUnit();
    }
    return itemWeightUnit;
  }

  @XmlElement(name = "itemWidth")
  public BigDecimal getItemWidth() {
    if (product != null && itemWidth == null) {
      itemWidth = product.getItemWidth();
    }
    return itemWidth;
  }

  @XmlElement(name = "itemWidthUnit")
  public String getItemWidthUnit() {
    if (product != null && itemWidthUnit == null) {
      itemWidthUnit = product.getItemWidthUnit();
    }
    return itemWidthUnit;
  }

  @XmlElement(name = "name")
  public String getName() {
    if (product != null && name == null) {
      name = product.getName();
    }
    return name;
  }

  @XmlElement(name = "offerRecordId")
  public String getOfferRecordId() {
    return offerRecordId;
  }

  @XmlElement(name = "option")
  public String getOption() {
    return option;
  }

  public Set<Option> getOptions() {
    return options;
  }

  @XmlTransient
  public Integer getPosition() {
    return position;
  }

  public Product getProduct() {
    return product;
  }

  @XmlElement(name = "productNumber")
  public String getProductNumber() {
    if (product != null && productNumber == null) {
      productNumber = product.getNumber();
    }
    return productNumber;
  }

  @XmlElement(name = "quantity", required = true)
  public BigInteger getQuantity() {
    return quantity;
  }

  @XmlElement(name = "shippingCost")
  public BigDecimal getShippingCost() {
    if (product != null && shippingCost == null) {
      shippingCost = product.getShippingCost();
    }
    return shippingCost;
  }

  @Transient
  @XmlTransient
  public BigDecimal getShippingTotal() {
    if (getShippingCost() != null && getQuantity() != null) {
      return getShippingCost().multiply(new BigDecimal(getQuantity()));
    }
    return BigDecimal.ZERO;
  }

  @XmlElement(name = "tax")
  public BigDecimal getTax() {
    if (product != null && tax == null) {
      tax = product.getTax();
    }
    return tax;
  }

  @Transient
  @XmlTransient
  public BigDecimal getTaxTotal() {
    if (getTax() != null && getQuantity() != null) {
      return getTax().multiply(new BigDecimal(getQuantity()));
    }
    return BigDecimal.ZERO;
  }

  @Override
  public void prePersist() {
    if (offerRecordId == null || "".equals(offerRecordId.trim())) {
      offerRecordId = UUID.randomUUID().toString();
    }

    if (product != null) {
      name = name == null ? product.getName() : name;
      description = description == null ? product.getDescription() : description;
      amount = amount == null ? product.getAmount() : amount;
      productNumber = productNumber == null ? product.getNumber() : productNumber;
      tax = tax == null ? product.getTax() : tax;
      shippingCost = shippingCost == null ? product.getShippingCost() : shippingCost;
      itemWeight = itemWeight == null ? product.getItemWeight() : itemWeight;
      itemWeightUnit = itemWeightUnit == null ? product.getItemWeightUnit() : itemWeightUnit;
      itemLength = itemLength == null ? product.getItemLength() : itemLength;
      itemLengthUnit = itemLengthUnit == null ? product.getItemLengthUnit() : itemLengthUnit;
      itemWidth = itemWidth == null ? product.getItemWidth() : itemWidth;
      itemWidthUnit = itemWidthUnit == null ? product.getItemWidthUnit() : itemLengthUnit;
      itemHeight = itemHeight == null ? product.getItemHeight() : itemHeight;
      itemHeightUnit = itemHeightUnit == null ? product.getItemLengthUnit() : itemHeightUnit;
      itemUrl = itemUrl == null ? product.getItemUrl() : itemUrl;
    }
  }

  @Override
  public void preUpdate() {
    if (offerRecordId == null || "".equals(offerRecordId.trim())) {
      offerRecordId = UUID.randomUUID().toString();
    }
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDiscount(BigDecimal discount) {
    this.discount = discount;
  }

  public void setItemHeight(BigDecimal itemHeight) {
    this.itemHeight = itemHeight;
  }

  public void setItemHeightUnit(String itemHeightUnit) {
    this.itemHeightUnit = itemHeightUnit;
  }

  public void setItemLength(BigDecimal itemLength) {
    this.itemLength = itemLength;
  }

  public void setItemLengthUnit(String itemLengthUnit) {
    this.itemLengthUnit = itemLengthUnit;
  }

  public void setItemUrl(String itemUrl) {
    this.itemUrl = itemUrl;
  }

  public void setItemWeight(BigDecimal itemWeight) {
    this.itemWeight = itemWeight;
  }

  public void setItemWeightUnit(String itemWeightUnit) {
    this.itemWeightUnit = itemWeightUnit;
  }

  public void setItemWidth(BigDecimal itemWidth) {
    this.itemWidth = itemWidth;
  }

  public void setItemWidthUnit(String itemWidthUnit) {
    this.itemWidthUnit = itemWidthUnit;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOfferRecordId(String offerRecordId) {
    this.offerRecordId = offerRecordId;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public void setOptions(Set<Option> options) {
    this.options = options;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public void setProductNumber(String productNumber) {
    this.productNumber = productNumber;
  }

  public void setQuantity(BigInteger quantity) {
    this.quantity = quantity;
  }

  public void setShippingCost(BigDecimal shippingCost) {
    this.shippingCost = shippingCost;
  }

  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }
}
