package controls;

import backend.FilesBackend;
import helper.Helper;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.WizardStage;
import javafx.scene.layout.GridPane;
import model.Profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToLongBiFunction;
import java.util.function.UnaryOperator;
import java.lang.System;
import java.util.regex.Pattern;
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
    @FXML private ToggleButton may20ToggleButton;
    @FXML private ToggleButton may21ToggleButton;
    @FXML private ToggleButton may22ToggleButton;
    @FXML private ToggleButton may23ToggleButton;
    @FXML private ToggleButton may24ToggleButton;
    @FXML private ToggleButton may27ToggleButton;
    @FXML private ToggleButton may28ToggleButton;
    @FXML private ToggleButton may29ToggleButton;
    @FXML private ToggleButton may30ToggleButton;
    @FXML private ToggleButton may31ToggleButton;
    @FXML private ToggleButton june4ToggleButton;
    @FXML private ToggleButton june5ToggleButton;
    @FXML private ToggleButton june6ToggleButton;
    @FXML private ToggleButton june7ToggleButton;
    @FXML private ToggleButton june8ToggleButton;
    @FXML private Button wizardDeliveryBackButton;
    @FXML private Button wizardToPaymentButton;
    @FXML private Button wizardDeliverySaveButton;
    @FXML private Button wizardDeliverySavedButton;
    @FXML private Label wizardErrorPhoneNo;
    @FXML private Label wizardErrorEmail;
    @FXML private Label wizardErrorZipCode;

    private static WizardDeliveryController instance;
    private WizardStageController parentController;
    private ToggleGroup dateSelected = new ToggleGroup();
    private ToggleGroup typeOfHousing = new ToggleGroup();
    private Profile profile;
    public ToggleButton chosenDate;
    public static final Pattern ValidateEmailPattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
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

        wizardDeliverySaveButton.setVisible(true);
        wizardDeliverySaveButton.setDisable(true);
        wizardDeliverySavedButton.setVisible(false);
        wizardErrorEmail.setVisible(false);
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
            resetSavedButtonIfNeeded();
            wizardDeliverySaveButton.setDisable(false);
            System.out.println("Wizard email change length");
        });

        wizardEmail.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue){ return; }

            var validEmail = wizardEmail.getText().isEmpty() || isEmailValid();

            if(validEmail) { setNormalCss(wizardEmail); }
            else { setErrorCss(wizardEmail); }

            wizardErrorEmail.setVisible(!validEmail);
            wizardDeliverySaveButton.setDisable(!validEmail);
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
                resetSavedButtonIfNeeded();
            }

            if(requireLength < 0 || nVal.intValue() < requireLength){
                validateInputs();
                return;
            }

            tf.setText(tf.getText().substring(0, requireLength));
            validateInputs();
        });
    }

    private void resetSavedButtonIfNeeded(){
        if(!wizardDeliverySavedButton.isVisible()){ return; }
        wizardDeliverySavedButton.setVisible(false);
        wizardDeliverySaveButton.setDisable(false);
        wizardDeliverySaveButton.setVisible(true);
    }

    private void setNormalCss(TextField tf){
        if(tf == null){ throw new RuntimeException("Attempt to set normal css to null object"); }
        tf.getStyleClass().clear();
        tf.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
    }

    private void setErrorCss(TextField tf){
        if(tf == null){ throw new RuntimeException("Attempt to set error css to null object"); }
        tf.getStyleClass().clear();
        tf.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
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

        may20ToggleButton.setToggleGroup(dateSelected);
        may21ToggleButton.setToggleGroup(dateSelected);
        may22ToggleButton.setToggleGroup(dateSelected);
        may23ToggleButton.setToggleGroup(dateSelected);
        may24ToggleButton.setToggleGroup(dateSelected);
        may27ToggleButton.setToggleGroup(dateSelected);
        may28ToggleButton.setToggleGroup(dateSelected);
        may29ToggleButton.setToggleGroup(dateSelected);
        may30ToggleButton.setToggleGroup(dateSelected);
        may31ToggleButton.setToggleGroup(dateSelected);
        june4ToggleButton.setToggleGroup(dateSelected);
        june5ToggleButton.setToggleGroup(dateSelected);
        june6ToggleButton.setDisable(true);
        june7ToggleButton.setToggleGroup(dateSelected);
        june8ToggleButton.setToggleGroup(dateSelected);
    }

    @FXML
    private void wizardHouseSelected() {
        wizardLevel.setDisable(true);
        resetSavedButtonIfNeeded();
    }

    @FXML
    private void wizardApartmentSelected() {
        wizardLevel.setDisable(false);
        resetSavedButtonIfNeeded();
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

    // when "spara ändringar" is pressed
    @FXML
    private void wizardSave() {
        if(!validateInputs()){
            resetSavedButtonIfNeeded();
            wizardDeliverySaveButton.setDisable(true);
            return;
        }
        updateProfile();
        changeToSavedButton(true);
        validateInputs();
    }

    private void changeToSavedButton(boolean b) {
        wizardDeliverySaveButton.setVisible(!b);
        wizardDeliverySavedButton.setVisible(b);
    }

    private void enableWizardSaveButton(boolean b) {
        wizardDeliverySaveButton.setVisible(b);
        wizardDeliverySaveButton.setDisable(!b);
    }

    private boolean validateInputs(){
        var isValid = true;
        var invalidInputs = emptyFields();

        if(!wizardEmail.getText().isEmpty() && !isEmailValid()){
            invalidInputs.add(wizardEmail);
            isValid = false;
        }

        if(!wizardZipCode.getText().isEmpty() && wizardZipCode.getText().length() < 5){
            invalidInputs.add(wizardZipCode);
            isValid = false;
        }

        if(!wizardPhoneNumber.getText().isEmpty() && wizardPhoneNumber.getText().length() < 10){
            invalidInputs.add(wizardPhoneNumber);
            isValid = false;
        }

        if(!wizardLevel.isDisabled()){
            int lvl = Integer.parseInt(wizardLevel.getText());
            if(lvl < 0){
                isValid = false;
                invalidInputs.add(wizardLevel);
            }
        }

        if(!invalidInputs.isEmpty()){
            isValid = false;
            invalidInputs.forEach(this::setErrorCss);
            System.out.println("All fields not valid");
        }

        wizardToPaymentButton.setDisable(!isValid);

        if(!isValid){ return false; }

        System.out.println("All fields valid");
        wizardDeliverySaveButton.setDisable(false);
        return true;
    }

    private List<TextField> emptyFields(){
        return Arrays.stream(mInputFields).filter(x -> x.getText().isEmpty()).collect(Collectors.toList());
        //return Arrays.stream(mInputFields).filter(x -> x.getText().isEmpty()).toArray();
    }

    private boolean isEmailValid(){
        return ValidateEmailPattern.matcher(wizardEmail.getText()).find();
    }
}

