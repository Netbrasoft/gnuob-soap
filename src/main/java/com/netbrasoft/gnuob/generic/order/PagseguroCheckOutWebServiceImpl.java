package com.netbrasoft.gnuob.generic.order;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.netbrasoft.gnuob.monitor.SimonInterceptor;

import com.netbrasoft.gnuob.exception.GNUOpenBusinessServiceException;
import com.netbrasoft.gnuob.generic.contract.Contract;
import com.netbrasoft.gnuob.generic.customer.Customer;
import com.netbrasoft.gnuob.generic.security.MetaData;
import com.netbrasoft.gnuob.generic.security.SecuredGenericTypeService;

@WebService(targetNamespace = "http://gnuob.netbrasoft.com/")
@Stateless(name = "PagseguroCheckOutWebServiceImpl")
@Interceptors(value = { SimonInterceptor.class })
public class PagseguroCheckOutWebServiceImpl<O extends Order> implements CheckOutWebService<O> {

   @EJB(beanName = "PagseguroCheckOutServiceImpl")
   private CheckOutService<O> checkOutService;

   @EJB(beanName = "SecuredGenericTypeServiceImpl")
   private SecuredGenericTypeService<O> securedGenericOrderService;

   @EJB(beanName = "SecuredGenericTypeServiceImpl")
   private SecuredGenericTypeService<Contract> securedGenericContractService;

   @EJB(beanName = "SecuredGenericTypeServiceImpl")
   private SecuredGenericTypeService<Customer> securedGenericCustomerService;

   @Override
   @WebMethod(operationName = "doCheckout")
   public O doCheckout(@WebParam(name = "metaData", header = true) MetaData metadata, @WebParam(name = "order") O order) {
      try {
         checkOutService.doCheckout(order);
         securedGenericCustomerService.update(metadata, order.getContract().getCustomer());
         securedGenericContractService.update(metadata, order.getContract());
         securedGenericOrderService.merge(metadata, order);
         return order;
      } catch (Exception e) {
         throw new GNUOpenBusinessServiceException(e.getMessage(), e);
      }
   }

   @Override
   @WebMethod(operationName = "doCheckoutDetails")
   public O doCheckoutDetails(@WebParam(name = "metaData", header = true) MetaData metadata, @WebParam(name = "order") O order) {
      try {
         checkOutService.doCheckoutDetails(order);
         securedGenericCustomerService.update(metadata, order.getContract().getCustomer());
         securedGenericContractService.update(metadata, order.getContract());
         securedGenericOrderService.merge(metadata, order);
         return order;
      } catch (Exception e) {
         throw new GNUOpenBusinessServiceException(e.getMessage(), e);
      }
   }

   @Override
   @WebMethod(operationName = "doCheckoutPayment")
   public O doCheckoutPayment(@WebParam(name = "metaData", header = true) MetaData metadata, @WebParam(name = "order") O order) {
      try {
         checkOutService.doCheckoutPayment(order);
         securedGenericCustomerService.update(metadata, order.getContract().getCustomer());
         securedGenericContractService.update(metadata, order.getContract());
         securedGenericOrderService.merge(metadata, order);
         return order;
      } catch (Exception e) {
         throw new GNUOpenBusinessServiceException(e.getMessage(), e);
      }
   }
}