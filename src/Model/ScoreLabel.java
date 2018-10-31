package Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.scene.text.Font.font;

public class ScoreLabel extends Label {
    private static final String SCORE_FONT = "src/view/Font/Proxima_Font.otf";

    public ScoreLabel(String text) {
        super(text);

        setPrefWidth(150);
        setPrefHeight(50);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10,10,10,10));
        setTheFont();
        setText(text);
    }

    private void setTheFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(SCORE_FONT)),20));
        } catch (FileNotFoundException e) {
            setFont(font("Verdana",20));
        }
    }
}
