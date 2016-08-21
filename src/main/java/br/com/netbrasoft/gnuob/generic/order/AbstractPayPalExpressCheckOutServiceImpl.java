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

package br.com.netbrasoft.gnuob.generic.order;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CUSTOMER_SERVICE_NUMBER_PROPERTY;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_SITE_CANCEL_PROPERTY_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_SITE_PAYPAL_NOTIFICATION_PROPERTY_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_SITE_REDIRECT_PROPERTY_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PASSWORD_PROPERTY;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SIGNATURE_PROPERTY;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE_PROPERTY;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USERNAME_PROPERTY;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.VERSION_PROPERTY;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.headers.Header;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.customer.Address;
import br.com.netbrasoft.gnuob.generic.security.OperationAccess;
import br.com.netbrasoft.gnuob.generic.security.Rule.Operation;
import ebay.api.paypalapi.DoExpressCheckoutPaymentReq;
import ebay.api.paypalapi.DoExpressCheckoutPaymentRequestType;
import ebay.api.paypalapi.DoExpressCheckoutPaymentResponseType;
import ebay.api.paypalapi.GetExpressCheckoutDetailsReq;
import ebay.api.paypalapi.GetExpressCheckoutDetailsRequestType;
import ebay.api.paypalapi.GetExpressCheckoutDetailsResponseType;
import ebay.api.paypalapi.GetTransactionDetailsReq;
import ebay.api.paypalapi.GetTransactionDetailsRequestType;
import ebay.api.paypalapi.GetTransactionDetailsResponseType;
import ebay.api.paypalapi.PayPalAPIAAInterface;
import ebay.api.paypalapi.PayPalAPIInterface;
import ebay.api.paypalapi.PayPalAPIInterfaceService;
import ebay.api.paypalapi.SetExpressCheckoutReq;
import ebay.api.paypalapi.SetExpressCheckoutRequestType;
import ebay.api.paypalapi.SetExpressCheckoutResponseType;
import ebay.apis.corecomponenttypes.BasicAmountType;
import ebay.apis.corecomponenttypes.MeasureType;
import ebay.apis.eblbasecomponents.AckCodeType;
import ebay.apis.eblbasecomponents.AddressNormalizationStatusCodeType;
import ebay.apis.eblbasecomponents.AddressOwnerCodeType;
import ebay.apis.eblbasecomponents.AddressStatusCodeType;
import ebay.apis.eblbasecomponents.AddressType;
import ebay.apis.eblbasecomponents.CountryCodeType;
import ebay.apis.eblbasecomponents.CurrencyCodeType;
import ebay.apis.eblbasecomponents.CustomSecurityHeaderType;
import ebay.apis.eblbasecomponents.DoExpressCheckoutPaymentRequestDetailsType;
import ebay.apis.eblbasecomponents.DoExpressCheckoutPaymentResponseDetailsType;
import ebay.apis.eblbasecomponents.ErrorType;
import ebay.apis.eblbasecomponents.GetExpressCheckoutDetailsResponseDetailsType;
import ebay.apis.eblbasecomponents.ItemCategoryType;
import ebay.apis.eblbasecomponents.PayerInfoType;
import ebay.apis.eblbasecomponents.PaymentDetailsItemType;
import ebay.apis.eblbasecomponents.PaymentDetailsType;
import ebay.apis.eblbasecomponents.PaymentInfoType;
import ebay.apis.eblbasecomponents.PaymentItemInfoType;
import ebay.apis.eblbasecomponents.PaymentItemType;
import ebay.apis.eblbasecomponents.PaymentTransactionType;
import ebay.apis.eblbasecomponents.SetExpressCheckoutRequestDetailsType;
import ebay.apis.eblbasecomponents.UserIdPasswordType;

public abstract class AbstractPayPalExpressCheckOutServiceImpl<O extends Order> implements ICheckOutService<O> {

  @WebServiceRef(wsdlLocation = "https://www.paypalobjects.com/wsdl/PayPalSvc.wsdl")
  private PayPalAPIInterfaceService payPalAPIInterfaceService;
  private PayPalAPIAAInterface palAPIAAInterface;
  private PayPalAPIInterface palAPIInterface;

  public AbstractPayPalExpressCheckOutServiceImpl() {
    // This constructor will be used by the EJB container.
  }

  protected AbstractPayPalExpressCheckOutServiceImpl(final PayPalAPIAAInterface palAPIAAInterface,
      final PayPalAPIInterface palAPIInterface) {
    this.palAPIAAInterface = palAPIAAInterface;
    this.palAPIInterface = palAPIInterface;
  }

