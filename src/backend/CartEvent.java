package backend;

import model.ProductExt;
import java.util.EventObject;

public class CartEvent extends EventObject {
    private ProductExt shoppingItem;
    boolean addEvent;

    public void setShoppingItem(ProductExt item) {
        this.shoppingItem = item;
    }

    public ProductExt getShoppingItem() {
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
