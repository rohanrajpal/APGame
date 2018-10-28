package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import Model.LeaderBoardModel;
import java.io.*;
import Gui.LeaderBoard;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;


public class ControllerMainMenu {

    @FXML
    private Button stGame;

    @FXML
    private Button btLeaderBoard;

    @FXML
    private Button btResume;

    public void Message(ActionEvent actionEvent) {
        System.out.println("Whaat");
    }
    public void leaderboard(ActionEvent actionEvent) throws IOException {
        LeaderBoard gui=new LeaderBoard();
        gui.show((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

}
