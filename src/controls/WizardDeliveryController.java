package controls;

import backend.Backend;
import backend.FilesBackend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Profile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.UnaryOperator;
import java.lang.System;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.lang.Integer.*;

public class WizardDeliveryController extends AnchorPane{

    // input fields
    @FXML private TextField wizardFirstName;
    @FXML private TextField wizardLastName;
    @FXML private TextField wizardPhoneNumber;
    @FXML private TextField wizardAdress;
    @FXML private TextField wizardZipCode;
    @FXML private TextField wizardCity;
    @FXML private TextField wizardEmail;
    @FXML private TextField wizardLevel;
    @FXML private RadioButton wizardApartment;
    @FXML private RadioButton wizardHouse;

    // toggle buttons
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

    //error fields
    @FXML private Label wizardErrorPhoneNo;
    @FXML private Label wizardErrorEmail;
    @FXML private Label wizardErrorZipCode;

    private static WizardDeliveryController instance;
    private WizardStageController parentController;
    public ToggleGroup dateSelected = new ToggleGroup();
    private ToggleGroup typeOfHousing = new ToggleGroup();
    private TextField[] mInputFields;
    private Label[] mErrorFields;
    private ToggleButton[] mDateSelectors;
    private ToggleButton mCurrentDateToggleSelected;
    private HashMap mDateMap = new HashMap<ToggleButton, Date>();

    private WizardDeliveryController() {
        loadFxml();
        initArrays();
        setupDateSelectors();
        initTextFieldFormats();
        initToggleGroups();
        initWizardProfileForm();
        wizardToPaymentButton.toFront();
    }

    private void initArrays(){
        mErrorFields = new Label[]{
            wizardErrorPhoneNo,
            wizardErrorEmail,
            wizardErrorZipCode
        };

        mInputFields = new TextField[]{
                wizardFirstName,
                wizardLastName,
                wizardAdress,
                wizardCity,
                wizardPhoneNumber,
                wizardZipCode,
                wizardEmail
        };

        mDateSelectors = new ToggleButton[]{
                june3ToggleButton,
                june4ToggleButton,
                june5ToggleButton,
                june6ToggleButton,
                june7ToggleButton,
                june10ToggleButton,
                june11ToggleButton,
                june12ToggleButton,
                june13ToggleButton,
                june14ToggleButton,
                june17ToggleButton,
                june18ToggleButton,
                june19ToggleButton,
                june20ToggleButton,
                june21ToggleButton
        };
    }

    private void loadFxml(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/wizard_delivery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }

//    var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        purchaseDateLabel.setText(dateFormat.format(receipt.getPurchaseDate()));

    private void setupDateSelectors(){
        var cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        var dateNow = new Date();
        var dateFormat = new SimpleDateFormat("dd MMMMM");
        var random = new Random();
        for(var x : mDateSelectors){
            var d = cal.getTime();
            mDateMap.put(x, d);
            x.setText(dateFormat.format(d));
            if(d.before(dateNow)){ x.setDisable(true); }

            cal.add(Calendar.HOUR, cal.get(Calendar.DAY_OF_WEEK) ==  Calendar.FRIDAY ? 24*3 : 24);
        }

        for(var i = 0; i < 3;){
            var randomDate = mDateSelectors[random.nextInt(mDateSelectors.length)];
            if(randomDate.isDisabled()){continue;}
            i++;
            randomDate.setDisable(true);
        }
    }

