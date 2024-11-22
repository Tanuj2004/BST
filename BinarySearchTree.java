
package Tree;
public class BinarySearchTree {
    Node root;

    private static class ValueArray {
        private int[] values;
        private int size;

        public ValueArray() {
            this.values = new int[100];
            this.size = 0;
        }

        public void add(int value) {
            if (size == values.length) {
                int[] newValues = new int[values.length * 2];
                for (int i = 0; i < values.length; i++) {
                    newValues[i] = values[i];
                }
                values = newValues;
            }
            values[size++] = value;
        }

        public int get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException();
            }
            return values[index];
        }

        public int size() {
            return size;
        }
    }

    public int getHeight(Node node) {
        if (node == null) return 0;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public boolean insert(int value) {
        if (contains(root, value)) {
            return false;
        }
        root = insertRec(root, value);
        return true;
    }

    private boolean contains(Node node, int value) {
        if (node == null) return false;
        if (node.value == value) return true;
        return value < node.value ? contains(node.left, value) : contains(node.right, value);
    }

    private Node insertRec(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = insertRec(node.left, value);
        } else if (value > node.value) {
            node.right = insertRec(node.right, value);
        }
        return node;
    }

    public boolean removeNode(int value) {
        if (!contains(root, value)) {
            return false;
        }
        root = removeNodeRec(root, value);
        return true;
    }

    private Node removeNodeRec(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = removeNodeRec(node.left, value);
        } else if (value > node.value) {
            node.right = removeNodeRec(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            node.value = minValue(node.right);
            node.right = removeNodeRec(node.right, node.value);
        }
        return node;
    }

    private int minValue(Node node) {
        int minv = node.value;
        while (node.left != null) {
            node = node.left;
            minv = node.value;
        }
        return minv;
    }

    public void createIndex(int[] values) {
        for (int value : values) {
            insert(value);
        }
    }

    public boolean equals(BinarySearchTree otherTree) {
        return equalsRec(this.root, otherTree.root);
    }

    private boolean equalsRec(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        return (node1.value == node2.value) && equalsRec(node1.left, node2.left) && equalsRec(node1.right, node2.right);
    }

    public void addValueToNodes(int valueToAdd) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        ValueArray values = new ValueArray();
        collectValuesExcludingRoot(root, values);

        Node oldRoot = root;
        root = null;

        for (int i = 0; i < values.size(); i++) {
            int newValue = values.get(i) + valueToAdd;
            if (newValue > oldRoot.value) {
                insert(newValue);
            }
        }
        insert(oldRoot.value);
    }

    private void collectValuesExcludingRoot(Node node, ValueArray values) {
        if (node == null) return;
        if (node != root) {
            values.add(node.value);
        }
        collectValuesExcludingRoot(node.left, values);
        collectValuesExcludingRoot(node.right, values);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "Tree is empty.";
        }
        int height = getHeight(root);
        int width = (1 << height) * 4;
        int rows = height * 4;
        String[][] treeDisplay = new String[rows][width];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < width; j++) {
                treeDisplay[i][j] = " ";
            }
        }
        fillTreeDisplay(root, treeDisplay, 0, 0, width);
        StringBuilder result = new StringBuilder();
        for (String[] row : treeDisplay) {
            int lastNonSpace = row.length - 1;
            while (lastNonSpace >= 0 && row[lastNonSpace].equals(" ")) {
                lastNonSpace--;
            }
            for (int i = 0; i <= lastNonSpace; i++) {
                result.append(row[i]);
            }
            result.append("\n");
        }
        return result.toString();
    }

    private void fillTreeDisplay(Node node, String[][] treeDisplay, int row, int col, int width) {
        if (node == null) return;
        String nodeValue = String.valueOf(node.value);
        int nodeWidth = nodeValue.length();
        int center = col + width / 2;
        int start = center - nodeWidth / 2;

        for (int i = 0; i < nodeWidth; i++) {
            if (start + i < treeDisplay[row].length) {
                treeDisplay[row][start + i] = String.valueOf(nodeValue.charAt(i));
            }
        }

        int nextWidth = width / 2;

        if (node.left != null) {
            int leftCenter = col + nextWidth / 2;
            for (int i = leftCenter + 1; i < center; i++) {
                if (i < treeDisplay[row + 1].length) {
                    treeDisplay[row + 1][i] = "_";
                }
            }
            if (row + 2 < treeDisplay.length && leftCenter < treeDisplay[row + 2].length) {
                treeDisplay[row + 2][leftCenter] = "/";
            }
            fillTreeDisplay(node.left, treeDisplay, row + 3, col, nextWidth);
        }

        if (node.right != null) {
            int rightCenter = col + width - nextWidth / 2;
            for (int i = center + 1; i < rightCenter; i++) {
                if (i < treeDisplay[row + 1].length) {
                    treeDisplay[row + 1][i] = "_";
                }
            }
            if (row + 2 < treeDisplay.length && rightCenter < treeDisplay[row + 2].length) {
                treeDisplay[row + 2][rightCenter] = "\\";
            }
            fillTreeDisplay(node.right, treeDisplay, row + 3, col + width / 2, nextWidth);
        }
    }
}

