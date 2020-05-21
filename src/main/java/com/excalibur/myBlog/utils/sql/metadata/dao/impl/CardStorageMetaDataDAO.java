package com.excalibur.myBlog.utils.sql.metadata.dao.impl;

import com.excalibur.myBlog.repository.utils.JDBCFactory;
import com.excalibur.myBlog.utils.sql.metadata.MetaDataType;
import com.excalibur.myBlog.utils.sql.metadata.dao.MetaDataDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardStorageMetaDataDAO implements MetaDataDAO {

    private static final Logger LOGGER = LogManager.getLogger(MetaDataDAO.class);

    /*******************************************************************************
     *  Name            : selectMetadata(String catalog, String schema, DatabaseMetadata.Type type)
     *  Summary         : retrieves metadata from database
     *  CreatedDate     : 20.03.2020
     *  Parameters      : String catalog, String schema, DatabaseMetadata.Type type - type of metadata to retrieve
     *  Returns         : CachedRowSet
     ******************************************************************************/
    @Override
    public CachedRowSet selectMetadata(String catalog, String schema, MetaDataType type) throws SQLException, UnsupportedOperationException {
        try (Connection connection = JDBCFactory.createConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();
            ResultSet resultSet;
            switch (type) {
                case columns: {
                    resultSet = metaData.getColumns(catalog, schema, null, null);
                    break;
                }
                case references: {
                    resultSet = metaData.getExportedKeys(catalog, schema, null);
                    break;
                }
                default: throw new UnsupportedOperationException("Operation is not supported");
            }
            rowSet.populate(resultSet);
            resultSet.getStatement().close();
            return rowSet;
        } catch (SQLException e) {
            LOGGER.error(e, e);
            e.printStackTrace();
            throw new SQLException(e);
        } catch (UnsupportedOperationException e) {
            LOGGER.error(e, e);
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
