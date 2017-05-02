/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static java.util.Collections.singletonList;
import static java.util.Collections.sort;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static org.minifx.workbench.css.MiniFxCssConstants.COMPONENTS_OF_MAIN_PANEL_CLASS;
import static org.minifx.workbench.css.MiniFxCssConstants.SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS;
import static org.minifx.workbench.domain.PerspectivePos.CENTER;
import static org.minifx.workbench.util.Perspectives.orderFrom;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.AbstractPerspectiveInstance;
import org.minifx.workbench.domain.BorderLayoutPerspectiveImpl;
import org.minifx.workbench.domain.DefaultPerspective;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.PerspectivePos;
import org.minifx.workbench.domain.TextWorkbenchView;
import org.minifx.workbench.spring.WorkbenchElementsRepository;

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

    public static final Class<? extends Perspective> DEFAULT_PERSPECTIVE = DefaultPerspective.class;
    private static final String DEFAULT_ICON_SIZE = "1em";
    private static final String PERSPECTIVE_BUTTON_ICON_SIZE = "1.5em";

    private WorkbenchElementsRepository factoryMethodsRepository;

    public ViewInstantiator(WorkbenchElementsRepository factoryMethodsRepository) {
        this.factoryMethodsRepository = factoryMethodsRepository;
    }

    // perspectivePos

    public PerspectivePos perspectivePosForView(Object view) {
        requireNonNull(view, "view must not be null");
        Optional<PerspectivePos> pos = perspectivePosFromFactoryMethod(view);
        if (pos.isPresent()) {
            return pos.get();
        }
        Class<?> viewClass = view.getClass();
        return perspectivePositionFromClass(viewClass);
    }

    private Optional<PerspectivePos> perspectivePosFromFactoryMethod(Object view) {
        Optional<View> viewAnnotation = viewAnnotationFrom(view);

        Optional<Method> method = factoryMethodsRepository.factoryMethodForBean(view);

        if (method.isPresent()) {
            System.out.println(view + "; " + method.get() + "; " + Arrays.toString(method.get().getAnnotations()));
            View shownAt = method.get().getAnnotation(View.class);
            if (shownAt != null) {
                return Optional.ofNullable(shownAt.at());
            }
        }
        return Optional.empty();
    }

    private Optional<View> viewAnnotationFrom(Object view) {
        return factoryMethodsRepository.from(view).getAnnotation(View.class);
    }

    private PerspectivePos perspectivePositionFromClass(Class<?> viewClass) {
        View shownAt = viewClass.getAnnotation(View.class);
        if (shownAt == null) {
            return PerspectivePos.CENTER;
        }
        return shownAt.at();
    }

    // perspective

    public Class<? extends Perspective> perspectiveForView(Object view) {
        requireNonNull(view, "view must not be null");
        return perspectiveFromClass(view.getClass());
    }

    public Class<? extends Perspective> perspectiveFromClass(Class<?> viewClass) {
        requireNonNull(viewClass, "viewClass must not be null");
        View shownAtAnnotation = viewClass.getAnnotation(View.class);
        if (shownAtAnnotation == null) {
            return ViewInstantiator.DEFAULT_PERSPECTIVE;
        }
        return shownAtAnnotation.in();
    }

    // graphics

    private Node graphicsForView(Object view) {
        return graphicFromClass(view.getClass());
    }

    public Node graphicFromClass(Class<?> currentPerspective) {
        return graphicFrom(currentPerspective, DEFAULT_ICON_SIZE);
    }

    public Node graphicFrom(Class<?> currentPerspective, String size) {
        final Icon iconAnnotation = currentPerspective.getAnnotation(Icon.class);
        if (iconAnnotation == null) {
            return null;
        }
        final Text nodeIcon = FontAwesomeIconFactory.get().createIcon(iconAnnotation.icon(), size);
        final Color color = Color.valueOf(iconAnnotation.color().trim());
        nodeIcon.setFill(color);
        return nodeIcon;
    }

    // name

    private String nameFromClass(Class<?> viewClass) {
        Name name = viewClass.getAnnotation(Name.class);
        if (name != null) {
            return name.value();
        }

        return viewClass.getSimpleName();
    }

    private String nameFrom(Object view) {
        return Names.nameFromNameMethod(view).orElse(nameFromClass(view.getClass()));
    }

    // always show tabs?

    private boolean alwaysShowTabsFromView(Object view) {
        return alwaysShowTabsFromClass(view.getClass());
    }

    private boolean alwaysShowTabsFromClass(Class<? extends Object> class1) {
        View shownAnnotation = class1.getAnnotation(View.class);
        if (shownAnnotation == null) {
            return false;
        }
        return shownAnnotation.alwaysAsTab();
    }

    // end methods to get information

    public ListMultimap<Class<? extends Perspective>, Object> mapToPerspective(Collection<Object> views2) {
        Builder<Class<? extends Perspective>, Object> perspectiveToViewBuilder = ImmutableListMultimap.builder();
        for (Object view : views2) {
            perspectiveToViewBuilder.put(perspectiveForView(view), view);
        }
        return perspectiveToViewBuilder.build();
    }

    public List<AbstractPerspectiveInstance> instantiatePerspectives(
            ListMultimap<Class<? extends Perspective>, Object> perspectiveToViews) {
        List<AbstractPerspectiveInstance> perspectiveToNode = new ArrayList<>();
        for (Class<? extends Perspective> perspective : perspectiveToViews.keySet()) {
            final String name = nameFromClass(perspective);
            final Node icon = ofNullable(graphicFrom(perspective, PERSPECTIVE_BUTTON_ICON_SIZE))
                    .orElseGet(this::perspectiveDefaultIcon);

            List<Object> perspectiveViews = new ArrayList<>(perspectiveToViews.get(perspective));
            sort(perspectiveViews, comparingInt(v -> orderFrom(v.getClass())));
            BorderLayoutPerspectiveImpl instance = new BorderLayoutPerspectiveImpl(name, icon, perspectiveViews,
                    orderFrom(perspective));
            initComponents(instance);
            perspectiveToNode.add(instance);
        }
        sort(perspectiveToNode, comparingInt(AbstractPerspectiveInstance::order));
        return perspectiveToNode;
    }

    public List<AbstractPerspectiveInstance> perspectiveInstances() {
        return instantiatePerspectives(mapToPerspective(factoryMethodsRepository.views()));
    }

    public Node viewPaneFrom(List<Object> posViews) {
        return createContainerPaneFrom(posViews);
    }

    public List<Object> viewsForPosition(List<Object> views, PerspectivePos position) {
        return views.stream().filter(view -> position.equals(perspectivePosForView(view))).collect(Collectors.toList());
    }

    public Node createContainerPaneFrom(Collection<?> views) {
        if ((views.size() == 1)) {
            Object singleView = Iterables.getOnlyElement(views);
            if (!alwaysShowTabsFromView(singleView)) {
                return MiniFxComponents.fxNodeFrom(singleView);
            }
        }
        return createTabPaneFrom(views);
    }

    private TabPane createTabPaneFrom(Collection<?> posViews) {
        TabPane tabRoot = new TabPane();
        for (Object view : posViews) {
            Tab tab = new Tab();
            tab.setText(nameFrom(view));
            tab.setGraphic(graphicsForView(view));
            tab.setContent(MiniFxComponents.fxNodeFrom(view));
            tab.setClosable(false);

            tabRoot.getTabs().add(tab);
        }
        return tabRoot;
    }

    private Node perspectiveDefaultIcon() {
        Text defaultIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.ANGLE_DOWN,
                PERSPECTIVE_BUTTON_ICON_SIZE);
        defaultIcon.setFill(Color.rgb(2, 2, 2));
        return defaultIcon;
    }

    //
    // perspective instantiation
    //

    private void initComponents(BorderLayoutPerspectiveImpl perspective) {
        List<Object> views = perspective.views();

        if (views.isEmpty()) {
            singleViewInit(perspective, singletonList(new TextWorkbenchView("No views to display")));
        } else if (allViewsBelongToOnePosition(views)) {
            singleViewInit(perspective, views);
        } else {
            multipleViewInit(perspective, views);
        }
    }

    private void singleViewInit(BorderLayoutPerspectiveImpl perspective, List<Object> views) {
        setupNodeFor(perspective, CENTER, views).getStyleClass().add(SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS);
    }

    private void multipleViewInit(BorderLayoutPerspectiveImpl perspective, List<Object> views) {
        for (PerspectivePos position : PerspectivePos.values()) {
            List<Object> posViews = viewsForPosition(views, position);
            if (!posViews.isEmpty()) {
                setupNodeFor(perspective, position, posViews).getStyleClass().add(COMPONENTS_OF_MAIN_PANEL_CLASS);
            }
        }
    }

    private Node setupNodeFor(BorderLayoutPerspectiveImpl perspective, PerspectivePos position, List<Object> posViews) {
        Node viewPane = viewPaneFrom(posViews);
        position.set(viewPane).into(perspective);
        return viewPane;
    }

    private boolean allViewsBelongToOnePosition(List<Object> views) {
        return views.stream().collect(groupingBy(this::perspectivePosForView)).values().stream()
                .anyMatch(viewsOfAPosition -> viewsOfAPosition.size() == views.size());
    }

}
