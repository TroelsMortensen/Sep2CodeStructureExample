package ui.popup;

import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopupController
{
    private final Stage stage;
    private final String message;
    public Label messageLabel;

    public PopupController(Stage stage, String message){
        this.stage = stage;
        this.message = message;
    }

    public void initialize(){
        messageLabel.setText(message);
    }

    public void onClose()
    {
        stage.close();
    }
}
