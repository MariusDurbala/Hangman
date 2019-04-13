package sample;

import constants.ApplicationConstants;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    public TextField Username;
    public TextField Password;
    public Button Login;

    public void loginAction(ActionEvent event) {
        if (Login.getText().equals(ApplicationConstants.BTN_LOGIN_TEXT)){
            Login.setText(ApplicationConstants.BTN_LOGOUT_TEXT);
        }
        else {
            Login.setText(ApplicationConstants.BTN_LOGIN_TEXT);
        }
    }
}
