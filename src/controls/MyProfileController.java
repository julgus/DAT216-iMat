package controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.StyleClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import model.Profile;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class MyProfileController extends AnchorPane {

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField phoneNo;
    @FXML private TextField address;
    @FXML private TextField zipCode;
    @FXML private TextField city;
    @FXML private RadioButton apartment;
    @FXML private RadioButton house;

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

    StoreStageController parentController;
    ToggleGroup paymentMethod = new ToggleGroup();
    ToggleGroup typeOfHouse = new ToggleGroup();
    Profile profile;

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

        initToggleGroups();
        profile = new Profile();

        /* Apply text filters on textfields */

        UnaryOperator<TextFormatter.Change> onlyDigitsFilter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        UnaryOperator<TextFormatter.Change> onlyLettersFilter = change -> {
            String text = change.getText();
            if (text.matches("[a-ö]*") || text.matches("[A-Ö]*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> phoneNoFormat = new TextFormatter<>(onlyDigitsFilter);
        phoneNo.setTextFormatter(phoneNoFormat);
        addRequiredTextFormat(phoneNo, 10);

        TextFormatter<String> cvcFormat = new TextFormatter<>(onlyDigitsFilter);
        cvcCode.setTextFormatter(cvcFormat);
        addRequiredTextFormat(cvcCode, 3);

        TextFormatter<String> cardMonthFormat = new TextFormatter<>(onlyDigitsFilter);
        cardMonth.setTextFormatter(cardMonthFormat);
        addRequiredTextFormat(cardMonth, 2);

        TextFormatter<String> cardYearFormat = new TextFormatter<>(onlyDigitsFilter);
        cardYear.setTextFormatter(cardYearFormat);
        addRequiredTextFormat(cardYear, 2);

        TextFormatter<String> cardNoFormat = new TextFormatter<>(onlyDigitsFilter);
        cardNumber.setTextFormatter(cardNoFormat);
        addRequiredTextFormat(cardNumber, 16);

        TextFormatter<String> zipCodeFormat = new TextFormatter<>(onlyDigitsFilter);
        zipCode.setTextFormatter(zipCodeFormat);
        addRequiredTextFormat(zipCode, 5);

        TextFormatter<String> firstNameFormat = new TextFormatter<>(onlyLettersFilter);
        firstName.setTextFormatter(firstNameFormat);

        TextFormatter<String> lastNameFormat = new TextFormatter<>(onlyLettersFilter);
        lastName.setTextFormatter(lastNameFormat);

        TextFormatter<String> addressFormat = new TextFormatter<>(onlyLettersFilter);
        address.setTextFormatter(addressFormat);

        TextFormatter<String> cityFormat = new TextFormatter<>(onlyLettersFilter);
        city.setTextFormatter(cityFormat);

        TextFormatter<String> personalNumberFormat = new TextFormatter<>(onlyDigitsFilter);
        personalNumber.setTextFormatter(personalNumberFormat);

        cardPayment.selectedProperty();
    }

    public static MyProfileController getInstance() {
        if(myProfileController == null)
            myProfileController = new MyProfileController();
        return myProfileController;
    }

    public void setParentController(StoreStageController controller) {
        parentController = controller;
    }

    private void initToggleGroups(){
        cardPayment.setToggleGroup(paymentMethod);
        invoice.setToggleGroup(paymentMethod);

    }

    @FXML
    private void invoiceSelected(){
        cardNumber.setDisable(true);
        cardMonth.setDisable(true);
        cardYear.setDisable(true);
        cvcCode.setDisable(true);
        personalNumber.setDisable(false);

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
        personalNumber.setDisable(true);
        cardNumber.setDisable(false);
        cardMonth.setDisable(false);
        cardYear.setDisable(false);
        cvcCode.setDisable(false);
        //make active elements black
        cardDate.setStyle("-fx-text-fill: black");
        cardCvc.setStyle("-fx-text-fill: black");
        cardNo.setStyle("-fx-text-fill: black");

        //Style inactive elements
        personNo.setStyle("-fx-text-fill: grey-primary");
        personalNumber.setStyle("-fx-text-fill: grey-primary;");

    }
    @FXML private void updateProfile(){

    }

    private void addRequiredTextFormat(TextField field, int limit) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (newValue.intValue() > limit) {
                        field.setText(field.getText().substring(0, limit));
                    } else if (newValue.intValue() == limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium");
                    }
                } else {
                    if (newValue.intValue() < limit) {
                        field.getStyleClass().clear();
                        field.getStyleClass().addAll("text-field", "text-input", "text-normal-medium", "incorrect-format");
                    }
                }
            }
        });
    }

}