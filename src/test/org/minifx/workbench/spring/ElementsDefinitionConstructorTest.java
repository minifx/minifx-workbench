/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.minifx.workbench.domain.PerspectivePos.LEFT;
import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.conf.fullyconfigured.AbstractFxBorderPaneView;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.util.Perspectives;
import org.mockito.Mockito;

public class ElementsDefinitionConstructorTest {

    private ElementsDefinitionConstructor viewInstantiator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        WorkbenchElementsRepository repository = Mockito.mock(WorkbenchElementsRepository.class);
        Mockito.when(repository.from(any()))
                .then(invoc -> new OngoingAnnotationExtraction(null, invoc.getArguments()[0]));
        BeanInformationExtractor extractor = Mockito.mock(BeanInformationExtractor.class);
        viewInstantiator = new ElementsDefinitionConstructor(repository, extractor);
    }

    @Test
    public void perspectiveOfNullInstanceThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("view ");
        viewInstantiator.perspectiveFor(null);
    }

    @Test
    public void unannotatedInstanceRetrievesDefaultPerspective() {
        assertThat(viewInstantiator.perspectiveFor(new UnannotatedTestView()))
                .isEqualTo(Perspectives.DEFAULT_PERSPECTIVE);
    }

    @Test
    public void annotatedInstanceRetrievesCorrectPerspective() {
        assertThat(viewInstantiator.perspectiveFor(new AnnotatedTestView())).isEqualTo(AnyPerspective.class);
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
