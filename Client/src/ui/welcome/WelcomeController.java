package ui.welcome;

import startup.ViewHandler;
import startup.ViewType;
import ui.common.Controller;

public class WelcomeController implements Controller
{


    public WelcomeController()
    {
    }

    public void openLoginView()
    {
    }

    public void openRegisterView()
    {
        ViewHandler.showView(ViewType.REGISTER);
    }
}
