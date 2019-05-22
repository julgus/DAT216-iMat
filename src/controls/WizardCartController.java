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
import model.CartItem;
import model.ShoppingCartExt;
import model.ShoppingItem;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WizardCartController extends AnchorPane implements ShoppingCartListener {

    @FXML
    ScrollPane wizardCartScrollPane;
    @FXML
    FlowPane wizardCartFlowPane;
    @FXML
    Label wizardCartPrice;
    @FXML
    Label wizardCartTotalPrice;
    @FXML
    Button wizardToDeliveryButton;

    private WizardStageController parentController;
    private static WizardCartController wizardCartController;
    private Map<ShoppingItem,WizardCartItem> currentWizardItems = new HashMap<>();
    private WizardCartItem currentWizardItem;
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

        shoppingItems = ShoppingCartExt.getInstance().getItems();
        copyShoppingCart();
        updateWizardCartLabels();

    }


    public void setWizardCartFlowPane(FlowPane wizardCartFlowPane) {
        this.wizardCartFlowPane = wizardCartFlowPane;
    }

    public static WizardCartController getInstance(){
        if(wizardCartController == null)
            wizardCartController = new WizardCartController();
        return wizardCartController;
    }

    public void updateWizardCartLabels() {
        wizardCartTotalPrice.setText(String.valueOf(ShoppingCartExt.getInstance().getTotal() + 50));
        wizardCartPrice.setText(String.valueOf(ShoppingCartExt.getInstance().getTotal()));
    }

    private boolean isInWizardCart(ShoppingItem shoppingItem) {
        for (ShoppingItem item : ShoppingCartExt.getInstance().getItems())
            if (shoppingItem.getProduct().getProductId() == (item.getProduct().getProductId()) && shoppingItem.getNumberOfItems() > 1)
                return true;
        return false;
    }

    private void addWizardCartItem(ShoppingItem item) {
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
        //currentWizardItems.get(item).updateLabels();
    }

    private void copyShoppingCart(){
        wizardCartFlowPane.getChildren().clear();

        for(ShoppingItem shoppingItem : shoppingItems) {
            wizardCartFlowPane.getChildren().add(currentWizardItem);
        }

    }

    private void removeWizardCartItem(ShoppingItem item) {
        if (item.getNumberOfItems() == 0) {
            wizardCartFlowPane.getChildren().remove(currentWizardItems.get(item));
            //currentWizardItem.updateLabels();
            currentWizardItems.remove(item);
            // Add empty cart message if no items are left in cart
            }
            //currentWizardItems.get(item).updateLabels();
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
    }

}


