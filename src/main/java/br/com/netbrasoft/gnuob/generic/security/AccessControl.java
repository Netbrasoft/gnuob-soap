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

package br.com.netbrasoft.gnuob.generic.security;

import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.GENERIC_TYPE_SERVICE_IMPL_NAME;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.SITE_ID;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.UNCHECKED;
import static br.com.netbrasoft.gnuob.generic.NetbrasoftSoapConstants.USER_ID;

import java.util.Arrays;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.LockModeType;

import org.javasimon.SimonManager;
import org.javasimon.Split;

import br.com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import br.com.netbrasoft.gnuob.generic.AbstractType;
import br.com.netbrasoft.gnuob.generic.IGenericTypeService;
import br.com.netbrasoft.gnuob.generic.OrderByEnum;
import br.com.netbrasoft.gnuob.generic.Paging;
import br.com.netbrasoft.gnuob.generic.Parameter;
import br.com.netbrasoft.gnuob.generic.security.Rule.Operation;
import de.rtner.security.auth.spi.SimplePBKDF2;

public class AccessControl<A extends AbstractAccess, U extends User, S extends Site> {

  @SuppressWarnings(UNCHECKED)
  private class Subject {

    private S site = (S) Site.getInstance();
    private U user = (U) User.getInstance();

    public boolean isUserLinkedToSite() {
      return user.getSites().contains(site);
    }
  }

  @EJB(beanName = GENERIC_TYPE_SERVICE_IMPL_NAME)
  private IGenericTypeService<A> accessTypeService;

  @EJB(beanName = GENERIC_TYPE_SERVICE_IMPL_NAME)
  private IGenericTypeService<U> userServiceImpl;

  @EJB(beanName = GENERIC_TYPE_SERVICE_IMPL_NAME)
  private IGenericTypeService<S> siteServiceImpl;

  @AroundInvoke
  public Object intercept(final InvocationContext ctx) {
    switch (ctx.getMethod().getAnnotation(OperationAccess.class).operation()) {
      case CREATE:
        return createOperationAccess(ctx);
      case READ:
        return readOperationAccess(ctx);
      case UPDATE:
        return updateOperationAccess(ctx);
      case DELETE:
        return deleteOperationAccess(ctx);
      default:
        return noneOperationAccess(ctx);
    }
  }

  private Object createOperationAccess(final InvocationContext ctx) {
    final Split split = SimonManager
        .getStopwatch("br.com.netbrasoft.gnuob.generic.security.AccessControl.createOperationAccess").start();
    try {
      return processCreateOperationAccess(ctx, authenticateSubject(getMetaData(ctx)), getAbstractType(ctx));
    } finally {
      split.stop();
    }
  }

  @SuppressWarnings(UNCHECKED)
  private Object processCreateOperationAccess(final InvocationContext ctx, final Subject subject,
      final AbstractType abstractType) {
    if (isRoot(subject) || isAllowedToUpdate(subject, Operation.CREATE)) {
      try {
        if (abstractType instanceof AbstractAccess) {
          createAccess((A) abstractType, subject);
        }
        return ctx.proceed();
      } catch (final Exception e) {
        throw new GNUOpenBusinessServiceException(e.getMessage(), e);
      }
    } else {
      throw new GNUOpenBusinessServiceException(String.format(
          "Given user [%s] doesn't have the right access to create this entity object, verify that the given user has access (ERR01)",
          subject.user.getName()));
    }
  }

  private void createAccess(final A detachedAccess, final Subject subject) {
    detachedAccess.setGroup(subject.user.getGroup());
    detachedAccess.setSite(subject.site);
    detachedAccess.setOwner(subject.user);
    if (detachedAccess.getPermission() == null) {
      detachedAccess.setPermission(Permission.getInstance());
      detachedAccess.getPermission().setOwner(subject.user.getPermission().getOwner());
      detachedAccess.getPermission().setGroup(subject.user.getPermission().getGroup());
      detachedAccess.getPermission().setOthers(subject.user.getPermission().getOthers());
    }
  }

  private Object readOperationAccess(final InvocationContext ctx) {
    final Split split =
        SimonManager.getStopwatch("br.com.netbrasoft.gnuob.generic.security.AccessControl.readOperationAccess").start();
    try {
      return processReadOperationAccess(ctx, authenticateSubject(getMetaData(ctx)));
    } finally {
      split.stop();
    }
  }

