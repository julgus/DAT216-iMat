package model;

import se.chalmers.cse.dat216.project.Product;

import java.util.EventObject;

public class SwapSceneEvent extends EventObject {

    boolean direction;
    ProductPrimaryCategory targetPage;

    public SwapSceneEvent(Object source) {
        super(source);
    }

    public void setCheckoutEvent(boolean flag) {
        this.direction = flag;
    }

    public boolean isCheckoutEvent() {
        return this.direction;
    }

    public void setTargetPage(ProductPrimaryCategory targetPage) {
        this.targetPage = targetPage;
    }

    public ProductPrimaryCategory getTargetPage() {
        return targetPage;
    }


}
