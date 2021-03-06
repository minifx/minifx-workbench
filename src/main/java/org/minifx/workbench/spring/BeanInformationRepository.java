/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.lang.reflect.Method;
import java.util.Optional;

public interface BeanInformationRepository {

    /**
     * Retrieves the (cached) bean name (as used in the application context) for the given bean.
     *
     * @param bean the bean for which to get the name
     * @return the name of the bean if known
     */
    Optional<String> beanNameFor(Object bean);

    /**
     * Retrieves the (cached) factory method of the given bean if available.
     *
     * @param bean the bean for which to retrieve the factory method.
     * @return the factory method of the bean (if it has one)
     */
    Optional<Method> factoryMethodFor(Object bean);

    /**
     * Starting point of a fluent clause to find annotations of a given type from the bean. The full syntax is like
     * this:
     * <pre>
     *      Optional&#60;AnAnnotation&#62; annotation = from(aBean).getAnnotation(AnAnnotation.class);
     * </pre>
     * Details on the strategy to find the annotations, see {@link OngoingAnnotationExtraction}.
     *
     * @param bean the bean on which to find annotations
     * @return an object to specify the annotations to find
     * @see OngoingAnnotationExtraction
     */
    OngoingAnnotationExtraction from(Object bean);

}