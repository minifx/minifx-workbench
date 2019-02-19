/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import com.google.common.collect.ListMultimap;
import org.minifx.workbench.annotations.Footer;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.PerspectivePos;
import org.minifx.workbench.domain.definition.DisplayProperties;
import org.minifx.workbench.domain.definition.FooterDefinition;
import org.minifx.workbench.domain.definition.PerspectiveDefinition;
import org.minifx.workbench.domain.definition.ViewDefinition;
import org.minifx.workbench.nodes.FxNodeFactory;
import org.minifx.workbench.util.Names;
import org.minifx.workbench.util.Perspectives;
import org.minifx.workbench.util.Purpose;
import org.springframework.core.annotation.Order;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

/**
 * Contains all the relevant logic to bring together all the information which is required to instantiate the workbench
 * elements later. In particular, this is:
 * <ul>
 * <li>The relation between views and perspectives (and the view position inside the perspectives)
 * <li>The names of views, perspectives and footers
 * <li>Icons of views perspectives and footers
 * </ul>
 * As a final result, this class constructs perspective definitions (see {@link #perspectives()} (which contain view
 * definitions) and footer definitions (see {@link #footers()}). All of these can be used to instantiate the
 * perspectives (and views) and footers.
 * <p>
 * This class uses a {@link WorkbenchElementsRepository} to get the collected components as well as to access the
 * factory methods and annotations of the collected elements.
 * <p>
 * For deriving information, usually annotations are used. Those annotations are searched in the order as described in
 * {@link BeanInformationRepository#from(Object)}. Based on this, the following strategies are applied in particular
 * cases:
 * <ul>
 * <li><b>Views:</b> Each bean for which a {@link View} annotation is found, is considered as view in MiniFx. The view
 * annotation describes the perspective in which the view is displayed and the position within the view. Both values are
 * optional. If omitted, the view is displayed in the perspective {@link Perspectives#DEFAULT_PERSPECTIVE} at the
 * position {@link Perspectives#DEFAULT_POSITION}.
 * <li><b>Icons:</b> They are derived from the {@link Icon} annotation (both for views and perspectives). If none is
 * specified, then perspectives will get a default icon and views will have no icon.
 * <li><b>Names:</b> Names for views are derived in the following order:
 * <ol>
 * <li>If annotated by {@link Name}, the value from there is used.
 * <li>If possible, a name from a name method of the bean is used (see {@link Names#nameFromNameMethod(Object)}.
 * <li>The bean name from the spring context
 * <li>If none of the above is found, the simple class name of the view is used.
 * </ol>
 * <li><b>Order:</b> if an {@link Order} annotation is specified for a view (or a perspective) then it is taken into
 * account.
 * </ul>
 *
 * @author kfuchsbe
 */
public class ElementsDefinitionConstructor {

    private final WorkbenchElementsRepository repository;
    private final BeanInformationExtractor extractor;
    private final FxNodeFactory fxNodeFactory;

    /**
     * Constructor which requires an elements repository, which will be used to look up the collected elements as well
     * as annotations on them and factory methods.
     *
     * @param elementsRepository the repository to use
     * @param beanInformationExtractor the instance who knows about the information of the beans
     * @param fxNodeFactory the factory to create java fx nodes
     * @throws NullPointerException if the given repository is {@code null}
     */
    public ElementsDefinitionConstructor(WorkbenchElementsRepository elementsRepository,
            BeanInformationExtractor beanInformationExtractor, FxNodeFactory fxNodeFactory) {
        this.repository = requireNonNull(elementsRepository, "elementsRepository must not be null");
        this.extractor = requireNonNull(beanInformationExtractor, "beanInformationExtractor must not be null");
        this.fxNodeFactory = requireNonNull(fxNodeFactory, "fxNodeFactory must not be null");
    }

    /**
     * Returns a perspective definition for each perspective used by at least one of the views found in the repository.
     *
     * @return all perspective definitions as derived from the views in the repository
     */
    public Set<PerspectiveDefinition> perspectives() {
        return definitionsFrom(mapToPerspective(repository.views()));
    }

    /**
     * Returns a footer definition for each footer found in the repository
     *
     * @return all footer definitions
     */
    public Set<FooterDefinition> footers() {
        return footerDefinitionsFrom(repository.footers());
    }

    @VisibleForTesting
    PerspectivePos viewPosFor(Object view) {
        requireNonNull(view, "view must not be null");
        return viewAnnotation(view).map(View::at).orElse(Perspectives.DEFAULT_POSITION);
    }

    @VisibleForTesting
    Class<? extends Perspective> perspectiveFor(Object view) {
        requireNonNull(view, "view must not be null");
        Optional<Class<? extends Perspective>> p = viewAnnotation(view).map(View::in);
        return p.orElse(Perspectives.DEFAULT_PERSPECTIVE);
    }

    private Optional<View> viewAnnotation(Object view) {
        return repository.from(view).getAnnotation(View.class);
    }

    private boolean alwaysShowTabsFromView(Object view) {
        return viewAnnotation(view).map(View::enforceTab).orElse(false);
    }

    private boolean alwaysShowTabsFromFooter(Object footer) {
        return footerAnnotation(footer).map(Footer::enforceTab).orElse(false);
    }

    private Optional<Footer> footerAnnotation(Object footer) {
        return repository.from(footer).getAnnotation(Footer.class);
    }

    private ListMultimap<Class<? extends Perspective>, Object> mapToPerspective(Collection<Object> views) {
        Builder<Class<? extends Perspective>, Object> perspectiveToViewBuilder = ImmutableListMultimap.builder();
        for (Object view : views) {
            perspectiveToViewBuilder.put(perspectiveFor(view), view);
        }
        return perspectiveToViewBuilder.build();
    }

    private Set<PerspectiveDefinition> definitionsFrom(
            ListMultimap<Class<? extends Perspective>, Object> mapToPerspective) {
        return mapToPerspective.asMap().entrySet().stream().map(e -> toPerspectiveDefinition(e.getKey(), e.getValue()))
                .collect(toSet());
    }

    private PerspectiveDefinition toPerspectiveDefinition(Class<? extends Perspective> perspective,
            Collection<Object> allViews) {
        return new PerspectiveDefinition(perspective, Perspectives.perspectiveDisplayProperties(perspective),
                viewDefinitionsFrom(allViews));
    }

    private Set<ViewDefinition> viewDefinitionsFrom(Collection<Object> allViews) {
        return allViews.stream().map(this::toViewDefinition).collect(toSet());
    }

    private Set<FooterDefinition> footerDefinitionsFrom(Collection<Object> allViews) {
        return allViews.stream().map(this::toFooterDefinition).collect(toSet());
    }

    private ViewDefinition toViewDefinition(Object view) {
        return new ViewDefinition(fxNodeFactory.fxNodeFrom(view), viewPosFor(view),
                displayPropertiesFrom(view, Purpose.VIEW), alwaysShowTabsFromView(view));
    }

    private FooterDefinition toFooterDefinition(Object footer) {
        return new FooterDefinition(fxNodeFactory.fxNodeFrom(footer), displayPropertiesFrom(footer, Purpose.VIEW),
                alwaysShowTabsFromFooter(footer));
    }

    public DisplayProperties displayPropertiesFrom(Object view, Purpose purpose) {
        return this.extractor.displayPropertiesFrom(view, purpose);
    }

}
