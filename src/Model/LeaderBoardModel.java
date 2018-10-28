package Model;

import java.util.ArrayList;
import java.util.Date;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LeaderBoardModel {
    private ArrayList<LeaderBoardelements> Leaders;
    private static final long serialVersionUID=43L;
    public LeaderBoardModel() {
        Leaders = new ArrayList<>();
    }

    public ArrayList<LeaderBoardelements> getLeaders() {
        return Leaders;
    }

    public void setLeaders(ArrayList<LeaderBoardelements> leaders) {
        Leaders = leaders;
    }
    public	static	void serialize(LeaderBoardModel d) throws IOException	{
        ObjectOutputStream out	=	null;
        try	{
            FileOutputStream file = new FileOutputStream("storage/LeaderBoardModel.txt");
            out = new ObjectOutputStream(file);
            out.writeObject(d);
        }
        finally	{
            out.close();
        }
    }
    public	static	LeaderBoardModel deserialize() throws IOException,ClassNotFoundException {
        ObjectInputStream	in	=	null;
        LeaderBoardModel s1=new LeaderBoardModel();
        try	{

            FileInputStream file = new FileInputStream("storage/LeaderBoardModel.txt");
            in = new ObjectInputStream(file);
            s1	=	(LeaderBoardModel)in.readObject();
        }
        finally	{
            in.close();
        }
        return s1;
    }

}
