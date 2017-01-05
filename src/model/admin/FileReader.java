package model.admin;

import controller.admin.ImportController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class FileReader extends Thread {

    private File file;
    private ImportController importController;

    public FileReader(File file, ImportController importController) {
        this.file = file;
        this.importController = importController;
    }

    public void run() {

        try {
            Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8);
            for (String line : (Iterable<String>) lines::iterator) {
                importController.putRow(line);
                importController.increaseRowQueueCount();
            }
            lines.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}