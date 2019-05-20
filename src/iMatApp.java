import backend.Backend;
import controls.CartController;
import controls.StoreStageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ProductExt;
import model.ShoppingCartExt;
import model.ShoppingItem;

public class iMatApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("views/store_stage.fxml"));
        stage.setTitle("iMat");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.setMaximized(true);
        stage.setFullScreen(false);
        ShoppingCartExt.getInstance().addItem(Backend.getInstance().getShoppingItem(1));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
