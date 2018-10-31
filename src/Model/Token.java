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
    private static final String AddTail_src = "view/tokens/token_addtail.png";
    private static final String DestroyAll_src = "view/tokens/token_destroyall.png";
    private static final String Magnet_src = "view/tokens/token_magnet.png";
    private static final String Shield_src = "view/tokens/token_shield.png";
    private static final String BLOCK_FONT = "src/view/Font/Proxima_Font.otf";

    private int option;
    public Token(String text,int option) {
        super(text);
        this.option = option;
        setPrefWidth(40);
        setPrefHeight(40);
        BackgroundImage bImage=null;
        if (option == 1){
            setBlockLabelFont();
            setAlignment(Pos.CENTER);
        }
        switch (option){
            case 1:
                bImage = new BackgroundImage(new Image(AddTail_src,40,40,
                        false,true), BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
                break;
            case 2:
                bImage = new BackgroundImage(new Image(DestroyAll_src,40,40,
                        false,true), BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
                break;
            case 3:
                bImage = new BackgroundImage(new Image(Magnet_src,40,40,
                        false,true), BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
                break;

            case 4:
                bImage = new BackgroundImage(new Image(Shield_src,40,40,
                        false,true), BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        }

        setBackground(new Background(bImage));
    }

    private void setBlockLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(BLOCK_FONT)),20));
        } catch (FileNotFoundException e) {
            setFont(font("Verdana",20));
        }
    }

    public int getOption() {
        return option;
    }

    public int getValue(){
        return Integer.parseInt(getText());
    }
}

