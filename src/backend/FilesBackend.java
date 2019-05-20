package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.PersistenceCart;
import model.Receipt;
import model.ShoppingItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilesBackend {
    private static FilesBackend instance;

    public static FilesBackend getInstance() {
        if(instance == null){ instance = new FilesBackend(); }
        return instance;
    }

    private List<ShoppingItem> loadedShoppingItems = null;

    public List<ShoppingItem> getLoadedShoppingItems() {
        if(loadedShoppingItems == null){
            loadedShoppingItems = readFromCartFile();
        }
        return loadedShoppingItems;
    }

    private File getCartFile(){
        var directoryPath = new File(String.format("%s\\%s", System.getProperty("user.home"), "iMat"));
        var file = new File(String.format("%s\\%s", directoryPath.getPath(), "cart.txt"));

        if(!file.exists()){
            try {
                directoryPath.mkdirs();
                file.createNewFile();
            }
            catch (Exception ex){
                throw new RuntimeException("failed to create crate file" + ex);
            }
        }

        return file;
    }

    private List<ShoppingItem> readFromCartFile(){
        var content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(getCartFile().toURI())));
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

        }
        catch (Exception ex){
            throw new RuntimeException("Failed to write to file");
        }
    }

    public List<Receipt> readFromReceiptFile(){
        var directoryPath = getReceiptDirectory();

        final var gson = new Gson();

        return getReceiptFiles().stream()
                .map(y -> {
                    try {
                        return gson.fromJson(new String(Files.readAllBytes(Paths.get(y.toURI()))), Receipt.class);
                    } catch (IOException e) {
                        throw new RuntimeException(" failed to stream receipt files: " + e);
                    }
                })
                .collect(Collectors.toList());
    }

    public List<File> getReceiptFiles(){
        return Arrays.stream(getReceiptDirectory().listFiles())
                .filter(x -> x.getName().contains("receipt"))
                .collect(Collectors.toList());
    }

    public List<String> getReceiptFileNames(){
        return getReceiptFiles().stream().map(File::getName).collect(Collectors.toList());
    }


    private File getReceiptDirectory(){
        var directory = new File(String.format("%s\\%s\\%s", System.getProperty("user.home"), "iMat", "Receipts"));
        directory.mkdirs();
        return directory;
    }

}
