package controls;

import backend.Backend;
import backend.FilesBackend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;
import model.Receipt;

import java.io.IOException;
import java.util.List;

public class ReceiptsController extends AnchorPane {

    @FXML private Accordion receiptsAccordion;

    private static ReceiptsController instance;

    List<Receipt> receipts;

    private ReceiptsController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/receipts_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        receipts = FilesBackend.getInstance().readFromReceiptFile();
        addReceiptPanes();

    }

    public static ReceiptsController getInstance() {
        if(instance == null) {
            instance = new ReceiptsController();
        }
        return instance;
    }

    private void addReceiptPanes() {
        for (Receipt receipt : receipts) {
            ReceiptPane receiptPane = new ReceiptPane(receipt, this);
            receiptsAccordion.getPanes().add(receiptPane);
        }
    }
}
