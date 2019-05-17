package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.ProductExt;
import model.ProductPrimaryCategory;
import model.ProductSecondaryCategory;
import model.SpecialProduct;
import org.jdesktop.application.Resource;
import org.json.JSONObject;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Backend implements ProductsData {
    private TreeMap<Integer, ProductExt> data = new TreeMap<>();
    private static Backend instance;

    public static Backend getInstance() {
        if(instance == null) { instance = new Backend(); }
        return instance;
    }

    public Backend() {
        //transformProvidedBackend();
        loadFromFile("/products_v1.txt").forEach(x -> data.put(x.getProductId(), x));
    }

    private void transformProvidedBackend(){
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject jobj = new JSONObject();
        List<ProductExt> sortedProducts = IMatDataHandler.getInstance().getProducts()
            .stream()
            .map(ProductExt::fromProduct)
            .sorted(Comparator.comparing(ProductExt::getProductId))
            .collect(Collectors.toList());
        sortedProducts.forEach(x -> jobj.put(String.valueOf(x.getProductId()), new JSONObject(gson.toJson(x))));
        System.out.println(jobj.toString(1));
        printToFile(jobj);
    }

    private List<ProductExt> loadFromFile(String filePath){
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            JsonObject jobj = gson.fromJson(new String(Resource.class.getResourceAsStream(filePath).readAllBytes()),
                JsonObject.class);
            return jobj.entrySet().stream()
                .map(x -> gson.fromJson(x.getValue(), ProductExt.class))
                .collect(Collectors.toList());
        }
        catch (Exception ex){
            throw new RuntimeException("failed to read file");
        }
    }

    private void printToFile(JSONObject jobj){
        try {
            File f = new File("G:\\tmp\\products.txt");
            FileWriter fw = new FileWriter(f);
            fw.write(jobj.toString(3));
            fw.close();
        }
        catch (Exception ex){
            System.err.println("Failed to print to file " + ex.getMessage());
        }
    }

    @Override
    public ProductExt getProductById(int id) {
        return data.getOrDefault(id, null);
    }

    @Override
    public List<ProductExt> getAllProducts() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<ProductExt> getProductWithPrimaryCategory(ProductPrimaryCategory category) {
        return data.entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(x -> x.getPrimaryCategory().equals(category))
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductExt> getProductsWithSecondaryCategory(ProductSecondaryCategory category) {
        return getProductsWithSecondaryCategory(category, new ArrayList<>(data.values()));
    }

    @Override
    public List<ProductExt> getProductsWithSecondaryCategory(ProductSecondaryCategory category, List<ProductExt> fromList) {
        return fromList.stream()
            .filter(x -> x.getSecondaryCategory().equals(category))
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductExt> searchProductsByName(String search) {
        if(search == null || search.isEmpty()){
            return new ArrayList<>();
        }

        Pattern pattern = Pattern.compile(String.format("\\w*?%s\\w*?", search), Pattern.CASE_INSENSITIVE);
        return data.values().stream()
                .filter(x -> pattern.matcher(x.getName()).matches())
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductExt> getSpecialProducts(SpecialProduct product) {
        return data.values().stream()
            .filter(x -> x.getSpecialProduct().equals(product))
            .collect(Collectors.toList());
    }

    @Override
    public List<ProductSecondaryCategory> getSecondaryCategories(ProductPrimaryCategory category) {
        return getProductWithPrimaryCategory(category).stream()
            .map(ProductExt::getSecondaryCategory)
            .distinct()
            .collect(Collectors.toList());
    }

    @Override
    public String getPrimaryCategoryName(ProductPrimaryCategory category) {
        return category.name();
    }

    @Override
    public String getSecondaryCategoryName(ProductSecondaryCategory category) {
        switch (category){
            case NötterOchFrön:
                return "Nötter och frön";

            case ExotiskaFrukter:
                return "Exotiska frukter";

            case KallaDrycker:
                return "Kalla drycker";

            case VarmaDrycker:
                return "Varma drycker";

            case RäkorOchSkaldjur:
                return "Räkor och skaldjur";

            case BurkarOchKonserver:
                return "Burkar och konserver";

            default: return category.name();
        }
    }
}
