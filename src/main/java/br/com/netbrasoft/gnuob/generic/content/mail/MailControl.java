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

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.BR_COM_NETBRASOFT_GNUOB_GENERIC_CONTENT_CONTENT_RESOURCE_LOADER;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.BR_COM_NETBRASOFT_GNUOB_GENERIC_CONTENT_MAIL_MAIL_CONTROL_SEND_MAIL_ACTION;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_RESOURCE_LOADER_CACHE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_RESOURCE_LOADER_CLASS;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_RESOURCE_LOADER_DESCRIPTION;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_RESOURCE_LOADER_MODIFICATION_CHECK_INTERVAL;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_RESOURCE_LOADER_PASSWORD;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_RESOURCE_LOADER_SITE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTENT_RESOURCE_LOADER_USER;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.CONTEXT_VISITOR_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.FALSE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GENERIC_TYPE_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.JAVA_JBOSS_MAIL_DEFAULT;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.MODIFICATION_CHECK_INTERVAL_IN_SEC;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.RESOURCE_LOADER;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.TEXT_HTML_CHARSET_UTF_8;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED_VALUE;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.VELOCITY_CONTENT_RESOURCE_LOADER;
import static com.google.common.collect.Lists.newArrayList;
import static javax.mail.Transport.send;
import static javax.persistence.LockModeType.NONE;

import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.javasimon.SimonManager;
import org.javasimon.Split;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.IGenericTypeService;
import br.com.netbrasoft.gnuob.generic.content.contexts.IContextVisitor;
import br.com.netbrasoft.gnuob.generic.security.AbstractAccess;
import br.com.netbrasoft.gnuob.generic.security.MetaData;

public class MailControl<A extends AbstractAccess> {

  @EJB(beanName = GENERIC_TYPE_SERVICE_IMPL_NAME)
  private IGenericTypeService<A> accessTypeService;

  @EJB(beanName = CONTEXT_VISITOR_IMPL_NAME)
  private IContextVisitor contextVisitor;

  @Resource(mappedName = JAVA_JBOSS_MAIL_DEFAULT)
  private Session session;

  public MailControl() {
    // This constructor will be used by the EJB container.
  }

  MailControl(final IGenericTypeService<A> accessTypeService, final IContextVisitor contextVisitor,
      final Session session) {
    this.accessTypeService = accessTypeService;
    this.contextVisitor = contextVisitor;
    this.session = session;
  }

  @AroundInvoke
  public Object intercept(final InvocationContext ctx) {
    final Split split =
        SimonManager.getStopwatch(BR_COM_NETBRASOFT_GNUOB_GENERIC_CONTENT_MAIL_MAIL_CONTROL_SEND_MAIL_ACTION).start();
    try {
      final Object proces = ctx.proceed();
      processMail(getCredentials(ctx), getAttachedTyp(ctx), getMailAction(ctx));
      return proces;
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException("Mail action exception.", e);
    } finally {
      split.stop();
    }
  }

  private MetaData getCredentials(final InvocationContext ctx) {
    return (MetaData) newArrayList(ctx.getParameters()).stream().filter(e -> e instanceof MetaData).findFirst().get();
  }

  private A getAttachedTyp(final InvocationContext ctx) {
    return accessTypeService.find(getType(ctx), getType(ctx).getId(), NONE);
  }

  @SuppressWarnings(UNCHECKED_VALUE)
  private A getType(final InvocationContext ctx) {
    return (A) newArrayList(ctx.getParameters()).stream().filter(e -> e instanceof AbstractAccess).findFirst().get();
  }

  private MailAction getMailAction(final InvocationContext ctx) {
    return ctx.getMethod().getAnnotation(MailAction.class);
  }

  private void processMail(final MetaData credentials, final A type, final MailAction mailAction) {
    try {
      if (mailAction.operation().isEnabled()) {
        send(mail(credentials, type, mailAction));
      }
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException("Mail control exception during email processing.", e);
    }
  }

  private Message mail(final MetaData credentials, final A type, final MailAction mailAction)
      throws MessagingException {
    final VelocityEngine templateEngine = initTemplateEngine(credentials);
    final Message message = new MimeMessage(session);
    final InternetAddress internetAddress = new InternetAddress("bendraaisma@gmail.com");
    message.setSentDate(new Date());
    message.setFrom(new InternetAddress(System.getProperty("gnuob." + credentials.getSite() + ".email")));
    message.setRecipients(Message.RecipientType.TO, new InternetAddress[] {internetAddress});
    message.setSubject(getSubject(type, mailAction, templateEngine));
    message.setContent(getContent(type, mailAction, templateEngine), TEXT_HTML_CHARSET_UTF_8);
    return message;
  }

  private VelocityEngine initTemplateEngine(final MetaData credentials) {
    final VelocityEngine ve = new VelocityEngine(createVelocityEngineProperties(credentials));
    ve.init();
    return ve;
  }

  private Properties createVelocityEngineProperties(final MetaData credentials) {
    final Properties properties = new Properties();
    properties.setProperty(RESOURCE_LOADER, CONTENT);
    properties.setProperty(CONTENT_RESOURCE_LOADER_CLASS,
        BR_COM_NETBRASOFT_GNUOB_GENERIC_CONTENT_CONTENT_RESOURCE_LOADER);
    properties.setProperty(CONTENT_RESOURCE_LOADER_DESCRIPTION, VELOCITY_CONTENT_RESOURCE_LOADER);
    properties.setProperty(CONTENT_RESOURCE_LOADER_CACHE, FALSE);
    properties.setProperty(CONTENT_RESOURCE_LOADER_MODIFICATION_CHECK_INTERVAL, MODIFICATION_CHECK_INTERVAL_IN_SEC);
    properties.setProperty(CONTENT_RESOURCE_LOADER_USER, credentials.getUser());
    properties.setProperty(CONTENT_RESOURCE_LOADER_PASSWORD, credentials.getPassword());
    properties.setProperty(CONTENT_RESOURCE_LOADER_SITE, credentials.getSite());
    return properties;
  }

  private String getSubject(final A type, final MailAction mailAction, final VelocityEngine templateEngine) {
    return fillTemplate(type, templateEngine.getTemplate(mailAction.operation().getSubject().getTemplate()));
  }

  private String getContent(final A type, final MailAction mailAction, final VelocityEngine templateEngine) {
    return fillTemplate(type, templateEngine.getTemplate(mailAction.operation().getBody().getTemplate()));
  }

  private String fillTemplate(final A type, final Template template) {
    final StringWriter stringWriter = new StringWriter();
    template.merge(type.accept(contextVisitor), stringWriter);
    return stringWriter.toString();
  }
}
