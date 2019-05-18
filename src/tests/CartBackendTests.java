package tests;

import backend.Backend;
import backend.CartBackend;
import org.junit.Assert;
import org.junit.Test;

public class CartBackendTests {
    @Test
    public void addAndRemoveItem(){
        CartBackend.getInstance().clearCart();
        var product = Backend.getInstance().getProductById(1);
        CartBackend.getInstance().add(product, 5);
        Assert.assertEquals(5, CartBackend.getInstance().getProductInCart(product).getAmount());
        CartBackend.getInstance().remove(product);
        Assert.assertEquals(4, CartBackend.getInstance().getProductInCart(product).getAmount());
    }

    @Test
    public void isRemovedBelowZeroAmount(){
        CartBackend.getInstance().clearCart();
        var product = Backend.getInstance().getProductById(1);
        CartBackend.getInstance().add(product, 1);
        CartBackend.getInstance().remove(product);
        Assert.assertNull(CartBackend.getInstance().getProductInCart(product));
    }

    @Test
    public void addMultipleOfSame(){
        CartBackend.getInstance().clearCart();
        var loops = 3;
        var product = Backend.getInstance().getProductById(1);
        for(var i = 0; i < loops; i++){
            CartBackend.getInstance().add(product, 1);
        }
        Assert.assertEquals(loops, CartBackend.getInstance().getProductInCart(product).getAmount());
    }

    @Test
    public void doesSubscriberGetMessages(){
        CartBackend.getInstance().clearSubscribers();
        var mock = new CartListenerMock();
        var product = Backend.getInstance().getProductById(1);
        CartBackend.getInstance().subscribe(mock);
        for(var i = 0; i < 3; i++){
            CartBackend.getInstance().add(product, 1);
        }

        Assert.assertEquals(3, mock.getUpdatesReceived());

        CartBackend.getInstance().remove(product);

        Assert.assertEquals(4, mock.getUpdatesReceived());
    }

    @Test
    public void ensureSingleSubscriber(){
        CartBackend.getInstance().clearSubscribers();
        var mock = new CartListenerMock();
        CartBackend.getInstance().subscribe(mock);
        CartBackend.getInstance().subscribe(mock);
        Assert.assertEquals(1, CartBackend.getInstance().getCartListeners().size());
    }

    @Test
    public void unsubscribeStopReceive(){
        CartBackend.getInstance().clearSubscribers();
        CartBackend.getInstance().clearCart();
        var mock = new CartListenerMock();
        var product = Backend.getInstance().getProductById(1);
        CartBackend.getInstance().subscribe(mock);
        CartBackend.getInstance().add(product, 1);
        CartBackend.getInstance().unsubscribe(mock);
        CartBackend.getInstance().add(product, 1);
        Assert.assertEquals(1, mock.getUpdatesReceived());
    }
}