  private Object processReadOperationAccess(final InvocationContext ctx, final Subject subject) {
    if (isRoot(subject) || isAllowedToUpdate(subject, Operation.READ)) {
      enableAccessFilter(subject);
      try {
        return ctx.proceed();
      } catch (final Exception e) {
        throw new GNUOpenBusinessServiceException(e.getMessage(), e);
      } finally {
        disableAccessFilter();
      }
    } else {
      throw new GNUOpenBusinessServiceException(String.format(
          "Given user [%s] doesn't have the right access to read this entity object, verify that the given user has access (ERR01)",
          subject.user.getName()));
    }
  }

  private void enableAccessFilter(final Subject subject) {
    accessTypeService.enableFilter(AbstractAccess.NFQ1, Parameter.getInstance(USER_ID, subject.user.getId()));
    accessTypeService.enableFilter(AbstractAccess.NFQ2, Parameter.getInstance(SITE_ID, subject.site.getId()));
  }

  private void disableAccessFilter() {
    accessTypeService.disableFilter(AbstractAccess.NFQ1);
    accessTypeService.disableFilter(AbstractAccess.NFQ2);
  }

  private Object noneOperationAccess(final InvocationContext ctx) {
    final Split split =
        SimonManager.getStopwatch("br.com.netbrasoft.gnuob.generic.security.AccessControl.noneOperationAccess").start();
    try {
      return ctx.proceed();
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      split.stop();
    }
  }

  private Object updateOperationAccess(final InvocationContext ctx) {
    final Split split = SimonManager
        .getStopwatch("br.com.netbrasoft.gnuob.generic.security.AccessControl.updateOperationAccess").start();
    try {
      return processOperationAccess(ctx, authenticateSubject(getMetaData(ctx)), getAbstractType(ctx), Operation.UPDATE);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      split.stop();
    }
  }

  private Object deleteOperationAccess(final InvocationContext ctx) {
    final Split split = SimonManager
        .getStopwatch("br.com.netbrasoft.gnuob.generic.security.AccessControl.deleteOperationAccess").start();
    try {
      return processOperationAccess(ctx, authenticateSubject(getMetaData(ctx)), getAbstractType(ctx), Operation.DELETE);
    } catch (final Exception e) {
      throw new GNUOpenBusinessServiceException(e.getMessage(), e);
    } finally {
      split.stop();
    }
  }

  private MetaData getMetaData(final InvocationContext ctx) {
    return (MetaData) Arrays.asList(ctx.getParameters()).stream().filter(e -> e instanceof MetaData).findFirst().get();
  }

  private AbstractType getAbstractType(final InvocationContext ctx) {
    return (AbstractType) Arrays.asList(ctx.getParameters()).stream().filter(e -> e instanceof AbstractType).findFirst()
        .get();
  }

  private Object processOperationAccess(final InvocationContext ctx, final Subject subject,
      final AbstractType abstractType, final Operation operation) {
    if (isRoot(subject) || isAllowedToUpdate(subject, operation)) {
      verifyOperationAccess(subject, abstractType, operation);
      try {
        return ctx.proceed();
      } catch (final Exception e) {
        throw new GNUOpenBusinessServiceException(e.getMessage(), e);
      }
    } else {
      throw new GNUOpenBusinessServiceException(String.format(
          "Given user [%s] doesn't have the right access to %s this entity object, verify that the given user has access (ERR01)",
          subject.user.getName(), operation.toString().toLowerCase()));
    }
  }

  @SuppressWarnings(UNCHECKED)
  private void verifyOperationAccess(final Subject subject, final AbstractType abstractType,
      final Operation operation) {
    if (abstractType instanceof AbstractAccess) {
      final A attachedAccess = getAbstractAccess(abstractType);
      if (isAllowedToUpdateAccess(subject, operation, attachedAccess)) {
        updateAccess((A) abstractType, attachedAccess);
      } else {
        throw new GNUOpenBusinessServiceException(String.format(
            "Given user [%s] doesn't have the right access to %s this entity object, verify that the given user has access (ERR02)",
            subject.user.getName(), operation.toString().toLowerCase()));
      }
    }
  }

  private boolean isAllowedToUpdateAccess(final Subject subject, final Operation operation, final A attachedAccess) {
    return isRoot(subject) || hasOwnership(subject, attachedAccess, operation)
        || hasGroupOwnership(subject, attachedAccess, operation) || hasOtherOwnership(attachedAccess, operation);
  }

  private boolean isRoot(final Subject subject) {
    return subject.user.getRoot() == null ? false : subject.user.getRoot();
  }

