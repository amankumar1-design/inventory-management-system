import java.io.*;
import java.util.*;

public class Inventory {
    private HashMap<Integer, Item> itemMap = new HashMap<>();   // hashing: search by ID
    private ItemBST nameTree = new ItemBST();                   // BST: search by name
    private static final String FILE_NAME = "inventory.txt";

    public Inventory() {
        loadItems();
    }

    // Add a new item after validation
    public boolean addItem(int id, String name, int quantity, double price) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Item name cannot be blank.");
            return false;
        }
        if (id <= 0 || quantity < 0 || price <= 0) {
            System.out.println("ID and price must be positive and quantity cannot be negative.");
            return false;
        }
        if (itemMap.containsKey(id)) {
            System.out.println("An item with ID " + id + " already exists.");
            return false;
        }
        Item item = new Item(id, name.trim(), quantity, price);
        if (!nameTree.insert(item)) {
            System.out.println("An item named " + name.trim() + " already exists.");
            return false;
        }
        itemMap.put(id, item);
        System.out.println("Item added: " + item);
        return true;
    }

    // Hashing: O(1) average lookup by ID
    public void searchById(int id) {
        Item item = itemMap.get(id);
        if (item != null) System.out.println("Found: " + item);
        else System.out.println("Item with ID " + id + " not found.");
    }

    // BST: O(log N) average lookup by name
    public void searchByName(String name) {
        Item item = nameTree.search(name.trim());
        if (item != null) System.out.println("Found: " + item);
        else System.out.println("Item " + name.trim() + " not found.");
    }

    // Update stock quantity of an item (found through hashing)
    public void updateQuantity(int id, int newQuantity) {
        Item item = itemMap.get(id);
        if (item == null) {
            System.out.println("Item with ID " + id + " not found.");
        } else if (newQuantity < 0) {
            System.out.println("Quantity cannot be negative.");
        } else {
            item.setQuantity(newQuantity);
            System.out.println("Quantity updated: " + item);
        }
    }

    // Delete an item from both the HashMap and the BST
    public void deleteItem(int id) {
        Item item = itemMap.remove(id);
        if (item == null) {
            System.out.println("Item with ID " + id + " not found.");
            return;
        }
        nameTree.delete(item.getName());
        System.out.println("Item deleted: " + item.getName());
    }

    // Display all items alphabetically using BST in-order traversal
    public void displayAlphabetical() {
        ArrayList<Item> list = nameTree.inorder();
        if (list.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("--- INVENTORY (A-Z) ---");
        int i = 1;
        for (Item item : list) System.out.println((i++) + ". " + item);
    }

    // Sorting: display items sorted by price or quantity
    public void displaySorted(boolean byPrice) {
        ArrayList<Item> list = new ArrayList<>(itemMap.values());
        if (list.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        if (byPrice) list.sort(Comparator.comparingDouble(Item::getPrice));
        else list.sort(Comparator.comparingInt(Item::getQuantity));
        System.out.println(byPrice ? "--- SORTED BY PRICE ---" : "--- SORTED BY QUANTITY ---");
        int i = 1;
        for (Item item : list) System.out.println((i++) + ". " + item);
    }

    // Binary Search on the price-sorted list: finds all items with the exact price
    public void searchByPrice(double price) {
        ArrayList<Item> list = new ArrayList<>(itemMap.values());
        list.sort(Comparator.comparingDouble(Item::getPrice));
        int low = 0, high = list.size() - 1, found = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            double midPrice = list.get(mid).getPrice();
            if (midPrice == price) { found = mid; break; }
            else if (midPrice < price) low = mid + 1;
            else high = mid - 1;
        }
        if (found == -1) {
            System.out.println("No item found with price " + String.format("%.2f", price));
            return;
        }
        int start = found, end = found;                  // expand for duplicate prices
        while (start > 0 && list.get(start - 1).getPrice() == price) start--;
        while (end < list.size() - 1 && list.get(end + 1).getPrice() == price) end++;
        System.out.println("Items with price " + String.format("%.2f", price) + ":");
        for (int i = start; i <= end; i++) System.out.println(list.get(i));
    }

    // Report items whose stock is below the given limit
    public void lowStockReport(int limit) {
        boolean any = false;
        System.out.println("--- LOW STOCK (below " + limit + ") ---");
        for (Item item : nameTree.inorder()) {
            if (item.getQuantity() < limit) {
                System.out.println(item);
                any = true;
            }
        }
        if (!any) System.out.println("No items are below the limit.");
    }

    // File handling: save as id|name|quantity|price
    public void saveItems() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Item item : nameTree.preorder()) {
                bw.write(item.getId() + "|" + item.getName() + "|" + item.getQuantity() + "|" + item.getPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    // File handling: load records when the application starts
    public void loadItems() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length != 4) continue;
                Item item = new Item(Integer.parseInt(p[0]), p[1],
                        Integer.parseInt(p[2]), Double.parseDouble(p[3]));
                itemMap.put(item.getId(), item);
                nameTree.insert(item);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}
