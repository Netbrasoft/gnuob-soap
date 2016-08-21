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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GNUOB_SITE_REDIRECT_PROPERTY_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAGSEGURO_NOTIFICATION_PROPERTY_VALUE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.security.OperationAccess;
import br.com.netbrasoft.gnuob.generic.security.Rule.Operation;
import br.com.uol.pagseguro.domain.Address;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.Shipping;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;

public abstract class AbstractPagseguroCheckOutServiceImpl<O extends Order> implements ICheckOutService<O> {

  private final IPagseguroCheckOutService pagseguroCheckOutService;

  public AbstractPagseguroCheckOutServiceImpl() {
    this(new PagseguroCheckOutServiceImp());
  }

  protected AbstractPagseguroCheckOutServiceImpl(final IPagseguroCheckOutService pagseguroCheckOutService) {
    this.pagseguroCheckOutService = pagseguroCheckOutService;
  }

  private Address doAddress(final br.com.netbrasoft.gnuob.generic.customer.Address address) {
    final Address addressType = new Address();
    addressType.setCity(address.getCityName());
    addressType.setStreet(address.getStreet1());
    addressType.setComplement(address.getComplement());
    addressType.setCountry(address.getCountry());
    addressType.setDistrict(address.getDistrict());
    addressType.setNumber(address.getNumber());
    addressType.setPostalCode(address.getPostalCode());
    addressType.setState(address.getStateOrProvince());
    return addressType;
  }

