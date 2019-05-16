package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/* This class is responsible for holding all the nodes of the Store Stage  */

public class StoreStageController implements Initializable {

    @FXML private AnchorPane topMenuPane;
    @FXML private HBox horizontalPane;
    @FXML private AnchorPane productPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            topMenuPane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/views/topmenu.fxml")));
        } catch(IOException e){
            System.out.println("Unable to load top menu");
        }

        try {
            productPane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/views/product_view.fxml")));
            productPane.setPrefWidth(900);
        } catch(IOException e){
            System.out.println("Unable to load product view");
        }

        try {
            horizontalPane.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/views/shopping_cart.fxml")));
            horizontalPane.setPrefWidth(365);
        } catch(IOException e){
            System.out.println("Unable to load shopping cart");
        }

    }
}
