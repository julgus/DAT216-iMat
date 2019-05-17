package controls;

import backend.Backend;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import model.ProductExt;
import model.ProductPrimaryCategory;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ProductViewController implements Initializable {

    @FXML private Label categoryLabel;
    @FXML private ToolBar subMenu;
    @FXML private FlowPane productFlowPane;

    private Backend backend;
    private Map<String, ProductCard> productCardMap = new HashMap<String, ProductCard>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backend = Backend.getInstance();

        backend.getAllProducts().stream()
            .forEach(product -> productCardMap.put(product.getName(),
                new ProductCard(product, this)));

        updateProductList();
    }

    public void setMainCategory(ProductPrimaryCategory category) {
        categoryLabel.setText(category.name());
        updateSubMenuItems(category);
    }

    private void updateSubMenuItems(ProductPrimaryCategory category) {
/*
        switch(category) {
            case Kött:
                break;
            case Fisk:
                break;
            case Sötsaker:
                break;
            case Skafferi:
                break;
            case Dryck:
                break;
            case Grönsaker:
                break;
            case Frukt:
                break;
            case Mejeri:
                break;
            default: break;
        }*/

    }

    private void updateProductList() {
        productFlowPane.getChildren().clear();

        backend.getAllProducts().stream()
            .forEach(
                product -> productFlowPane.getChildren().add(
                    productCardMap.get(product.getName())
                )
            );
    }

}
