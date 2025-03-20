package startup;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartApp extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        ViewHandler viewHandler = new ViewHandler(stage);
        viewHandler.start();
    }

    public static void main(String[] args)
    {
        StartApp.launch(args);
//        Request request = new Request("auth", "register", new RegisterUserRequest("trmo@via.dk", "troels1234", "troels", "mortensen"));
//        Object o = SocketService.sendRequest(request);
        ;
    }
}
