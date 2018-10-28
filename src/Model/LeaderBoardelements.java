package Model;

import java.util.Date;

public class LeaderBoardelements{
    private int index;
    private int score;
    private Date Dateofcreation;
    public  LeaderBoardelements(int x,int y,Date d){
        index=x;
        score=y;
        Dateofcreation=d;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getscore() {
        return score;
    }

    public void setscore(int score) {
        this.score = score;
    }

    public Date getDateofcreation() {
        return Dateofcreation;
    }

    public void setDateofcreation(Date dateofcreation) {
        Dateofcreation = dateofcreation;
    }


}
