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

package br.com.netbrasoft.gnuob.generic.factory;

import br.com.netbrasoft.gnuob.generic.AbstractType;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.category.Category;
import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.customer.PostalCode;
import br.com.netbrasoft.gnuob.generic.offer.Offer;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.product.Product;
import br.com.netbrasoft.gnuob.generic.security.Group;
import br.com.netbrasoft.gnuob.generic.security.MetaData;
import br.com.netbrasoft.gnuob.generic.security.Site;
import br.com.netbrasoft.gnuob.generic.security.User;
import br.com.netbrasoft.gnuob.generic.setting.Setting;

public final class MessageCreaterFactory {

  private static final String LEFT_BLOCK_SEPERATOR = "[";
  private static final String MIDDLE_BLOCK_SEPERATOR = "][";
  private static final String RIGHT_BLOCK_SEPERATOR = "]";

  private MessageCreaterFactory() {}

  public static String createMessage(final String operation, final MetaData credentials, final AbstractType type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  private static StringBuilder createMessageLeftPart(final String operation, final MetaData credentials) {
    return new StringBuilder().append(LEFT_BLOCK_SEPERATOR).append(operation).append(MIDDLE_BLOCK_SEPERATOR)
        .append(credentials);
  }

  private static StringBuilder createMessageRightPart(final Paging paging, final OrderByEnum orderingProperty) {
    return new StringBuilder().append(paging).append(orderingProperty).append(createMessageRightPart());
  }

  private static StringBuilder createMessageRightPart() {
    return new StringBuilder(RIGHT_BLOCK_SEPERATOR);
  }

  public static String createMessage(final String operation, final MetaData credentials, final AbstractType type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Order type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Order type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Category type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Category type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Content type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Content type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Contract type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Contract type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Customer type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Customer type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final PostalCode type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final PostalCode type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Offer type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Offer type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Product type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Product type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Setting type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Setting type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Group type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Group type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Site type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final Site type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final User type) {
    return createMessageLeftPart(operation, credentials).append(type).append(createMessageRightPart()).toString();
  }

  public static String createMessage(final String operation, final MetaData credentials, final User type,
      final Paging paging, final OrderByEnum orderingProperty) {
    return createMessageLeftPart(operation, credentials).append(type)
        .append(createMessageRightPart(paging, orderingProperty)).toString();
  }
}
