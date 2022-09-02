import java.nio.file.Path;
import java.io.FileWriter;
import java.io.File;

public class Item {

    // Fields
    protected String itemName;
    protected String itemModel;
    protected String itemBrand;
    protected int itemQuantity;
    protected double itemPrice;

    // Constructor
    public Item(String itemName, String itemModel, String itemBrand, int itemQuantity, double itemPrice) {
        this.itemName = itemName.trim();
        this.itemModel = itemModel.trim();
        this.itemBrand = itemBrand.trim();
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    // method - when the worker in the shop buys a 2nd hand item, add the new item to database
    public void writeToDatabase(Path dbPath) {
        try {
            File f = new File(dbPath.toString());
            // Create the database if it does not exist
            if (!f.exists()) {
                f.createNewFile();
            }
            // If the database exists, write new item into the database
            if (f.exists()) {
                FileWriter fileWriter = new FileWriter(dbPath.toString(), true);
                fileWriter.write(
                        this.itemName + "\t" +
                                this.itemModel + "\t" +
                                this.itemBrand + "\t" +
                                this.itemQuantity + "\t" +
                                String.format("%.2f", this.itemPrice) + "\n");
                fileWriter.close();
                System.out.println("A new item has been added into the database successfully.");
            } else {
                System.out.println("The database cannot be created.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while writing new item into the database.");
        }

    }
}