package controller.admin;

import view.admin.AdminView;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class AdminController implements ActionListener {

    private AdminView adminView;
    private File file;

    public AdminController(AdminView adminView) {
        this.adminView = adminView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "open_file":
                /**
                 * Öffne den File Chooser Dialog und zeige den Filenamen in der View an
                 */
                file = getFile();
                if (file != null) {
                    adminView.setFileName(file.getName());
                }
                break;
            case "import_file":
                if (file == null) {
                    /**
                     * Fehlermeldung zurückgeben
                     */
                } else {
                    /**
                     * Importiere das File
                     */
                    ImportController importController = new ImportController(file, adminView);
                    importController.start();
                }
                break;
        }

    }

    public File getFile() {
        JFileChooser fileChooser = new JFileChooser();

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith("csv");
            }

            @Override
            public String getDescription() {
                return "CSV Datei";
            }
        };
        fileChooser.setFileFilter(filter);

        fileChooser.setMultiSelectionEnabled(false);
        int returnCode = fileChooser.showOpenDialog(adminView);
        if (returnCode == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }


}
