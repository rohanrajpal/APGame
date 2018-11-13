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
import java.io.Serializable;
import java.util.Random;

import static javafx.scene.text.Font.font;

public class Block extends Label implements Serializable {
    private static final String BLOCK_COLOR_YELLOW = "view/Blocks/yellow_block.png";
    private static final String BLOCK_COLOR_BLUE = "view/Blocks/blue_block.png";
    private static final String BLOCK_COLOR_RED = "view/Blocks/purple_block.png";
    private static final String BLOCK_COLOR_PURPLE = "view/Blocks/red_block.png";
    private static final String BLOCK_FONT = "src/view/Font/Proxima_Font.otf";
    Random randomPositionDecider;
    private double x;
    private double y;



    public String gettext() {
        return text;
    }

    public void settext(String text) {
        this.text = text;
    }

    public String getImagecolor() {
        return Imagecolor;
    }

    public void setImagecolor(String imagecolor) {
        Imagecolor = imagecolor;
    }

    private String text;
    private String Imagecolor;
    public double getX() {
        return x;
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
    public Block(String txt) {
        super(txt);
        this.text=txt;
        setPrefWidth(78);
        setPrefHeight(80);
        setText(txt);
        setBlockLabelFont();
        setAlignment(Pos.CENTER);
        Image imgToSet;
        randomPositionDecider = new Random();
        int choice =  (randomPositionDecider.nextInt(4)+1);
        if(choice ==1 ){
            this.Imagecolor=BLOCK_COLOR_YELLOW;
            imgToSet = new Image(BLOCK_COLOR_YELLOW,80,80,
                    false,true);
        }
        else if (choice ==2){
            this.Imagecolor=BLOCK_COLOR_BLUE;
            imgToSet = new Image(BLOCK_COLOR_BLUE,80,80,
                    false,true);
        }
        else if (choice == 3){
            this.Imagecolor=BLOCK_COLOR_PURPLE;
            imgToSet = new Image(BLOCK_COLOR_PURPLE,80,80,
            false,true);
        }
        else{
            this.Imagecolor=BLOCK_COLOR_RED;
            imgToSet = new Image(BLOCK_COLOR_RED,80,80,
                    false,true);
        }
        BackgroundImage bImage = new BackgroundImage(imgToSet, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        setBackground(new Background(bImage));
    }
    public Block(String txt,String Imagecolor){
        super(txt);
        setPrefWidth(78);
        setPrefHeight(80);
        setText(txt);
        setBlockLabelFont();
        setAlignment(Pos.CENTER);
        Image imgToSet;
        if(Imagecolor==null){
            Imagecolor=BLOCK_COLOR_RED;
        }
        imgToSet = new Image(Imagecolor,80,80,
                false,true);
        BackgroundImage bImage = new BackgroundImage(imgToSet, BackgroundRepeat.NO_REPEAT,
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

    public int getValue(){
        return Integer.parseInt(getText());
    }
}
