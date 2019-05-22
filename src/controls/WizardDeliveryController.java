package controls;

import backend.FilesBackend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.WizardStage;
import javafx.scene.layout.GridPane;
import model.Profile;

import java.io.IOException;
import java.util.function.UnaryOperator;

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

    private static WizardDeliveryController instance;
    private WizardStageController parentController;
    ToggleGroup dateSelected = new ToggleGroup();
    ToggleGroup typeOfHousing = new ToggleGroup();
    Profile profile;

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

        initProfileForm();
        wizardToPaymentButton.setDisable(true);
        wizardDeliverySaveButton.setVisible(false);

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

    }

    @FXML
    private void toPaymentStage() {
        parentController.viewPaymentStage();
    }

    @FXML
    private void toCartStage() {
        parentController.viewCartStage();
    }

    private void initProfileForm() {
        profile = FilesBackend.getInstance().readProfileFromFile();

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
                        enableToPaymentButton(true);
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

    private boolean isValidLength(TextField textField, int limit){
        return (textField.getText().length() == limit || textField.getText().length() == 0);
    }

    public void addChangeListener(TextField textField) {
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue)
                    enableWizardSaveButton(true);
            }
        });
    }

    private void enableWizardSaveButton(boolean b) {
        wizardDeliverySaveButton.setVisible(true);
        wizardDeliverySaveButton.setDisable(!b);
    }
}
