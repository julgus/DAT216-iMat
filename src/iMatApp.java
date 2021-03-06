import backend.Backend;
import backend.FilesBackend;
import controls.*;
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
    private Scene storeScene;
    private Scene checkOutScene;
    private final int appWidth = 1265, appHeight = 750;

    @Override
    public void start(Stage stage) throws Exception {


        Parent store = FXMLLoader.load(getClass().getResource("views/store_stage.fxml"));
        Parent checkOut = FXMLLoader.load(getClass().getResource("views/wizard_stage.fxml"));
        mainStage = stage;
        mainStage.setTitle("iMat");
        storeScene = new Scene(store, appWidth, appHeight);
        checkOutScene = new Scene(checkOut, appWidth, appHeight);
        mainStage.setScene(storeScene);
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
            if (evt.getTargetPage() == ProductPrimaryCategory.Kvitton) {
                TopMenuController.getInstance().viewReceiptPage();
            }
            else if (evt.getTargetPage() == ProductPrimaryCategory.Sök) {
                TopMenuController.getInstance().viewSearchPage();
            }
        }
    }

}
