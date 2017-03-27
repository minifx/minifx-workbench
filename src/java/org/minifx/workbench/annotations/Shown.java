/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.annotations;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.PerspectivePos;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Shown {

    Class<? extends Perspective> in();

    PerspectivePos at() default CENTER;
    
    boolean alwaysAsTab() default false;
}
