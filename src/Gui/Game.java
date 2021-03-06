package Gui;

import Model.*;
import controller.ControllerGame;
import controller.ControllerLeaderboard;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import view.ImageButton;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 *
 */
public class Game {
    private static final int GAME_WIDTH =400 ;
    private static final int GAME_HEIGHT = 600;
    private  String filename="src\\sound\\choose_option.mp3";
    private  Media media = new Media(Paths.get(filename).toUri().toString());
    private  MediaPlayer player = new MediaPlayer(media);
    private String filename1="src\\sound\\blast.mp3";
    private Media media1 = new Media(Paths.get(filename1).toUri().toString());
    private  MediaPlayer blast = new MediaPlayer(media1);
    private String filename2="src\\sound\\coin.mp3";
    private  Media media2 = new Media(Paths.get(filename2).toUri().toString());
    private  MediaPlayer coin = new MediaPlayer(media2);
    private  String filename3="src\\sound\\blockhit.mp3";
    private  Media media3 = new Media(Paths.get(filename3).toUri().toString());
    private final MediaPlayer blockhit = new MediaPlayer(media3);
    private final static int SNAKEHEAD_RADIUS=20;
    private final static int BLOCK_RADIUS=40;
    private static int TOKEN_RADIUS=10;
    private boolean keyreleased;
    private ScoreLabel scoreLabelText;
    private int Totalscore=0;
    private int points;
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

    private Timer timer;
    private Timeline timeline;

    private boolean facesWallleft= false;
    private boolean facesWallright=false;
    private boolean isMagnetOn =false;
    private int[] wallPositions;
    private Random randomPositionDecider;
    private GameSubScene subGameScene;
    private boolean highscoredetected;
    private boolean isGameRunning;
    private double downSpeed= 8;
    private double snakeSpeed=5;
    private boolean isShieldOn = false;
    private SnakeLengthLabel snakeLen;
    private ScoreLabel magLabel;
    private ScoreLabel shieldLabel;
    private boolean isDarkMode;
    private ArrayList<Double> snakecor;

    /**
     *The initial consutructor which when called creates necessary objects and calls
     * the required functions.
     * Default token positions and listeners are added
     * @param G The Gamemodel class
     * @param bl    The previous deserialized blocklist
     * @param tk    The previous token list
     * @param wa    The basic arraylist of walls
     * @param le    The previous leaderboard model
     */
    public Game(GameModel G,ArrayList<Block> bl,ArrayList<Token> tk,ArrayList<Wallswrapper> wa,LeaderBoardModel le){
        isDarkMode=false;
        this.templeaderboard=le;
        this.GameStructure=G;
        snake=new ArrayList<>();
        snakecor=new ArrayList<Double>();
        initmainlayout();
        blockslist=bl;
        tokenslist=tk;
        walllist=wa;
        highscoredetected=false;
        points=GameStructure.getPoints();
        randomPositionDecider = new Random();
        blockPositions = new ArrayList<>(5);
        TokenPositions= new ArrayList<>(5);
        blockPositions.add(0);
        blockPositions.add(82);
        blockPositions.add(163);
        blockPositions.add(243);
        blockPositions.add(323);
        TokenPositions.add(40);
        TokenPositions.add(120);
        TokenPositions.add(200);
        TokenPositions.add(280);
        TokenPositions.add(360);
        wallPositions=new int[]{80-13,160-13,240-13,320-13};
        isGameRunning = true;
        makepausebutton();
    }


    /**
     * This pauses the game when you click it.
     * I have a boolean which when set to false pauses the game will pause.
     * The restart and exit button are then shown.
     */
    private void makepausebutton() {
        ImageButton ib=new ImageButton("/view/Helper_images/pause.png");
        ib.setLayoutX(313);
        ib.setLayoutY(20);
        ib.setInterim("pause");
        ib.setOnAction(event -> {
            boolean prevState = isGameRunning;
            isGameRunning = false;
//            isGameRunning = !isGameRunning;
            if (prevState)
                openPauseMenu();
        });
        rootLayout.getChildren().add(ib);
    }

    /**
     * This is called when the user will press the pause button
     */
    private void openPauseMenu() {
        subGameScene = new GameSubScene();
        rootLayout.getChildren().add(subGameScene);

        subGameScene.getPane().getChildren().add(createResumeButton());

        subGameScene.getPane().getChildren().add(createGoBacktoMenuButton());

        subGameScene.getPane().getChildren().add(createRestartButton());
//        subGameScene;
    }

    /**
     * This function is called when the pause menu is created.
     * @return Button
     */
    private Button createRestartButton() {
        ImageButton imgRestart = new ImageButton("../view/Helper_images/icon-restart.png");
        imgRestart.setLayoutX(113);
        imgRestart.setLayoutY(170);
        imgRestart.setfit(80,80);
        imgRestart.setInterim("restart");
        imgRestart.setLe(Totalscore,new GameModel(new SnakeModel(5,178,375),0,Totalscore,0));
        return imgRestart;
    }

