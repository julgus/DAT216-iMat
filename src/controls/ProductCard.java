package controls;

import backend.Backend;
import backend.CartEvent;
import backend.ShoppingCartListener;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.ProductExt;
import model.ShoppingCartExt;
import model.ShoppingItem;

import java.io.IOException;

public class ProductCard extends AnchorPane implements ShoppingCartListener {
    private ProductViewController parentController;
    private ProductExt product;
    private ShoppingItem shoppingItem;

    @FXML private Label productTitleLabel;
    @FXML private Label productPriceLabel;
    @FXML private Label productCompPriceLabel;
    @FXML private ImageView productImage;
    @FXML private Button cartRemove;
    @FXML private Button cartAdd;
    @FXML private Label numberOfItems;
    @FXML private StackPane stackPane;
    @FXML private AnchorPane singleButtonPane;
    @FXML private AnchorPane buttonGroupPane;

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
        this.shoppingItem = Backend.getInstance().getShoppingItem(product.getProductId());

        String productName = product.getUnit().equals("kr/kg") ? product.getName() + " 1 kg" : product.getName();
        productTitleLabel.setText(productName);
        String unit = shoppingItem.getProduct().getUnit().equals("kr/kg") ? " kr/kg" : " kr/st";
        productPriceLabel.setText(String.format("%1$,.2f", product.getPrice()) + unit);
        productImage.setImage(parentController.getProductImage(product.getImageName()));
        ShoppingCartExt.getInstance().addShoppingCartListener(this);
    }

    private void updateLabel(){
        String unit = shoppingItem.getProduct().getUnit().equals("kr/kg") ? " kg" : " st";
        numberOfItems.setText((shoppingItem.getNumberOfItems()) + unit);
    }

    @FXML
    protected void onClick(Event event){

    }

    @FXML
    public void addToCart(){
        if (shoppingItem.getNumberOfItems() == 0) {
            buttonGroupPane.toFront();
        }
        ShoppingCartExt.getInstance().addItem(this.shoppingItem);
    }

    @FXML
    public void removeFromCart() {
        ShoppingCartExt.getInstance().removeItem(this.shoppingItem);
        if (shoppingItem.getNumberOfItems() == 0) {
            singleButtonPane.toFront();
        }
    }

    @Override
    public void shoppingCartChanged(CartEvent event) {
        if(event.getShoppingItem() == null) {

        }
        else if (event.getShoppingItem().getProduct().getProductId() == (shoppingItem.getProduct().getProductId())) {
            updateLabel();
            if (shoppingItem.getNumberOfItems() == 0) {
                singleButtonPane.toFront();
            } else {
                buttonGroupPane.toFront();
            }
        }
    }
}
