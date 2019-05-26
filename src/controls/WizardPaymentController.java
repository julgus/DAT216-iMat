package controls;

import backend.FilesBackend;
import helper.Helper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.*;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class WizardPaymentController extends AnchorPane {

    @FXML private TextField cardNoTextField;
    @FXML private TextField cardYearTextField;
    @FXML private TextField cardMonthTextField;
    @FXML private TextField cardCVCTextField;
    @FXML private TextField personalNumberTextField;

    @FXML private Label cardNoLabel;
    @FXML private Label cardValidityLabel;
    @FXML private Label cardCVCLabel;
    @FXML private Label personalNumberLabel;
    @FXML private Label numberOfItemsLabel;
    @FXML private Label deliveryDateLabel;
    @FXML private Label totalAmountLabel;

    @FXML private Line validitySpacer;

    @FXML private RadioButton cardRadioButton;
    @FXML private RadioButton billRadioButton;

    @FXML private Button toReceiptStageButton;

    private LinkedList<TextField> textFields = new LinkedList<>();

    private static WizardPaymentController instance;
    private WizardStageController parentController;
    private ToggleGroup paymentMethod = new ToggleGroup();

    private WizardPaymentController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/wizard_payment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
            IOException exception) {
            throw new RuntimeException(exception);
        }

        Profile currentUser = FilesBackend.getInstance().readProfileFromFile();

        textFields.add(cardNoTextField);
        textFields.add(cardMonthTextField);
        textFields.add(cardYearTextField);
        textFields.add(cardCVCTextField);
        textFields.add(personalNumberTextField);
        toReceiptStageButton.toFront();

        if (currentUser != null) {
            cardNoTextField.setText(Helper.onlyShowLastCharacters(currentUser.getCardNumber(), 4));
            cardMonthTextField.setText(currentUser.getValidMonth() < 10 ? "0" + currentUser.getValidMonth() : "" + currentUser.getValidMonth());
            cardYearTextField.setText("" + currentUser.getValidYear());
            personalNumberTextField.setText(Helper.onlyShowLastCharacters(currentUser.getPersonalNumber(), 4));

            if(currentUser.isCardPayment()) {
                cardRadioButton.setSelected(true);
                selectCard();
            } else {
                billRadioButton.setSelected(true);
                selectInvoice();
            }
        }

        cardRadioButton.setToggleGroup(paymentMethod);
        billRadioButton.setToggleGroup(paymentMethod);

        UnaryOperator<TextFormatter.Change> onlyDigitsFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> cvcFormat = new TextFormatter<>(onlyDigitsFilter);
        cardCVCTextField.setTextFormatter(cvcFormat);
        limitTextLength(cardCVCTextField, 3);

        TextFormatter<String> cardMonthFormat = new TextFormatter<>(onlyDigitsFilter);
        cardMonthTextField.setTextFormatter(cardMonthFormat);
        limitTextLength(cardMonthTextField, 2);

        TextFormatter<String> cardYearFormat = new TextFormatter<>(onlyDigitsFilter);
        cardYearTextField.setTextFormatter(cardYearFormat);
        limitTextLength(cardYearTextField, 2);

        TextFormatter<String> cardNoFormat = new TextFormatter<>(onlyDigitsFilter);
        cardNoTextField.setTextFormatter(cardNoFormat);
        limitTextLength(cardNoTextField, 16);

        TextFormatter<String> personalNoFormat = new TextFormatter<>(onlyDigitsFilter);
        personalNumberTextField.setTextFormatter(personalNoFormat);
        limitTextLength(personalNumberTextField, 12);

        updateForwardButton();

    }

    public static WizardPaymentController getInstance() {
        if(instance == null)
            instance = new WizardPaymentController();
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

    public void refresh() {
        parentController.setBlockToDate();
        numberOfItemsLabel.setText(ShoppingCartExt.getInstance().getNumberOfItemsInCart() + " st");
        totalAmountLabel.setText(String.format("%1$,.2f kr", ShoppingCartExt.getInstance().getTotal() + 50));
    }

    @FXML
    private void toReceiptStage() {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        ShoppingCartExt.getInstance().getItems().stream()
            .map(x -> new ReceiptItem(x.getProduct(), x.getNumberOfItems()))
            .forEach(x -> receiptItems.add(x));
        Receipt receipt = new Receipt(receiptItems, new Date(), new Date(), 50.00);
        FilesBackend.getInstance().saveReceipt(receipt);
        parentController.setReceipt(receipt);
        ShoppingCartExt.getInstance().clear();
        parentController.viewReceiptStage();
    }

    @FXML
    private void toDeliveryStage() {
        if(!parentController.isDelayTimePassed()){ return; }
        parentController.viewPaymentStage();
    }

    private boolean cardNoValid() {
        return cardNoTextField.getText().length() == 16;
    }

    private boolean cardMonthValid() {
        if (cardMonthTextField.getText().isEmpty()) {
            return false;
        } else {
            int month = Integer.parseInt(cardMonthTextField.getText());
            if (month >= 1 && month <= 12) {
                return cardMonthTextField.getText().length() == 2;
            } else {
                return false;
            }
        }
    }

    private boolean cardYearValid() {
        if (cardYearTextField.getText().isEmpty()) {
            return false;
        } else {
            int year = Integer.parseInt(cardYearTextField.getText());
            if (year >= 19 && year <= 29) {
                return cardYearTextField.getText().length() == 2;
            } else {
                return false;
            }
        }
    }

    private boolean cardCVCValid() {
        return cardCVCTextField.getText().length() == 3;
    }

    private boolean personalNumberValid() {
        return personalNumberTextField.getText().length() == 12;
    }

    private boolean allTextFieldsAreValid() {
        if (billRadioButton.isSelected()) {
            return personalNumberValid();
        } else {
            return cardNoValid() &&
                cardMonthValid() &&
                cardYearValid() &&
                cardCVCValid();
        }
    }

    @FXML
    private void selectInvoice() {
        updateForwardButton();
        cardNoTextField.setDisable(true);
        cardNoLabel.setStyle("-fx-text-fill: grey-primary");

        cardCVCTextField.setDisable(true);
        cardCVCLabel.setStyle("-fx-text-fill: grey-primary");

        cardValidityLabel.setStyle("-fx-text-fill: grey-primary");
        cardYearTextField.setDisable(true);
        cardMonthTextField.setDisable(true);

        personalNumberTextField.setDisable(false);
        personalNumberLabel.setStyle("fx-text-fill: black");

        validitySpacer.setStyle("-fx-border-color: grey-primary");
    }

    @FXML
    private void selectCard() {
        updateForwardButton();
        cardNoTextField.setDisable(false);
        cardNoLabel.setStyle("-fx-text-fill: black");

        cardCVCTextField.setDisable(false);
        cardCVCLabel.setStyle("-fx-text-fill: black");

        cardValidityLabel.setStyle("-fx-text-fill: black");
        cardMonthTextField.setDisable(false);
        cardYearTextField.setDisable(false);

        personalNumberLabel.setStyle("fx-text-fill: grey-primary");
        personalNumberTextField.setDisable(true);

        validitySpacer.setStyle("-fx-border-color: black");
    }

    private void updateForwardButton() {
        if (!allTextFieldsAreValid()) {
            toReceiptStageButton.setDisable(true);
        } else {
            toReceiptStageButton.setDisable(false);
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
                        int index = textFields.indexOf(field);
                        textFields.get(index < textFields.size() - 1 ? index + 1 : index).requestFocus();
                    }
                } else {
                    if (newValue.intValue() < limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
                    }

                }
                updateForwardButton();
            }
        });
    }
}
