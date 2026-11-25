import java.util.Scanner;

public class Main {

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Inventory inventory = new Inventory();
        int choice;

        do {
            System.out.println("\n====== Inventory Management System ======");
            System.out.println("1. Add Item");
            System.out.println("2. Display All Items (A-Z, BST)");
            System.out.println("3. Search Item by ID (Hashing)");
            System.out.println("4. Search Item by Name (BST)");
            System.out.println("5. Display Sorted by Price / Quantity");
            System.out.println("6. Search Item by Price (Binary Search)");
            System.out.println("7. Update Quantity");
            System.out.println("8. Delete Item");
            System.out.println("9. Low Stock Report");
            System.out.println("10. Exit");
            System.out.println("-----------------------------------------");
            choice = readInt(sc, "Enter your choice: ");

            switch (choice) {
                case 1:
                    int id = readInt(sc, "Enter item ID: ");
                    System.out.print("Enter item name: ");
                    String name = sc.nextLine();
                    int qty = readInt(sc, "Enter quantity: ");
                    double price = readDouble(sc, "Enter price: ");
                    inventory.addItem(id, name, qty, price);
                    break;
                case 2:
                    inventory.displayAlphabetical();
                    break;
                case 3:
                    inventory.searchById(readInt(sc, "Enter item ID: "));
                    break;
                case 4:
                    System.out.print("Enter item name: ");
                    inventory.searchByName(sc.nextLine());
                    break;
                case 5:
                    int sortChoice = readInt(sc, "Sort by (1 = Price, 2 = Quantity): ");
                    inventory.displaySorted(sortChoice == 1);
                    break;
                case 6:
                    inventory.searchByPrice(readDouble(sc, "Enter price: "));
                    break;
                case 7:
                    int uid = readInt(sc, "Enter item ID: ");
                    int newQty = readInt(sc, "Enter new quantity: ");
                    inventory.updateQuantity(uid, newQty);
                    break;
                case 8:
                    inventory.deleteItem(readInt(sc, "Enter item ID: "));
                    break;
                case 9:
                    inventory.lowStockReport(readInt(sc, "Enter stock limit: "));
                    break;
                case 10:
                    inventory.saveItems();
                    System.out.println("Inventory saved. Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 10);
        sc.close();
    }
}
