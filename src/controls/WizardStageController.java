package controls;

import helper.Helper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.ProductPrimaryCategory;
import model.Receipt;
import model.WizardStage;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Stream;

/* This class is responsible for holding all the nodes of the Check-out Stage  */

public class WizardStageController implements Initializable{

    //Sequence map buttons
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

    @FXML private ImageView cartImage;
    @FXML private ImageView deliveryImage;
    @FXML private ImageView paymentImage;
    @FXML private ImageView receiptImage;

    @FXML private AnchorPane wizardMainPane;

    private WizardCartController cartController;
    private WizardDeliveryController deliveryController;
    private WizardPaymentController paymentController;
    private WizardReceiptController receiptController;

    private WizardStage currentStage;
    private static Receipt receipt;
    private long mBlockToTime; // set load time on initiation
    private final int BlockInteractionsMs = 500;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cartController = WizardCartController.getInstance();
        cartController.setParentController(this);
        viewCartStage();
        currentStage = WizardStage.Cart;

        deliveryController = WizardDeliveryController.getInstance();
        deliveryController.setParentController(this);

        paymentController = WizardPaymentController.getInstance();
        paymentController.setParentController(this);

        receiptController = WizardReceiptController.getInstance();
        receiptController.setParentController(this);
    }

    @FXML
    public void backToStore() {
        Helper.fireGoToStoreEvent();
        viewCartStage();
    }

    public void backToStore(ProductPrimaryCategory targetPage) {
        Helper.fireGoToStoreEvent(targetPage);
        viewCartStage();
    }
    /*
    METHODS FOR SEQUENCE MAP BUTTONS
     */
    @FXML
    public void toCartStage() {
        if(currentStage != WizardStage.Receipt) {
            updateWizardVisualization(WizardStage.Cart);
            wizardMainPane.getChildren().clear();
            wizardMainPane.getChildren().add(cartController);
            cartController.refresh();
        }
    }

    @FXML
    public void toDeliveryStage() {
        //if(currentStage != WizardStage.Cart && currentStage != WizardStage.Receipt){
        updateWizardVisualization(WizardStage.Delivery);
        wizardMainPane.getChildren().clear();
        wizardMainPane.getChildren().add(deliveryController);
        deliveryController.refresh();
        //}
    }


    /*
    METHODS FOR NEXT/PREVIOUS BUTTONS
     */

    public void viewCartStage() {
        updateWizardVisualization(WizardStage.Cart);
        wizardMainPane.getChildren().clear();
        wizardMainPane.getChildren().add(cartController);
        cartController.refresh();
    }

    public void viewPaymentStage() {
        updateWizardVisualization(WizardStage.Payment);
        wizardMainPane.getChildren().clear();
        wizardMainPane.getChildren().add(paymentController);
        paymentController.refresh();
    }


    public void viewReceiptStage() {
        updateWizardVisualization(WizardStage.Receipt);
        wizardMainPane.getChildren().clear();
        wizardMainPane.getChildren().add(receiptController);
        receiptController.refresh();
    }

    protected boolean isDelayTimePassed(){
        var res = new Date().getTime() > mBlockToTime;
        if (!res){
            System.out.println("Click temporarily blocked, try again in: " + (mBlockToTime - new Date().getTime()) + " milliseconds");
        }
        return res;
    }

    protected void setBlockToDate(){
        var cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MILLISECOND, BlockInteractionsMs);
        mBlockToTime = cal.getTimeInMillis();
    }

    private void updateWizardVisualization(WizardStage stage) {
        switch (stage) {
            case Cart:
                addActiveButtonStyling(cartStageButton);
                addPassiveButtonStyling(deliveryStageButton, paymentStageButton, receiptStageButton);
                addPassiveLineStyling(cartToDeliveryLine, deliveryToPaymentLine, paymentToReceiptLine);
                addActiveLabelStyling(cartStageLabel);
                addPassiveLabelStyling(deliveryStageLabel, paymentStageLabel, receiptStageLabel);
                wizardTitleLabel.setText("Din varukorg");
                wizardInstructionsLabel.setText("Granska och redigera din varukorg innan du går vidare till val av leveranstid.");
                cartImage.setImage(getStageImage(WizardStage.Cart, true));
                cartImage.setOpacity(1);
                deliveryImage.setImage(getStageImage(WizardStage.Delivery, false));
                deliveryImage.setOpacity(0.3);
                paymentImage.setImage(getStageImage(WizardStage.Payment, false));
                paymentImage.setOpacity(0.3);
                receiptImage.setImage(getStageImage(WizardStage.Receipt, false));
                receiptImage.setOpacity(0.3);
                currentStage = WizardStage.Cart;
                break;
            case Delivery:
                addActiveButtonStyling(deliveryStageButton);
                addPassiveButtonStyling(paymentStageButton, receiptStageButton);
                addActiveLineStyling(cartToDeliveryLine);
                addPassiveLineStyling(deliveryToPaymentLine, paymentToReceiptLine);
                addActiveLabelStyling(deliveryStageLabel);
                addPassiveLabelStyling(paymentStageLabel, receiptStageLabel);
                wizardTitleLabel.setText("Leverans");
                wizardInstructionsLabel.setText("Här anger du din leveransadress samt din önskade leveranstid.");
                cartImage.setImage(getStageImage(WizardStage.Cart, true));
                cartImage.setOpacity(1);
                deliveryImage.setImage(getStageImage(WizardStage.Delivery, true));
                deliveryImage.setOpacity(1);
                paymentImage.setImage(getStageImage(WizardStage.Payment, false));
                paymentImage.setOpacity(0.3);
                receiptImage.setImage(getStageImage(WizardStage.Receipt, false));
                receiptImage.setOpacity(0.3);
                currentStage = WizardStage.Delivery;
                break;
            case Payment:
                addActiveButtonStyling(paymentStageButton);
                addPassiveButtonStyling(receiptStageButton);
                addActiveLineStyling(deliveryToPaymentLine);
                addPassiveLineStyling(paymentToReceiptLine);
                addActiveLabelStyling(paymentStageLabel);
                addPassiveLabelStyling(receiptStageLabel);
                wizardTitleLabel.setText("Betalning");
                wizardInstructionsLabel.setText("Välj ett betalsätt och ange den information som efterfrågas.");
                cartImage.setImage(getStageImage(WizardStage.Cart, true));
                cartImage.setOpacity(1);
                deliveryImage.setImage(getStageImage(WizardStage.Delivery, true));
                deliveryImage.setOpacity(1);
                paymentImage.setImage(getStageImage(WizardStage.Payment, true));
                paymentImage.setOpacity(1);
                receiptImage.setImage(getStageImage(WizardStage.Receipt, false));
                receiptImage.setOpacity(0.3);
                currentStage = WizardStage.Payment;
                break;
            case Receipt:
                addActiveButtonStyling(receiptStageButton);
                addActiveLineStyling(paymentToReceiptLine);
                addActiveLabelStyling(receiptStageLabel);
                wizardTitleLabel.setText("Kvitto");
                wizardInstructionsLabel.setText("Nedan visas en sammanställning av ditt köp samt din valda leveranstid.");
                cartImage.setImage(getStageImage(WizardStage.Cart, true));
                cartImage.setOpacity(1);
                deliveryImage.setImage(getStageImage(WizardStage.Delivery, true));
                deliveryImage.setOpacity(1);
                paymentImage.setImage(getStageImage(WizardStage.Payment, true));
                paymentImage.setOpacity(1);
                receiptImage.setImage(getStageImage(WizardStage.Receipt, true));
                receiptImage.setOpacity(1);
                currentStage = WizardStage.Receipt;
                break;
            default:
                return;
        }
    }

    private void addActiveLabelStyling(Label ... labels) {
        Stream.of(labels)
            .forEach(x -> x.setOpacity(1));
    }

    private void addPassiveLabelStyling(Label ... labels) {
        Stream.of(labels)
            .forEach(x -> x.setOpacity(0.3));
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

    private Image getStageImage(WizardStage stage, boolean active) {
        String imagePath = "";
        switch (stage) {
            case Cart:
                imagePath = active ? "images/shopping-cart-white.png" : "images/shopping-cart-black.png";
                break;
            case Delivery:
                imagePath = active ? "images/truck-white.png" : "images/truck-black.png";
                break;
            case Payment:
                imagePath = active ? "images/coins-white.png" : "images/coins-black.png";
                break;
            case Receipt:
                imagePath = active ? "images/invoice-white.png" : "images/invoice-black.png";
                break;
            default:
                 break;
        }
        return new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Receipt getReceipt() {
        return receipt;
    }


}

