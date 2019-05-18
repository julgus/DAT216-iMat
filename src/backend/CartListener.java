package backend;

import java.util.List;

public interface CartListener {
    void cartUpdated(List<CartItem> items);
}
