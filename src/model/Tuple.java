package model;

public class Tuple {

    private ShoppingItem item;
    private int numberOfItem;

    public Tuple(ShoppingItem item, int numberOfItem) {
        this.item = item;
        this.numberOfItem = numberOfItem;
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    public ShoppingItem getItem() {
        return item;
    }
}
