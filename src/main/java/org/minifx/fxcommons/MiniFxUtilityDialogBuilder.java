/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.fxcommons;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Collection;

import static org.minifx.fxcommons.MiniFxSceneBuilder.miniFxSceneBuilder;

public final class MiniFxUtilityDialogBuilder {

    private final MiniFxSceneBuilder sceneBuilder = miniFxSceneBuilder();
    private String title = "";
    private Node owner;

    private MiniFxUtilityDialogBuilder() {
        /* Factory method */
    }

    public static MiniFxUtilityDialogBuilder miniFxUtilityDialogBuilder() {
        return new MiniFxUtilityDialogBuilder();
    }

    public MiniFxUtilityDialogBuilder withTitle(String inTitle) {
        title = inTitle;
        return this;
    }

    public MiniFxUtilityDialogBuilder withRoot(Parent parent) {
        AnchorPane dialogWrapper = new AnchorPane(parent);
        AnchorPane.setTopAnchor(parent, 10.0);
        AnchorPane.setRightAnchor(parent, 10.0);
        AnchorPane.setBottomAnchor(parent, 10.0);
        AnchorPane.setLeftAnchor(parent, 10.0);
        sceneBuilder.withRoot(dialogWrapper);
        return this;
    }

    public MiniFxUtilityDialogBuilder withSceneSize(int width, int height) {
        sceneBuilder.withSize(width, height);
        return this;
    }

    public MiniFxUtilityDialogBuilder withOwner(Node owner) {
        this.owner = owner;
        return this;
    }

    public MiniFxUtilityDialogBuilder withAdditionalCss(Collection<String> inAdditionalCss) {
        sceneBuilder.withAdditionalCss(inAdditionalCss);
        return this;
    }

    public Stage build() {
        Stage dialog = new Stage();
        dialog.setTitle(title);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setScene(sceneBuilder.build());

        if(owner != null && owner.getScene() != null && owner.getScene().getWindow() != null) {
            dialog.initOwner(owner.getScene().getWindow());
        }

        return dialog;
    }
}
