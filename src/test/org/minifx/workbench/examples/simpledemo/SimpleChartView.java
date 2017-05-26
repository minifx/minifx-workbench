/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.examples.simpledemo;

import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.domain.PerspectivePos;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.StackPane;

@Name("A chart")
@Icon(color="orange", value=FontAwesomeIcon.PIE_CHART)
@View(at=PerspectivePos.CENTER, in=DashboardPerspective.class)
@Component
public class SimpleChartView extends StackPane {

    public SimpleChartView() {
        PieChart pieChart = new PieChart();
        pieChart.getData().add(new Data("A", 10));
        pieChart.getData().add(new Data("B", 10));
        
        this.getChildren().add(pieChart);
    }
    
}
