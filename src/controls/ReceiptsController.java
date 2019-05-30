package controls;

import backend.Backend;
import backend.FilesBackend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.layout.AnchorPane;
import model.Receipt;

import java.io.IOException;
import java.util.Comparator;
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
        updateReceiptPanes();

    }

    public static ReceiptsController getInstance() {
        if(instance == null) {
            instance = new ReceiptsController();
        }
        return instance;
    }

    private void updateReceiptPanes() {
        receiptsAccordion.getPanes().clear();

        receipts.stream()
            .sorted(Comparator.comparing(receipt -> receipt.getPurchaseDate(), Comparator.reverseOrder()))
            .map(x -> getReceiptPane(x))
            .forEach(x -> receiptsAccordion.getPanes().add(x));
    }

    public void refresh() {
        receipts = FilesBackend.getInstance().readFromReceiptFile();
        updateReceiptPanes();
        if (receiptsAccordion.getExpandedPane() !=  null) {
            receiptsAccordion.getExpandedPane().setExpanded(false);
        }
    }

    private ReceiptPane getReceiptPane(Receipt receipt) {
        return new ReceiptPane(receipt, this);
    }
}