  private void doAddress(final br.com.netbrasoft.gnuob.generic.customer.Address address, final Address addressType) {
    address.setCountry(addressType.getCountry());
    address.setStateOrProvince(addressType.getState());
    address.setCityName(addressType.getCity());
    address.setPostalCode(addressType.getPostalCode());
    address.setDistrict(addressType.getDistrict());
    address.setStreet1(addressType.getStreet());
    address.setNumber(addressType.getNumber());
    address.setComplement(addressType.getComplement());
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doCheckout(final O order) {

    // set checkout request fields.
    final Checkout checkout = new Checkout();
    checkout.setCurrency(
        Currency.valueOf(NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getCurrencyCode()));
    checkout.setExtraAmount(order.getExtraAmount().setScale(2));
    checkout.setNotificationURL(System.getProperty("gnuob." + order.getSite().getName() + ".pagsegure.notification",
        PAGSEGURO_NOTIFICATION_PROPERTY_VALUE));
    checkout.setRedirectURL(System.getProperty("gnuob." + order.getSite().getName() + ".pagseguro.redirect",
        GNUOB_SITE_REDIRECT_PROPERTY_VALUE));
    checkout.setReference(order.getOrderId());

    // set checkout shipping request details field..
    checkout.setShipping(doShipping(order));

    // set checkout items request details fields.
    checkout.setItems(doItems(order));

    // Set checkout sender request details fields.
    checkout.setSender(doSender(order));

    try {
      // Do checkout call.
      final String response = pagseguroCheckOutService.createCheckoutRequest(checkout, false);
      order.setToken(response.split("code=")[1]);
    } catch (final PagSeguroServiceException e) {
      throw new GNUOpenBusinessServiceException("Exception from Pagseguro Checkout, please try again.", e);
    }
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doCheckoutDetails(final O order) {
    try {
      final Transaction transaction = pagseguroCheckOutService.searchByCode(order.getTransactionId());

      // Information about the payer.
      doSender(order, transaction.getSender());

      // Information about the payment.
      doPaymentDetails(order, transaction);

      // Status of the checkout session. If payment is completed, the
      // transaction identification number of the resulting transaction is
      // returned.
      order.setCheckoutStatus(transaction.getStatus().name());
    } catch (final PagSeguroServiceException e) {
      throw new GNUOpenBusinessServiceException("Exception from Pagseguro Checkout, please try again.", e);
    }
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doCheckoutPayment(final O order) {
    try {
      final Transaction transaction = pagseguroCheckOutService.searchByCode(order.getTransactionId());

      // Information about the payment.
      order.getInvoice().getPayments().add(doPaymentInfo(transaction));

      // Status of the checkout session. If payment is completed, the
      // transaction identification number of the resulting transaction is
      // returned.
      order.setCheckoutStatus(transaction.getStatus().name());
    } catch (final PagSeguroServiceException e) {
      throw new GNUOpenBusinessServiceException("Exception from Pagseguro Checkout, please try again.", e);
    }
  }

  private Item doItem(final OrderRecord orderRecord) {
    final Item item = new Item();
    item.setAmount(orderRecord.getAmount().setScale(2));
    item.setDescription(orderRecord.getDescription());
    item.setId(orderRecord.getOrderRecordId());
    item.setQuantity(orderRecord.getQuantity().intValue());
    item.setShippingCost(orderRecord.getShippingCost().setScale(2));
    item.setWeight(orderRecord.getItemWeight().longValue());
    return item;
  }

  private void doItem(final OrderRecord orderRecord, final Item item) {
    orderRecord.setDescription(item.getDescription());
    orderRecord.setAmount(item.getAmount());
    orderRecord.setQuantity(BigInteger.valueOf(item.getQuantity()));
  }

  private List<Item> doItems(final O order) {

    final List<Item> items = new ArrayList<>();

    // Details about each individual item included in the order.
    for (final OrderRecord orderRecord : order.getRecords()) {
      items.add(doItem(orderRecord));
    }

    return items;
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public O doNotification(final O order) {
    try {
      final Transaction transaction = pagseguroCheckOutService.checkTransaction(order.getNotificationId());

      // Information about the order and transaction.
      order.setTransactionId(transaction.getCode());
      order.setOrderId(transaction.getReference());
      order.setNotificationId(null);

      return order;
    } catch (final PagSeguroServiceException e) {
      throw new GNUOpenBusinessServiceException("Exception from Pagseguro Notification, please try again.", e);
    }
  }

  private void doPaymentDetails(final O order, final Transaction transaction) {
    order.setTransactionId(transaction.getCode());
    order.setOrderId(transaction.getReference());
    order.setDiscountTotal(transaction.getDiscountAmount());
    order.setExtraAmount(transaction.getExtraAmount());
    doShipping(order, transaction.getShipping());

    // Details about each individual item included in the order.
    for (final Item item : transaction.getItems()) {
      for (final OrderRecord orderRecord : order.getRecords()) {
        if (item.getId() == orderRecord.getOrderRecordId()) {
          doItem(orderRecord, item);
          break;
        }
      }
    }
  }

  private Payment doPaymentInfo(final Transaction transaction) {
    final Payment payment = new Payment();

    // Unique transaction ID of the payment.
    payment.setTransactionId(transaction.getCode());

    // Type of transaction.
    payment.setTransactionType(transaction.getType().name());

    // Indicates whether the payment is instant or delayed.
    payment.setPaymentType(transaction.getPaymentMethod().getType().getType());

    // The final amount charged, including any shipping and taxes from your
    // Merchant Profile.
    payment.setGrossAmount(transaction.getGrossAmount());

    // Amount deposited in your Pagseguro account after a currency
    // conversion.
    payment.setSettleAmount(transaction.getNetAmount());

    // Time/date stamp of payment.
    if (transaction.getEscrowEndDate() != null) {
      payment.setPaymentDate(new Date(transaction.getEscrowEndDate().getTime()));
    }

    // Reason that this payment is being held.
    payment.setHoldDecision(transaction.getCancellationSource());

    // Indica o número de parcelas que o comprador escolheu no pagamento com
    // cartão de crédito.
    payment.setInstallmentCount(BigInteger.valueOf(transaction.getInstallmentCount()));

    // Pagseguro fee amount charged for the transaction.
    payment.setFeeAmount(transaction.getFeeAmount());

    // The status of the payment.
    payment.setPaymentStatus(transaction.getStatus().name());

    return payment;
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doRefundTransaction(final O order) {
    throw new UnsupportedOperationException("Refund transaction is not supported for PagSeguro.");
  }

  private Sender doSender(final O order) {
    final Sender sender = new Sender();
    sender.setEmail(order.getContract().getCustomer().getBuyerEmail());
    sender.setName(order.getContract().getCustomer().getFriendlyName());
    return sender;
  }

  private void doSender(final O order, final Sender sender) {
    order.getContract().getCustomer().setPayer(sender.getEmail());
    order.getContract().getCustomer().setFriendlyName(sender.getName());
    order.getContract().getCustomer().setContactPhone(sender.getPhone().toString());
  }

  private Shipping doShipping(final O order) {
    final Shipping shipping = new Shipping();

    final BigDecimal cost = order.getShippingTotal().subtract(order.getShippingDiscount());
    shipping.setCost(cost.setScale(2));
    shipping.setType(ShippingType.valueOf(order.getShipment().getShipmentType()));

    // Address to which the order is shipped.
    shipping.setAddress(doAddress(order.getShipment().getAddress()));
    return shipping;
  }

  private void doShipping(final O order, final Shipping shipping) {
    order.setShippingTotal(shipping.getCost());
    order.getShipment().setShipmentType(shipping.getType().name());
    doAddress(order.getShipment().getAddress(), shipping.getAddress());
  }

  @Override
  @OperationAccess(operation = Operation.NONE)
  public void doTransactionDetails(final O order) {
    try {
      final Transaction transaction = pagseguroCheckOutService.searchByCode(order.getTransactionId());

      // Information about the payer.
      doSender(order, transaction.getSender());

      // Information about the payment.
      doPaymentDetails(order, transaction);

      // Information about the payment.
      order.getInvoice().getPayments().add(doPaymentInfo(transaction));

      // Status of the checkout session. If payment is completed, the
      // transaction identification number of the resulting transaction is
      // returned.
      order.setCheckoutStatus(transaction.getStatus().name());

    } catch (final PagSeguroServiceException e) {
      throw new GNUOpenBusinessServiceException("Exception from Pagseguro Transaction Details, please try again.", e);
    }
  }
}
