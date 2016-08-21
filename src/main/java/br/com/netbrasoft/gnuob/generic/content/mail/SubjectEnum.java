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

package br.com.netbrasoft.gnuob.generic.content.mail;

public enum SubjectEnum {

  //@formatter:off
  CONFIRMATION_NEW_CUSTOMER_SUBJECT("confirmation_new_customer_subject.vm"),
  CONFIRMATION_NEW_ORDER_SUBJECT("confirmation_new_order_subject.vm"),
  NONE_SUBJECT("");
  //@formatter:on

  private String template;

  private SubjectEnum(final String template) {
    this.template = template;
  }

  public String getTemplate() {
    return template;
  }
}
