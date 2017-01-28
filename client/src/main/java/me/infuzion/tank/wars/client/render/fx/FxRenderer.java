package me.infuzion.tank.wars.client.render.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import me.infuzion.tank.wars.client.render.Renderer;
import me.infuzion.tank.wars.provider.InfoProvider;

public class FxRenderer extends Application implements Renderer {

    private static boolean init;
    private static InfoProvider provider;
    private static GameAreaController controller;

    public FxRenderer(InfoProvider provider) {
        FxRenderer.provider = provider;
        new Thread(Application::launch).start();

    }

    /**
     * Should never be called by anything other than JavaFX
     */
    public FxRenderer() {
        if (!Platform.isFxApplicationThread()) {
            throw new RuntimeException("Should not be called!");
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("/GameArea.fxml").openStream());
        controller = loader.getController();
        if (controller == null) {
            throw new RuntimeException(
                "NULL CONTROLLER! JavaFX failed to initialize or this method was not called by JavaFX.");
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        init = true;
        primaryStage.setTitle("Rendering using JavaFX!");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }


    @Override
    public void draw() {
        if (!init) {
            return;
        }
        Platform.runLater(() -> controller.draw(provider));
    }

    @Override
    public void stopRenderer() {
    }

}
