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

package br.com.netbrasoft.gnuob.generic.content.contexts;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CATEGORY_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTEXT_VISITOR_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTRACT_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CUSTOMER_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GROUP_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.OFFER_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.ORDER_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.PRODUCT_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SETTING_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE_PARAM_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER_PARAM_NAME;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import br.com.netbrasoft.gnuob.generic.category.Category;
import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.offer.Offer;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.product.Product;
import br.com.netbrasoft.gnuob.generic.security.Group;
import br.com.netbrasoft.gnuob.generic.security.Site;
import br.com.netbrasoft.gnuob.generic.security.User;
import br.com.netbrasoft.gnuob.generic.setting.Setting;
import br.com.netbrasoft.gnuob.monitor.AppSimonInterceptor;

@Stateless(name = CONTEXT_VISITOR_IMPL_NAME)
@Interceptors(value = {AppSimonInterceptor.class})
public class ContextVisitorImpl implements IContextVisitor {

  @Override
  public Context visit(final Category category) {
    final VelocityContext context = new VelocityContext();
    context.put(CATEGORY_PARAM_NAME, category);
    return context;
  }

  @Override
  public Context visit(final Content content) {
    final VelocityContext context = new VelocityContext();
    context.put(CONTENT_PARAM_NAME, content);
    return context;
  }

  @Override
  public Context visit(final Contract contract) {
    final VelocityContext context = new VelocityContext();
    context.put(CONTRACT_PARAM_NAME, contract);
    return context;
  }

  @Override
  public Context visit(final Customer customer) {
    final VelocityContext context = new VelocityContext();
    context.put(CUSTOMER_PARAM_NAME, customer);
    return context;
  }

  @Override
  public Context visit(final Group group) {
    final VelocityContext context = new VelocityContext();
    context.put(GROUP_PARAM_NAME, group);
    return context;
  }

  @Override
  public Context visit(final Offer offer) {
    final VelocityContext context = new VelocityContext();
    context.put(OFFER_PARAM_NAME, offer);
    return context;
  }

  @Override
  public Context visit(final Order order) {
    final VelocityContext context = new VelocityContext();
    context.put(ORDER_PARAM_NAME, order);
    return context;
  }

  @Override
  public Context visit(final Product product) {
    final VelocityContext context = new VelocityContext();
    context.put(PRODUCT_PARAM_NAME, product);
    return context;
  }

  @Override
  public Context visit(final Setting setting) {
    final VelocityContext context = new VelocityContext();
    context.put(SETTING_PARAM_NAME, setting);
    return context;
  }

  @Override
  public Context visit(final Site site) {
    final VelocityContext context = new VelocityContext();
    context.put(SITE_PARAM_NAME, site);
    return context;
  }

  @Override
  public Context visit(final User user) {
    final VelocityContext context = new VelocityContext();
    context.put(USER_PARAM_NAME, user);
    return context;
  }
}
