package Model;

import java.io.Serializable;
import java.util.Date;

public class LeaderBoardelements implements Comparable<LeaderBoardelements>, Serializable {
    private int score;
    private Date Dateofcreation;
    public  LeaderBoardelements(int y,Date d){
        score=y;
        Dateofcreation=d;
    }
    @Override
    public int compareTo(LeaderBoardelements comparestu) {
        int compareage = ((LeaderBoardelements) comparestu).getscore();
        return compareage-this.score ;
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
