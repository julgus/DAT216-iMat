package controls;

import backend.CartEvent;
import backend.ShoppingCartListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.CartItem;

import model.ShoppingCartExt;
import model.ShoppingItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartController extends AnchorPane implements ShoppingCartListener {

    private List<ShoppingItem> shoppingItems;
    private Map<ShoppingItem,CartItem> currentItems = new HashMap<>();
    private CartItem currentCartItem;
    private static CartController cartController;
    private ShoppingCartExt shoppingCart;

    @FXML FlowPane cartFlowPane;
    @FXML Label cartItemsLabel;
    @FXML Label cartTotalLabel;

    public CartController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/shopping_cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        shoppingItems = ShoppingCartExt.getInstance().getItems();
    }

    public static CartController getInstance(){
        if(cartController == null)
            cartController = new CartController();
        return cartController;
    }

    @Override
    public void shoppingCartChanged(CartEvent event) {
        if (event.isAddEvent()) {

            if (!(isInCart(event.getShoppingItem()))) {
                currentCartItem = new CartItem(event.getShoppingItem());
                cartFlowPane.getChildren().add(currentCartItem);
                currentItems.put(event.getShoppingItem(),currentCartItem);
            }
            currentItems.get(event.getShoppingItem()).updateLabels();

        }
        else {
            if (event.getShoppingItem().getNumberOfItems() == 0) {
                cartFlowPane.getChildren().remove(currentItems.get(event.getShoppingItem()));
                currentCartItem.updateLabels();
                currentItems.remove(event.getShoppingItem());
            }else{
                currentItems.get(event.getShoppingItem()).updateLabels();
            }

        }

        cartItemsLabel.setText(ShoppingCartExt.getInstance().getNumberOfItemsInCart() + " st varor |");
        cartTotalLabel.setText("Totalt: "+ ShoppingCartExt.getInstance().getTotal()+ " kr");


    }

    private boolean isInCart(ShoppingItem shoppingItem) {
        for (ShoppingItem item : ShoppingCartExt.getInstance().getItems())
            if (shoppingItem == item && shoppingItem.getNumberOfItems() > 1)
                return true;
        return false;
    }

}
