package startup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import networking.authentication.AuthenticationClient;
import networking.authentication.SocketAuthenticationClient;
import networking.user.SocketUsersClient;
import networking.user.UsersClient;
import ui.common.Controller;
import ui.login.LoginController;
import ui.login.LoginVM;
import ui.register.RegisterController;
import ui.register.RegisterVM;
import ui.viewusers.ViewUsersController;
import ui.viewusers.ViewUsersVM;
import ui.welcome.WelcomeController;

import java.io.IOException;

public class ViewHandler
{
    private static Stage stage;

    public ViewHandler(Stage stage)
    {
        this.stage = stage;
    }

    public void start()
    {
        showView(ViewType.WELCOME);
        stage.show();
    }

    public static void showView(ViewType viewToShow)
    {
        try
        {
            switch (viewToShow)
            {
                case WELCOME -> openWelcomeView();
                case REGISTER -> openRegisterView();
                case LOGIN -> openLoginView();
                case VIEWUSERS -> openUsersOverview();
                default -> throw new RuntimeException("View not found.");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void openUsersOverview() throws IOException
    {
        UsersClient client = new SocketUsersClient();
        ViewUsersVM vm = new ViewUsersVM(client);
        ViewUsersController controller = new ViewUsersController(vm);
        String viewTitle = "Overview";
        String viewSubPath = "viewusers/ViewUsers.fxml";
        openView(viewTitle, viewSubPath, controller);
    }

    private static void openLoginView() throws IOException
    {
        AuthenticationClient service = new SocketAuthenticationClient();
        LoginVM vm = new LoginVM(service);
        LoginController controller = new LoginController(vm);
        String viewTitle = "Login";
        String viewSubPath = "login/Login.fxml";
        openView(viewTitle, viewSubPath, controller);
    }

    private static void openRegisterView() throws IOException
    {
        AuthenticationClient service = new SocketAuthenticationClient();
        RegisterVM vm = new RegisterVM(service);
        RegisterController controller = new RegisterController(vm);
        String viewTitle = "Register";
        String viewSubPath = "register/Register.fxml";
        openView(viewTitle, viewSubPath, controller);
    }

    private static void openWelcomeView() throws IOException
    {
        WelcomeController controller = new WelcomeController();
        String viewTitle = "Welcome";
        String viewSubPath = "welcome/Welcome.fxml";
        openView(viewTitle, viewSubPath, controller);
    }

    private static void openView(String viewTitle, String viewSubPath, Controller controller) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewHandler.class.getResource("../ui/" + viewSubPath));
        fxmlLoader.setControllerFactory(ignore -> controller);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(viewTitle);
        stage.setScene(scene);
    }
}
