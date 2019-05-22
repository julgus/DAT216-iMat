package model;

import se.chalmers.cse.dat216.project.Customer;

import java.util.Date;
import java.util.List;

public class Receipt {

    private final List<ReceiptItem> receiptItems;
    private final Date purchaseDate;
    private final Date deliveryDate;
    private final double deliveryFee;
    private boolean delivered;

    public Receipt(List<ReceiptItem> receiptItems, Date purchaseDate, Date deliveryDate, Double deliveryFee, boolean delivered) {
        this.receiptItems = receiptItems;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.deliveryFee = deliveryFee;
        this.delivered = delivered;
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public double getTotalAmount() {
        return getReceiptItems().stream()
            .mapToDouble(x -> x.getTotal() * x.getNumberOfItems())
            .sum() + deliveryFee;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }
}
