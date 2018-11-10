package Model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import static javafx.scene.text.Font.font;

public class Ball extends Token implements Serializable {
    private static final String AddTail_src = "view/snake_tail.png";
    private static final String BLOCK_FONT = "src/view/Font/Proxima_Font.otf";
    public Ball(String text){
        super(text);
        setBlockLabelFont();
        setAlignment(Pos.CENTER);
        setPrefHeight(25);
        setPrefWidth(25);
        BackgroundImage bImage = new BackgroundImage(new Image(AddTail_src,25,25,
                false,true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        setBackground(new Background(bImage));
    }

    public Ball() {
        super("");
    }

    public void setBlockLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(BLOCK_FONT)),15));
        } catch (FileNotFoundException e) {
            setFont(font("Verdana",20));
        }
    }
}
