package controls;

import backend.CartEvent;
import backend.ShoppingCartListener;
import helper.Helper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.CartItem;

import model.ShoppingCartExt;
import model.ShoppingItem;

import java.io.IOException;
import java.util.List;


public class CartController extends AnchorPane implements ShoppingCartListener {

    private List<ShoppingItem> cartItems;
    private CartItem currentItem;
    private static CartController cartController;

    @FXML FlowPane cartFlowPane;

    private CartController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/shopping_cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }


    }


    public static CartController getInstance(){
        if(cartController == null)
            cartController = new CartController();
        return cartController;
    }

    @Override
    public void shoppingCartChanged(CartEvent event) {
        cartItems = ShoppingCartExt.getInstance().getItems();
        currentItem = new CartItem(event.getShoppingItem());
        System.out.println("Hej");
        if (event.isAddEvent()) {
            System.out.println("la");

            if (isInCart(event.getShoppingItem())) {
                cartFlowPane.getChildren().add(currentItem);

            }

        }
        //TODO: REMOVE FUNKAR INTE
        else {
            if (event.getShoppingItem().getNumberOfItems() <= 1) {
                cartFlowPane.getChildren().remove(currentItem);
                cartItems.remove(event.getShoppingItem());
            }

        }


        //currentItem.updateNumberOfItems();
    }

    //TODO: DENNA LOGIK FUNKAR EJ, LÄGGER TILL FLERA I VARUKORGEN
    private boolean isInCart(ShoppingItem shoppingItem) {
        for (ShoppingItem item : cartItems)
            if (shoppingItem == item)
                return true;
        return false;
    }
}
