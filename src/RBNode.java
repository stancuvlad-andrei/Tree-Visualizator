package trees;
import trees.Color;
import java.io.Serializable;

enum Color {
    RED, BLACK
}

class RBNode implements Serializable {
    int key;
    RBNode parent, left, right;
    Color color;

    public RBNode(int key) {
        this.key = key;
        this.parent = this.left = this.right = null;
        this.color = Color.RED;
    }
}