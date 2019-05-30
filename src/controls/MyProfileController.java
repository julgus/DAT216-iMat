package controls;

import backend.FilesBackend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import model.Profile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

import static java.lang.Integer.parseInt;

public class MyProfileController extends AnchorPane {

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField phoneNo;
    @FXML private TextField eMailField;
    @FXML private TextField address;
    @FXML private TextField zipCode;
    @FXML private TextField city;
    @FXML private RadioButton apartment;
    @FXML private RadioButton house;
    @FXML private TextField level;
    @FXML private TextField cardNumber;
    @FXML private TextField cardYear;
    @FXML private TextField cardMonth;
    @FXML private TextField personalNumber;
    @FXML private RadioButton invoice;
    @FXML private RadioButton cardPayment;
    @FXML private Label cardNo;
    @FXML private Label cardNoHelp;
    @FXML private Label cardDate;
    @FXML private Label cardDateHelp;
    @FXML private Label personNo;
    @FXML private Label personNoHelp;
    @FXML private Line slashLine;
    @FXML private Button saved;
    @FXML private Button saveButton;
    @FXML private Label errorPhoneNo;
    @FXML private Label errorEmail;
    @FXML private Label errorDate;
    @FXML private Label errorZipCode;
    @FXML private Label errorCardNo;
    @FXML private Label errorPersonalNo;
    @FXML private AnchorPane cardPane;
    @FXML private AnchorPane invoicePane;
    @FXML private StackPane paymentPane;

    private ToggleGroup paymentMethod = new ToggleGroup();
    private ToggleGroup typeOfHousing = new ToggleGroup();
    private Profile profile;
    private boolean cardSelected;
    private List<TextField> fields = new LinkedList<>();


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

        profile = FilesBackend.getInstance().readProfileFromFile();
        if (profile == null) {
            profile = Profile.getInstance();
        }

        initToggleGroups();
        initProfileForm();
        initTextFormatters();
        initFieldList();
        addChangeListners();

        /*Set 'save' button visible but disabled at start*/

        saveButton.setDisable(true);
        saveButton.setVisible(true);
        saved.setVisible(false);



