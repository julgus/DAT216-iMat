package model;

public class ReceiptItem {

    private ProductExt product;
    private double amount = 1;
    private int numberOfItems;

    public ReceiptItem(ProductExt product) {
        this.product = product;
        this.numberOfItems = 0;
    }

    public ReceiptItem(ProductExt product, int numberOfItems) {
        this.product = product;
        this.numberOfItems = numberOfItems;
    }

    public ReceiptItem(ProductExt product, double amount, int numberOfItems) {
        this.product = product;
        this.amount = amount;
        this.numberOfItems = numberOfItems;
    }

    public double getAmount() {
        return this.amount;
    }

    public int getNumberOfItems(){
        return numberOfItems;
    }
    public ProductExt getProduct() {
        return this.product;
    }

    public double getTotal() {
        return amount * product.getPrice();
    }

}
