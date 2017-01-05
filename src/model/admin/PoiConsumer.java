package model.admin;

import controller.admin.ImportController;
import model.admin.DatabaseImport;
import model.admin.DatabaseProxy;

import java.util.ArrayList;

/**
 * Created by dieterbiedermann on 05.01.17.
 */
public class PoiConsumer extends Thread {

    private ImportController importController;
    private DatabaseImport databaseImport;
    private ArrayList<String[]> poiList;

    public PoiConsumer(ImportController importController, DatabaseProxy databaseProxy) {
        this.importController = importController;
        this.databaseImport = new DatabaseImport(importController, databaseProxy);
        this.poiList = new ArrayList<>();
    }

    public void run() {
        while (!importController.allRowsProcessed()) {
            String row = importController.getRow();
            if (row != null) {
                String[] rowItem = row.split("\\|");
                if (rowItem.length < 4) {
                    System.out.println("Error -> wrong row");
                } else {

                    System.out.println(this.getName() + " -> " + row);
                    poiList.add(rowItem);

                    if (poiList.size() >= 2000) {
                        databaseImport.insertMultiValuePois(poiList);
                        poiList.clear();
                    }
                }

            }
        }

        if (poiList.size() > 0) {
            databaseImport.insertMultiValuePois(poiList);
            poiList.clear();
        }

    }

}