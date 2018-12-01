package Model;

import java.io.Serializable;
import java.util.Date;

public class LeaderBoardelements implements Comparable<LeaderBoardelements>, Serializable {
    private int score;
    private Date Dateofcreation;

    /**
     * Constructor to set the LeaderBoard values for a single entry
     * @param y
     * @param d
     */
    public  LeaderBoardelements(int y,Date d){
        score=y;
        Dateofcreation=d;
    }

    /**
     * Comparatot to compare two LeaderBoard records
     * @param comparestu
     * @return
     */
    @Override
    public int compareTo(LeaderBoardelements comparestu) {
        int compareage = ((LeaderBoardelements) comparestu).getscore();
        return compareage-this.score ;
    }

    /**
     * function to get score of a LeaderBoard record
     * @return
     */
    public int getscore() {
        return score;
    }
    /**
     * function to set score of a LeaderBoard record
     */
    public void setscore(int score) {
        this.score = score;
    }
    /**
     * function to get date of creation of a LeaderBoard record
     * @return
     */

    public Date getDateofcreation() {
        return Dateofcreation;
    }
    /**
     * function to set date of creation  of a LeaderBoard record
     */
    public void setDateofcreation(Date dateofcreation) {
        Dateofcreation = dateofcreation;
    }


}
