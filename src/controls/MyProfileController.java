package controls;

import backend.FilesBackend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.Profile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

import static java.lang.Integer.parseInt;

public class MyProfileController extends AnchorPane {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNo;
    @FXML
    private TextField eMailField;
    @FXML
    private TextField address;
    @FXML
    private TextField zipCode;
    @FXML
    private TextField city;
    @FXML
    private RadioButton apartment;
    @FXML
    private RadioButton house;
    @FXML
    private TextField level;

    @FXML
    private TextField cardNumber;
    @FXML
    private TextField cardYear;
    @FXML
    private TextField cardMonth;
    @FXML
    private TextField personalNumber;
    @FXML
    private RadioButton invoice;
    @FXML
    private RadioButton cardPayment;
    @FXML
    private Label cardNo;
    @FXML
    private Label cardNoHelp;
    @FXML
    private Label cardDate;
    @FXML
    private Label cardDateHelp;
    @FXML
    private Label personNo;
    @FXML
    private Label personNoHelp;
    @FXML
    private Line slashLine;
    @FXML
    private Button saved;
    @FXML
    private Button saveButton;
    @FXML
    private Label errorMessage;
    @FXML private Label errorPhoneNo;
    @FXML private Label errorEmail;
    @FXML private Label errorDate;
    @FXML private Label errorZipCode;
    @FXML private Label errorCardNo;
    @FXML private Label errorPersonalNo;



    private ToggleGroup paymentMethod = new ToggleGroup();
    private ToggleGroup typeOfHousing = new ToggleGroup();
    private Profile profile;
    private boolean cardSelected;
    private List<Node> nodes = new LinkedList<>();


    private static MyProfileController myProfileController;

    private MyProfileController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/profile_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        /* Set selected startvalues for radio buttons, will change if anything else specified in profile*/

        apartment.setSelected(true);
        profile = Profile.getInstance();


        initToggleGroups();
        initProfileForm();
        initTextFormatters();
        addChangeListners();

        /*Set 'save' button visible but disabled at start*/

        saveButton.setDisable(true);
        saveButton.setVisible(true);
        saved.setVisible(false);
        errorMessage.setVisible(false);


