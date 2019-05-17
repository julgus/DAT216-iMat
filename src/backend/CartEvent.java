package backend;

import model.ProductExt;
import model.ShoppingItem;

import java.util.EventObject;

public class CartEvent extends EventObject {

    private ShoppingItem shoppingItem;
    boolean addEvent;

    public void setShoppingItem(ShoppingItem item) {
        this.shoppingItem = item;
    }

    public ShoppingItem getShoppingItem() {
        return this.shoppingItem;
    }

    public void setAddEvent(boolean flag) {
        this.addEvent = flag;
    }

    public boolean isAddEvent() {
        return this.addEvent;
    }

    public CartEvent(Object source) {
        super(source);
    }
}
