package helper;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Helper {

    private Helper() {
        throw new UnsupportedOperationException();
    }

    public static void fitToAnchorPane(AnchorPane master, Node child) {
        master.setTopAnchor(child, 0.0);
        master.setBottomAnchor(child, 0.0);
        master.setLeftAnchor(child,0.0);
        master.setRightAnchor(child, 0.0);
    }

}