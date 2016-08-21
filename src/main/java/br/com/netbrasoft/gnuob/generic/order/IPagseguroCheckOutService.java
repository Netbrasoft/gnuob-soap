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

import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.domain.checkout.Checkout;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;

public abstract interface IPagseguroCheckOutService {

  public abstract String createCheckoutRequest(Checkout checkout, Boolean onlyCheckoutCode)
      throws PagSeguroServiceException;

  public abstract Transaction checkTransaction(String notificationCode) throws PagSeguroServiceException;

  public abstract Transaction searchByCode(String transactionCode) throws PagSeguroServiceException;
}
