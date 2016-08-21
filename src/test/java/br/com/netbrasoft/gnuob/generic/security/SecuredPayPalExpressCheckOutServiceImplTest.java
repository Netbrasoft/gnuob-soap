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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Address;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.order.Invoice;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.order.OrderRecord;
import br.com.netbrasoft.gnuob.generic.order.Payment;
import br.com.netbrasoft.gnuob.generic.order.Shipment;
import br.com.netbrasoft.gnuob.generic.security.ISecuredGenericTypeCheckOutService;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.generic.security.SecuredPayPalExpressCheckOutServiceImpl;
import br.com.netbrasoft.gnuob.generic.security.Site;
import ebay.api.paypalapi.DoExpressCheckoutPaymentResponseType;
import ebay.api.paypalapi.GetExpressCheckoutDetailsResponseType;
import ebay.api.paypalapi.GetTransactionDetailsResponseType;
import ebay.api.paypalapi.PayPalAPIAAInterface;
import ebay.api.paypalapi.PayPalAPIInterface;
import ebay.api.paypalapi.SetExpressCheckoutResponseType;
import ebay.apis.corecomponenttypes.BasicAmountType;
import ebay.apis.corecomponenttypes.MeasureType;
import ebay.apis.eblbasecomponents.AckCodeType;
import ebay.apis.eblbasecomponents.AddressType;
import ebay.apis.eblbasecomponents.CountryCodeType;
import ebay.apis.eblbasecomponents.CurrencyCodeType;
import ebay.apis.eblbasecomponents.DoExpressCheckoutPaymentResponseDetailsType;
import ebay.apis.eblbasecomponents.ErrorType;
import ebay.apis.eblbasecomponents.GetExpressCheckoutDetailsResponseDetailsType;
import ebay.apis.eblbasecomponents.PayPalUserStatusCodeType;
import ebay.apis.eblbasecomponents.PayerInfoType;
import ebay.apis.eblbasecomponents.PaymentCodeType;
import ebay.apis.eblbasecomponents.PaymentDetailsItemType;
import ebay.apis.eblbasecomponents.PaymentDetailsType;
import ebay.apis.eblbasecomponents.PaymentInfoType;
import ebay.apis.eblbasecomponents.PaymentItemInfoType;
import ebay.apis.eblbasecomponents.PaymentItemType;
import ebay.apis.eblbasecomponents.PaymentStatusCodeType;
import ebay.apis.eblbasecomponents.PaymentTransactionCodeType;
import ebay.apis.eblbasecomponents.PaymentTransactionType;
import ebay.apis.eblbasecomponents.PendingStatusCodeType;
import ebay.apis.eblbasecomponents.PersonNameType;
import ebay.apis.eblbasecomponents.ReversalReasonCodeType;
import ebay.apis.eblbasecomponents.SeverityCodeType;
import ebay.apis.eblbasecomponents.TaxIdDetailsType;

public class SecuredPayPalExpressCheckOutServiceImplTest {

