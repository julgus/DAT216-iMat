package controls;

import helper.Helper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import model.ReceiptItem;
import model.ShoppingCartExt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* This class is responsible for holding all the nodes of the Store Stage  */

public class StoreStageController implements Initializable {

    @FXML private AnchorPane topMenuPane;
    @FXML private AnchorPane cartPane;
    @FXML private AnchorPane productPane;

    private ProductViewController productViewController;
    private CartController cartController;
    private TopMenuController topMenuController;
    private ReceiptsController receiptsController;
    private MyProfileController profileController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productViewController = ProductViewController.getInstance();
        productViewController.setParentController(this);
        productPane.getChildren().add(productViewController);
        Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));

        cartController = CartController.getInstance();
        cartController.setParentController(this);
        ShoppingCartExt.getInstance().addShoppingCartListener(cartController);
        cartPane.getChildren().add(cartController);
        Helper.fitToAnchorPane(cartPane, cartPane.getChildren().get(0));

        topMenuController = TopMenuController.getInstance();
        topMenuController.setParentController(this);
        topMenuPane.getChildren().add(topMenuController);
        Helper.fitToAnchorPane(topMenuPane, topMenuPane.getChildren().get(0));

        profileController = MyProfileController.getInstance();
        receiptsController = ReceiptsController.getInstance();
    }

    public void viewProfile() {
        if (!productViewController.equals(productPane.getChildren().get(0))) {
            productPane.getChildren().clear();
            productPane.getChildren().add(profileController);
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));
        }
    }

    public void viewProducts(){
        System.out.println("todo viewProducts");
    }

    public void viewReceipts(){
        System.out.println("todo viewReceipts");
    }
}