    /**
     * The function of this button is to serialize the current
     * game and return to the main menu.
     * @return Returns the created go back to main menu button
     */
    private Button createGoBacktoMenuButton() {
        ImageButton imgexit = new ImageButton("../view/Helper_images/error.png");
        imgexit.setLayoutX(180);
        imgexit.setLayoutY(70);
        imgexit.setInterim("StartPage");
        rootLayout.getChildren().removeAll();

        this.GameStructure.setBlockslist(this.blockslist);
        this.GameStructure.setTokenslist(this.tokenslist);
        this.GameStructure.setWalllist(this.walllist);
        this.GameStructure.setSnake(new SnakeModel(this.snake.size(),168,375));
        this.GameStructure.setPoints(this.points);
        this.GameStructure.setlatestpoints(this.Totalscore);
        this.GameStructure.startpagestage=0;
        imgexit.setLe(Totalscore,this.GameStructure);
//        imgResume.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                isGameRunning = true;
//                rootLayout.getChildren().remove(subGameScene);
//            }
//        });
        return imgexit;
    }

    /**
     * The resume button will go back to the main game.
     * @return  Creates and decides layout of the resume button and then continues
     */
    private Button createResumeButton() {
        ImageButton imgResume = new ImageButton("../view/Helper_images/resume-button.png");
        imgResume.setLayoutX(50);
        imgResume.setLayoutY(70);

        imgResume.setOnAction(event -> {
            isGameRunning = true;
            rootLayout.getChildren().remove(subGameScene);
        });
        return imgResume;
    }

