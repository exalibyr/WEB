package com.excalibur.myBlog.utils.sql.metadata.service;



import com.excalibur.myBlog.utils.sql.metadata.entity.DatabaseMetaData;

import java.sql.SQLException;

public interface MetaDataProcessor {

    DatabaseMetaData getMetaData(String catalog, String schema) throws SQLException;


}
