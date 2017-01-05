package model.admin;

import controller.admin.ImportController;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class DatabaseImport {

    ImportController importController;
    DatabaseProxy databaseProxy;
    PreparedStatement preparedStatement;
    int counter = 0;

    public DatabaseImport(ImportController importController, DatabaseProxy databaseProxy) {
        try {
            this.databaseProxy = databaseProxy;
            this.importController = importController;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insertMultiValuePois(ArrayList<String[]> poiList) {

        try {
            databaseProxy.setAutoCommit(false);

            StringBuilder sb;
            sb = new StringBuilder();
            sb.append("insert into poi (category, id, latitude, longitude, name) values ");

            for (int i = 1; i <= poiList.size(); i++) {
                sb.append("(?,?,?,?,?),");
            }
            sb.delete(sb.length()-1,sb.length());
            preparedStatement = databaseProxy.prepareStatement(sb.toString());

            int cnt = 0;
            for (String[] poi:poiList) {
                for (String item: poi) {
                    cnt++;
                    preparedStatement.setString(cnt, item);
                }
            }

            counter++;
            preparedStatement.execute();
            if (counter % 50 == 0) {
                databaseProxy.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {

                    if (counter > 0) {
                        System.out.println(Thread.currentThread().getName() + " - " + preparedStatement.toString());
                        databaseProxy.commit();
                    }
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("poiList error queue");
                }
            }
        }

    }

    public void insertMultiValueCategories(ArrayList<String[]> categoryList) {
        try {
            databaseProxy.setAutoCommit(false);

            StringBuilder sb;
            sb = new StringBuilder();
            sb.append("insert into poi_category (name, id) values ");

            for (int i = 1; i <= categoryList.size(); i++) {
                sb.append("(?,?),");
            }
            sb.delete(sb.length()-1,sb.length());
            preparedStatement = databaseProxy.prepareStatement(sb.toString());

            int cnt = 0;
            for (String[] category:categoryList) {
                for (String item: category) {
                    cnt++;
                    preparedStatement.setString(cnt, item);
                }
            }

            counter++;
            preparedStatement.execute();
            if (counter % 50 == 0) {
                databaseProxy.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {

                    if (counter > 0) {
                        System.out.println(Thread.currentThread().getName() + " - " + preparedStatement.toString());
                        databaseProxy.commit();
                    }
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("categoryList error queue");
                }
            }
        }
    }
}
