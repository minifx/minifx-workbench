/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minifx.workbench.domain.PerspectivePos.LEFT;
import static org.minifx.workbench.util.Views.DEFAULT_PERSPECTIVE;
import static org.minifx.workbench.util.Views.perspectiveFromObject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.minifx.workbench.annotations.Shown;
import org.minifx.workbench.domain.AbstractFxBorderPaneView;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.WorkbenchView;

public class ViewsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void perspectiveOfNullClassThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("viewClass ");
        Views.perspectiveOf((Class<? extends WorkbenchView>) null);
    }

    @Test
    public void perspectiveOfNullInstanceThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("view ");
        Views.perspectiveFromObject((WorkbenchView) null);
    }

    @Test
    public void unannotatedClassRetrievesDefaultPerspective() {
        assertThat(Views.perspectiveOf(UnannotatedTestView.class)).isEqualTo(DEFAULT_PERSPECTIVE);
    }

    @Test
    public void unannotatedInstanceRetrievesDefaultPerspective() {
        assertThat(perspectiveFromObject(new UnannotatedTestView())).isEqualTo(DEFAULT_PERSPECTIVE);
    }

    @Test
    public void annotatedClassRetrievesCorrectPerspective() {
        assertThat(Views.perspectiveOf(AnnotatedTestView.class)).isEqualTo(AnyPerspective.class);
    }

    @Test
    public void annotatedInstanceRetrievesCorrectPerspective() {
        assertThat(perspectiveFromObject(new AnnotatedTestView())).isEqualTo(AnyPerspective.class);
    }

    private static class UnannotatedTestView extends AbstractFxBorderPaneView {
        /* nothing here */
    }

    @Shown(in = AnyPerspective.class, at = LEFT)
    private static class AnnotatedTestView extends AbstractFxBorderPaneView {
        /* nothing here */
    }

    private interface AnyPerspective extends Perspective {
        /* marker */
    }

}
