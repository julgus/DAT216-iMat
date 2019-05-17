import backend.Backend;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class iMatApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/store_stage.fxml"));

        //Scene scene = new Scene(root, 1265, 745);
        stage.setTitle("iMat");
        stage.setScene(new Scene(root, 1920, 1080));
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
        Backend backend = Backend.getInstance();
    }
}
