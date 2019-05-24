package helper;

import controls.CartController;
import controls.WizardCartController;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.ProductPrimaryCategory;
import model.Profile;
import model.SwapSceneEvent;
import model.SwapSceneListener;

public class Helper {

    private static SwapSceneListener listener;
    private static Profile currentUser;

    private Helper() {
        throw new UnsupportedOperationException();
    }

    public static void fitToAnchorPane(AnchorPane master, Node child) {
        master.setTopAnchor(child, 0.0);
        master.setBottomAnchor(child, 0.0);
        master.setLeftAnchor(child,0.0);
        master.setRightAnchor(child, 0.0);
    }

    public static void setSwapSceneListener(SwapSceneListener swapListener) {
        listener = swapListener;
    }

    public static void setCurrentUser(Profile user) {
        currentUser = user;
    }

    public static Profile getCurrentUser() {
        return currentUser;
    }

    public static void fireGoToCartEvent() {
        SwapSceneEvent evt = new SwapSceneEvent(CartController.getInstance());
        evt.setCheckoutEvent(true);

        listener.changeScenes(evt);
    }

    public static void fireGoToStoreEvent() {
        SwapSceneEvent evt = new SwapSceneEvent(WizardCartController.getInstance());
        evt.setCheckoutEvent(false);

        listener.changeScenes(evt);
    }

    public static void fireGoToStoreEvent(ProductPrimaryCategory targetPage) {
        SwapSceneEvent evt = new SwapSceneEvent(WizardCartController.getInstance());
        evt.setCheckoutEvent(false);
        evt.setTargetPage(targetPage);

        listener.changeScenes(evt);
    }

    public static String onlyShowLastCharacters(String data, int keep){
        if(data == null || data.isEmpty() || keep > data.length()){ return data; }

        var builder = new StringBuilder();
        for(var i = 0; i < data.length() - keep; i++){
            builder.append("*");
        }
        builder.append(data.substring(data.length() - keep));
        return builder.toString();
    }
}