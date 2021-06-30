package org.minifx.fxcommons;

import org.minifx.workbench.components.MainPane;
import org.springframework.context.ApplicationContext;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class MiniFxWorkbench {

    private final MiniFxSceneBuilder sceneBuilder;
    private final MainPane mainPane;
    private final ApplicationContext springContext;

    public MiniFxWorkbench(MiniFxSceneBuilder sceneBuilder, MainPane mainPane, ApplicationContext springContext) {
        this.sceneBuilder = sceneBuilder;
        this.mainPane = mainPane;
        this.springContext = springContext;
    }

    public Parent mainPane() {
        return mainPane;
    }

    public Scene createScene() {
        return sceneBuilder.withRoot(mainPane).build();
    }

    public void applyStylesTo(Scene scene) {
        sceneBuilder.applyStylesTo(scene);
    }

    public ApplicationContext springContext() {
        return springContext;
    }

}
