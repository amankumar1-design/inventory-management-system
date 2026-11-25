import java.util.ArrayList;

public class ItemBST {

    private static class Node {
        Item item;
        Node left, right;
        Node(Item item) { this.item = item; }
    }

    private Node root;

    // Insert an item using its name as the key (case-insensitive)
    public boolean insert(Item item) {
        if (root == null) {
            root = new Node(item);
            return true;
        }
        Node current = root;
        while (true) {
            int cmp = item.getName().compareToIgnoreCase(current.item.getName());
            if (cmp == 0) return false;               // duplicate name
            if (cmp < 0) {
                if (current.left == null) { current.left = new Node(item); return true; }
                current = current.left;
            } else {
                if (current.right == null) { current.right = new Node(item); return true; }
                current = current.right;
            }
        }
    }

    // Search an item by exact name (case-insensitive)
    public Item search(String name) {
        Node current = root;
        while (current != null) {
            int cmp = name.compareToIgnoreCase(current.item.getName());
            if (cmp == 0) return current.item;
            current = (cmp < 0) ? current.left : current.right;
        }
        return null;
    }

    // Delete an item by name
    public boolean delete(String name) {
        if (search(name) == null) return false;
        root = delete(root, name);
        return true;
    }

    private Node delete(Node node, String name) {
        if (node == null) return null;
        int cmp = name.compareToIgnoreCase(node.item.getName());
        if (cmp < 0) {
            node.left = delete(node.left, name);
        } else if (cmp > 0) {
            node.right = delete(node.right, name);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = node.right;                // smallest node in right subtree
            while (successor.left != null) successor = successor.left;
            node.item = successor.item;
            node.right = delete(node.right, successor.item.getName());
        }
        return node;
    }

    // In-order traversal gives items in alphabetical order of name
    public ArrayList<Item> inorder() {
        ArrayList<Item> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    // Pre-order traversal is used while saving, so that loading the file
    // rebuilds the same tree shape (saving in sorted order would create a skewed BST)
    public ArrayList<Item> preorder() {
        ArrayList<Item> list = new ArrayList<>();
        preorder(root, list);
        return list;
    }

    private void preorder(Node node, ArrayList<Item> list) {
        if (node == null) return;
        list.add(node.item);
        preorder(node.left, list);
        preorder(node.right, list);
    }

    private void inorder(Node node, ArrayList<Item> list) {
        if (node == null) return;
        inorder(node.left, list);
        list.add(node.item);
        inorder(node.right, list);
    }
}
