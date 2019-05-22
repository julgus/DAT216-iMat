package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FilesBackend {
    private static FilesBackend instance;

    public static FilesBackend getInstance() {
        if(instance == null){ instance = new FilesBackend(); }
        return instance;
    }

    //
    // CART
    //
    private List<ShoppingItem> loadedShoppingItems = null;

    private List<ShoppingItem> getLoadedShoppingItems() {
        if(loadedShoppingItems == null){
            loadedShoppingItems = readFromCartFile();
        }
        return loadedShoppingItems;
    }

    public List<Tuple> getShoppingItemsForCart() {
        List<Tuple> shoppingItemMap = new ArrayList<>();
        Tuple t;
        for (ShoppingItem item : getLoadedShoppingItems()) {
            //get the only used shoppingItem from backend, pair with saved number of items from closed cart
            t = new Tuple(Backend.getInstance().getShoppingItem(item.getProduct().getProductId()), item.getNumberOfItems());
            shoppingItemMap.add(t);
        }

        return shoppingItemMap;
    }

    private File getCartFile(){
        var directoryPath = new File(getOsSpecificAppPath());
        var file = new File(String.format("%s%s%s", directoryPath.getPath(), osBackslash(), "cart.txt"));

        directoryPath.mkdirs();
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        }
        catch (Exception ex){
            throw new RuntimeException("failed to create crate file" + ex);
        }

        return file;
    }

    private List<ShoppingItem> readFromCartFile(){
        var cartFile = getCartFile();
        if(cartFile.exists()){
            return new ArrayList<>();
        }

        var content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(cartFile.toURI())));
        }
        catch (IOException ex){
            throw new RuntimeException("IOException when reading cart file: " + ex);
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to read cart file: " + ex);
        }

        var itemList = new Gson().fromJson(content, PersistenceCart.class);
        System.out.println("Read cart from file");
        return itemList == null || itemList.getCartItems() == null ? new ArrayList<>() : itemList.getCartItems();
    }

    //
    // RECEIPT
    //
    public List<Receipt> readFromReceiptFile(){
        return getReceiptFiles().stream()
                .map(y -> {
                    try {
                        return new Gson().fromJson(new String(Files.readAllBytes(Paths.get(y.toURI()))), Receipt.class);
                    } catch (IOException e) {
                        throw new RuntimeException(" failed to stream receipt files: " + e);
                    }
                })
                .collect(Collectors.toList());
    }

    public void saveToCartFile(ArrayList<ShoppingItem> items){
        var itemsJson = new GsonBuilder().setPrettyPrinting().create().toJson(new PersistenceCart().setCartItems(items));
        try {
            var fw = new FileWriter(getCartFile());
            fw.write(itemsJson);
            fw.close();
            System.out.println("Saved cart to file.");
        }
        catch (Exception ex){
            throw new RuntimeException("failed to write to file " + ex);
        }
    }

    public void saveReceipt(Receipt receipt){
        if(receipt == null){
            throw new RuntimeException("Null argument exception");
        }
        var itemJson = new GsonBuilder().setPrettyPrinting().create().toJson(receipt);

        try {
            var filePath = String.format("%s%s%s", getReceiptDirectory().getPath(),
                    osBackslash(),
                    "receipt_" + receipt.getPurchaseDate().getTime() + ".txt");
            var file = new File(filePath);
            file.createNewFile();
            var fw = new FileWriter(file);
            fw.write(itemJson);
            fw.close();
            System.out.println("Write receipt to file");
        }
        catch (IOException ex){
            throw new RuntimeException("Failed to read or write\n" + ex);
        }
        catch (Exception ex){
            throw new RuntimeException("Failed to write receipt to file\n" + ex);
        }
    }

    public List<File> getReceiptFiles(){
        return Arrays.stream(Objects.requireNonNull(getReceiptDirectory().listFiles()))
                .filter(x -> x.getName().toLowerCase().contains("receipt"))
                .collect(Collectors.toList());
    }

    public List<String> getReceiptFileNames(){
        return getReceiptFiles().stream().map(File::getName).collect(Collectors.toList());
    }

    private File getReceiptDirectory(){
        var directory = new File(String.format("%s%s%s", getOsSpecificAppPath(), osBackslash(), "Receipts"));
        directory.mkdirs();
        return directory;
    }

    //
    // Profile
    //

    public void saveProfile(Profile profile){
        if(profile == null){
            throw new RuntimeException("profile argument null");
        }

        var json = new GsonBuilder().setPrettyPrinting().create().toJson(profile);

        try {
            var fw = new FileWriter(getProfileFile());
            fw.write(json);
            fw.close();
            System.out.println("Saved profile to file");
        }
        catch (IOException ex){
            throw new RuntimeException("IO exception writing profile to file");
        }
    }

    public Profile readProfileFromFile(){
        try {
            return new Gson().fromJson(new String(Files.readAllBytes(Path.of(getProfileFile().toURI()))), Profile.class);
        }
        catch (IOException ex){
            throw new RuntimeException("Failed to read profile class to file");
        }
        catch (JsonSyntaxException ex){
            throw new RuntimeException("Invalid profile json");
        }
    }

    private File getProfileFile(){
        var profileDirectory = new File(String.format("%s%s%s.txt",
                getOsSpecificAppPath(), osBackslash(), Profile.class.getSimpleName().toLowerCase()));
        try {
            profileDirectory.createNewFile();
        }
        catch (IOException ex){
            throw new RuntimeException("IO exception reading profile file\n" + ex);
        }
        return profileDirectory;
    }


    //
    // Helpers
    //

    private String getOsSpecificAppPath(){
        return isWindows()
            ? String.format("%s\\%s", System.getProperty("user.home"), "iMat")
            : "/Users/juliagustafsson/Documents/Indek/DAT216/iMat";
    }

    private boolean isWindows(){
        return System.getProperty("os.name").contains("Windows");
    }

    private String osBackslash(){
        return isWindows() ? "\\" : "/";
    }

}
