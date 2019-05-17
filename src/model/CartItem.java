package model;

import controls.CartController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CartItem extends AnchorPane {

    @FXML
    private Label cartItemProduct;
    @FXML
    private Label cartItemPrice;
    @FXML
    private ImageView cartItemImage;



    public CartItem(ShoppingItem item)
    {
        //System.out.println(getClass().getResource("/views/cart_item.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/cart_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }
        this.cartItemProduct.setText(item.getProduct().getName());
        this.cartItemPrice.setText(item.getProduct().getPrice()+"kr");
        this.cartItemImage.setImage(new Image("images/" + item.getProduct().getImageName()));
    }
    //TODO
    public void updateNumberOfItems(){

    }

}
