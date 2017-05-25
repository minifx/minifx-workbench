/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import static java.util.Collections.newSetFromMap;
import static java.util.stream.Collectors.toList;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.minifx.workbench.annotations.Footer;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.ToolbarItem;
import org.minifx.workbench.domain.WorkbenchFooter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

@Component
public class WorkbenchElementsCollector implements BeanFactoryAware, BeanPostProcessor, WorkbenchElementsRepository {

    private DefaultListableBeanFactory beanFactory;
    private Map<Object, Method> beansToFactoryMethod = new ConcurrentHashMap<>();

    private Set<Object> views = newSetFromMap(new ConcurrentHashMap<>());
    private Set<Object> footers = newSetFromMap(new ConcurrentHashMap<>());
    private Set<Object> toolbarItems = newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Method factoryMethod = factoryMethodForBeanName(beanName);
        if (factoryMethod != null) {
            beansToFactoryMethod.put(bean, factoryMethod);
        }
        return bean;
    }

    private Method factoryMethodForBeanName(String beanName) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
        String factoryBeanName = beanDefinition.getFactoryBeanName();
        String factoryMethodName = beanDefinition.getFactoryMethodName();

        if (factoryBeanName != null && factoryMethodName != null) {
            Object factoryBean = beanFactory.getBean(factoryBeanName);

            Class<? extends Object> factoryClass = factoryBean.getClass();

            if (Proxy.isProxyClass(factoryClass) || Enhancer.isEnhanced(factoryClass)) {
                factoryClass = factoryClass.getSuperclass();
            }

            Set<Method> methods = new HashSet<>(Arrays.asList(factoryClass.getMethods()));
            methods.addAll(Arrays.asList(factoryClass.getDeclaredMethods()));

            List<Method> filteredMethods = methods.stream().filter(m -> factoryMethodName.equals(m.getName()))
                    .collect(toList());
            if (filteredMethods.isEmpty()) {
                throw new IllegalStateException(
                        "No method of name " + factoryMethodName + " found in class " + factoryClass + ".");
            }
            return Iterables.getOnlyElement(filteredMethods);
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (isView(bean)) {
            views.add(bean);
        } else if (isToolbarItem(bean)) {
            toolbarItems.add(bean);
        } else if (isFooter(bean)) {
            footers.add(bean);
        }
        return bean;
    }

    private boolean isFooter(Object bean) {
        if (bean instanceof WorkbenchFooter) {
            return true;
        }
        return from(bean).getAnnotation(Footer.class).isPresent();
    }

    private boolean isToolbarItem(Object bean) {
        if (bean instanceof ToolbarItem) {
            return true;
        }
        return from(bean).getAnnotation(org.minifx.workbench.annotations.ToolbarItem.class).isPresent();
    }

    private boolean isView(Object bean) {
        return from(bean).getAnnotation(View.class).isPresent();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Optional<Method> factoryMethodFor(Object bean) {
        return Optional.ofNullable(beansToFactoryMethod.get(bean));
    }

    @Override
    public OngoingAnnotationExtraction from(Object object) {
        return OngoingAnnotationExtraction.from(beansToFactoryMethod.get(object), object);
    }

    @Override
    public Set<Object> views() {
        return ImmutableSet.copyOf(this.views);
    }

    @Override
    public Set<Object> toolbarItems() {
        return ImmutableSet.copyOf(this.toolbarItems);
    }

    @Override
    public Set<Object> footers() {
        return ImmutableSet.copyOf(this.footers);
    }

}
