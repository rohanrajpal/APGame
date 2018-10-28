package controller;

import Gui.LeaderBoard;
import Gui.StartPage;
import Model.LeaderBoardModel;
import Model.LeaderBoardelements;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.text.*;
import java.io.IOException;
import java.util.Date;
import javafx.fxml.Initializable;;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerLeaderboard implements Initializable {
    @FXML
    private GridPane Grid1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void back(ActionEvent actionEvent){
        StartPage gui=new StartPage();
        gui.start((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());

    }
    public  void leaderboard() throws IOException {
        LeaderBoardModel L=new LeaderBoardModel();

//        try{
//            LeaderBoardModel temp=LeaderBoardModel.deserialize();
//            L.setLeaders(temp.getLeaders());
//        }
//        catch(Exception E) {
//            LeaderBoardModel.serialize(L);
//        }
        L.getLeaders().add(new LeaderBoardelements(1,100,new Date()));
        L.getLeaders().add(new LeaderBoardelements(2,200,new Date()));
        for(int i=0;i<L.getLeaders().size();i++){
            Label l=new Label();
            l.setText(Integer.toString(L.getLeaders().get(i).getIndex()));
            GridPane.setHalignment(l, HPos.CENTER);
            Grid1.add(l,0,i+1);
            Label l1=new Label();
            l1.setText(Integer.toString(L.getLeaders().get(i).getscore()));
            GridPane.setHalignment(l1, HPos.CENTER);
            Grid1.add(l1,1,i+1);
            Label l2=new Label();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(L.getLeaders().get(i).getDateofcreation());
            l2.setText(strDate);
            GridPane.setHalignment(l2, HPos.CENTER);
            Grid1.add(l2,2,i+1);
        }


    }
}
