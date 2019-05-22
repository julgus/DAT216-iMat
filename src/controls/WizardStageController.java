package controls;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.WizardStage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/* This class is responsible for holding all the nodes of the Check-out Stage  */

public class WizardStageController implements Initializable {

    @FXML private Button cartStageButton;
    @FXML private Button deliveryStageButton;
    @FXML private Button paymentStageButton;
    @FXML private Button receiptStageButton;
    @FXML private Button returnToStore;

    @FXML private Label cartStageLabel;
    @FXML private Label deliveryStageLabel;
    @FXML private Label paymentStageLabel;
    @FXML private Label receiptStageLabel;
    @FXML private Label wizardTitleLabel;
    @FXML private Label wizardInstructionsLabel;

    @FXML private Line cartToDeliveryLine;
    @FXML private Line deliveryToPaymentLine;
    @FXML private Line paymentToReceiptLine;

    @FXML private AnchorPane wizardMainPane;

    private WizardPaymentController paymentController;
    private WizardReceiptController receiptController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

      //  paymentController = WizardPaymentController.getInstance();

      //  receiptController = WizardReceiptController.getInstance();

    }

    @FXML
    public void viewCartStage() {
        updateWizardVisualization(WizardStage.Cart);
        wizardMainPane.getChildren().clear();
     //   wizardMainPane.getChildren().add()
    }

    @FXML
    public void viewDeliveryStage() {
        updateWizardVisualization(WizardStage.Delivery);
    }

    @FXML
    public void viewPaymentStage() {
        updateWizardVisualization(WizardStage.Payment);

    }

    @FXML
    public void viewReceiptStage() {
        updateWizardVisualization(WizardStage.Receipt);

    }

    private void updateWizardVisualization(WizardStage stage) {
        switch (stage) {
            case Cart:
                addActiveButtonStyling(cartStageButton);
                addPassiveButtonStyling(deliveryStageButton, paymentStageButton, receiptStageButton);
                addPassiveLineStyling(cartToDeliveryLine, deliveryToPaymentLine, paymentToReceiptLine);
                wizardTitleLabel.setText("Din varukorg");
                wizardInstructionsLabel.setText("Granska och redigera din varukorg innan du går vidare till val av leveranstid.");
                break;
            case Delivery:
                addActiveButtonStyling(deliveryStageButton);
                addPassiveButtonStyling(paymentStageButton, receiptStageButton);
                addActiveLineStyling(cartToDeliveryLine);
                addPassiveLineStyling(deliveryToPaymentLine, paymentToReceiptLine);
                wizardTitleLabel.setText("Leverans");
                wizardInstructionsLabel.setText("Här anger du din leveransadress samt din önskade leveranstid.");
                break;
            case Payment:
                addActiveButtonStyling(paymentStageButton);
                addPassiveButtonStyling(receiptStageButton);
                addActiveLineStyling(deliveryToPaymentLine);
                addPassiveLineStyling(paymentToReceiptLine);
                wizardTitleLabel.setText("Betalning");
                wizardInstructionsLabel.setText("Här anger du din leveransadress samt din önskade leveranstid.");
                break;
            case Receipt:
                addActiveButtonStyling(receiptStageButton);
                addActiveLineStyling(paymentToReceiptLine);
                wizardTitleLabel.setText("Kvitto");
                wizardInstructionsLabel.setText("Nedan visas en sammanställning av ditt köp samt din valda leveranstid.");
                break;
            default:
                break;
        }
    }

    private void addActiveButtonStyling(Button... buttons) {
        Stream.of(buttons)
            .forEach(x -> x.getStyleClass().clear());

        Stream.of(buttons)
            .forEach(x -> x.getStyleClass().addAll("button", "wizard-icons-active"));
    }

    private void addActiveLineStyling(Line... lines) {
        Stream.of(lines)
            .forEach(x -> x.getStyleClass().clear());

        Stream.of(lines)
            .forEach(x -> x.getStyleClass().addAll("button", "wizard-line-active"));
    }

    private void addPassiveButtonStyling(Button ... buttons) {
        Stream.of(buttons)
            .forEach(x -> x.getStyleClass().clear());

        Stream.of(buttons)
            .forEach(x -> x.getStyleClass().addAll("button", "wizard-icons-passive"));
    }

    private void addPassiveLineStyling(Line ... lines) {
        Stream.of(lines)
            .forEach(x -> x.getStyleClass().clear());

        Stream.of(lines)
            .forEach(x -> x.getStyleClass().addAll("button", "wizard-line-passive"));
    }

}
