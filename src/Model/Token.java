package Model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import static javafx.scene.text.Font.font;

public class Token extends Label implements Serializable {
    protected double x;
    protected  double y;
    private String text;

    /**
     * function to get x co-ordinate of token
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
    /**
     * function to get text of token
     * @return
     */
    public String gettext() {
        return text;
    }
    /**
     * function to set text of token
     */
    public void settext(String text) {
        this.text = text;
    }

    /**
     * Constructor to initialise a token
     * @param text
     */
    public Token(String text) {
        super(text);
        this.text=text;
        setPrefWidth(40);
        setPrefHeight(40);

    }

    /**
     * function to get value of a token
     * @return
     */

    public int getValue(){
        return Integer.parseInt(getText());
    }
}

