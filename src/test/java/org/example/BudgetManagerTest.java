package org.example;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetManagerTest {
    private BudgetManager budgetManager;
    private FileManager fileManager;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
        fileManager = new FileManager();
    }

    @AfterEach
    public void tearDown() {
        File file = fileManager.getFile();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAddIncome() {
        budgetManager.addToBalance(100);
        assertEquals(100, budgetManager.getBalance());
    }

    @Test
    public void testAddItem() {
        budgetManager.addItem("Coffee", 5.0, 1);
        assertEquals(1, budgetManager.getItemsList().size());
        assertEquals("Coffee", budgetManager.getItemsList().get(0).getName());
    }

    @Test
    public void testShowPurchaseList() {
        budgetManager.addItem("Bread", 2.0, 1);
        budgetManager.addItem("T-shirt", 10.0, 2);
        assertDoesNotThrow(() -> budgetManager.showPurchaseList("Food"));
        assertDoesNotThrow(() -> budgetManager.showPurchaseList("Clothes"));
    }

    @Test
    public void testShowBalance() {
        budgetManager.addToBalance(200);
        budgetManager.addItem("Snacks", 50, 1);
        assertEquals(150, budgetManager.getBalance() - budgetManager.getItemsList().stream().mapToDouble(Item::getPrice).sum());
    }

    @Test
    public void testSortAllPurchases() {
        budgetManager.addItem("Cereal", 10.0, 1);
        budgetManager.addItem("Jeans", 40.0, 2);
        budgetManager.sortAllPurchases();
        assertEquals("Jeans", budgetManager.getItemsList().get(0).getName());
    }

    @Test
    public void testSortByType() {
        budgetManager.addItem("Movie Ticket", 15.0, 3);
        budgetManager.addItem("Pizza", 25.0, 1);
        budgetManager.SortAllTypes(); // Manual check for console output
        // Consider refactoring to capture console output for automated testing
    }

    @Test
    public void testFileSavingAndLoading() throws IOException {
        budgetManager.addToBalance(100);
        budgetManager.addItem("Book", 20, 2);
        fileManager.saveFile(budgetManager);

        BudgetManager newBudgetManager = new BudgetManager();
        fileManager.loadFile(newBudgetManager);
        assertEquals(100, newBudgetManager.getBalance());
        assertEquals(1, newBudgetManager.getItemsList().size());
        assertEquals("Book", newBudgetManager.getItemsList().get(0).getName());
    }

    @Test
    public void testSortCertainType() {
        budgetManager.addItem("Beef", 10.0, 1);
        budgetManager.addItem("Chicken", 15.0, 1);
        budgetManager.sortType(1); // Food category
        assertEquals("Beef", budgetManager.getItemsList().get(0).getName()); // Ensure correct order after sorting
    }

    @Test
    public void testInvalidCategoryInSort() {
        budgetManager.sortType(5); // Invalid type; should handle gracefully
        assertEquals(0, budgetManager.getItemsList().size()); // No items should change
    }

//    @Test
//    public void testShowEmptyPurchaseList() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            budgetManager.showPurchaseList("Food");
//        });
}









