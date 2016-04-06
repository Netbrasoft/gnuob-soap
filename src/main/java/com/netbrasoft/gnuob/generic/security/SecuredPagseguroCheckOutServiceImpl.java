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

package com.netbrasoft.gnuob.generic.security;

import static com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SECURED_PAGSEGURO_CHECK_OUT_SERVICE_IMPL_NAME;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import com.netbrasoft.gnuob.generic.order.Order;
import com.netbrasoft.gnuob.generic.order.AbstractPagseguroCheckOutServiceImpl;
import com.netbrasoft.gnuob.generic.security.Rule.Operation;
import com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@Stateless(name = SECURED_PAGSEGURO_CHECK_OUT_SERVICE_IMPL_NAME,
    mappedName = SECURED_PAGSEGURO_CHECK_OUT_SERVICE_IMPL_NAME)
@Interceptors(value = {AccessControl.class, AppSimonInterceptor.class})
public class SecuredPagseguroCheckOutServiceImpl<T extends Order> extends AbstractPagseguroCheckOutServiceImpl<T>
    implements ISecuredGenericTypeCheckOutService<T> {

  @Override
  @OperationAccess(operation = Operation.UPDATE)
  public void doCheckout(final MetaData credentials, final T type) {
    super.doCheckout(type);
  }

  @Override
  @OperationAccess(operation = Operation.UPDATE)
  public void doCheckoutDetails(final MetaData credentials, final T type) {
    super.doCheckoutDetails(type);
  }

  @Override
  @OperationAccess(operation = Operation.UPDATE)
  public void doCheckoutPayment(final MetaData credentials, final T type) {
    super.doCheckoutPayment(type);
  }

  @Override
  @OperationAccess(operation = Operation.UPDATE)
  public T doNotification(final MetaData credentials, final T type) {
    return super.doNotification(type);
  }

  @Override
  @OperationAccess(operation = Operation.UPDATE)
  public void doRefundTransaction(final MetaData credentials, final T type) {
    super.doRefundTransaction(type);
  }

  @Override
  @OperationAccess(operation = Operation.UPDATE)
  public void doTransactionDetails(final MetaData credentials, final T type) {
    super.doTransactionDetails(type);
  }
}