  private AddressType doAddress(final Address address) {
    final AddressType addressType = new AddressType();
    addressType.setAddressID(String.valueOf(address.getId()));
    addressType.setAddressNormalizationStatus(AddressNormalizationStatusCodeType.NORMALIZED);
    addressType.setAddressOwner(AddressOwnerCodeType.PAY_PAL);
    addressType.setAddressStatus(AddressStatusCodeType.NONE);
    addressType.setCityName(address.getCityName());
    if (address.getCountry() != null) {
      addressType.setCountry(Enum.valueOf(CountryCodeType.class, address.getCountry()));
    }
    addressType.setCountryName(address.getCountryName());
    addressType.setExternalAddressID(String.valueOf(address.getId()));
    addressType.setInternationalName("International shipment address");
    addressType.setInternationalStateAndCity(address.getInternationalStateAndCity());
    addressType.setInternationalStreet(address.getInternationalStreet());
    addressType.setName("Shipment address");
    addressType.setPhone(address.getPhone());
    addressType.setPostalCode(address.getPostalCode());
    addressType.setStateOrProvince(address.getStateOrProvince());
    addressType.setStreet1(address.getStreet1());
    addressType.setStreet2(address.getStreet2());
    return addressType;
  }

  private void doAddressType(final Address address, final AddressType addressType) {
    address.setCityName(addressType.getCityName());
    address.setCountry(addressType.getCountry().value());
    address.setCountryName(addressType.getCountryName());
    address.setInternationalStateAndCity(addressType.getInternationalStateAndCity());
    address.setInternationalStreet(addressType.getInternationalStreet());
    address.setPhone(addressType.getPhone());
    address.setPostalCode(addressType.getPostalCode());
    address.setStateOrProvince(addressType.getStateOrProvince());
    address.setStreet1(addressType.getStreet1());
    address.setStreet2(addressType.getStreet2());
  }

  private BigDecimal doBasicAmountType(final BasicAmountType basicAmountType) {
    return new BigDecimal(basicAmountType.getValue());
  }

