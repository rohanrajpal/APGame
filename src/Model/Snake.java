package Model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Snake {
    private ArrayList<ImageView> mainSnakeList;
    private Pane rootLayout;
    SnakeLengthLabel snakeLen;
    public Snake(int snakeInpLength, int posX, int posY, Pane rootLayoutInp) {
        this.rootLayout = rootLayoutInp;
        mainSnakeList = new ArrayList<>();

        for(int i=0;i<snakeInpLength;i++) {
            ImageView img = new ImageView("/view/snake_tail.png");
            img.setFitHeight(25);
            img.setFitWidth(25);
            mainSnakeList.add(img);
            mainSnakeList.get(i).setLayoutX(posX);
            mainSnakeList.get(i).setLayoutY(posY+(i*25));
            rootLayout.getChildren().add(mainSnakeList.get(i));
        }

        snakeLen = new SnakeLengthLabel("5");
        snakeLen.setLayoutX(mainSnakeList.get(0).getLayoutX());
        snakeLen.setLayoutY(mainSnakeList.get(0).getLayoutY()-25);
        rootLayout.getChildren().add(snakeLen);
    }
}
