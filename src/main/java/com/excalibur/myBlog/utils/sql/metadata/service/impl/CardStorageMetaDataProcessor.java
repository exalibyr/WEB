package com.excalibur.myBlog.utils.sql.metadata.service.impl;

import com.excalibur.myBlog.repository.configuration.DatabaseConfiguration;
import com.excalibur.myBlog.utils.sql.metadata.MetaDataType;
import com.excalibur.myBlog.utils.sql.metadata.dao.MetaDataDAO;
import com.excalibur.myBlog.utils.sql.metadata.dao.impl.CardStorageMetaDataDAO;
import com.excalibur.myBlog.utils.sql.metadata.entity.DatabaseMetaData;
import com.excalibur.myBlog.utils.sql.metadata.entity.Entity;
import com.excalibur.myBlog.utils.sql.metadata.entity.Property;
import com.excalibur.myBlog.utils.sql.metadata.entity.Reference;
import com.excalibur.myBlog.utils.sql.metadata.service.MetaDataProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CardStorageMetaDataProcessor implements MetaDataProcessor {

    private MetaDataDAO metaDataDAO = new CardStorageMetaDataDAO();

    /*******************************************************************************
     *  Name            : getMetaData(String catalog, String schema)
     *  Summary         : returns structured metadata from database
     *  CreatedDate     : 19.03.2020
     *  Parameters      : String catalog - DB name, String schema - schema name
     *  Returns         : DatabaseMetadata - object with structured metadata
     ******************************************************************************/
    @Override
    public DatabaseMetaData getMetaData(String catalog, String schema) throws SQLException, UnsupportedOperationException {
        //get properties metadata from DB
        ResultSet columns = this.metaDataDAO.selectMetadata(catalog, schema, MetaDataType.columns);
        //get references metadata from DB
        ResultSet references = this.metaDataDAO.selectMetadata(catalog, schema, MetaDataType.references);
        Map<String, Entity> entityMap = new HashMap<>();
        while (columns.next()) {
            String entityName = columns.getString(DatabaseConfiguration.TABLE_NAME_LABEL);
            String propertyName = columns.getString(DatabaseConfiguration.COLUMN_NAME_LABEL);
            //create new entity if if not exists
            Entity entity = entityMap.get(entityName);
            if (entity == null) {
                entity = new Entity(entityName);
                entityMap.put(entityName, entity);
            }
            //create new property if it not exists
            Property property = entity.getProperty(propertyName);
            if (property == null) {
                entity.addProperty(
                        propertyName,
                        columns.getString(DatabaseConfiguration.TYPE_NAME_LABEL),
                        columns.getInt(DatabaseConfiguration.DATA_TYPE_LABEL)
                );
            }
        }
        while (references.next()) {
            // get metadata from row
            String entityName = references.getString(DatabaseConfiguration.FK_TABLE_NAME_LABEL);
            String propertyName = references.getString(DatabaseConfiguration.FK_COLUMN_NAME_LABEL);
            String referencedEntityName = references.getString(DatabaseConfiguration.PK_TABLE_NAME_LABEL);
            String referencedPropertyName = references.getString(DatabaseConfiguration.PK_COLUMN_NAME_LABEL);

            //create entities and properties from metadata
            Entity entity = entityMap.get(entityName);
            Property property = entity.getProperty(propertyName);
            Entity referencedEntity = entityMap.get(referencedEntityName);
            Property referencedProperty = referencedEntity.getProperty(referencedPropertyName);

            //create references to referenced entity and property
            Reference reference = new Reference(referencedEntity, referencedProperty, false);
            entity.addReference(reference);
            property.addReference(reference);

            //create reverse references to entity and property
            Reference reverseRef = new Reference(entity, property, true);
            referencedEntity.addReference(reverseRef);
            referencedProperty.addReference(reverseRef);
        }

        //create metadata object
        DatabaseMetaData databaseMetadata = new DatabaseMetaData();
        databaseMetadata.setCatalog(catalog);
        databaseMetadata.setSchema(schema);
        databaseMetadata.setEntityMap(entityMap);
        return databaseMetadata;
    }
}
