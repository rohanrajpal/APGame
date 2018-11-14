package Model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static javafx.scene.text.Font.font;

public class SnakeLengthLabel extends Label {
    public SnakeLengthLabel(String text) {
        super(text);
        setPrefHeight(25);
        setPrefWidth(25);
        setAlignment(Pos.CENTER);
        setTextFill(Color.rgb(255,255,255));
        setFont(font("Verdana",15));
        setText(text);
    }
}
