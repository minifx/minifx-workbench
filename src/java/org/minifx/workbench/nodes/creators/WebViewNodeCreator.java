/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.nodes.creators;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.minifx.workbench.nodes.FxNodeCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@Component
@Order(3)
public class WebViewNodeCreator implements FxNodeCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebViewNodeCreator.class);

    @Override
    public Node fxNodeFrom(Object object) {
        return urlFrom(object).map(url -> {
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load(url.toString());
            return webView;
        }).orElse(null);
    }

    private Optional<URL> urlFrom(Object object) {
        if (object instanceof URL) {
            return Optional.of((URL) object);
        }
        if (object instanceof String) {
            String string = (String) object;
            if (string.startsWith("http://") || string.startsWith("https://")) {
                try {
                    return Optional.of(new URL((String) object));
                } catch (MalformedURLException e) {
                    LOGGER.warn("No URL could be created from string {}.", string, e);
                }
            }
        }
        return Optional.empty();
    }

}
