package controls;

import javafx.scene.layout.AnchorPane;

public class WizardReceiptController extends AnchorPane {

    private static WizardReceiptController instance;
    private WizardStageController parentController;

    private WizardReceiptController() {

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
