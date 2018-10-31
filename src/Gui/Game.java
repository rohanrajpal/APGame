package Gui;

import Model.Block;
import Model.GameModel;
import Model.Token;
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
import java.util.Random;

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

    private Block[] blockslist;
    private Token[] tokenslist;
    private int[] blockPositions;
    Random randomPositionDecider;

    public Game(GameModel G){
        this.GameStructure=G;
        this.GameStructure.getSnake().setlength(5);
        snake=new ArrayList<>();
        initmainlayout();
        createKeyListeners();
        randomPositionDecider = new Random();
        blockPositions = new int[]{40, 120, 200, 280, 360};
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
        createBlocks();
        createTokens();

    }

    private void createTokens() {

    }

    private void setNewElementsPosition(Token token) {
        token.setLayoutY(80);
        token.setLayoutX(blockPositions[randomPositionDecider.nextInt(blockPositions.length-1)+1]);
    }

    private void createBlocks() {
        int k=(randomPositionDecider.nextInt(4))+1;
        blockslist = new Block[(int)k];

        for (int i=0;i<blockslist.length;i++){
            int value = randomPositionDecider.nextInt(50);
            blockslist[i] = new Block(Integer.toString(value));
            setNewElementsPosition(blockslist[i]);
            rootLayout.getChildren().add(blockslist[i]);
        }

        k=(randomPositionDecider.nextInt(4))+1;
        tokenslist = new Token[k];

        for (int i=0;i<tokenslist.length;i++){
            int option = randomPositionDecider.nextInt(4)+1;
            int value = randomPositionDecider.nextInt(5)+1;

            if (option == 1){
                tokenslist[i] = new Token(Integer.toString(value),option);
            }
            else{
                tokenslist[i] = new Token("",option);
            }

            setNewElementsPosition(tokenslist[i]);
            rootLayout.getChildren().add(tokenslist[i]);
        }
    }

    private void setNewElementsPosition(Block block) {
        block.setLayoutX(blockPositions[randomPositionDecider.nextInt(blockPositions.length-1)+1]);
        block.setLayoutY(0);
    }

    private void createGameLoop() {
        TimerGame = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveSnake();
                moveBlocks();
                relocateblocksbelowscreen();
                movePowerUps();
            }
        };
        TimerGame.start();
    }

    private void movePowerUps() {
        for (int i=0;i<tokenslist.length;i++){
            tokenslist[i].setLayoutY(tokenslist[i].getLayoutY()+3.5);
        }
    }

    private void relocateblocksbelowscreen() {
        for (int i=0;i<blockslist.length;i++){
            if (blockslist[i].getLayoutY()>550){
                setNewElementsPosition(blockslist[i]);
            }
        }
        for (int i=0;i< tokenslist.length;i++){
            if (tokenslist[i].getLayoutY()>550){
                setNewElementsPosition(tokenslist[i]);
            }
        }
    }

    private void moveBlocks() {
        for (int i=0;i<blockslist.length;i++){
            blockslist[i].setLayoutY(blockslist[i].getLayoutY()+3.5);
        }
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
            if(snake.get(0).getLayoutX()<gameScene.getWidth()-55) {
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
            snake.get(i).setLayoutX(168);
            snake.get(i).setLayoutY(375+(i*20));
            rootLayout.getChildren().add(snake.get(i));
        }
    }
}
