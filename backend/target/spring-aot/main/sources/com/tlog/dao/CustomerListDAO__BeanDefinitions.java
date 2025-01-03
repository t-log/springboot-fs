package com.tlog.dao;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomerListDAO}.
 */
@Generated
public class CustomerListDAO__BeanDefinitions {
  /**
   * Get the bean definition for 'list'.
   */
  public static BeanDefinition getListBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CustomerListDAO.class);
    beanDefinition.setInstanceSupplier(CustomerListDAO::new);
    return beanDefinition;
  }
}
