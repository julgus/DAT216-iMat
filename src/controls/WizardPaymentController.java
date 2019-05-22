package controls;

import javafx.scene.layout.AnchorPane;

public class WizardPaymentController extends AnchorPane {

    private static WizardPaymentController instance;
    private WizardStageController parentController;

    private WizardPaymentController() {

    }

    public static WizardPaymentController getInstance() {
        if(instance == null)
            instance = new WizardPaymentController();
        return instance;
    }

    public void setParentController(WizardStageController controller) {
        parentController = controller;
    }

}
