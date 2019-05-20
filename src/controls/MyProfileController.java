package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import se.chalmers.cse.dat216.project.CreditCard;

import java.io.IOException;

public class MyProfileController extends AnchorPane {

    @FXML private TextField cardNumber;
    @FXML private TextField cardYear;
    @FXML private TextField cardMonth;
    @FXML private TextField cvcCode;
    @FXML private TextField personalNumber;
    @FXML private RadioButton invoice;
    @FXML private RadioButton cardPayment;
    @FXML private Label cardNo;
    @FXML private Label cardDate;
    @FXML private Label cardCvc;
    @FXML private Label personNo;
    @FXML private Line slashLine;


    ToggleGroup paymentMethod = new ToggleGroup();
    CreditCard creditCard;

    private static MyProfileController myProfileController;

    private MyProfileController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/profile_view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        initToggleGroup();


    }

    public static MyProfileController getInstance(){
        if(myProfileController == null)
            myProfileController = new MyProfileController();
        return myProfileController;
    }
    private void initToggleGroup(){
        cardPayment.setToggleGroup(paymentMethod);
        invoice.setToggleGroup(paymentMethod);
    }


    @FXML
    private void invoiceSelected(){
        cardNumber.setEditable(false);
        cardMonth.setEditable(false);
        cardYear.setEditable(false);
        cvcCode.setEditable(false);
        personalNumber.setEditable(true);

        //make labels grey
        cardDate.setStyle("-fx-text-fill: grey-primary");
        cardCvc.setStyle("-fx-text-fill: grey-primary");
        cardNo.setStyle("-fx-text-fill: grey-primary");
        //make textfields grey

        slashLine.setStyle("-fx-stroke: grey-primary");

        //Style active elements
        personNo.setStyle("-fx-text-fill: black");
        personalNumber.setStyle("-fx-text-fill: black;");




    }
    @FXML
    private void cardSelected(){
        /*FIX STYLE FOR MORE STATIC PLIANCY
        cardNumber.setStyle("-fx-highlight-fill: black; -fx-text-fill: black;");
        cardMonth.setStyle("-fx-skin: default; -fx-text-fill: black;");
        cardYear.setStyle("-fx-skin: default; -fx-text-fill: black;");
        cvcCode.setStyle("-fx-skin: default; -fx-text-fill: black;");

         */
        personalNumber.setEditable(false);
        cardNumber.setEditable(true);
        cardMonth.setEditable(true);
        cardYear.setEditable(true);
        cvcCode.setEditable(true);
        //make active elements black
        cardDate.setStyle("-fx-text-fill: black");
        cardCvc.setStyle("-fx-text-fill: black");
        cardNo.setStyle("-fx-text-fill: black");

    }
    @FXML private void updateCreditCard(){

        int validCard;
        try {
            if (cardNumber.getText().toCharArray().length == 16) {
                validCard = Integer.parseInt(cardNumber.getText());
            }
        }catch (Exception e){
            cardNumber.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }


    }


}
