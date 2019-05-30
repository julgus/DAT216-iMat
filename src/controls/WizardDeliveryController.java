package controls;

import backend.Backend;
import backend.FilesBackend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.lang.System;
import java.util.stream.Collectors;

import static java.lang.Integer.*;

public class WizardDeliveryController extends AnchorPane{

    @FXML private TextField wizardFirstName;
    @FXML private TextField wizardLastName;
    @FXML private TextField wizardPhoneNumber;
    @FXML private TextField wizardAdress;
    @FXML private TextField wizardZipCode;
    @FXML private TextField wizardCity;
    @FXML private TextField wizardEmail;
    @FXML private RadioButton wizardApartment;
    @FXML private RadioButton wizardHouse;
    @FXML private TextField wizardLevel;
    @FXML private GridPane wizardDateGridPane;
    @FXML private ToggleButton june3;
    @FXML private ToggleButton june3ToggleButton;
    @FXML private ToggleButton june4ToggleButton;
    @FXML private ToggleButton june5ToggleButton;
    @FXML private ToggleButton june6ToggleButton;
    @FXML private ToggleButton june7ToggleButton;
    @FXML private ToggleButton june10ToggleButton;
    @FXML private ToggleButton june11ToggleButton;
    @FXML private ToggleButton june12ToggleButton;
    @FXML private ToggleButton june13ToggleButton;
    @FXML private ToggleButton june14ToggleButton;
    @FXML private ToggleButton june17ToggleButton;
    @FXML private ToggleButton june18ToggleButton;
    @FXML private ToggleButton june19ToggleButton;
    @FXML private ToggleButton june20ToggleButton;
    @FXML private ToggleButton june21ToggleButton;
    @FXML private Button wizardDeliveryBackButton;
    @FXML private Button wizardToPaymentButton;
    @FXML private CheckBox saveCheckBox;
    @FXML private Label wizardErrorPhoneNo;
    @FXML private Label wizardErrorEmail;
    @FXML private Label wizardErrorZipCode;

    private static WizardDeliveryController instance;
    private WizardStageController parentController;
    public ToggleGroup dateSelected = new ToggleGroup();
    private ToggleGroup typeOfHousing = new ToggleGroup();
    private Profile profile;
    //public ToggleButton chosenDate;
    private TextField[] mInputFields = null;

    private WizardDeliveryController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/wizard_delivery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        wizardApartment.setSelected(true);

        profile = FilesBackend.getInstance().readProfileFromFile();
        if (profile == null) {
            profile = Profile.getInstance();
        }

        initTextFormatters();
        //addChangeListeners();
        initToggleGroups();
        initWizardProfileForm();

