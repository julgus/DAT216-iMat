package model;

import backend.CartEvent;
import backend.ShoppingCartListener;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class ShoppingCartExt {

    private ArrayList<ShoppingItem> items = new ArrayList();
    private ArrayList<ShoppingCartListener> listeners = new ArrayList();
    private ShoppingCartExt shoppingCart;

    private ShoppingCartExt(){}

    public ShoppingCartExt getInstance(){
        if(shoppingCart == null){
            shoppingCart = new ShoppingCartExt();
        }
        return shoppingCart;
    }

    public void addItem(ShoppingItem item) {
        this.items.add(item);
        this.fireShoppingCartChanged(item, true);
    }

    public void removeItem(ShoppingItem item) {
        this.items.remove(item);
        this.fireShoppingCartChanged(item, false);
    }
    public void clear() {
        this.items.clear();
        System.out.println("Clear shopping cart");
        this.fireShoppingCartChanged(null, false);
    }

    public List<ShoppingItem> getItems() {
        return this.items;
    }

    public double getTotal() {
        double total = 0.0D;

        ShoppingItem item;
        for(Iterator var3 = this.items.iterator(); var3.hasNext(); total += item.getTotal()) {
            item = (ShoppingItem)var3.next();
        }

        return total;
    }

    public void addShoppingCartListener(ShoppingCartListener scl) {
        this.listeners.add(scl);
    }

    public void removeShoppingCartListener(ShoppingCartListener scl) {
        this.listeners.remove(scl);
    }

    public void fireShoppingCartChanged(ShoppingItem item, boolean addEvent) {
        CartEvent evt = new CartEvent(this);
        evt.setShoppingItem(item);
        evt.setAddEvent(addEvent);
        Iterator var4 = this.listeners.iterator();

        while(var4.hasNext()) {
            ShoppingCartListener scl = (ShoppingCartListener)var4.next();
            scl.shoppingCartChanged(evt);
        }

    }
}
