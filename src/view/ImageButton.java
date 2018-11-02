package view;

import controller.*;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;;import java.io.IOException;

public class ImageButton extends Button {

    private final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";

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
                    ControllerMainMenu.start(event);
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
            }
        });

    }


}