  private boolean isAllowedToUpdate(final Subject subject, final Operation operation) {
    return subject.user.getAccess().getOperations().contains(operation);
  }

  private void updateAccess(final A detachedAccess, final A attachedAccess) {
    if (detachedAccess.getPermission() == null) {
      detachedAccess.setPermission(attachedAccess.getPermission());
    }
    detachedAccess.setGroup(attachedAccess.getGroup());
    detachedAccess.setSite(attachedAccess.getSite());
    detachedAccess.setOwner(attachedAccess.getOwner());
    detachedAccess.setCreation(attachedAccess.getCreation());
    detachedAccess.setModification(attachedAccess.getModification());
  }

  private boolean hasOwnership(final Subject subject, final A attachedAccess, final Operation operation) {
    return attachedAccess.getOwner().equals(subject.user)
        && attachedAccess.getPermission().getOwner().getOperations().contains(operation);
  }

  private boolean hasGroupOwnership(final Subject subject, final A attachedAccess, final Operation operation) {
    return subject.user.getGroups().stream().filter(e -> e.equals(attachedAccess.getGroup())
        && attachedAccess.getPermission().getGroup().getOperations().contains(operation)).count() > 0;
  }

  private boolean hasOtherOwnership(final A attachedAccess, final Operation operation) {
    return attachedAccess.getPermission().getOthers().getOperations().contains(operation);
  }

  @SuppressWarnings(UNCHECKED)
  private A getAbstractAccess(final AbstractType abstractType) {
    final A abstractAccess = accessTypeService.find((A) abstractType, abstractType.getId(), LockModeType.NONE);
    if (abstractAccess != null) {
      return abstractAccess;
    }
    throw new GNUOpenBusinessServiceException("Entity object is not found in database, access is denied (ERR03)");
  }

  private Subject authenticateSubject(final MetaData metaData) {
    final Split split =
        SimonManager.getStopwatch("br.com.netbrasoft.gnuob.generic.security.AccessControl.authenticateSubject").start();
    try {
      return processAuthenticatedSubject(metaData);
    } finally {
      split.stop();
    }
  }

  private Subject processAuthenticatedSubject(final MetaData metaData) {
    final Subject subject = new Subject();
    subject.site = getSiteAndCheckIt(metaData.getSite());
    subject.user = getUserAndCheckCredentials(metaData.getUser(), metaData.getPassword());
    if (!subject.isUserLinkedToSite()) {
      throw new GNUOpenBusinessServiceException(String.format(
          "Given user [%s] doesn't have the right access for site [%s], verify that the given user have access to the site (ERR04)",
          metaData.getUser(), metaData.getSite()));
    }
    return subject;
  }

  private S getSiteAndCheckIt(final String site) {
    if (containSite(site)) {
      return findSite(site);
    }
    throw new GNUOpenBusinessServiceException(String
        .format("Given site [%s] doesn't have the right access, verify that the given site has access (ERR01)", site));
  }

  private U getUserAndCheckCredentials(final String userName, final String candidatePassword) {
    if (containUser(userName)) {
      return verifyUserPassword(findUser(userName), candidatePassword);
    }
    throw new GNUOpenBusinessServiceException(String.format(
        "Given user [%s] doesn't have the right access, verify that the given user has access (ERR03)", userName));
  }

  @SuppressWarnings(UNCHECKED)
  private boolean containUser(final String user) {
    return userServiceImpl.count((U) User.getInstance(user)) > 0;
  }

  private U verifyUserPassword(final U attachedUser, final String candidatePassword) {
    if (!new SimplePBKDF2().verifyKeyFormatted(attachedUser.getPassword(), candidatePassword)) {
      throw new GNUOpenBusinessServiceException(
          String.format("Given user [%s] doesn't have the right access, verify that the given user has access (ERR02)",
              attachedUser.getName()));
    }
    return attachedUser;
  }

  @SuppressWarnings(UNCHECKED)
  private U findUser(final String userName) {
    return userServiceImpl.find((U) User.getInstance(userName), Paging.getInstance(-1, -1), OrderByEnum.NONE).get(0);
  }

  @SuppressWarnings(UNCHECKED)
  private boolean containSite(final String site) {
    return siteServiceImpl.count((S) Site.getInstance(site)) > 0;
  }

  @SuppressWarnings(UNCHECKED)
  private S findSite(final String site) {
    return siteServiceImpl.find((S) Site.getInstance(site), Paging.getInstance(-1, -1), OrderByEnum.NONE).get(0);
  }
}