    public static WizardDeliveryController getInstance() {
        if (instance == null){ instance = new WizardDeliveryController(); }
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

    @FXML
    private void toPaymentStage() {
        if(!parentController.isDelayTimePassed()){ return; }

        parentController.setBlockToDate();
        System.out.println("Proceeding to payment stage");
        if(saveCheckBox.isSelected()){ updateProfileByInputFields(); }

        WizardPaymentController.getInstance().setDeliveryDateText();
        parentController.viewPaymentStage();
        parentController.setBlockToDate();
    }

    @FXML
    private void toCartStage() {
        if(!parentController.isDelayTimePassed()){ return; }
        clearInvalidFieldsAndSaveProfile();
        parentController.viewCartStage();
        System.out.println("Called cart save");
    }

    @FXML
    private void wizardHouseSelected() {
        wizardLevel.setDisable(true);
    }

    @FXML
    private void wizardApartmentSelected() {
        wizardLevel.setDisable(false);
    }

    @FXML
    private void wizardSave() {
        saveCheckBox.setSelected(!saveCheckBox.isSelected());
    }

    private void initWizardProfileForm() {
        setHelpPrompts();
        fillFieldsByProfileValues();
        addListeners();
    }

    private void setHelpPrompts(){
        wizardFirstName.setPromptText(Profile.getInputPromptName());
        wizardLastName.setPromptText(Profile.getInputPromptLastname());
        wizardPhoneNumber.setPromptText(Profile.getInputPromptPhoneNo());
        wizardAdress.setPromptText(Profile.getInputPromptAddress());
        wizardEmail.setPromptText(Profile.getInputPromptEmail());
        wizardCity.setPromptText(Profile.getInputPromptCity());
        wizardZipCode.setPromptText(Profile.getInputPromptZipCode());
        wizardLevel.setPromptText(Profile.getInputPromptLevel());
    }

    private void addListeners(){
        addListenerTextField(wizardZipCode, wizardErrorZipCode, 5);
        addListenerTextField(wizardPhoneNumber, wizardErrorPhoneNo, 10);
        addListenerTextField(wizardFirstName, null, -1);
        addListenerTextField(wizardLastName, null, -1);
        addListenerTextField(wizardAdress, null, -1);
        addListenerTextField(wizardCity, null, -1);
        addListenerTextField(wizardFirstName, null, -1);
        addListenerTextField(wizardLevel, null, 3);
        addEmailTextFieldListener();
    }

    private void addEmailTextFieldListener(){
        wizardEmail.lengthProperty().addListener((observableValue, oVal, newVal) -> {
            if(Profile.isValidEmail(wizardEmail.getText())){
                setValidCss(wizardEmail);
                if(allFieldsEntered()){ finalValidation(); }
            }
            else {
                setNormalCss(wizardEmail);
                wizardToPaymentButton.setDisable(true);
            }

            wizardErrorEmail.setVisible(false);
        });

        wizardEmail.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue || wizardEmail.getText().isEmpty()){
                setNormalCss(wizardEmail);
                wizardErrorEmail.setVisible(false);
                return;
            }

            var validEmail = Profile.isValidEmail(wizardEmail.getText());

            if(validEmail) {
                setValidCss(wizardEmail);
                if(allFieldsEntered()){ finalValidation(); }
            }
            else {
                setErrorCss(wizardEmail);
                wizardToPaymentButton.setDisable(true);
            }

            wizardErrorEmail.setVisible(!validEmail);
        });
    }

    private void addListenerTextField(final TextField tf, final Label errorField, int requireLength){
        tf.focusedProperty().addListener((observableValue, oVal, nVal) -> {
            wizardToPaymentButton.setDisable(true);

            if(errorField != null && tf.getText().length() < requireLength){
                setErrorCss(tf);
                errorField.setVisible(true);
                return;
            }

            if(tf.getText().isEmpty()){
                setNormalCss(tf);
                if(errorField != null){ errorField.setVisible(false); }
                return;
            }

            setValidCss(tf);
            if(allFieldsEntered()) { finalValidation(); }
        });

        tf.lengthProperty().addListener((observableValue, oVal, nVal) -> {
            if(errorField != null){ errorField.setVisible(false); }

            if(nVal.intValue() == 0){
                wizardToPaymentButton.setDisable(true);
            }

            if(requireLength < 0 || nVal.intValue() == 0){
                setNormalCss(tf);
                return;
            }

            if(oVal.intValue() > nVal.intValue() && nVal.intValue() < requireLength){
                wizardToPaymentButton.setDisable(true);
                setErrorCss(tf);
                return;
            }

            if(nVal.intValue() < requireLength){
                setNormalCss(tf);
                return;
            }

            tf.setText(tf.getText().substring(0, requireLength));
            setValidCss(tf);
            if(allFieldsEntered()) { finalValidation(); }
        });
    }

    private void setNormalCss(final TextField tf){
        if(tf == null){ throw new RuntimeException("Attempt to set normal css to null object"); }
        tf.setStyle("-fx-border-color: grey-primary");
    }

    private void setErrorCss(final TextField tf){
        if(tf == null){ throw new RuntimeException("Attempt to set error css to null object"); }
        tf.setStyle("-fx-border-color: red-primary");
    }

    private void setValidCss(final TextField tf){
        if(tf == null){ throw new RuntimeException("Attempt to set valid css to null object"); }
        tf.setStyle("-fx-border-color: green-primary");
    }

    private final Pattern lettersPattern = Pattern.compile("\\p{L}");
    private final Pattern numbersPattern = Pattern.compile("[0-9]");
    private final Pattern specialCharactersPattern = Pattern.compile("[^\\w]");

    private void initTextFieldFormats() {
        UnaryOperator<TextFormatter.Change> numbersOnly = change ->{
            if(change.getControlNewText().isEmpty()){ return change; }
            return lettersPattern.matcher(change.getControlNewText()).find()
                    || specialCharactersPattern.matcher(change.getControlNewText()).find()
                    ? null : change;
        };

        wizardPhoneNumber.setTextFormatter(new TextFormatter<>(numbersOnly));
        wizardZipCode.setTextFormatter(new TextFormatter<>(numbersOnly));
        wizardLevel.setTextFormatter(new TextFormatter<>(numbersOnly));

        UnaryOperator<TextFormatter.Change> onlyLettersFilter = change ->{
            if(change.getControlNewText().isEmpty()){ return change; }
            return numbersPattern.matcher(change.getControlNewText()).find()
                    || specialCharactersPattern.matcher(change.getControlNewText()).find()
                    ? null : change;
        };

        wizardFirstName.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
        wizardLastName.setTextFormatter(new TextFormatter<>(onlyLettersFilter));
    }

    private void initToggleGroups() {
        saveCheckBox.setSelected(true);

        wizardApartment.setToggleGroup(typeOfHousing);
        wizardHouse.setToggleGroup(typeOfHousing);

        june3ToggleButton.setToggleGroup(dateSelected);
        june4ToggleButton.setToggleGroup(dateSelected);
        june5ToggleButton.setToggleGroup(dateSelected);
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

        dateSelected.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle old_toggle, Toggle new_toggle) {
                var toggle = (ToggleButton) dateSelected.getSelectedToggle();

                if(mCurrentDateToggleSelected == null){
                    mCurrentDateToggleSelected = toggle;
                }

                if(toggle == null || toggle.equals(mCurrentDateToggleSelected)){
                    return;
                }

                Date date = (Date) mDateMap.get(toggle);
                mCurrentDateToggleSelected = toggle;

                Backend.getInstance().setDeliveryDate(date);
                if(allFieldsEntered()){ finalValidation(); }
            }
        });

        typeOfHousing.selectedToggleProperty().addListener((observableValue, previousToggle, newToggle) -> {
            wizardLevel.setDisable(newToggle == wizardHouse);
        });
    }

    private void updateProfileByInputFields() {
        var profile = getProfile();
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
            System.err.println("Failed to parse string to int" + e);
        }

        FilesBackend.getInstance().saveProfile(profile);
    }

    private boolean textFieldHasText(TextField tf){
        return !tf.getText().isEmpty();
    }

    private void inputValidation(){
        Arrays.stream(mErrorFields).forEach(x -> x.setVisible(false));
        Arrays.stream(mInputFields).forEach(this::setNormalCss);

        var invalid = new ArrayList<TextField>();

        if(textFieldHasText(wizardEmail) &&!Profile.isValidEmail(wizardEmail.getText())){
            invalid.add(wizardEmail);
            wizardErrorEmail.setVisible(true);
        }

        if(textFieldHasText(wizardFirstName) && !Profile.isValidText(wizardFirstName.getText())){
            invalid.add(wizardFirstName);
        }

        if(textFieldHasText(wizardLastName) && !Profile.isValidText(wizardLastName.getText())){
            invalid.add(wizardLastName);
        }

        if(textFieldHasText(wizardAdress) && !Profile.isValidText(wizardAdress.getText())){
            invalid.add(wizardAdress);
        }

        if(textFieldHasText(wizardCity) && !Profile.isValidText(wizardCity.getText())){
            invalid.add(wizardCity);
        }

        if(textFieldHasText(wizardZipCode) && !Profile.isValidZipCode(wizardZipCode.getText())){
            invalid.add(wizardZipCode);
            wizardErrorZipCode.setVisible(true);
        }

        if(textFieldHasText(wizardPhoneNumber) && !Profile.isValidPhoneNumber(wizardPhoneNumber.getText())){
            invalid.add(wizardPhoneNumber);
            wizardPhoneNumber.setVisible(true);
        }

        invalid.forEach(this::setErrorCss);
        var emptyFields = Arrays.stream(mInputFields).filter(this::textFieldHasText).collect(Collectors.toList());
        emptyFields.stream().filter(x -> !invalid.contains(x)).forEach(this::setValidCss);

        if(invalid.isEmpty()){ setPaymentButtonStatus(); }
        else { wizardToPaymentButton.setDisable(true); }
    }

    private boolean allFieldsEntered(){
        return Arrays.stream(mInputFields).noneMatch(x -> x.getText().isEmpty());
    }

    private void finalValidation(){
        Arrays.stream(mErrorFields).forEach(x -> x.setVisible(false));
        var invalid = new ArrayList<TextField>();

        if(!Profile.isValidEmail(wizardEmail.getText())){
            invalid.add(wizardEmail);
            wizardErrorEmail.setVisible(true);
        }

        if(!Profile.isValidText(wizardFirstName.getText())){
            invalid.add(wizardFirstName);
        }

        if(!Profile.isValidText(wizardLastName.getText())){
            invalid.add(wizardLastName);
        }

        if(!Profile.isValidText(wizardAdress.getText())){
            invalid.add(wizardAdress);
        }

        if(!Profile.isValidText(wizardCity.getText())){
            invalid.add(wizardCity);
        }

        if(!Profile.isValidZipCode(wizardZipCode.getText())){
            invalid.add(wizardZipCode);
            wizardErrorZipCode.setVisible(true);
        }

        if(!Profile.isValidPhoneNumber(wizardPhoneNumber.getText())){
            invalid.add(wizardPhoneNumber);
            wizardPhoneNumber.setVisible(true);
        }

        Arrays.stream(mInputFields).forEach(x -> {
            if(x.getText().isEmpty() && !invalid.contains(x)){
                invalid.add(x);
            }
        });

        invalid.forEach(this::setErrorCss);
        Arrays.stream(mInputFields).filter(x -> !invalid.contains(x)).forEach(this::setValidCss);

        if(invalid.isEmpty()){ setPaymentButtonStatus(); }
    }

    private void setPaymentButtonStatus(){
        wizardToPaymentButton.setDisable(!(allFieldsEntered() && Backend.getInstance().getDeliveryDate() != null));
    }

    public void refresh(){
        fillFieldsByProfileValues();
        inputValidation();
        if(allFieldsEntered()){ finalValidation();}
    }

    private void fillFieldsByProfileValues(){
        var profile = getProfile();
        if (!profile.getFirstName().isEmpty()){ wizardFirstName.setText(profile.getFirstName()); }
        if (!profile.getLastName().isEmpty()){ wizardLastName.setText(profile.getLastName()); }
        if (!profile.getMobilePhoneNumber().isEmpty()){ wizardPhoneNumber.setText(profile.getMobilePhoneNumber()); }
        if (!profile.getAddress().isEmpty()){ wizardAdress.setText(profile.getAddress()); }
        if (!profile.getEmail().isEmpty()){ wizardEmail.setText(profile.getEmail()); }
        if (!profile.getCity().isEmpty()){ wizardCity.setText(profile.getCity()); }
        if (!profile.getPostCode().isEmpty()){ wizardZipCode.setText(profile.getPostCode()); }
        if (profile.getLevel() >= 0){ wizardLevel.setText(Integer.toString(profile.getLevel())); }

        wizardApartment.setSelected(!profile.isHouse());
        wizardHouse.setSelected(profile.isHouse());
    }

    // if any fields are invalid and the user goes back to store the values should be cleared and saved to profile
    private void clearInvalidFieldsAndSaveProfile(){
        Arrays.stream(mErrorFields).forEach(x -> x.setVisible(false));
        Arrays.stream(mInputFields).filter(x -> !textFieldHasText(x)).forEach(x -> x.setText(""));
        updateProfileByInputFields();
    }

    private Profile getProfile(){
        return FilesBackend.getInstance().readProfileFromFile();
    }
}

