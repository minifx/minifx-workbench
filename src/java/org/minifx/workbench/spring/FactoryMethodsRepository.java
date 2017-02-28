/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import java.lang.reflect.Method;
import java.util.Optional;

public interface FactoryMethodsRepository {

    Optional<Method> factoryMethodForBean(String beanName);

}
