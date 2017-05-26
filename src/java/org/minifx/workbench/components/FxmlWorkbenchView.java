/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.minifx.workbench.components;

import static org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils.qualifiedBeanOfType;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.minifx.workbench.annotations.FxmlControllerQualifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class FxmlWorkbenchView extends Parent {
    private static final Logger LOGGER = LoggerFactory.getLogger(FxmlWorkbenchView.class);
    private final String fxmlFileName;

    public FxmlWorkbenchView(String fxmlFileName) {
        this.fxmlFileName = fxmlFileName;
    }

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void initializeFXML() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlFileName));
            loader.setControllerFactory(this::opportunisticLookupOrCreateBean);
            Parent root = loader.load();
            getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T opportunisticLookupOrCreateBean(Class<T> clazz) {
        try {
            if (getClass().isAnnotationPresent(FxmlControllerQualifier.class)) {
                LOGGER.info("Looking up controller {} in application context (with qualifier)", clazz);
                return qualifiedBeanOfType(applicationContext, clazz,
                        getClass().getAnnotation(FxmlControllerQualifier.class).value());
            } else {
                LOGGER.info("Looking up controller {} in application context", clazz);
                return applicationContext.getBean(clazz);
            }
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalStateException("More than one candidate for controller '" + clazz
                    + "' found, consider adding a @FxmlControllerQualifier annotation!", e);
        } catch (NoSuchBeanDefinitionException e) {
            String beanName = "fxml" + clazz.getSimpleName();
            LOGGER.info("No bean definition for controller {} found, opportunistically injecting '{}' on the fly.",
                    clazz, beanName);
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext;
            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setBeanClass(clazz);
            definition.setAutowireCandidate(true);
            definition.setLazyInit(false);
            registry.registerBeanDefinition(beanName, definition);
            return (T) applicationContext.getBean(beanName);
        }
    }
}
