import backend.Backend;
import backend.FilesBackend;
import controls.CartController;
import controls.TopMenuController;
import controls.WizardCartController;
import controls.WizardStageController;
import helper.Helper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class iMatApp extends Application implements SwapSceneListener {

    private Stage mainStage;
    Scene storeScene;
    Scene checkOutScene;

    @Override
    public void start(Stage stage) throws Exception {
        Parent store = FXMLLoader.load(getClass().getResource("views/store_stage.fxml"));
        Parent checkOut = FXMLLoader.load(getClass().getResource("views/wizard_stage.fxml"));
        mainStage = stage;
        mainStage.setTitle("iMat");
        storeScene = new Scene(store, 1265, 750);
        checkOutScene = new Scene(checkOut, 1265, 750);
        mainStage.setScene(storeScene);
        //mainStage.setMaximized(true);
        //mainStage.setFullScreen(false);
        mainStage.show();

        Helper.setSwapSceneListener(this);
        Helper.setCurrentUser(FilesBackend.getInstance().readProfileFromFile());

        //add previous shoppingcart
        for(Tuple item : FilesBackend.getInstance().getShoppingItemsForCart()){
            ShoppingCartExt.getInstance().addItemsCartInit(item.getItem(), item.getNumberOfItem());
        }
    }

    @Override
    public void stop() {
        // TODO save stuff?
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void changeScenes(SwapSceneEvent evt) {
        if (evt.isCheckoutEvent()) {
            mainStage.setScene(checkOutScene);
            WizardCartController.getInstance().refresh();
        } else {
            mainStage.setScene(storeScene);
        }
    }

}
