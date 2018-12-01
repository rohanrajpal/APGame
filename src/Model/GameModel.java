package Model;

import java.io.*;
import java.util.ArrayList;

public class GameModel implements Serializable {
    private SnakeModel snake;
    private int latestpoints;
    public SnakeModel getSnake() {
        return snake;
    }
    private ArrayList<Block> blockslist;
    private ArrayList<Wallswrapper> walllist;
    private ArrayList<Token> tokenslist;
    private int points;
    public int startpagestage;
    /**
     * function to set the last points recorded by a instance of the game gui to update leaderboard
     */
    public int getPoints() {
        return points;
    }
    /**
     * function to set the last points recorded by a instance of the game gui to update leaderboard
     * @param points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * function to set the last points recorded by a instance of the game gui
     * @param points
     */
    public void  setlatestpoints(int points) {
        this.latestpoints = points;
    }

    /**
     * function to get the last points recorded by a instance of the game gui
     * @return
     */
    public int getlatestPoints(){
        return latestpoints;
    }
    /**
     * function to get the available blocks
     * @return
     */
    public ArrayList<Block> getBlockslist() {
        return blockslist;
    }
    /**
     * function to set the available blocks
     * @return
     */
    public void setBlockslist(ArrayList<Block> blockslist) {
        this.blockslist = blockslist;
    }
    /**
     * function to get the available walls
     * @return
     */
    public ArrayList<Wallswrapper> getWalllist() {
        return walllist;
    }
    /**
     * function to set the available walls
     * @return
     */
    public void setWalllist(ArrayList<Wallswrapper> walllist) {
        this.walllist = walllist;
    }
    /**
     * function to get the available tokens
     * @return
     */
    public ArrayList<Token> getTokenslist() {
        return tokenslist;
    }
    /**
     * function to set the available tokens
     * @return
     */

    public void setTokenslist(ArrayList<Token> tokenslist) {
        this.tokenslist = tokenslist;
    }
    /**
     * function to set the current snake model
     * @return
     */
    public void setSnake(SnakeModel snake) {
        this.snake = snake;
    }

    /**
     * Constructor to create a new game model
     * @param snake
     * @param Points
     * @param latest
     */
    public GameModel(SnakeModel snake,int Points,int latest,int choice) {
        this.snake = snake;
        snake.setlength(5);
        blockslist=new ArrayList<>();
        walllist=new ArrayList<>();
        tokenslist=new ArrayList<>();
        this.points=Points;
        this.latestpoints=latest;
        this.startpagestage=choice;
    }

    /**
     * function to serialize the cuurent gamemodel to a file
     * @param d
     * @throws IOException
     */
    public	static	void serialize(GameModel d) throws IOException {
        ObjectOutputStream out	=	null;
        try	{

            FileOutputStream file = new FileOutputStream("GameModelStore.txt");
            out = new ObjectOutputStream(file);
            out.writeObject(d);
        }
        finally	{
            out.close();
        }
    }

    /**
     * function to deserialize a file to get the current game model
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public	static	GameModel deserialize() throws IOException,ClassNotFoundException {
        ObjectInputStream in	=	null;
        GameModel s1=new GameModel(new SnakeModel(5,168,375),0,0,1);
        try	{

            FileInputStream file = new FileInputStream("GameModelStore.txt");


            in = new ObjectInputStream(file);


            s1	=	(GameModel)in.readObject();

        }
        finally	{
            in.close();
        }
        return s1;
    }
}
