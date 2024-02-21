package trees;

import java.io.Serializable;

public class TreeNode implements Serializable {
    int key;
    TreeNode left, right;

    public TreeNode(int item) {
        key = item;
        left = right = null;
    }
}
