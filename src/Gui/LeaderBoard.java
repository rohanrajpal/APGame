package Gui;

import Model.mousecordinates;
import controller.ControllerLeaderboard;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LeaderBoard {


    private Pane rootLayout;

    /**
     * Constructor to call functions on LeaderBoard Sobject creation
     */
    public LeaderBoard(int choice){
        initmainlayout(choice);

    }

    /**
     * Init function to load leaderboard fxml
     */
    private void initmainlayout(int choice) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartPage.class.getResource("../view/LeaderBoardLayout.fxml"));
            rootLayout =  loader.load();
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mousecordinates.xOffset = event.getSceneX();
                    mousecordinates.yOffset = event.getSceneY();
                }
            });
            ControllerLeaderboard controller = loader.getController();
            controller.leaderboard(choice);
            // Show the scene containing the root layout.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * function to show primary stage
     * @param primaryStage
     */
    public void show(Stage primaryStage){
        rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - mousecordinates.xOffset);
                primaryStage.setY(event.getScreenY() - mousecordinates.yOffset);
            }
        });
        Scene scene = new Scene(rootLayout);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
