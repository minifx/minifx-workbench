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
import org.minifx.workbench.annotations.ToolbarItem;
import org.minifx.workbench.annotations.View;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * A Spring bean post processor which has two main responsibilities:
 * <ol>
 * <li>It collects factory methods of all beans created in the application context. This allows to get the name check
 * for annotations on both, the factory methods and the bean classes themselves.
 * <li>It collects all beans which are recognized as minifx elements (by corresponding annotations). These are: views
 * (see {@link View}), footers (see {@link Footer}) and toolbar items (see {@link ToolbarItem}).
 * </ol>
 * The collected information is exposed through the {@link WorkbenchElementsRepository} and
 * {@link BeanInformationRepository} interfaces for further usage.
 *
 * @author kfuchsbe
 */
@Component
public class WorkbenchElementsPostProcessor
        implements BeanFactoryAware, BeanPostProcessor, WorkbenchElementsRepository, BeanInformationRepository {

    private DefaultListableBeanFactory beanFactory;
    private Map<Object, Method> beansToFactoryMethod = new ConcurrentHashMap<>();
    private Map<Object, String> beansToBeanNames = new ConcurrentHashMap<>();

    private Set<Object> views = newSetFromMap(new ConcurrentHashMap<>());
    private Set<Object> footers = newSetFromMap(new ConcurrentHashMap<>());
    private Set<Object> toolbarItems = newSetFromMap(new ConcurrentHashMap<>());
    private Set<Object> perspectives = newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        beansToBeanNames.put(bean, beanName);
        Method factoryMethod = factoryMethodForBeanName(beanName);
        if (factoryMethod != null) {
            beansToFactoryMethod.put(bean, factoryMethod);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (isView(bean)) {
            views.add(bean);
        } else if (isToolbarItem(bean)) {
            toolbarItems.add(bean);
        } else if (isFooter(bean)) {
            footers.add(bean);
            // } else if (isPerspective(bean)) {
            // perspectives.add(bean);
        }
        return bean;
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
    public Optional<String> beanNameFor(Object bean) {
        return Optional.ofNullable(beansToBeanNames.get(bean));
    }

    @Override
    public OngoingAnnotationExtraction from(Object object) {
        return new OngoingAnnotationExtraction(beansToFactoryMethod.get(object), object);
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

    @Override
    public Set<Object> perspectives() {
        return ImmutableSet.copyOf(this.perspectives);
    }

    private boolean isFooter(Object bean) {
        return from(bean).getAnnotation(Footer.class).isPresent();
    }

    private boolean isToolbarItem(Object bean) {
        return from(bean).getAnnotation(ToolbarItem.class).isPresent();
    }

    private boolean isView(Object bean) {
        return from(bean).getAnnotation(View.class).isPresent();
    }

    private Method factoryMethodForBeanName(String beanName) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
        String factoryBeanName = beanDefinition.getFactoryBeanName();
        String factoryMethodName = beanDefinition.getFactoryMethodName();
        if ((factoryBeanName != null) && (factoryMethodName != null)) {
            List<Method> filteredMethods = methodsOfName(factoryBeanClass(factoryBeanName), factoryMethodName);
            if (filteredMethods.isEmpty()) {
                throw new IllegalStateException("No method of name " + factoryMethodName + " found in class "
                        + factoryBeanClass(factoryBeanName) + ".");
            }
            return Iterables.getOnlyElement(filteredMethods);
        }
        return null;
    }

    private List<Method> methodsOfName(Class<? extends Object> factoryClass, String factoryMethodName) {
        Set<Method> methods = new HashSet<>(Arrays.asList(factoryClass.getMethods()));
        methods.addAll(Arrays.asList(factoryClass.getDeclaredMethods()));
        return methods.stream().filter(m -> factoryMethodName.equals(m.getName())).collect(toList());
    }

    private Class<? extends Object> factoryBeanClass(String factoryBeanName) {
        Object factoryBean = beanFactory.getBean(factoryBeanName);
        Class<? extends Object> factoryClass = factoryBean.getClass();
        if (Proxy.isProxyClass(factoryClass) || Enhancer.isEnhanced(factoryClass)) {
            factoryClass = factoryClass.getSuperclass();
        }
        return factoryClass;
    }

}
