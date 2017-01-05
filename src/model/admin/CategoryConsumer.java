package model.admin;

import controller.admin.ImportController;

import java.util.ArrayList;

/**
 * Created by dieterbiedermann on 05.01.17.
 */
public class CategoryConsumer extends Thread {

    private ImportController importController;
    private DatabaseImport databaseImport;
    private ArrayList<String[]> categoryList;

    public CategoryConsumer(ImportController importController, DatabaseProxy databaseProxy) {
        this.importController = importController;
        this.databaseImport = new DatabaseImport(importController, databaseProxy);
        this.categoryList = new ArrayList<>();
    }

    public void run() {
        while (!importController.allRowsProcessed()) {
            String row = importController.getRow();
            if (row != null) {
                String[] rowItem = row.split(",");
                if (rowItem.length != 2 || rowItem[0].isEmpty()) {
                    System.out.println("Error -> wrong row");
                } else {

                    System.out.println(this.getName() + " -> " + row);
                    categoryList.add(rowItem);

                    if (categoryList.size() >= 1000) {
                        databaseImport.insertMultiValueCategories(categoryList);
                        categoryList.clear();
                    }
                }

            }
        }

        if (categoryList.size() > 0) {
            databaseImport.insertMultiValueCategories(categoryList);
            categoryList.clear();
        }

    }

}