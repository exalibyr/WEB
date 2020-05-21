package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.dao.Publication;
import com.excalibur.myBlog.repository.Extended.ExtendedPublicationRepository;
import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;

import java.sql.*;
import java.util.Optional;

//public class ExtendedPublicationRepositoryImpl implements ExtendedPublicationRepository {
//
//    @Override
//    public Optional<Publication> findByIdWithFiles(Integer publicationId) {
//        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
//            Statement statement = connection.createStatement();
//            String query = "SELECT p.id, p.date_time, p.title, p.content, p.user_id " +
//                    "FROM publication p, publication_file pf, file f";
//            System.out.println("Native SQL: " + query);
//            ResultSet resultSet = statement.executeQuery(query);
//            if (resultSet.next()) {
//                return resultSet.getInt("id");
//            } else {
//                throw new SQLException("Query returned no rows");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//}
