package trees;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

class BTreeNode implements Serializable{
    List<Integer> keys;
    List<BTreeNode> children;
    boolean isLeaf;

    public BTreeNode() {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.isLeaf = true;
    }
    
    public int getNumKeys() {
        return keys.size();
    }

    public BTreeNode getChild(int index) {
        if (index >= 0 && index < children.size()) {
            return children.get(index);
        }
        return null;
    }
}