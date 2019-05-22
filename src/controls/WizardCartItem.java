package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.ShoppingItem;

import java.io.IOException;

public class WizardCartItem extends AnchorPane {

    @FXML
    ImageView wizardCartItemImageView;
    @FXML
    Label wizardCartItemName;
    @FXML
    Label wizardCartItemPrice;

    ShoppingItem item;

    public WizardCartItem(ShoppingItem item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/wizard_cart_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }
        this.item = item;

        wizardCartItemName.setText(item.getProduct().getName());
        wizardCartItemPrice.setText(String.format("%1$,.2f", item.getProduct().getPrice()) + " " + item.getProduct().getUnit());
        wizardCartItemImageView.setImage(new Image("images/" + item.getProduct().getImageName()));
    }
}
