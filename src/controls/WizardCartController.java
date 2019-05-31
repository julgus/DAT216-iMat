package controls;

import backend.CartEvent;
import backend.ShoppingCartListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.CartItem;
import model.ShoppingCartExt;
import model.ShoppingItem;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class WizardCartController extends AnchorPane implements ShoppingCartListener {

    @FXML
    private ScrollPane wizardCartScrollPane;
    @FXML
    private FlowPane wizardCartFlowPane;
    @FXML
    private Label wizardCartPrice;
    @FXML
    private Label wizardCartTotalPrice;
    @FXML
    private Button wizardToDeliveryButton;
    @FXML
    private StackPane cartStackPane;
    @FXML
    private AnchorPane emptyCartView;
    @FXML
    private VBox cartView;

    private WizardStageController parentController;
    private static WizardCartController wizardCartController;
    private Map<ShoppingItem,WizardCartItem> currentWizardItems = new HashMap<>();
    private List<ShoppingItem> shoppingItems;

    private WizardCartController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/wizard_cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        ShoppingCartExt.getInstance().addShoppingCartListener(this);

        shoppingItems = ShoppingCartExt.getInstance().getItems();
        updateWizardCartLabels();
    }

    public static WizardCartController getInstance(){
        if(wizardCartController == null) {
            wizardCartController = new WizardCartController();
        }
        return wizardCartController;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

    public void refresh() {
        parentController.setBlockToDate();
        updateWizardCartLabels();
        if (!cartIsEmpty()) {
            cartView.setVisible(true);
            wizardToDeliveryButton.setDisable(false);
        }
    }

    public void updateWizardCartLabels() {
        wizardCartTotalPrice.setText(String.format("%1$,.2f", ShoppingCartExt.getInstance().getTotal() + 50) + " kr");
        wizardCartPrice.setText(String.format("%1$,.2f", ShoppingCartExt.getInstance().getTotal()) + " kr");
    }

    private boolean isInWizardCart(ShoppingItem shoppingItem) {
        for (ShoppingItem item : ShoppingCartExt.getInstance().getItems())
            if (shoppingItem.getProduct().getProductId() == (item.getProduct().getProductId()) && shoppingItem.getNumberOfItems() > 1)
                return true;
        return false;
    }

    private void addWizardCartItem(ShoppingItem item) {
        WizardCartItem currentWizardItem;
        if (!(isInWizardCart(item))) {
            currentWizardItem = new WizardCartItem(item);
            // Remove empty cart message if first card is added
            if (currentWizardItems.isEmpty()) {
                wizardCartFlowPane.getChildren().clear();
            }
            wizardCartFlowPane.getChildren().add(currentWizardItem);
            currentWizardItems.put(item,currentWizardItem);
            wizardCartScrollPane.setVvalue(1.0);
        }
        currentWizardItems.get(item).updateLabels();
    }

    private void removeWizardCartItem(ShoppingItem item) {
        if (item.getNumberOfItems() == 0) {
            wizardCartFlowPane.getChildren().remove(currentWizardItems.get(item));
            currentWizardItems.remove(item);
            // Add empty cart message if no items are left in cart
            }
    }

    @Override
    public void shoppingCartChanged(CartEvent event) {
        if (event.isAddEvent()) {
            addWizardCartItem(event.getShoppingItem());
        }
        else {
            removeWizardCartItem(event.getShoppingItem());
        }
        updateWizardCartLabels();
        if (currentWizardItems.get(event.getShoppingItem()) != null) {
            currentWizardItems.get(event.getShoppingItem()).updateLabels();
        }
        if (cartIsEmpty()) {
            cartView.setVisible(false);
            wizardToDeliveryButton.setDisable(true);
        }
    }

    private boolean cartIsEmpty() {
        return currentWizardItems.isEmpty();
    }

    @FXML
    private void toDeliveryStage() {
        if(!parentController.isDelayTimePassed()){ return; }
        parentController.setBlockToDate();
        System.out.println("Proceeding to delivery");
        parentController.toDeliveryStage();
    }

    @FXML private void backToStore() {
        parentController.backToStore();
    }
}


