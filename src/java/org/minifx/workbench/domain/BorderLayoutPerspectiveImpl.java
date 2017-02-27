/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.domain;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static org.minifx.workbench.css.MiniFxCssConstants.COMPONENTS_OF_MAIN_PANEL_CLASS;
import static org.minifx.workbench.css.MiniFxCssConstants.SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS;
import static org.minifx.workbench.domain.PerspectivePos.CENTER;
import static org.minifx.workbench.util.Views.viewPaneFrom;
import static org.minifx.workbench.util.Views.viewsForPosition;

import java.util.List;

import org.minifx.workbench.util.Views;

import javafx.scene.Node;

/**
 * Internal API. Represents the real manifestation of a perspective
 * 
 * @author kfuchsbe
 */
public class BorderLayoutPerspectiveImpl extends AbstractPerspectiveInstance {

    public BorderLayoutPerspectiveImpl(String name, Node graphic, List<WorkbenchView> views, int order) {
        super(name, graphic, views, order);
        initComponents();
    }

    private void initComponents() {
        List<WorkbenchView> views = views();

        if (views.isEmpty()) {
            singleViewInit(singletonList(new TextWorkbenchView("No views to display")));
        } else if (allViewsBelongToOnePosition(views)) {
            singleViewInit(views);
        } else {
            multipleViewInit(views);
        }
    }

    private void singleViewInit(List<WorkbenchView> views) {
        setupNodeFor(CENTER, views).getStyleClass().add(SINGLE_COMPONENT_OF_MAIN_PANEL_CLASS);
    }

    private void multipleViewInit(List<WorkbenchView> views) {
        for (PerspectivePos position : PerspectivePos.values()) {
            List<WorkbenchView> posViews = viewsForPosition(views, position);
            if (!posViews.isEmpty()) {
                setupNodeFor(position, posViews).getStyleClass().add(COMPONENTS_OF_MAIN_PANEL_CLASS);
            }
        }
    }

    private Node setupNodeFor(PerspectivePos position, List<WorkbenchView> posViews) {
        Node viewPane = viewPaneFrom(posViews);
        viewPane.minWidth(400);
        viewPane.maxWidth(400);
        viewPane.prefWidth(400);
        position.set(viewPane).into(this);
        return viewPane;
    }

    private static boolean allViewsBelongToOnePosition(List<WorkbenchView> views) {
        return views.stream().collect(groupingBy(Views::extractPerspectivePosition)).values().stream()
                .anyMatch(viewsOfAPosition -> viewsOfAPosition.size() == views.size());
    }
}
