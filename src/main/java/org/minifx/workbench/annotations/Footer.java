/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation which qualifies a bean as a footer to be used in MiniFx. Footers are always shown at the bottom of an
 * application, outside of all perspectives.If several footers are available, then they are shown in tabs.
 * 
 * @author kfuchsbe
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Footer {

    /**
     * If this property is set to {@code true}, then a tab as shown, even if there is only one footer available.
     * 
     * @return {@code true} if tabs should be enforced, {@code false} otherwise
     */
    boolean enforceTab() default false;

}
