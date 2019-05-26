package controls;

import backend.FilesBackend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.ProductPrimaryCategory;
import model.Receipt;
import model.ReceiptItem;

import java.io.IOException;

public class WizardReceiptController extends AnchorPane {

    @FXML private FlowPane receiptItemPane;
    @FXML private Label deliveryInfoLabel;
    @FXML private Label totalItemsLabel;
    @FXML private Label totalAmountLabel;

    private static WizardReceiptController instance;
    private WizardStageController parentController;
    private Receipt receipt;

    private WizardReceiptController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/wizard_receipt.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
            IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void refresh() {
        parentController.setBlockToDate();
        receipt = parentController.getReceipt();
        totalItemsLabel.setText(String.format("%1$,.2f", receipt.getTotalAmount() - receipt.getDeliveryFee()) + " kr");
        totalAmountLabel.setText("Totalt " + String.format("%1$,.2f", receipt.getTotalAmount()) + " kr");
        addReceiptItemsToPane();
    }

    public static WizardReceiptController getInstance() {
        if(instance == null)
            instance = new WizardReceiptController();
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

    private void addReceiptItemsToPane() {
        receiptItemPane.getChildren().clear();

        receipt.getReceiptItems().stream()
            .map(this::getReceiptItemCard)
            .forEach(x -> receiptItemPane.getChildren().add(x));
    }

    private ReceiptItemCard getReceiptItemCard(ReceiptItem item){
        return new ReceiptItemCard(item);
    }

    @FXML private void viewReceipts() {
        if(!parentController.isDelayTimePassed()){ return; }
        parentController.backToStore(ProductPrimaryCategory.Kvitton);
    }

    @FXML private void returnToStore() {
        if(!parentController.isDelayTimePassed()){ return; }
        parentController.backToStore();
    }

}
