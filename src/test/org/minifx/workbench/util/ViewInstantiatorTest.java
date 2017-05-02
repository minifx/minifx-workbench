/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minifx.workbench.domain.PerspectivePos.LEFT;
import static org.minifx.workbench.util.ViewInstantiator.DEFAULT_PERSPECTIVE;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.AbstractFxBorderPaneView;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.WorkbenchView;

public class ViewInstantiatorTest {

    private ViewInstantiator viewInstantiator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        viewInstantiator = new ViewInstantiator(null);
    }

    @Test
    public void perspectiveOfNullClassThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("viewClass ");
        viewInstantiator.perspectiveFromClass((Class<? extends WorkbenchView>) null);
    }

    @Test
    public void perspectiveOfNullInstanceThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("view ");
        viewInstantiator.perspectiveForView((WorkbenchView) null);
    }

    @Test
    public void unannotatedClassRetrievesDefaultPerspective() {
        assertThat(viewInstantiator.perspectiveFromClass(UnannotatedTestView.class)).isEqualTo(DEFAULT_PERSPECTIVE);
    }

    @Test
    public void unannotatedInstanceRetrievesDefaultPerspective() {
        assertThat(viewInstantiator.perspectiveForView(new UnannotatedTestView())).isEqualTo(DEFAULT_PERSPECTIVE);
    }

    @Test
    public void annotatedClassRetrievesCorrectPerspective() {
        assertThat(viewInstantiator.perspectiveFromClass(AnnotatedTestView.class)).isEqualTo(AnyPerspective.class);
    }

    @Test
    public void annotatedInstanceRetrievesCorrectPerspective() {
        assertThat(viewInstantiator.perspectiveForView(new AnnotatedTestView())).isEqualTo(AnyPerspective.class);
    }

    private static class UnannotatedTestView extends AbstractFxBorderPaneView {
        /* nothing here */
    }

    @View(in = AnyPerspective.class, at = LEFT)
    private static class AnnotatedTestView extends AbstractFxBorderPaneView {
        /* nothing here */
    }

    private interface AnyPerspective extends Perspective {
        /* marker */
    }

}
