package trees;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.io.Serializable;


public class BTree implements Serializable {
    private BTreeNode root; 
    private int t;


    public BTree(int t) {
        this.root = new BTreeNode();
        this.t = t;
    }
    
 
    public BTreeNode getRoot() {
        return root;
    }

  
    public void insert(int key) {
        BTreeNode r = root;
        if (r.keys.size() == 2 * t - 1) {
            BTreeNode s = new BTreeNode();
            root = s;
            s.children.add(r);
            splitChild(s, 0);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }


    private void insertNonFull(BTreeNode x, int key) {
        int i = x.keys.size() - 1;
        if (x.isLeaf) {
            while (i >= 0 && key < x.keys.get(i)) {
                i--;
            }
            x.keys.add(i + 1, key);
        } else {
            while (i >= 0 && key < x.keys.get(i)) {
                i--;
            }
            i++;

            if (x.children.get(i).keys.size() == 2 * t - 1) {
                splitChild(x, i);
                if (key > x.keys.get(i)) {
                    i++;
                }
            }
            insertNonFull(x.children.get(i), key);
        }
    }


    private void splitChild(BTreeNode x, int i) {
        BTreeNode y = x.children.get(i);
        BTreeNode z = new BTreeNode();
        x.children.add(i + 1, z);
        x.keys.add(i, y.keys.get(t - 1));

        for (int j = 0; j < t - 1; j++) {
            z.keys.add(j, y.keys.get(j + t));
        }

        if (!y.isLeaf) {
            for (int j = 0; j < t; j++) {
                z.children.add(j, y.children.get(j + t));
            }
        }

        for (int j = y.keys.size() - 1; j >= t - 1; j--) {
            y.keys.remove(j);
        }

        x.isLeaf = false;
    }


    public void removeNode(int key) {
        if (root != null) {
            root = removeNodeRec(root, key);
        }
    }

 
    private BTreeNode removeNodeRec(BTreeNode root, int key) {
        if (root == null) {
            return root;
        }

        int index = findKeyIndex(root, key);

        if (index < root.keys.size() && root.keys.get(index) == key) {
            if (root.isLeaf) {
                root.keys.remove(index);
            } else {
                BTreeNode predecessor = getPredecessor(root, index);
                int predecessorKey = predecessor.keys.get(predecessor.keys.size() - 1);
                root.keys.set(index, predecessorKey);
                root.children.set(index, removeNodeRec(root.children.get(index), predecessorKey));
            }
        } else {
            boolean lastChild = (index == root.keys.size());
            if (root.children.get(index).keys.size() < t) {
                fillChild(root, index);
            }

            if (lastChild && index > root.keys.size()) {
                root.children.get(index - 1).keys.remove(root.children.get(index - 1).keys.size() - 1);
            } else {
                root.children.set(index, removeNodeRec(root.children.get(index), key));
            }
        }


        if (root.keys.isEmpty() && !root.isLeaf) {
            root = root.children.get(0);
        }

        return root;
    }

 
    private int findKeyIndex(BTreeNode node, int key) {
        int index = 0;
        while (index < node.keys.size() && key > node.keys.get(index)) {
            index++;
        }
        return index;
    }


    private BTreeNode getPredecessor(BTreeNode node, int index) {
        BTreeNode current = node.children.get(index);
        while (!current.isLeaf) {
            current = current.children.get(current.keys.size());
        }
        return current;
    }


    private void fillChild(BTreeNode node, int index) {
        if (index != 0 && node.children.get(index - 1).keys.size() >= t) {
            borrowFromPrev(node, index);
        } else if (index != node.keys.size() && node.children.get(index + 1).keys.size() >= t) {
            borrowFromNext(node, index);
        } else {
            if (index != node.keys.size()) {
                merge(node, index);
            } else {
                merge(node, index - 1);
            }
        }
    }

    
    private void borrowFromPrev(BTreeNode node, int index) {
        BTreeNode child = node.children.get(index);
        BTreeNode sibling = node.children.get(index - 1);

        child.keys.add(0, node.keys.get(index - 1));
        if (!child.isLeaf) {
            child.children.add(0, sibling.children.get(sibling.children.size() - 1));
            sibling.children.remove(sibling.children.size() - 1);
        }

        node.keys.set(index - 1, sibling.keys.get(sibling.keys.size() - 1));
        sibling.keys.remove(sibling.keys.size() - 1);
    }

  
    private void borrowFromNext(BTreeNode node, int index) {
        BTreeNode child = node.children.get(index);
        BTreeNode sibling = node.children.get(index + 1);

        child.keys.add(node.keys.get(index));
        if (!child.isLeaf) {
            child.children.add(sibling.children.get(0));
            sibling.children.remove(0);
        }

        node.keys.set(index, sibling.keys.get(0));
        sibling.keys.remove(0);
    }

  
    private void merge(BTreeNode node, int index) {
        BTreeNode child = node.children.get(index);
        BTreeNode sibling = node.children.get(index + 1);

        child.keys.add(node.keys.get(index));

        for (int i = 0; i < sibling.keys.size(); i++) {
            child.keys.add(sibling.keys.get(i));
        }

        if (!child.isLeaf) {
            for (int i = 0; i < sibling.children.size(); i++) {
                child.children.add(sibling.children.get(i));
            }
        }

        node.keys.remove(index);
        node.children.remove(index + 1);
    }

  
    public void printTree(BTreeNode x, int level) {
        System.out.println("Nivel " + level + ": " + x.keys);

        if (!x.isLeaf) {
            for (BTreeNode child : x.children) {
                printTree(child, level + 1);
            }
        }
    }
    
   
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   
    public static BTree loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (BTree) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
