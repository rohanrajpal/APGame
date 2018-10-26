package Model;

import java.util.ArrayList;
import java.util.Date;

class LeaderBoardelements{
    private int index;
    private String name;
    private Date Dateofcreation;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateofcreation() {
        return Dateofcreation;
    }

    public void setDateofcreation(Date dateofcreation) {
        Dateofcreation = dateofcreation;
    }


}
public class LeaderBoardModel {
    private ArrayList<LeaderBoardelements> Leaders;

    public LeaderBoardModel() {
        Leaders = new ArrayList<>();
    }

    public ArrayList<LeaderBoardelements> getLeaders() {
        return Leaders;
    }

    public void setLeaders(ArrayList<LeaderBoardelements> leaders) {
        Leaders = leaders;
    }
}
