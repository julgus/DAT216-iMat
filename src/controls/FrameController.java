package controls;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.ProductPrimaryCategory;
import se.chalmers.cse.dat216.project.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Class representing the top menu, FAB and the shopping cart
 */

public class FrameController implements Initializable {

    @FXML private Button searchButton;
    @FXML private Button meatButton;
    @FXML private Button fishButton;
    @FXML private Button dairyButton;
    @FXML private Button sweetButton;
    @FXML private Button drinkButton;
    @FXML private Button vegetableButton;
    @FXML private Button fruitButton;
    @FXML private Button pantryButton;
    @FXML private Button receiptButton;
    @FXML private Button profileButton;

    ProductViewController viewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewController = new ProductViewController();

        searchButton.setOnAction(actionEvent ->  {


        });

        meatButton.setOnAction(actionEvent ->  {
            viewController.setMainCategory(ProductPrimaryCategory.Kött);
        });

        fishButton.setOnAction(actionEvent ->  {
            viewController.setMainCategory(ProductPrimaryCategory.Fisk);
        });

        dairyButton.setOnAction(actionEvent ->  {

        });

        sweetButton.setOnAction(actionEvent ->  {

        });

        drinkButton.setOnAction(actionEvent ->  {

        });

        vegetableButton.setOnAction(actionEvent ->  {

        });

        fruitButton.setOnAction(actionEvent ->  {

        });

        pantryButton.setOnAction(actionEvent ->  {

        });

    }
}
