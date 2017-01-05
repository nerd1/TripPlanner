package controller.admin;

import model.admin.CategoryConsumer;
import model.admin.DatabaseProxy;
import model.admin.FileReader;
import model.admin.PoiConsumer;
import view.admin.AdminView;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class ImportController {

    private File file;
    private AdminView adminView;
    private LinkedList<String> rowQueue = new LinkedList<String>();
    private long rowQueueCount = -1;
    private long counter = 0;

    public ImportController(File file, AdminView adminView) {
        this.file = file;
        this.adminView = adminView;
    }

    public void start() {

        FileReader fileReader = new FileReader(file, this);
        fileReader.start();

        DatabaseProxy databaseProxy = new DatabaseProxy();

        if (adminView.getFileType() == "poi") {
            PoiConsumer poiConsumer = new PoiConsumer(this, databaseProxy);
            poiConsumer.start();
        } else {
            CategoryConsumer categoryConsumer = new CategoryConsumer(this, databaseProxy);
            categoryConsumer.start();
        }

    }

    public synchronized void putRow(String s) {
        rowQueue.addLast(s);
        notify();
    }

    public synchronized String getRow() {
        while (queueIsEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        String row = rowQueue.getFirst();
        rowQueue.removeFirst();
        counter++;
        return row;
    }

    public boolean queueIsEmpty() {
        return rowQueue.isEmpty();
    }

    public void increaseRowQueueCount() {
        if (rowQueueCount < 0) {
            rowQueueCount = 0;
        }
        rowQueueCount++;
    }

    public boolean allRowsProcessed() {
        return counter == rowQueueCount;
    }
}
