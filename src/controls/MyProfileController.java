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


    private ToggleGroup paymentMethod = new ToggleGroup();
    private ToggleGroup typeOfHousing = new ToggleGroup();
    private Profile profile;
    private boolean cardSelected;
    private String defaultCardNo = "ex: 1234 5678 1013 5564";
    StringBuilder sb;


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
        sb = new StringBuilder();

        initToggleGroups();
        initProfileForm();
        initTextFormatters();
        addChangeListners();

        /*Set 'save' button visible but disabled at start*/

        saveButton.setDisable(true);
        saveButton.setVisible(true);
        saved.setVisible(false);
        errorMessage.setVisible(false);
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
        eMailField.setText(profile.getEmail());
        city.setText(profile.getCity());
        zipCode.setText(profile.getPostCode());
        level.setText(Integer.toString(profile.getLevel()));

        house.setSelected(profile.isHouse());

        if (profile.isCardPayment()) {
            cardSelected();
            cardPayment.setSelected(true);
            personalNumber.setPromptText(profile.getPersonalNumber());

            if (!(profile.getCardNumber().equals("")))
                cardNumber.setText(profile.getCardNumber());
            else
                cardNumber.setPromptText(defaultCardNo);

            if (profile.getValidYear() != 0 && profile.getValidMonth() != 0) {
                cardYear.setText(Integer.toString(profile.getValidYear()));
                cardMonth.setText(Integer.toString(profile.getValidMonth()));
            } else {
                cardYear.setPromptText("ÅÅ");
                cardMonth.setPromptText("MM");
            }
            /* If invoice payment selected */
        } else {
            invoiceSelected();
            invoice.setSelected(true);
            if (!(profile.getPersonalNumber().equals("")))
                personalNumber.setText(profile.getPersonalNumber());
            if (!profile.getCardNumber().equals(""))
                cardNumber.setPromptText(profile.getCardNumber());
            else if (profile.getCardNumber().equals(""))
                cardNumber.setPromptText(defaultCardNo);
            if (profile.getValidYear() != 0 && profile.getValidMonth() != 0) {
                cardYear.setPromptText(Integer.toString(profile.getValidYear()));
                cardMonth.setPromptText(Integer.toString(profile.getValidMonth()));
            } else {
                cardYear.setPromptText("ÅÅ");
                cardMonth.setPromptText("MM");
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

        //make active elements black
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


                    } else if (newValue.intValue() == limit || newValue.intValue() == 0) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
                        if (isValidLength(field, limit))
                            enableSaveButton();
                    }
                } else {
                    if (newValue.intValue() < limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
                        enableSaveButton();
                    }

                }
            }
        });
    }

    public void addChangeListner(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue)
                    enableSaveButton();

            }
        });
    }

    //when hitting save button
    @FXML
    private void update() {

        if (allFieldsValid()) {
            updateProfile();
            changeToSavedButton(true);
            sb.setLength(0);
        }
        errorMessage.setVisible(true);
        errorMessage.setText(sb.toString());
        sb.setLength(0);
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
        profile.setLevel(parseInt(level.getText()));
        profile.setValidMonth(parseInt(cardMonth.getText()));
        profile.setValidYear(parseInt(cardYear.getText()));


        FilesBackend.getInstance().saveProfile(profile);

    }

    private void enableSaveButton() {
        saveButton.setDisable(false);
        saveButton.setVisible(true);
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
        if ((eMailField.getText().contains("@") && eMailField.getText().contains(".")) || eMailField.getText().equals(""))
            return true;
        sb.append("ange giltig e-postadress. ");
        return false;
    }

    private boolean validZipCode() {
        if (zipCode.getText().length() == 5 || zipCode.getText().equals(""))
            return true;
        sb.append("ange giltigt postnummer. ");
        return false;
    }

    private boolean validCardNumber() {
        if (cardNumber.getText().length() == 16 || cardNumber.getText().equals(""))
            return true;
        if(!cardNumber.isDisabled())
             sb.append("ange giltigt kortnummer. ");
        return false;
    }

    private boolean validPersonalNumber() {
        if (personalNumber.getText().length() == 12 || personalNumber.getText().equals(""))
            return true;
        sb.append("ange giltigt personnummer, 12 siffror. ");
        return false;
    }

    private boolean validPhoneNo() {
        if (phoneNo.getText().length() == 10 || phoneNo.getText().equals(""))
            return true;
        sb.append("ange giltigt mobilnummer. ");
        return false;
    }

    private boolean validCardMonth() {
        if (cardMonth.getText().length() <= 2 || cardMonth.getText().equals("")) {
            try {
                int month = parseInt(cardMonth.getText());
                return (month < 13);
            } catch (NumberFormatException e) {
                if (!cardMonth.isDisabled())
                    sb.append("ange giltig månad. ");
            }
        }
        return false;
    }

    private boolean validCardYear() {
        if (cardYear.getText().length() <= 2 || cardYear.getText().equals("")) {
            try {
                int year = parseInt(cardYear.getText());
                return (year >= 19 && year < 29);
            } catch (NumberFormatException e) {
                if (!cardYear.isDisabled())
                    sb.append("ange giltigt år. ");

            }
        }
        return false;
    }

    private boolean validLevel() {
        if(level.getText().length() > 0) {
            try {
                int lev = parseInt(level.getText());
                return (lev < 99);
            } catch (NumberFormatException e) {
                if(!level.isDisabled())
                    sb.append("ange giltigt våning. ");

            }
        }
        return false;
    }


}
