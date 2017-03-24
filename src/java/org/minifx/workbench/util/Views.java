/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static java.util.Collections.sort;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
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

public class Views {

    public static final Class<? extends Perspective> DEFAULT_PERSPECTIVE = DefaultPerspective.class;
    private static final String DEFAULT_ICON_SIZE = "1em";
    private static final String PERSPECTIVE_BUTTON_ICON_SIZE = "1.5em";

    private Views() {
        /* Only static methods */
    }

    public static final PerspectivePos extractPerspectivePosition(WorkbenchView view) {
        Shown shownAt = view.getClass().getAnnotation(Shown.class);
        if (shownAt == null) {
            return PerspectivePos.CENTER;
        }
        return shownAt.at();
    }

    public static final Class<? extends Perspective> perspectiveFromObject(WorkbenchView view) {
        requireNonNull(view, "view must not be null");
        return perspectiveOf(view.getClass());
    }

    public static Class<? extends Perspective> perspectiveOf(Class<? extends WorkbenchView> viewClass) {
        requireNonNull(viewClass, "viewClass must not be null");
        Shown shownAtAnnotation = viewClass.getAnnotation(Shown.class);
        if (shownAtAnnotation == null) {
            return Views.DEFAULT_PERSPECTIVE;
        }
        return shownAtAnnotation.in();
    }

    public static final ListMultimap<Class<? extends Perspective>, WorkbenchView> mapToPerspective(
            Collection<WorkbenchView> views2) {
        Builder<Class<? extends Perspective>, WorkbenchView> perspectiveToViewBuilder = ImmutableListMultimap.builder();
        for (WorkbenchView view : views2) {
            perspectiveToViewBuilder.put(perspectiveFromObject(view), view);
        }
        return perspectiveToViewBuilder.build();
    }

    public static final List<AbstractPerspectiveInstance> instantiatePerspectives(
            ListMultimap<Class<? extends Perspective>, WorkbenchView> perspectiveToViews) {
        List<AbstractPerspectiveInstance> perspectiveToNode = new ArrayList<>();
        for (Class<? extends Perspective> perspective : perspectiveToViews.keySet()) {
            final String name = nameFrom(perspective);
            final Node icon = ofNullable(graphicFrom(perspective, PERSPECTIVE_BUTTON_ICON_SIZE))
                    .orElseGet(Views::perspectiveDefaultIcon);

            BorderLayoutPerspectiveImpl instance = new BorderLayoutPerspectiveImpl(name, icon,
                    perspectiveToViews.get(perspective), orderFrom(perspective));
            perspectiveToNode.add(instance);
        }
        sort(perspectiveToNode, comparingInt(AbstractPerspectiveInstance::order));
        return perspectiveToNode;
    }

    public static final Node graphicFrom(Class<?> currentPerspective) {
        return graphicFrom(currentPerspective, DEFAULT_ICON_SIZE);
    }

    public static final Node graphicFrom(Class<?> currentPerspective, String size) {
        final Icon iconAnnotation = currentPerspective.getAnnotation(Icon.class);
        if (iconAnnotation == null) {
            return null;
        }
        final Text nodeIcon = FontAwesomeIconFactory.get().createIcon(iconAnnotation.icon(), size);
        final Color color = Color.valueOf(iconAnnotation.color().trim());
        nodeIcon.setFill(color);
        return nodeIcon;
    }

    public static final List<AbstractPerspectiveInstance> perspectiveInstancesFrom(Collection<WorkbenchView> views) {
        return instantiatePerspectives(mapToPerspective(views));
    }

    public static final Node viewPaneFrom(List<WorkbenchView> posViews) {
        return createContainerPaneFrom(posViews);
    }

    public static final List<WorkbenchView> viewsForPosition(List<WorkbenchView> views, PerspectivePos position) {
        return views.stream().filter((view) -> position.equals(extractPerspectivePosition(view)))
                .collect(Collectors.toList());
    }

    private static String nameFrom(Object view) {
        Class<?> viewClass = view.getClass();
        Name name = viewClass.getAnnotation(Name.class);
        if (name != null) {
            return name.value();
        }

        return Names.nameFromNameMethod(view).orElse(viewClass.getSimpleName());
    }
    
    public static Node createContainerPaneFrom(List<?> views) {
        if(views.size() == 1) {
            return MiniFxComponents.fxNodeFrom(views.get(0));
        }
        return createTabPaneFrom(views);
    }

    private static TabPane createTabPaneFrom(List<?> posViews) {
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

    private static final Node perspectiveDefaultIcon() {
        // final Node perspectiveDefaultIcon = new Text("");
        // perspectiveDefaultIcon.setStyle("-fx-font-size:" + PERSPECTIVE_BUTTON_ICON_SIZE);
        // return perspectiveDefaultIcon;
        Text defaultIcon = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.ANGLE_DOWN,
                PERSPECTIVE_BUTTON_ICON_SIZE);
        defaultIcon.setFill(Color.rgb(2, 2, 2));
        return defaultIcon;
    }

}
