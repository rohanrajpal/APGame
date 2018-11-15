package controller;

import Gui.LeaderBoard;
import Gui.StartPage;
import Model.LeaderBoardModel;
import Model.LeaderBoardelements;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import view.ImageButton;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

;


public class ControllerLeaderboard implements Initializable {
    @FXML
    private GridPane Grid1;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public static void back(MouseEvent event){
        StartPage gui=new StartPage();
        gui.show((Stage) ((Node) event.getSource()).getScene().getWindow());

    }
    public static void serializegameondeath(int points,Stage stage)throws IOException,ClassNotFoundException{
        LeaderBoardModel L= LeaderBoardModel.deserialize();
        if(L.getLeaders().size()>=10) {
            if (points > L.getLeaders().get(L.getLeaders().size() - 1).getscore()) {
                L.getLeaders().remove(L.getLeaders().size() - 1);
                L.getLeaders().add(new LeaderBoardelements(points, new Date()));
                java.util.Collections.sort(L.getLeaders());
            }
        }
        else{
            L.getLeaders().add(new LeaderBoardelements(points, new Date()));
            java.util.Collections.sort(L.getLeaders());
        }
        LeaderBoardModel.serialize(L);
        StartPage gui=new StartPage();
        gui.show(stage);
    }
    public static void addpoints(int points,MouseEvent event,String togo) throws IOException, ClassNotFoundException {
        LeaderBoardModel L= LeaderBoardModel.deserialize();
        if(L.getLeaders().size()>=10) {
            if (points > L.getLeaders().get(L.getLeaders().size() - 1).getscore()) {
                L.getLeaders().remove(L.getLeaders().size() - 1);
                L.getLeaders().add(new LeaderBoardelements(points, new Date()));
                java.util.Collections.sort(L.getLeaders());
            }
        }
        else{
            L.getLeaders().add(new LeaderBoardelements(points, new Date()));
            java.util.Collections.sort(L.getLeaders());
        }
        LeaderBoardModel.serialize(L);
        if(togo.equals("startpage")) {
            back(event);
        }
        else if(togo.equals("restart")){
            ControllerMainMenu.start(event);
        }
    }
    public  void leaderboard() throws IOException {

        LeaderBoardModel L=new LeaderBoardModel();

        try{
            LeaderBoardModel temp=LeaderBoardModel.deserialize();
            L.setLeaders(temp.getLeaders());
        }
        catch(Exception E) {

            LeaderBoardModel.serialize(L);
        }

        ImageButton ib=new ImageButton("/view/Helper_images/back-button.png");
        ib.setInterim("back");
        GridPane.setHalignment(ib, HPos.CENTER);
        Grid1.add(ib,0,0);
        for(int i=0;i<L.getLeaders().size();i++){
            Label l=new Label();
            l.setText(Integer.toString(i+1));
            l.setTextFill(Paint.valueOf("#eba478"));
            GridPane.setHalignment(l, HPos.CENTER);
            Grid1.add(l,0,i+1);
            Label l1=new Label();
            l1.setText(Integer.toString(L.getLeaders().get(i).getscore()));
            l1.setTextFill(Paint.valueOf("#eba478"));
            GridPane.setHalignment(l1, HPos.CENTER);
            Grid1.add(l1,1,i+1);
            Label l2=new Label();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(L.getLeaders().get(i).getDateofcreation());
            l2.setText(strDate);
            l2.setTextFill(Paint.valueOf("#eba478"));
            GridPane.setHalignment(l2, HPos.CENTER);
            Grid1.add(l2,2,i+1);
        }


    }
}
