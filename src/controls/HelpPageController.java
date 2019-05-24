package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HelpPageController extends AnchorPane{

    StoreStageController parentController;
    static HelpPageController helpPageController;


    private HelpPageController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/help.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static HelpPageController getInstance() {
        if(helpPageController == null){
            helpPageController = new HelpPageController();
        }
        return helpPageController;
    }

    public void refresh(){}


    public void setParentController(StoreStageController parentController){
        this.parentController = parentController;
    }

}