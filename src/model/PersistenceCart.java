package model;

import java.util.ArrayList;

public class PersistenceCart {
    private ArrayList<ShoppingItem> cartItems;

    public ArrayList<ShoppingItem> getCartItems() {
        return cartItems;
    }

    public PersistenceCart setCartItems(ArrayList<ShoppingItem> cartItems) {
        this.cartItems = cartItems;
        return this;
    }
}

