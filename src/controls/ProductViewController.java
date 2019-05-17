package controls;

import backend.Backend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import model.ProductPrimaryCategory;
import model.ProductSecondaryCategory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductViewController extends AnchorPane {

    @FXML private Label categoryLabel;
    @FXML private ToolBar subMenu;
    @FXML private FlowPane productFlowPane;
    @FXML private Button viewAllButton;
    @FXML private HBox searchBar;
    @FXML private Button searchButton;
    @FXML private TextField searchField;
    @FXML private ScrollPane scrollPane;

    private static ProductViewController instance;

    private Map<Integer, ProductCard> productCardMap = new HashMap<>();
    private ProductPrimaryCategory primaryCategory;

    public static ProductViewController getInstance() {
        if(instance == null) { instance = new ProductViewController(); }
        return instance;
    }

    private ProductViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/product_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        productFlowPane.setHgap(15);
        productFlowPane.setVgap(15);

        setMainCategory(ProductPrimaryCategory.Sök);

        searchField.setVisible(true);

        viewAllButton.setOnAction(actionEvent ->  {
            updateProductList(primaryCategory);
        });

        searchButton.setOnAction(actionEvent ->  {
            if (searchField.getText().equals("")) {
                updateProductList();
            } else {
                updateProductList(searchField.getText());
            }
        });

    }

    public void setMainCategory(ProductPrimaryCategory category) {
        primaryCategory = category;
        categoryLabel.setText(category.name());
        scrollPane.setVvalue(0);
        if (ProductPrimaryCategory.Sök.equals(category)) {
            subMenu.setVisible(false);
            updateProductList();
            searchBar.setPrefWidth(450);
            searchBar.setMaxWidth(450);
            searchBar.setVisible(true);
            subMenu.setVisible(false);
            subMenu.setMaxWidth(0);
        } else {
            subMenu.setVisible(true);
            updateSubMenuItems(category);
            updateProductList(category);
            searchBar.setVisible(false);
            searchBar.setMaxWidth(0);
            subMenu.setMaxWidth(880);
            subMenu.setVisible(true);
        }
    }

    private void updateSubMenuItems(ProductPrimaryCategory category) {
        subMenu.getItems().clear();
        subMenu.getItems().addAll(viewAllButton);

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

    private void updateProductList(String searchString) {
        productFlowPane.getChildren().clear();

        Backend.getInstance().searchProductsByName(searchString).stream()
            .map(x -> getProductCard(x.getProductId()))
            .forEach(x -> productFlowPane.getChildren().add(x));
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

    public Image getProductImage(String imageName) {
        String imagePath = "images/" + imageName;
        return new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
    }

}
