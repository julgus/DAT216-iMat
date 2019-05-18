package tests;

import backend.CartItem;
import backend.CartListener;

import java.util.List;

public class CartListenerMock implements CartListener {
    private int updatesReceived = 0;

    @Override
    public void cartUpdated(List<CartItem> items) {
        updatesReceived++;
    }

    public int getUpdatesReceived() {
        return updatesReceived;
    }
}
