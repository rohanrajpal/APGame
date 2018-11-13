package Model;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import java.io.Serializable;

public class Shield extends Token implements Serializable {
    private static final String Shield_src = "view/tokens/icons8-shield-48.png";
    public Shield (String text) {
        super(text);
        BackgroundImage bImage = new BackgroundImage(new Image(Shield_src , 40, 40,
                false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(bImage));
    }

    public Shield() {
        super("");
    }
}
