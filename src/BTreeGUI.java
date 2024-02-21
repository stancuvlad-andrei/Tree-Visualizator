package trees;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BTreeGUI extends JFrame {
    private BTree bTree;
    private BTreePanel treePanel;
    private JTextField inputField;
    private JButton menuButton;
    private JButton createButton;
    private JButton insertButton;
    private JButton removeButton;
    private JTextField nodeField;
    private JLabel enterLabel;
    private JLabel gradeLabel;
    private JButton saveButton;
    private JButton loadButton;

    public BTreeGUI() {
        setTitle("B-Tree GUI");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        inputField = new JTextField(10);
        createButton = new JButton("Create B-Tree");
        enterLabel = new JLabel("Enter B-Tree degree: ");
        controlPanel.add(enterLabel);
        controlPanel.add(inputField);
        controlPanel.add(createButton);

        gradeLabel = new JLabel();
        gradeLabel.setVisible(false);
        controlPanel.add(gradeLabel);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int degree = Integer.parseInt(inputField.getText());
                    bTree = new BTree(degree);
                    createButton.setVisible(false);
                    inputField.setVisible(false);
                    enterLabel.setVisible(false);
                    menuButton.setVisible(true);
                    insertButton.setVisible(true);
                    removeButton.setVisible(true);
                    nodeField.setVisible(true);
                    saveButton.setVisible(true);
                    loadButton.setVisible(true);
                    gradeLabel.setText("B-Tree grade: " + degree);
                    gradeLabel.setVisible(true);
                    refreshTree();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
                }
            }
        });
        
        menuButton = new JButton("Menu");
        menuButton.setVisible(false);
        insertButton = new JButton("Insert Node");
        insertButton.setVisible(false);
        removeButton = new JButton("Remove Node");
        removeButton.setVisible(false);
        saveButton = new JButton("Save B-Tree");
        saveButton.setVisible(false);
        loadButton = new JButton("Load B-Tree");
        loadButton.setVisible(false);

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
                    int value = Integer.parseInt(nodeField.getText());
                    bTree.insert(value);
                    refreshTree();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
                }
            }
        });


        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(nodeField.getText());
                    
                    
                    if (bTree != null) {
                        bTree.removeNode(value);
                        refreshTree();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
                }
            }
        });
        
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBTreeToFile();
            }
        });

        
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBTreeFromFile();
            }
        });


        nodeField = new JTextField();
        nodeField.setVisible(false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(menuButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(nodeField);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        treePanel = new BTreePanel(null); 
        add(treePanel, BorderLayout.CENTER);
    }

    private void refreshTree() {
        treePanel.setBTree(bTree);
        validate();
        repaint();
    }
    
    public void menuG() {
    	new MainMenuGUI();
        new MainMenuGUI().setVisible(true);
        this.dispose();
    }
    
    private void saveBTreeToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            bTree.saveToFile(fileToSave.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "B-Tree saved to file: " + fileToSave.getAbsolutePath());
        }
    }

    private void loadBTreeFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            BTree loadedBTree = BTree.loadFromFile(fileToLoad.getAbsolutePath());
            if (loadedBTree != null) {
                bTree = loadedBTree;
                refreshTree();
                JOptionPane.showMessageDialog(this, "B-Tree loaded from file: " + fileToLoad.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(this, "Error loading B-Tree from file.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BTreeGUI().setVisible(true);
            }
        });
    }
}

