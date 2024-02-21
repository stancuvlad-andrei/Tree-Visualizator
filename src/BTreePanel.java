package trees;

import javax.swing.*;
import java.awt.*;

class BTreePanel extends JPanel {
    private BTree bTree;

    public BTreePanel(BTree bTree) {
        this.bTree = bTree;
    }

    public void setBTree(BTree bTree) {
        this.bTree = bTree;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bTree != null && bTree.getRoot() != null) {
            drawTree(g, getWidth() / 2, 30, bTree.getRoot(), 50, 70);
        }
    }

    private void drawTree(Graphics g, int x, int y, BTreeNode node, int xOffset, int yOffset) {
        g.drawRect(x - 15, y - 15, 30 + node.getNumKeys() * 10, 30);
        g.drawString(node.keys.toString(), x - 5, y + 5);

        if (!node.isLeaf) {
            int childX = x - xOffset;
            int childY = y + yOffset;
            drawTree(g, childX, childY, node.getChild(0), xOffset / 2, yOffset);
            g.drawLine(x, y + 15, childX + 15, childY - 15);

            childX = x + xOffset;
            drawTree(g, childX, childY, node.getChild(1), xOffset / 2, yOffset);
            g.drawLine(x, y + 15, childX - 15, childY - 15);
        }
    }


}
