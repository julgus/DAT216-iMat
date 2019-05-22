package controls;

import backend.FilesBackend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.Profile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    private Label cardDate;
    @FXML
    private Label personNo;
    @FXML
    private Line slashLine;
    @FXML
    private Button saved;
    @FXML
    private Button saveButton;

    StoreStageController parentController;
    ToggleGroup paymentMethod = new ToggleGroup();
    ToggleGroup typeOfHousing = new ToggleGroup();
    Profile profile;
    boolean fieldChanged = false;
    boolean cardSelected;


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
        cardPayment.setSelected(true);
        personalNumber.setDisable(true);
        apartment.setSelected(true);
        profile = Profile.getInstance();

        initToggleGroups();
        initProfileForm();



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
        limitTextLength(cardYear, 2);

        TextFormatter<String> cardNoFormat = new TextFormatter<>(onlyDigitsFilter);
        cardNumber.setTextFormatter(cardNoFormat);
        limitTextLength(cardNumber, 16);

        TextFormatter<String> zipCodeFormat = new TextFormatter<>(onlyDigitsFilter);
        zipCode.setTextFormatter(zipCodeFormat);
        limitTextLength(zipCode, 5);

        TextFormatter<String> firstNameFormat = new TextFormatter<>(onlyLettersFilter);
        firstName.setTextFormatter(firstNameFormat);
        addChangeListner(firstName);

        TextFormatter<String> lastNameFormat = new TextFormatter<>(onlyLettersFilter);
        lastName.setTextFormatter(lastNameFormat);
        addChangeListner(lastName);

        TextFormatter<String> addressFormat = new TextFormatter<>(onlyLettersFilter);
        addChangeListner(address);

        TextFormatter<String> cityFormat = new TextFormatter<>(onlyLettersFilter);
        city.setTextFormatter(cityFormat);
        addChangeListner(city);

        TextFormatter<String> personalNumberFormat = new TextFormatter<>(onlyDigitsFilter);
        personalNumber.setTextFormatter(personalNumberFormat);
        limitTextLength(personalNumber,12);

        //TODO: FIX CSS for disabled button
        saveButton.setStyle("orange-button-active");

        saveButton.setDisable(true);
        saved.setVisible(false);

    }

    public static MyProfileController getInstance() {
        if (myProfileController == null)
            myProfileController = new MyProfileController();
        return myProfileController;
    }

    public void setParentController(StoreStageController controller) {
        parentController = controller;
    }

    private void initToggleGroups() {
        cardPayment.setToggleGroup(paymentMethod);
        invoice.setToggleGroup(paymentMethod);
        apartment.setToggleGroup(typeOfHousing);
        house.setToggleGroup(typeOfHousing);
    }

    private void initProfileForm() {
        profile = FilesBackend.getInstance().readProfileFromFile();

        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        phoneNo.setText(profile.getMobilePhoneNumber());
        address.setText(profile.getAddress());
        city.setText(profile.getCity());
        zipCode.setText(profile.getPostCode());
        level.setText(Integer.toString(profile.getLevel()));


        house.setSelected(profile.isHouse());

        if (profile.isCardPayment()) {
            cardPayment.setSelected(true);
            cardNumber.setText(profile.getCardNumber());
            cardYear.setText(Integer.toString(profile.getValidYear()));
            cardMonth.setText(Integer.toString(profile.getValidMonth()));
            personalNumber.setPromptText(profile.getPersonalNumber());

        } else if (!(profile.isCardPayment())) {
            personalNumber.focusedProperty();
            personalNumber.setText(profile.getPersonalNumber());
            cardNumber.setPromptText(profile.getCardNumber());
            cardYear.setPromptText(Integer.toString(profile.getValidYear()));
            cardMonth.setPromptText(Integer.toString(profile.getValidMonth()));
        }

    }

    @FXML
    private void invoiceSelected() {
        cardNumber.setDisable(true);
        cardMonth.setDisable(true);
        cardYear.setDisable(true);
        personalNumber.setDisable(false);

        //make labels grey
        cardDate.setStyle("-fx-text-fill: grey-primary");
        cardNo.setStyle("-fx-text-fill: grey-primary");
        //make textfields grey

        slashLine.setStyle("-fx-stroke: grey-primary");

        //Style active elements
        personNo.setStyle("-fx-text-fill: black");
        personalNumber.setStyle("-fx-text-fill: black;");
        cardSelected = false;

    }

    @FXML
    private void cardSelected() {

        personalNumber.setDisable(true);
        cardNumber.setDisable(false);
        cardMonth.setDisable(false);
        cardYear.setDisable(false);

        //make active elements black
        cardDate.setStyle("-fx-text-fill: black");
        cardNo.setStyle("-fx-text-fill: black");

        //Style inactive elements
        personNo.setStyle("-fx-text-fill: grey-primary");
        personalNumber.setStyle("-fx-text-fill: grey-primary;");

        cardSelected = true;

    }

    private void limitTextLength(TextField field, int limit) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (newValue.intValue() > limit) {
                        field.setText(field.getText().substring(0, limit));
                        enableSaveButton(true);
                    } else if (newValue.intValue() == limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
                        if(isValidLength(field,limit))
                            enableSaveButton(true);
                    }
                } else {
                    if (newValue.intValue() < limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
                        enableSaveButton(false);
                    }

                }
            }
        });
    }

    public void addChangeListner(TextField textField) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue)
                    enableSaveButton(true);
            }
        });
    }

    //when hitting save button
    @FXML
    private void update() {

        if(allFieldsValid()) {

            changeToSavedButton();
            updateProfile();

        }

    }
    private void updateProfile(){
        profile.setFirstName(firstName.getText());
        profile.setLastName(lastName.getText());
        profile.setMobilePhoneNumber(phoneNo.getText());
        profile.setAddress(address.getText());
        profile.setCity(city.getText());
        profile.setPostCode(zipCode.getText());
        profile.setCardNumber(cardNumber.getText());
        profile.setPersonalNumber(personalNumber.getText());
        profile.setCardPayment(cardSelected);


        try {
            profile.setLevel(parseInt(level.getText()));
            profile.setValidMonth(parseInt(cardMonth.getText()));
            profile.setValidYear(parseInt(cardYear.getText()));
        }catch (NumberFormatException e){

        }

        FilesBackend.getInstance().saveProfile(profile);

    }

    private void enableSaveButton(boolean b) {
        saveButton.setDisable(!b);
    }
    private boolean isValidLength(TextField textField, int limit){
        return (textField.getText().length() == limit || textField.getText().length() == 0);
    }
    private boolean allFieldsValid(){
        return true;
    }
    private void changeToSavedButton(){
        saveButton.setVisible(false);
        saved.setVisible(true);
    }
}
