package com.excalibur.myBlog.utils.sql.metadata.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DatabaseMetaData {

    private String catalog;                         //database name
    private String schema;                          //schema name
    private Map<String, Entity> entityMap;          //map with entity names and objects
    private List<Property> properties;              //list of all entity properties in current catalog and schema

    public DatabaseMetaData() {
    }

    public DatabaseMetaData(Map<String, Entity> entityMap, String catalog, String schema) throws IllegalArgumentException {
        if (entityMap == null) throw new IllegalArgumentException("entityMap cannot be null");
        this.catalog = catalog;
        this.schema = schema;
        this.entityMap = entityMap;
        this.properties = new ArrayList<>();
        for (Entity entity : this.entityMap.values()) {
            this.properties.addAll(entity.getPropertyMap().values());
        }
    }

    public Map<String, Entity> getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map<String, Entity> entityMap) {
        this.entityMap = entityMap;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Entity getEntity(String name) {
        return this.entityMap.get(name);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<Property> getProperties(String name) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("Property name cannot be null");
        List<Property> entityProps = new ArrayList<>();
        for (Property property : this.properties) {
            if (name.equals(property.getName())) {
                entityProps.add(property);
            }
        }
        return entityProps;
    }

    public Collection<Entity> getEntities() {
        return this.entityMap.values();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Entity entity : entityMap.values()) {
            builder.append(entity).append("\n");
        }
        return builder.toString();
    }
}
