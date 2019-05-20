package backend;

import model.ProductExt;
import model.ShoppingItem;

import java.util.EventObject;

public class CartEvent extends EventObject {

    private ShoppingItem shoppingItem;
    boolean addEvent;

    public CartEvent setShoppingItem(ShoppingItem item) {
        this.shoppingItem = item;
        return this;
    }

    public ShoppingItem getShoppingItem() {
        return this.shoppingItem;
    }

    public CartEvent setAddEvent(boolean flag) {
        this.addEvent = flag;
        return this;
    }

    public boolean isAddEvent() {
        return this.addEvent;
    }

    public CartEvent(Object source) {
        super(source);
    }
}
