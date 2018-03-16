/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.conf.fullyconfigured;

import static org.minifx.workbench.domain.PerspectivePos.CENTER;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.minifx.workbench.annotations.Name;
import org.minifx.workbench.annotations.View;
import org.minifx.workbench.spring.ActivatePerspectiveCommand;
import org.minifx.workbench.spring.PerspectiveActivatedEvent;
import org.minifx.workbench.spring.ChangePerspectiveButtonStyleCommand;
import org.minifx.workbench.util.InSwing;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.google.common.collect.ImmutableList;

import javafx.scene.Node;

@Configuration
@ComponentScan
public class FullExampleConfiguration {

    @Bean
    public P1LeftA p1LeftViewA() {
        return new P1LeftA();
    }

    @Bean
    public P1CenterA p1CenterViewA() {
        return new P1CenterA();
    }

    @Bean
    public P1LeftB p1LeftViewB() {
        return new P1LeftB();
    }

    @Bean
    public UnannotatedView unannotatedView() {
        return new UnannotatedView();
    }

    @Bean
    public EmbeddedSwingPanel swingNode() {
        return InSwing.create(EmbeddedSwingPanel::new);
    }

    // @Bean
    /* enable this bean to see perspectives automatically switching */
    public ExecutorService perspectiveSwitcher(ApplicationEventPublisher publisher) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        AtomicInteger flag = new AtomicInteger();
        service.scheduleAtFixedRate(() -> {
            ActivatePerspectiveCommand event;
            if (flag.getAndIncrement() % 2 == 0) {
                event = new ActivatePerspectiveCommand(Perspective1.class);
            } else {
                event = new ActivatePerspectiveCommand(Perspective2.class);
            }
            System.out.println("publishing " + event);
            publisher.publishEvent(event);
        } , 5, 5, TimeUnit.SECONDS);
        return service;
    }

    @Bean
    /* enable this bean to see perspectives state changes switching */
    public ExecutorService styleSwitcher(ApplicationEventPublisher publisher) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        AtomicInteger flag = new AtomicInteger();
        service.scheduleAtFixedRate(() -> {
            ChangePerspectiveButtonStyleCommand event;
            if (flag.getAndIncrement() % 2 == 0) {
                event = ChangePerspectiveButtonStyleCommand.fromPerspectiveAndChange(Perspective2.class, node -> {
                    node.getStyleClass().remove("error-status");
                    node.getStyleClass().add("successful-status");
                });
            } else {
                event = ChangePerspectiveButtonStyleCommand.fromPerspectiveAndChange(Perspective2.class, node -> {
                    node.getStyleClass().remove("successful-status");
                    node.getStyleClass().add("error-status");
                });
            }
            System.out.println("publishing " + event);
            publisher.publishEvent(event);
        } , 3, 7, TimeUnit.SECONDS);
        return service;
    }

    @EventListener
    public void printActivatedPerspective(PerspectiveActivatedEvent evt) {
        System.out.println("Perspective " + evt.perspective() + " was activated.");
    }

    @View(in = Perspective1.class, at = CENTER)
    @Name("CERN Website")
    @Bean
    public String webView() {
        return "http://www.cern.ch";
    }

    @Bean
    public List<String> cssStyleSheets() {
        return ImmutableList.of("/org/minifx/workbench/conf/fullyconfigured/checklistPerspectiveButtonStyle.css");
    }

}