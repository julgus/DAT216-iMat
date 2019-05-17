package model;

import java.io.Serializable;

public class ShoppingItem implements Serializable {

    private ProductExt product;
    private Double amount;


    public ShoppingItem(ProductExt product) {
        this.product = product;
        this.amount = 1.0D;
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
