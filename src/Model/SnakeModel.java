package Model;

import java.io.Serializable;

public class SnakeModel implements Serializable {
    private int length;
    private double x;
    private double y;
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public int getlength(){
        return this.length;
    }
    public void setlength(int x){
        this.length=x;

    }
    public SnakeModel(int x,double xcor,double ycor){
        this.length=x;
        this.x=xcor;
        this.y=ycor;
    }
}
