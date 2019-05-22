package controls;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.ProductPrimaryCategory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Arrays;

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
            System.out.println(searchButton.getStyleClass());
            setActiveStyling(searchButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Sök);
        });

        meatButton.setOnAction(actionEvent ->  {
            setActiveStyling(meatButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Kött);
        });

        fishButton.setOnAction(actionEvent ->  {
            setActiveStyling(fishButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Fisk);
        });

        dairyButton.setOnAction(actionEvent ->  {
            setActiveStyling(dairyButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Mejeri);
        });

        sweetButton.setOnAction(actionEvent ->  {
            setActiveStyling(sweetButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Sötsaker);
        });

        drinkButton.setOnAction(actionEvent ->  {
            setActiveStyling(drinkButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Dryck);
        });

        vegetableButton.setOnAction(actionEvent ->  {
            setActiveStyling(vegetableButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Grönsaker);
        });

        fruitButton.setOnAction(actionEvent ->  {
            setActiveStyling(fruitButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Frukt);
        });

        pantryButton.setOnAction(actionEvent ->  {
            setActiveStyling(pantryButton);
            parentController.viewProducts();
            ProductViewController.getInstance().setMainCategory(ProductPrimaryCategory.Skafferi);
        });

        receiptButton.setOnAction(actionEvent ->  {
            setActiveStyling(receiptButton);
            parentController.viewReceipts();
        });

        profileButton.setOnAction(actionEvent ->  {
            setActiveStyling(profileButton);
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

    private void setActiveStyling(Button activeButton) {
        resetButtonStyling();
        activeButton.getStyleClass().clear();
        activeButton.getStyleClass().addAll("menu-item-active", "button", "menu-item-label");
    }

    private void setStandardButtonStyle(Button ... buttons) {
        Arrays.stream(buttons).
            forEach(x -> x.getStyleClass().clear());

        Arrays.stream(buttons).
            forEach(x -> x.getStyleClass().addAll("button", "menu-item", "menu-item-label"));
    }

    private void resetButtonStyling() {
        setStandardButtonStyle(searchButton, meatButton, fishButton, dairyButton,
            sweetButton, drinkButton, vegetableButton, fruitButton,
            pantryButton, receiptButton, profileButton);
    }

}
