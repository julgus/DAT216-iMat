package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.ReceiptItem;

import java.io.IOException;

public class ReceiptItemCard extends AnchorPane {

    ReceiptItem item;

    @FXML private Label receiptItemProduct;
    @FXML private Label receiptItemNumber;
    @FXML private Label receiptItemPrice;
    @FXML private ImageView receiptItemImage;

    public ReceiptItemCard(ReceiptItem item)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/receipt_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }

        this.item = item;

        String productName = item.getProduct().getUnit().equals(" kr/kg") ? item.getProduct().getName() + " 1 kg" : item.getProduct().getName();
        receiptItemProduct.setText(productName);
        receiptItemPrice.setText(String.format("%1$,.2f", item.getProduct().getPrice()*item.getNumberOfItems()) + " kr");
        receiptItemNumber.setText(item.getNumberOfItems() + " st");
        receiptItemImage.setImage(new Image("images/" + item.getProduct().getImageName()));
    }

}
