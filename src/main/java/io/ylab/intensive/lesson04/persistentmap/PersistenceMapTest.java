package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    // Написать код демонстрации работы
    persistentMap.init("Race");

    persistentMap.put("first", "BMW");
    persistentMap.put("second", "McLauren");
    persistentMap.put("third", "AUDI");

    persistentMap.put("first", "AUDI");

    System.out.println(persistentMap.getKeys());

    System.out.println(persistentMap.containsKey("second"));
    System.out.println(persistentMap.containsKey("fourth"));

    System.out.println(persistentMap.containsKey("third"));
    persistentMap.remove("third");
    System.out.println(persistentMap.containsKey("third"));

    PersistentMap persistentMap1 = new PersistentMapImpl(dataSource);
    persistentMap1.init("tasks");

    persistentMap1.put("first", "Products");
    persistentMap1.put("second", "school");
    persistentMap1.put("third", "party");

    persistentMap.clear();

  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
