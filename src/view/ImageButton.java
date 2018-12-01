package view;

import Gui.LeaderBoard;
import Gui.StartPage;
import Model.GameModel;
import Model.LeaderBoardModel;
import controller.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;;import java.io.IOException;
public class ImageButton extends Button {

    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
    ImageView image;
    public void setLe(int le, GameModel g) {
        this.points = le;
        this.GAME=g;
    }
    private GameModel GAME;
    private int points;
    public String getInterim() {
        return interim;
    }

    public void setInterim(String interim) {
        this.interim = interim;
    }

    private String interim="";
    public String getSTYLE_NORMAL() {
        return STYLE_NORMAL;
    }

    public String getSTYLE_PRESSED() {
        return STYLE_PRESSED;
    }

    /**
     * constructor for button control
     * @param imageurl
     */
    public ImageButton(String imageurl) {

        image=new ImageView(new Image(getClass().getResourceAsStream(imageurl)));
        setGraphic(image);
        setStyle(STYLE_NORMAL);

        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle(STYLE_PRESSED);
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setStyle(STYLE_NORMAL);
                if(interim.equals("back")){
                    interim=" ";
                    ControllerLeaderboard.back(event);
                }
                if(interim.equals("back2")){
                    interim=" ";
                    ControllerLeaderboard.back2(event);
                }
                if(interim.equals("restart")){
                    interim=" ";
                    try {
                        ControllerGame.serializegame(GAME,event);
                        ControllerLeaderboard.addpoints(points,event,"restart");

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                if(interim.equals("start")){
                    interim=" ";
                    try {
                        ControllerMainMenu.start(event);
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                if(interim.equals("leaderboard"))  {
                    interim=" ";
                    try {
                        ControllerMainMenu.leaderboard(event,0);
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                if(interim.equals("leaderboard1"))  {
                    interim=" ";
                    try {
                        ControllerMainMenu.leaderboard(event,1);
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                if(interim.equals("resume")){
                    try {
                        ControllerMainMenu.resume(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    interim=" ";
                }
                if(interim.equals("exit")){
                    interim=" ";
                    ControllerMainMenu.exit();
                }
                if(interim.equals("Pause")){
                    interim=" ";
                    ControllerGame.pause(event);
                }
                if(interim.equals("StartPage")){
                    interim=" ";
                    try {
                        ControllerGame.serializegame(GAME,event);
                        ControllerLeaderboard.addpoints(points,event,"startpage");

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     * sets preserve ratio of image
     * @param t
     */
    public void setPreserveRatio(boolean t){
        image.setPreserveRatio(t);
    }

    /**
     * sets height and width of images
     * @param x
     * @param y
     */
    public void setfit(double x,double y) {
        image.setFitHeight(x);
        image.setFitWidth(y);
    }

}