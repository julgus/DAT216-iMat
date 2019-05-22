package controls;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WizardReceiptController extends AnchorPane {

    private static WizardReceiptController instance;
    private WizardStageController parentController;

    private WizardReceiptController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/wizard_receipt.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (
            IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static WizardReceiptController getInstance() {
        if(instance == null)
            instance = new WizardReceiptController();
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

}
