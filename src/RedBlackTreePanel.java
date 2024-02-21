package trees;

import javax.swing.*;
import java.awt.*;

public class RedBlackTreePanel extends JPanel {
    private RBNode root;
    private static final int HORIZONTAL_SPACING = 150;
    private static final int VERTICAL_SPACING = 70;

    public RedBlackTreePanel(RBNode root) {
        this.root = root;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, getWidth() / 2, 30, root, HORIZONTAL_SPACING, VERTICAL_SPACING);
    }

    private void drawTree(Graphics g, int x, int y, RBNode node, int xOffset, int yOffset) {
        if (node != null) {
            java.awt.Color originalColor = g.getColor(); 

            if (node.color == Color.RED) {
                g.setColor(java.awt.Color.RED);
            } else {
                g.setColor(java.awt.Color.BLACK);
            }

            g.drawOval(x - 15, y - 15, 30, 30);
            g.drawString(Integer.toString(node.key), x - 5, y + 5);

            if (node.left != null) {
                int childX = x - xOffset;
                int childY = y + yOffset;
                drawTree(g, childX, childY, node.left, xOffset / 2, yOffset);
                g.drawLine(x, y + 15, childX, childY - 15);
            }

            if (node.right != null) {
                int childX = x + xOffset;
                int childY = y + yOffset;
                drawTree(g, childX, childY, node.right, xOffset / 2, yOffset);
                g.drawLine(x, y + 15, childX, childY - 15);
            }

            g.setColor(originalColor);
        }
    }
}
