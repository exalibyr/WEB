package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;
import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.Extended.ExtendedUserRepository;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class ExtendedUserRepositoryImpl implements ExtendedUserRepository {

    @Override
    public ResultSet findByNameSurname(String name, String surname) {
        String query = "SELECT id, name, surname, about, avatar FROM user_ WHERE name LIKE '" + name + "%' AND surname LIKE '" + surname + "%'";
        System.out.println("Native SQL: " + query);
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
            Statement statement = connection.createStatement();
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(statement.executeQuery(query));
            statement.close();
            return rowSet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer saveUser(User user) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfiguration.getDatabaseURL(), DatabaseConfiguration.getDatabaseLogin(), DatabaseConfiguration.getDatabasePassword())) {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO user_ (name, surname, about, username)" +
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
                throw new SQLException("Batch insert failed");
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
            String query = "UPDATE user_ " +
                            "SET name = '" + user.getName() +
                            "', surname = '" + user.getSurname() +
                            "', about = '" + user.getAbout() +
                            "' , avatar = '" + user.getAvatar() +
                            "' WHERE id = " + user.getId();
            System.out.println("Native SQL: " + query);
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
