package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.repository.Extended.ExtendedPublicationRepository;
import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;
import com.excalibur.myBlog.repository.utils.JDBCFactory;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.Optional;

public class ExtendedPublicationRepositoryImpl implements ExtendedPublicationRepository {

    @Override
    public Optional<Publication> findByIdWithFiles(Integer publicationId) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
            Statement statement = connection.createStatement();
            String query = "SELECT p.id, p.date_time, p.title, p.content, p.user_id " +
                    "FROM publication p, publication_file pf, file f";
            System.out.println("Native SQL: " + query);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return null;
            } else {
                throw new SQLException("Query returned no rows");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResultSet findLatest() throws SQLException {
        String query = "SELECT * " +
                "FROM publication p, publication_file pf, file f " +
                "WHERE p.id = pf.publication_id" +
                "AND pf.file_id = f.id" +
                "LIMIT 20";
        try (Connection connection = JDBCFactory.createConnection()) {
            Statement statement = connection.createStatement();
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            System.out.println("Native SQL: " + query);
            rowSet.populate(statement.executeQuery(query));
            statement.close();
            return rowSet;
        }
    }
}
