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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.Shown;
import org.minifx.workbench.domain.AbstractPerspectiveInstance;
import org.minifx.workbench.domain.BorderLayoutPerspectiveImpl;
import org.minifx.workbench.domain.DefaultPerspective;
import org.minifx.workbench.domain.Perspective;
import org.minifx.workbench.domain.PerspectivePos;
import org.minifx.workbench.domain.TextWorkbenchView;
import org.minifx.workbench.domain.WorkbenchView;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
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

    public PerspectivePos extractPerspectivePosition(WorkbenchView view) {
        Shown shownAt = view.getClass().getAnnotation(Shown.class);
        if (shownAt == null) {
            return PerspectivePos.CENTER;
        }
        return shownAt.at();
    }

    public Class<? extends Perspective> perspectiveFromObject(WorkbenchView view) {
        requireNonNull(view, "view must not be null");
        return perspectiveOf(view.getClass());
    }

    public Class<? extends Perspective> perspectiveOf(Class<? extends WorkbenchView> viewClass) {
        requireNonNull(viewClass, "viewClass must not be null");
        Shown shownAtAnnotation = viewClass.getAnnotation(Shown.class);
        if (shownAtAnnotation == null) {
            return ViewInstantiator.DEFAULT_PERSPECTIVE;
        }
        return shownAtAnnotation.in();
    }

    public ListMultimap<Class<? extends Perspective>, WorkbenchView> mapToPerspective(
            Collection<WorkbenchView> views2) {
        Builder<Class<? extends Perspective>, WorkbenchView> perspectiveToViewBuilder = ImmutableListMultimap.builder();
        for (WorkbenchView view : views2) {
            perspectiveToViewBuilder.put(perspectiveFromObject(view), view);
        }
        return perspectiveToViewBuilder.build();
    }

    public List<AbstractPerspectiveInstance> instantiatePerspectives(
            ListMultimap<Class<? extends Perspective>, WorkbenchView> perspectiveToViews) {
        List<AbstractPerspectiveInstance> perspectiveToNode = new ArrayList<>();
        for (Class<? extends Perspective> perspective : perspectiveToViews.keySet()) {
            final String name = nameFromClass(perspective);
            final Node icon = ofNullable(graphicFrom(perspective, PERSPECTIVE_BUTTON_ICON_SIZE))
                    .orElseGet(this::perspectiveDefaultIcon);

            BorderLayoutPerspectiveImpl instance = new BorderLayoutPerspectiveImpl(name, icon,
                    perspectiveToViews.get(perspective), orderFrom(perspective));
            initComponents(instance);
            perspectiveToNode.add(instance);
        }
        sort(perspectiveToNode, comparingInt(AbstractPerspectiveInstance::order));
        return perspectiveToNode;
    }

    public Node graphicFrom(Class<?> currentPerspective) {
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

    private boolean alwaysShowTabs(Object view) {
        Shown shownAnnotation = view.getClass().getAnnotation(Shown.class);
        if (shownAnnotation == null) {
            return false;
        }
        return shownAnnotation.alwaysAsTab();
    }

    public List<AbstractPerspectiveInstance> perspectiveInstancesFrom(Collection<WorkbenchView> views) {
        return instantiatePerspectives(mapToPerspective(views));
    }

    public Node viewPaneFrom(List<WorkbenchView> posViews) {
        return createContainerPaneFrom(posViews);
    }

    public List<WorkbenchView> viewsForPosition(List<WorkbenchView> views, PerspectivePos position) {
        return views.stream().filter(view -> position.equals(extractPerspectivePosition(view)))
                .collect(Collectors.toList());
    }

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

    public Node createContainerPaneFrom(List<?> views) {
        if ((views.size() == 1) && (!alwaysShowTabs(views.get(0)))) {
            return MiniFxComponents.fxNodeFrom(views.get(0));
        }
        return createTabPaneFrom(views);
    }

    private TabPane createTabPaneFrom(List<?> posViews) {
        TabPane tabRoot = new TabPane();
        for (Object view : posViews) {
            Tab tab = new Tab();
            tab.setText(nameFrom(view));
            tab.setGraphic(graphicFrom(view.getClass()));
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
        List<WorkbenchView> views = perspective.views();

        if (views.isEmpty()) {
            singleViewInit(perspective, singletonList(new TextWorkbenchView("No views to display")));
        } else if (allViewsBelongToOnePosition(views)) {
            singleViewInit(perspective, views);
        } else {
            multipleViewInit(perspective, views);
        }
    }

    private void singleViewInit(BorderLayoutPerspectiveImpl perspective, List<WorkbenchView> views) {
        setupNodeFor(perspective, CENTER, views).getStyleClass().add(SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS);
    }

    private void multipleViewInit(BorderLayoutPerspectiveImpl perspective, List<WorkbenchView> views) {
        for (PerspectivePos position : PerspectivePos.values()) {
            List<WorkbenchView> posViews = viewsForPosition(views, position);
            if (!posViews.isEmpty()) {
                setupNodeFor(perspective, position, posViews).getStyleClass().add(COMPONENTS_OF_MAIN_PANEL_CLASS);
            }
        }
    }

    private Node setupNodeFor(BorderLayoutPerspectiveImpl perspective, PerspectivePos position,
            List<WorkbenchView> posViews) {
        Node viewPane = viewPaneFrom(posViews);
        position.set(viewPane).into(perspective);
        return viewPane;
    }

    private boolean allViewsBelongToOnePosition(List<WorkbenchView> views) {
        return views.stream().collect(groupingBy(this::extractPerspectivePosition)).values().stream()
                .anyMatch(viewsOfAPosition -> viewsOfAPosition.size() == views.size());
    }

}
