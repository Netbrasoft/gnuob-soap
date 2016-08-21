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

public abstract interface IContextVisitor {

  public abstract Context visit(Category category);

  public abstract Context visit(Content content);

  public abstract Context visit(Contract contract);

  public abstract Context visit(Customer customer);

  public abstract Context visit(Group group);

  public abstract Context visit(Offer offer);

  public abstract Context visit(Order order);

  public abstract Context visit(Product product);

  public abstract Context visit(Setting setting);

  public abstract Context visit(Site site);

  public abstract Context visit(User user);
}
