import Gui.Game;
import Gui.StartPage;
import Model.GameModel;
import Model.SnakeModel;
import javafx.stage.Stage;
import javafx.application.*;
import javafx.stage.StageStyle;

public class SnakevsBloc extends Application
{
    public	static	void main(String[]	args)
    {
	    launch(args);
    }
    @Override
        public	void	start(Stage	primaryStage)	{
        try{
            primaryStage.initStyle(StageStyle.TRANSPARENT);
        }
        catch (Exception e){

        }
        GameModel g=new GameModel(new SnakeModel(5,178,375),0,0,0);
        try {
             g = GameModel.deserialize();
        }
        catch (Exception e){

        }
        System.out.println(g.startpagestage);
        StartPage gui=new StartPage(g.startpagestage);
        gui.show(primaryStage);
    }
}
