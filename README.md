# inventory-management-system

A console-based Java application for managing inventory items using core data structures and algorithms like Binary Search Trees (BST), Hashing, and Binary Search[cite: 2].

## Features
- **Add Item:** Add new items with validated numeric inputs[cite: 2].
- **Display All Items (A-Z):** View items sorted alphabetically using a Binary Search Tree[cite: 2].
- **Search by ID:** Fast item lookup implemented via Hashing[cite: 2].
- **Search by Name:** Search items by name using BST traversal[cite: 2].
- **Sorted Views & Binary Search:** Sort items by price or quantity and search by price using Binary Search[cite: 2].

## Project Structure
```text
aman project/
├── Inventory.java     # Core inventory operations, hashing, and sorting
├── Item.java          # Item data model
├── ItemBST.java       # Binary Search Tree implementation for alphabetical storage
├── Main.java          # Entry point, input validation, and CLI menu
└── inventory.txt      # Persistent storage for inventory records
```[cite: 2]

## Getting Started

### Prerequisites
- Java Development Kit (JDK 8 or higher)

### Compile & Run
1. Open your terminal in the project root directory[cite: 2].
2. Compile all Java files:
   ```bash
   javac *.java
