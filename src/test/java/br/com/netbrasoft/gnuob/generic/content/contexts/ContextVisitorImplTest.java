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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.netbrasoft.gnuob.generic.category.Category;
import br.com.netbrasoft.gnuob.generic.content.Content;
import br.com.netbrasoft.gnuob.generic.content.contexts.ContextVisitorImpl;
import br.com.netbrasoft.gnuob.generic.content.contexts.IContextVisitor;
import br.com.netbrasoft.gnuob.generic.contract.Contract;
import br.com.netbrasoft.gnuob.generic.customer.Customer;
import br.com.netbrasoft.gnuob.generic.offer.Offer;
import br.com.netbrasoft.gnuob.generic.order.Order;
import br.com.netbrasoft.gnuob.generic.product.Product;
import br.com.netbrasoft.gnuob.generic.security.Group;
import br.com.netbrasoft.gnuob.generic.security.Site;
import br.com.netbrasoft.gnuob.generic.security.User;
import br.com.netbrasoft.gnuob.generic.setting.Setting;

public class ContextVisitorImplTest {

  private Category mockCategory;
  private Content mockContent;
  private Contract mockContract;
  private Customer mockCustomer;
  private Group mockGroup;
  private Offer mockOffer;
  private Order mockOrder;
  private Product mockProduct;
  private Setting mockSetting;
  private Site mockSite;
  private User mockUser;
  private IContextVisitor contextVisitorImpl;

  @Before
  public void setUp() throws Exception {
    mockCategory = mock(Category.class);
    mockContent = mock(Content.class);
    mockContract = mock(Contract.class);
    mockCustomer = mock(Customer.class);
    mockGroup = mock(Group.class);
    mockOffer = mock(Offer.class);
    mockOrder = mock(Order.class);
    mockProduct = mock(Product.class);
    mockSetting = mock(Setting.class);
    mockSite = mock(Site.class);
    mockUser = mock(User.class);
    contextVisitorImpl = new ContextVisitorImpl();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testVisitCategory() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockCategory));
  }

  @Test
  public void testVisitContent() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockContent));
  }

  @Test
  public void testVisitContract() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockContract));
  }

  @Test
  public void testVisitCustomer() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockCustomer));
  }

  @Test
  public void testVisitGroup() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockGroup));
  }

  @Test
  public void testVisitOffer() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockOffer));
  }

  @Test
  public void testVisitOrder() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockOrder));
  }

  @Test
  public void testVisitProduct() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockProduct));
  }

  @Test
  public void testVisitSetting() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockSetting));
  }

  @Test
  public void testVisitSite() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockSite));
  }

  @Test
  public void testVisitUser() {
    assertNotNull("Visit", contextVisitorImpl.visit(mockUser));
  }
}
