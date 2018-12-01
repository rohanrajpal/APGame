package Model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import java.io.Serializable;
import java.util.ArrayList;

public class wall extends Label implements Serializable {
    private static final String wall_image= "view/Blocks/wall.png";
    private double x;
    private double y;
    /**
     * function to get x co-ordinate of wall
     * @return
     */
    public double getX() {
        return x;
    }
    /**
     * function to set x co-ordinate of token
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * function to get y co-ordinate of token
     * @return
     */
    public double getY() {
        return y;
    }
    /**
     * function to set y co-ordinate of token
     */
    public void setY(double y) {
        this.y = y;
    }

    public wall(){
        setPrefWidth(30);
        setPrefHeight(80);
        setAlignment(Pos.CENTER);
        BackgroundImage bImage = new BackgroundImage(new Image(wall_image,30,80,
                false,true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(bImage));
    }
}
