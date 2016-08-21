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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SECURED_PAY_PAL_EXPRESS_CHECK_OUT_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.security.Rule.Operation.UPDATE;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import br.com.netbrasoft.gnuob.generic.order.AbstractPayPalExpressCheckOutServiceImpl;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;
import ebay.api.paypalapi.PayPalAPIAAInterface;
import ebay.api.paypalapi.PayPalAPIInterface;

@Stateless(name = SECURED_PAY_PAL_EXPRESS_CHECK_OUT_SERVICE_IMPL_NAME,
    mappedName = SECURED_PAY_PAL_EXPRESS_CHECK_OUT_SERVICE_IMPL_NAME)
@Interceptors(value = {AccessControl.class, AppSimonInterceptor.class})
public class SecuredPayPalExpressCheckOutServiceImpl<T extends Order>
    extends AbstractPayPalExpressCheckOutServiceImpl<T> implements ISecuredGenericTypeCheckOutService<T> {

  public SecuredPayPalExpressCheckOutServiceImpl() {
    // This constructor will be used by the EJB container.
  }

  SecuredPayPalExpressCheckOutServiceImpl(final PayPalAPIAAInterface palAPIAAInterface,
      final PayPalAPIInterface palAPIInterface) {
    super(palAPIAAInterface, palAPIInterface);
  }

  @Override
  @OperationAccess(operation = UPDATE)
  public void doCheckout(final MetaData credentials, final T type) {
    super.doCheckout(type);
  }

  @Override
  @OperationAccess(operation = UPDATE)
  public void doCheckoutDetails(final MetaData credentials, final T type) {
    super.doCheckoutDetails(type);
  }

  @Override
  @OperationAccess(operation = UPDATE)
  public void doCheckoutPayment(final MetaData credentials, final T type) {
    super.doCheckoutPayment(type);
  }

  @Override
  @OperationAccess(operation = UPDATE)
  public T doNotification(final MetaData credentials, final T type) {
    return super.doNotification(type);
  }

  @Override
  @OperationAccess(operation = UPDATE)
  public void doRefundTransaction(final MetaData credentials, final T type) {
    super.doRefundTransaction(type);
  }

  @Override
  @OperationAccess(operation = UPDATE)
  public void doTransactionDetails(final MetaData credentials, final T type) {
    super.doTransactionDetails(type);
  }
}
