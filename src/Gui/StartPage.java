package Gui;

import controller.ControllerLeaderboard;
import controller.ControllerMainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Model.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class StartPage{
    private AnchorPane rootLayout;

    public StartPage(int choice){

        initmainlayout(choice);
    }

    private void initmainlayout(int choice) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartPage.class.getResource("../view/StartPageLayout.fxml"));
            rootLayout =  loader.load();
            rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mousecordinates.xOffset = event.getSceneX();
                    mousecordinates.yOffset = event.getSceneY();
                }
            });
            ControllerMainMenu controller = loader.getController();
            controller.init(choice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
