package controls;

import backend.Backend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ReceiptsController extends AnchorPane {

    @FXML private Accordion receiptsAccordion;

    private static ReceiptsController instance;

    private ReceiptsController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/receipts_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public static ReceiptsController getInstance() {
        if(instance == null) {
            instance = new ReceiptsController();
        }
        return instance;
    }

    private void addReceiptPanes() {

    }
}
