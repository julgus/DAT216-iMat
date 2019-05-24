package controls;

import backend.FilesBackend;
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
import java.util.function.UnaryOperator;
import java.lang.System;

import static java.lang.Integer.parseInt;

public class WizardDeliveryController extends AnchorPane {

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
    @FXML Button wizardDeliveryBackButton;
    @FXML Button wizardToPaymentButton;
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
    private StringBuilder sb;
    private ToggleButton[] dateButtonArray = new ToggleButton[15];

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

        sb = new StringBuilder();

        initTextFormatters();
        addChangeListeners();
        initToggleGroups();
        refresh();

        enableToPaymentButton(false);
        wizardDeliverySaveButton.setVisible(false);
        wizardDeliverySavedButton.setVisible(false);

    }

//-------------------- Form part beginning -------------------------------------------------------------------------//
    public static WizardDeliveryController getInstance() {
        if (instance == null)
            instance = new WizardDeliveryController();
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

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
        parentController.viewPaymentStage();
    }

    @FXML
    private void toCartStage() {
        if(!parentController.isDelayTimePassed()){ return; }
        parentController.viewCartStage();
    }

    private void initWizardProfileForm() {
        if (!profile.getFirstName().equals(""))
            wizardFirstName.setText(profile.getFirstName());
        else
            wizardFirstName.setPromptText(Profile.getInputPromptName());
        if (!profile.getLastName().equals(""))
            wizardLastName.setText(profile.getLastName());
        else
            wizardLastName.setPromptText(Profile.getInputPromptLastname());
        if (!profile.getMobilePhoneNumber().equals(""))
            wizardPhoneNumber.setText(profile.getMobilePhoneNumber());
        else
            wizardPhoneNumber.setPromptText(Profile.getInputPromptPhoneNo());
        if (!profile.getAddress().equals(""))
            wizardAdress.setText(profile.getAddress());
        else
            wizardAdress.setPromptText(Profile.getInputPromptAddress());
        if (!profile.getEmail().equals(""))
            wizardEmail.setText(profile.getEmail());
        else
            wizardEmail.setPromptText(Profile.getInputPromptEmail());
        if (!profile.getCity().equals(""))
            wizardCity.setText(profile.getCity());
        else
            wizardCity.setPromptText(Profile.getInputPromptCity());
        if (!profile.getPostCode().equals(""))
            wizardZipCode.setText(profile.getPostCode());
        else
            wizardZipCode.setPromptText(Profile.getInputPromptZipCode());
        if(!Integer.toString(profile.getLevel()).equals(""))
            wizardLevel.setText(Integer.toString(profile.getLevel()));
        else
            wizardLevel.setPromptText(Profile.getInputPromptLevel());
    }


    private void limitTextLength(TextField field, int limit) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (newValue.intValue() > limit) {
                        field.setText(field.getText().substring(0, limit));
                        wizardUpdate();
                    }
                    else if (newValue.intValue() == limit) {

                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
                        if (isValidLength(field, limit))
                            wizardUpdate();
                    }
                }
                else {
                    if (newValue.intValue() < limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
                        wizardUpdate();
                    }
                }
            }
        });
    }

    private boolean isValidLength(TextField textField, int limit) {
        return (textField.getText().length() == limit || textField.getText().length() == 0);
    }

    public void addChangeListener(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue)
                    wizardUpdate();
            }
        });
    }

    private void addChangeListeners() {
        limitTextLength(wizardZipCode, 5);
        limitTextLength(wizardPhoneNumber, 10);
        addChangeListener(wizardFirstName);
        addChangeListener(wizardLastName);
        addChangeListener(wizardAdress);
        addChangeListener(wizardEmail);
        addChangeListener(wizardCity);
        addChangeListener(wizardHouse);
        addChangeListener(wizardApartment);
        addChangeListener(wizardLevel);
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
        wizardPhoneNumber.setTextFormatter(phoneNoFormat);
        limitTextLength(wizardPhoneNumber, 10);

        TextFormatter<String> zipCodeFormat = new TextFormatter<>(onlyDigitsFilter);
        wizardZipCode.setTextFormatter(zipCodeFormat);
        limitTextLength(wizardZipCode, 5);

        TextFormatter<String> firstNameFormat = new TextFormatter<>(onlyLettersFilter);
        wizardFirstName.setTextFormatter(firstNameFormat);

        TextFormatter<String> lastNameFormat = new TextFormatter<>(onlyLettersFilter);
        wizardLastName.setTextFormatter(lastNameFormat);

        TextFormatter<String> cityFormat = new TextFormatter<>(onlyLettersFilter);
        wizardCity.setTextFormatter(cityFormat);
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

    private void wizardUpdate() {
        if (allFieldsValid()) {
            enableWizardSaveButton(true);
        }
    }

    // when "spara ändringar is pressed"
    @FXML
    private void wizardSave() {
            updateProfile();
            changeToSavedButton(true);
            enableToPaymentButton(true);
    }

    private void changeToSavedButton(boolean b) {
        wizardDeliverySaveButton.setVisible(!b);
        wizardDeliverySavedButton.setVisible(b);
    }

    private void enableToPaymentButton(boolean b) {
        wizardToPaymentButton.setDisable(!b);
    }

    private void enableWizardSaveButton(boolean b) {
        wizardDeliverySaveButton.setVisible(b);
        wizardDeliverySaveButton.setDisable(!b);
    }

    //Bad practice, not following command query principle....

    private boolean allFieldsValid() {
        return (validZipCode() && validPhoneNo() && validLevel() && validEmail());
    }

    private boolean validZipCode() {
        if (wizardZipCode.getText().length() == 5 || wizardZipCode.getText().equals("")){
            wizardErrorZipCode.setVisible(false);
            return true;
        }
        wizardErrorZipCode.setVisible(true);
        return false;
    }


    private boolean validPhoneNo() {
        if (wizardPhoneNumber.getText().length() == 10 || wizardPhoneNumber.getText().equals("")){
            wizardErrorPhoneNo.setVisible(false);
            return true;
        }
        wizardErrorPhoneNo.setVisible(true);
        return false;
    }

    private boolean validLevel() {
        if (wizardLevel.getText().length() > 0) {
            try {
                int lev = parseInt(wizardLevel.getText());
                return (lev < 99);
            } catch (NumberFormatException e) {
                if (!wizardLevel.isDisabled())
                    sb.append("ange giltig våning. ");

            }
        }
        return false;
    }

    private boolean validEmail() {
        if ((wizardEmail.getText().contains("@") && wizardEmail.getText().contains(".")) || wizardEmail.getText().equals("")){
            wizardEmail.getStyleClass().clear();
            wizardEmail.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
            wizardErrorEmail.setVisible(false);
            return true;
        }
        wizardEmail.getStyleClass().clear();
        wizardEmail.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
        wizardErrorEmail.setVisible(true);
        return false;
    }

//------------------------------------ Form part end -----------------------------------------------------------------------------------------------//

//------------------------------------ Date buttons beginning --------------------------------------------------------------------------------------//

    public void initDateButtonArray() {
        may20ToggleButton = dateButtonArray[0];
        may21ToggleButton = dateButtonArray[1];
        may22ToggleButton = dateButtonArray[2];
        may23ToggleButton = dateButtonArray[3];
        may24ToggleButton = dateButtonArray[4];
        may27ToggleButton = dateButtonArray[5];
        may28ToggleButton = dateButtonArray[6];
        may29ToggleButton = dateButtonArray[7];
        may30ToggleButton = dateButtonArray[8];
        may31ToggleButton = dateButtonArray[9];
        june4ToggleButton = dateButtonArray[10];
        june5ToggleButton = dateButtonArray[11];
        june6ToggleButton = dateButtonArray[12];
        june7ToggleButton = dateButtonArray[13];
        june8ToggleButton = dateButtonArray[14];
    }



}

