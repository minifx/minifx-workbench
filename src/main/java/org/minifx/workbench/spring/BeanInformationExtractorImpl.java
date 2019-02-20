/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.spring;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.minifx.workbench.annotations.Icon;
import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.NoGutters;
import org.minifx.workbench.domain.definition.DisplayProperties;
import org.minifx.workbench.util.Icons;
import org.minifx.workbench.util.Optionals;
import org.minifx.workbench.util.Perspectives;
import org.minifx.workbench.util.Purpose;
import org.springframework.core.annotation.Order;

import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.minifx.workbench.util.Names.nameFromNameMethod;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

public class BeanInformationExtractorImpl implements BeanInformationExtractor {

    private final BeanInformationRepository repository;

    public BeanInformationExtractorImpl(BeanInformationRepository beanInformationRepository) {
        this.repository = requireNonNull(beanInformationRepository, "beanInformationRepository must not be null");
    }

    @Override
    public DisplayProperties displayPropertiesFrom(Object view, Purpose purpose) {
        return new DisplayProperties(viewNameFrom(view), graphicsFor(view, purpose).orElse(null), viewOrderFrom(view), hasGutters(view));
    }

    private boolean hasGutters(Object view) {
        return !repository.from(view).getAnnotation(NoGutters.class).isPresent();
    }

    private String viewNameFrom(Object view) {
        Optional<String> nameFromAnnotation = repository.from(view).getAnnotation(Name.class).map(Name::value);
        return Optionals.first(nameFromAnnotation, nameFromNameMethod(view), repository.beanNameFor(view))
                .orElse(view.getClass().getSimpleName());
    }

    private Optional<Node> graphicsFor(Object view, Purpose purpose) {
        Optional<Node> icon = iconAnnotation(view).map(ic -> Icons.graphicFrom(ic, purpose.defaultIconSize()));
        Optional<Node> defaultIcon = defaultFor(purpose);
        return Optionals.first(icon, defaultIcon);
    }

    private Optional<Node> defaultFor(Purpose purpose) {
        if (Purpose.PERSPECTIVE.equals(purpose)) {
            return Optional.of(Perspectives.perspectiveDefaultGraphics());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Color iconColor(Object view) {
        String color = iconAnnotation(view).map(Icon::color).orElse(Icon.DEFAULT_COLOR);
        return Color.valueOf(color);
    }

    private int viewOrderFrom(Object view) {
        Optional<Order> order = repository.from(view).getAnnotation(Order.class);
        return order.map(Order::value).orElse(LOWEST_PRECEDENCE);
    }

    private Optional<Icon> iconAnnotation(Object view) {
        return repository.from(view).getAnnotation(Icon.class);
    }

}
