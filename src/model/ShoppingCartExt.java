package model;

import backend.CartEvent;
import backend.ShoppingCartListener;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCartExt {

    private ArrayList<model.ProductExt> items = new ArrayList();
    private ArrayList<ShoppingCartListener> listeners = new ArrayList();

    public void addItem(model.ProductExt p) {
        this.items.add(p);
        this.fireShoppingCartChanged(p, true);
    }

    public void removeItem(model.ProductExt p) {
        this.items.remove(p);
        this.fireShoppingCartChanged(p, false);
    }
    public void clear() {
        this.items.clear();
        System.out.println("Clear shopping cart");
        this.fireShoppingCartChanged((model.ProductExt)null, false);
    }

    public List<model.ProductExt> getItems() {
        return this.items;
    }

    public double getTotal() {
        double total = 0.0D;

        model.ProductExt p;
        for(Iterator var3 = this.items.iterator(); var3.hasNext(); total += p.getPrice()) {
            p = (model.ProductExt)var3.next();
        }

        return total;
    }

    public void addShoppingCartListener(ShoppingCartListener scl) {
        this.listeners.add(scl);
    }

    public void removeShoppingCartListener(ShoppingCartListener scl) {
        this.listeners.remove(scl);
    }

    public void fireShoppingCartChanged(model.ProductExt item, boolean addEvent) {
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
