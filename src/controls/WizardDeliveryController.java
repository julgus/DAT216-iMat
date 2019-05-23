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

import java.io.IOException;
import java.util.function.UnaryOperator;

import static java.lang.Integer.parseInt;

public class WizardDeliveryController extends AnchorPane {

    @FXML
    TextField wizardFirstName;
    @FXML
    TextField wizardLastName;
    @FXML
    TextField wizardPhoneNumber;
    @FXML
    TextField wizardAdress;
    @FXML
    TextField wizardZipCode;
    @FXML
    TextField wizardCity;
    @FXML
    RadioButton wizardApartment;
    @FXML
    RadioButton wizardHouse;
    @FXML
    TextField wizardLevel;
    @FXML
    GridPane wizardDateGridPane;
    @FXML
    ToggleButton may20ToggleButton;
    @FXML
    ToggleButton may21ToggleButton;
    @FXML
    ToggleButton may22ToggleButton;
    @FXML
    ToggleButton may23ToggleButton;
    @FXML
    ToggleButton may24ToggleButton;
    @FXML
    ToggleButton may27ToggleButton;
    @FXML
    ToggleButton may28ToggleButton;
    @FXML
    ToggleButton may29ToggleButton;
    @FXML
    ToggleButton may30ToggleButton;
    @FXML
    ToggleButton may31ToggleButton;
    @FXML
    ToggleButton june4ToggleButton;
    @FXML
    ToggleButton june5ToggleButton;
    @FXML
    ToggleButton june6ToggleButton;
    @FXML
    ToggleButton june7ToggleButton;
    @FXML
    ToggleButton june8ToggleButton;
    @FXML
    Button wizardDeliveryBackButton;
    @FXML
    Button wizardToPaymentButton;
    @FXML
    Button wizardDeliverySaveButton;
    @FXML
    Button wizardDeliverySavedButton;

    private static WizardDeliveryController instance;
    private WizardStageController parentController;
    private ToggleGroup dateSelected = new ToggleGroup();
    private ToggleGroup typeOfHousing = new ToggleGroup();
    private Profile profile;
    StringBuilder sb;

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
        profile = Profile.getInstance();
        sb = new StringBuilder();

        initTextFormatters();
        addChangeListeners();
        initToggleGroups();
        initWizardProfileForm();

        enableToPaymentButton(false);
        wizardDeliverySaveButton.setVisible(false);
        wizardDeliverySavedButton.setVisible(false);

    }

    public static WizardDeliveryController getInstance() {
        if (instance == null)
            instance = new WizardDeliveryController();
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

    public void refresh() {
        initWizardProfileForm();
    }

    @FXML
    private void toPaymentStage() {
        parentController.viewPaymentStage();
    }

    @FXML
    private void toCartStage() {
        parentController.viewCartStage();
    }

    private void initWizardProfileForm() {
        profile = FilesBackend.getInstance().readProfileFromFile();
        if(profile == null){ return; }
        if (profile != null) {
            wizardFirstName.setText(profile.getFirstName());
            wizardLastName.setText(profile.getLastName());
            wizardPhoneNumber.setText(profile.getMobilePhoneNumber());
            wizardAdress.setText(profile.getAddress());
            wizardCity.setText(profile.getCity());
            wizardZipCode.setText(profile.getPostCode());
            wizardLevel.setText(Integer.toString(profile.getLevel()));
        }
    }

    private void limitTextLength(TextField field, int limit) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (newValue.intValue() > limit) {
                        field.setText(field.getText().substring(0, limit));


                    } else if (newValue.intValue() == limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
                        if(isValidLength(field,limit))
                            enableToPaymentButton(true);
                    }

                } else {
                    if (newValue.intValue() < limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
                        enableToPaymentButton(false);
                    }

                }
            }
        });
    }

    private void enableToPaymentButton(boolean b) {
        wizardToPaymentButton.setDisable(!b);
    }

    private void enableWizardSaveButton(boolean b) {
        wizardDeliverySaveButton.setVisible(b);
        wizardDeliverySaveButton.setDisable(!b);
        wizardDeliverySaveButton.getStyleClass().add("green-button-active");
    }

    private boolean isValidLength(TextField textField, int limit){
        return (textField.getText().length() == limit || textField.getText().length() == 0);
    }

    public void addChangeListener(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue)
                    enableWizardSaveButton(true);
            }
        });
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

    private void addChangeListeners(){
        limitTextLength(wizardZipCode, 5);
        limitTextLength(wizardPhoneNumber, 10);
        addChangeListener(wizardFirstName);
        addChangeListener(wizardLastName);
        addChangeListener(wizardAdress);
        //addChangeListener(wizardEmailField);
        addChangeListener(wizardCity);
        addChangeListener(wizardHouse);
        addChangeListener(wizardApartment);
        addChangeListener(wizardLevel);
    }

    private void initToggleGroups() {
        wizardApartment.setToggleGroup(typeOfHousing);
        wizardHouse.setToggleGroup(typeOfHousing);
        //todo add date buttons togglegroup
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
        //profile.setEmail(eMailField.getText());
        profile.setLevel(parseInt(wizardLevel.getText()));

        FilesBackend.getInstance().saveProfile(profile);

    }
    private void changeToSavedButton(boolean b) {
        wizardDeliverySaveButton.setVisible(!b);
        wizardDeliverySavedButton.setVisible(b);
    }

    @FXML
    private void wizardDeliveryUpdate() {

        if (allFieldsValid()) {
            updateProfile();
            changeToSavedButton(true);
            sb.setLength(0);
        }
        //errorMessage.setVisible(true);
        //errorMessage.setText(sb.toString());
        sb.setLength(0);
    }

    //Bad practice, not following command query principle....

    private boolean allFieldsValid() {
        return (validZipCode() && validPhoneNo() && validLevel()); //validEmail &&
    }

    private boolean validZipCode() {
        if (wizardZipCode.getText().length() == 5 || wizardZipCode.getText().equals(""))
            return true;
        sb.append("ange giltigt postnummer. ");
        return false;
    }


    private boolean validPhoneNo() {
        if (wizardPhoneNumber.getText().length() == 10 || wizardPhoneNumber.getText().equals(""))
            return true;
        sb.append("ange giltigt mobilnummer. ");
        return false;
    }

    private boolean validLevel() {
        if(wizardLevel.getText().length() > 0) {
            try {
                int lev = parseInt(wizardLevel.getText());
                return (lev < 99);
            } catch (NumberFormatException e) {
                if(!wizardLevel.isDisabled())
                    sb.append("ange giltigt våning. ");

            }
        }
        return false;
    }

    /*private boolean validEmail() {
        if ((eMailField.getText().contains("@") && eMailField.getText().contains(".")) || eMailField.getText().equals(""))
            return true;
        sb.append("ange giltig e-postadress. ");
        return false;
    }
    */
}
