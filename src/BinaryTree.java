package trees;

import java.io.*;
import java.io.Serializable;


public class BinaryTree implements Serializable {
    TreeNode root; 

        public BinaryTree() {
        root = null;
    }

    
    void insertNode(int key) {
        root = insertNodeRec(root, key);
    }

   
    TreeNode insertNodeRec(TreeNode root, int key) {
        if (root == null) {
            root = new TreeNode(key);
            return root;
        }

        if (key < root.key) {
            root.left = insertNodeRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertNodeRec(root.right, key);
        }

        return root;
    }

    
    void removeNode(int key) {
        root = removeNodeRec(root, key);
    }

    
    TreeNode removeNodeRec(TreeNode root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.key) {
            root.left = removeNodeRec(root.left, key);
        } else if (key > root.key) {
            root.right = removeNodeRec(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.key = minValue(root.right);
            root.right = removeNodeRec(root.right, root.key);
        }

        return root;
    }

    
    int minValue(TreeNode root) {
        int minValue = root.key;
        while (root.left != null) {
            minValue = root.left.key;
            root = root.left;
        }
        return minValue;
    }

    
    boolean contains(int key) {
        return contains(root, key);
    }

   
    private boolean contains(TreeNode root, int key) {
        if (root == null) {
            return false;
        }

        if (key == root.key) {
            return true;
        } else if (key < root.key) {
            return contains(root.left, key);
        } else {
            return contains(root.right, key);
        }
    }
    
   
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public static BinaryTree loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (BinaryTree) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