        dateSelected.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle old_toggle, Toggle new_toggle) {
                //ToggleButton tb = (ToggleButton) dateSelected.getSelectedToggle();
                var toggleText = ((ToggleButton) dateSelected.getSelectedToggle()).getText();
                Backend.getInstance().setDeliveryDate(toggleText);
            }
        });


        wizardErrorEmail.setVisible(false);
        saveCheckBox.setSelected(true);
    }

    public static WizardDeliveryController getInstance() {
        if (instance == null)
            instance = new WizardDeliveryController();
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

//-------------------- Form part beginning -------------------------------------------------------------------------//

    public void refresh() {
        profile = FilesBackend.getInstance().readProfileFromFile();
        if (profile == null) {
            profile = Profile.getInstance();
        }

        if(parentController != null){ parentController.setBlockToDate(); }
        initWizardProfileForm();
    }

    @FXML
    private void toPaymentStage() {
        if(!parentController.isDelayTimePassed()){ return; }
        if(!validateInputs()){
            System.out.println("all inputs not valid, cant proceed");
            return;
        }
        parentController.setBlockToDate();
        System.out.println("Proceeding to payment stage");
        WizardPaymentController.getInstance().setDeliveryDateText();
        parentController.viewPaymentStage();
        parentController.setBlockToDate();
    }

    @FXML
    private void toCartStage() {
        if(!parentController.isDelayTimePassed()){ return; }
        parentController.viewCartStage();
    }

    private void initWizardProfileForm() {
        wizardFirstName.setPromptText(Profile.getInputPromptName());
        wizardLastName.setPromptText(Profile.getInputPromptLastname());
        wizardPhoneNumber.setPromptText(Profile.getInputPromptPhoneNo());
        wizardAdress.setPromptText(Profile.getInputPromptAddress());
        wizardEmail.setPromptText(Profile.getInputPromptEmail());
        wizardCity.setPromptText(Profile.getInputPromptCity());
        wizardZipCode.setPromptText(Profile.getInputPromptZipCode());
        wizardLevel.setPromptText(Profile.getInputPromptLevel());

        if (!profile.getFirstName().isEmpty()){ wizardFirstName.setText(profile.getFirstName()); }
        if (!profile.getLastName().isEmpty()){ wizardLastName.setText(profile.getLastName()); }
        if (!profile.getMobilePhoneNumber().isEmpty()){ wizardPhoneNumber.setText(profile.getMobilePhoneNumber()); }
        if (!profile.getAddress().isEmpty()){ wizardAdress.setText(profile.getAddress()); }
        if (!profile.getEmail().isEmpty()){ wizardEmail.setText(profile.getEmail()); }
        if (!profile.getCity().isEmpty()){ wizardCity.setText(profile.getCity()); }
        if (!profile.getPostCode().isEmpty()){ wizardZipCode.setText(profile.getPostCode()); }
        if (profile.getLevel() >= 0){ wizardLevel.setText(Integer.toString(profile.getLevel())); }

        addListenerTextField(wizardZipCode, wizardErrorZipCode, 5);
        addListenerTextField(wizardPhoneNumber, wizardErrorPhoneNo, 10);
        addListenerTextField(wizardFirstName, null, -1);
        addListenerTextField(wizardLastName, null, -1);
        addListenerTextField(wizardAdress, null, -1);
        addListenerTextField(wizardCity, null, -1);
        addListenerTextField(wizardFirstName, null, -1);
        addListenerTextField(wizardLevel, null, 3);


        mInputFields = new TextField[]{
                wizardFirstName,
                wizardLastName,
                wizardAdress,
                wizardCity,
                wizardPhoneNumber,
                wizardZipCode,
                wizardEmail
        };

        wizardToPaymentButton.setDisable(Arrays.stream(mInputFields).anyMatch(x -> x.getText().isEmpty()));
        wizardToPaymentButton.toFront();

        wizardEmail.lengthProperty().addListener((observableValue, oVal, newVal) -> {
            if(newVal.intValue() > oVal.intValue()){ return; }

            System.out.println("Wizard email change length");
        });

        wizardEmail.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue){ return; }
            var validEmail = Profile.isValidEmail(wizardEmail.getText());

            if(validEmail) { setNormalCss(wizardEmail); }
            else { setErrorCss(wizardEmail); }

            wizardErrorEmail.setVisible(!validEmail);

        });
    }

    private void addListenerTextField(final TextField tf, final Label errorField, int requireLength){
        setNormalCss(tf);
        if(errorField != null){ errorField.setVisible(false); }

        tf.focusedProperty().addListener((observableValue, oVal, nVal) -> {
            if(nVal){ return; }
            if(requireLength < 0 || errorField == null){ return; }
            var isRequiredLength = tf.getText().length() == requireLength || tf.getText().isEmpty();
            errorField.setVisible(!isRequiredLength);
            if(isRequiredLength){ setNormalCss(tf); }
            else { setErrorCss(tf); }

            validateInputs();
        });

//        tf.textProperty().addListener((observableValue, oVal, nVal) -> {
//
//        });

        tf.lengthProperty().addListener((observableValue, oVal, nVal) -> {
            if(oVal.intValue() > nVal.intValue()){
                validateInputs();
            }

            if(requireLength < 0 || nVal.intValue() < requireLength){
                validateInputs();
                return;
            }

            tf.setText(tf.getText().substring(0, requireLength));
            validateInputs();
        });
    }

    private void setNormalCss(final TextField tf){
        if(tf == null){ throw new RuntimeException("Attempt to set normal css to null object"); }
        tf.setStyle("-fx-border-color: green-primary");
    }

    private void setErrorCss(final TextField tf){
        if(tf == null){ throw new RuntimeException("Attempt to set error css to null object"); }
        tf.setStyle("-fx-border-color: red-primary");
    }

    private void initTextFormatters() {
        /* Apply text filters on textfields */

        UnaryOperator<TextFormatter.Change> onlyDigitsFilter = change ->
            Profile.isValidNumber(change.getText()) ? change : null;

        UnaryOperator<TextFormatter.Change> onlyLettersFilter = change ->
            Profile.isValidText(change.getText()) ? change : null;

        wizardPhoneNumber.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        wizardZipCode.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        wizardFirstName.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
        wizardLastName.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
        wizardCity.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
    }

    // togglegroups for both house/apartment and date buttons
    private void initToggleGroups() {
        wizardApartment.setToggleGroup(typeOfHousing);
        wizardHouse.setToggleGroup(typeOfHousing);

        june3ToggleButton.setToggleGroup(dateSelected);
        june4ToggleButton.setToggleGroup(dateSelected);
        june5ToggleButton.setToggleGroup(dateSelected);
        june6ToggleButton.setDisable(true);
        june7ToggleButton.setToggleGroup(dateSelected);
        june10ToggleButton.setToggleGroup(dateSelected);
        june11ToggleButton.setToggleGroup(dateSelected);
        june12ToggleButton.setToggleGroup(dateSelected);
        june13ToggleButton.setToggleGroup(dateSelected);
        june14ToggleButton.setToggleGroup(dateSelected);
        june17ToggleButton.setToggleGroup(dateSelected);
        june18ToggleButton.setToggleGroup(dateSelected);
        june19ToggleButton.setToggleGroup(dateSelected);
        june20ToggleButton.setToggleGroup(dateSelected);
        june21ToggleButton.setToggleGroup(dateSelected);
    }

    @FXML
    private void wizardHouseSelected() {
        wizardLevel.setDisable(true);
    }

    @FXML
    private void wizardApartmentSelected() {
        wizardLevel.setDisable(false);
    }

    private void updateProfile() {
        profile.setFirstName(wizardFirstName.getText());
        profile.setLastName(wizardLastName.getText());
        profile.setMobilePhoneNumber(wizardPhoneNumber.getText());
        profile.setAddress(wizardAdress.getText());
        profile.setCity(wizardCity.getText());
        profile.setPostCode(wizardZipCode.getText());
        profile.setEmail(wizardEmail.getText());
        try {
            profile.setLevel(parseInt(wizardLevel.getText()));
        } catch (NumberFormatException e) {
            System.out.println("Failed to parse string to int" + e);
        }

        FilesBackend.getInstance().saveProfile(profile);
    }

    // when chexkbox is pressed
    @FXML
    private void wizardSave() {
        saveCheckBox.setSelected(!saveCheckBox.isSelected());
        updateProfile();
    }




    private boolean validateInputs(){
        var isValid = true;
        var invalidInputs = emptyFields();

        // first name
        if(!Profile.isValidText(wizardFirstName.getText())){
            invalidInputs.add(wizardFirstName);
        }

        // last name
        if(!Profile.isValidText(wizardLastName.getText())){
            invalidInputs.add(wizardLastName);
        }

        // address
        if(!Profile.isValidText(wizardAdress.getText())){
            invalidInputs.add(wizardAdress);
        }

        //city
        if(!Profile.isValidText(wizardCity.getText())){
            invalidInputs.add(wizardCity);
        }

        //email
        if(!Profile.isValidEmail(wizardEmail.getText())){
            invalidInputs.add(wizardEmail);
        }

        //zip
        if(!Profile.isValidZipCode(wizardZipCode.getText())){
            invalidInputs.add(wizardZipCode);
        }

        //phone number
        if(!Profile.isValidPhoneNumber(wizardPhoneNumber.getText())){
            invalidInputs.add(wizardPhoneNumber);
        }

        //level
        if(!wizardLevel.isDisabled()){
            int lvl = Integer.parseInt(wizardLevel.getText());
            if(lvl < 0){
                invalidInputs.add(wizardLevel);
            }
        }

        if(!invalidInputs.isEmpty()){
            isValid = false;
            invalidInputs.forEach(this::setErrorCss);
            System.out.println("All fields not valid");
        }

        wizardToPaymentButton.setDisable(!isValid);


        System.out.println("All fields valid");
        return isValid;
    }

    private List<TextField> emptyFields(){
        return Arrays.stream(mInputFields).filter(x -> x.getText().isEmpty()).collect(Collectors.toList());
        //return Arrays.stream(mInputFields).filter(x -> x.getText().isEmpty()).toArray();
    }
}

