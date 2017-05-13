package me.infuzion.tank.wars.client.render.fx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import me.infuzion.tank.wars.client.key.fx.FxKeyListener;
import me.infuzion.tank.wars.input.executor.InputExecutor;
import me.infuzion.tank.wars.object.Drawable;
import me.infuzion.tank.wars.provider.InfoProvider;
import me.infuzion.tank.wars.util.GraphicsObject;
import me.infuzion.tank.wars.util.Settings;

import java.net.URL;
import java.util.ResourceBundle;

public class GameAreaController implements Initializable {

    @FXML
    private Canvas canvas;

    public void registerKeyListener(InputExecutor executor) {
        FxKeyListener keyListener = new FxKeyListener(executor);
        canvas.requestFocus();
        canvas.setOnKeyPressed(keyListener::onKeyDown);
        canvas.setOnKeyReleased(keyListener::onKeyRelease);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvas.setHeight(Settings.SCREEN_HEIGHT);
        canvas.setWidth(Settings.SCREEN_WIDTH);
        canvas.getGraphicsContext2D().strokeText("Loading files!", 50, 50);
    }

    void draw(InfoProvider provider) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.WHITE);
        GraphicsObject g = new FxGraphicsObject(context);
        g.fillRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        for (Drawable object : provider.getDrawableObjects()) {
            object.draw(g);
        }
        g.setColor(Color.BLACK);
        g.setFont(g.getFont().deriveFont(12.0f));
        g.drawString("FPS: " + provider.getFPS() + " limited to " + Settings.frameLimit, 0, 20);
        g.drawString("TPS: " + provider.getTPS() + " limited to " + Settings.tickLimit, 0, 40);
        g.drawString("JavaFX Renderer", 0, 60);
    }
}
