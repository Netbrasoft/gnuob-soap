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

import static br.com.netbrasoft.gnuob.generic.content.mail.BodyEnum.CONFIRMATION_NEW_ORDER_BODY;
import static br.com.netbrasoft.gnuob.generic.content.mail.BodyEnum.NONE_BODY;
import static br.com.netbrasoft.gnuob.generic.content.mail.SubjectEnum.CONFIRMATION_NEW_ORDER_SUBJECT;
import static br.com.netbrasoft.gnuob.generic.content.mail.SubjectEnum.NONE_SUBJECT;

public enum MailEnum {

  //@formatter:off
  WELCOME_NEW_CUSTOMER_MAIL(CONFIRMATION_NEW_ORDER_SUBJECT, CONFIRMATION_NEW_ORDER_BODY),
  CONFIRMATION_NEW_ORDER_MAIL(CONFIRMATION_NEW_ORDER_SUBJECT, CONFIRMATION_NEW_ORDER_BODY),
  NO_MAIL(NONE_SUBJECT, NONE_BODY);
  //@formatter:on

  private SubjectEnum subject;
  private BodyEnum body;

  private MailEnum(final SubjectEnum subject, final BodyEnum body) {
    this.subject = subject;
    this.body = body;
  }

  public BodyEnum getBody() {
    return body;
  }

  public SubjectEnum getSubject() {
    return subject;
  }

  public boolean isEnabled() {
    return !NO_MAIL.equals(this);
  }
}
