package controls;

import helper.Helper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.ProductPrimaryCategory;
import model.ShoppingCartExt;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.AnchorPane.setTopAnchor;

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
    private HelpPageController helpPageController;


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
        helpPageController = HelpPageController.getInstance();
        helpPageController.setParentController(this);
    }

    public void viewProfile() {
        if (!profileController.equals(productPane.getChildren().get(0))) {
            productPane.getChildren().clear();
            productPane.getChildren().add(profileController);
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));
            profileController.refresh();
        }
    }
    public void viewHelpPage() {
        if (!helpPageController.equals(productPane.getChildren().get(0))) {
            productPane.getChildren().clear();
            productPane.getChildren().add(helpPageController);
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));
            helpPageController.refresh();
        }
    }

    public void viewReceipts() {
        if (!receiptsController.equals(productPane.getChildren().get(0))) {
            productPane.getChildren().clear();
            productPane.getChildren().add(receiptsController);
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));
            receiptsController.refresh();
        }
    }

    public void viewProducts() {
        if (!productViewController.equals(productPane.getChildren().get(0))) {
            productPane.getChildren().clear();
            productPane.getChildren().add(productViewController);
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));
        }
    }

    public void viewRecent(){
        if (!productViewController.equals(productPane.getChildren().get(0))) {
            productPane.getChildren().clear();
            productPane.getChildren().add(productViewController);
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));

        }
    }

    public void viewSearchPage() {
        if (!productViewController.equals(productPane.getChildren().get(0))) {
            productPane.getChildren().clear();
            productPane.getChildren().add(productViewController);
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));
            productViewController.setMainCategory(ProductPrimaryCategory.Sök);
        }
    }
}
