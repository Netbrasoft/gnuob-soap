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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.EMAIL_PROPERTY;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PAGSEGURO_PRODUCTION_TOKEN_PROPERTY_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PRODUCTION_TOKEN_PROPERTY;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SANDBOX_TOKEN_PROPERTY;
import static br.com.uol.pagseguro.properties.PagSeguroConfig.setProductionEnvironment;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.service.NotificationService;
import br.com.uol.pagseguro.service.TransactionSearchService;
import br.com.uol.pagseguro.service.checkout.CheckoutService;

class PagseguroCheckOutServiceImp implements IPagseguroCheckOutService {

  PagseguroCheckOutServiceImp() {
    if (!PAGSEGURO_PRODUCTION_TOKEN_PROPERTY_VALUE.equals(PRODUCTION_TOKEN_PROPERTY)) {
      setProductionEnvironment();
    }
  }

  @Override
  public String createCheckoutRequest(final Checkout checkout, final Boolean onlyCheckoutCode)
      throws PagSeguroServiceException {
    return CheckoutService.createCheckoutRequest(
        new AccountCredentials(EMAIL_PROPERTY, PRODUCTION_TOKEN_PROPERTY, SANDBOX_TOKEN_PROPERTY), checkout,
        onlyCheckoutCode);
  }

  @Override
  public Transaction checkTransaction(final String notificationCode) throws PagSeguroServiceException {
    return NotificationService.checkTransaction(
        new AccountCredentials(EMAIL_PROPERTY, PRODUCTION_TOKEN_PROPERTY, SANDBOX_TOKEN_PROPERTY), notificationCode);
  }

  @Override
  public Transaction searchByCode(final String transactionCode) throws PagSeguroServiceException {
    return TransactionSearchService.searchByCode(
        new AccountCredentials(EMAIL_PROPERTY, PRODUCTION_TOKEN_PROPERTY, SANDBOX_TOKEN_PROPERTY), transactionCode);
  }
}
