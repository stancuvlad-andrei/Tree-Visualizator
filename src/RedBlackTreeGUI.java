package trees;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RedBlackTreeGUI extends JFrame {
    private RedBlackTree rbTree;
    private JPanel treePanel;
    private JButton menuButton;
    private JButton insertButton;
    private JButton removeButton;
    private JTextField inputField;
    private JButton saveButton;
    private JButton loadButton;

    public RedBlackTreeGUI() {
        rbTree = new RedBlackTree();

        setTitle("Red-Black Tree GUI");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        treePanel = new JPanel();
        treePanel.setLayout(new BorderLayout());
        add(treePanel, BorderLayout.CENTER);

        menuButton = new JButton("Menu");
        insertButton = new JButton("Insert Node");
        removeButton = new JButton("Remove Node");
        inputField = new JTextField();
        saveButton = new JButton("Save Tree");
        loadButton = new JButton("Load Tree");
        
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuG();
            }
        });
        
        
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    if (!rbTree.contains(value)) {  // Check if the value is not already in the tree
                        rbTree.insertNode(value);
                        refreshTree();
                    } else {
                        JOptionPane.showMessageDialog(null, "Node with value " + value + " already exists in the tree.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
                }
            }
        });


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    rbTree.removeNode(value);
                    refreshTree();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
                }
            }
        });
        
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTreeToFile();
            }
        });

        
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTreeFromFile();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(1, 6));
        inputPanel.add(menuButton);
        inputPanel.add(insertButton);
        inputPanel.add(removeButton);
        inputPanel.add(inputField);
        inputPanel.add(saveButton);
        inputPanel.add(loadButton);

        add(inputPanel, BorderLayout.SOUTH);

        refreshTree();
    }

    private void refreshTree() {
        treePanel.removeAll();
        treePanel.add(new RedBlackTreePanel(rbTree.getRoot()), BorderLayout.CENTER);
        validate();
        repaint();
    }
    
    public void menuG() {
    	new MainMenuGUI();
        new MainMenuGUI().setVisible(true);
        this.dispose();
    }
    
    private void saveTreeToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            rbTree.saveToFile(fileToSave.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Tree saved to file: " + fileToSave.getAbsolutePath());
        }
    }

    private void loadTreeFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            RedBlackTree loadedTree = RedBlackTree.loadFromFile(fileToLoad.getAbsolutePath());
            if (loadedTree != null) {
                rbTree = loadedTree;
                refreshTree();
                JOptionPane.showMessageDialog(this, "Tree loaded from file: " + fileToLoad.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(this, "Error loading tree from file.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RedBlackTreeGUI().setVisible(true);
            }
        });
    }
}
