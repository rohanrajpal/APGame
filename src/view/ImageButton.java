package view;

import Gui.LeaderBoard;
import Gui.StartPage;
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

    public void setLe(int le) {
        this.points = le;
    }

    private int points;
    public String getInterim() {
        return interim;
    }

    public void setInterim(String interim) {
        this.interim = interim;
    }

    private String interim;
    public String getSTYLE_NORMAL() {
        return STYLE_NORMAL;
    }

    public String getSTYLE_PRESSED() {
        return STYLE_PRESSED;
    }



    public ImageButton(String imageurl) {

        setGraphic(new ImageView(new Image(getClass().getResourceAsStream(imageurl))));
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
                        ControllerMainMenu.leaderboard(event);
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                if(interim.equals("resume")){
                    interim=" ";
                    ControllerMainMenu.resume();
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
                        ControllerLeaderboard.addpoints(points,event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }


}