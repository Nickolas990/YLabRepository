package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    private final DataSource dataSource;

    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        StringBuilder result;
        String sql = "SELECT * FROM " + tableName + " LIMIT 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int cols = rsMetaData.getColumnCount();

            result = new StringBuilder();
            result.append("SELECT ");

            for (int i = 1; i <= cols; i++) {
                if (i < cols) {
                    result.append(rsMetaData.getColumnName(i));
                    result.append(", ");
                } else {
                    result.append(rsMetaData.getColumnName(i));
                }
            }
        }
        result.append(" FROM ");
        result.append(tableName);

        return result.toString();
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> result = null;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData meta = connection.getMetaData();
            result = new ArrayList<>();
            ResultSet rs = meta.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                result.add(rs.getString(3));
            }
        }
        return result;
    }
}
