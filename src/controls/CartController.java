package controls;

import backend.CartEvent;
import backend.ShoppingCartListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    @FXML private Label cartItemsLabel;
    @FXML private Label cartTotalLabel;
    @FXML private FlowPane cartFlowPane;
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane emptyCartMessage;

    private StoreStageController parentController;

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

        shoppingItems = ShoppingCartExt.getInstance().getItems();
        updateCartLabels();

//        Platform.runLater(() -> {
//            CartBackend.getInstance().getLoadedShoppingItems().forEach(x -> new CartEvent(this).setShoppingItem(x).setAddEvent(true));
//            //shoppingItems.addAll(CartBackend.getInstance().readFromCartFile());
//            updateCartLabels();
//        });
    }

    public static CartController getInstance(){
        if(cartController == null)
            cartController = new CartController();
        return cartController;
    }

    public void setParentController(StoreStageController controller) {
        this.parentController = controller;
    }

    @Override
    public void shoppingCartChanged(CartEvent event) {
        if (event.isAddEvent()) {
            addCartItem(event.getShoppingItem());
        }
        else {
            removeCartItem(event.getShoppingItem());
        }
        updateCartLabels();
    }

    private boolean isInCart(ShoppingItem shoppingItem) {
        for (ShoppingItem item : ShoppingCartExt.getInstance().getItems())
            if (shoppingItem.getProduct().getProductId() == (item.getProduct().getProductId()) && shoppingItem.getNumberOfItems() > 1)
                return true;
        return false;
    }

    public void updateCartLabels() {
        cartItemsLabel.setText(ShoppingCartExt.getInstance().getNumberOfItemsInCart() + " st varor");
        cartTotalLabel.setText(String.format("Totalt %1$,.2f kr", ShoppingCartExt.getInstance().getTotal()));
    }

    public void addCartItem(ShoppingItem item) {
        if (!(isInCart(item))) {
            currentCartItem = new CartItem(item);
            // Remove empty cart message if first card is added
            if (currentItems.isEmpty()) {
                cartFlowPane.getChildren().clear();
            }
            cartFlowPane.getChildren().add(currentCartItem);
            currentItems.put(item,currentCartItem);
            scrollPane.setVvalue(1.0);
        }
        currentItems.get(item).updateLabels();
    }

    private void removeCartItem(ShoppingItem item) {
        if (item.getNumberOfItems() == 0) {
            cartFlowPane.getChildren().remove(currentItems.get(item));
            currentCartItem.updateLabels();
            currentItems.remove(item);
            // Add empty cart message if no items are left in cart
            if (currentItems.isEmpty()) {
                cartFlowPane.getChildren().add(emptyCartMessage);
            }
        }else{
            currentItems.get(item).updateLabels();
        }
    }

    @FXML
    private void goToCheckout() {

    }

    @FXML
    private void emptyTheCart() {

        ShoppingCartExt.getInstance().clear();
    }

}
