/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

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
public interface WorkbenchElementsRepository extends BeanInformationRepository {

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
     * Returns all beans which were recognized as MiniFx perspectives within the application context. The returned beans
     * are exactly those objects as found in the context. No processing is done at this stage yet.
     *
     * @return all the beans that are identified as perspectives.
     */
    Set<Object> perspectives();

}
