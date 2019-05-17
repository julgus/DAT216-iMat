package model;

import se.chalmers.cse.dat216.project.Product;

public class ProductExt {
    private ProductPrimaryCategory primaryCategory;
    private ProductSecondaryCategory secondaryCategory;
    private int productId = 0;
    private String name = "";
    private SpecialProduct specialProduct;
    private String unit;
    private double price = 0.00D;
    private String imageName = "";

    public int getProductId() {
        return productId;
    }

    public ProductExt setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductExt setName(String name) {
        this.name = name;
        return this;
    }

    public SpecialProduct getSpecialProduct() {
        return specialProduct;
    }

    public ProductExt setSpecialProduct(SpecialProduct specialProduct) {
        this.specialProduct = specialProduct;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ProductExt setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public ProductExt setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getImageName() {
        return imageName;
    }

    public ProductExt setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public ProductPrimaryCategory getPrimaryCategory() {

        return primaryCategory;
    }

    public ProductExt setPrimaryCategory(ProductPrimaryCategory primaryCategory) {
        this.primaryCategory = primaryCategory;
        return this;
    }

    public ProductSecondaryCategory getSecondaryCategory() {
        return secondaryCategory;
    }

    public ProductExt setSecondaryCategory(ProductSecondaryCategory secondaryCategory) {
        this.secondaryCategory = secondaryCategory;
        return this;
    }

    public String toString() {
        return this.productId + " - " + this.name;
    }

    public boolean equals(Object o) {
        if (o instanceof Product) {
            return this.getProductId() == ((Product)o).getProductId();
        } else {
            return false;
        }
    }

    public static ProductExt fromProduct(Product product){
        return new ProductExt()
            .setName(product.getName())
            .setImageName(product.getImageName())
            .setPrice(product.getPrice())
            .setProductId(product.getProductId())
            .setUnit(product.getUnit())
            .setSpecialProduct(SpecialProduct.none)
            .setPrimaryCategory(ProductPrimaryCategory.none)
            .setSecondaryCategory(ProductSecondaryCategory.none);
    }
    public double getAmount(){
        return 1.0D;
    }
}
