package Gui;

import controller.ControllerLeaderboard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class LeaderBoard {


    private Pane rootLayout;

    public LeaderBoard(){
        initmainlayout();

    }
    private void initmainlayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartPage.class.getResource("../view/LeaderBoardLayout.fxml"));
            rootLayout =  loader.load();
            ControllerLeaderboard controller = loader.getController();
            controller.leaderboard();
            // Show the scene containing the root layout.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void show(Stage primaryStage){

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
