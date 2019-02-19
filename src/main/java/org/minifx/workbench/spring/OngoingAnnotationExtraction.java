/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

import org.springframework.core.annotation.AnnotationUtils;

/**
 * Part of a fluent clause to extract annotation information from beans.
 * 
 * @author kfuchsbe
 * @see #getAnnotation(Class)
 */
public class OngoingAnnotationExtraction {

    private final Method factoryMethod;
    private final Object bean;

    /**
     * Constructor which takes and optional factory method and a bean, for which annotations can be found later by
     * calling {@link #getAnnotation(Class)}.
     * 
     * @param factoryMethod the factory for the bean. This is optional and thus is allowed to be {@code null}
     * @param bean the bean for which annotations shall be extracted
     */
    OngoingAnnotationExtraction(Method factoryMethod, Object bean) {
        this.factoryMethod = factoryMethod;
        this.bean = Objects.requireNonNull(bean, "bean must not be null");
    }

    /**
     * Tries to find the given annotation for the bean in question. The strategy (and order) in which the annotation is
     * searched is the following:
     * <ol>
     * <li>If a factory method is available for the bean, then the annotation is searched on this factory method. The
     * usual spring annotation utils are used for this, so also the methods of super types are searched.
     * <li>If not factory method is available, or if the annotation could not be found on it, then the annotation is
     * searched on the class of the bean. Also here, springs annotation utils are used, so the annotation is also
     * searched on super types.
     * </ol>
     * 
     * @param annotationClass the type of the annotation to be found
     * @param <T> the type of the annotation to be searched
     * @return the annotation, if found.
     * @see AnnotationUtils#findAnnotation(Class, Class)
     * @see AnnotationUtils#findAnnotation(Method, Class)
     */
    public <T extends Annotation> Optional<T> getAnnotation(Class<T> annotationClass) {
        Optional<T> annotation = getFromFactoryMethod(annotationClass);
        if (annotation.isPresent()) {
            return annotation;
        }
        return Optional.ofNullable(AnnotationUtils.findAnnotation(bean.getClass(), annotationClass));
    }

    
    private <T extends Annotation> Optional<T> getFromFactoryMethod(Class<T> annotationClass) {
        if (factoryMethod != null) {
            return Optional.ofNullable(AnnotationUtils.findAnnotation(factoryMethod, annotationClass));
        }
        return Optional.empty();
    }

}
