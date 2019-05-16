package backend;

import model.ProductExt;
import model.ProductPrimaryCategory;
import model.ProductSecondaryCategory;
import model.SpecialProduct;

import java.util.List;

public interface ProductsData {
    ProductExt getProductById(int id);
    List<ProductExt> getAllProducts();
    List<ProductExt> getProductWithPrimaryCategory(ProductPrimaryCategory category);
    List<ProductExt> getProductsWithSecondaryCategory(ProductSecondaryCategory category);
    List<ProductExt> getProductsWithSecondaryCategory(ProductSecondaryCategory category, List<ProductExt> fromList);
    List<ProductExt> searchProductsByName(String search);
    List<ProductExt> getSpecialProducts(SpecialProduct product);
    String getPrimaryCategoryName(ProductPrimaryCategory category);
    String getSecondaryCategoryName(ProductSecondaryCategory category);
}
