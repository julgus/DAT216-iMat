package backend;

import model.ProductExt;
import java.util.ArrayList;
import java.util.List;

public class CartBackend{
    private static CartBackend instance;
    private List<CartListener> cartListeners = new ArrayList<>();
    private List<CartItem> cartItems = new ArrayList<>();

    public static CartBackend getInstance() {
        if(instance == null){ instance = new CartBackend(); }
        return instance;
    }

    public void subscribe(final CartListener cartListener){
        if(cartListeners.contains(cartListener)){
            System.err.println("Attempt to add cart listener twice");
            return;
        }

        cartListeners.add(cartListener);
    }

    public void unsubscribe(final CartListener cartListener){
        if(!cartListeners.contains(cartListener)){
            return;
        }

        cartListeners.remove(cartListener);
    }

    public void add(final ProductExt product, final int amount) {
        if(product == null){
            throw new RuntimeException("attempt to add null product");
        }

        addById(product.getProductId(), amount);
    }

    public void addById(final int id, final int amount) {
        if(amount <= 0){
            return;
        }

        var existing = cartItems.stream()
                .filter(x -> x.getProductId() == id)
                .findFirst()
                .orElse(null);

        if(existing != null){
            existing.setAmount(existing.getAmount()+1);
        }
        else {
            cartItems.add(new CartItem()
                    .setProductId(id)
                    .setAmount(amount));
        }

        cartListeners.forEach(x -> x.cartUpdated(cartItems));
    }

    public void remove(final ProductExt productExt) {
        if(productExt == null){
            throw new RuntimeException("Attempt to remove null product");
        }

        removeById(productExt.getProductId());
    }

    public void removeById(final int id) {
        var product = cartItems.stream()
                .filter(x -> x.getProductId() == id)
                .findFirst()
                .orElse(null);

        if(product == null){
            return;
        }

        product.setAmount(product.getAmount()-1);

        if(product.getAmount() <= 0){
            cartItems.remove(product);
        }

        cartListeners.forEach(x -> x.cartUpdated(cartItems));
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public CartItem getProductInCart(final ProductExt product){
        return getProductInCartById(product.getProductId());
    }

    public CartItem getProductInCartById(final int productId){
        return cartItems.stream().filter(x -> x.getProductId() == productId)
                .findFirst()
                .orElse(null);
    }

    public void clearCart(){
        cartItems.clear();
    }

    public void clearSubscribers(){
        cartListeners.clear();
    }

    public List<CartListener> getCartListeners() {
        return new ArrayList<>(cartListeners);
    }
}
