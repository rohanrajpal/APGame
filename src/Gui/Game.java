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

    private boolean facesWallleft= false;
    private boolean facesWallright=false;
    private int[] wallPositions;
    Random randomPositionDecider;
    GameSubScene subGameScene;

    private boolean isGameRunning;

    public Game(GameModel G,ArrayList<Block> bl,ArrayList<Token> tk,ArrayList<Wallswrapper> wa,LeaderBoardModel le){
        this.templeaderboard=le;
        this.GameStructure=G;
        snake=new ArrayList<>();
        initmainlayout();
        createKeyListeners();
        blockslist=bl;
        tokenslist=tk;
        walllist=wa;
        points=GameStructure.getPoints();
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
        rootLayout.getChildren().removeAll();
//        for(int i=0;i<blockslist.size();i++) {
//            if (blockslist.get(i).getLayoutY() > 600 || blockslist.get(i).getLayoutY() < -50) {
//                    blockslist.remove(i);
//            }
//        }
//        for(int i=0;i<tokenslist.size();i++){
//            if(tokenslist.get(i).getLayoutY()<600 || tokenslist.get(i).getLayoutY()>-50){
//                tokenslist.remove(i);
//            }
//        }
//        for(int i=0;i<walllist.size();i++){
//            for (int j=0;j<walllist.get(i).getLength();j++) {
//                if (walllist.get(i).getWalls().get(j).getLayoutY() < 600 || walllist.get(i).getWalls().get(j).getLayoutY() > -50) {
//                    walllist.get(i).getWalls().remove(j);
//                }
//            }
//        }
        this.GameStructure.setBlockslist(this.blockslist);
        this.GameStructure.setTokenslist(this.tokenslist);
        this.GameStructure.setWalllist(this.walllist);
        this.GameStructure.setSnake(new SnakeModel(this.snake.size(),168,375));
        this.GameStructure.setPoints(this.points);
        imgResume.setLe(Totalscore,this.GameStructure);
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

            gameScene=new Scene(rootLayout,400,600);
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
    public void creatExistingGame(){
        createSnake();
        createGameLoop();
        createExistingBlocks();
        createExistingTokens();
        createExistingwall();
        createscoreLabelText();
    }
    public void createExistingBlocks(){
        for(int i=0;i<blockslist.size();i++) {
            if (blockslist.get(i).getLayoutY() < 600 || blockslist.get(i).getLayoutY() > -50) {
                rootLayout.getChildren().add(blockslist.get(i));
            }
        }
        createBlocks();
    }
    public void createExistingTokens(){
        for(int i=0;i<tokenslist.size();i++){
            if(tokenslist.get(i).getLayoutY()<600 || tokenslist.get(i).getLayoutY()>-50){
                rootLayout.getChildren().add(tokenslist.get(i));
            }
        }

            createTokens();

    }
    public void createExistingwall(){


            for(int i=0;i<walllist.size();i++){
                for (int j=0;j<walllist.get(i).getLength();j++) {
                    if (walllist.get(i).getWalls().get(j).getLayoutY() < 600 || walllist.get(i).getWalls().get(j).getLayoutY() > -50) {
                        rootLayout.getChildren().add(walllist.get(i).getWalls().get(j));
                    }
                }
            }



        createwall();

    }

    private void createscoreLabelText() {

        scoreLabelText = new ScoreLabel("Score : "+this.points);
        scoreLabelText.setLayoutY(20);
        scoreLabelText.setLayoutX(20);
        rootLayout.getChildren().add(scoreLabelText);
    }


    private void setNewElementsPosition(Token token) {
        token.setLayoutX(TokenPositions.get(randomPositionDecider.nextInt(TokenPositions.size())));
        token.setLayoutY(-100);
        token.setY(token.getLayoutY());
        token.setX(token.getLayoutX());
    }
    private void setNewElementsPosition(Block block) {
        block.setLayoutX(blockPositions.get(randomPositionDecider.nextInt(blockPositions.size())));
        block.setLayoutY(-100);
        block.setY(block.getLayoutY());
        block.setX(block.getLayoutX());
    }
    private void setNewElementsPosition(Wallswrapper Wall) {
        int rnd=wallPositions[randomPositionDecider.nextInt(wallPositions.length)];
        for(int i=0;i<Wall.getLength();i++) {
            Wall.getWalls().get(i).setLayoutX(rnd);
            Wall.getWalls().get(i).setLayoutY(-250+(63*(i)));
            Wall.getWalls().get(i).setY(Wall.getWalls().get(i).getLayoutY());
            Wall.getWalls().get(i).setX(Wall.getWalls().get(i).getLayoutX());
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
                    tokenslist.add(new Ball(Integer.toString(value)));
                }
                else if(option==2){
                    tokenslist.add(new Bomb(""));
                }
                else if(option==3){
                    tokenslist.add(new Shield(""));
                }
                else if(option==4){
                    tokenslist.add(new Magnet(""));
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
                walllist.get(j).getWalls().get(i).setY(walllist.get(j).getWalls().get(i).getLayoutY());
            }
        }
    }

    private void movePowerUps() {
        for (int i=0;i<tokenslist.size();i++){
            tokenslist.get(i).setLayoutY(tokenslist.get(i).getLayoutY()+5);
            tokenslist.get(i).setY(tokenslist.get(i).getLayoutY());
        }
    }
    private void moveBlocks() {
        for (int i=0;i<blockslist.size();i++){
            blockslist.get(i).setLayoutY(blockslist.get(i).getLayoutY()+5);
            blockslist.get(i).setY( blockslist.get(i).getLayoutY());
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
    private void moveLeft(){
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
                GameStructure.getSnake().setX(this.snake.get(0).getLayoutX());
                //snake.get(i).setLayoutX(snake.get(i).getLayoutX() - 3);

            }
        }
    }
    private void moveRight(){
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
                GameStructure.getSnake().setX(this.snake.get(0).getLayoutX());
            }//snake.get(i).setLayoutX(snake.get(i).getLayoutX() + 3);
        }
    }
    private void moveSnake(){
//
        if (isLeftKeyPressed && !isRightKeyPressed){
            if (facesWallleft){
               facesWallleft = false;
//                moveRight();
            }
            else{
                moveLeft();
            }
        }

        if (isRightKeyPressed && !isLeftKeyPressed) {
            if (facesWallright){
                facesWallright=false;
//                moveLeft();
            }
            else{
                moveRight();
            }
        }

    }

    private void createSnake() {
        for(int i=0;i<GameStructure.getSnake().getlength();i++) {
            ImageView img = new ImageView("/view/snake_tail.png");
            img.setFitHeight(25);
            img.setFitWidth(25);
            snake.add(img);
            snake.get(i).setLayoutX(GameStructure.getSnake().getX());
            snake.get(i).setLayoutY(GameStructure.getSnake().getY()+(i*25));
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

            ImageView snakeLocalHead = snake.get(0);
//            if (tokenslist.get(j).getBoundsInParent().intersects(snakeLocalHead.getBoundsInParent())){
//                System.out.println("simple?");
//            }
            if (SNAKEHEAD_RADIUS+TOKEN_RADIUS > calcculateDistance(snake.get(0).getLayoutX() + 20,
                    tokenslist.get(j).getLayoutX()+tokenslist.get(j).getPrefWidth()/2,
                    snake.get(0).getLayoutY()+ 20,
                    tokenslist.get(j).getLayoutY()+tokenslist.get(j).getPrefHeight()/2)) {

//                if (snake.get(0).intersects(tokenslist.get(j).getLayoutX(),
//                        tokenslist.get(j).getLayoutY(),tokenslist.get(j).getPrefWidth(),
//                        tokenslist.get(j).getPrefHeight())){
//                    System.out.println("same");
//                }
//                ImageView snakeLocalHead = snake.get(0);
//                if (tokenslist.get(j).intersects(snakeLocalHead.getBoundsInLocal())){
//                    System.out.println("simple?");
//                }
                setNewElementsPosition(tokenslist.get(j));

                if (tokenslist.get(j).getClass() == (new Ball()).getClass()) {
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

        for (int i=0;i<walllist.size();i++){
                int len = walllist.get(i).getWalls().size();

//            System.out.println(x.getBoundsInLocal());
            for (int j=0;j<len;j++){
                wall x = walllist.get(i).getWalls().get(j);
                if (snake.get(0).getBoundsInParent().intersects(x.getBoundsInParent())){
                    if(snake.get(0).getLayoutX()-x.getLayoutX()>=-5) {
                        facesWallleft = true;
                    }
                    else{
                        facesWallright = true;
                    }
                }
            }

        }



    }

    private double calcculateDistance(double x1,double x2,double y1,double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
}
