package org.ylab.melnikov.lesson3.orgstructure;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Nikolay Melnikov
 */
public class App {
    public static void main(String[] args) {

        App app = new App();

        File csvFile;
        try {
            csvFile = app.getFileFromResource("sample.csv");
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }

        OrgStructureParser parser = file -> {
            Map<Long, Employee> employees = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine();
                while (reader.ready()) {
                    String[] data = reader.readLine().split(";");
                    Employee employee = new Employee();
                    employee.setId(Long.parseLong(data[0]));

                    if (!data[1].isEmpty()) {
                        employee.setBossId(Long.parseLong(data[1]));
                    } else {
                        employee.setBossId(null);
                    }
                    employee.setName(data[2]);
                    employee.setPosition(data[3]);

                    employees.put(employee.getId(), employee);
                }
                for (Map.Entry<Long, Employee> entry : employees.entrySet()) {
                    Employee current = entry.getValue();
                    current.setBoss(employees.get(current.getBossId()));
                    if (!Objects.isNull(current.getBossId())) {
                        employees.get(current.getBossId()).getSubordinate().add(current);
                    }
                }
            }
            return employees.get(1L);
        };

        try {
            Employee employee = parser.parseStructure(csvFile);
            System.out.println(employee.getName() + " " + employee.getPosition());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}
