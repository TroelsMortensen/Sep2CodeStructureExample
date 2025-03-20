package startup;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartApp extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setHeight(400);
        stage.setWidth(600);
        ViewHandler viewHandler = new ViewHandler(stage);
        viewHandler.start();
    }

    public static void main(String[] args)
    {
        StartApp.launch(args);
    }
}
