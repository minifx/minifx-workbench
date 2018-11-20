package org.minifx.workbench.examples.doc;

import javafx.scene.control.Label;
import org.minifx.workbench.annotations.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimplisticConfiguration {

    @View
    @Bean
    public Label helloWorldLabel() {
        return new Label("Hello World");
    }

}
