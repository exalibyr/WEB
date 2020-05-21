package com.excalibur.myBlog.repository.utils;

import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCFactory {

    private static final Logger LOGGER = LogManager.getLogger(JDBCFactory.class);

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() throws SQLException {
        LOGGER.debug("Opening connection to " + DatabaseConfiguration.getDatabaseURL());
        Connection connection = DriverManager.getConnection(
                DatabaseConfiguration.getDatabaseURL(),
                DatabaseConfiguration.getDatabaseLogin(),
                DatabaseConfiguration.getDatabasePassword()
        );
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }



}
