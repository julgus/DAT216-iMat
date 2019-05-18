package model;

import backend.CartEvent;
import backend.ShoppingCartListener;


import java.util.*;


public class ShoppingCartExt {

    private final List<ShoppingItem> items = new ArrayList<>();

    private ArrayList<ShoppingCartListener> listeners = new ArrayList();
    private static ShoppingCartExt shoppingCart;

    private ShoppingCartExt(){}

    public static ShoppingCartExt getInstance(){
        if(shoppingCart == null){
            shoppingCart = new ShoppingCartExt();
        }
        return shoppingCart;
    }

    public void addItem(ShoppingItem item) {
        if(!isInCart(item)) {
            this.items.add(item); //add item to cart if first one of this product
        }
        item.increaseNumberOfItems();//increase number of items by one
        this.fireShoppingCartChanged(item,true);
    }

    protected boolean isInCart(ShoppingItem item){
        for (ShoppingItem shoppingItem : items)
            if(shoppingItem == item)
                return true;
        return false;
    }

    public void removeItem(ShoppingItem item) {
        if (item.getNumberOfItems() == 1) {
            items.remove(item);
            item.decreaseNumberOfItems();
        }
        else if(item.getNumberOfItems() > 0)
            item.decreaseNumberOfItems();
        this.fireShoppingCartChanged(item, false);
    }

    //return number of total items
    public int getNumberOfItemsInCart(){
        int sumOfItems = 0;
        for(ShoppingItem item: items){
            sumOfItems += item.getNumberOfItems();
        }
        return sumOfItems;
    }

    public void clear() {
        this.items.clear();
        System.out.println("Clear shopping cart");
        this.fireShoppingCartChanged(null, false);
    }

    public List<ShoppingItem> getItems() {
        return items;

    }

    public double getTotal() {
        double total = 0.0D;

        for(ShoppingItem item : items){
            total = total + (item.getTotal() * item.getNumberOfItems());
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

        for(ShoppingCartListener listener: listeners){
            listener.shoppingCartChanged(evt);
        }

    }
}
