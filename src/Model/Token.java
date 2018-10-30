package Model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.scene.text.Font.font;

public class Token extends Label {
    public static final String Back_src = "view/Blocks/block_yellow.png";
    private static final String BLOCK_FONT = "src/view/Font/Proxima_Font.otf";
    public Token(String text) {
        super(text);

        setPrefWidth(40);
        setPrefHeight(40);

        setBlockLabelFont();
        setAlignment(Pos.CENTER);

        BackgroundImage bImage = new BackgroundImage(new Image(Back_src,80,80,
                false,true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        setBackground(new Background(bImage));
    }

    private void setBlockLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(BLOCK_FONT)),20));
        } catch (FileNotFoundException e) {
            setFont(font("Verdana",20));
        }
    }
}

