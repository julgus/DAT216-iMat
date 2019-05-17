package model;

import backend.CartEvent;
import backend.ShoppingCartListener;


import java.util.*;


public class ShoppingCartExt {

    private Map<ShoppingItem, Integer> items = new HashMap<>();

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
        this.items.put(item,getNumberOfItems(item)+1); //increase current number of items by one
        this.fireShoppingCartChanged(item,true);
    }

    public void removeItem(ShoppingItem item) {
        int currentItems = getNumberOfItems(item);
        if (currentItems > 1)
            this.items.put(item,currentItems-1);
        this.items.put(item,0);
        this.fireShoppingCartChanged(item, false);
    }
    //return current number of one item
    public int getNumberOfItems(ShoppingItem item){
        return items.get(item);
    }

    //return number of total items
    public int getNumberOfItemsInCart(){
        return items.keySet().size();
    }

    public void clear() {
        this.items.clear();
        System.out.println("Clear shopping cart");
        this.fireShoppingCartChanged(null, false);
    }

    public List<ShoppingItem> getItems() {
        return (List<ShoppingItem>) items.keySet();

    }

    public double getTotal() {
        double total = 0.0D;

        for(ShoppingItem item : items.keySet()){
            total += item.getTotal()*items.get(item);
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
