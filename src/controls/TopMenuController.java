package controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.ProductPrimaryCategory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * Class representing the top menu, FAB and the shopping cart
 */

public class TopMenuController extends AnchorPane {

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

    private StoreStageController parentController;
    private static TopMenuController topMenuController;

    private TopMenuController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/topmenu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
            IOException exception) {
            throw new RuntimeException(exception);
        }

        searchButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Sök);
        });

        meatButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Kött);
        });

        fishButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Fisk);
        });

        dairyButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Mejeri);
        });

        sweetButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Sötsaker);
        });

        drinkButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Dryck);
        });

        vegetableButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Grönsaker);
        });

        fruitButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Frukt);
        });

        pantryButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Skafferi);
        });

        pantryButton.setOnAction(actionEvent ->  {
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Skafferi);
        });

        receiptButton.setOnAction(actionEvent ->  {
            parentController.viewReceipts();
        });

        profileButton.setOnAction(actionEvent ->  {
            parentController.viewProfile(); 
        });
    }

    public static TopMenuController getInstance() {
        if(topMenuController == null)
            topMenuController = new TopMenuController();
        return topMenuController;
    }

    public void setParentController(StoreStageController controller) {
        this.parentController = controller;
    }

}
