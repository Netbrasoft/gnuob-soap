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

package br.com.netbrasoft.gnuob;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.netbrasoft.gnuob.generic.GenericSuite;
import br.com.netbrasoft.gnuob.generic.category.CategorySuite;
import br.com.netbrasoft.gnuob.generic.content.ContentSuite;
import br.com.netbrasoft.gnuob.generic.content.contexts.ContextSuite;
import br.com.netbrasoft.gnuob.generic.contract.ContractSuite;
import br.com.netbrasoft.gnuob.generic.customer.CustomerSuite;
import br.com.netbrasoft.gnuob.generic.offer.OfferSuite;
import br.com.netbrasoft.gnuob.generic.order.OrderSuite;
import br.com.netbrasoft.gnuob.generic.product.ProductSuite;
import br.com.netbrasoft.gnuob.generic.security.SecuritySuite;
import br.com.netbrasoft.gnuob.generic.setting.SettingSuite;

@RunWith(Suite.class)
@SuiteClasses({GenericSuite.class, CategorySuite.class, ContentSuite.class, ContextSuite.class, ContractSuite.class,
    CustomerSuite.class, OfferSuite.class, OrderSuite.class, ProductSuite.class, SecuritySuite.class,
    SettingSuite.class})
public class AllTests {

}
