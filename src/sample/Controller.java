package sample;

import constants.ApplicationConstants;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import sun.rmi.runtime.Log;

public class Controller {
    public TextField Username;
    public PasswordField Password;
    public Button Login;
    public TabPane tabPane;
    public Tab tabPlay;
    public Tab tabLogin;
    public Label lblUsername;
    public Label lblPassword;

    public void loginAction(ActionEvent event) {
        if (Login.getText().equals(ApplicationConstants.BTN_LOGIN_TEXT)){

            if (Username.getText().equals(ApplicationConstants.USERNAME)&&
                    Password.getText().equals(ApplicationConstants.PASSWORD)){
                Login.setText(ApplicationConstants.BTN_LOGOUT_TEXT);
                lblPassword.setTextFill(Color.GREEN);
                lblUsername.setTextFill(Color.GREEN);
            }
            else {
                lblPassword.setTextFill(Color.RED);
                lblUsername.setTextFill(Color.RED);
            }
        }
        else {
            Login.setText(ApplicationConstants.BTN_LOGIN_TEXT);
            tabPane.getSelectionModel().select(tabPlay);
            tabPane.getTabs().get(1).setDisable(true);
        }
    }

    public void activateLoginTab(ActionEvent event) {
        tabPane.getTabs().get(1).setDisable(false);
        tabPane.getSelectionModel().select(tabLogin);
    }
}
