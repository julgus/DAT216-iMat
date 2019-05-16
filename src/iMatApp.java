import backend.Backend;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class iMatApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("views/product_view.fxml"));

        Scene scene = new Scene(root, 1980, 1080);

        stage.setTitle("iMat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
        Backend.getInstance();
    }

}
