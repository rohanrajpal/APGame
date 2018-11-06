import Gui.StartPage;
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
        StartPage gui=new StartPage();
        gui.show(primaryStage);
    }
}
