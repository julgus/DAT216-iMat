package controls;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import model.ProductPrimaryCategory;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {

    @FXML private Label categoryLabel;
    @FXML private ToolBar subMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setMainCategory(ProductPrimaryCategory category) {
        categoryLabel.setText(category.name());
        updateSubMenuItems(category);
    }

    private void updateSubMenuItems(ProductPrimaryCategory category) {

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
        }

    }
}
