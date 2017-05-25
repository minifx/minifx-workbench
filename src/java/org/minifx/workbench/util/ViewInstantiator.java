/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.Collections.singletonList;
import static java.util.Collections.sort;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.minifx.workbench.domain.PerspectivePos.CENTER;
import static org.minifx.workbench.util.Names.nameFromNameMethod;
import static org.minifx.workbench.util.Perspectives.orderFrom;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.minifx.workbench.annotations.Footer;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.AbstractPerspectiveInstance;
import org.minifx.workbench.domain.BorderLayoutPerspectiveImpl;
import org.minifx.workbench.domain.DefaultPerspective;
import org.minifx.workbench.domain.DisplayProperties;
import org.minifx.workbench.domain.FooterDefinition;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.PerspectiveDefinition;
import org.minifx.workbench.domain.PerspectivePos;
import org.minifx.workbench.domain.TextWorkbenchView;
import org.minifx.workbench.domain.ViewDefinition;
import org.minifx.workbench.spring.WorkbenchElementsRepository;
import org.springframework.core.annotation.Order;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ViewInstantiator {

    private static final Color DEFAULT_COLOR = Color.rgb(2, 2, 2);
    private static final FontAwesomeIcon DEFAULT_PERSPECTIVE_ICON = FontAwesomeIcon.ANGLE_DOWN;
    public static final PerspectivePos DEFAULT_POSITION = CENTER;
    public static final Class<? extends Perspective> DEFAULT_PERSPECTIVE = DefaultPerspective.class;

    private static final String DEFAULT_ICON_SIZE = "1em";
    private static final String PERSPECTIVE_ICON_SIZE = "1.5em";

    private WorkbenchElementsRepository repository;

    public ViewInstantiator(WorkbenchElementsRepository factoryMethodsRepository) {
        this.repository = factoryMethodsRepository;
    }

    public PerspectivePos viewPosFor(Object view) {
        requireNonNull(view, "view must not be null");
        Optional<PerspectivePos> p = repository.from(view).getAnnotation(View.class).map(View::at);
        return p.orElse(DEFAULT_POSITION);
    }

    @VisibleForTesting
    Class<? extends Perspective> perspectiveFor(Object view) {
        requireNonNull(view, "view must not be null");
        Optional<Class<? extends Perspective>> p = repository.from(view).getAnnotation(View.class).map(View::in);
        return p.orElse(DEFAULT_PERSPECTIVE);
    }

    public Optional<FontAwesomeIcon> iconFor(Object view) {
        requireNonNull(view, "view must not be null");
        return iconFrom(viewIconAnnotation(view));
    }

    private static final Optional<FontAwesomeIcon> iconFrom(Optional<Icon> annotation) {
        return annotation.map(Icon::icon);
    }

    private Optional<Icon> viewIconAnnotation(Object view) {
        return repository.from(view).getAnnotation(Icon.class);
    }

    private int perspectiveOrderFrom(Class<? extends Perspective> perspective) {
        return orderFrom(perspective);
    }

    public Optional<FontAwesomeIcon> perspectiveIcon(Class<?> currentPerspective) {
        requireNonNull(currentPerspective, "perspective must not be null");
        return iconFrom(perspectiveIconAnnotation(currentPerspective));
    }

    private Optional<Icon> perspectiveIconAnnotation(Class<?> currentPerspective) {
        return Optional.ofNullable(currentPerspective.getAnnotation(Icon.class));
    }

    // graphics

    private Optional<Node> graphicsForView(Object view) {
        Optional<Icon> annotation = viewIconAnnotation(view);
        return annotation.map(ic -> graphicFrom(ic, DEFAULT_ICON_SIZE));
    }

    private Node perspectiveGraphicsFrom(Class<? extends Perspective> perspective) {
        Optional<Icon> annotation = Optional.ofNullable(perspective.getAnnotation(Icon.class));
        return annotation.map(ic -> graphicFrom(ic, PERSPECTIVE_ICON_SIZE)).orElseGet(this::perspectiveDefaultIcon);
    }

    private Node graphicFrom(Icon iconAnnotation, String size) {
        FontAwesomeIcon icon = iconAnnotation.icon();
        Text nodeIcon = FontAwesomeIconFactory.get().createIcon(icon, size);
        nodeIcon.setFill(color(iconAnnotation));
        return nodeIcon;
    }

    private static Color color(final Icon iconAnnotation) {
        return Color.valueOf(iconAnnotation.color().trim());
    }

    // name

    public String perspectiveNameFrom(Class<?> perspective) {
        return Optional.ofNullable(perspective.getAnnotation(Name.class)).map(Name::value)
                .orElse(perspective.getSimpleName());
    }

    private String viewNameFrom(Object view) {
        Optional<String> nameFromAnnotation = repository.from(view).getAnnotation(Name.class).map(Name::value);
        return Optionals.first(nameFromAnnotation, nameFromNameMethod(view),
                repository.factoryMethodFor(view).map(Method::getName)).orElse(view.getClass().getSimpleName());
    }

    private int viewOrderFrom(Object view) {
        Optional<Order> order = repository.from(view).getAnnotation(Order.class);
        return order.map(Order::value).orElse(LOWEST_PRECEDENCE);
    }

    private boolean alwaysShowTabsFromView(Object view) {
        return repository.from(view).getAnnotation(View.class).map(View::alwaysAsTab).orElse(false);
    }

    private boolean alwaysShowTabsFromFooter(Object footer) {
        return repository.from(footer).getAnnotation(Footer.class).map(Footer::alwaysAsTab).orElse(false);
    }

    // end methods to get information

    private ListMultimap<Class<? extends Perspective>, Object> mapToPerspective(Collection<Object> views2) {
        Builder<Class<? extends Perspective>, Object> perspectiveToViewBuilder = ImmutableListMultimap.builder();
        for (Object view : views2) {
            perspectiveToViewBuilder.put(perspectiveFor(view), view);
        }
        return perspectiveToViewBuilder.build();
    }

    @Deprecated
    private List<AbstractPerspectiveInstance> instantiatePerspectives(
            ListMultimap<Class<? extends Perspective>, Object> perspectiveToViews) {
        List<AbstractPerspectiveInstance> perspectiveToNode = new ArrayList<>();
        for (Class<? extends Perspective> perspective : perspectiveToViews.keySet()) {
            final String name = perspectiveNameFrom(perspective);
            final Node icon = perspectiveGraphicsFrom(perspective);

            List<Object> perspectiveViews = new ArrayList<>(perspectiveToViews.get(perspective));
            sort(perspectiveViews, comparingInt(v -> orderFrom(v.getClass())));
            BorderLayoutPerspectiveImpl instance = new BorderLayoutPerspectiveImpl(name, icon, perspectiveViews,
                    perspectiveOrderFrom(perspective));
            initComponents(instance);
            perspectiveToNode.add(instance);
        }
        sort(perspectiveToNode, comparingInt(AbstractPerspectiveInstance::order));
        return perspectiveToNode;
    }

    @Deprecated
    public List<AbstractPerspectiveInstance> perspectiveInstances() {
        ListMultimap<Class<? extends Perspective>, Object> mapToPerspective = mapToPerspective(repository.views());

        return instantiatePerspectives(mapToPerspective);
    }

    public Set<PerspectiveDefinition> perspectives() {
        ListMultimap<Class<? extends Perspective>, Object> mapToPerspective = mapToPerspective(repository.views());
        return definitionsFrom(mapToPerspective);

    }

    private Set<PerspectiveDefinition> definitionsFrom(
            ListMultimap<Class<? extends Perspective>, Object> mapToPerspective) {
        return mapToPerspective.asMap().entrySet().stream().map(e -> toPerspectiveDefinition(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
    }

    private PerspectiveDefinition toPerspectiveDefinition(Class<? extends Perspective> perspective,
            Collection<Object> allViews) {
        return new PerspectiveDefinition(perspective, perspectiveDisplayProperties(perspective),
                viewDefinitionsFrom(allViews));
    }

    private DisplayProperties perspectiveDisplayProperties(Class<? extends Perspective> perspective) {
        return new DisplayProperties(perspectiveNameFrom(perspective), perspectiveGraphicsFrom(perspective),
                perspectiveOrderFrom(perspective));
    }

    private Set<ViewDefinition> viewDefinitionsFrom(Collection<Object> allViews) {
        return allViews.stream().map(this::toViewDefinition).collect(toImmutableSet());
    }

    public Set<FooterDefinition> footers() {
        return footerDefinitionsFrom(repository.footers());
    }

    private Set<FooterDefinition> footerDefinitionsFrom(Collection<Object> allViews) {
        return allViews.stream().map(this::toFooterDefinition).collect(toImmutableSet());
    }

    private ViewDefinition toViewDefinition(Object view) {
        return new ViewDefinition(MiniFxComponents.fxNodeFrom(view), viewPosFor(view), viewDisplayProperties(view),
                alwaysShowTabsFromView(view));
    }

    private FooterDefinition toFooterDefinition(Object footer) {
        return new FooterDefinition(MiniFxComponents.fxNodeFrom(footer), viewDisplayProperties(footer),
                alwaysShowTabsFromFooter(footer));
    }

    private DisplayProperties viewDisplayProperties(Object view) {
        return new DisplayProperties(viewNameFrom(view), graphicsForView(view).orElse(null), viewOrderFrom(view));
    }

    public List<Object> viewsForPosition(List<Object> views, PerspectivePos position) {
        return views.stream().filter(view -> position.equals(viewPosFor(view))).collect(Collectors.toList());
    }

    @Deprecated
    public Node createContainerPaneFrom(Collection<?> views) {
        if ((views.size() == 1)) {
            Object singleView = Iterables.getOnlyElement(views);
            if (!alwaysShowTabsFromView(singleView)) {
                return MiniFxComponents.fxNodeFrom(singleView);
            }
        }
        return createTabPaneFrom(views);
    }

    @Deprecated
    private TabPane createTabPaneFrom(Collection<?> posViews) {

        List<Node> nodes = posViews.stream().map(MiniFxComponents::fxNodeFrom).collect(toList());

        TabPane tabRoot = new TabPane();
        for (Node node : nodes) {
            Tab tab = new Tab();
            tab.setText(viewNameFrom(node));
            tab.setGraphic(graphicsForView(node).orElse(null));
            tab.setContent(node);
            tab.setClosable(false);
            tabRoot.getTabs().add(tab);
        }
        return tabRoot;
    }

    private Node perspectiveDefaultIcon() {
        Text defaultIcon = FontAwesomeIconFactory.get().createIcon(DEFAULT_PERSPECTIVE_ICON, PERSPECTIVE_ICON_SIZE);
        defaultIcon.setFill(DEFAULT_COLOR);
        return defaultIcon;
    }

    //
    // perspective instantiation
    //

    @Deprecated
    public boolean allViewsBelongToOnePosition(List<Object> views) {
        return views.stream().collect(groupingBy(this::viewPosFor)).values().stream()
                .anyMatch(viewsOfAPosition -> viewsOfAPosition.size() == views.size());
    }

    public Node setupNodeFor(BorderLayoutPerspectiveImpl borderLayoutPerspectiveImpl, PerspectivePos position,
            List<Object> posViews) {
        Node viewPane = createContainerPaneFrom(posViews);
        position.set(viewPane).into(borderLayoutPerspectiveImpl);
        return viewPane;
    }

    public void multipleViewInit(BorderLayoutPerspectiveImpl borderLayoutPerspectiveImpl, List<Object> views) {
        for (PerspectivePos position : PerspectivePos.values()) {
            List<Object> posViews = viewsForPosition(views, position);
            if (!posViews.isEmpty()) {
                Node node = setupNodeFor(borderLayoutPerspectiveImpl, position, posViews);
                MiniFxComponents.configureMultiNodeStyle(node);
            }
        }
    }

    public void singleViewInit(BorderLayoutPerspectiveImpl borderLayoutPerspectiveImpl, List<Object> views) {
        Node node = setupNodeFor(borderLayoutPerspectiveImpl, ViewInstantiator.DEFAULT_POSITION, views);
        MiniFxComponents.configureSingleNodeStyle(node);
    }

    public void initComponents(BorderLayoutPerspectiveImpl borderLayoutPerspectiveImpl) {
        List<Object> views = borderLayoutPerspectiveImpl.views();

        if (views.isEmpty()) {
            singleViewInit(borderLayoutPerspectiveImpl, singletonList(new TextWorkbenchView("No views to display")));
        } else if (allViewsBelongToOnePosition(views)) {
            singleViewInit(borderLayoutPerspectiveImpl, views);
        } else {
            multipleViewInit(borderLayoutPerspectiveImpl, views);
        }
    }

}
