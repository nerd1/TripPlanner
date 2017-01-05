package view.admin;

import controller.admin.AdminController;
import controller.admin.ImportController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class AdminView extends JFrame {

    private JButton chooseFileButton, startImportButton;
    private JLabel fileName;
    private ButtonGroup fileTypeGroup;
    private JRadioButton fileTypeCategory, fileTypePoi;

    public AdminView() {

        AdminController adminController = new AdminController(this);

        this.setLayout(new BorderLayout());
        JPanel gridPanel = new JPanel(new GridLayout(5,1));

        chooseFileButton = new JButton("Open file");
        chooseFileButton.setActionCommand("open_file");
        chooseFileButton.addActionListener(adminController);

        fileName = new JLabel();

        fileTypeCategory = new JRadioButton("Category", true);
        fileTypePoi = new JRadioButton("Point of interest");
        fileTypeGroup = new ButtonGroup();
        fileTypeGroup.add(fileTypeCategory);
        fileTypeGroup.add(fileTypePoi);

        startImportButton = new JButton("Import file");
        startImportButton.setActionCommand("import_file");
        startImportButton.addActionListener(adminController);

        gridPanel.add(chooseFileButton);
        gridPanel.add(fileName);
        gridPanel.add(fileTypeCategory);
        gridPanel.add(fileTypePoi);
        gridPanel.add(startImportButton);

        this.add(gridPanel, BorderLayout.NORTH);
        this.setTitle("TripPlanner - Administration");
        this.setSize(new Dimension(500,300));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        AdminView adminView = new AdminView();
        adminView.setVisible(true);
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    public String getFileType() {
        return fileTypeCategory.isSelected() ? "category" : "poi";
    }
}
