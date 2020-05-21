package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.repository.Extended.ExtendedFileRepository;
import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;

import javax.validation.constraints.NotNull;
import java.sql.*;

public class ExtendedFileRepositoryImpl implements ExtendedFileRepository {

    @Override
    public int saveFile(@NotNull File file, @NotNull Integer publicationId, @NotNull Integer userId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfiguration.getDatabaseURL(),
                DatabaseConfiguration.getDatabaseLogin(),
                DatabaseConfiguration.getDatabasePassword()
        )) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String query = "INSERT INTO file (name, user_id)" +
                    " VALUES ('" + file.getName() + "', " + userId + ")" +
                    " RETURNING id";
            System.out.println("Native SQL: " + query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int fileId =  resultSet.getInt("id");
                query = "INSERT INTO publication_file (publication_id, file_id)" +
                        " VALUES (" + publicationId + ", " + fileId + ")";
                System.out.println("Native SQL: " + query);
                Statement statement1 = connection.createStatement();
                statement1.execute(query);
                connection.commit();
                return fileId;
            } else {
                throw new SQLException("Query returned no rows");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
