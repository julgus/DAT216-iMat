package controls;

import backend.Backend;
import backend.CartEvent;
import backend.FilesBackend;
import backend.ShoppingCartListener;
import helper.Helper;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.*;


import java.io.IOException;
import java.util.Date;
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
    @FXML private Button checkoutButton;
    @FXML private Button emptyCartButton;
    @FXML private Button yesButton;
    @FXML private Button noButton;
    @FXML private AnchorPane lightboxPane;
    @FXML private AnchorPane dialogePane;

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

        if (cartIsEmpty()) {
            emptyCartButton.setDisable(true);
            checkoutButton.setDisable(true);
        }
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
        cartTotalLabel.setText(String.format("%1$,.2f kr", ShoppingCartExt.getInstance().getTotal()));
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
            scrollPane.layout();
            scrollPane.setVvalue(1.0);
            emptyCartButton.setDisable(false);
            checkoutButton.setDisable(false);
        }
        currentItems.get(item).updateLabels();
    }

    private void removeCartItem(ShoppingItem item) {
        if (item.getNumberOfItems() == 0) {
            cartFlowPane.getChildren().remove(currentItems.get(item));
            currentCartItem.updateLabels();
            currentItems.remove(item);
            // Add empty cart message if no items are left in cart
            if (cartIsEmpty()) {
                emptyCartButton.setDisable(true);
                checkoutButton.setDisable(true);
                cartFlowPane.getChildren().add(emptyCartMessage);
            }
        }else{
            currentItems.get(item).updateLabels();
        }
    }

    @FXML
    private void goToCheckout() {
        Helper.fireGoToCartEvent();
    }

    @FXML
    private void emptyTheCart() {
        lightboxPane.toFront(); //BRING TO FRONT
        dialogePane.toFront();

    }
    @FXML
    private void empty(){
        //Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
        //alert.setTitle("Varning!");
        //ImageView garbage = new ImageView("/images/garbage.png");
        //garbage.setFitWidth(30);
        //garbage.setFitHeight(30);
        //alert.setGraphic(garbage);
        //alert.getDialogPane().getStyleClass().addAll("text-normal-medium");
        //alert.setHeaderText("Är du säker på att du vill tömma varukorgen?");
        //alert.showAndWait();
        //if (alert.getResult() == ButtonType.YES) {
        ShoppingCartExt.getInstance().clear();
        dialogePane.toBack();
        lightboxPane.toBack();
    }
    @FXML
    private void cancelEmpty(){
        dialogePane.toBack();
        lightboxPane.toBack();
    }


    private boolean cartIsEmpty() {
        return currentItems.isEmpty();
    }

    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }
}
