package Model;

import java.util.ArrayList;

public class Wallswrapper {

    private ArrayList<wall> walls;
    private int length;

    public ArrayList<wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<wall> walls) {
        this.walls = walls;
    }

    public Wallswrapper(int len){
        walls=new ArrayList<>();
        for(int i=0;i<len;i++){
            this.walls.add(new wall());
        }
        this.length=len;
    }
    public void addwall(){
            this.walls.add(new wall());
            this.length=this.length+1;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {

        this.length = length;
    }

}