  private BasicAmountType doBasicAmountType(final BigDecimal value) {
    try {
      if (value != null) {
        final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        final BasicAmountType basicAmountType = new BasicAmountType();
        basicAmountType.setCurrencyID(CurrencyCodeType
            .valueOf(NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getCurrencyCode()));
        basicAmountType.setValue(numberFormat.parse(numberFormat.format(value)).toString());
        return basicAmountType;
      }
      return null;
    } catch (final ParseException e) {
      return null;
    }
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doCheckout(final O order) {

    // SetExpressCheckout fields.
    final SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
    final SetExpressCheckoutRequestType setExpressCheckoutRequestType = new SetExpressCheckoutRequestType();
    setExpressCheckoutRequestType.setVersion(VERSION_PROPERTY);

    // SetExpressCheckout request fields.
    setExpressCheckoutRequestType.setSetExpressCheckoutRequestDetails(doSetExpressCheckoutRequestsDetailsType(order));

    // SetExpressCheckout fields.
    setExpressCheckoutReq.setSetExpressCheckoutRequest(setExpressCheckoutRequestType);

    // Do SetExpressCheckout call.
    final SetExpressCheckoutResponseType setExpressCheckoutResponseType =
        getPayPalAPIAAInterface().setExpressCheckout(setExpressCheckoutReq);

    if (setExpressCheckoutResponseType.getAck() == AckCodeType.SUCCESS) {
      order.setToken(setExpressCheckoutResponseType.getToken());
    } else {
      throw new GNUOpenBusinessServiceException("Exception from Paypal Express Checkout [errors="
          + getParameterErrors(setExpressCheckoutResponseType.getErrors()) + "], please try again.");
    }
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doCheckoutDetails(final O order) {

    // GetExpressCheckoutDetails Resquest Fields
    final GetExpressCheckoutDetailsReq getExpressCheckoutDetailsReq = new GetExpressCheckoutDetailsReq();
    final GetExpressCheckoutDetailsRequestType getExpressCheckoutDetailsRequestType =
        new GetExpressCheckoutDetailsRequestType();
    getExpressCheckoutDetailsRequestType.setVersion(VERSION_PROPERTY);

    // The timestamped token value that was returned by SetExpressCheckout
    // response and passed on GetExpressCheckoutDetails request.
    getExpressCheckoutDetailsRequestType.setToken(order.getToken());

    // GetExpressCheckoutDetails Response fields.
    getExpressCheckoutDetailsReq.setGetExpressCheckoutDetailsRequest(getExpressCheckoutDetailsRequestType);

    // Do GetExpressCheckoutDetails call.
    final GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponseType =
        getPayPalAPIAAInterface().getExpressCheckoutDetails(getExpressCheckoutDetailsReq);

    // TODO make better error handling for this test.
    if (getExpressCheckoutDetailsResponseType.getAck() == AckCodeType.SUCCESS) {

      final GetExpressCheckoutDetailsResponseDetailsType getExpressCheckoutDetailsResponseDetailsType =
          getExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails();

      // The timestamped token value that was returned by
      // SetExpressCheckout
      // response and passed on GetExpressCheckoutDetails request.
      order.setToken(getExpressCheckoutDetailsResponseDetailsType.getToken());

      // Information about the payer.
      doPayerInfoType(order, getExpressCheckoutDetailsResponseDetailsType.getPayerInfo());

      // A free-form field for your own use, as set by you in the Custom
      // element of the SetExpressCheckout request.
      order.setCustom(getExpressCheckoutDetailsResponseDetailsType.getCustom());

      // Your own invoice or tracking number, as set by you in the element
      // of
      // the same name in the SetExpressCheckout request.
      order.getInvoice().setInvoiceId(getExpressCheckoutDetailsResponseDetailsType.getInvoiceID());

      // Buyer's contact phone number.
      order.getContract().getCustomer().setContactPhone(getExpressCheckoutDetailsResponseDetailsType.getContactPhone());

      // When implementing parallel payments, you can create up to 10 sets of payment details type
      // parameter fields, each representing one payment you are hosting on your marketplace.
      doPaymentDetailsTypes(order, getExpressCheckoutDetailsResponseDetailsType.getPaymentDetails().iterator().next());

      // Text entered by the buyer on the PayPal website if you set the
      // AllowNote field to 1 in SetExpressCheckout.
      order.setNote(getExpressCheckoutDetailsResponseDetailsType.getNote());

      // Shipping options and insurance.

      // Status of the checkout session. If payment is completed, the
      // transaction identification number of the resulting transaction is
      // returned.
      order.setCheckoutStatus(getExpressCheckoutDetailsResponseDetailsType.getCheckoutStatus());

      // Gift message entered by the buyer on the PayPal checkout pages.
      order.setGiftMessage(getExpressCheckoutDetailsResponseDetailsType.getGiftMessage());

      // Whether the buyer requested a gift receipt.
      order.setGiftReceiptEnable(Boolean.valueOf(getExpressCheckoutDetailsResponseDetailsType.getGiftReceiptEnable()));

      // Returns the gift wrap amount only if the buyer selects the gift
      // option on the PayPal pages.
      order.setGiftWrapAmount(doBasicAmountType(getExpressCheckoutDetailsResponseDetailsType.getGiftWrapAmount()));

      // Buyer's email address if the buyer provided it on the PayPal
      // pages.
      order.getContract().getCustomer()
          .setBuyerMarketingEmail(getExpressCheckoutDetailsResponseDetailsType.getBuyerMarketingEmail());

      // Survey response the buyer selects on the PayPal pages.

      // Payment request information for each bucket in the cart.
    } else {
      throw new GNUOpenBusinessServiceException("Exception from Paypal Express Checkout Details [errors="
          + getParameterErrors(getExpressCheckoutDetailsResponseType.getErrors()) + "], please try again.");
    }
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doCheckoutPayment(final O order) {

    // Do Express Checkout Payment Request Fields
    final DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
    final DoExpressCheckoutPaymentRequestType doExpressCheckoutPaymentRequestType =
        new DoExpressCheckoutPaymentRequestType();
    doExpressCheckoutPaymentRequestType.setVersion(VERSION_PROPERTY);

    // Do Express Checkout Payment Request Fields.
    doExpressCheckoutPaymentRequestType
        .setDoExpressCheckoutPaymentRequestDetails(doExpressCheckoutPaymentRequestDetailsType(order));

    // Do Express Checkout Payment Fields.
    doExpressCheckoutPaymentReq.setDoExpressCheckoutPaymentRequest(doExpressCheckoutPaymentRequestType);

    // Do Express Checkout Payment call.
    final DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponseType =
        getPayPalAPIAAInterface().doExpressCheckoutPayment(doExpressCheckoutPaymentReq);

    if (doExpressCheckoutPaymentResponseType.getAck() == AckCodeType.SUCCESS) {

      final DoExpressCheckoutPaymentResponseDetailsType doExpressCheckoutPaymentResponseDetailsType =
          doExpressCheckoutPaymentResponseType.getDoExpressCheckoutPaymentResponseDetails();

      // The timestamped token value that was returned by
      // SetExpressCheckout
      // response and passed on GetExpressCheckoutDetails request.
      order.setToken(doExpressCheckoutPaymentResponseDetailsType.getToken());

      // Information about the payment.
      for (final PaymentInfoType paymentInfoType : doExpressCheckoutPaymentResponseDetailsType.getPaymentInfo()) {
        order.getInvoice().getPayments().add(doPaymentInfoType(paymentInfoType));
      }

      // The text entered by the buyer on the PayPal website if you set
      // the
      // AllowNote field to 1 in SetExpressCheckout.
      order.setNote(doExpressCheckoutPaymentResponseDetailsType.getNote());

      // The ID of the billing agreement associated with the Express
      // Checkout
      // transaction.
      order.setBillingAgreementId(doExpressCheckoutPaymentResponseDetailsType.getBillingAgreementID());
    } else {
      throw new GNUOpenBusinessServiceException("Exception from Paypal Express Checkout [errors="
          + getParameterErrors(doExpressCheckoutPaymentResponseType.getErrors()) + "], please try again.");
    }
  }

  private DoExpressCheckoutPaymentRequestDetailsType doExpressCheckoutPaymentRequestDetailsType(final O order) {
    final DoExpressCheckoutPaymentRequestDetailsType doExpressCheckoutPaymentRequestDetailsType =
        new DoExpressCheckoutPaymentRequestDetailsType();

    // The timestamped token value that was returned in the
    // SetExpressCheckout
    // response and passed in the GetExpressCheckoutDetails request.
    doExpressCheckoutPaymentRequestDetailsType.setToken(order.getToken());

    // Unique PayPal buyer account identification number as returned in the
    // GetExpressCheckoutDetails response.
    doExpressCheckoutPaymentRequestDetailsType.setPayerID(order.getContract().getCustomer().getPayerId());

    // Information about the payment.
    doExpressCheckoutPaymentRequestDetailsType.getPaymentDetails().add(doPaymentDetailsType(order));

    // Shipping options and insurance.

    // The gift message the buyer entered on the PayPal pages.
    doExpressCheckoutPaymentRequestDetailsType.setGiftMessage(order.getGiftMessage());

    // Whether the buyer selected a gift receipt on the PayPal pages.
    if (order.getGiftMessageEnable() != null) {
      doExpressCheckoutPaymentRequestDetailsType.setGiftReceiptEnable("true");
    }

    // Return the gift wrap name only if the buyer selected the gift option
    // on
    // the PayPal pages.
    if (order.getGiftWrapName() != null) {
      doExpressCheckoutPaymentRequestDetailsType.setGiftWrapName(order.getGiftWrapName());
    }

    // Amount only if the buyer selected the gift option on the PayPal
    // pages.
    if (order.getGiftWrapAmount() != null) {
      doExpressCheckoutPaymentRequestDetailsType.setGiftWrapAmount(doBasicAmountType(order.getGiftWrapAmount()));
    }

    // The buyer email address opted in by the buyer on the PayPal pages.
    doExpressCheckoutPaymentRequestDetailsType
        .setBuyerMarketingEmail(order.getContract().getCustomer().getBuyerMarketingEmail());
    return doExpressCheckoutPaymentRequestDetailsType;
  }

  private BigDecimal doMesureType(final MeasureType measureType) {
    return measureType == null ? null : BigDecimal.valueOf(measureType.getValue());
  }

  private MeasureType doMesureType(final String unit, final BigDecimal value) {

    if (unit != null && !unit.trim().isEmpty() && value != null) {
      final MeasureType measureType = new MeasureType();
      measureType.setUnit(unit);
      measureType.setValue(value.doubleValue());
    }

    return null;
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public O doNotification(final O order) {

    // GetTransactionDetails Request Fields
    final GetTransactionDetailsReq getTransactionDetailsReq = new GetTransactionDetailsReq();
    final GetTransactionDetailsRequestType getTransactionDetailsRequestType = new GetTransactionDetailsRequestType();
    getTransactionDetailsRequestType.setVersion(VERSION_PROPERTY);

    // TransactionDetails request fields.
    getTransactionDetailsRequestType.setTransactionID(order.getNotificationId());

    getTransactionDetailsReq.setGetTransactionDetailsRequest(getTransactionDetailsRequestType);

    // Do TransactionDetails call.
    final GetTransactionDetailsResponseType getTransactionDetailsResponseType =
        getPayPalAPIInterface().getTransactionDetails(getTransactionDetailsReq);

    if (getTransactionDetailsResponseType.getAck() == AckCodeType.SUCCESS) {
      order.setTransactionId(
          getTransactionDetailsResponseType.getPaymentTransactionDetails().getPaymentInfo().getTransactionID());
      order
          .setCustom(getTransactionDetailsResponseType.getPaymentTransactionDetails().getPaymentItemInfo().getCustom());
    } else {
      throw new GNUOpenBusinessServiceException("Exception from Paypal Express Checkout [errors="
          + getParameterErrors(getTransactionDetailsResponseType.getErrors()) + "], please try again.");
    }

    order.setNotificationId(null);

    return order;
  }

  private void doPayerInfoType(final O order, final PayerInfoType payerInfo) {
    order.getContract().getCustomer().setPayer(payerInfo.getPayer());
    order.getContract().getCustomer().setPayerId(payerInfo.getPayerID());
    order.getContract().getCustomer().setPayerStatus(payerInfo.getPayerStatus().value());
    order.getContract().getCustomer().setFirstName(payerInfo.getPayerName().getFirstName());
    order.getContract().getCustomer().setSuffix(payerInfo.getPayerName().getSuffix());
    order.getContract().getCustomer().setMiddleName(payerInfo.getPayerName().getMiddleName());
    order.getContract().getCustomer().setLastName(payerInfo.getPayerName().getLastName());
    order.getContract().getCustomer().setSalutation(payerInfo.getPayerName().getSalutation());
    /*
     * if (payerInfo.getPayerCountry() != null) {
     * order.getContract().getCustomer().getAddress().setCountry(payerInfo.getPayerCountry().value()
     * ); }
     */
    order.getContract().getCustomer().setPayerBusiness(payerInfo.getPayerBusiness());
    order.getContract().getCustomer().setTaxIdType(payerInfo.getTaxIdDetails().getTaxIdType());
    order.getContract().getCustomer().setTaxId(payerInfo.getTaxIdDetails().getTaxId());
    order.getContract().getCustomer().setContactPhone(payerInfo.getContactPhone());

    doAddressType(order.getContract().getCustomer().getAddress(), payerInfo.getAddress());
  }

  private PaymentDetailsItemType doPaymentDetailsItemType(final OrderRecord orderRecord) {
    final PaymentDetailsItemType paymentDetailsItemType = new PaymentDetailsItemType();

    paymentDetailsItemType.setName(orderRecord.getName());
    paymentDetailsItemType.setDescription(orderRecord.getDescription());
    paymentDetailsItemType.setAmount(doBasicAmountType(orderRecord.getAmount()));
    paymentDetailsItemType.setNumber(orderRecord.getOrderRecordId());
    paymentDetailsItemType.setQuantity(orderRecord.getQuantity());
    paymentDetailsItemType.setTax(doBasicAmountType(orderRecord.getTax()));
    paymentDetailsItemType.setItemWeight(doMesureType(orderRecord.getItemWeightUnit(), orderRecord.getItemWeight()));
    paymentDetailsItemType.setItemLength(doMesureType(orderRecord.getItemLengthUnit(), orderRecord.getItemLength()));
    paymentDetailsItemType.setItemWidth(doMesureType(orderRecord.getItemWidthUnit(), orderRecord.getItemWidth()));
    paymentDetailsItemType.setItemHeight(doMesureType(orderRecord.getItemHeightUnit(), orderRecord.getItemHeight()));
    paymentDetailsItemType.setItemURL(orderRecord.getItemUrl());
    paymentDetailsItemType.setItemCategory(ItemCategoryType.PHYSICAL);
    return paymentDetailsItemType;
  }

  private void doPaymentDetailsItemType(final OrderRecord orderRecord,
      final PaymentDetailsItemType paymentDetailsItemType) {
    orderRecord.setName(paymentDetailsItemType.getName());
    orderRecord.setDescription(paymentDetailsItemType.getDescription());
    orderRecord.setAmount(doBasicAmountType(paymentDetailsItemType.getAmount()));
    orderRecord.setOrderRecordId(paymentDetailsItemType.getNumber());
    orderRecord.setQuantity(paymentDetailsItemType.getQuantity());
    orderRecord.setTax(doBasicAmountType(paymentDetailsItemType.getTax()));
    orderRecord.setItemWeight(doMesureType(paymentDetailsItemType.getItemWeight()));
    orderRecord.setItemLength(doMesureType(paymentDetailsItemType.getItemLength()));
    orderRecord.setItemWidth(doMesureType(paymentDetailsItemType.getItemWidth()));
    orderRecord.setItemHeight(doMesureType(paymentDetailsItemType.getItemHeight()));
    orderRecord.setItemUrl(paymentDetailsItemType.getItemURL());
  }

  private PaymentDetailsType doPaymentDetailsType(final O order) {

    // Information about the payment.
    final PaymentDetailsType paymentDetailsType = new PaymentDetailsType();

    // Total cost of the transaction to the buyer.
    paymentDetailsType.setOrderTotal(doBasicAmountType(order.getOrderTotal()));

    // Sum of cost of all items in this order.
    paymentDetailsType.setItemTotal(doBasicAmountType(order.getItemTotal()));

    // Total shipping costs for this order.
    paymentDetailsType.setShippingTotal(doBasicAmountType(order.getShippingTotal()));

    // Shipping discount for this order
    paymentDetailsType.setShippingDiscount(doBasicAmountType(order.getShippingDiscount().negate()));

    // Indicates whether insurance is available as an option the buyer can
    // choose on the PayPal Review page
    if (order.getInsuranceTotal() != null && order.getInsuranceTotal().compareTo(BigDecimal.ZERO) == 1) {
      paymentDetailsType.setInsuranceOptionOffered("true");

      // Total shipping insurance costs for this order.
      paymentDetailsType.setInsuranceTotal(doBasicAmountType(order.getInsuranceTotal()));
    }

    // Total handling costs for this order.
    paymentDetailsType.setHandlingTotal(doBasicAmountType(order.getHandlingTotal()));

    // Sum of tax for all items in this order.
    paymentDetailsType.setTaxTotal(doBasicAmountType(order.getTaxTotal()));

    // Description of items the buyer is purchasing.
    paymentDetailsType.setOrderDescription(order.getOrderDescription());

    // Your URL for receiving Instant Payment Notification (IPN) about this
    // transaction.
    paymentDetailsType.setNotifyURL(System.getProperty("gnuob." + order.getSite().getName() + ".paypal.notification",
        GNUOB_SITE_PAYPAL_NOTIFICATION_PROPERTY_VALUE));

    // Address to which the order is shipped.
    if (order.getShipment() != null) {
      paymentDetailsType.setShipToAddress(doAddress(order.getShipment().getAddress()));
    }

    // Details about each individual item included in the order.
    for (final OrderRecord orderRecord : order.getRecords()) {
      paymentDetailsType.getPaymentDetailsItem().add(doPaymentDetailsItemType(orderRecord));
    }

    // Note to the merchant.
    paymentDetailsType.setNoteText(order.getNoteText());

    // A free-form field for your own use.
    paymentDetailsType.setCustom(order.getCustom());

    // Transaction identification number of the transaction that was
    // created.
    // Note: This field is only returned after a successful transaction for
    // DoExpressCheckout has occurred.
    if (order.getTransactionId() != null) {
      paymentDetailsType.setTransactionId(order.getTransactionId());
    } else {
      order.setTransactionId(order.getToken());
    }

    return paymentDetailsType;
  }

  private void doPaymentDetailsTypes(final O order, final PaymentDetailsType paymentDetailsType) {
    order.setOrderTotal(doBasicAmountType(paymentDetailsType.getOrderTotal()));
    order.setItemTotal(doBasicAmountType(paymentDetailsType.getItemTotal()));
    order.setShippingTotal(doBasicAmountType(paymentDetailsType.getShippingTotal()));
    order.setInsuranceTotal(doBasicAmountType(paymentDetailsType.getInsuranceTotal()));
    order.setShippingDiscount(doBasicAmountType(paymentDetailsType.getShippingDiscount()).negate());
    order.setInsuranceOptionOffered(new Boolean(paymentDetailsType.getInsuranceOptionOffered()));
    order.setHandlingTotal(doBasicAmountType(paymentDetailsType.getHandlingTotal()));
    order.setTaxTotal(doBasicAmountType(paymentDetailsType.getTaxTotal()));
    order.setOrderDescription(paymentDetailsType.getOrderDescription());
    order.setCustom(paymentDetailsType.getCustom());
    order.getInvoice().setInvoiceId(paymentDetailsType.getInvoiceID());
    doAddressType(order.getShipment().getAddress(), paymentDetailsType.getShipToAddress());

    // Details about each individual item included in the order.
    for (final PaymentDetailsItemType paymentDetailsItemType : paymentDetailsType.getPaymentDetailsItem()) {
      for (final OrderRecord orderRecord : order.getRecords()) {
        if (paymentDetailsItemType.getNumber() != null
            && paymentDetailsItemType.getNumber().equalsIgnoreCase(orderRecord.getOrderRecordId())) {
          doPaymentDetailsItemType(orderRecord, paymentDetailsItemType);
          break;
        }
      }
    }

    order.setNoteText(paymentDetailsType.getNoteText());
  }

  private Payment doPaymentInfoType(final PaymentInfoType paymentInfoType) {
    final Payment payment = new Payment();

    // Unique transaction ID of the payment.
    payment.setTransactionId(paymentInfoType.getTransactionID());

    // Type of transaction.
    if (paymentInfoType.getTransactionType() != null) {
      payment.setTransactionType(paymentInfoType.getTransactionType().value());
    } else {
      payment.setTransactionType("");
    }

    // Indicates whether the payment is instant or delayed.
    payment.setPaymentType(paymentInfoType.getPaymentType().value());

    // Time/date stamp of payment.
    payment.setPaymentDate(new Date(paymentInfoType.getPaymentDate().toGregorianCalendar().getTimeInMillis()));

    // The final amount charged, including any shipping and taxes from your
    // Merchant Profile.
    payment.setGrossAmount(doBasicAmountType(paymentInfoType.getGrossAmount()));

    // PayPal fee amount charged for the transaction.
    payment.setFeeAmount(doBasicAmountType(paymentInfoType.getFeeAmount()));

    // Amount deposited in your PayPal account after a currency conversion.
    payment.setSettleAmount(doBasicAmountType(paymentInfoType.getSettleAmount()));

    // Tax charged on the transaction.
    payment.setTaxAmount(doBasicAmountType(paymentInfoType.getTaxAmount()));

    // Exchange rate if a currency conversion occurred. Relevant only if
    // your
    // are billing in their non-primary currency. If the buyer chooses to
    // pay
    // with a currency other than the non-primary currency, the conversion
    // occurs in the buyer's account.
    payment.setExchangeRate(paymentInfoType.getExchangeRate());

    // The status of the payment.
    payment.setPaymentStatus(paymentInfoType.getPaymentStatus().value());

    // Reason the payment is pending.
    payment.setPendingReason(paymentInfoType.getPendingReason().value());

    // Reason for a reversal if TransactionType is reversal.
    payment.setReasonCode(paymentInfoType.getReasonCode().value());

    // Reason that this payment is being held.
    payment.setHoldDecision(paymentInfoType.getHoldDecision());

    // The kind of seller protection in force for the transaction.
    payment.setProtectionEligibilityType(paymentInfoType.getProtectionEligibilityType());

    // StoreId as entered in the transaction.
    payment.setStoreId(paymentInfoType.getStoreID());

    // TerminalId as entered in the transaction.
    payment.setTerminalId(paymentInfoType.getTerminalID());

    // Unique identifier of the specific payment request. The value should
    // match the one you passed in the DoExpressCheckout request.
    payment.setPaymentRequestId(paymentInfoType.getPaymentRequestID());

    return payment;
  }

  private void doPaymentItemInfo(final O order, final PaymentItemInfoType paymentItemInfo) {

    order.getInvoice().setInvoiceId(paymentItemInfo.getInvoiceID());
    order.setCustom(paymentItemInfo.getCustom());

    // Details about each individual item included in the order.
    for (final PaymentItemType paymentItemType : paymentItemInfo.getPaymentItem()) {
      for (final OrderRecord orderRecord : order.getRecords()) {
        if (paymentItemType.getNumber() != null
            && paymentItemType.getNumber().equalsIgnoreCase(orderRecord.getOrderRecordId())) {
          doPaymentItemType(orderRecord, paymentItemType);
          break;
        }
      }
    }
  }

  private void doPaymentItemType(final OrderRecord orderRecord, final PaymentItemType paymentItemType) {
    orderRecord.setName(paymentItemType.getName());
    orderRecord.setAmount(doBasicAmountType(paymentItemType.getAmount()));
    orderRecord.setOrderRecordId(paymentItemType.getNumber());
    orderRecord.setQuantity(BigInteger.valueOf(Integer.valueOf(paymentItemType.getQuantity())));
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doRefundTransaction(final O order) {
    // TODO Auto-generated method stub
  }

  private SetExpressCheckoutRequestDetailsType doSetExpressCheckoutRequestsDetailsType(final O order) {
    // SetExpressCheckout request fields.
    final SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetailsType =
        new SetExpressCheckoutRequestDetailsType();

    // The expected maximum total amount of the complete order, including
    // shipping cost and tax charges.
    setExpressCheckoutRequestDetailsType.setMaxAmount(doBasicAmountType(order.getMaxTotal()));

    // URL to which the buyer's browser is returned after choosing to pay
    // with
    // PayPal. For digital goods.
    setExpressCheckoutRequestDetailsType.setReturnURL(System
        .getProperty("gnuob." + order.getSite().getName() + ".paypal.redirect", GNUOB_SITE_REDIRECT_PROPERTY_VALUE));

    // URL to which the buyer is returned if the buyer does not approve the
    // use of PayPal to pay you. For digital goods.
    setExpressCheckoutRequestDetailsType.setCancelURL(
        System.getProperty("gnuob." + order.getSite().getName() + ".paypal.cancel", GNUOB_SITE_CANCEL_PROPERTY_VALUE));

    // Indicates whether or not you require the buyer's shipping address on
    // file with PayPal be a confirmed address.
    setExpressCheckoutRequestDetailsType.setReqConfirmShipping("1");

    // Determines where or not PayPal displays shipping address fields on
    // the
    // PayPal pages.
    setExpressCheckoutRequestDetailsType.setNoShipping("0");

    // Enables the buyer to enter a note to the merchant on the PayPal page
    // during checkout.
    setExpressCheckoutRequestDetailsType.setAllowNote("1");

    // SetExpressCheckout request details fields.
    setExpressCheckoutRequestDetailsType.getPaymentDetails().add(doPaymentDetailsType(order));

    // Determines whether or not the PayPal pages should display the
    // shipping
    // address set by you in this SetExpressCheckout request.
    setExpressCheckoutRequestDetailsType.setAddressOverride("1");

    // Merchant Customer Service number displayed on the PayPal pages.
    setExpressCheckoutRequestDetailsType.setCustomerServiceNumber(CUSTOMER_SERVICE_NUMBER_PROPERTY);

    // Enables the gift message widget on the PayPal pages.
    if (order.getGiftMessageEnable() != null && order.getGiftMessageEnable()) {
      setExpressCheckoutRequestDetailsType.setGiftMessageEnable("1");
    }

    // Enable gift receipt widget on the PayPal pages.
    if (order.getGiftReceiptEnable() != null && order.getGiftReceiptEnable()) {
      setExpressCheckoutRequestDetailsType.setGiftReceiptEnable("1");
    }

    // Enable gift wrap widget on the PayPal pages.
    if (order.getGiftWrapEnable() != null && order.getGiftWrapEnable()) {
      setExpressCheckoutRequestDetailsType.setGiftWrapEnable("1");
      setExpressCheckoutRequestDetailsType.setGiftWrapName(order.getGiftWrapName());
      setExpressCheckoutRequestDetailsType.setGiftWrapAmount(doBasicAmountType(order.getGiftWrapAmount()));
    }

    // PageStyle, logos etc. on PayPal site.

    // Email address of the buyer as entered during checkout.
    setExpressCheckoutRequestDetailsType.setBuyerEmail(order.getContract().getCustomer().getBuyerEmail());

    // Set brand name.
    setExpressCheckoutRequestDetailsType.setBrandName("-");

    // Your own invoice or tracking number.
    setExpressCheckoutRequestDetailsType.setInvoiceID(order.getInvoice().getInvoiceId());
    return setExpressCheckoutRequestDetailsType;
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doTransactionDetails(final O order) {
    // GetTransactionDetails Request Fields
    final GetTransactionDetailsReq getTransactionDetailsReq = new GetTransactionDetailsReq();
    final GetTransactionDetailsRequestType getTransactionDetailsRequestType = new GetTransactionDetailsRequestType();
    getTransactionDetailsRequestType.setVersion(VERSION_PROPERTY);

    // TransactionDetails request fields.F
    getTransactionDetailsRequestType.setTransactionID(order.getTransactionId());

    getTransactionDetailsReq.setGetTransactionDetailsRequest(getTransactionDetailsRequestType);

    // Do TransactionDetails call.
    final GetTransactionDetailsResponseType getTransactionDetailsResponseType =
        getPayPalAPIInterface().getTransactionDetails(getTransactionDetailsReq);

    if (getTransactionDetailsResponseType.getAck() == AckCodeType.SUCCESS) {

      final PaymentTransactionType paymentTransactionType =
          getTransactionDetailsResponseType.getPaymentTransactionDetails();

      // Information about the payer.
      doPayerInfoType(order, paymentTransactionType.getPayerInfo());

      // Information about the payment.
      order.getInvoice().getPayments().add(doPaymentInfoType(paymentTransactionType.getPaymentInfo()));

      // Payment Item Info Type Fields
      doPaymentItemInfo(order, paymentTransactionType.getPaymentItemInfo());
    } else {
      throw new GNUOpenBusinessServiceException("Exception from Paypal Express Checkout [errors="
          + getParameterErrors(getTransactionDetailsResponseType.getErrors()) + "], please try again.");
    }
  }

  private String getParameterErrors(final List<ErrorType> errors) {

    final class PayPalError extends ErrorType {

      public PayPalError(final ErrorType errorType) {
        setErrorCode(errorType.getErrorCode());
        setLongMessage(errorType.getLongMessage());
        setSeverityCode(errorType.getSeverityCode());
        setShortMessage(errorType.getShortMessage());
      }

      @Override
      public String toString() {
        return "{errorCode=" + getErrorCode() + " longMessage=" + getLongMessage() + " severityCode="
            + getSeverityCode().value() + " shortMessage=" + getShortMessage() + "}";
      }
    }

    final StringBuilder stringBuilder = new StringBuilder();

    for (final ErrorType errorType : errors) {
      final PayPalError payPalError = new PayPalError(errorType);
      stringBuilder.append(payPalError.toString());
    }

    return stringBuilder.toString();
  }

  private PayPalAPIAAInterface getPayPalAPIAAInterface() {
    if (palAPIAAInterface == null) {
      palAPIAAInterface = payPalAPIInterfaceService.getPayPalAPIAA();
      final HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
      final UserIdPasswordType userIdPasswordType = new UserIdPasswordType();

      userIdPasswordType.setUsername(USERNAME_PROPERTY);
      userIdPasswordType.setPassword(PASSWORD_PROPERTY);
      userIdPasswordType.setSignature(SIGNATURE_PROPERTY);

      httpClientPolicy.setConnectionTimeout(36000);
      httpClientPolicy.setAllowChunking(false);
      httpClientPolicy.setReceiveTimeout(32000);

      ((HTTPConduit) ClientProxy.getClient(palAPIAAInterface).getConduit()).setClient(httpClientPolicy);

      final CustomSecurityHeaderType customSecurityHeaderType = new CustomSecurityHeaderType();
      customSecurityHeaderType.setCredentials(userIdPasswordType);

      final List<Header> headers = new ArrayList<>();
      try {
        final Header header = new Header(new QName("urn:ebay:api:PayPalAPI", "RequesterCredentials"),
            customSecurityHeaderType, new JAXBDataBinding(CustomSecurityHeaderType.class));
        headers.add(header);
      } catch (final JAXBException e) {
        throw new GNUOpenBusinessServiceException(
            "Exception from Paypal Express Requester Credentials, please try again.", e);
      }

      ((BindingProvider) palAPIAAInterface).getRequestContext().put(Header.HEADER_LIST, headers);
      ((BindingProvider) palAPIAAInterface).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
          SITE_PROPERTY);
    }
    return palAPIAAInterface;
  }

  private PayPalAPIInterface getPayPalAPIInterface() {
    if (palAPIInterface == null) {
      palAPIInterface = payPalAPIInterfaceService.getPayPalAPI();
      final HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
      httpClientPolicy.setConnectionTimeout(36000);
      httpClientPolicy.setAllowChunking(false);
      httpClientPolicy.setReceiveTimeout(32000);

      ((HTTPConduit) ClientProxy.getClient(palAPIInterface).getConduit()).setClient(httpClientPolicy);

      final UserIdPasswordType userIdPasswordType = new UserIdPasswordType();
      userIdPasswordType.setUsername(USERNAME_PROPERTY);
      userIdPasswordType.setPassword(PASSWORD_PROPERTY);
      userIdPasswordType.setSignature(SIGNATURE_PROPERTY);

      final CustomSecurityHeaderType customSecurityHeaderType = new CustomSecurityHeaderType();
      customSecurityHeaderType.setCredentials(userIdPasswordType);

      final List<Header> headers = new ArrayList<>();
      try {
        headers.add(new Header(new QName("urn:ebay:api:PayPalAPI", "RequesterCredentials"), customSecurityHeaderType,
            new JAXBDataBinding(CustomSecurityHeaderType.class)));
      } catch (final JAXBException e) {
        throw new GNUOpenBusinessServiceException(
            "Exception from Paypal Express Requester Credentials, please try again.", e);
      }

      ((BindingProvider) palAPIInterface).getRequestContext().put(Header.HEADER_LIST, headers);
      ((BindingProvider) palAPIInterface).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
          SITE_PROPERTY);
    }
    return palAPIInterface;
  }
}
