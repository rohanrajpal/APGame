package Model;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import java.io.Serializable;

public class Bomb extends Token implements Serializable {
    private static final String DestroyAll_src = "view/tokens/token_destroyall.png";

    /**
     * Constructor to create bomb image with particular value
     * @param text
     */
    public Bomb(String text) {
        super(text);
        BackgroundImage bImage = new BackgroundImage(new Image(DestroyAll_src, 40, 40,
                false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(bImage));
    }
    /**
     * Constructor to create bomb image with default value
     */
    public Bomb() {
        super("");
    }
}
