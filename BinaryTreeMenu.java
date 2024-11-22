package Tree;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BinaryTreeMenu {
    private final BinarySearchTree tree1;
    private final BinarySearchTree tree2;
    private final Scanner sc;

    public BinaryTreeMenu() {
        this.tree1 = new BinarySearchTree();
        this.tree2 = new BinarySearchTree();
        this.sc = new Scanner(System.in);
    }

    /**
     * Starts the main menu loop.
     */
    public void startMenu() {
        int choice = 0;

        while (choice != Constants.MAX_MENU_CHOICE) {
            try {
                System.out.println(Constants.MENU_HEADER);
                System.out.println(Constants.MENU_OPTION_1);
                System.out.println(Constants.MENU_OPTION_2);
                System.out.println(Constants.MENU_OPTION_3);
                System.out.println(Constants.MENU_OPTION_4);
                System.out.println(Constants.MENU_OPTION_5);
                choice = getValidatedIntInput(Constants.PROMPT_ENTER_CHOICE,
                       Constants.MIN_MENU_CHOICE, Constants.MAX_MENU_CHOICE);

                switch (choice) {
                    case 1:
                        handleAddTree();
                        break;
                    case 2:
                        handleCheckEquality();
                        break;
                    case 3:
                        handleRemoveNode();
                        break;
                    case 4:
                        handleAddValueToNodes();
                        break;
                    case 5:
                        System.out.println(Constants.EXITING);
                        break;
                    default:
                        System.out.println(Constants.INVALID_INPUT);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(Constants.INVALID_INPUT);
                sc.next(); // Clear invalid input
            }
        }

        sc.close();
    }

    /**
     * Handles adding values to a selected tree.
     */
    private void handleAddTree() {
        int size = getValidatedIntInput(Constants.PROMPT_ADD_TREE_SIZE,
               Constants.MIN_INPUT_VALUE, 100);
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = getValidatedIntInput(
                    String.format(Constants.PROMPT_ENTER_VALUE, i + 1),
                    Constants.MIN_INPUT_VALUE, Constants.MAX_INPUT_VALUE);
        }

        int treeChoice = getValidatedIntInput(String.format(Constants.PROMPT_SELECT_TREE, Constants.ADDING_VALUES_TO),
                Constants.MIN_TREE_CHOICE, Constants.MAX_TREE_CHOICE);
        BinarySearchTree selectedTree = (treeChoice == 1) ? tree1 : tree2;

        selectedTree.createIndex(values);
        System.out.println("Tree " + treeChoice + ":");
        System.out.println(selectedTree.toString());
    }

    /**
     * Handles checking if two trees are equal.
     */
    private void handleCheckEquality() {
        System.out.println(Constants.CHECKING_TREES_EQUAL);
        boolean areEqual = tree1.equals(tree2);
        System.out.println(Constants.TREE_EQUALITY_RESULT + areEqual);
    }

    /**
     * Handles removing a node from a selected tree.
     */
    private void handleRemoveNode() {
        int removeValue = getValidatedIntInput(Constants.ENTER_VALUE_TO_REMOVE,
               Constants.MIN_INPUT_VALUE, Constants.MAX_INPUT_VALUE);
        int treeChoice = getValidatedIntInput(String.format(Constants.PROMPT_SELECT_TREE, Constants.REMOVING_VALUE_FROM),
                Constants.MIN_TREE_CHOICE, Constants.MAX_TREE_CHOICE);

        BinarySearchTree selectedTree = (treeChoice == 1) ? tree1 : tree2;
        if (selectedTree.removeNode(removeValue)) {
            System.out.println(String.format(Constants.REMOVED_VALUE, removeValue, treeChoice));
        } else {
            System.out.println(String.format(Constants.VALUE_NOT_FOUND, treeChoice));
        }
        System.out.println("Tree " + treeChoice + ":");
        System.out.println(selectedTree.toString());
    }

    /**
     * Handles adding a value to all nodes (except the root) of a selected tree.
     */
    private void handleAddValueToNodes() {
        int addValue = getValidatedIntInput(Constants.ENTER_VALUE_TO_ADD_TO_NODES,
                Constants.MIN_INPUT_VALUE, Constants.MAX_INPUT_VALUE);
        int treeChoice = getValidatedIntInput(String.format(Constants.PROMPT_SELECT_TREE, Constants.MODIFYING_TREE),
                Constants.MIN_TREE_CHOICE, Constants.MAX_TREE_CHOICE);

        BinarySearchTree selectedTree = (treeChoice == 1) ? tree1 : tree2;
        selectedTree.addValueToNodes(addValue);
        System.out.println(String.format(Constants.TREE_UPDATED, treeChoice));
        System.out.println(selectedTree.toString());
    }

    /**
     * Helper method to validate integer input within a range.
     */
    private int getValidatedIntInput(String message, int min, int max) {
        int input = -1;
        while (true) {
            System.out.print(message);
            try {
                input = sc.nextInt();
                if (input >= min && input <= max) {
                    break;
                }
                System.out.println(String.format(Constants.ERROR_OUT_OF_RANGE, min, max));
            } catch (InputMismatchException e) {
                System.out.println(Constants.INVALID_INPUT);
                sc.next(); // Clear invalid input
            }
        }
        return input;
    }
}
