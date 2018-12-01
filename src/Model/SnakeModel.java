package Model;

import java.io.Serializable;

public class SnakeModel implements Serializable {
    private int length;
    private double x;
    private double y;
    /**
     * function to get x co-ordinate of snake
     * @return
     */

    public double getX() {
        return x;
    }
    /**
     * function to set x co-ordinate of snake
     */

    public void setX(double x) {
        this.x = x;
    }
    /**
     * function to get y co-ordinate of snake
     * @return
     */

    public double getY() {
        return y;
    }
    /**
     * function to set y co-ordinate of snake
     */
    public void setY(double y) {
        this.y = y;
    }
    /**
     * function to get length of snake
     * @return
     */
    public int getlength(){
        return this.length;
    }
    /**
     * function to set length of snake
     */

    public void setlength(int x){
        this.length=x;

    }

    /**
     * SnakeModel to set values of the snake head
     * @param x
     * @param xcor
     * @param ycor
     */
    public SnakeModel(int x,double xcor,double ycor){
        this.length=x;
        this.x=xcor;
        this.y=ycor;
    }
}
