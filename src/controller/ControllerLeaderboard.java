package controller;

import Gui.LeaderBoard;
import Model.LeaderBoardModel;
import Model.LeaderBoardelements;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.text.*;
import java.io.IOException;
import java.util.Date;

public class ControllerLeaderboard {
    @FXML
    private static GridPane Grid1;


    public static void leaderboard(ActionEvent event) throws IOException {
        LeaderBoardModel L=new LeaderBoardModel();
       Grid1=new GridPane();
//        try{
//            LeaderBoardModel temp=LeaderBoardModel.deserialize();
//            L.setLeaders(temp.getLeaders());
//        }
//        catch(Exception E) {
//            LeaderBoardModel.serialize(L);
//        }
        L.getLeaders().add(new LeaderBoardelements(1,100,new Date()));
        for(int i=0;i<L.getLeaders().size();i++){

            Grid1.add(new Label(Integer.toString(L.getLeaders().get(i).getIndex())),0,i+1);
            Grid1.add(new Label(Integer.toString(L.getLeaders().get(i).getscore())),1,i+1);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(L.getLeaders().get(i).getDateofcreation());
            Grid1.add(new Label(strDate),2,i+1);
        }
        LeaderBoard gui=new LeaderBoard();
        gui.show((Stage) ((Node) event.getSource()).getScene().getWindow());

    }
}
