package controls;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;

import java.io.IOException;

public class WarningController extends DialogPane{

    public void DialogPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/warning_message.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}
