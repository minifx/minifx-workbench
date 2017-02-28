/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.google.common.collect.Iterables;

public class FactoryMethodsCollector implements BeanFactoryAware, BeanPostProcessor, FactoryMethodsRepository {

    private DefaultListableBeanFactory beanFactory;
    private Map<String, Method> beanNamesToFactoryMethod = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

        Method factoryMethod = factoryMethodFor(beanDefinition);
        if (factoryMethod != null) {
            beanNamesToFactoryMethod.put(beanName, factoryMethod);
        }
        return bean;
    }

    private Method factoryMethodFor(BeanDefinition beanDefinition) {
        String factoryBeanName = beanDefinition.getFactoryBeanName();
        String factoryMethodName = beanDefinition.getFactoryMethodName();

        if (factoryBeanName != null && factoryMethodName != null) {
            Object factoryBean = beanFactory.getBean(factoryBeanName);
            List<Method> filteredMethods = stream(factoryBean.getClass().getMethods())
                    .filter(m -> factoryMethodName.equals(m.getName())).collect(toList());
            if (filteredMethods.isEmpty()) {
                throw new IllegalStateException(
                        "No method of name " + factoryMethodName + " found in class " + factoryBean.getClass() + ".");
            }
            return Iterables.getOnlyElement(filteredMethods);
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Optional<Method> factoryMethodForBean(String beanName) {
        return Optional.ofNullable(beanNamesToFactoryMethod.get(beanName));
    }

}
