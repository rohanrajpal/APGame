package Gui;

import Model.mousecordinates;
import controller.ControllerGame;
import controller.ControllerLeaderboard;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Game {
    private static final int GAME_WIDTH =400 ;
    private static final int GAME_HEIGHT = 600;
    private Pane rootLayout;
    private Scene gameScene;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private ImageView snake;
    
    private AnimationTimer TimerGame;

    public Game(){
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
//        System.out.println(angle);
        if (isLeftKeyPressed && !isRightKeyPressed){

            if (snake.getLayoutX() > 0){
                snake.setLayoutX(snake.getLayoutX() - 3);
            }
        }

        if (isRightKeyPressed && !isLeftKeyPressed){

            if (snake.getLayoutX()<369){
                snake.setLayoutX(snake.getLayoutX()+3);
            }
        }

    }

    private void createSnake() {
        snake = new ImageView("/view/snake_tail.png");
        snake.setLayoutX(178);
        snake.setLayoutY(315);

        rootLayout.getChildren().add(snake);
    }
}
