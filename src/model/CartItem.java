package model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class CartItem {

    @FXML
    private Label cartItemProduct;
    @FXML
    private Label cartItemPrice;
    @FXML
    private ImageView carItemImage;



    public CartItem()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/cart_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {

            throw new RuntimeException(exception);
        }
    }

}
