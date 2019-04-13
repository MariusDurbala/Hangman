package sample;

import constants.ApplicationConstants;
import helper.Utility;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    public Tab tabCategories;
    public TextField categoryName;
    public Button btnAddCategory;
    public ComboBox comboBoxCategories;

    public void loginAction(ActionEvent event) {
        if (Login.getText().equals(ApplicationConstants.BTN_LOGIN_TEXT)){

            if (Username.getText().equals(ApplicationConstants.USERNAME)&&
                    Password.getText().equals(ApplicationConstants.PASSWORD)){
                Login.setText(ApplicationConstants.BTN_LOGOUT_TEXT);
                lblPassword.setTextFill(Color.BLACK);
                lblUsername.setTextFill(Color.BLACK);
                tabPane.getTabs().get(2).setDisable(false);
                tabPane.getSelectionModel().select(tabCategories);
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
            tabPane.getTabs().get(2).setDisable(true);
        }
    }

    public void activateLoginTab(ActionEvent event) {
        tabPane.getTabs().get(1).setDisable(false);
        tabPane.getSelectionModel().select(tabLogin);
    }

    public void loginEnterKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            loginAction(null);
        }
    }

    public void addCategory(ActionEvent event) {
        if (!categoryName.getText().isEmpty()){
            Utility.createCategoryFile(categoryName.getText());
            fillCategoryComboBox(null);
            categoryName.clear();
        }
    }

    public void fillCategoryComboBox(Event event) {
        comboBoxCategories.getItems().clear();
        comboBoxCategories.getItems().addAll(Utility.listFilesWithoutExtensionsFromPath(ApplicationConstants.APP_FOLDER_DATA_PATH+
                "\\"+ApplicationConstants.CATEGORIES_FOLDER_NAME));
        //comboBoxCategories.getSelectionModel().select(0);
    }
}
