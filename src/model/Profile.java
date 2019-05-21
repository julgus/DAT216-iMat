package model;


public class Profile {

    private String firstName = "";
    private String lastName = "";
    private String mobilePhoneNumber = "";
    private String email = "";
    private String address = "";
    private String postCode = "";
    private String city = "";

    private String cardType = "";
    private String holdersName = "";
    private int validMonth = 2;
    private int validYear = 2011;
    private String cardNumber = "";
    private int cvcCode = 0;


    public Profile(){
        this.firstName = "";
        this.lastName = "";
        this.mobilePhoneNumber = "";
        this.email = "";
        this.address = "";
        this.postCode = "";
        this.city = "";

        this.cardType = "";
        this.holdersName = "";
        this.validMonth = 2;
        this.validYear = 2011;
        this.cardNumber = "";
        this.cvcCode = 0;
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
        return this.firstName;
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
        this.city = this.city;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

}