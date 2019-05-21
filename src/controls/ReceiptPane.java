package controls;

import backend.Backend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import model.Receipt;
import model.ReceiptItem;
import model.ShoppingCartExt;

import java.io.IOException;

public class ReceiptPane extends TitledPane {

    @FXML private Label purchaseDateLabel;
    @FXML private Label deliveryStatusLabel;
    @FXML private Label deliveryDateLabel;
    @FXML private Label totalPriceLabel;
    @FXML private Label sumItemsLabel;
    @FXML private Label deliveryFeeLabel;
    @FXML private Label totalAmountLabel;
    @FXML private FlowPane receiptItemPane;

    private Receipt receipt;

    public ReceiptPane(Receipt receipt, ReceiptsController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/receipt_header.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.receipt = receipt;

        purchaseDateLabel.setText(receipt.getDeliveryDate().toString());
        deliveryDateLabel.setText(receipt.getDeliveryDate().toString());
        deliveryStatusLabel.setText(receipt.isDelivered()? "Levererad" : "Ej levererad");
        totalPriceLabel.setText(String.format("%1$,.2f", receipt.getTotalAmount()) + " kr");
        sumItemsLabel.setText(String.format("%1$,.2f", receipt.getTotalAmount() - receipt.getDeliveryFee()) + " kr");
        totalAmountLabel.setText(String.format("%1$,.2f", receipt.getTotalAmount() - receipt.getDeliveryFee()) + " kr");
        deliveryFeeLabel.setText(String.format("%1$,.2f", receipt.getDeliveryFee()) + " kr");

        addReceiptItemsToPane();

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

    @FXML
    private void addReceiptItemsToCart() {
        receipt.getReceiptItems().stream()
            .forEach(x -> ShoppingCartExt.getInstance()
                .addItems(Backend.getInstance()
                    .getShoppingItem(x.getProduct().getProductId()), x.getNumberOfItems()));
    }

}
