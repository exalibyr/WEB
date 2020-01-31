package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;
import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.Extended.ExtendedUserRepository;
import java.sql.*;

public class ExtendedUserRepositoryImpl implements ExtendedUserRepository {

    @Override
    public Integer saveUser(User user) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO user_data (name, surname, about, username)" +
                            " VALUES ('" + user.getName() + "', '" + user.getSurname() + "', '" + user.getAbout() + "', '" + user.getUsername() + "')" +
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


    @Override
    public Integer saveUserRoles(User user) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
            Statement statement = connection.createStatement();
            String query;
            System.out.println("************* BATCH PACKAGE BEGIN ****************");
            for (Role role : user.getRoles()) {
                query = "INSERT INTO user_role (user_id, role_id) " +
                        "VALUES (" + user.getId() + ", " + role.getId() + ")";
                System.out.println("Native SQL: " + query);
                statement.addBatch(query);
            }
            int rows = statement.executeBatch().length;
            System.out.println("************* BATCH PACKAGE END ****************");
            if (rows != user.getRoles().size()) {
                throw new RuntimeException("Batch insert failed");
            } else {
                return rows;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Integer updateUser(User user) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
            Statement statement = connection.createStatement();
            String query = "UPDATE user_data " +
                            "SET name = '" + user.getName() +
                            "', surname = '" + user.getSurname() +
                            "', about = '" + user.getAbout() +
                            "' WHERE id = " + user.getId();
            System.out.println("Native SQL: " + query);
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
