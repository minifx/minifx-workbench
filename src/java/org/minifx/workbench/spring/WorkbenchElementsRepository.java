/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

import org.minifx.workbench.annotations.Footer;
import org.minifx.workbench.annotations.ToolbarItem;
import org.minifx.workbench.annotations.View;

/**
 * Exposes collected MiniFx elements (usually done by a bean post processor and also some more specific information,
 * like factory methods and annotations found on the bean classes themselves or their factory methods.
 * 
 * @author kfuchsbe
 */
public interface WorkbenchElementsRepository {

    /**
     * Returns all beans which were recognized as MiniFx views within the application context. The returned beans are
     * exactly those as found in the application context. No processing is done at this stage.
     * 
     * @return a set of all views
     * @see View
     */
    Set<Object> views();

    /**
     * Returns all beans which were recognized as MiniFx toolbar items within the application context. The returned
     * beans are exactly those as found in the application context. No processing is done at this stage.
     * 
     * @return a set of all toolbar items
     * @see ToolbarItem
     */
    Set<Object> toolbarItems();

    /**
     * Returns all beans which were recognized as MiniFx footers within the application context. The returned beans are
     * exactly those as found in the application context. No processing is done at this stage.
     * 
     * @return a set of all footers
     * @see Footer
     */
    Set<Object> footers();

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
     * <p>
     * <code><pre>
     *     Optional<AnAnnotation> annotation = from(aBean).getAnnotation(AnAnnotation.class);
     * </pre> </code>
     * <p>
     * Details on the strategy to find the annotations, see {@link OngoingAnnotationExtraction}.
     * 
     * @param bean the bean on which to find annotations
     * @return an object to specify the annotations to find
     * @see OngoingAnnotationExtraction
     */
    OngoingAnnotationExtraction from(Object bean);

}
