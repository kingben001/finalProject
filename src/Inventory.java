import java.nio.file.Path;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;

public class Inventory {

    // Fields
    protected ArrayList<Item> itemList;
    protected Path dbPath;

    // Constructor
    public Inventory(Path dbPath) {
        this.dbPath = dbPath;
        this.itemList = new ArrayList<Item>();

        // Read all the items from the database
        try {
            File f = new File(this.dbPath.toString());
            if (f.exists()) {
                // Create readers
                FileReader fileReader = new FileReader(this.dbPath.toString());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;

                // Add items into the itemList
                while ((line = bufferedReader.readLine()) != null) {
                    String[] stringArray = line.split("\t", 5);

                    Item item = new Item(
                            stringArray[0].trim(),
                            stringArray[1].trim(),
                            stringArray[2].trim(),
                            Integer.parseInt(stringArray[3].trim()),
                            Double.parseDouble(stringArray[4].trim()));

                    this.itemList.add(item);
                }

                // Close all the readers
                bufferedReader.close();
                fileReader.close();
            } else {
                f.createNewFile();
            }
            if (!f.exists()) {
                System.out.println("The database cannot be created.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while reading data from the database.");
        }

    }

    // method - print items in the inventory
    public void printItems() {
        System.out.println(
                "No.\tName\tModel\tBrand\tQuantity\tPrice");
        for (int i = 0; i < this.itemList.size(); i++) {
            System.out.println(
                    i + "\t" +
                            this.itemList.get(i).itemName + "\t" +
                            this.itemList.get(i).itemModel + "\t" +
                            this.itemList.get(i).itemBrand + "\t" +
                            this.itemList.get(i).itemQuantity + "\t" +
                            this.itemList.get(i).itemPrice);
        }
    }

    // method - print stats of items in the inventory
    public void printItemsStats() {
        // Calculate total number of items and total net worth
        int totalNumberOfItems = 0;
        int totalNetWorth = 0;
        for (int i = 0; i < this.itemList.size(); i++) {
            totalNumberOfItems += this.itemList.get(i).itemQuantity;
            totalNetWorth += this.itemList.get(i).itemQuantity * this.itemList.get(i).itemPrice;
        }
        System.out.println("Total number of items in the inventory: " + totalNumberOfItems);
        System.out.println("Total net worth in the inventory: $" + totalNetWorth);
    }

    // method - print items in the inventory
    public void sellItems(int sellItemIndex, int sellItemQuantity) {
        if (sellItemIndex < this.itemList.size()) {
            double totalPrice = 0;

            if (this.itemList.get(sellItemIndex).itemQuantity >= sellItemQuantity) {
                this.itemList.get(sellItemIndex).itemQuantity = this.itemList.get(sellItemIndex).itemQuantity
                        - sellItemQuantity;
            } else {
                // Sell maximum quantity of items
                sellItemQuantity = this.itemList.get(sellItemIndex).itemQuantity;
                this.itemList.get(sellItemIndex).itemQuantity = 0;
            }

            // Calculate total price
            totalPrice = sellItemQuantity * this.itemList.get(sellItemIndex).itemPrice;
            System.out.println("The total price of " + sellItemQuantity +
                    " " + this.itemList.get(sellItemIndex).itemName +
                    " (" + this.itemList.get(sellItemIndex).itemBrand +
                    ", " + this.itemList.get(sellItemIndex).itemModel +
                    ") is " + totalPrice + ".");

            // Write data to database
            try {
                FileWriter fileWriter = new FileWriter(this.dbPath.toString());
                for (int i = 0; i < this.itemList.size(); i++) {
                    if (this.itemList.get(i).itemQuantity > 0) {
                        fileWriter.write(
                                this.itemList.get(i).itemName + "\t" +
                                        this.itemList.get(i).itemModel + "\t" +
                                        this.itemList.get(i).itemBrand + "\t" +
                                        this.itemList.get(i).itemQuantity + "\t"
                                        + String.format("%.2f", this.itemList.get(i).itemPrice) + "\n");
                    }

                }

                fileWriter.close();
            } catch (Exception e) {
                System.out.println("An error occurred while writing new item into the database.");
            }

        } else {
            System.out.println("The selected item cannot be found in the inventory.");
        }
    }
}