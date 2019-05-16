package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* This class is responsible for holding all the nodes of the Store Stage  */

public class StoreStageController implements Initializable {

    @FXML private VBox verticalPane;
    @FXML private HBox horizontalPane;
    @FXML private AnchorPane productPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            verticalPane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/views/top_menu.fxml")));
        } catch(IOException e){
            System.out.println("Unable to load top menu");
        }

        try {
            productPane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/views/product_view.fxml")));
        } catch(IOException e){
            System.out.println("Unable to load product view");
        }

        try {
            horizontalPane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/views/shopping_cart.fxml")));
        } catch(IOException e){
            System.out.println("Unable to load shopping cart");
        }

    }
}
