package controls;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.ProductExt;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    private ProductViewController parentController;
    private ProductExt product;

    @FXML private Label productTitleLabel;
    @FXML private Label productPriceLabel;
    @FXML private Label productCompPriceLabel;
    @FXML private ImageView productImage;

    public ProductCard(ProductExt product, ProductViewController productViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        parentController = productViewController;
        this.product = product;

        productTitleLabel.setText(product.getName());
        productPriceLabel.setText(product.getPrice() + " " + product.getUnit());
    }

    @FXML
    protected void onClick(Event event){

    }

}
