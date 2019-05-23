package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.ShoppingCartExt;
import model.ShoppingItem;

import java.io.IOException;

public class WizardCartItem extends AnchorPane {

    @FXML
    private ImageView wizardCartItemImageView;
    @FXML
    private Label wizardCartItemName;
    @FXML
    private Label wizardCartItemPrice;
    @FXML
    private Label numberOfItems;

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
        wizardCartItemPrice.setText(String.format("%1$,.2f", item.getProduct().getPrice() * item.getNumberOfItems()) + " kr");
        numberOfItems.setText(item.getNumberOfItems() + " st");
        wizardCartItemImageView.setImage(new Image("images/" + item.getProduct().getImageName()));
    }

    public void updateLabels(){
        updateNoOfItems();
        updatePrice();
    }

    private void updateNoOfItems(){
        String unit = item.getProduct().getUnit().equals("kr/kg") ? " kg" : " st";
        numberOfItems.setText((item.getNumberOfItems()) + unit);
    }

    private void updatePrice(){
        wizardCartItemPrice.setText(String.format("%1$,.2f", item.getProduct().getPrice() * item.getNumberOfItems()) + " kr");
    }

    @FXML
    public void addToCart(){
        ShoppingCartExt.getInstance().addItem(item);
    }

    @FXML
    public void removeFromCart(){
        ShoppingCartExt.getInstance().removeItem(item);
    }

}
