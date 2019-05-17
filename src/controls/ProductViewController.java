package controls;

import backend.Backend;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import model.ProductPrimaryCategory;
import model.ProductSecondaryCategory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {

    @FXML private Label categoryLabel;
    @FXML private ToolBar subMenu;
    @FXML private FlowPane productFlowPane;

    private Map<Integer, ProductCard> productCardMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productFlowPane.setHgap(15);
        productFlowPane.setVgap(15);

        setMainCategory(ProductPrimaryCategory.Mejeri);
    }

    public void setMainCategory(ProductPrimaryCategory category) {
        categoryLabel.setText(category.name());
        updateSubMenuItems(category);
        updateProductList(category);
    }

    private void updateSubMenuItems(ProductPrimaryCategory category) {
        List<ProductSecondaryCategory> subcategories = Backend.getInstance().getSecondaryCategories(category);
        for(ProductSecondaryCategory subcategory : subcategories) {
            Button button = new Button();
            button.setText(Backend.getInstance().getSecondaryCategoryName(subcategory));

            button.setOnAction(actionEvent ->  {
                updateProductList(subcategory);
            });

            subMenu.getItems().addAll(button);
        }

    }

    private void updateProductList() {
        productFlowPane.getChildren().clear();

        Backend.getInstance().getAllProducts().stream()
            .map(x -> getProductCard(x.getProductId()))
            .forEach(x -> productFlowPane.getChildren().add(x));
    }

    private void updateProductList(ProductSecondaryCategory category) {
        productFlowPane.getChildren().clear();

        Backend.getInstance().getProductsWithSecondaryCategory(category).stream()
            .map(x -> getProductCard(x.getProductId()))
            .forEach(x -> productFlowPane.getChildren().add(x));
    }

    private void updateProductList(ProductPrimaryCategory category) {
        productFlowPane.getChildren().clear();

        Backend.getInstance().getProductWithPrimaryCategory(category).stream()
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