  private MetaData mockCredentials;
  private GetExpressCheckoutDetailsResponseType mockGetExpressCheckoutDetailsResponseType;
  private GetExpressCheckoutDetailsResponseDetailsType mockGetExpressCheckoutDetailsResponseDetailsType;
  private PayerInfoType mockPayerInfoType;
  private PersonNameType mockPersonNameType;
  private TaxIdDetailsType mockTaxIdDetailsType;
  private AddressType mockAddressType;
  private PaymentDetailsType mockPaymentDetailsType;
  private PaymentDetailsItemType mockPaymentDetailsItemType;
  private BasicAmountType mockBasicAmountType;
  private MeasureType mockMeasureType;
  private ErrorType mockErrorType;
  private SetExpressCheckoutResponseType mockSetExpressCheckoutResponseType;
  private DoExpressCheckoutPaymentResponseType mockDoExpressCheckoutPaymentResponseType;
  private DoExpressCheckoutPaymentResponseDetailsType mockDoExpressCheckoutPaymentResponseDetailsType;
  private PaymentInfoType mockPaymentInfoType;
  private GetTransactionDetailsResponseType mockGetTransactionDetailsResponseType;
  private PaymentTransactionType mockPaymentTransactionType;
  private PaymentItemInfoType mockPaymentItemInfoType;
  private PaymentItemType mockPaymentItemType;
  private PayPalAPIAAInterface mockPalAPIAAInterface;
  private PayPalAPIInterface mockPalAPIInterface;
  private ISecuredGenericTypeCheckOutService<Order> securedPayPalExpressCheckOutServiceImpl =
      new SecuredPayPalExpressCheckOutServiceImpl<>();
  private Order spyOrder;
  private OrderRecord spyOrderRecord;
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    mockCredentials = mock(MetaData.class);
    spyOrderRecord = spy(OrderRecord.class);
    spyOrderRecord.setOrderRecordId("NUMBER");
    spyOrder = spy(Order.class);
    spyOrder.setContract(spy(Contract.class));
    spyOrder.getContract().setCustomer(spy(Customer.class));
    spyOrder.getContract().getCustomer().setAddress(spy(Address.class));
    spyOrder.getRecords().add(spyOrderRecord);
    spyOrder.setShipment(spy(Shipment.class));
    spyOrder.getShipment().setAddress(spy(Address.class));
    spyOrder.setInvoice(spy(Invoice.class));
    spyOrder.setSite(spy(Site.class));
    mockPalAPIAAInterface = mock(PayPalAPIAAInterface.class);
    mockPalAPIInterface = mock(PayPalAPIInterface.class);
    mockGetExpressCheckoutDetailsResponseType = mock(GetExpressCheckoutDetailsResponseType.class);
    mockGetExpressCheckoutDetailsResponseDetailsType = mock(GetExpressCheckoutDetailsResponseDetailsType.class);
    mockPayerInfoType = mock(PayerInfoType.class);
    mockPersonNameType = mock(PersonNameType.class);
    mockTaxIdDetailsType = mock(TaxIdDetailsType.class);
    mockAddressType = mock(AddressType.class);
    mockPaymentDetailsType = mock(PaymentDetailsType.class);
    mockPaymentDetailsItemType = mock(PaymentDetailsItemType.class);
    mockBasicAmountType = mock(BasicAmountType.class);
    mockMeasureType = mock(MeasureType.class);
    mockErrorType = mock(ErrorType.class);
    mockSetExpressCheckoutResponseType = mock(SetExpressCheckoutResponseType.class);
    mockDoExpressCheckoutPaymentResponseType = mock(DoExpressCheckoutPaymentResponseType.class);
    mockDoExpressCheckoutPaymentResponseDetailsType = mock(DoExpressCheckoutPaymentResponseDetailsType.class);
    mockPaymentInfoType = mock(PaymentInfoType.class);
    mockGetTransactionDetailsResponseType = mock(GetTransactionDetailsResponseType.class);
    mockPaymentTransactionType = mock(PaymentTransactionType.class);
    mockPaymentItemInfoType = mock(PaymentItemInfoType.class);
    mockPaymentItemType = mock(PaymentItemType.class);
    securedPayPalExpressCheckOutServiceImpl =
        new SecuredPayPalExpressCheckOutServiceImpl<>(mockPalAPIAAInterface, mockPalAPIInterface);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testDoCheckoutDetailsSuccessfully() {
    when(mockPalAPIAAInterface.getExpressCheckoutDetails(any())).thenReturn(mockGetExpressCheckoutDetailsResponseType);
    when(mockGetExpressCheckoutDetailsResponseType.getAck()).thenReturn(AckCodeType.SUCCESS);
    when(mockGetExpressCheckoutDetailsResponseType.getGetExpressCheckoutDetailsResponseDetails())
        .thenReturn(mockGetExpressCheckoutDetailsResponseDetailsType);
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getPayerInfo()).thenReturn(mockPayerInfoType);
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getToken()).thenReturn("TOKEN");
    when(mockPayerInfoType.getPayer()).thenReturn("PAYER");
    when(mockPayerInfoType.getPayerID()).thenReturn("PAYER ID");
    when(mockPayerInfoType.getPayerStatus()).thenReturn(PayPalUserStatusCodeType.VERIFIED);
    when(mockPayerInfoType.getPayerName()).thenReturn(mockPersonNameType);
    when(mockPersonNameType.getFirstName()).thenReturn("FIRST NAME");
    when(mockPersonNameType.getSuffix()).thenReturn("SUFFIX");
    when(mockPersonNameType.getMiddleName()).thenReturn("MIDDLE NAME");
    when(mockPersonNameType.getLastName()).thenReturn("LAST NAME");
    when(mockPersonNameType.getSalutation()).thenReturn("SALUTATION");
    when(mockPayerInfoType.getPayerCountry()).thenReturn(CountryCodeType.BR);
    when(mockPayerInfoType.getPayerBusiness()).thenReturn("PAYER BUSINESS");
    when(mockPayerInfoType.getTaxIdDetails()).thenReturn(mockTaxIdDetailsType);
    when(mockTaxIdDetailsType.getTaxIdType()).thenReturn("TAX ID TYPE");
    when(mockTaxIdDetailsType.getTaxId()).thenReturn("TAX ID");
    when(mockPayerInfoType.getContactPhone()).thenReturn("CONTACT PHONE");
    when(mockAddressType.getCityName()).thenReturn("CITY NAME");
    when(mockAddressType.getCountry()).thenReturn(CountryCodeType.BR);
    when(mockAddressType.getCountryName()).thenReturn("COUNTRY NAME");
    when(mockAddressType.getInternationalStateAndCity()).thenReturn("INTERNATIONAL STATE AND CITY");
    when(mockAddressType.getInternationalStreet()).thenReturn("INTERNATIONAL STREET");
    when(mockAddressType.getPhone()).thenReturn("PHONE");
    when(mockAddressType.getPostalCode()).thenReturn("POSTAL CODE");
    when(mockAddressType.getStateOrProvince()).thenReturn("STATE OR PROVINCE");
    when(mockAddressType.getStreet1()).thenReturn("STREET 1");
    when(mockAddressType.getStreet2()).thenReturn("STREET 2");
    when(mockPayerInfoType.getAddress()).thenReturn(mockAddressType);
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getCustom()).thenReturn("CUSTOM");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getInvoiceID()).thenReturn("INVOICE ID");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getContactPhone()).thenReturn("CONTACT PHONE");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getPaymentDetails())
        .thenReturn(Lists.newArrayList(mockPaymentDetailsType));
    when(mockBasicAmountType.getValue()).thenReturn("100.00");
    when(mockBasicAmountType.getCurrencyID()).thenReturn(CurrencyCodeType.BRL);
    when(mockPaymentDetailsType.getOrderTotal()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsType.getItemTotal()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsType.getShippingTotal()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsType.getInsuranceTotal()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsType.getShippingDiscount()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsType.getInsuranceOptionOffered()).thenReturn("true");
    when(mockPaymentDetailsType.getHandlingTotal()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsType.getTaxTotal()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsType.getOrderDescription()).thenReturn("ORDER DESCRIPTION");
    when(mockPaymentDetailsType.getCustom()).thenReturn("CUSTOM");
    when(mockPaymentDetailsType.getInvoiceID()).thenReturn("INVOICE ID");
    when(mockPaymentDetailsType.getShipToAddress()).thenReturn(mockAddressType);
    when(mockPaymentDetailsType.getPaymentDetailsItem()).thenReturn(Lists.newArrayList(mockPaymentDetailsItemType));
    when(mockPaymentDetailsItemType.getNumber()).thenReturn("NUMBER");
    when(mockPaymentDetailsItemType.getName()).thenReturn("NAME");
    when(mockPaymentDetailsItemType.getDescription()).thenReturn("DESCRIPTION");
    when(mockPaymentDetailsItemType.getAmount()).thenReturn(mockBasicAmountType);
    when(mockMeasureType.getUnit()).thenReturn("UNIT");
    when(mockMeasureType.getValue()).thenReturn(100.00);
    when(mockPaymentDetailsItemType.getQuantity()).thenReturn(BigInteger.valueOf(100L));
    when(mockPaymentDetailsItemType.getTax()).thenReturn(mockBasicAmountType);
    when(mockPaymentDetailsItemType.getItemWeight()).thenReturn(mockMeasureType);
    when(mockPaymentDetailsItemType.getItemLength()).thenReturn(mockMeasureType);
    when(mockPaymentDetailsItemType.getItemWidth()).thenReturn(mockMeasureType);
    when(mockPaymentDetailsItemType.getItemHeight()).thenReturn(mockMeasureType);
    when(mockPaymentDetailsItemType.getItemURL()).thenReturn("URL");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getNote()).thenReturn("NOTE");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getCheckoutStatus()).thenReturn("CHECKOUT STATUS");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getGiftMessage()).thenReturn("GIFT MESSAGE");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getGiftReceiptEnable()).thenReturn("true");
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getGiftWrapAmount()).thenReturn(mockBasicAmountType);
    when(mockGetExpressCheckoutDetailsResponseDetailsType.getBuyerMarketingEmail()).thenReturn("BUYER MARKETING EMAIL");
    securedPayPalExpressCheckOutServiceImpl.doCheckoutDetails(mockCredentials, spyOrder);
    assertEquals("Token", "TOKEN", spyOrder.getToken());
    assertEquals("Payer", "PAYER", spyOrder.getContract().getCustomer().getPayer());
    assertEquals("PayerId", "PAYER ID", spyOrder.getContract().getCustomer().getPayerId());
    assertEquals("PayerStatus", "verified", spyOrder.getContract().getCustomer().getPayerStatus());
    assertEquals("FirstName", "FIRST NAME", spyOrder.getContract().getCustomer().getFirstName());
    assertEquals("Suffix", "SUFFIX", spyOrder.getContract().getCustomer().getSuffix());
    assertEquals("MiddleName", "MIDDLE NAME", spyOrder.getContract().getCustomer().getMiddleName());
    assertEquals("LastName", "LAST NAME", spyOrder.getContract().getCustomer().getLastName());
    assertEquals("Salutation", "SALUTATION", spyOrder.getContract().getCustomer().getSalutation());
    // TODO add Customer.country to data model.
    // assertEquals("Country", "BR",
    // spyOrder.getContract().getCustomer().getAddress().getCountry());
    assertEquals("PayerBusiness", "PAYER BUSINESS", spyOrder.getContract().getCustomer().getPayerBusiness());
    assertEquals("TaxIdType", "TAX ID TYPE", spyOrder.getContract().getCustomer().getTaxIdType());
    assertEquals("TaxId", "TAX ID", spyOrder.getContract().getCustomer().getTaxId());
    assertEquals("ContactPhone", "CONTACT PHONE", spyOrder.getContract().getCustomer().getContactPhone());
    assertEquals("CityName", "CITY NAME", spyOrder.getContract().getCustomer().getAddress().getCityName());
    assertEquals("Country", "BR", spyOrder.getContract().getCustomer().getAddress().getCountry());
    assertEquals("CountryName", "COUNTRY NAME", spyOrder.getContract().getCustomer().getAddress().getCountryName());
    assertEquals("InternationalStateAndCity", "INTERNATIONAL STATE AND CITY",
        spyOrder.getContract().getCustomer().getAddress().getInternationalStateAndCity());
    assertEquals("InternationalStreet", "INTERNATIONAL STREET",
        spyOrder.getContract().getCustomer().getAddress().getInternationalStreet());
    assertEquals("Phone", "PHONE", spyOrder.getContract().getCustomer().getAddress().getPhone());
    assertEquals("PostalCode", "POSTAL CODE", spyOrder.getContract().getCustomer().getAddress().getPostalCode());
    assertEquals("StateOrProvince", "STATE OR PROVINCE",
        spyOrder.getContract().getCustomer().getAddress().getStateOrProvince());
    assertEquals("Street1", "STREET 1", spyOrder.getContract().getCustomer().getAddress().getStreet1());
    assertEquals("Street2", "STREET 2", spyOrder.getContract().getCustomer().getAddress().getStreet2());
    assertEquals("Custom", "CUSTOM", spyOrder.getCustom());
    assertEquals("InvoiceId", "INVOICE ID", spyOrder.getInvoice().getInvoiceId());
    assertEquals("ContactPhone", "CONTACT PHONE", spyOrder.getContract().getCustomer().getContactPhone());
    assertEquals("OrderTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getOrderTotal()));
    assertEquals("ItemTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getItemTotal()));
    assertEquals("ShippingTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getShippingTotal()));
    assertEquals("InsuranceTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getInsuranceTotal()));
    assertEquals("ShippingDiscount", 0, BigDecimal.valueOf(-100.00).compareTo(spyOrder.getShippingDiscount()));
    assertEquals("InsuranceOptionOffered", true, spyOrder.getInsuranceOptionOffered());
    assertEquals("HandlingTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getHandlingTotal()));
    assertEquals("TaxTotal", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getTaxTotal()));
    assertEquals("OrderDescription", "ORDER DESCRIPTION", spyOrder.getOrderDescription());
    assertEquals("Custom", "CUSTOM", spyOrder.getCustom());
    assertEquals("InvoiceId", "INVOICE ID", spyOrder.getInvoice().getInvoiceId());
    assertEquals("CityName", "CITY NAME", spyOrder.getShipment().getAddress().getCityName());
    assertEquals("Country", "BR", spyOrder.getShipment().getAddress().getCountry());
    assertEquals("CountryName", "COUNTRY NAME", spyOrder.getShipment().getAddress().getCountryName());
    assertEquals("InternationalStateAndCity", "INTERNATIONAL STATE AND CITY",
        spyOrder.getShipment().getAddress().getInternationalStateAndCity());
    assertEquals("InternationalStreet", "INTERNATIONAL STREET",
        spyOrder.getShipment().getAddress().getInternationalStreet());
    assertEquals("Phone", "PHONE", spyOrder.getShipment().getAddress().getPhone());
    assertEquals("PostalCode", "POSTAL CODE", spyOrder.getShipment().getAddress().getPostalCode());
    assertEquals("StateOrProvince", "STATE OR PROVINCE", spyOrder.getShipment().getAddress().getStateOrProvince());
    assertEquals("Street1", "STREET 1", spyOrder.getShipment().getAddress().getStreet1());
    assertEquals("Street2", "STREET 2", spyOrder.getShipment().getAddress().getStreet2());
    assertEquals("OrderRecordId", "NUMBER", spyOrderRecord.getOrderRecordId());
    assertEquals("Name", "NAME", spyOrderRecord.getName());
    assertEquals("Description", "DESCRIPTION", spyOrderRecord.getDescription());
    assertEquals("Amount", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getAmount()));
    assertEquals("Quantity", 0, BigInteger.valueOf(100L).compareTo(spyOrderRecord.getQuantity()));
    assertEquals("Tax", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getTax()));
    assertEquals("ItemWeight", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getItemWeight()));
    assertEquals("ItemLength", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getItemLength()));
    assertEquals("ItemWidth", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getItemWidth()));
    assertEquals("ItemHeight", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getItemHeight()));
    assertEquals("ItemUrl", "URL", spyOrderRecord.getItemUrl());
    assertEquals("Note", "NOTE", spyOrder.getNote());
    assertEquals("CheckoutStatus", "CHECKOUT STATUS", spyOrder.getCheckoutStatus());
    assertEquals("GiftMessage", "GIFT MESSAGE", spyOrder.getGiftMessage());
    assertEquals("GiftReceiptEnable", true, spyOrder.getGiftReceiptEnable());
    assertEquals("GiftWrapAmount", 0, BigDecimal.valueOf(100.00).compareTo(spyOrder.getGiftWrapAmount()));
    assertEquals("BuyerMarketingEmail", "BUYER MARKETING EMAIL",
        spyOrder.getContract().getCustomer().getBuyerMarketingEmail());
    verify(mockPalAPIAAInterface).getExpressCheckoutDetails(any());
  }

  @Test
  public void testDoCheckoutDetailsuUnSuccessfully() {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage(
        "Exception from Paypal Express Checkout Details [errors={errorCode=ERROR CODE longMessage=LONG MESSAGE severityCode=CustomCode shortMessage=SHORT MESSAGE}], please try again.");
    when(mockPalAPIAAInterface.getExpressCheckoutDetails(any())).thenReturn(mockGetExpressCheckoutDetailsResponseType);
    when(mockGetExpressCheckoutDetailsResponseType.getAck()).thenReturn(AckCodeType.FAILURE);
    when(mockErrorType.getErrorCode()).thenReturn("ERROR CODE");
    when(mockErrorType.getLongMessage()).thenReturn("LONG MESSAGE");
    when(mockErrorType.getSeverityCode()).thenReturn(SeverityCodeType.CUSTOM_CODE);
    when(mockErrorType.getShortMessage()).thenReturn("SHORT MESSAGE");
    when(mockGetExpressCheckoutDetailsResponseType.getErrors()).thenReturn(Lists.newArrayList(mockErrorType));
    securedPayPalExpressCheckOutServiceImpl.doCheckoutDetails(mockCredentials, spyOrder);
  }

  @Test
  public void testDoCheckoutSuccessfully() {
    when(mockPalAPIAAInterface.setExpressCheckout(any())).thenReturn(mockSetExpressCheckoutResponseType);
    when(mockSetExpressCheckoutResponseType.getAck()).thenReturn(AckCodeType.SUCCESS);
    when(mockSetExpressCheckoutResponseType.getToken()).thenReturn("TOKEN");
    securedPayPalExpressCheckOutServiceImpl.doCheckout(mockCredentials, spyOrder);
    assertEquals("Token", "TOKEN", spyOrder.getToken());
    verify(mockPalAPIAAInterface).setExpressCheckout(any());
  }

  @Test
  public void testDoCheckoutUnSuccessfully() {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage(
        "Exception from Paypal Express Checkout [errors={errorCode=ERROR CODE longMessage=LONG MESSAGE severityCode=CustomCode shortMessage=SHORT MESSAGE}], please try again.");
    when(mockPalAPIAAInterface.setExpressCheckout(any())).thenReturn(mockSetExpressCheckoutResponseType);
    when(mockSetExpressCheckoutResponseType.getAck()).thenReturn(AckCodeType.FAILURE);
    when(mockErrorType.getErrorCode()).thenReturn("ERROR CODE");
    when(mockErrorType.getLongMessage()).thenReturn("LONG MESSAGE");
    when(mockErrorType.getSeverityCode()).thenReturn(SeverityCodeType.CUSTOM_CODE);
    when(mockErrorType.getShortMessage()).thenReturn("SHORT MESSAGE");
    when(mockSetExpressCheckoutResponseType.getErrors()).thenReturn(Lists.newArrayList(mockErrorType));
    securedPayPalExpressCheckOutServiceImpl.doCheckout(mockCredentials, spyOrder);
  }

  @Test
  public void testDoCheckoutPaymentSuccessfully() throws DatatypeConfigurationException {
    when(mockPalAPIAAInterface.doExpressCheckoutPayment(any())).thenReturn(mockDoExpressCheckoutPaymentResponseType);
    when(mockDoExpressCheckoutPaymentResponseType.getAck()).thenReturn(AckCodeType.SUCCESS);
    when(mockDoExpressCheckoutPaymentResponseType.getDoExpressCheckoutPaymentResponseDetails())
        .thenReturn(mockDoExpressCheckoutPaymentResponseDetailsType);
    when(mockDoExpressCheckoutPaymentResponseDetailsType.getToken()).thenReturn("TOKEN");
    when(mockDoExpressCheckoutPaymentResponseDetailsType.getPaymentInfo())
        .thenReturn(Lists.newArrayList(mockPaymentInfoType));
    when(mockPaymentInfoType.getTransactionID()).thenReturn("TRANSACTION ID");
    when(mockPaymentInfoType.getTransactionType()).thenReturn(PaymentTransactionCodeType.EXPRESS_CHECKOUT);
    when(mockPaymentInfoType.getPaymentType()).thenReturn(PaymentCodeType.INSTANT);
    when(mockPaymentInfoType.getPaymentDate())
        .thenReturn(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
    when(mockBasicAmountType.getValue()).thenReturn("100.00");
    when(mockBasicAmountType.getCurrencyID()).thenReturn(CurrencyCodeType.BRL);
    when(mockPaymentInfoType.getGrossAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getFeeAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getSettleAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getTaxAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getExchangeRate()).thenReturn("EXCHANGE RATE");
    when(mockPaymentInfoType.getPaymentStatus()).thenReturn(PaymentStatusCodeType.COMPLETED);
    when(mockPaymentInfoType.getPendingReason()).thenReturn(PendingStatusCodeType.VERIFY);
    when(mockPaymentInfoType.getReasonCode()).thenReturn(ReversalReasonCodeType.NONE);
    when(mockPaymentInfoType.getHoldDecision()).thenReturn("HOLD DECISION");
    when(mockPaymentInfoType.getProtectionEligibilityType()).thenReturn("PROTECTION ELIGIBILITY TYPE");
    when(mockPaymentInfoType.getStoreID()).thenReturn("STORE ID");
    when(mockPaymentInfoType.getTerminalID()).thenReturn("TERMINAL ID");
    when(mockPaymentInfoType.getPaymentRequestID()).thenReturn("PAYMENT REQUEST ID");
    when(mockDoExpressCheckoutPaymentResponseDetailsType.getNote()).thenReturn("NOTE");
    when(mockDoExpressCheckoutPaymentResponseDetailsType.getBillingAgreementID()).thenReturn("BILLING AGREEMENT ID");
    securedPayPalExpressCheckOutServiceImpl.doCheckoutPayment(mockCredentials, spyOrder);
    final Payment payment = spyOrder.getInvoice().getPayments().iterator().next();
    assertEquals("Token", "TOKEN", spyOrder.getToken());
    assertEquals("TransactionId", "TRANSACTION ID", payment.getTransactionId());
    assertEquals("TransactionType", "express-checkout", payment.getTransactionType());
    assertEquals("PaymentType", "instant", payment.getPaymentType());
    assertEquals("PaymentDate", LocalDate.now().toString(), payment.getPaymentDate().toString());
    assertEquals("GrossAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getGrossAmount()));
    assertEquals("FeeAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getFeeAmount()));
    assertEquals("SettleAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getSettleAmount()));
    assertEquals("TaxAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getTaxAmount()));
    assertEquals("ExchangeRate", "EXCHANGE RATE", payment.getExchangeRate());
    assertEquals("PaymentStatus", "Completed", payment.getPaymentStatus());
    assertEquals("PendingReason", "verify", payment.getPendingReason());
    assertEquals("ReasonCode", "none", payment.getReasonCode());
    assertEquals("HoldDecision", "HOLD DECISION", payment.getHoldDecision());
    assertEquals("ProtectionEligibilityType", "PROTECTION ELIGIBILITY TYPE", payment.getProtectionEligibilityType());
    assertEquals("StoreId", "STORE ID", payment.getStoreId());
    assertEquals("TerminalId", "TERMINAL ID", payment.getTerminalId());
    assertEquals("PaymentRequestId", "PAYMENT REQUEST ID", payment.getPaymentRequestId());
    verify(mockPalAPIAAInterface).doExpressCheckoutPayment(any());
  }

  @Test
  public void testDoCheckoutPaymentUnSuccessfully() {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage(
        "Exception from Paypal Express Checkout [errors={errorCode=ERROR CODE longMessage=LONG MESSAGE severityCode=CustomCode shortMessage=SHORT MESSAGE}], please try again.");
    when(mockPalAPIAAInterface.doExpressCheckoutPayment(any())).thenReturn(mockDoExpressCheckoutPaymentResponseType);
    when(mockDoExpressCheckoutPaymentResponseType.getAck()).thenReturn(AckCodeType.FAILURE);
    when(mockErrorType.getErrorCode()).thenReturn("ERROR CODE");
    when(mockErrorType.getLongMessage()).thenReturn("LONG MESSAGE");
    when(mockErrorType.getSeverityCode()).thenReturn(SeverityCodeType.CUSTOM_CODE);
    when(mockErrorType.getShortMessage()).thenReturn("SHORT MESSAGE");
    when(mockDoExpressCheckoutPaymentResponseType.getErrors()).thenReturn(Lists.newArrayList(mockErrorType));
    securedPayPalExpressCheckOutServiceImpl.doCheckoutPayment(mockCredentials, spyOrder);
  }

  @Test
  public void testDoNotificationSucessfully() {
    when(mockPalAPIInterface.getTransactionDetails(any())).thenReturn(mockGetTransactionDetailsResponseType);
    when(mockGetTransactionDetailsResponseType.getAck()).thenReturn(AckCodeType.SUCCESS);
    when(mockGetTransactionDetailsResponseType.getPaymentTransactionDetails()).thenReturn(mockPaymentTransactionType);
    when(mockPaymentTransactionType.getPaymentInfo()).thenReturn(mockPaymentInfoType);
    when(mockPaymentInfoType.getTransactionID()).thenReturn("TRANSACTION ID");
    when(mockPaymentTransactionType.getPaymentItemInfo()).thenReturn(mockPaymentItemInfoType);
    when(mockPaymentItemInfoType.getCustom()).thenReturn("CUSTOM");
    securedPayPalExpressCheckOutServiceImpl.doNotification(mockCredentials, spyOrder);
    assertEquals("TransactionId", "TRANSACTION ID", spyOrder.getTransactionId());
    assertEquals("Custom", "CUSTOM", spyOrder.getCustom());
    verify(mockPalAPIInterface).getTransactionDetails(any());
  }

  @Test
  public void testDoNotificationUnSucessfully() {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage(
        "Exception from Paypal Express Checkout [errors={errorCode=ERROR CODE longMessage=LONG MESSAGE severityCode=CustomCode shortMessage=SHORT MESSAGE}], please try again.");
    when(mockPalAPIInterface.getTransactionDetails(any())).thenReturn(mockGetTransactionDetailsResponseType);
    when(mockGetTransactionDetailsResponseType.getAck()).thenReturn(AckCodeType.FAILURE);
    when(mockErrorType.getErrorCode()).thenReturn("ERROR CODE");
    when(mockErrorType.getLongMessage()).thenReturn("LONG MESSAGE");
    when(mockErrorType.getSeverityCode()).thenReturn(SeverityCodeType.CUSTOM_CODE);
    when(mockErrorType.getShortMessage()).thenReturn("SHORT MESSAGE");
    when(mockGetTransactionDetailsResponseType.getErrors()).thenReturn(Lists.newArrayList(mockErrorType));
    securedPayPalExpressCheckOutServiceImpl.doNotification(mockCredentials, spyOrder);
  }

  @Test
  public void testDoTransactionDetailsSucessfully() throws DatatypeConfigurationException {
    when(mockPalAPIInterface.getTransactionDetails(any())).thenReturn(mockGetTransactionDetailsResponseType);
    when(mockGetTransactionDetailsResponseType.getAck()).thenReturn(AckCodeType.SUCCESS);
    when(mockGetTransactionDetailsResponseType.getPaymentTransactionDetails()).thenReturn(mockPaymentTransactionType);
    when(mockPaymentTransactionType.getPayerInfo()).thenReturn(mockPayerInfoType);
    when(mockPayerInfoType.getPayer()).thenReturn("PAYER");
    when(mockPayerInfoType.getPayerID()).thenReturn("PAYER ID");
    when(mockPayerInfoType.getPayerStatus()).thenReturn(PayPalUserStatusCodeType.VERIFIED);
    when(mockPayerInfoType.getPayerName()).thenReturn(mockPersonNameType);
    when(mockPersonNameType.getFirstName()).thenReturn("FIRST NAME");
    when(mockPersonNameType.getSuffix()).thenReturn("SUFFIX");
    when(mockPersonNameType.getMiddleName()).thenReturn("MIDDLE NAME");
    when(mockPersonNameType.getLastName()).thenReturn("LAST NAME");
    when(mockPersonNameType.getSalutation()).thenReturn("SALUTATION");
    when(mockPayerInfoType.getPayerCountry()).thenReturn(CountryCodeType.BR);
    when(mockPayerInfoType.getPayerBusiness()).thenReturn("PAYER BUSINESS");
    when(mockPayerInfoType.getTaxIdDetails()).thenReturn(mockTaxIdDetailsType);
    when(mockTaxIdDetailsType.getTaxIdType()).thenReturn("TAX ID TYPE");
    when(mockTaxIdDetailsType.getTaxId()).thenReturn("TAX ID");
    when(mockPayerInfoType.getContactPhone()).thenReturn("CONTACT PHONE");
    when(mockAddressType.getCityName()).thenReturn("CITY NAME");
    when(mockAddressType.getCountry()).thenReturn(CountryCodeType.BR);
    when(mockAddressType.getCountryName()).thenReturn("COUNTRY NAME");
    when(mockAddressType.getInternationalStateAndCity()).thenReturn("INTERNATIONAL STATE AND CITY");
    when(mockAddressType.getInternationalStreet()).thenReturn("INTERNATIONAL STREET");
    when(mockAddressType.getPhone()).thenReturn("PHONE");
    when(mockAddressType.getPostalCode()).thenReturn("POSTAL CODE");
    when(mockAddressType.getStateOrProvince()).thenReturn("STATE OR PROVINCE");
    when(mockAddressType.getStreet1()).thenReturn("STREET 1");
    when(mockAddressType.getStreet2()).thenReturn("STREET 2");
    when(mockPayerInfoType.getAddress()).thenReturn(mockAddressType);
    when(mockPaymentTransactionType.getPaymentInfo()).thenReturn(mockPaymentInfoType);
    when(mockPaymentInfoType.getTransactionID()).thenReturn("TRANSACTION ID");
    when(mockPaymentInfoType.getTransactionType()).thenReturn(PaymentTransactionCodeType.EXPRESS_CHECKOUT);
    when(mockPaymentInfoType.getPaymentType()).thenReturn(PaymentCodeType.INSTANT);
    when(mockPaymentInfoType.getPaymentDate())
        .thenReturn(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
    when(mockBasicAmountType.getValue()).thenReturn("100.00");
    when(mockBasicAmountType.getCurrencyID()).thenReturn(CurrencyCodeType.BRL);
    when(mockPaymentInfoType.getGrossAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getFeeAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getSettleAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getTaxAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentInfoType.getExchangeRate()).thenReturn("EXCHANGE RATE");
    when(mockPaymentInfoType.getPaymentStatus()).thenReturn(PaymentStatusCodeType.COMPLETED);
    when(mockPaymentInfoType.getPendingReason()).thenReturn(PendingStatusCodeType.VERIFY);
    when(mockPaymentInfoType.getReasonCode()).thenReturn(ReversalReasonCodeType.NONE);
    when(mockPaymentInfoType.getHoldDecision()).thenReturn("HOLD DECISION");
    when(mockPaymentInfoType.getProtectionEligibilityType()).thenReturn("PROTECTION ELIGIBILITY TYPE");
    when(mockPaymentInfoType.getStoreID()).thenReturn("STORE ID");
    when(mockPaymentInfoType.getTerminalID()).thenReturn("TERMINAL ID");
    when(mockPaymentInfoType.getPaymentRequestID()).thenReturn("PAYMENT REQUEST ID");
    when(mockPaymentTransactionType.getPaymentItemInfo()).thenReturn(mockPaymentItemInfoType);
    when(mockPaymentItemInfoType.getInvoiceID()).thenReturn("INVOICE ID");
    when(mockPaymentItemInfoType.getCustom()).thenReturn("CUSTOM");
    when(mockPaymentItemInfoType.getPaymentItem()).thenReturn(Lists.newArrayList(mockPaymentItemType));
    when(mockPaymentItemType.getNumber()).thenReturn("NUMBER");
    when(mockPaymentItemType.getName()).thenReturn("NAME");
    when(mockPaymentItemType.getAmount()).thenReturn(mockBasicAmountType);
    when(mockPaymentItemType.getQuantity()).thenReturn("100");
    securedPayPalExpressCheckOutServiceImpl.doTransactionDetails(mockCredentials, spyOrder);
    assertEquals("Payer", "PAYER", spyOrder.getContract().getCustomer().getPayer());
    assertEquals("PayerId", "PAYER ID", spyOrder.getContract().getCustomer().getPayerId());
    assertEquals("PayerStatus", "verified", spyOrder.getContract().getCustomer().getPayerStatus());
    assertEquals("FirstName", "FIRST NAME", spyOrder.getContract().getCustomer().getFirstName());
    assertEquals("Suffix", "SUFFIX", spyOrder.getContract().getCustomer().getSuffix());
    assertEquals("MiddleName", "MIDDLE NAME", spyOrder.getContract().getCustomer().getMiddleName());
    assertEquals("LastName", "LAST NAME", spyOrder.getContract().getCustomer().getLastName());
    assertEquals("Salutation", "SALUTATION", spyOrder.getContract().getCustomer().getSalutation());
    // TODO add Customer.country to data model.
    // assertEquals("Country", "BR",
    // spyOrder.getContract().getCustomer().getAddress().getCountry());
    assertEquals("PayerBusiness", "PAYER BUSINESS", spyOrder.getContract().getCustomer().getPayerBusiness());
    assertEquals("TaxIdType", "TAX ID TYPE", spyOrder.getContract().getCustomer().getTaxIdType());
    assertEquals("TaxId", "TAX ID", spyOrder.getContract().getCustomer().getTaxId());
    assertEquals("ContactPhone", "CONTACT PHONE", spyOrder.getContract().getCustomer().getContactPhone());
    assertEquals("CityName", "CITY NAME", spyOrder.getContract().getCustomer().getAddress().getCityName());
    assertEquals("Country", "BR", spyOrder.getContract().getCustomer().getAddress().getCountry());
    assertEquals("CountryName", "COUNTRY NAME", spyOrder.getContract().getCustomer().getAddress().getCountryName());
    assertEquals("InternationalStateAndCity", "INTERNATIONAL STATE AND CITY",
        spyOrder.getContract().getCustomer().getAddress().getInternationalStateAndCity());
    assertEquals("InternationalStreet", "INTERNATIONAL STREET",
        spyOrder.getContract().getCustomer().getAddress().getInternationalStreet());
    assertEquals("Phone", "PHONE", spyOrder.getContract().getCustomer().getAddress().getPhone());
    assertEquals("PostalCode", "POSTAL CODE", spyOrder.getContract().getCustomer().getAddress().getPostalCode());
    assertEquals("StateOrProvince", "STATE OR PROVINCE",
        spyOrder.getContract().getCustomer().getAddress().getStateOrProvince());
    assertEquals("Street1", "STREET 1", spyOrder.getContract().getCustomer().getAddress().getStreet1());
    assertEquals("Street2", "STREET 2", spyOrder.getContract().getCustomer().getAddress().getStreet2());
    final Payment payment = spyOrder.getInvoice().getPayments().iterator().next();
    assertEquals("TransactionId", "TRANSACTION ID", payment.getTransactionId());
    assertEquals("TransactionType", "express-checkout", payment.getTransactionType());
    assertEquals("PaymentType", "instant", payment.getPaymentType());
    assertEquals("PaymentDate", LocalDate.now().toString(), payment.getPaymentDate().toString());
    assertEquals("GrossAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getGrossAmount()));
    assertEquals("FeeAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getFeeAmount()));
    assertEquals("SettleAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getSettleAmount()));
    assertEquals("TaxAmount", 0, BigDecimal.valueOf(100.00).compareTo(payment.getTaxAmount()));
    assertEquals("ExchangeRate", "EXCHANGE RATE", payment.getExchangeRate());
    assertEquals("PaymentStatus", "Completed", payment.getPaymentStatus());
    assertEquals("PendingReason", "verify", payment.getPendingReason());
    assertEquals("ReasonCode", "none", payment.getReasonCode());
    assertEquals("HoldDecision", "HOLD DECISION", payment.getHoldDecision());
    assertEquals("ProtectionEligibilityType", "PROTECTION ELIGIBILITY TYPE", payment.getProtectionEligibilityType());
    assertEquals("StoreId", "STORE ID", payment.getStoreId());
    assertEquals("TerminalId", "TERMINAL ID", payment.getTerminalId());
    assertEquals("PaymentRequestId", "PAYMENT REQUEST ID", payment.getPaymentRequestId());
    assertEquals("getInvoiceId", "INVOICE ID", spyOrder.getInvoice().getInvoiceId());
    assertEquals("getInvoiceId", "CUSTOM", spyOrder.getCustom());
    assertEquals("OrderRecordId", "NUMBER", spyOrderRecord.getOrderRecordId());
    assertEquals("Name", "NAME", spyOrderRecord.getName());
    assertEquals("Amount", 0, BigDecimal.valueOf(100.00).compareTo(spyOrderRecord.getAmount()));
    assertEquals("Quantity", 0, BigInteger.valueOf(100L).compareTo(spyOrderRecord.getQuantity()));
    assertEquals("OrderRecordId", "NUMBER", spyOrderRecord.getOrderRecordId());
    verify(mockPalAPIInterface).getTransactionDetails(any());
  }

  @Test
  public void testDoTransactionDetailsUnSucessfully() {
    expectedException.expect(GNUOpenBusinessServiceException.class);
    expectedException.expectMessage(
        "Exception from Paypal Express Checkout [errors={errorCode=ERROR CODE longMessage=LONG MESSAGE severityCode=CustomCode shortMessage=SHORT MESSAGE}], please try again.");
    when(mockPalAPIInterface.getTransactionDetails(any())).thenReturn(mockGetTransactionDetailsResponseType);
    when(mockGetTransactionDetailsResponseType.getAck()).thenReturn(AckCodeType.FAILURE);
    when(mockErrorType.getErrorCode()).thenReturn("ERROR CODE");
    when(mockErrorType.getLongMessage()).thenReturn("LONG MESSAGE");
    when(mockErrorType.getSeverityCode()).thenReturn(SeverityCodeType.CUSTOM_CODE);
    when(mockErrorType.getShortMessage()).thenReturn("SHORT MESSAGE");
    when(mockGetTransactionDetailsResponseType.getErrors()).thenReturn(Lists.newArrayList(mockErrorType));
    securedPayPalExpressCheckOutServiceImpl.doTransactionDetails(mockCredentials, spyOrder);
  }
}
