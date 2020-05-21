package com.excalibur.myBlog.utils.sql.metadata.dao;

import com.excalibur.myBlog.utils.sql.metadata.MetaDataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MetaDataDAO {

    ResultSet selectMetadata(String catalog, String schema, MetaDataType type) throws SQLException;

}
