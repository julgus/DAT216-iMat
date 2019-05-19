package model;

public class ReceiptItem {

    private ProductExt product;
    private Double amount;
    private int numberOfItems;

    public ReceiptItem(ProductExt product) {
        this.product = product;
        this.amount = 1.00D;
        this.numberOfItems = 0;
    }

    public ReceiptItem(ProductExt product, double amount) {
        this.product = product;
        this.amount = amount;
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
        return this.amount * this.product.getPrice();
    }

}