    /**
     * This loads the initial layout created using FXML
     */
    private void initmainlayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartPage.class.getResource("../view/GAMELAYOUT.fxml"));
            rootLayout =  loader.load();
            ControllerGame controller = loader.getController();
            controller.init();
            rootLayout.setOnMousePressed(event -> {
                mousecordinates.xOffset = event.getSceneX();
                mousecordinates.yOffset = event.getSceneY();
            });

            gameScene=new Scene(rootLayout,400,600);
            gameScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This initializes all the key listeners
     * This game also has cheat codes to activate the power ups.
     */
    AnimationTimer animate= new AnimationTimer() {
        @Override
        public void handle(long now) {
            for (int i = snake.size() - 1; i > 0; i--) {
                snakecor.set(i, snakecor.get(i - 1));
            }
            if (snake.get(0).getLayoutX() > 20) {
                snakecor.set(0, snakecor.get(0)- snakeSpeed);
            }
            for (int i = 0; i < snake.size(); i++) {
                snake.get(i).setLayoutX(snakecor.get(i));
            }
        }
    };;
    AnimationTimer  animate1 = new AnimationTimer() {
        @Override
        public void handle(long now) {
            for (int i = snake.size() - 1; i > 0; i--) {
                snakecor.set(i, snakecor.get(i - 1));
            }
            for (int i = 0; i < snake.size(); i++) {
                snake.get(i).setLayoutX(snakecor.get(i));
            }
        }
    };
    AnimationTimer  animate2 = new AnimationTimer() {
        @Override
        public void handle(long now) {
            for (int i = snake.size() - 1; i > 0; i--) {
                snakecor.set(i, snakecor.get(i - 1));
            }
            if(snake.get(0).getLayoutX()<gameScene.getWidth()-55) {
                snakecor.set(0, snakecor.get(0)+ snakeSpeed);
            }
            for (int i = 0; i < snake.size(); i++) {
                snake.get(i).setLayoutX(snakecor.get(i));
            }
        }
    };
    private void createKeyListeners() {
        gameScene.setOnKeyPressed(event -> {
            keyreleased=false;
            if (event.getCode() == KeyCode.LEFT){

                isLeftKeyPressed = true;
            }
            else if (event.getCode() == KeyCode.RIGHT){

                isRightKeyPressed = true;
            }
            //To check is Magnet is working correctly
            if (event.getCode() == KeyCode.S){
                turnOnShield();
            }
            if (event.getCode() == KeyCode.M){
                turnOnMagnet();
            }
            if (event.getCode() == KeyCode.D){
                destroyAllBlocks();
            }
            if (event.getCode() == KeyCode.I){
                incSnakeLength(1);
            }
            if (event.getCode() == KeyCode.C){
                correctSnakePostions();
            }
            if (event.getCode() == KeyCode.Q){
                darkMode();
            }
        });

        gameScene.setOnKeyReleased(event -> {
                System.out.println("released");
                animate.stop();
                animate2.stop();
                animate1.start();
            if (event.getCode() == KeyCode.LEFT){
                isLeftKeyPressed = false;
            }else if (event.getCode() == KeyCode.RIGHT){
                isRightKeyPressed = false;
            }
        });

    }

    /**
     * We have controlled on how to show the whole game by deciding how to show the primary stage.
     * @param primaryStage The main stage where the scene is added
     */
    public void show(Stage primaryStage){
        rootLayout.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - mousecordinates.xOffset);
            primaryStage.setY(event.getScreenY() - mousecordinates.yOffset);
        });
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    /**
     * calls necessary functions to create a new game.
     *
     */
    public void createNewGame() {
        createSnake();
        createGameLoop();
        createBlocks();
        //createTokens();
        createwall();
        moveBlocks();
        moveWalls();
        createscoreLabelText();
        createKeyListeners();
    }
    /**
     * Calls necessary functions to deserialize a game
     */
    public void creatExistingGame(){
        createSnake();
        createGameLoop();
        createExistingBlocks();
        // createExistingTokens();
        createExistingwall();
        moveBlocks();
        moveWalls();
        createscoreLabelText();
        createKeyListeners();
    }

    /**
     * Creates existing blocks in the same space when we deserialize.
     */
    private void createExistingBlocks(){
        for (Block aBlockslist : blockslist) {
            if (aBlockslist.getLayoutY() < 600 || aBlockslist.getLayoutY() > -50) {
                rootLayout.getChildren().add(aBlockslist);
            }
        }
        createExistingTokens();
        createBlocks();
    }

    /**
     * Creates token in the same space when we deserialize the game.
     */
    private void createExistingTokens(){
        for (Token aTokenslist : tokenslist) {
            if (aTokenslist.getLayoutY() < 600 || aTokenslist.getLayoutY() > -50) {
                rootLayout.getChildren().add(aTokenslist);
            }
        }

        //createTokens();

    }

    /**
     * Creates an existing wall when we deserialize.
     */
    private void createExistingwall(){
        for (Wallswrapper aWalllist : walllist) {
            for (int j = 0; j < aWalllist.getLength(); j++) {
                if (aWalllist.getWalls().get(j).getLayoutY() < 600 || aWalllist.getWalls().get(j).getLayoutY() > -50) {
                    rootLayout.getChildren().add(aWalllist.getWalls().get(j));
                }
            }
        }
        createwall();
    }

    /**
     * The score label created and its position is decided.
     */
    private void createscoreLabelText() {

        scoreLabelText = new ScoreLabel("Score : "+this.points);
        scoreLabelText.setLayoutY(20);
        scoreLabelText.setLayoutX(20);
        rootLayout.getChildren().add(scoreLabelText);
    }

    /**
     * Takes the position to be set and then sets the position according to it.
     * @param token The token whose position is to be update
     * @param pos   The final Y layout to be set
     */
    private void setNewElementsPosition(Token token,int pos,int k) {
        token.setLayoutX(TokenPositions.get(pos));
        token.setLayoutY(k);
        token.setY(token.getLayoutY());
        token.setX(token.getLayoutX());
    }

    /**
     * Takes the position to be set and then sets the position according to it.
     * @param block The block whose position is to be updated
     * @param pos The index of blocks to get
     */
    private void setNewElementsPosition(Block block,int pos) {
        block.setLayoutX(blockPositions.get(pos));
        block.setLayoutY(-100);
        block.setY(block.getLayoutY());
        block.setX(block.getLayoutX());
    }

    /**
     * Takes the position to be set and then sets the position according to it.
     * @param Wall The wall whose position is to be set
     * @param pos  The index to retrieve the wall from
     */
    private void setNewElementsPosition(Wallswrapper Wall,int pos) {
        int rnd=wallPositions[pos];
        for(int i=0;i<Wall.getLength();i++) {
            Wall.getWalls().get(i).setLayoutX(rnd);
            Wall.getWalls().get(i).setLayoutY(-180-(63*(i)));
            Wall.getWalls().get(i).setY(Wall.getWalls().get(i).getLayoutY());
            Wall.getWalls().get(i).setX(Wall.getWalls().get(i).getLayoutX());
        }
    }


    /**
     * Creates the wall every 6 seconds using the timeline
     */
    private void createwall() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(6), ev -> {
            if (isGameRunning) {
                int k = (randomPositionDecider.nextInt(4)) + 1;
                ArrayList<Integer> tempindex = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    tempindex.add(i);
                }
                k += walllist.size();
                Collections.shuffle(tempindex);
                int count = 0;
                for (int i = walllist.size(); i < k; i++) {
                    int value = randomPositionDecider.nextInt(3) + 1;
                    walllist.add(new Wallswrapper(value));
                    setNewElementsPosition(walllist.get(walllist.size() - 1), tempindex.get(count));
                    count++;
                    rootLayout.getChildren().addAll(walllist.get(walllist.size() - 1).getWalls());
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        moveWalls();
    }

    /**
     * Creates the blocks according to the timeline
     */
    private void createBlocks() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
            if (isGameRunning) {
                {
                    int k = (randomPositionDecider.nextInt(5)) + 1;
                    ArrayList<Integer> tempindex = new ArrayList();
                    for (int i = 0; i < 5; i++) {
                        tempindex.add(i);
                    }
                    Collections.shuffle(tempindex);
                    k += blockslist.size();
                    int count = 0;
                    for (int i = blockslist.size(); i < k; i++) {
                        int value = randomPositionDecider.nextInt(snake.size()) + 1;
                        int value2 = randomPositionDecider.nextInt(100) + (int)snakeSpeed;
                        int[] valArr = {value, value2};
                        if (i == blockslist.size()) {
                            blockslist.add(new Block(Integer.toString(valArr[0])));
                        } else {
                            blockslist.add(new Block(Integer.toString(valArr[1])));
                        }
                        setNewElementsPosition(blockslist.get(blockslist.size() - 1), tempindex.get(count));
                        count++;
                        rootLayout.getChildren().add(blockslist.get(blockslist.size() - 1));
                    }
                }
                for (int io = 0; io < 2; io++) {
                    int k1 = (randomPositionDecider.nextInt(3)) + 0;
                    ArrayList<Integer> tempindex1 = new ArrayList();
                    for (int i = 0; i < 5; i++) {
                        tempindex1.add(i);
                    }
                    Collections.shuffle(tempindex1);
                    k1 += tokenslist.size();
                    int count1 = 0;
                    for (int i = tokenslist.size(); i < k1; i++) {
                        int option = randomPositionDecider.nextInt(8) + 1;
                        int value = randomPositionDecider.nextInt(5) + 1;
                        if (option == 2) {
                            tokenslist.add(new Bomb(""));
                        } else if (option == 3) {
                            tokenslist.add(new Shield(""));
                        } else if (option == 4) {
                            tokenslist.add(new Magnet(""));
                        } else {
                            tokenslist.add(new Ball(Integer.toString(value)));
                        }
                        setNewElementsPosition(tokenslist.get(tokenslist.size() - 1), tempindex1.get(count1), (-150 * (io + 1)));
                        count1++;
                        rootLayout.getChildren().add(tokenslist.get(tokenslist.size() - 1));
                    }
                }
            }}));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    /**
     * Creates a new token every 8 seconds
     */
    private void createTokens(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(8), ev -> {
            if (isGameRunning) {
                int k = (randomPositionDecider.nextInt(5)) + 1;
                ArrayList<Integer> tempindex = new ArrayList();
                for (int i = 0; i < k; i++) {
                    tempindex.add(i);
                }
                k += tokenslist.size();
                int count = 0;
                for (int i = tokenslist.size(); i < k; i++) {
                    int option = randomPositionDecider.nextInt(8) + 1;
                    int value = randomPositionDecider.nextInt(5) + 1;
                    if (option == 2) {
                        tokenslist.add(new Bomb(""));
                    } else if (option == 3) {
                        tokenslist.add(new Shield(""));
                    } else if (option == 4) {
                        tokenslist.add(new Magnet(""));
                    } else {
                        tokenslist.add(new Ball(Integer.toString(value)));
                    }

                    setNewElementsPosition(tokenslist.get(tokenslist.size() - 1), tempindex.get(count),-100);
                    count++;
                    rootLayout.getChildren().add(tokenslist.get(tokenslist.size() - 1));
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    /**
     * This is the main Game Loop, which keeps on calling the functiosn inside of it.
     */
    private void createGameLoop() {
        AnimationTimerExt TimerGame = new AnimationTimerExt(20) {
            @Override
            public void handle() {
                if (isGameRunning) {
                    //createwall();
                    darkModeInvisible();
                    moveBlocks();
                    moveWalls();
                    relocateelementsbelowscreen();
                    movePowerUps();
                    checkIfElementsCollide();
                    moveSnake();
                    if(!highscoredetected) {
                        checkhighscore();
                    }
                    UpdateSnakeSize();
                    Stage stage = (Stage) rootLayout.getScene().getWindow();
                    if(stage!=null)
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            System.out.println("Stage is closing");
                            try {
                                GameStructure.setBlockslist(blockslist);
                                GameStructure.setTokenslist(tokenslist);
                                GameStructure.setWalllist(walllist);
                                GameStructure.setSnake(new SnakeModel(snake.size(),168,375));
                                GameStructure.setPoints(points);
                                GameStructure.setlatestpoints(Totalscore);
                                GameStructure.startpagestage=0;
                                ControllerGame.serializegameondeath(GameStructure);
                                ControllerLeaderboard.addpointswithstage(points);
                            }
                            catch (Exception e){
                                System.out.println("onclose error");
                            }

                        }
                    });
                }
            }
        };
        TimerGame.start();
    }

    /**
     * This checks if a game makes a high score when the snake dies.
     */
    private void checkhighscore() {
        if(this.templeaderboard.getLeaders().size()>=10) {
            if (Totalscore > this.templeaderboard.getLeaders().get(this.templeaderboard.getLeaders().size() - 1).getscore()) {
                this.templeaderboard.getLeaders().remove(this.templeaderboard.getLeaders().size() - 1);
                this.templeaderboard.getLeaders().add(new LeaderBoardelements(Totalscore, new Date()));
                java.util.Collections.sort(this.templeaderboard.getLeaders());
                highscoredetected=true;
            }
            try {
                LeaderBoardModel.serialize(this.templeaderboard);
            }
            catch (Exception e){

                System.out.println(e.getMessage());
            }

        }


    }

    /**
     * Movewalls is a function to move a wall according to the downspeed selected.
     */
    private void moveWalls() {
        for (Wallswrapper aWalllist : walllist) {
            for (int i = 0; i < aWalllist.getLength(); i++) {
                aWalllist.getWalls().get(i).setLayoutY(aWalllist.getWalls().get(i).getLayoutY() + downSpeed);
//                walllist.get(j).getWalls().get(i).setY(walllist.get(j).getWalls().get(i).getLayoutY());
            }
        }
    }

    /**
     * Moves the power ups according to the downspeed
     */
    private void movePowerUps() {
        for (Token aTokenslist : tokenslist) {
            aTokenslist.setLayoutY(aTokenslist.getLayoutY() + downSpeed);
//            tokenslist.get(i).setY(tokenslist.get(i).getLayoutY());
        }
    }

    /**
     * Moves the blocks according the the downspeed.
     */
    private void moveBlocks() {
        for (Block aBlockslist : blockslist) {
            aBlockslist.setLayoutY(aBlockslist.getLayoutY() + downSpeed);
//            blockslist.get(i).setY( blockslist.get(i).getLayoutY());
        }
    }

    /**
     * Relocates or removes the elements when they go below the screen.
     */
    private void relocateelementsbelowscreen() {
            for(int j=0;j<blockslist.size();j++) {
                if (blockslist.get(j).getLayoutY() > 600) {
                        rootLayout.getChildren().remove(blockslist.get(j));
                        blockslist.remove(blockslist.get(j));
                }
            }
        for(int j=0;j<tokenslist.size();j++) {
            if (tokenslist.get(j).getLayoutY() > 600) {

                rootLayout.getChildren().remove(tokenslist.get(j));
                tokenslist.remove(tokenslist.get(j));

            }
        }
        boolean removeWall =false;
         for(int k=0;k<walllist.size();k++) {
             for (int j = 0; j < walllist.get(k).getLength(); j++) {
                 if (walllist.get(k).getWalls().get(j).getLayoutY() > 720) {
                     rootLayout.getChildren().remove(walllist.get(k).getWalls().get(j));
                     removeWall = true;
                 }
             }
             if (removeWall){
                 walllist.remove(walllist.get(k));
             }

         }



    }

    /**
     * Function to smoothly move left
     */
    private void moveLeft() {

        animate1.stop();
        animate2.stop();
        animate.start();

//        if (snake.get(0).getLayoutX() > 20) {
//            for (int i = 0; i < snake.size(); i++) {
//                ImageView p = snake.get(i);
//                final TranslateTransition transition = new TranslateTransition(Duration.millis((i + 1) * (60)), p);
//                transition.setFromX(p.getTranslateX());
//                transition.setFromY(p.getTranslateY());
//                transition.setToX(p.getTranslateX());
//                transition.setToY(p.getTranslateY());
//                transition.playFromStart();
//                transition.setOnFinished(t -> {
//                    p.setLayoutX(p.getLayoutX() - (snakeSpeed));
//                    p.setLayoutY(p.getLayoutY());
//                });
//                GameStructure.getSnake().setX(this.snake.get(0).getLayoutX());
//                //snake.get(i).setLayoutX(snake.get(i).getLayoutX() - snakeSpeed);
//            }
//        }
    }

    /**
     * Function to smoothly move right
     */
    private void moveRight(){
        animate1.stop();
        animate.stop();
        animate2.start();
//        if(snake.get(0).getLayoutX()<gameScene.getWidth()-55) {
//            for (int i = 0; i < snake.size(); i++) {
//                ImageView p = snake.get(i);
//                final TranslateTransition transition = new TranslateTransition(Duration.millis((i+1) * (60)), p);
//                transition.setFromX(p.getTranslateX());
//                transition.setFromY(p.getTranslateY());
//                transition.setToX(p.getTranslateX());
//                transition.setToY(p.getTranslateY());
//                transition.playFromStart();
//                transition.setOnFinished(t -> {
//                    p.setLayoutX(p.getLayoutX() + snakeSpeed);
//                    p.setLayoutY(p.getLayoutY());
//                });
//                GameStructure.getSnake().setX(this.snake.get(0).getLayoutX());
//            }//snake.get(i).setLayoutX(snake.get(i).getLayoutX() + 3);
//        }
    }

    /**
     * Moves snakes based on the keys pressed.
     */
    private void moveSnake(){
        if (isLeftKeyPressed && !isRightKeyPressed){
            if (facesWallleft){
               facesWallleft = false;
            }
            else{
                moveLeft();
            }
        }

        if (isRightKeyPressed && !isLeftKeyPressed) {
            if (facesWallright){
                facesWallright=false;
            }
            else{
                moveRight();
            }
        }
    }

    /**
     * Creates the snake and sets the default snake lengths.
     */
    private void createSnake() {
        for(int i=0;i<GameStructure.getSnake().getlength();i++) {
            ImageView img = new ImageView("/view/snake_tail.png");
            img.setFitHeight(25);
            img.setFitWidth(25);
            snake.add(img);
            snake.get(i).setLayoutX(GameStructure.getSnake().getX());
            snake.get(i).setLayoutY(GameStructure.getSnake().getY()+(i*25));
            rootLayout.getChildren().add(snake.get(i));
            snakecor.add(snake.get(i).getLayoutX());
        }
        snakeLen = new SnakeLengthLabel("5");
        snakeLen.setLayoutX(snake.get(0).getLayoutX());
        snakeLen.setLayoutY(snake.get(0).getLayoutY()-25);
        rootLayout.getChildren().add(snakeLen);
        System.out.println(snake.size());
    }

    /**
     * Changes the downSpeed and snakeSpeed based on snake size.
     */
    private void UpdateSnakeSize(){
        snakeLen.setText(String.valueOf(snake.size()));
        snakeLen.setLayoutX(snake.get(0).getLayoutX());
        downSpeed = 0.4*(snake.size()+9);
        snakeSpeed = 0.4 * (snake.size() + 5);

    }

    /**
     * Serialize the score on death.
     */
    private void ondeath(){
        isGameRunning = false;
        System.out.println("Game Over");

        try {
            ControllerGame.serializegameondeath(new GameModel(new SnakeModel(5, 178, 375), 0,points,1));
            ControllerLeaderboard.serializegameondeath(points,(Stage)this.gameScene.getWindow());
        }
        catch (Exception ignored){

        }
    }

    /**
     * The main collide function to check collision with blocks, tokens and walls
     */
    private void checkIfElementsCollide(){
        //            if (SNAKEHEAD_RADIUS+BLOCK_RADIUS >calcculateDistance(snake.get(0).getLayoutX() + 20
        //                    ,blockslist.get(i).getLayoutX()+blockslist.get(i).getPrefWidth()/2,
        //                    snake.get(0).getLayoutY()+ 20,
        //                    blockslist.get(i).getLayoutY()+ blockslist.get(i).getPrefHeight()/2))
        collisionWithBlocks();

//        System.out.println(snake.size());
//        System.out.println(isMagnetOn);
        collisionWithTokens();


//        System.out.println(walllist.size());
//        System.out.println(TOKEN_RADIUS);
        collisionWithWalls();
    }

    /**
     * Collison with walls
     */
    private void collisionWithWalls() {
        for (Wallswrapper aWalllist : walllist) {
            int len = aWalllist.getWalls().size();

//            System.out.println(x.getBoundsInLocal());
            for (int j = 0; j < len; j++) {
                wall x = aWalllist.getWalls().get(j);
                if (snake.get(0).getBoundsInParent().intersects(x.getBoundsInParent())) {
                    if (snake.get(0).getLayoutX() - x.getLayoutX() >= -5) {
                        animate.stop();
                        animate1.start();
                        facesWallleft = true;
                    } else {
                        animate2.stop();
                        animate1.start();
                        facesWallright = true;
                    }
                }
            }
        }
    }

    /**
     * Collison with tokens
     */
    private void collisionWithTokens() {
        for (int j=0;j<tokenslist.size();j++){

//            if (tokenslist.get(j).getBoundsInParent().intersects(snakeLocalHead.getBoundsInParent())){
//                System.out.println("simple?");
//            }
            int localRad = TOKEN_RADIUS;
            if (tokenslist.get(j).getClass() != (new Ball()).getClass()){
                localRad = 10;
            }

//            System.out.println(localRad);

            if (SNAKEHEAD_RADIUS+localRad > calcculateDistance(snake.get(0).getLayoutX() + 20,
                    tokenslist.get(j).getLayoutX()+tokenslist.get(j).getPrefWidth()/2,
                    snake.get(0).getLayoutY()+ 20,
                    tokenslist.get(j).getLayoutY()+tokenslist.get(j).getPrefHeight()/2)) {

                isGameRunning =false;
                if (tokenslist.get(j).getClass() == (new Ball()).getClass()) {
                    coin.play();
                    coin.seek(Duration.millis(0));
                    int lenToInc = tokenslist.get(j).getValue();
                    incSnakeLength(lenToInc);

                }

                if (tokenslist.get(j).getClass() == (new Bomb()).getClass()){
                    blast.play();
                    blast.seek(Duration.millis(0));
                    destroyAllBlocks();
                }

                if(tokenslist.get(j).getClass() == (Shield.class)){
                    turnOnShield();
                }

                if (tokenslist.get(j).getClass() == Magnet.class){
                    turnOnMagnet();
                }
                ImageView imageView = new ImageView("view/Animations/Explosion.gif");
                Token toDestroyToken = tokenslist.get(j);
                imageView.setLayoutY(toDestroyToken.getLayoutY());
                imageView.setLayoutX(toDestroyToken.getLayoutX());
                imageView.setFitWidth(toDestroyToken.getPrefWidth());
                imageView.setFitHeight(toDestroyToken.getPrefHeight());
                rootLayout.getChildren().add(imageView);
                destroyBlockAnimation(imageView, 500);
                rootLayout.getChildren().remove(tokenslist.remove(j));

                isGameRunning = true;
//                Timeline waitForStableSnake = new Timeline();
//                waitForStableSnake.setCycleCount(1);
//                KeyFrame kf = new KeyFrame(Duration.millis(100),event -> {
//                    correctSnakePostions();
//                });
//                waitForStableSnake.getKeyFrames().add(kf);
//                waitForStableSnake.play();
            }

//            if (isMagnetOn){
//                ImageView snakeHead = snake.get(0);
//                if (SNAKEHEAD_RADIUS+TOKEN_RADIUS > calcculateDistance(snake.get(0).getLayoutX() + 20,
//                        tokenslist.get(j).getLayoutX()+tokenslist.get(j).getPrefWidth()/2,
//                        snake.get(0).getLayoutY()+ 20,
//                        tokenslist.get(j).getLayoutY()+tokenslist.get(j).getPrefHeight()/2)) {
//                    System.out.println("Getting here");
//                    Token p = tokenslist.get(j);
//                    final TranslateTransition transition = new TranslateTransition(Duration.millis(100), p);
//
//                    transition.setToX(snakeHead.getLayoutX());
//                    transition.setToY(snakeHead.getLayoutY());
//                    transition.play();
//                }
//            }
        }
    }

    /**
     * Collision with blocks
     */
    private void collisionWithBlocks() {
        if(snake.size()<=0){
            ondeath();

        }
        for (int i=0;i<blockslist.size();i++) {
            Bounds bd = blockslist.get(i).getBoundsInParent();
//            System.out.println(bd.getMinX());
            Bounds snakBd =snake.get(0).getBoundsInParent();
            if (SNAKEHEAD_RADIUS+100 > calcculateDistance(snake.get(0).getLayoutX() + 20,
                    blockslist.get(i).getLayoutX()+blockslist.get(i).getPrefWidth()/2,
                    snake.get(0).getLayoutY()+ 20,
                    blockslist.get(i).getLayoutY()+blockslist.get(i).getPrefHeight()/2)){
                blockslist.get(i).setVisible(true);
            }
            if (blockslist.get(i).getBoundsInParent().intersects(snake.get(0).getBoundsInParent())) {
//                System.out.println(bd+"\n"+snakBd);
                if(snakBd.intersects(bd.getMinX(),bd.getMinY()+70,
                        bd.getWidth(),bd.getHeight()-78)){
                    isGameRunning = false;

                    int valueOfBlock = blockslist.get(i).getValue();
//                    if (valueOfBlock < snake.size() || isShieldOn) {
                        if (valueOfBlock <= 5 || isShieldOn) {

                            if (valueOfBlock >= snake.size()){
                                ondeath();
                                break;
                            }
                            if (!isShieldOn) {
                                for (int r = 0; r < valueOfBlock; r++) {
                                    if (snake.size()>0){
                                        snakecor.remove(snakecor.size()-1);
                                        rootLayout.getChildren().remove(snake.remove(snake.size() - 1));
                                    }
                                }
                            }
                            blast.play();
                            blast.seek(Duration.millis(0));
                            destroyBlockAndUpdateScore(i,blockslist.get(i).getValue());
                            isGameRunning = true;

                        } else {
                            Timeline anim = new Timeline();
                            anim.setCycleCount(1);
                            boolean killsnake=false;
                            if (valueOfBlock >= snake.size())
                                killsnake= true;

                            int finalI = i;
                            int pointsLocal = blockslist.get(i).getValue();
                            for (int j = 0; j < Math.min(valueOfBlock,snake.size()); j++) {

                                snake.size();
                                Duration duration = Duration.millis(j * 100);
                                int finalJ = j;
                                KeyFrame keyFrame = new KeyFrame(duration, event -> {
                                    if(snake.size()>0){
                                        blockhit.play();
                                        blockhit.seek(Duration.millis(0));
                                        snakecor.remove(snakecor.size()-1);
                                        rootLayout.getChildren().remove(snake.remove(snake.size() - 1));
//                                    int blockVal = Integer.parseInt(blockslist.get(finalI).gettext()) - 1;
                                        blockslist.get(finalI).setText(String.valueOf(valueOfBlock- finalJ));
                                    }

                                }

                                );
                                anim.getKeyFrames().add(keyFrame);
                            }

                            anim.play();

                            boolean finalKillsnake = killsnake;
                            anim.setOnFinished(event -> {
                                blast.play();
                                blast.seek(Duration.millis(0));
                                destroyBlockAndUpdateScore(finalI,pointsLocal);
                                isGameRunning = true;
                                if (finalKillsnake)
                                    ondeath();
                            });

                        }



                }
                else{
                    if (snakBd.getMinX() > bd.getMinX()){
                        facesWallleft=true;
                    }
                    else{
                        facesWallright=true;
                    }
                }
            }
        }
    }

    /**
     * Shows the destroy animation and then updates the score
     * @param i Operations to be done on block with index i
     */
    private void destroyBlockAndUpdateScore(int i,int ptsLocal) {
        points += ptsLocal;
        Totalscore += points;
        ImageView imageView = new ImageView("view/Animations/Explosion.gif");
        imageView.setLayoutY(blockslist.get(i).getLayoutY());
        imageView.setLayoutX(blockslist.get(i).getLayoutX());
        rootLayout.getChildren().add(imageView);
        destroyBlockAnimation(imageView, 500);
        rootLayout.getChildren().remove(blockslist.remove(i));

        String newScore = "Score: ";
        scoreLabelText.setText(newScore + points);
    }

    /**
     * Increases the snake length by the lentoInc
     * @param lenToInc The length of the snake to be increased
     */
    private void incSnakeLength(int lenToInc) {
        for (int k = 0; k < lenToInc; k++) {
                double toSetX = snake.get(snake.size()-1).getLayoutX();
                double toSetY = snake.get(snake.size()-1).getLayoutY() +25;
                ImageView img = new ImageView("/view/snake_tail.png");
                img.setFitHeight(25);
                img.setFitWidth(25);
                snake.add(img);
                double offSet = 0;
                if (isLeftKeyPressed && !isRightKeyPressed){
                    offSet = -snakeSpeed;
                }
                else if (isRightKeyPressed&& !isLeftKeyPressed){
                    offSet = snakeSpeed;
                }
                snake.get(snake.size() - 1).setLayoutX(toSetX+offSet);
                snake.get(snake.size() - 1).setLayoutY(toSetY);
                snakecor.add(toSetX);
                rootLayout.getChildren().add(snake.get(snake.size() - 1));
        }
    }

    /**
     * Corrects the snake postion if it gets messed up
     */
    private void correctSnakePostions() {
        if (!isLeftKeyPressed && !isRightKeyPressed){
            for (int i=1;i<snake.size();i++){
                snake.get(i).setLayoutX(snake.get(i-1).getLayoutX());
                snake.get(i).setLayoutY(snake.get(i-1).getLayoutY()+25);
            }
        }
    }

    /**
     * Shows the destroy block animation
     * @param imageView The imageview which is the animation
     * @param len   The duration to display the animation
     */
    private void destroyBlockAnimation(ImageView imageView, int len) {
        Timeline newlocalTimeline = new Timeline();
        Duration duration = Duration.millis(len);
        KeyFrame kf = new KeyFrame(duration);
        newlocalTimeline.getKeyFrames().add(kf);
        newlocalTimeline.setOnFinished(event -> {
            rootLayout.getChildren().remove(imageView);
//                        isGameRunning = true;
        });
        newlocalTimeline.play();
    }

    /**
     * Destorys all the blocks on the screen.
     */
    private void destroyAllBlocks() {
        ArrayList<ImageView> gifArrList = new ArrayList<>();
        for (int i = 0;i<blockslist.size();i++){
            points+=blockslist.get(i).getValue();
            Totalscore+=points;
            gifArrList.add(new ImageView("view/Animations/Explosion.gif"));
            gifArrList.get(i).setLayoutX(blockslist.get(i).getLayoutX());
            gifArrList.get(i).setLayoutY(blockslist.get(i).getLayoutY());
            rootLayout.getChildren().add(gifArrList.get(i));

            rootLayout.getChildren().remove(blockslist.get(i));
//            blockslist.remove(blockslist.get(i));
        }
        blockslist.clear();
        Timeline newlocalTimeline= new Timeline();
        Duration duration = Duration.millis(500);
        KeyFrame kf = new KeyFrame(duration);
        newlocalTimeline.getKeyFrames().add(kf);
        newlocalTimeline.setOnFinished(event -> {
            for (ImageView aGifArrList : gifArrList) {
                rootLayout.getChildren().remove(aGifArrList);
            }
            gifArrList.clear();
        });
        newlocalTimeline.play();

        String newScore =  "Score: ";
        scoreLabelText.setText(newScore+points);
    }

    /**
     * Turns the magnet On
     */
    private void turnOnMagnet() {
        if (!isMagnetOn) {
            isMagnetOn =true;
            createAndDisplayTimer();
            if (magLabel == null)
                createMagnetLabel();
            showMagnetLabel();
            TOKEN_RADIUS = 100;
            timer = new Timer();
            timer.schedule(new changeBackRadius(), 5 * 1000);
        }
    }
    private void createAndDisplayTimer() {
        ImageView imageView = new ImageView("view/Animations/ctdnobg.gif");
        imageView.setLayoutY(20);
        imageView.setLayoutX(50);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        rootLayout.getChildren().add(imageView);
        destroyBlockAnimation(imageView,5000);
    }
    /**
     * Turns the shield on
     */
    private void turnOnShield() {
        if (!isShieldOn) {
            isShieldOn = true;
            createAndDisplayTimer();
            if (shieldLabel==null)
                createShieldLabel();
            showShieldLabel();
            timer = new Timer();
            timer.schedule(new changeBackShield(), 5 * 1000);
        }
    }

    /**
     * Display the shield label
     */
    private void showShieldLabel() {
        rootLayout.getChildren().add(shieldLabel);
    }

    /**
     * Shows the magnet label
     */
    private void showMagnetLabel() {
        rootLayout.getChildren().add(magLabel);
    }

    /**
     * Creates the magnet label
     */
    private void createMagnetLabel() {
        magLabel = new ScoreLabel("M");
        System.out.println("Showing?");
        magLabel.setLayoutY(20);
        magLabel.setLayoutX(100);
    }

    /**
     * Creates the shield label
     */
    private void createShieldLabel(){
        shieldLabel = new ScoreLabel("S");
        shieldLabel.setLayoutY(20);
        shieldLabel.setLayoutX(150);
    }

    /**
     * Changes the radius back to default
     */
    private class changeBackRadius extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() ->
            {
                TOKEN_RADIUS =10;
                removeMagLabel();
                isMagnetOn = false;
                if (!isShieldOn)
                timer.cancel();
            });

        }

    }

    /**
     * Turns off the shield after 5 seconds.
     */
    private class changeBackShield extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(()->{
                isShieldOn = false;
                removeShieldLabel();
                if (!isMagnetOn)
                timer.cancel();
            });

        }
    }

    /**
     * Remove the shield label
     */
    private void removeShieldLabel() {
//        System.out.println("Executed");
        rootLayout.getChildren().remove(shieldLabel);
    }

    /**
     * Remove the magnet label
     */
    private void removeMagLabel() {
        rootLayout.getChildren().remove(magLabel);
    }

    /**
     * Does the eucledian distance calculation
     * @param x1 x coord of first
     * @param x2 x coord of second
     * @param y1 y coord of first
     * @param y2 y coord of second
     * @return returns the distance
     */
    private double calcculateDistance(double x1,double x2,double y1,double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    private void darkModeInvisible(){
        if (isDarkMode){
            for (int i=0;i<blockslist.size();i++){
                blockslist.get(i).setVisible(false);
            }
        }
    }
    private void darkMode(){
//        blockslist.get(0).setVisible();

        isDarkMode=true;
        createAndDisplayTimer();
        timer = new Timer();
        timer.schedule(new changeBackVisibility(), 5* 1000);
    }

    private class changeBackVisibility extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(()->{
                isDarkMode=false;
            });
        }
    }
}