         //ONLY FOR TESTING PURPOSE
        boolean a = validCardMonth();
        boolean b = validCardNumber();
        boolean c = validCardYear();
        boolean d = validEmail();
        boolean e = validLevel();
        boolean f = validPersonalNumber();
        boolean g = validPhoneNo();
        boolean h = validZipCode();



    }

    public static MyProfileController getInstance() {
        if (myProfileController == null)
            myProfileController = new MyProfileController();
        return myProfileController;
    }


    private void initTextFormatters() {
        /* Apply text filters on textfields */

        UnaryOperator<TextFormatter.Change> onlyDigitsFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        UnaryOperator<TextFormatter.Change> onlyLettersFilter = change -> {
            String text = change.getText();
            if (text.matches("[a-ö]*") || text.matches("[A-Ö]*")) {
                return change;
            }
            return null;
        };


        TextFormatter<String> phoneNoFormat = new TextFormatter<>(onlyDigitsFilter);
        phoneNo.setTextFormatter(phoneNoFormat);
        limitTextLength(phoneNo, 10);


        TextFormatter<String> cardMonthFormat = new TextFormatter<>(onlyDigitsFilter);
        cardMonth.setTextFormatter(cardMonthFormat);
        limitTextLength(cardMonth, 2);

        TextFormatter<String> cardYearFormat = new TextFormatter<>(onlyDigitsFilter);
        cardYear.setTextFormatter(cardYearFormat);


        TextFormatter<String> cardNoFormat = new TextFormatter<>(onlyDigitsFilter);
        cardNumber.setTextFormatter(cardNoFormat);


        TextFormatter<String> zipCodeFormat = new TextFormatter<>(onlyDigitsFilter);
        zipCode.setTextFormatter(zipCodeFormat);

        TextFormatter<String> firstNameFormat = new TextFormatter<>(onlyLettersFilter);
        firstName.setTextFormatter(firstNameFormat);


        TextFormatter<String> lastNameFormat = new TextFormatter<>(onlyLettersFilter);
        lastName.setTextFormatter(lastNameFormat);

        TextFormatter<String> cityFormat = new TextFormatter<>(onlyLettersFilter);
        city.setTextFormatter(cityFormat);


        TextFormatter<String> personalNumberFormat = new TextFormatter<>(onlyDigitsFilter);
        personalNumber.setTextFormatter(personalNumberFormat);

    }

    private void addChangeListners() {
        limitTextLength(zipCode, 5);
        limitTextLength(cardNumber, 16);
        limitTextLength(personalNumber, 12);
        limitTextLength(phoneNo, 10);
        limitTextLength(cardMonth, 2);
        limitTextLength(cardYear, 2);
        addChangeListner(firstName);
        addChangeListner(lastName);
        addChangeListner(address);
        addChangeListner(eMailField);
        addChangeListner(city);
        addChangeListner(cardPayment);
        addChangeListner(invoice);
        addChangeListner(house);
        addChangeListner(apartment);
        addChangeListner(level);
    }

    private void initToggleGroups() {
        cardPayment.setToggleGroup(paymentMethod);
        invoice.setToggleGroup(paymentMethod);
        apartment.setToggleGroup(typeOfHousing);
        house.setToggleGroup(typeOfHousing);
    }

    private void initProfileForm() {
        profile = FilesBackend.getInstance().readProfileFromFile();
        if(profile == null){
            return;}

        else {
            if(!firstName.getText().equals(""))
                firstName.setText(profile.getFirstName());
            else
                firstName.setPromptText(Profile.getInputPromptName());
            if(!lastName.getText().equals(""))
                lastName.setText(profile.getLastName());
            else
                lastName.setPromptText(Profile.getInputPromptLastname());
            if(!phoneNo.getText().equals(""))
                phoneNo.setText(profile.getMobilePhoneNumber());
            else
                phoneNo.setPromptText(Profile.getInputPromptPhoneNo());
            if(!address.getText().equals(""))
                address.setText(profile.getAddress());
            else
                address.setPromptText(Profile.getInputPromptAddress());
            if(!eMailField.getText().equals(""))
                eMailField.setText(profile.getEmail());
            else
                eMailField.setPromptText(Profile.getInputPromptEmail());
            if(!city.getText().equals(""))
                city.setText(profile.getCity());
            else
                city.setPromptText(Profile.getInputPromptCity());
            if(!zipCode.equals(""))
                zipCode.setText(profile.getPostCode());
            else
                zipCode.setPromptText(Profile.getInputPromptZipCode());
            if(!level.getText().equals(""))
                level.setText(Integer.toString(profile.getLevel()));
            else
                level.setPromptText(Profile.getInputPromptLevel());
            if(!personalNumber.getText().equals("")){
                personalNumber.setText(profile.getPersonalNumber());
            }
            else
                personalNumber.setPromptText(Profile.getInputPromptPersonalNo());



            house.setSelected(profile.isHouse());

            if (profile.isCardPayment()) {
                cardSelected();
                cardPayment.setSelected(true);
                personalNumber.setStyle("fx-text-fill: primary-grey");

                if (!(profile.getCardNumber().equals("")))
                    cardNumber.setText(profile.getCardNumber());
                else{
                    cardNumber.setPromptText(Profile.getInputPromptCardNo());
                }
                if (profile.getValidYear() != 0 && profile.getValidMonth() != 0) {
                    cardYear.setText(Integer.toString(profile.getValidYear()));
                    cardMonth.setText(Integer.toString(profile.getValidMonth()));
                }else{
                    cardYear.setPromptText(Profile.getInputPromptValidYear());
                    cardMonth.setPromptText(Profile.getInputPromptValidMonth());
                }
                /* If invoice payment selected */
            } else {
                invoiceSelected();
                invoice.setSelected(true);
                if (!(profile.getPersonalNumber().equals("")))
                    personalNumber.setText(profile.getPersonalNumber());
                if (!profile.getCardNumber().equals("")) {
                    cardNumber.setStyle("fx-text-fill: primary-grey");
                }
                if (profile.getValidYear() != 0 && profile.getValidMonth() != 0) {
                    cardYear.setStyle("fx-text-fill: primary-grey");
                    cardMonth.setStyle("fx-text-fill: primary-grey");
                }
            }
        }
    }



    @FXML
    private void invoiceSelected() {
        cardNumber.setDisable(true);
        cardMonth.setDisable(true);
        cardYear.setDisable(true);
        personalNumber.setDisable(false);
        personalNumber.focusedProperty();

        //make labels grey
        cardDate.setStyle("-fx-text-fill: grey-primary");
        cardDateHelp.setStyle("-fx-text-fill: grey-primary");
        cardNo.setStyle("-fx-text-fill: grey-primary");
        cardNoHelp.setStyle("-fx-text-fill: grey-primary");

        slashLine.setStyle("-fx-stroke: grey-primary");

        //Style active elements
        personNo.setStyle("-fx-text-fill: black");
        personNoHelp.setStyle("-fx-text-fill: black");
        personalNumber.setStyle("-fx-text-fill: black;");
        cardSelected = false;

    }

    @FXML
    private void cardSelected() {
        cardNumber.focusedProperty();
        personalNumber.setDisable(true);
        cardNumber.setDisable(false);
        cardMonth.setDisable(false);
        cardYear.setDisable(false);

        //make active labels black
        cardDate.setStyle("-fx-text-fill: black");
        cardDateHelp.setStyle("-fx-text-fill: black");
        cardNo.setStyle("-fx-text-fill: black");
        cardNoHelp.setStyle("-fx-text-fill: black");

        //Style inactive elements
        personNo.setStyle("-fx-text-fill: grey-primary");
        personNoHelp.setStyle("-fx-text-fill: grey-primary");
        personalNumber.setStyle("-fx-text-fill: grey-primary;");

        cardSelected = true;

    }

    @FXML
    private void houseSelected() {
        level.setDisable(true);
    }

    private void limitTextLength(TextField field, int limit) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (newValue.intValue() > limit) {
                        field.setText(field.getText().substring(0, limit));
                        update();


                    } else if (newValue.intValue() == limit || newValue.intValue() == 0) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
                        if (isValidLength(field, limit)) {
                            update();
                        }

                    }
                } else {
                    if (newValue.intValue() < limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
                        update();
                    }

                }
            }
        });
    }

    public void addChangeListner(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    update();
                }

            }
        });
    }
    private void update(){
        if (allFieldsValid()) {
            enableSaveButton();
            styleValidFields();
            errorMessage.setVisible(false);
        }
        styleInvalidFields();
        errorMessage.setVisible(true);
    }
    private void styleValidFields() {
        if(validCardMonth()) {
            cardMonth.getStyleClass().clear();
            cardMonth.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }if(validCardNumber()){
            cardNumber.getStyleClass().clear();
            cardNumber.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }if(validCardYear()){
            cardYear.getStyleClass().clear();
            cardYear.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }if(validEmail()){
            eMailField.getStyleClass().clear();
            eMailField.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }if (validLevel()){
            level.getStyleClass().clear();
            level.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }if(validPersonalNumber()){
            personalNumber.getStyleClass().clear();
            personalNumber.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }if(validPhoneNo()){
            phoneNo.getStyleClass().clear();
            phoneNo.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }if(validZipCode()){
            zipCode.getStyleClass().clear();
            zipCode.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
        }

    }

    private void styleInvalidFields(){
        if(!validCardMonth()) {
            cardMonth.getStyleClass().clear();
            cardMonth.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }if(!validCardNumber()){
            cardNumber.getStyleClass().clear();
            cardNumber.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }if(!validCardYear()){
            cardYear.getStyleClass().clear();
            cardYear.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }if(!validEmail()){
            eMailField.getStyleClass().clear();
            eMailField.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }if (!validLevel()){
            level.getStyleClass().clear();
            level.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }if(!validPersonalNumber()){
            personalNumber.getStyleClass().clear();
            personalNumber.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }if(!validPhoneNo()){
            phoneNo.getStyleClass().clear();
            phoneNo.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }if(!validZipCode()){
            zipCode.getStyleClass().clear();
            zipCode.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        }
    }


    //when hitting save button
    @FXML
    private void save() {
        updateProfile();
        changeToSavedButton(true);
    }

    private void updateProfile() {
        profile.setFirstName(firstName.getText());
        profile.setLastName(lastName.getText());
        profile.setMobilePhoneNumber(phoneNo.getText());
        profile.setAddress(address.getText());
        profile.setCity(city.getText());
        profile.setPostCode(zipCode.getText());
        profile.setCardNumber(cardNumber.getText());
        profile.setPersonalNumber(personalNumber.getText());
        profile.setCardPayment(cardSelected);
        profile.setEmail(eMailField.getText());
        try {
            profile.setLevel(parseInt(level.getText()));
            profile.setValidMonth(parseInt(cardMonth.getText()));
            profile.setValidYear(parseInt(cardYear.getText()));
        }catch (NumberFormatException e){
            System.out.println("Failed to parse string to int" + e);
        }


        FilesBackend.getInstance().saveProfile(profile);

    }

    private void enableSaveButton() {
        saveButton.setDisable(false);
        saveButton.setVisible(true);
    }
    private void showDisabledSave(){
        saveButton.setDisable(true);
        saveButton.setVisible(true);
        saved.setVisible(false);
    }

    private boolean isValidLength(TextField textField, int limit) {
        return (textField.getText().length() == limit || textField.getText().length() == 0);
    }

    private void changeToSavedButton(boolean b) {
        saveButton.setVisible(!b);
        saved.setVisible(b);
    }

    private boolean allFieldsValid() {
        return (validEmail() && validZipCode() && validCardNumber() && validPersonalNumber() && validCardYear() && validCardMonth() && validPhoneNo() && validLevel());
    }

    //Bad practice, not following command query principle....
    private boolean validEmail() {
        if ((eMailField.getText().contains("@") && eMailField.getText().contains(".")) || eMailField.getText().equals("")) {
            errorEmail.setVisible(false);
            return true;
        }
        errorEmail.setVisible(true);
        return false;
    }

    private boolean validZipCode() {
        if (zipCode.getText().length() == 5 || zipCode.getText().equals("")){
            errorZipCode.setVisible(false);
            return true;
        }
        errorZipCode.setVisible(true);
        return false;
    }

    private boolean validCardNumber() {
        if (cardNumber.getText().length() == 16 || cardNumber.getText().equals("") || cardNumber.isDisabled()){
            errorCardNo.setVisible(false);
            return true;
        }
        errorCardNo.setVisible(true);
        return false;
    }

    private boolean validPersonalNumber() {
        if (personalNumber.getText().length() == 12 || personalNumber.getText().equals("") || personalNumber.isDisabled()){
            errorPersonalNo.setVisible(false);
            return true;}
        errorPersonalNo.setVisible(true);
        return false;
    }

    private boolean validPhoneNo() {
        if (phoneNo.getText().length() == 10 || phoneNo.getText().equals("")){
            errorPhoneNo.setVisible(false);
            return true;
        }
        errorPhoneNo.setVisible(true);
        return false;
    }

    private boolean validCardMonth() {
        if( cardMonth.isDisabled() || cardMonth.getText().equals("")){
            errorDate.setVisible(false);
            return true;
        }
        else if (cardMonth.getText().length() <= 2) {
            try {
                int month = parseInt(cardMonth.getText());
                errorDate.setVisible(false);
                return (month < 13);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse int from month" + e);
            }
        }
        errorDate.setVisible(true);
        return false;
    }

    private boolean validCardYear() {
        if(cardYear.isDisabled() || cardYear.getText().equals("")){
            errorDate.setVisible(false);
            return true;
        }
        if (cardYear.getText().length() <= 2) {
            try {
                int year = parseInt(cardYear.getText());
                errorDate.setVisible(false);
                return (year >= 19 && year < 29);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse int from year");
            }
        }
        errorDate.setVisible(true);
        return false;
    }

    private boolean validLevel() {
        if(level.getText().equals("") || level.isDisabled()){
            return true;
        }
        if(level.getText().length() > 0) {
            try {
                int lev = parseInt(level.getText());
                return (lev < 99);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse int from level" + e);
            }
        }
        return false;
    }


}
