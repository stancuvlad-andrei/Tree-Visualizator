package trees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenuGUI extends JFrame {
    private JPanel mainPanel;
    private JButton binaryTreeButton;
    private JButton redBlackTreeButton;
    private JButton bTreeButton;
    

    public MainMenuGUI() {
        setTitle("Meniu Principal");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        binaryTreeButton = new JButton("Arbore Binar");
        redBlackTreeButton = new JButton("Arbore Roșu-Negru");
        bTreeButton = new JButton("Arbore B");

        mainPanel.add(binaryTreeButton);
        mainPanel.add(redBlackTreeButton);
        mainPanel.add(bTreeButton);

        binaryTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	biTreeG();
            }
        });

        redBlackTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	rDTreeG();
            }
        });
        
        bTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bTreeG();
            }
        });

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }
    

    public void biTreeG() {
    	new BinaryTreeGUI();
        new BinaryTreeGUI().setVisible(true);
        this.dispose();
    }
    

    public void rDTreeG() {
    	new RedBlackTreeGUI();
        new RedBlackTreeGUI().setVisible(true);
        this.dispose();
    }
    

    public void bTreeG() {
    	new BTreeGUI();
        new BTreeGUI().setVisible(true);
        this.dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenuGUI().setVisible(true);
            }
        });
    }
}