        //ONLY FOR TESTING PURPOSE
        boolean a = validCardMonth();
        boolean b = validCardNumber();
        boolean c = validCardYear();
        boolean d = validEmail();
        boolean f = validPersonalNumber();
        boolean g = validPhoneNo();
        boolean h = validZipCode();
        boolean i = validLevel();

    }

    public static MyProfileController getInstance() {
        if (myProfileController == null)
            myProfileController = new MyProfileController();
        return myProfileController;
    }

    private void initFieldList(){
        //fields.add(phoneNo);
        //fields.add(eMailField);
        fields.add(zipCode);
        fields.add(city);
        fields.add(cardNumber);
        fields.add(cardMonth);
        fields.add(cardYear);
    }


    public void refresh() {
        profile = FilesBackend.getInstance().readProfileFromFile();
        if (profile == null) {
            profile = Profile.getInstance();
        }
        initProfileForm();
    }

        private void initTextFormatters() {
        /* Apply text filters on textfields */


        UnaryOperator<TextFormatter.Change> onlyDigitsFilter = change ->
            change.getText().matches("[0-9]*") ? change : null;

        UnaryOperator<TextFormatter.Change> onlyLettersFilter = change ->
            change.getText().matches("\\p{L}*") ? change : null;

        phoneNo.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        cardMonth.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        cardYear.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        level.setTextFormatter( new TextFormatter<>(onlyDigitsFilter));
        cardNumber.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        zipCode.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        firstName.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
        lastName.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
        city.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
        personalNumber.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
    }


    private void addChangeListners() {
        limitTextLength(zipCode, 5);
        limitTextLength(cardNumber, 16);
        limitTextLength(personalNumber, 12);
        limitTextLength(phoneNo, 10);
        limitTextLength(cardMonth, 2);
        limitTextLength(cardYear, 2);
        limitTextLength(phoneNo, 10);
        addTextFieldChangeListner(firstName);
        addTextFieldChangeListner(lastName);
        addTextFieldChangeListner(address);
        addTextFieldChangeListner(eMailField);
        addTextFieldChangeListner(city);
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

    private void setTextOrPromptIfEmpty(TextField tf, String text, String promptText){
        if(text == null || promptText == null){
            throw new RuntimeException("Attempt to set null text and or prompt");
        }

        tf.setText(text);
        tf.setPromptText(promptText);
    }

    private void initProfileForm() {
        setTextOrPromptIfEmpty(firstName, profile.getFirstName(), Profile.getInputPromptName());
        setTextOrPromptIfEmpty(lastName, profile.getLastName(), Profile.getInputPromptLastname());
        setTextOrPromptIfEmpty(phoneNo, profile.getMobilePhoneNumber(), Profile.getInputPromptPhoneNo());
        setTextOrPromptIfEmpty(address, profile.getAddress(), Profile.getInputPromptAddress());
        setTextOrPromptIfEmpty(eMailField, profile.getEmail(), Profile.getInputPromptEmail());
        setTextOrPromptIfEmpty(city, profile.getCity(), Profile.getInputPromptCity());
        setTextOrPromptIfEmpty(zipCode, profile.getPostCode(), Profile.getInputPromptZipCode());
        setTextOrPromptIfEmpty(level, Integer.toString(profile.getLevel()), Profile.getInputPromptLevel());
        setTextOrPromptIfEmpty(personalNumber, profile.getPersonalNumber(), Profile.getInputPromptPersonalNo());

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
            if (profile.getValidYear() != 0)
                cardYear.setText(Integer.toString(profile.getValidYear()));
            if(profile.getValidMonth() != 0){
                cardMonth.setText(Integer.toString(profile.getValidMonth()));
                if(profile.getValidMonth() < 10){
                    cardMonth.setText("0"+profile.getValidMonth());
                }
            }

            else{
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



    @FXML
    private void invoiceSelected() {
        invoicePane.toFront();
        cardSelected = false;
        personalNumber.requestFocus();

    }

    @FXML
    private void cardSelected() {
        cardPane.toFront();
        cardSelected = true;
        cardNumber.requestFocus();
    }

    @FXML
    private void houseSelected() {
        level.setDisable(true);
    }

    @FXML private void apartmentSelected(){
        level.setDisable(false);
    }

    private void limitTextLength(TextField field, int limit) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (newValue.intValue() > limit) {
                        field.setText(field.getText().substring(0, limit));
                        field.setStyle("-fx-border-color: red-primary");
                        update();
                    } else if (newValue.intValue() == limit || newValue.intValue() == 0) {
                        if (isValidLength(field, limit)) {
                            update();
                            focusNext(field);
                        }

                    }
                }else {
                    if (newValue.intValue() < limit) {
                        field.setStyle("-fx-border-color: red-primary");
                        update();
                    }
                }
            }
        });
    }

    private void addChangeListner(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    update();
                }

            }
        });
    }

    private void addTextFieldChangeListner(TextField field) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldPropertyValue, Number newPropertyValue) {
                if (newPropertyValue != oldPropertyValue) {
                    update();
                }

            }
        });
    }

    private void update(){
        if (allFieldsValid()) {
            saveButton.setDisable(false);
        }
        setErrorDate();
        changeToSavedButton(false);
        saveButton.setDisable(true);
    }

    private void focusNext(TextField field){
        if(fields.contains(field)) {
            int index = fields.indexOf(field);
            if(fields.size() != index + 1)
                fields.get(index+1).requestFocus();
        }
    }


    //when hitting save button
    @FXML
    private void save() {
        if(allFieldsValid()) {
            updateProfile();
            changeToSavedButton(true);
        }
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



    private boolean isValidLength(TextField textField, int limit) {
        return (textField.getText().length() == limit || textField.getText().length() == 0);
    }

    private void changeToSavedButton(boolean b) {
        saveButton.setVisible(!b);
        saved.setVisible(b);
    }

    private boolean allFieldsValid() {
        boolean a = validCardMonth();
        boolean b = validCardNumber();
        boolean c = validCardYear();
        boolean d = validEmail();
        boolean f = validPersonalNumber();
        boolean g = validPhoneNo();
        boolean h = validZipCode();
        boolean i = validLevel();
        return (a&b&c&d&f&g&h&i);
    }

    //Bad practice, not following command query principle....
    private boolean validEmail() {
        if (Profile.isValidEmail(eMailField.getText()) || eMailField.getText().equals("")){
            eMailField.setStyle("-fx-border-color: border-primary");
            errorEmail.setVisible(false);
            return true;
        }
        eMailField.setStyle("-fx-border-color: red-primary");
        errorEmail.setVisible(true);
        return false;
    }

    private boolean validZipCode() {
        if (zipCode.getText().length() == 5 || zipCode.getText().equals("")){
            errorZipCode.setVisible(false);
            zipCode.setStyle("-fx-border-color: border-primary");
            return true;
        }
        errorZipCode.setVisible(true);
        return false;
    }

    private boolean validCardNumber() {
        if (cardNumber.getText().length() == 16 || cardNumber.getText().equals("") || !cardSelected){
            errorCardNo.setVisible(false);
            cardNumber.setStyle("-fx-border-color: border-primary");
            return true;
        }
        errorCardNo.setVisible(true);
        return false;
    }

    private boolean validPersonalNumber() {
        if (personalNumber.getText().length() == 12 || personalNumber.getText().equals("") || cardSelected){
            errorPersonalNo.setVisible(false);
            personalNumber.setStyle("-fx-border-color: border-primary");
            return true;}
        errorPersonalNo.setVisible(true);
        return false;
    }

    private boolean validPhoneNo() {
        if (phoneNo.getText().length() == 10 || phoneNo.getText().equals("")){
            errorPhoneNo.setVisible(false);
            phoneNo.setStyle("-fx-border-color: border-primary");
            return true;
        }
        errorPhoneNo.setVisible(true);
        return false;
    }

    private void setErrorDate(){
        if(validCardMonth() && validCardYear())
            errorDate.setVisible(false);
        else
            errorDate.setVisible(true);
    }

    private boolean validCardMonth() {
        if( cardMonth.getText().equals("")||!cardSelected){
            cardMonth.setStyle("-fx-border-color: border-primary");
            return true;
        }
        else if (cardMonth.getText().length() == 2) {
            try {
                int month = parseInt(cardMonth.getText());
                errorDate.setVisible(month > 13);
                if(month < 13)
                    cardMonth.setStyle("-fx-border-color: border-primary");
                return (month < 13);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse int from month" + e);
            }
        }
        return false;
    }

    private boolean validCardYear() {
        if(cardYear.getText().equals("") || !cardSelected){
            cardMonth.setStyle("-fx-border-color: border-primary");
            return true;
        }
        if (cardYear.getText().length() == 2) {
            try {
                int year = parseInt(cardYear.getText());
                if(18 < year && year < 29) {
                    cardYear.setStyle("-fx-border-color: border-primary");
                }
                return (year > 18 && year < 29);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse int from year");
            }
        }
        return false;
    }
    private boolean validLevel(){
        if(level.getText().equals("") || level.getText().length() == 1 || level.getText().length() == 2){
            return true;
        }
        return false;
    }



}
