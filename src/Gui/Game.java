package Gui;

import Model.*;
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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private static final int GAME_WIDTH =400 ;
    private static final int GAME_HEIGHT = 600;

    private final static int SNAKEHEAD_RADIUS=20;
    private final static int BLOCK_RADIUS=40;
    private final static int TOKEN_RADIUS=10;

    private ScoreLabel scoreLabelText;
    private int points=0;

    private Pane rootLayout;
    private Scene gameScene;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private ArrayList<ImageView> snake;
    private GameModel GameStructure;
    private AnimationTimer TimerGame;
    private static int pressed=0;
    private Block[] blockslist;
    private Wallswrapper[] walllist;
    private Token[] tokenslist;
    private int[] blockPositions;
    private int[] wallPositions;
    Random randomPositionDecider;

    public Game(GameModel G){
        this.GameStructure=G;
        this.GameStructure.getSnake().setlength(5);
        snake=new ArrayList<>();
        initmainlayout();
        createKeyListeners();
        randomPositionDecider = new Random();
        blockPositions = new int[]{0,80,160,240,320};
        wallPositions=new int[]{80-5,160-5,240-5,320-5};
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
        createwall();
        createscoreLabelText();
    }

    private void createscoreLabelText() {
        scoreLabelText = new ScoreLabel("Score : 00");
        scoreLabelText.setLayoutY(20);
        scoreLabelText.setLayoutX(20);
        rootLayout.getChildren().add(scoreLabelText);
    }


    private void setNewElementsPosition(Token token) {
        token.setLayoutY(0);
        token.setLayoutX(blockPositions[randomPositionDecider.nextInt(blockPositions.length)]);
    }
    private void setNewElementsPosition(Block block) {
        block.setLayoutX(blockPositions[randomPositionDecider.nextInt(blockPositions.length)]);
        block.setLayoutY(0);
    }
    private void setNewElementsPosition(Wallswrapper Wall) {
        int rnd=wallPositions[randomPositionDecider.nextInt(wallPositions.length)];
        for(int i=0;i<Wall.getLength();i++) {
            Wall.getWalls().get(i).setLayoutX(rnd);
            Wall.getWalls().get(i).setLayoutY((63*(i)));
        }
    }
    private void createwall(){
        int k=(randomPositionDecider.nextInt(4))+1;
        walllist = new Wallswrapper[(int)k];
        for (int i=0;i<walllist.length;i++){
            int value = randomPositionDecider.nextInt(3)+1;
            walllist[i] = new Wallswrapper(value);
            setNewElementsPosition(walllist[i]);
            for(int j=0;j<walllist[i].getLength();j++) {
                rootLayout.getChildren().add(walllist[i].getWalls().get(j));
            }
        }
    }

    private void createBlocks() {

        int k=(randomPositionDecider.nextInt(4))+1;
        blockslist=new Block[(int)k];

        for (int i=0;i<blockslist.length;i++){
            int value = randomPositionDecider.nextInt(5)+1;
            blockslist[i] = new Block(Integer.toString(value));
            setNewElementsPosition(blockslist[i]);
            rootLayout.getChildren().add(blockslist[i]);
        }


    }
    private void createTokens(){
        int k=(randomPositionDecider.nextInt(4))+1;
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


    private void createGameLoop() {
        TimerGame = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveSnake();
                moveBlocks();
                moveWalls();
                relocateelementsbelowscreen();
                movePowerUps();
                checkIfElementsCollide();
            }
        };
        TimerGame.start();
    }

    private void moveWalls() {
        for(int j=0;j<walllist.length;j++) {
            for (int i = 0; i < walllist[j].getLength(); i++) {
                walllist[j].getWalls().get(i).setLayoutY(walllist[j].getWalls().get(i).getLayoutY() + 3.5);
            }
        }
    }

    private void movePowerUps() {
        for (int i=0;i<tokenslist.length;i++){
            tokenslist[i].setLayoutY(tokenslist[i].getLayoutY()+3.5);
        }
    }
    private void moveBlocks() {
        for (int i=0;i<blockslist.length;i++){
            blockslist[i].setLayoutY(blockslist[i].getLayoutY()+3.5);
        }
    }
    private void relocateelementsbelowscreen() {
            for(int j=0;j<blockslist.length;j++) {
                if (blockslist[j].getLayoutY() > 550) {
                    for (int i = 0; i < blockslist.length; i++) {
                        rootLayout.getChildren().remove(blockslist[i]);
                    }
                    createBlocks();
                    break;
                }
            }
        for(int j=0;j<tokenslist.length;j++) {
            if (tokenslist[0].getLayoutY() > 550) {
                for (int i = 0; i < tokenslist.length; i++) {
                    rootLayout.getChildren().remove(tokenslist[i]);
                }
                createTokens();
                break;
            }
        }
             for(int k=0;k<walllist.length;k++){
                if (walllist[k].getWalls().get(0).getLayoutY()>550) {
                    for (int i = 0; i < walllist[k].getLength(); i++) {
                            rootLayout.getChildren().remove(walllist[k].getWalls().get(i));
                    }
                    int value = randomPositionDecider.nextInt(3)+1;
                    walllist[k] = new Wallswrapper(value);
                    setNewElementsPosition(walllist[k]);
                    for(int j=0;j<walllist[k].getLength();j++) {
                        rootLayout.getChildren().add(walllist[k].getWalls().get(j));
                    }
                }
             }



    }

    private void moveSnake(){
//
        if (isLeftKeyPressed && !isRightKeyPressed){

            if(snake.get(0).getLayoutX()>20) {

                for (int i = 0; i < snake.size(); i++) {

                    ImageView p = snake.get(i);
                    final TranslateTransition transition = new TranslateTransition(Duration.millis((i + 1) * (100)), p);
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
//                for (int i = 0; i < GameStructure.getSnake().getlength(); i++) {
                    for (int i = 0; i < snake.size(); i++) {
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
            snake.get(i).setLayoutY(375+(i*33));
            rootLayout.getChildren().add(snake.get(i));
        }
    }

    private void checkIfElementsCollide(){
        for (int i=0;i<blockslist.length;i++){
            if (SNAKEHEAD_RADIUS+BLOCK_RADIUS >calcculateDistance(snake.get(0).getLayoutX() + 20
                    ,blockslist[i].getLayoutX()+blockslist[i].getPrefWidth()/2,
                    snake.get(0).getLayoutY()+ 20,
                    blockslist[i].getLayoutY()+ blockslist[i].getPrefHeight()/2))
            {
                setNewElementsPosition(blockslist[i]);
                points+=blockslist[i].getValue();
                System.out.println(blockslist[i].getValue());

                for (int r=0;r<blockslist[i].getValue();r++){
                    if (snake.size()!=1){
                        rootLayout.getChildren().remove(snake.remove(snake.size()-1));
                    }
                }
                String newScore =  "Score: ";
                scoreLabelText.setText(newScore+points);

            }
        }

        for (int j=0;j<tokenslist.length;j++){
            if (SNAKEHEAD_RADIUS+TOKEN_RADIUS > calcculateDistance(snake.get(0).getLayoutX() + 20,
                    tokenslist[j].getLayoutX()+tokenslist[j].getPrefWidth()/2,
                    snake.get(0).getLayoutY()+ 20,
                    tokenslist[j].getLayoutY()+tokenslist[j].getPrefHeight()/2)) {
                setNewElementsPosition(tokenslist[j]);

                if (tokenslist[j].getOption() == 1) {
                    int lenToInc = tokenslist[j].getValue();

                    for (int k = 0; k < lenToInc; k++) {
                            double toSetX = snake.get(snake.size() - 1).getLayoutX();
                            double toSetY = snake.get(snake.size() - 1).getLayoutY() + 33;
                            snake.add(new ImageView("/view/snake_tail.png"));
                            snake.get(snake.size() - 1).setLayoutX(toSetX);
                            snake.get(snake.size() - 1).setLayoutY(toSetY);
                            rootLayout.getChildren().add(snake.get(snake.size() - 1));
                    }
                }

            }
        }

    }

    private double calcculateDistance(double x1,double x2,double y1,double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
}
