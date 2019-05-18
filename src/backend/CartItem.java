package backend;


import model.ProductExt;

public class CartItem {
    private int productId;
    private int amount;
    private ProductExt product;

    public int getProductId() {
        return productId;
    }

    public CartItem setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public CartItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ProductExt getProduct() {
        if(product == null){
            product = Backend.getInstance().getProductById(productId);
        }

        return product;
    }
}
