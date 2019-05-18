package model;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CartItem extends AnchorPane {

    @FXML private Label cartItemProduct;
    @FXML private Label cartItemPrice;
    @FXML private ImageView cartItemImage;
    @FXML private Label numberOfItems;

    ShoppingItem item;

    public CartItem(ShoppingItem item)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/cart_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }

        this.item = item;
        this.cartItemProduct.setText(item.getProduct().getName());
        this.cartItemPrice.setText(item.getProduct().getPrice()+" kr");
        this.cartItemImage.setImage(new Image("images/" + item.getProduct().getImageName()));
    }

    private void updateLabel(){
        numberOfItems.setText(Integer.toString(item.getNumberOfItems())+" st");
    }

    @FXML
    protected void onClick(Event event){

    }

    @FXML
    public void addToCart(){
        ShoppingCartExt.getInstance().addItem(item);
        updateLabel();
    }

    @FXML
    public void removeFromCart(){
        ShoppingCartExt.getInstance().removeItem(item);
        if(item.getNumberOfItems() >= 0) {
            updateLabel();
        }
    }

}
