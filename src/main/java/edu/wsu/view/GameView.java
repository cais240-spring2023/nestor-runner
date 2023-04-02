package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.model.Entities.Entity;
import edu.wsu.model.Nestor;
import edu.wsu.model.NestorRunner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.io.IOException;
import java.util.Objects;

public class GameView extends StackPane implements View {

    private final Label scoreField;
    private final Sound sound;
    private final FreezePane endPane;
    private final FreezePane pausePane;
    private final GraphicsContext gc;
    private final Canvas canvas;

    public GameView(int groundLevel) {
        super();
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #add8e6;");

        endPane = new FreezePane("Game Over!", "Play Again", "Main Menu");
        endPane.setButton2Action(this::swapToMainMenu);
        pausePane = new FreezePane("Game Paused.", "Resume", "Main Menu");
        endPane.setButton2Action(this::swapToMainMenu);
        sound = new Sound();
        canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        StackPane playSpace = new StackPane();
        Rectangle ground = new Rectangle(SCENE_WIDTH, SCENE_HEIGHT - groundLevel);
        StackPane.setAlignment(ground, Pos.BOTTOM_CENTER);
        ground.setFill(Color.BLACK);
        playSpace.getChildren().addAll(ground, canvas);
        this.getChildren().add(playSpace);

        BorderPane hud = new BorderPane();
        GridPane scoreBox = new GridPane();
        Label scoreLabel = new Label("Score:");
        scoreLabel.setFont(Font.font("Franklin Gothic Medium", 20));
        scoreBox.add(scoreLabel,0, 0);
        scoreField = new Label("0000");
        scoreField.setFont(Font.font("Franklin Gothic Medium", FontPosture.ITALIC, 30));
        scoreBox.add(scoreField,0, 1);
        scoreBox.setPadding(new Insets(15, 15, 15, 15));
        hud.setTop(scoreBox);
        this.getChildren().add(hud);
    }

    public void setEventHandler(EventHandler<KeyEvent> e) {
        canvas.setOnKeyPressed(e);
        canvas.requestFocus();
    }

    private void swapToMainMenu(ActionEvent event) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("fxml/menu.fxml"));
            Parent menuRoot = menuLoader.load();
            View.getStage(event).setScene(new Scene(menuRoot, 640, 480));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(NestorRunner nestorRunner) {
            wipeGC();
            for (Entity entity : nestorRunner.getEntities()) {
                draw(
                        entity.type().toString(),
                        entity.getX(), entity.getY(),
                        entity.getWidth(), entity.getHeight()
                );
            }
            draw("Nestor",
                    nestorRunner.getNestorX(), nestorRunner.getNestorY(),
                    Nestor.WIDTH, Nestor.HEIGHT);
            drawScore(nestorRunner.getScore());
    }

    public void wipeGC() {
        gc.setFill(Color.rgb(0, 0, 0, 0));
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.fillRect(0, 0, getWidth(), getHeight());
    }

    public void drawScore(int score) {
        scoreField.setText(String.format("%04d", score));
    }

    /**
     * @param spriteName the name of the Sprite in resources (including file extension).
     */
    public void draw(String spriteName, int xPos, int yPos, int width, int height) {
        String imgURL = "/edu/wsu/sprites/" + spriteName + ".png";
        Image img = new Image(Objects.requireNonNull(getClass().getResource(imgURL)).toString());
        gc.drawImage(img, xPos, yPos, width, height);
    }

    public Sound getSound() {
        return sound;
    }

    public void end() {
        getChildren().add(endPane);
        sound.stopBackGroundTrack();
        sound.playDeathSound();
    }
    public void again() {
        getChildren().remove(endPane);
        sound.startBackGroundTrack();
    }

    public void pause() {
        getChildren().add(pausePane);
        sound.pauseBackGroundTrack();
    }
    public void unPause() {
        getChildren().remove(pausePane);
        sound.resumeBackGroundTrack();
    }


    public FreezePane getPausePane() {
        return pausePane;
    }
    public FreezePane getEndPane() {
        return endPane;
    }
}
