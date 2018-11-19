package org.minifx.fxcommons;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.function.Consumer;

import org.minifx.workbench.domain.Perspective;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Entry point for JavaFx applications that make use of Spring IOC for dependency injection. In order to use this class
 * call {@link #applicationLauncher()} in order to get the static builder initializer. Use
 * {@link FxLauncher#launch(String...)} in order to start the JavaFx application. Once started, a Spring context is
 * created and a {@link Scene} bean is created. Use the {@link Perspective} beans to build your application. In order to
 * customize the JavaFx {@link Stage}, use the {@link FxLauncher}.
 *
 * @author acalia, mgalilee
 */
@Component
public class SingleSceneSpringJavaFxApplication extends Application {

    public static final Consumer<WindowEvent> EXIT_ON_CLOSE = ev -> System.exit(0);
    private static final FxLauncher LAUNCHER = new FxLauncher();

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (!LAUNCHER.readyToLaunch) {
            throw new RuntimeException(
                    "Use the builder to configure the JavaFx application. Do not call directly Application.launch(...)");
        }

        @SuppressWarnings("resource") /* Closed automatically by the hook */
                AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(LAUNCHER.configurationClasses);
        /* Spring context register itself in the shutdown hook. It automatically keeps a reference to it this way */
        ctx.registerShutdownHook();
        Scene mainScene = ctx.getBean(Scene.class);
        primaryStage.setScene(mainScene);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setOnCloseRequest(LAUNCHER.windowCloseHandler::accept);
        primaryStage.setTitle(LAUNCHER.windowTitle);
    }

    /**
     * Returns the static builder of {@link SingleSceneSpringJavaFxApplication}. Use this method to configure and launch
     * a {@link SingleSceneSpringJavaFxApplication}.
     *
     * @return the single instance of the application launcher
     */
    public static FxLauncher applicationLauncher() {
        return LAUNCHER;
    }

    public static class FxLauncher {
        private Class<?>[] configurationClasses = new Class<?>[0];
        private String windowTitle = "";
        private Consumer<WindowEvent> windowCloseHandler = EXIT_ON_CLOSE;
        private boolean readyToLaunch = false;

        public FxLauncher configurationClasses(Class<?>... _configurationClasses) {
            requireNonNull(_configurationClasses, "configurationClasses must not be null");
            if (_configurationClasses.length < 1) {
                throw new IllegalArgumentException("There must be at least one configuration class.");
            }
            this.configurationClasses = Arrays.copyOf(_configurationClasses, _configurationClasses.length);
            return this;
        }

        public FxLauncher windowTitle(String _windowTitle) {
            this.windowTitle = _windowTitle;
            return this;
        }

        public FxLauncher windowCloseHandler(Consumer<WindowEvent> _windowCloseHandler) {
            this.windowCloseHandler = _windowCloseHandler;
            return this;
        }

        /**
         * Use {@link Application#launch(String...)} to start {@link SingleSceneSpringJavaFxApplication}.
         *
         * @param args the command line arguments to be passed to the application
         */
        public void launch(String... args) {
            this.readyToLaunch = true;
            Application.launch(SingleSceneSpringJavaFxApplication.class, args);
        }

    }
}