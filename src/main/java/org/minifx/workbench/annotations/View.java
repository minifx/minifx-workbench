/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.annotations;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.minifx.workbench.domain.DefaultPerspective;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.PerspectivePos;

/**
 * Annotation to qualify a bean as a view within MiniFx. This annotation can be placed on the class of the bean or on
 * the factory method of it.
 * 
 * @author kfuchsbe
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface View {

    /**
     * This property specifies the class representing the perspective in which the view shall be shown.
     * 
     * @return the class representing the perspective in which to show the annotated view.
     */
    Class<? extends Perspective>in() default DefaultPerspective.class;

    /**
     * This property specifies the position within the given perspective, where the view shall be shown
     * 
     * @return the position within the perspective where to show the annotated view.
     */
    PerspectivePos at() default CENTER;

    /**
     * If this property is set to {@code true}, then a tab as shown, even if there is only one view specified for the
     * given position in the given perspective.
     * 
     * @return {@code true} if tabs should be enforced, {@code false} otherwise
     */
    boolean enforceTab() default false;
}
