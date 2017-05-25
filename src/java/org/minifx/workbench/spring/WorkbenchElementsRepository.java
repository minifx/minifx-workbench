/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

public interface WorkbenchElementsRepository {

    Optional<Method> factoryMethodFor(Object bean);

    OngoingAnnotationExtraction from(Object object);
    
    Set<Object> views();
    
    Set<Object> toolbarItems();
    
    Set<Object> footers();

}
