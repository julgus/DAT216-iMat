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
    private ReceiptsController receiptsController;
    private MyProfileController profileController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        productViewController = ProductViewController.getInstance();
        productPane.getChildren().add(productViewController);
        Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));


        /*FOR TESTING OF MY PROFILE PAGE
        profileController = MyProfileController.getInstance();
        productPane.getChildren().add(profileController);
        Helper.fitToAnchorPane(productPane,productPane.getChildren().get(0));
        */


        cartController = CartController.getInstance();
        ShoppingCartExt.getInstance().addShoppingCartListener(cartController);
        cartPane.getChildren().add(cartController);
        Helper.fitToAnchorPane(cartPane, cartPane.getChildren().get(0));

        try {
            topMenuPane.getChildren().add(FXMLLoader.load(getClass().getResource("/views/topmenu.fxml")));
        } catch(IOException e){
            System.out.println("Unable to load top menu");
        }

    }

}
