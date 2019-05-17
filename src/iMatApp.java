import backend.Backend;
import controls.CartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ShoppingCartExt;

public class iMatApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/store_stage.fxml"));
        stage.setTitle("iMat");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.setMaximized(true);
        stage.setFullScreen(false);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
