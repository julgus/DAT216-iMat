package model;


import java.util.Objects;

public class ShoppingItem {

    private ProductExt product;
    private Double amount;
    private int numberOfItems;


    public ShoppingItem(ProductExt product) {
        this.product = product;
        this.amount = 1.0D;
        this.numberOfItems = 0;
    }

    public ShoppingItem(ProductExt product, double amount) {
        this.product = product;
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void increaseNumberOfItems(){
        numberOfItems++;
    }
    public void decreaseNumberOfItems(){
        numberOfItems--;
    }
    public void setNumberOfItems(int num){numberOfItems = num;}
    public int getNumberOfItems(){
        return numberOfItems;
    }
    public ProductExt getProduct() {
        return this.product;
    }

    public void setProduct(ProductExt product) {
        this.product = product;
    }

    public double getTotal() {
        return this.amount * this.product.getPrice();
    }
}
