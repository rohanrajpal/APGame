package Model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import java.util.ArrayList;

public class wall extends Label {
    private static final String wall_image= "view/Blocks/wall.png";
    public wall(){
        setPrefWidth(10);
        setPrefHeight(80);
        setAlignment(Pos.CENTER);
        BackgroundImage bImage = new BackgroundImage(new Image(wall_image,10,80,
                false,true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(bImage));
    }
}
