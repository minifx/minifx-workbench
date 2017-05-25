/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.util;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.minifx.workbench.css.MiniFxCssConstants.COMPONENTS_OF_MAIN_PANEL_CLASS;
import static org.minifx.workbench.css.MiniFxCssConstants.SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;

import org.minifx.workbench.domain.AbstractViewDefinition;
import org.minifx.workbench.domain.PerspectiveDefinition;
import org.minifx.workbench.domain.PerspectivePos;
import org.minifx.workbench.domain.TextWorkbenchView;
import org.minifx.workbench.domain.ViewDefinition;

import com.google.common.collect.Iterables;

import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

public class MiniFxComponents {

    private MiniFxComponents() {
        /* only static methods */
    }

    public static Node fxNodeFrom(Object view) {
        if (view instanceof Node) {
            return (Node) view;
        }
        if (view instanceof JComponent) {
            SwingNode swingNode = new SwingNode();
            swingNode.setContent((JComponent) view);
            return swingNode;
        }
        throw new IllegalArgumentException(
                "A view must either be a java-fx Node or swing JComponent. None of this is the case for the view "
                        + view + ".");
    }

    public static final List<Node> nodesFrom(List<?> toolbarItems) {
        return toolbarItems.stream().map(MiniFxComponents::fxNodeFrom).collect(toList());
    }

    public static TabPane tabPaneFrom(Collection<? extends AbstractViewDefinition> posViews) {
        List<AbstractViewDefinition> sortedViews = posViews.stream()
                .sorted(Comparator.comparingInt(v -> v.displayProperties().order())).collect(toList());

        TabPane tabRoot = new TabPane();
        for (AbstractViewDefinition node : sortedViews) {
            Tab tab = new Tab();
            tab.setText(node.displayProperties().name());
            tab.setGraphic(node.displayProperties().graphic().orElse(null));
            tab.setContent(node.node());
            tab.setClosable(false);
            tabRoot.getTabs().add(tab);
        }
        return tabRoot;
    }

    public static final Optional<Node> containerPaneFrom(Collection<? extends AbstractViewDefinition> views) {
        if (views.isEmpty()) {
            return Optional.empty();
        }

        if ((views.size() == 1)) {
            AbstractViewDefinition singleView = Iterables.getOnlyElement(views);
            if (!singleView.alwaysShowTabs()) {
                return Optional.of(singleView.node());
            }
        }

        return Optional.of(tabPaneFrom(views));
    }

    public static Node configureMultiNodeStyle(Node node) {
        node.getStyleClass().add(COMPONENTS_OF_MAIN_PANEL_CLASS);
        return node;
    }

    public static Node configureSingleNodeStyle(Node node) {
        node.getStyleClass().add(SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS);
        return node;
    }

    public static Consumer<Node> styleConfigurator(Map<PerspectivePos, List<ViewDefinition>> positionViews) {
        if (positionViews.size() == 1) {
            return MiniFxComponents::configureSingleNodeStyle;
        } else {
            return MiniFxComponents::configureMultiNodeStyle;
        }
    }

    public static void placeViewsIntoPerspective(BorderPane perspectiveImpl,
            Map<PerspectivePos, List<ViewDefinition>> positionViews, Consumer<Node> styleConfigurator) {
        for (Entry<PerspectivePos, List<ViewDefinition>> entry : positionViews.entrySet()) {
            Node node = containerPaneFrom(entry.getValue())
                    .orElseThrow(() -> new RuntimeException("No view for pos '" + entry.getKey() + "'"));
            styleConfigurator.accept(node);
            entry.getKey().set(node).into(perspectiveImpl);
        }
    }

    public static Node createPerspective(PerspectiveDefinition definition) {
        Set<ViewDefinition> views = definition.views();

        BorderPane perspectiveImpl = new BorderPane();
        if (views.isEmpty()) {
            Node node = new TextWorkbenchView("No views to display");
            configureSingleNodeStyle(node);
            ViewInstantiator.DEFAULT_POSITION.set(node).into(perspectiveImpl);
        } else {
            Map<PerspectivePos, List<ViewDefinition>> positionViews = views.stream()
                    .collect(groupingBy(ViewDefinition::perspectivePos));
            placeViewsIntoPerspective(perspectiveImpl, positionViews, styleConfigurator(positionViews));
        }
        return perspectiveImpl;
    }

}
