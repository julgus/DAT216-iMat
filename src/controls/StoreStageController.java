package controls;

import helper.Helper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* This class is responsible for holding all the nodes of the Store Stage  */

public class StoreStageController implements Initializable {

    @FXML private AnchorPane topMenuPane;
    @FXML private AnchorPane cartPane;
    @FXML private AnchorPane productPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            topMenuPane.getChildren().add(FXMLLoader.load(getClass().getResource("/views/topmenu.fxml")));
        } catch(IOException e){
            System.out.println("Unable to load top menu");
        }

        try {
            productPane.getChildren().add(FXMLLoader.load(getClass().getResource("/views/product_view.fxml")));
            Helper.fitToAnchorPane(productPane, productPane.getChildren().get(0));
        } catch(IOException e){
            System.out.println("Unable to load product view: " + e.getMessage());
        }

        try {
            cartPane.getChildren().add(FXMLLoader.load(getClass().getResource("/views/shopping_cart.fxml")));
            Helper.fitToAnchorPane(cartPane, cartPane.getChildren().get(0));
        } catch(IOException e){
            System.out.println("Unable to load shopping cart");
        }

    }
}
