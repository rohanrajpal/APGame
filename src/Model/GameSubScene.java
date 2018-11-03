package Model;

import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class GameSubScene extends SubScene {
    public static final String IMG_PATH ="../view/black_back.jpg";
    public GameSubScene(){
        super(new AnchorPane(),300,300);
        setLayoutX(50);
        setLayoutY(150);

//        Image img = new Image(IMG_PATH,300,500,false,true);
//        BackgroundImage imgBackToSet = new BackgroundImage(img, BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT,null);
//
//        AnchorPane rootnext = getPane();
//        rootnext.setBackground(new Background(imgBackToSet));
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}
