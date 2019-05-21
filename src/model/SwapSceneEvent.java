package model;

import java.util.EventObject;

public class SwapSceneEvent extends EventObject {

    boolean direction;

    public SwapSceneEvent(Object source) {
        super(source);
    }

    public void setCheckoutEvent(boolean flag) {
        this.direction = flag;
    }

    public boolean isCheckoutEvent() {
        return this.direction;
    }

}
