package controller;

import Gui.Game;
import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Gui.LeaderBoard;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import view.ImageButton;


public class ControllerMainMenu  implements Initializable {
    @FXML
    private Pane pane1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(int choice) {
        try {
            GameModel g = GameModel.deserialize();
                createstartpage1(choice);
        }
        catch (Exception e){
            createstartpage2();
        }
    }
    public void createstartpage2(){
        ImageButton ib=new ImageButton("/view/Helper_images/leaderboard-button.png");
        ImageButton ib1=new ImageButton("/view/Helper_images/play-button.png");
        ImageButton ib3=new ImageButton("/view/Helper_images/error.png");
        ib.setInterim("leaderboard");
        ib1.setInterim("start");
        ib3.setInterim("exit");
        ib.setLayoutX(174);
        ib.setLayoutY(475);
        ib1.setLayoutX(244);
        ib1.setLayoutY(383);
        ib3.setLayoutX(100);
        ib3.setLayoutY(383);
        pane1.getChildren().add(ib);
        pane1.getChildren().add(ib1);
        pane1.getChildren().add(ib3);
    }
    public void createstartpage1(int choice){
        ImageButton ib=new ImageButton("/view/Helper_images/leaderboard-button.png");
        ImageButton ib1=new ImageButton("/view/Helper_images/play-button.png");
        ImageButton ib2=new ImageButton("/view/Helper_images/resume-button.png");
        ImageButton ib3=new ImageButton("/view/Helper_images/error.png");
        Label l=new Label();
        int points=0;
        try {
            GameModel g=GameModel.deserialize();
            points=g.getlatestPoints();
        }
        catch (Exception e){
            points=0;
        }

            l.setTextFill(Color.rgb(255, 255, 255));
            l.setText(Integer.toString(points));
            l.setFont(new Font(50));

            l.setMinHeight(80);
            l.setMinWidth(80);
            l.setLayoutX(187);
            l.setLayoutY(300);
        if(choice==0) {
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
            pane1.getChildren().add(l);
        }
        else{
            ib.setInterim("leaderboard");
            ib1.setInterim("start");
            ib3.setInterim("exit");
            ib.setLayoutX(174);
            ib.setLayoutY(475);
            ib1.setLayoutX(244);
            ib1.setLayoutY(383);
            ib3.setLayoutX(100);
            ib3.setLayoutY(383);
            pane1.getChildren().add(ib);
            pane1.getChildren().add(ib1);
            pane1.getChildren().add(ib3);
            pane1.getChildren().add(l);
        }
    }
    public static void leaderboard(MouseEvent actionEvent) throws IOException {
        LeaderBoard gui=new LeaderBoard();
        gui.show((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }
    public static void start(MouseEvent actionEvent) throws ClassNotFoundException,IOException{

        LeaderBoardModel L=new LeaderBoardModel();
        try{
            L=LeaderBoardModel.deserialize();
            L.setLeaders(L.getLeaders());
        }
        catch(Exception E) {

            LeaderBoardModel.serialize(L);
        }
        GameModel model=new GameModel(new SnakeModel(5,178,375),0,0);
        Game gui=new Game(model,model.getBlockslist(),model.getTokenslist(),model.getWalllist(),L);
        gui.createNewGame();
        gui.show((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }
    public static void resume(MouseEvent actionEvent)throws ClassNotFoundException,IOException{
        LeaderBoardModel L=new LeaderBoardModel();
        try{
            L=LeaderBoardModel.deserialize();
            L.setLeaders(L.getLeaders());
        }
        catch(Exception E) {

            LeaderBoardModel.serialize(L);
        }
        GameModel model=new GameModel(new SnakeModel(5,168,375),0,0);
        try{
            model=GameModel.deserialize();

        }
        catch(Exception E) {

            GameModel.serialize(model);
        }

        for(int i=0;i<model.getBlockslist().size();i++){
            double x=model.getBlockslist().get(i).getX();
            double y=model.getBlockslist().get(i).getY();
            model.getBlockslist().set(i,new Block(model.getBlockslist().get(i).gettext(),model.getBlockslist().get(i).getImagecolor()));
            model.getBlockslist().get(i).setLayoutX(x);
            model.getBlockslist().get(i).setLayoutY(y);
            model.getBlockslist().get(i).setX(x);
            model.getBlockslist().get(i).setY(y);

        }
        for(int i=0;i<model.getTokenslist().size();i++){
            double x=model.getTokenslist().get(i).getX();
            double y=model.getTokenslist().get(i).getY();

            if(model.getTokenslist().get(i).getClass().getName().equals("Model.Ball")) {

                model.getTokenslist().set(i, new Ball(model.getTokenslist().get(i).gettext()));
            }
            if(model.getTokenslist().get(i).getClass().getName().equals("Model.Bomb")){
                model.getTokenslist().set(i, new Bomb(""));
            }
            if(model.getTokenslist().get(i).getClass().getName().equals("Model.Shield")){
                model.getTokenslist().set(i, new Shield(""));
            }
            if(model.getTokenslist().get(i).getClass().getName().equals("Model.Magnet")){
                model.getTokenslist().set(i, new Magnet(""));
            }

            model.getTokenslist().get(i).setLayoutX(x);
            model.getTokenslist().get(i).setLayoutY(y);
            model.getTokenslist().get(i).setX(x);
            model.getTokenslist().get(i).setY(y);
        }
        for(int i=0;i<model.getWalllist().size();i++){
            for (int j=0;j<model.getWalllist().get(i).getLength();j++) {
                double x=model.getWalllist().get(i).getWalls().get(j).getX();
                double y=model.getWalllist().get(i).getWalls().get(j).getY();
                model.getWalllist().get(i).getWalls().set(j,new wall());
                model.getWalllist().get(i).getWalls().get(j).setLayoutX(x);
                model.getWalllist().get(i).getWalls().get(j).setLayoutY(y);
                model.getWalllist().get(i).getWalls().get(j).setX(x);
                model.getWalllist().get(i).getWalls().get(j).setY(y);
            }
        }
        Game gui=new Game(model,model.getBlockslist(),model.getTokenslist(),model.getWalllist(),L);
        gui.creatExistingGame();
        gui.show((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }
    public static void exit(){
        System.exit(0);
    }

}
