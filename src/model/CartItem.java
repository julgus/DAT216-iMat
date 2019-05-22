package model;

import javafx.animation.FillTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.Clock;

public class CartItem extends AnchorPane {

    @FXML private Label cartItemProduct;
    @FXML private Label cartItemPrice;
    @FXML private ImageView cartItemImage;
    @FXML private Label numberOfItems;
    @FXML private Rectangle backgroundRect;

    ShoppingItem item;
    FillTransition ft;

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
        cartItemProduct.setText(item.getProduct().getName());
        cartItemPrice.setText(String.format("%1$,.2f", item.getProduct().getPrice()) + " " + item.getProduct().getUnit());
        cartItemImage.setImage(new Image("images/" + item.getProduct().getImageName()));

        ft = new FillTransition(Duration.millis(1500), backgroundRect, Color.web("#F5842C"), Color.WHITE);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void updateLabels(){
        updateNoOfItems();
        updatePrice();
    }

    private void updateNoOfItems(){
        numberOfItems.setText((item.getNumberOfItems())+" st");
    }
    private void updatePrice(){
        cartItemPrice.setText(String.format("%1$,.2f", item.getProduct().getPrice() * item.getNumberOfItems()) + " kr");
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
