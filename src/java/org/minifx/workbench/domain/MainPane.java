/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.Collections.singletonList;
import static org.minifx.workbench.css.MiniFxCssConstants.SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS;
import static org.minifx.workbench.util.MiniFxComponents.nodesFrom;

import java.util.ArrayList;
import java.util.List;

import org.minifx.workbench.css.MiniFxCssConstants;
import org.minifx.workbench.util.Views;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Main JavaFX Pane for a MiniFx application. It has a toolbar for perspective switching at the top. The active
 * perspective is integrated in the center.
 */
public class MainPane extends BorderPane {

    private static final HBox DEFAULT_FILLER = new HBox();

    public MainPane(List<AbstractPerspectiveInstance> perspectives, List<ToolbarItem> toolbarItems,
            List<WorkbenchFooter> footerItems) {
        this(perspectives, toolbarItems, footerItems, DEFAULT_FILLER);
    }

    public MainPane(List<AbstractPerspectiveInstance> perspectives, List<ToolbarItem> toolbarItems,
            List<WorkbenchFooter> footerItems, Node filler) {
        List<ToggleButton> perspectiveNodes = perspectiveButtons(copyOf(perspectives));
        List<Node> toolbarNodes = nodesFrom(copyOf(toolbarItems));

        createToolbar(perspectiveNodes, toolbarNodes, filler);
        createFooter(footerItems);
        
        triggerAnyPerspectiveSelection(perspectiveNodes);
    }

    private void createFooter(List<WorkbenchFooter> footerItems) {
        if(footerItems.isEmpty()) {
            return;
        }
        Node footerTabs = Views.createContainerPaneFrom(footerItems);
        footerTabs.getStyleClass().add(SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS);
        setBottom(footerTabs);
    }

    private void triggerAnyPerspectiveSelection(List<ToggleButton> perspectiveNodes) {
        if (!perspectiveNodes.isEmpty()) {
            perspectiveNodes.get(0).fire();
        }
    }

    private void createToolbar(List<? extends Node> perspectiveNodes, List<? extends Node> toolbarNodes,
            Node filler) {
        HBox toolbarBox = horizontalBoxOf(toolbarNodes, Pos.TOP_LEFT);
        HBox centralBox = horizontalBoxOf(singletonList(filler), Pos.TOP_CENTER);
        HBox perspectivesBox = horizontalBoxOf(perspectiveNodes, Pos.TOP_RIGHT);

        HBox.setHgrow(centralBox, Priority.ALWAYS);

        setTop(new ToolBar(toolbarBox, centralBox, perspectivesBox));
    }

    private HBox horizontalBoxOf(List<? extends Node> nodes, Pos alignment) {
        nodes.forEach(node -> node.getStyleClass().add(MiniFxCssConstants.TOOLBAR_BUTTON_CLASS));

        HBox box = new HBox();
        box.setSpacing(3);
        box.setAlignment(alignment);
        box.setFillHeight(true);
        box.getChildren().addAll(nodes);
        return box;
    }

    private List<ToggleButton> perspectiveButtons(List<AbstractPerspectiveInstance> perspectives) {
        List<ToggleButton> perspectiveButtons = new ArrayList<>();
        ToggleGroup toggleGroup = new ToggleGroup();
        for (AbstractPerspectiveInstance perspective : perspectives) {
            ToggleButton button = new ToggleButton(perspective.name(), perspective.graphic());
            button.setOnAction(evt -> setActive(perspective));
            button.getStyleClass().add(MiniFxCssConstants.PERSPECTIVE_BUTTON_CLASS);

            perspectiveButtons.add(button);
            toggleGroup.getToggles().add(button);
        }
        return perspectiveButtons;
    }

    private void setActive(AbstractPerspectiveInstance perspective) {
        perspective.getStyleClass().add(MiniFxCssConstants.MAIN_PANE_CLASS);
        setCenter(perspective);
    }

}
