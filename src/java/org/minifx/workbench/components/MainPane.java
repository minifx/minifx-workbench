/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.components;

import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.Collections.singletonList;
import static org.minifx.workbench.util.MiniFxComponents.containerPaneFrom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.minifx.workbench.css.MiniFxCssConstants;
import org.minifx.workbench.domain.definition.DisplayProperties;
import org.minifx.workbench.domain.definition.FooterDefinition;
import org.minifx.workbench.domain.definition.PerspectiveDefinition;
import org.minifx.workbench.nodes.FxNodeFactory;
import org.minifx.workbench.spring.AbstractPerspectiveEvent;
import org.minifx.workbench.spring.ActivatePerspectiveCommand;
import org.minifx.workbench.spring.PerspectiveActivatedEvent;
import org.minifx.workbench.spring.PerspectiveButtonColorCommand;
import org.minifx.workbench.util.MiniFxComponents;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Main JavaFX Pane for a MiniFx application. It has a toolbar for perspective switching at the top. The active
 * perspective is integrated in the center.
 */
public class MainPane extends BorderPane {

    private static final HBox DEFAULT_FILLER = new HBox();

    private final Map<Object, Node> perspectives;
    private final Map<Object, ToggleButton> perspectivesToButtons = new HashMap<>();

    private final ApplicationEventPublisher publisher;

    public MainPane(Collection<PerspectiveDefinition> perspectives, Iterable<Object> toolbarItems,
            Collection<FooterDefinition> footers, ApplicationEventPublisher publisher, FxNodeFactory fxNodeFactory) {
        this(perspectives, toolbarItems, footers, DEFAULT_FILLER, publisher, fxNodeFactory);
    }

    public MainPane(Collection<PerspectiveDefinition> perspectives, Iterable<Object> toolbarItems,
            Collection<FooterDefinition> footers, Node filler, ApplicationEventPublisher publisher,
            FxNodeFactory fxNodeFactory) {

        this.publisher = publisher;
        this.perspectives = perspectives.stream()
                .collect(Collectors.toMap(PerspectiveDefinition::perspective, MiniFxComponents::createPerspective));

        List<PerspectiveDefinition> orderedPerspectives = perspectives.stream()
                .sorted(Comparator.comparingInt(p -> p.displayProperties().order())).collect(Collectors.toList());

        List<ToggleButton> perspectiveButtons = perspectiveButtons(orderedPerspectives);
        List<Node> toolbarNodes = fxNodeFactory.fxNodesFrom(copyOf(toolbarItems));

        createToolbar(perspectiveButtons, toolbarNodes, filler);

        Optional<Node> footer = containerPaneFrom(footers).map(MiniFxComponents::configureSingleNodeStyle);
        setBottom(footer.orElse(null));

        triggerAnyPerspectiveSelection(perspectiveButtons);
    }

    private void triggerAnyPerspectiveSelection(List<ToggleButton> perspectiveNodes) {
        if (!perspectiveNodes.isEmpty()) {
            perspectiveNodes.get(0).fire();
        }
    }

    private void createToolbar(List<? extends Node> perspectiveNodes, List<? extends Node> toolbarNodes, Node filler) {
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

    private List<ToggleButton> perspectiveButtons(List<PerspectiveDefinition> perspectiveDefinitions) {
        List<ToggleButton> perspectiveButtons = new ArrayList<>();
        ToggleGroup toggleGroup = new ToggleGroup();
        for (PerspectiveDefinition perspective : perspectiveDefinitions) {
            DisplayProperties displayProperties = perspective.displayProperties();
            ToggleButton button = new ToggleButton(displayProperties.name(), displayProperties.graphic().orElse(null));
            button.setOnAction(evt -> setActive(perspective.perspective()));
            button.getStyleClass().add(MiniFxCssConstants.PERSPECTIVE_BUTTON_CLASS);

            perspectivesToButtons.put(perspective.perspective(), button);
            perspectiveButtons.add(button);
            toggleGroup.getToggles().add(button);
        }
        return perspectiveButtons;
    }

    private void setActive(Object perspectiveDefinition) {
        Node perspective = this.perspectives.get(perspectiveDefinition);
        perspective.getStyleClass().add(MiniFxCssConstants.MAIN_PANE_CLASS);
        setCenter(perspective);
        publisher.publishEvent(new PerspectiveActivatedEvent(perspectiveDefinition));
    }

    @EventListener
    public void acivatePerspective(ActivatePerspectiveCommand command) {
        Platform.runLater(() -> {
            perspectiveButton(command).ifPresent(ToggleButton::fire);
        });
    }

    private Optional<ToggleButton> perspectiveButton(AbstractPerspectiveEvent command) {
        return Optional.of(this.perspectivesToButtons.get(command.perspective()));
    }

//    @EventListener
//    public void colorPerspectiveButton(AbstractPerspectiveEvent command) {
//        Platform.runLater(() -> {
//            perspectiveButton(command).ifPresent(b -> b.setBackground());
//        });
//    }

}
