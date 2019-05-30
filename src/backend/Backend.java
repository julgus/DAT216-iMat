package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import controls.CartController;
import helper.WeightedPair;
import javafx.util.Pair;
import model.*;
import org.jdesktop.application.Resource;
import org.json.JSONObject;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Backend implements ProductsData {
    private TreeMap<Integer, ProductExt> data = new TreeMap<>();
    private Map<Integer, ShoppingItem> shoppingItems = new HashMap<>();
    private static Backend instance;

    public static Backend getInstance() {
        if(instance == null) { instance = new Backend(); }
        return instance;
    }

    private Backend() {
        //transformProvidedBackend();
        loadFromFile("/products_v1.txt").forEach(x -> {
            data.put(x.getProductId(), x);
            shoppingItems.put(x.getProductId(), new ShoppingItem(x, 1)); });

//        IntStream.range(1, data.size())
//            .forEach(x -> shoppingItems.put(x, new ShoppingItem(data.get(x),1)));
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
        return data.entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(x -> x.getSecondaryCategory().equals(category))
            .collect(Collectors.toList());
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

        final var lowerSearch = search.toLowerCase();
        var wPairs = data.values().stream().map(WeightedPair::new).collect(Collectors.toList());
        wPairs.forEach(x -> {
            Arrays.stream(x.getmProduct().getName().split(" "))
                    .forEach(y -> {
                        if(y.toLowerCase().equals(lowerSearch)){
                            x.addWeight(8); }
                    });

            if(x.getmProduct().getName().toLowerCase().contains(lowerSearch)){
                x.addWeight(4);
            }
            if(x.getmProduct().getSecondaryCategory().name().toLowerCase().contains(lowerSearch)){
                x.addWeight(2);
            }
            if(x.getmProduct().getPrimaryCategory().name().toLowerCase().contains(lowerSearch)){
                x.addWeight(1);
            }
        });

        return wPairs.stream().filter(x -> x.getmWeight() > 0)
                .sorted((a, b) -> Integer.compare(b.getmWeight(), a.getmWeight()))
                .map(WeightedPair::getmProduct)
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

            case Grönagrönsaker:
                return "Gröna grönsaker";

            default: return category.name();
        }
    }

    public ShoppingItem getShoppingItem(int id) {
        return shoppingItems.get(id);
    }

    public Receipt cartToReceipt(Date deliveryDate, double deliveryFee){
        var receiptList = CartController.getInstance().getShoppingItems()
                .stream()
                .map(x -> new ReceiptItem(x.getProduct(), x.getNumberOfItems()))
                .collect(Collectors.toList());

        return new Receipt(receiptList, new Date(), deliveryDate, deliveryFee);
    }

    public List<ShoppingItem> receiptToShoppingItems(Receipt receipt){
        return receipt.getReceiptItems()
                .stream()
                .map(x -> Backend.getInstance().getShoppingItem(x.getProduct().getProductId()))
                .collect(Collectors.toList());
    }
}
