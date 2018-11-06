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
    public double getX() {
        return x;
    }
    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public Token(String text) {
        super(text);
        this.text=text;
        setPrefWidth(40);
        setPrefHeight(40);

    }


    public int getValue(){
        return Integer.parseInt(getText());
    }
}

