package controller;

import Gui.Game;
import Model.GameModel;
import Model.SnakeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import Model.LeaderBoardModel;

import javafx.scene.input.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Gui.LeaderBoard;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import view.ImageButton;


public class ControllerMainMenu  implements Initializable {
    @FXML
    private Pane pane1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init() {
        ImageButton ib=new ImageButton("/view/Helper_images/leaderboard-button.png");
        ImageButton ib1=new ImageButton("/view/Helper_images/play-button.png");
        ImageButton ib2=new ImageButton("/view/Helper_images/resume-button.png");
        ImageButton ib3=new ImageButton("/view/Helper_images/error.png");
        ib.setInterim("leaderboard");
        ib1.setInterim("start");
        ib2.setInterim("resume");
        ib3.setInterim("exit");
        ib.setLayoutX(60);
        ib.setLayoutY(383);
        ib1.setLayoutX(164);
        ib1.setLayoutY(383);
        ib2.setLayoutX(274);
        ib2.setLayoutY(383);
        ib3.setLayoutX(164);
        ib3.setLayoutY(475);
        pane1.getChildren().add(ib);
        pane1.getChildren().add(ib1);
        pane1.getChildren().add(ib2);
        pane1.getChildren().add(ib3);
    }
    public static void leaderboard(MouseEvent actionEvent) throws IOException {
        LeaderBoard gui=new LeaderBoard();
        gui.show((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }
    public static void start(MouseEvent actionEvent){
        Game gui=new Game(new GameModel(new SnakeModel()),new ArrayList<>());
        gui.createNewGame();
        gui.show((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }
    public static void resume(){

    }
    public static void exit(){
        System.exit(0);
    }

}
