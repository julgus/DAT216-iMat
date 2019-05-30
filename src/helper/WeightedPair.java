package helper;

import model.ProductExt;

public class WeightedPair {
    private int mWeight = 0;
    private ProductExt mProduct;

    public WeightedPair(ProductExt product){
        if(product == null){
            throw new RuntimeException("Attempt to set null product");
        }

        mProduct = product;
    }

    public int getmWeight() {
        return mWeight;
    }

    public WeightedPair addWeight(int mWeight) {
        if(mWeight < 0){
            throw new RuntimeException("Attempt to add negative value");
        }

        this.mWeight += mWeight;
        return this;
    }

    public ProductExt getmProduct() {
        return mProduct;
    }
}
