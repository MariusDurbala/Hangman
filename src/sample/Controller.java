    package sample;

    import constants.ApplicationConstants;
    import helper.CategoryParser;
    import helper.Utility;
    import javafx.event.ActionEvent;
    import javafx.event.Event;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.input.KeyCode;
    import javafx.scene.input.KeyEvent;
    import javafx.scene.paint.Color;
    import model.Category;
    import model.Word;

    import java.io.IOException;
    import java.util.Random;

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
        public Label lblWordTabCategory;
        public TextField txtNewWord;
        public TextField txtNewHint;
        public Button btnAddWord;
        public Label lblCategoryNameComboBox;
        public CheckBox chkBoxCleanWords;
        public ComboBox cmbBoxSelectCategoryPlay;
        public Button btnPlay;
        public TextField txtWord2Guess;
        public Button btnGuess;
        public TextField txtGuess;

        @FXML
        public void initialize(){
            tabPane.getTabs().remove(tabLogin);
            //tabPane.getTabs().remove(tabCategories);
        }
        public void loginAction(ActionEvent event) {
            if (Login.getText().equals(ApplicationConstants.BTN_LOGIN_TEXT)){

                if (Username.getText().equals(ApplicationConstants.USERNAME)&&
                        Password.getText().equals(ApplicationConstants.PASSWORD)){
                    Login.setText(ApplicationConstants.BTN_LOGOUT_TEXT);

                    lblPassword.setTextFill(Color.BLACK);
                    lblUsername.setTextFill(Color.BLACK);

                    tabPane.getTabs().add(tabCategories);

                    tabPane.getSelectionModel().select(tabCategories);

                    Username.setEditable(false);
                    Password.setEditable(false);
                    //Username.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD,12));

                }
                else {
                    lblPassword.setTextFill(Color.RED);
                    lblUsername.setTextFill(Color.RED);
                }
            }
            else {
                Login.setText(ApplicationConstants.BTN_LOGIN_TEXT);
                tabPane.getTabs().remove(tabLogin);
                tabPane.getTabs().remove(tabCategories);
                tabPane.getSelectionModel().select(tabPlay);

                Username.setEditable(true);
                Password.setEditable(true);

                Username.clear();
                Password.clear();
            }
        }

        public void activateLoginTab(ActionEvent event) {
            tabPane.getTabs().add(tabLogin);
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
                comboBoxCategories.getSelectionModel().select(categoryName.getText());
                categoryName.clear();

            }
        }

        public void fillCategoryComboBox(Event event) {
            if (tabPlay.isSelected()){
                updatedComboBox(cmbBoxSelectCategoryPlay);
            }
            else if (tabCategories.isSelected()){
                updatedComboBox(comboBoxCategories);
            }
        }
        public void updatedComboBox(ComboBox comboBoxParam){
            comboBoxParam.getItems().clear();
            try{
                comboBoxParam.getItems().addAll(Utility.listFilesWithoutExtensionsFromPath(
                        ApplicationConstants.APP_FOLDER_DATA_PATH+
                        "\\"+ApplicationConstants.CATEGORIES_FOLDER_NAME));
            }
            catch (Exception e){

            }
        }

        public void handleAddWord(ActionEvent event) {
            boolean chkBoxCleanActive = chkBoxCleanWords.isSelected();
            if (comboBoxCategories.getSelectionModel().getSelectedIndex()== -1){
                lblCategoryNameComboBox.setTextFill(Color.RED);
            }
            else {
                lblCategoryNameComboBox.setTextFill(Color.BLACK);
                if (!txtNewWord.getText().isEmpty()){
                    lblWordTabCategory.setTextFill(Color.BLACK);
                    try {
                        Category category = CategoryParser.parseCategoryFile(chkBoxCleanActive,ApplicationConstants.APP_FOLDER_DATA_PATH+
                                "\\"+ApplicationConstants.CATEGORIES_FOLDER_NAME+
                                "\\"+comboBoxCategories.getSelectionModel().getSelectedItem().toString()+
                                ApplicationConstants.CATEGORY_FILE_EXTENSION);

                        if (category.wordExists(txtNewWord.getText())){
                            lblWordTabCategory.setTextFill(Color.RED);
                        }
                        else {
                            if (chkBoxCleanActive){
                                Utility.cleanWordsInCategory(category.getWordList(),comboBoxCategories.getSelectionModel().getSelectedItem().toString());
                            }
                            lblWordTabCategory.setTextFill(Color.BLACK);
                            Utility.addWordInCategory(category.getLastIdOfWord() + 1,
                                    txtNewWord.getText(),
                                    txtNewHint.getText(),
                                    comboBoxCategories.getSelectionModel().getSelectedItem().toString());

                            comboBoxCategories.getSelectionModel().select(-1);
                            txtNewWord.clear();
                            txtNewHint.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    lblWordTabCategory.setTextFill(Color.RED);
                }
            }
        }
        Word wordToGuess;
        String secretWord = "";
        public void handlePlay(ActionEvent event) {
            try {
                Category category = CategoryParser.parseCategoryFile(false, ApplicationConstants.APP_FOLDER_DATA_PATH +
                        "\\" + ApplicationConstants.CATEGORIES_FOLDER_NAME +
                        "\\" + cmbBoxSelectCategoryPlay.getSelectionModel().getSelectedItem().toString() +
                        ApplicationConstants.CATEGORY_FILE_EXTENSION);
                Random rand = new Random();
                int randomNumber = rand.nextInt(category.getLastIdOfWord());
                wordToGuess = category.getWordList().get(randomNumber);

                for (int i = 0; i < wordToGuess.getName().length(); i++) {
                    if (wordToGuess.getName().charAt(i) !=' ') {
                        secretWord += "_";
                    }
                }
                txtWord2Guess.setText(secretWord);
            }
            catch (Exception e){

            }
        }

        public void handleGuess(ActionEvent event) {
            StringBuilder sb= new StringBuilder(secretWord);
            if (wordToGuess.getName().contains(txtGuess.getText())){
                for (int i = 0; i < wordToGuess.getName().length(); i++) {
                    if (wordToGuess.getName().charAt(i) ==txtGuess.getText().toCharArray()[0]) {
                        sb.setCharAt(i,txtGuess.getText().toCharArray()[0]);
                    }
                    else {
                        secretWord+=" ";
                    }
                }
                secretWord=sb.toString();
                txtWord2Guess.setText(secretWord);
            }
            txtGuess.clear();
        }
    }
