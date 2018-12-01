package controller;

import Gui.Game;
import Model.GameModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import view.ImageButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame  implements Initializable {
    @FXML
    private Pane pane1;
    @Override
    /**
     * default initialise from javafx
     */
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * init function to load controller to javafx
     */
    public void init() {
//        ImageButton ib=new ImageButton("/view/Helper_images/pause.png");
//        ib.setLayoutX(313);
//        ib.setLayoutY(20);
//        ib.setInterim("pause");
//
//        ib.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                pause();
//            }
//        });
//        pane1.getChildren().add(ib);
    }

    /**
     * no body pause function to handle some cases
     * @param event
     */
    public static void pause(MouseEvent event){

    }

    /**
     * serialise during a mousepress event
     * @param g
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void serializegame (GameModel g,MouseEvent event)throws IOException,ClassNotFoundException {
        GameModel.serialize(g);
    }

    /**
     * seialize when there is no mousepress ( on death )
     * @param g
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void serializegameondeath(GameModel g)throws IOException,ClassNotFoundException{
        GameModel.serialize(g);
    }

}
