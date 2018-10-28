package Gui;

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

public class StartPage extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            primaryStage.initStyle(StageStyle.TRANSPARENT);
        }
        catch (Exception e){

        }
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Main Menu");
        initmainlayout();
    }

    private void initmainlayout() {
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
            rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - mousecordinates.xOffset);
                    primaryStage.setY(event.getScreenY() - mousecordinates.yOffset);
                }
            });
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
