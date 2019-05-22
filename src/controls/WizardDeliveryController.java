package controls;

import javafx.scene.layout.AnchorPane;

public class WizardDeliveryController extends AnchorPane {

    private static WizardDeliveryController instance;
    private WizardDeliveryController parentController;

    private WizardDeliveryController() {

    }

    public static WizardDeliveryController getInstance() {
        if (instance == null)
            instance = new WizardDeliveryController();
        return instance;
    }

    public void setParentController(WizardDeliveryController controller) {
        parentController = controller;
    }
}
