import backend.Backend;
import backend.FilesBackend;
import controls.CartController;
import controls.ProductViewController;
import controls.StoreStageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ShoppingCartExt;
import model.ShoppingItem;
import model.Tuple;

public class iMatApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/store_stage.fxml"));
        stage.setTitle("iMat");
        stage.setScene(new Scene(root, 1270, 750));
        stage.setMaximized(true);
//        stage.setFullScreen(false);

        /*
        FilesBackend.getInstance().getLoadedShoppingItems().forEach(x -> {
            CartController.getInstance().addCartItem(x);
            ShoppingCartExt.getInstance().addItem(x);
        });

         */

        for(Tuple item : FilesBackend.getInstance().getShoppingItemsForCart()){
            ShoppingCartExt.getInstance().addItemsCartInit(item.getItem(), item.getNumberOfItem());

        }
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
