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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public void  setlatestpoints(int points) {
        this.latestpoints = points;
    }
    public int getlatestPoints(){
        return latestpoints;
    }

    private int points;
    public ArrayList<Block> getBlockslist() {
        return blockslist;
    }

    public void setBlockslist(ArrayList<Block> blockslist) {
        this.blockslist = blockslist;
    }

    public ArrayList<Wallswrapper> getWalllist() {
        return walllist;
    }

    public void setWalllist(ArrayList<Wallswrapper> walllist) {
        this.walllist = walllist;
    }

    public ArrayList<Token> getTokenslist() {
        return tokenslist;
    }

    public void setTokenslist(ArrayList<Token> tokenslist) {
        this.tokenslist = tokenslist;
    }

    public void setSnake(SnakeModel snake) {
        this.snake = snake;
    }



    public GameModel(SnakeModel snake,int Points,int latest) {
        this.snake = snake;
        snake.setlength(5);
        blockslist=new ArrayList<>();
        walllist=new ArrayList<>();
        tokenslist=new ArrayList<>();
        this.points=Points;
        this.latestpoints=latest;
    }
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
    public	static	GameModel deserialize() throws IOException,ClassNotFoundException {
        ObjectInputStream in	=	null;
        GameModel s1=new GameModel(new SnakeModel(5,168,375),0,0);
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
