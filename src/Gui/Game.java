package Gui;

import Model.*;
import controller.ControllerGame;
import controller.ControllerLeaderboard;
import controller.ControllerMainMenu;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import view.ImageButton;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private static final int GAME_WIDTH =400 ;
    private static final int GAME_HEIGHT = 600;

    private final static int SNAKEHEAD_RADIUS=20;
    private final static int BLOCK_RADIUS=40;
    private final static int TOKEN_RADIUS=10;

    private ScoreLabel scoreLabelText;
    private int Totalscore=0;
    private int points=0;
    private Pane rootLayout;
    private Scene gameScene;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private ArrayList<ImageView> snake;
    private GameModel GameStructure;
    private AnimationTimer TimerGame;
    private static int pressed=0;
    private ArrayList<Block> blockslist;
    private ArrayList<Wallswrapper> walllist;
    private ArrayList<Token> tokenslist;
    private ArrayList<Integer> blockPositions;
    private ArrayList<Integer> TokenPositions;
    private LeaderBoardModel templeaderboard;
    private int[] wallPositions;
    Random randomPositionDecider;
    GameSubScene subGameScene;

    private boolean isGameRunning;

    public Game(GameModel G,ArrayList<Block> bl,ArrayList<Token> tk,ArrayList<Wallswrapper> wa,LeaderBoardModel le){
        this.templeaderboard=le;
        this.GameStructure=G;
        this.GameStructure.getSnake().setlength(5);
        snake=new ArrayList<>();
        initmainlayout();
        createKeyListeners();
        blockslist=bl;
        tokenslist=tk;
        walllist=wa;
        randomPositionDecider = new Random();
        blockPositions = new ArrayList<>(5);
        TokenPositions= new ArrayList<>(8);
        blockPositions.add(0);
        blockPositions.add(80);
        blockPositions.add(160);
        blockPositions.add(240);
        blockPositions.add(320);
        TokenPositions.add(0);
        TokenPositions.add(50);
        TokenPositions.add(100);
        TokenPositions.add(150);
        TokenPositions.add(200);
        TokenPositions.add(250);
        TokenPositions.add(300);
        TokenPositions.add(350);
        wallPositions=new int[]{80-5,160-5,240-5,320-5};

        isGameRunning = true;

        makepausebutton();
    }

    private void makepausebutton() {
        ImageButton ib=new ImageButton("/view/Helper_images/pause.png");
        ib.setLayoutX(313);
        ib.setLayoutY(20);
        ib.setInterim("pause");
        ib.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                isGameRunning = false;
                if (isGameRunning){
                    isGameRunning = false;
                }
                else{
                    isGameRunning = true;
                }
                openPauseMenu();
            }
        });
        rootLayout.getChildren().add(ib);
    }

    private void openPauseMenu() {
        subGameScene = new GameSubScene();
        rootLayout.getChildren().add(subGameScene);

        subGameScene.getPane().getChildren().add(createResumeButton());

        subGameScene.getPane().getChildren().add(createGoBacktoMenuButton());
//        subGameScene;
    }

    private Button createGoBacktoMenuButton() {
        ImageButton imgResume = new ImageButton("../view/Helper_images/error.png");
        imgResume.setLayoutX(110);
        imgResume.setLayoutY(170);
        imgResume.setInterim("StartPage");
        imgResume.setLe(Totalscore);
//        imgResume.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                isGameRunning = true;
//                rootLayout.getChildren().remove(subGameScene);
//            }
//        });
        return imgResume;
    }

    private Button createResumeButton() {
        ImageButton imgResume = new ImageButton("../view/Helper_images/resume-button.png");
        imgResume.setLayoutX(110);
        imgResume.setLayoutY(70);

        imgResume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                isGameRunning = true;
                rootLayout.getChildren().remove(subGameScene);
            }
        });
        return imgResume;
    }

    private void initmainlayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartPage.class.getResource("../view/GAMELAYOUT.fxml"));
            rootLayout =  loader.load();
            ControllerGame controller = loader.getController();
            controller.init();
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mousecordinates.xOffset = event.getSceneX();
                    mousecordinates.yOffset = event.getSceneY();
                }
            });
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
        token.setLayoutX(TokenPositions.get(randomPositionDecider.nextInt(TokenPositions.size())));
        token.setLayoutY(-100);
    }
    private void setNewElementsPosition(Block block) {
        block.setLayoutX(blockPositions.get(randomPositionDecider.nextInt(blockPositions.size())));
        block.setLayoutY(-100);
    }
    private void setNewElementsPosition(Wallswrapper Wall) {
        int rnd=wallPositions[randomPositionDecider.nextInt(wallPositions.length)];
        for(int i=0;i<Wall.getLength();i++) {
            Wall.getWalls().get(i).setLayoutX(rnd);
            Wall.getWalls().get(i).setLayoutY(-250+(63*(i)));
        }
    }
    private void createwall() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(6), ev -> {
            int k = (randomPositionDecider.nextInt(4)) + 1;
            k+=walllist.size();
            for (int i = walllist.size(); i < k; i++) {
                int value = randomPositionDecider.nextInt(3) + 1;
                walllist.add(new Wallswrapper(value));
                setNewElementsPosition(walllist.get(walllist.size()-1));
                rootLayout.getChildren().addAll(walllist.get(walllist.size()-1).getWalls());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void createBlocks() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
            int k=(randomPositionDecider.nextInt(4))+1;
            k+=blockslist.size();
            for (int i=blockslist.size();i<k;i++){
                int value = randomPositionDecider.nextInt(5)+1;
                blockslist.add(new Block(Integer.toString(value)));
                setNewElementsPosition(blockslist.get(blockslist.size()-1));
                rootLayout.getChildren().add(blockslist.get(blockslist.size()-1));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }
    private void createTokens(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(8), ev -> {
            int k=(randomPositionDecider.nextInt(4))+1;
            k+=tokenslist.size();
            for (int i=tokenslist.size();i<k;i++){
                int option = randomPositionDecider.nextInt(4)+1;
                int value = randomPositionDecider.nextInt(5)+1;
                if (option == 1){
                    tokenslist.add(new Token(Integer.toString(value),option));
                }
                else{
                    tokenslist.add(new Token("",option));
                }
                setNewElementsPosition(tokenslist.get(tokenslist.size()-1));
                rootLayout.getChildren().add(tokenslist.get(tokenslist.size()-1));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    private void createGameLoop() {
        AnimationTimerExt TimerGame = new AnimationTimerExt(20) {
            @Override
            public void handle() {
                if (isGameRunning) {

                    moveBlocks();
                    moveWalls();
                    relocateelementsbelowscreen();
                    movePowerUps();
                    checkIfElementsCollide();
                    moveSnake();
                    checkhighscore();
                }
            }
        };
        TimerGame.start();
    }

    private void checkhighscore() {
        if(this.templeaderboard.getLeaders().size()>=10) {
            if (Totalscore > this.templeaderboard.getLeaders().get(this.templeaderboard.getLeaders().size() - 1).getscore()) {
                this.templeaderboard.getLeaders().remove(this.templeaderboard.getLeaders().size() - 1);
                this.templeaderboard.getLeaders().add(new LeaderBoardelements(Totalscore, new Date()));
                java.util.Collections.sort(this.templeaderboard.getLeaders());
            }
        }
        try {
            LeaderBoardModel.serialize(this.templeaderboard);
        }
        catch (Exception e){

            System.out.println(e.getMessage());
        }

    }

    private void moveWalls() {
        for(int j=0;j<walllist.size();j++) {
            for (int i = 0; i < walllist.get(j).getLength(); i++) {
                walllist.get(j).getWalls().get(i).setLayoutY(walllist.get(j).getWalls().get(i).getLayoutY() +5);
            }
        }
    }

    private void movePowerUps() {
        for (int i=0;i<tokenslist.size();i++){
            tokenslist.get(i).setLayoutY(tokenslist.get(i).getLayoutY()+5);
        }
    }
    private void moveBlocks() {
        for (int i=0;i<blockslist.size();i++){
            blockslist.get(i).setLayoutY(blockslist.get(i).getLayoutY()+5);
        }
    }
    private void relocateelementsbelowscreen() {
            for(int j=0;j<blockslist.size();j++) {
                if (blockslist.get(j).getLayoutY() > 600) {
                        rootLayout.getChildren().remove(blockslist.get(j));
                }
            }
        for(int j=0;j<tokenslist.size();j++) {
            if (tokenslist.get(j).getLayoutY() > 600) {

                rootLayout.getChildren().remove(tokenslist.get(j));


            }
        }
             for(int k=0;k<walllist.size();k++) {
                 for (int j = 0; j < walllist.get(k).getLength(); j++) {
                     if (walllist.get(k).getWalls().get(j).getLayoutY() > 600) {

                             rootLayout.getChildren().remove(walllist.get(k).getWalls().get(j));



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
                    checkIfElementsCollide();
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
                    checkIfElementsCollide();
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
            ImageView img = new ImageView("/view/snake_tail.png");
            img.setFitHeight(25);
            img.setFitWidth(25);
            snake.add(img);
            snake.get(i).setLayoutX(168);
            snake.get(i).setLayoutY(375+(i*25));
            rootLayout.getChildren().add(snake.get(i));
        }
    }

    private void checkIfElementsCollide(){
        for (int i=0;i<blockslist.size();i++){
            if (SNAKEHEAD_RADIUS+BLOCK_RADIUS >calcculateDistance(snake.get(0).getLayoutX() + 20
                    ,blockslist.get(i).getLayoutX()+blockslist.get(i).getPrefWidth()/2,
                    snake.get(0).getLayoutY()+ 20,
                    blockslist.get(i).getLayoutY()+ blockslist.get(i).getPrefHeight()/2))
            {
                for (int r=0;r<blockslist.get(i).getValue();r++){
                    if (snake.size()!=1){
                        rootLayout.getChildren().remove(snake.remove(snake.size()-1));
                    }
                }
                points+=blockslist.get(i).getValue();
                Totalscore+=points;
                rootLayout.getChildren().remove(blockslist.get(i));
                blockslist.remove(blockslist.get(i));
                String newScore =  "Score: ";
                scoreLabelText.setText(newScore+points);
                break;
            }
        }

        for (int j=0;j<tokenslist.size();j++){
            if (SNAKEHEAD_RADIUS+TOKEN_RADIUS > calcculateDistance(snake.get(0).getLayoutX() + 20,
                    tokenslist.get(j).getLayoutX()+tokenslist.get(j).getPrefWidth()/2,
                    snake.get(0).getLayoutY()+ 20,
                    tokenslist.get(j).getLayoutY()+tokenslist.get(j).getPrefHeight()/2)) {
                setNewElementsPosition(tokenslist.get(j));

                if (tokenslist.get(j).getOption() == 1) {
                    int lenToInc = tokenslist.get(j).getValue();

                    for (int k = 0; k < lenToInc; k++) {
                            double toSetX = snake.get(snake.size() - 1).getLayoutX();
                            double toSetY = snake.get(snake.size() - 1).getLayoutY() + 25;
                            ImageView img = new ImageView("/view/snake_tail.png");
                            img.setFitHeight(25);
                            img.setFitWidth(25);
                            snake.add(img);
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
