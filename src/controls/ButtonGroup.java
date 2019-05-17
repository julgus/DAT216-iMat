package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ButtonGroup {

    @FXML
    private Button cart_add;
    @FXML
    private Button cart_remove;
    @FXML
    private Label cart_no_of_product;

    private Product product;

    public ButtonGroup(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("button_group.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
    }
}
