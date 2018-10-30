package Gui;

import Model.GameModel;
import Model.mousecordinates;
import controller.ControllerGame;
import controller.ControllerLeaderboard;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private static final int GAME_WIDTH =400 ;
    private static final int GAME_HEIGHT = 600;
    private Pane rootLayout;
    private Scene gameScene;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private ArrayList<ImageView> snake;
    private GameModel GameStructure;
    private AnimationTimer TimerGame;

    public Game(GameModel G){
        this.GameStructure=G;
        this.GameStructure.getSnake().setlength(5);
        snake=new ArrayList<>();
        initmainlayout();
        createKeyListeners();

    }
    private void initmainlayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartPage.class.getResource("../view/GAMELAYOUT.fxml"));
            rootLayout =  loader.load();
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mousecordinates.xOffset = event.getSceneX();
                    mousecordinates.yOffset = event.getSceneY();
                }
            });
            ControllerGame controller = loader.getController();
            //controller.leaderboard();
            // Show the scene containing the root layout.
            gameScene=new Scene(rootLayout);
            gameScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT){
                    isLeftKeyPressed = true;
                }else if (event.getCode() == KeyCode.RIGHT){
                    isRightKeyPressed = true;
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT){
                    isLeftKeyPressed = false;
                }else if (event.getCode() == KeyCode.RIGHT){
                    isRightKeyPressed = false;
                }
            }
        });
    }
    public void show(Stage primaryStage){
        rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - mousecordinates.xOffset);
                primaryStage.setY(event.getScreenY() - mousecordinates.yOffset);
            }
        });
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public void createNewGame() {
        createSnake();
        createGameLoop();
    }

    private void createGameLoop() {
        TimerGame = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveSnake();
            }
        };
        TimerGame.start();
    }

    private void moveSnake(){
//
        if (isLeftKeyPressed && !isRightKeyPressed){
            if(snake.get(0).getLayoutX()>20) {
                for (int i = 0; i < GameStructure.getSnake().getlength(); i++) {

                    ImageView p = snake.get(i);
                    final TranslateTransition transition = new TranslateTransition(Duration.millis((i + 1) * (100)), p);
                    if (p.getLayoutX() == 33) {

                        transition.stop();
                    }
                    transition.setFromX(p.getTranslateX());
                    transition.setFromY(p.getTranslateY());
                    transition.setToX(p.getTranslateX());
                    transition.setToY(p.getTranslateY());
                    transition.playFromStart();
                    transition.setOnFinished(t -> {
                        p.setLayoutX(p.getLayoutX() - (3));
                        p.setLayoutY(p.getLayoutY());
                    });


                    //snake.get(i).setLayoutX(snake.get(i).getLayoutX() - 3);

                }
            }
        }

        if (isRightKeyPressed && !isLeftKeyPressed) {
            if(snake.get(0).getLayoutX()<349) {
                for (int i = 0; i < GameStructure.getSnake().getlength(); i++) {
                    ImageView p = snake.get(i);
                    final TranslateTransition transition = new TranslateTransition(Duration.millis((i + 1) * (100)), p);
                    transition.setFromX(p.getTranslateX());
                    transition.setFromY(p.getTranslateY());
                    transition.setToX(p.getTranslateX());
                    transition.setToY(p.getTranslateY());
                    transition.playFromStart();
                    transition.setOnFinished(t -> {
                        p.setLayoutX(p.getLayoutX() + 3);
                        p.setLayoutY(p.getLayoutY());
                    });
                }//snake.get(i).setLayoutX(snake.get(i).getLayoutX() + 3);
            }
        }

    }




    private void createSnake() {
        for(int i=0;i<GameStructure.getSnake().getlength();i++) {
            snake.add(new ImageView("/view/snake_tail.png"));
            snake.get(i).setLayoutX(178);
            snake.get(i).setLayoutY(315+(i*20));
            rootLayout.getChildren().add(snake.get(i));
        }
    }
}
