package model;


public class Profile {

    private static Profile profile;

    private String firstName = "";
    private String lastName = "";
    private String mobilePhoneNumber = "";
    private String email = "";
    private String address = "";
    private String postCode = "";
    private String city = "";
    private int level = -1;
    private boolean isHouse;

    private String cardType = "";
    private String holdersName = "";
    private int validMonth;
    private int validYear;
    private String cardNumber = "";
    private int cvcCode;
    private boolean cardPayment = true;
    private String personalNumber = "";
    private static String inputPromptCardNo = "ex: 1234 5678 1013 5564";
    private static String inputPromptPersonalNo = "ex: 194502132445";
    private static String inputPromptPhoneNo = "ex: 0702213245";
    private static String inputPromptName = "Förnamn";
    private static String inputPromptLastname = "Efternamn";
    private static String inputPromptAddress = "ex: Storgatan 10";
    private static String inputPromptCity = "ex: Göteborg";
    private static String inputPromptZipCode = "41450";
    private static String inputPromptEmail = "exempel@mail.se";
    private static String inputPromptValidYear = "ÅÅ";
    private static String inputPromptValidMonth = "MM";
    private static String inputPromptLevel = "ex: 1";

    private Profile(){

    }

    public static Profile getInstance(){
        if(profile == null){
            profile = new Profile();
        }
        return profile;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getHoldersName() {
        return this.holdersName;
    }

    public void setHoldersName(String holdersName) {
        this.holdersName = holdersName;
    }

    public int getValidMonth() {
        return this.validMonth;
    }

    public void setValidMonth(int validMonth) {
        this.validMonth = validMonth;
    }

    public int getValidYear() {
        return this.validYear;
    }

    public void setValidYear(int validYear) {
        this.validYear = validYear;
    }

    public int getCvcCode() {
        return this.cvcCode;
    }

    public void setCvcCode(int verificationCode) {
        this.cvcCode = verificationCode;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public boolean isCardPayment(){
        return cardPayment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCardPayment(boolean cardPayment) {
        this.cardPayment = cardPayment;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public boolean isHouse() {
        return isHouse;
    }

    public void setHouse(boolean house) {
        isHouse = house;
    }

    public static String getInputPromptCardNo() {
        return inputPromptCardNo;
    }

    public static String getInputPromptPersonalNo() {
        return inputPromptPersonalNo;
    }

    public static String getInputPromptPhoneNo() {
        return inputPromptPhoneNo;
    }

    public static String getInputPromptName() {
        return inputPromptName;
    }

    public static String getInputPromptLastname() {
        return inputPromptLastname;
    }

    public static String getInputPromptAddress() {
        return inputPromptAddress;
    }

    public static String getInputPromptCity() {
        return inputPromptCity;
    }

    public static String getInputPromptZipCode() {
        return inputPromptZipCode;
    }

    public static String getInputPromptEmail() {
        return inputPromptEmail;
    }

    public static String getInputPromptValidYear() {
        return inputPromptValidYear;
    }

    public static String getInputPromptValidMonth() {
        return inputPromptValidMonth;
    }

    public static String getInputPromptLevel() {
        return inputPromptLevel;
    }
}
