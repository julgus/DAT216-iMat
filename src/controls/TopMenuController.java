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

public class TopMenuController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchButton.setOnAction(actionEvent ->  {

        });

        meatButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Kött);
        });

        fishButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Fisk);
        });

        dairyButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Mejeri);
        });

        sweetButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Sötsaker);
        });

        drinkButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Dryck);
        });

        vegetableButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Grönsaker);
        });

        fruitButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Frukt);
        });

        pantryButton.setOnAction(actionEvent ->  {
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Skafferi);
        });

    }
}
