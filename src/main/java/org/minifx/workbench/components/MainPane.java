/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.minifx.workbench.css.MiniFxCssConstants;
import org.minifx.workbench.domain.definition.DisplayProperties;
import org.minifx.workbench.domain.definition.FooterDefinition;
import org.minifx.workbench.domain.definition.PerspectiveDefinition;
import org.minifx.workbench.nodes.FxNodeFactory;
import org.minifx.workbench.spring.AbstractPerspectiveEvent;
import org.minifx.workbench.spring.ActivatePerspectiveCommand;
import org.minifx.workbench.spring.ChangePerspectiveButtonStyleCommand;
import org.minifx.workbench.spring.PerspectiveActivatedEvent;
import org.minifx.workbench.util.MiniFxComponents;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.copyOf;
import static java.util.Collections.singletonList;
import static org.minifx.workbench.css.MiniFxCssConstants.PERSPECTIVE_BUTTON_CLASS;
import static org.minifx.workbench.css.MiniFxCssConstants.TOOLBAR_BUTTON_CLASS;
import static org.minifx.workbench.util.MiniFxComponents.containerPaneFrom;
import static org.minifx.workbench.util.MiniFxComponents.createPerspective;

/**
 * Main JavaFX Pane for a MiniFx application. It has a toolbar for perspective switching at the top. The active
 * perspective is integrated in the center.
 */
public class MainPane extends BorderPane {

    private static final HBox DEFAULT_FILLER = new HBox();

    private final Map<Object, Node> perspectives = new HashMap<>();
    private final Map<Object, ToggleButton> perspectivesToButtons = new HashMap<>();

    private final ApplicationEventPublisher publisher;
    private HBox perspectiveButtonsBox;
    private ToggleGroup perspectiveButtonToggleGroup = new ToggleGroup();

    public MainPane(Iterable<Object> toolbarItems, ApplicationEventPublisher publisher, FxNodeFactory fxNodeFactory) {
        this(toolbarItems, DEFAULT_FILLER, publisher, fxNodeFactory);
    }

    public MainPane(Iterable<Object> toolbarItems, Node filler, ApplicationEventPublisher publisher,
            FxNodeFactory fxNodeFactory) {
        this.publisher = publisher;
        List<Node> toolbarNodes = fxNodeFactory.fxNodesFrom(copyOf(toolbarItems));

        HBox toolbarBox = horizontalBoxOf(Pos.TOP_LEFT);
        HBox centralBox = horizontalBoxOf(Pos.TOP_CENTER);
        perspectiveButtonsBox = horizontalBoxOf(Pos.TOP_RIGHT);

        addToolbarButtonsTo(toolbarNodes, toolbarBox);
        addToolbarButtonsTo(singletonList(filler), centralBox);

        HBox.setHgrow(centralBox, Priority.ALWAYS);

        setTop(new ToolBar(toolbarBox, centralBox, perspectiveButtonsBox));
    }

    /**
     * Set the {@link FooterDefinition}s that will be added to the pane. NOTE: this method can be called only once
     * in the current implementation!
     */
    public void setFooters(Collection<FooterDefinition> footers) {
        if (getBottom() != null) {
            /* A refactor of MiniFxComponents#containerPaneFrom would allow dynamic insertions of footer items */
            throw new IllegalStateException("Footer already initialized!");
        }

        Platform.runLater(() -> containerPaneFrom(footers).map(MiniFxComponents::configureSingleNodeStyle).ifPresent(this::setBottom));
    }

    /**
     * Add {@link PerspectiveDefinition}s to the pane. NOTE: this method can be called only once in the current
     * implementation!
     */
    public void setPerspectives(Collection<PerspectiveDefinition> definitions) {
        if (definitions.isEmpty()) {
            return;
        }

        if (!perspectives.isEmpty()) {
            throw new IllegalStateException("Perspectives already initialized!");
        }

        definitions.forEach(definition -> perspectives.put(definition.perspective(), createPerspective(definition)));

        List<ToggleButton> orderedPerspectiveButtons = definitions.stream() //
                .sorted(Comparator.comparingInt(p -> p.displayProperties().order())) //
                .map(this::createPerspectiveButton) //
                .collect(Collectors.toList());

        addToolbarButtonsTo(orderedPerspectiveButtons, perspectiveButtonsBox);
    }

    private static HBox horizontalBoxOf(Pos alignment) {
        HBox box = new HBox();
        box.setSpacing(3);
        box.setAlignment(alignment);
        box.setFillHeight(true);
        return box;
    }

    private static void addToolbarButtonsTo(List<? extends Node> nodes, HBox box) {
        nodes.forEach(node -> node.getStyleClass().add(TOOLBAR_BUTTON_CLASS));
        box.getChildren().addAll(nodes);
    }

    private ToggleButton createPerspectiveButton(PerspectiveDefinition perspective) {
        DisplayProperties displayProperties = perspective.displayProperties();
        ToggleButton button = new ToggleButton(displayProperties.name(), displayProperties.graphic().orElse(null));
        button.setGraphicTextGap(10);
        button.setOnAction(evt -> setActive(perspective.perspective()));
        button.getStyleClass().addAll(PERSPECTIVE_BUTTON_CLASS, TOOLBAR_BUTTON_CLASS);

        perspectivesToButtons.put(perspective.perspective(), button);
        perspectiveButtonToggleGroup.getToggles().add(button);
        return button;
    }

    private void setActive(Object perspectiveDefinition) {
        Node perspective = this.perspectives.get(perspectiveDefinition);
        perspective.getStyleClass().add(MiniFxCssConstants.MAIN_PANE_CLASS);
        setCenter(perspective);
        publisher.publishEvent(new PerspectiveActivatedEvent(perspectiveDefinition));
    }

    private Optional<ToggleButton> perspectiveButton(AbstractPerspectiveEvent command) {
        return Optional.of(this.perspectivesToButtons.get(command.perspective()));
    }

    @EventListener
    public void activatePerspective(ActivatePerspectiveCommand command) {
        Platform.runLater(() -> perspectiveButton(command).ifPresent(ToggleButton::fire));
    }

    @EventListener
    public void colorPerspectiveButton(ChangePerspectiveButtonStyleCommand command) {
        Platform.runLater(() -> perspectiveButton(command).map(b -> b.getStyleClass()).ifPresent(command::apply));
    }

}
