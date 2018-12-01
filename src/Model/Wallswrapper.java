package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Wallswrapper implements Serializable {

    private ArrayList<wall> walls;
    private int length;

    /**
     * returns the list of walls for a head walls wrapper
     * @return
     */
    public ArrayList<wall> getWalls() {
        return walls;
    }
    /**
     * sets the list of walls for a head walls wrapper
     */
    public void setWalls(ArrayList<wall> walls) {
        this.walls = walls;
    }

    /**
     * Constructor for walls wrapper
     * @param len
     */
    public Wallswrapper(int len){
        walls=new ArrayList<>();
        for(int i=0;i<len;i++){
            this.walls.add(new wall());
        }
        this.length=len;
    }

    /**
     * function to addwall
     */
    public void addwall(){
            this.walls.add(new wall());
            this.length=this.length+1;
    }

    /**
     * function to get length
     * @return
     */
    public int getLength() {
        return length;
    }

    /**
     * function to set length of wall
     * @param length
     */
    public void setLength(int length) {

        this.length = length;
    }

}
