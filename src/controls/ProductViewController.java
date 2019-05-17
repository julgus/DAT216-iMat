package controls;

import backend.Backend;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import model.ProductPrimaryCategory;

import java.awt.*;
import java.net.URL;
import java.util.*;

public class ProductViewController implements Initializable {

    @FXML private Label categoryLabel;
    @FXML private ToolBar subMenu;
    @FXML private FlowPane productFlowPane;

    private Map<Integer, ProductCard> productCardMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateProductList();

        productFlowPane.setHgap(15);
        productFlowPane.setVgap(15);
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

        Backend.getInstance().getAllProducts().stream()
            .map(x -> getProductCard(x.getProductId()))
            .forEach(x -> productFlowPane.getChildren().add(x));
    }

    private ProductCard getProductCard(int id){
        if(productCardMap.containsKey(id)){
            return productCardMap.get(id);
        }

        var productCard = new ProductCard(Backend.getInstance().getProductById(id), this);
        productCardMap.put(id, productCard);
        return productCard;
    }

}
