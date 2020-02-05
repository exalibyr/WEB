package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.repository.Extended.ExtendedFileRepository;
import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;

import java.sql.*;

public class ExtendedFileRepositoryImpl implements ExtendedFileRepository {
    @Override
    public int saveFile(File file) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO file (name, user_id)" +
                            " VALUES ('" + file.getName() + "', " + file.getUser().getId() + ")" +
                            " RETURNING id";
            System.out.println("Native SQL: " + query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new SQLException("Query returned no rows");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
