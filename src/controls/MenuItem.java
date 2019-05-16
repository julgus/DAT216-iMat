package controls;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Class representing the menu items in the top menu
 */

public class MenuItem extends AnchorPane {

    @FXML private Label menuLabel;
    @FXML private ImageView menuIcon;

    //TODO: fix product categories, connect with category icons
    public MenuItem(ProductCategory category) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        // this.menuIcon.setImage(category.getIcon());
        //this.menuLabel.setText(category.getText());
    }
}

