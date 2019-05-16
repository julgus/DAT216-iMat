import backend.Backend;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class iMatApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("views/topmenu.fxml"));

        Scene scene = new Scene(root, 1265, 745);

        stage.setTitle("iMat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
        Backend.getInstance();
    }

}
