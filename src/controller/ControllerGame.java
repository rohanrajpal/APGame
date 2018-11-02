package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import view.ImageButton;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame  implements Initializable {
    @FXML
    private Pane pane1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init() {
        ImageButton ib=new ImageButton("/view/Helper_images/pause.png");
        ib.setLayoutX(313);
        ib.setLayoutY(20);
        ib.setInterim("pause");
        pane1.getChildren().add(ib);
    }
    public static void pause(MouseEvent actionEvent){



    }

}
