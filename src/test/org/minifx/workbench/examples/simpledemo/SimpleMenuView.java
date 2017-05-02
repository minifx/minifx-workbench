/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.examples.simpledemo;

import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.PerspectivePos;
import org.minifx.workbench.domain.WorkbenchView;
import org.springframework.stereotype.Component;

import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

@Name("List of categories")
@View(at=PerspectivePos.LEFT, in=DashboardPerspective.class)
@Component
public class SimpleMenuView extends StackPane implements WorkbenchView{
    
    public SimpleMenuView() {
        
        ListView<String> list = new ListView<String>();
        list.getItems().add("Category A");
        list.getItems().add("Category B");
        
        this.getChildren().add(list);
    }

}
