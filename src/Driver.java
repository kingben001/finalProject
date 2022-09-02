import java.util.Scanner;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {
    public static void printMainMenu() {
        // This is the main menu
        System.out.println("");
        System.out.println("------------------------------------------------------------");
        System.out.println("-------------Electronics Store Inventory System-------------");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. Purchase new item                                        ");
        System.out.println("2. Check inventory                                          ");
        System.out.println("3. Sell item                                                ");
        System.out.println("4. Clear database                                           ");
        System.out.println("5. Quit                                                     ");
    }

    public static void main(String[] args) {
        // Database path
        Path dbPath = Paths.get("db.txt");


        Scanner scanner = new Scanner(System.in);
        int menuNumber;

        while (true) {
            printMainMenu();
            System.out.print("Please choose from menu: ");

            try {
                // Get user selection
                menuNumber = Integer.parseInt(scanner.nextLine());

                // Check selected option
                if (menuNumber > 5 || menuNumber < 1) {
                    System.out.println("This option is not available in the menu.");
                    break;
                } else if (menuNumber == 1) {
                    // Purchase new item
                    try {
                        // Create the database if it does not exist
                        File f = new File(dbPath.toString());
                        if (!f.exists()) {
                            f.createNewFile();
                        }

                        // Ask user to enter the item he/she purchase
                        System.out.print("Please enter item name: ");
                        String itemName = scanner.nextLine();
                        System.out.print("Please enter item model: ");
                        String itemModel = scanner.nextLine();
                        System.out.print("Please enter item brand: ");
                        String itemBrand = scanner.nextLine();
                        System.out.print("Please enter item quantity: ");
                        int itemQuantity = Integer.parseInt(scanner.nextLine());
                        System.out.print("Please enter item price: ");
                        double itemPrice = Double.parseDouble(scanner.nextLine());

                        // Create a new item
                        Item newItem = new Item(itemName, itemModel, itemBrand, itemQuantity, itemPrice);

                        // Write the new item to database
                        newItem.writeToDatabase(dbPath);
                    } catch (Exception e) {
                        System.out.println("An error occurred when adding new item into database.");
                    }
                } else if (menuNumber == 2) {
                    // Get the inventory from the database
                    Inventory inventory = new Inventory(dbPath);

                    // Print everything in the inventory
                    inventory.printItems();
                    inventory.printItemsStats();
                } else if (menuNumber == 3) {
                    // Get the inventory from the database
                    Inventory inventory = new Inventory(dbPath);

                    // Print everything in the inventory
                    inventory.printItems();
                    System.out.print("Please enter the number of the item you would like to sell: ");
                    int sellItemIndex = Integer.parseInt(scanner.nextLine());
                    System.out.print("Please enter the quantity of the item you would like to sell: ");
                    int sellItemQuantity = Integer.parseInt(scanner.nextLine());

                    inventory.sellItems(sellItemIndex, sellItemQuantity);

                } else if (menuNumber == 4) {
                    // Clear the database
                    File f = new File(dbPath.toString());
                    if (f.exists()) {
                        if (f.delete()) {
                            System.out.println("The database has been cleared.");
                        } else {
                            System.out.println("Failed to clear the database.");
                        }
                    } else {
                        System.out.println("The database does not exist.");
                    }
                } else if (menuNumber == 5) {
                    // Exit the program
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input detected!");
                break;
            }
        }

        scanner.close();
    }
}