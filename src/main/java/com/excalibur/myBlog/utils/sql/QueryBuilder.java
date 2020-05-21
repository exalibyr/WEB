package com.excalibur.myBlog.utils.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryBuilder {

    private static void setCondition(StringBuilder builder, SQLCondition condition) {
        if (condition != null) {
            builder
                    .append(" WHERE ").append(condition.getField())
                    .append(" ").append(condition.getOperator()).append(" ")
                    .append(condition.getValueAsString());
        }
    }

    /*******************************************************************************
     *  Name            : buildUpdateQuery(String entity, Map<String, String> properties, SQLCondition condition)
     *  Summary         : returns an update query with one condition, ready for use
     *  CreatedDate     : 16.03.2020
     *  Parameters      : String entity - table name to update,
     *                  : Map<String, String> properties - Map with field name as key and field value as value,
     *                  : SQLCondition condition - object with condition description (if null, not applied)
     *  Returns         : String - query
     ******************************************************************************/
    public static String buildUpdateQuery(String entity, Map<String, String> properties, SQLCondition condition) throws IllegalArgumentException {
        // validate params
        if (entity == null) {
            throw new IllegalArgumentException("UPDATE operation must have entity");
        } else if (properties == null || properties.isEmpty()){
            throw new IllegalArgumentException("UPDATE operation must have at least one property to be set");
        }
        //build query
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(entity).append(" SET ");
        for(String propertyName : properties.keySet()) {
            builder.append(propertyName).append(" = '").append(properties.get(propertyName)).append("', ");
        }
        // remove last redundant ", "
        int length = builder.length();
        builder.delete(length - 2, length);
        //set condition
        setCondition(builder, condition);
        //return query
        return builder.append(";").toString();
    }

    /*******************************************************************************
     *  Name            : buildSelectQuery(String entity, List<String> propertyNames, SQLCondition condition)
     *  Summary         : returns a select query with one condition, ready for use
     *  CreatedDate     : 16.03.2020
     *  Parameters      : String entity - table name to select from,
     *                  : List<String> propertyNames - field names to select (if null, all fields selected),
     *                  : SQLCondition condition - object with condition description (if null, not applied)
     *  Returns         : String - query
     ******************************************************************************/
    public static String buildSelectQuery(String entity, List<String> propertyNames, SQLCondition condition) throws IllegalArgumentException {
        // validate params
        if (entity == null) {
            throw new IllegalArgumentException("SELECT operation must have at least one entity");
        }
        // build query
        StringBuilder builder = new StringBuilder();
        if (propertyNames == null || propertyNames.isEmpty()) {
            builder.append("SELECT * FROM ").append(entity);
        } else {
            builder.append("SELECT ").append(String.join(", ", propertyNames)).append(" FROM ").append(entity);
        }
        //set condition
        setCondition(builder, condition);
        //return query
        return builder.append(";").toString();
    }

    /*******************************************************************************
     *  Name            : buildInsertQuery(String entity, Map<String, String> properties)
     *  Summary         : returns an insert query, ready for use
     *  CreatedDate     : 16.03.2020
     *  Parameters      : String entity - table name to insert into,
     *                  : Map<String, String> properties - Map with field name as key and field value as value
     *  Returns         : String - query
     ******************************************************************************/
    public static String buildInsertQuery(String entity, Map<String, String> properties) throws IllegalArgumentException {
        // validate params
        if (entity == null) {
            throw new IllegalArgumentException("INSERT operation must have entity");
        } else if (properties == null || properties.isEmpty()){
            throw new IllegalArgumentException("INSERT operation must have at least one property to be set");
        }
        //build query
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(entity).append(" (").append(String.join(", ", properties.keySet())).append(")").append(" VALUES (");
        for(String propertyName : properties.keySet()) {
            builder.append("'").append(properties.get(propertyName)).append("', ");
        }
        // remove last redundant ", "
        int length = builder.length();
        return builder.delete(length - 2, length).append(") RETURNING id;").toString();
    }

    //TODO: доработать метод
    public static String buildSelectQuery(Set<String> entities, Map<String, List<String>> entityPropertyNames) throws IllegalArgumentException {
        // validate params
        if (entities == null || entities.isEmpty()) {
            throw new IllegalArgumentException("SELECT operation must have at least one entity");
        }
        // build query
        Map<String, String> entityAlias = new HashMap<>();
        int i = 1;
        for (String entity : entities) {
            entityAlias.put(entity, "e" + i);
            i++;
        }
        StringBuilder selectClauseBuilder = new StringBuilder();
        StringBuilder fromClauseBuilder = new StringBuilder();
        selectClauseBuilder.append("SELECT ");
        fromClauseBuilder.append(" FROM ");
        if (entityPropertyNames == null || entityPropertyNames.isEmpty()) {
            selectClauseBuilder.append("*");
            for (String entity : entityAlias.keySet()) {
                fromClauseBuilder.append(entity).append(" ").append(entityAlias.get(entity)).append(", ");
            }
        } else {
            for (String entity : entityAlias.keySet()) {
                for (String propertyName : entityPropertyNames.get(entity)) {
                    selectClauseBuilder.append(entityAlias.get(entity)).append(".").append(propertyName).append(", ");
                }
                fromClauseBuilder.append(entity).append(" ").append(entityAlias.get(entity)).append(", ");
            }
        }
        int length = selectClauseBuilder.length();
        selectClauseBuilder.delete(length - 2, length);
        length = fromClauseBuilder.length();
        fromClauseBuilder.delete(length - 2, length);
        return selectClauseBuilder.append("\n").toString() + fromClauseBuilder.append(";").toString();
    }

//    public static String buildUpdateQuery(String entity, Map<String, String> properties, Map<String, String> conditions) throws IllegalArgumentException {
//        // validate params
//        if (entity == null || entity.isBlank()) {
//            throw new IllegalArgumentException("UPDATE operation must have entity");
//        } else if (properties == null || properties.isEmpty()){
//            throw new IllegalArgumentException("UPDATE operation must have at least one property to be set");
//        }
//        //build query
//        StringBuilder builder = new StringBuilder();
//        builder.append("UPDATE ").append(entity).append(" SET ");
//        for(String propertyName : properties.keySet()) {
//            builder.append(propertyName).append(" = '").append(properties.get(propertyName)).append("', ");
//        }
//        int length = builder.length();
//        builder.delete(length - 2, length);
//        //TODO: доработать фильтрацию
//        applyFilter(builder, conditions);
//        return builder.append(";").toString();
//    }
//
//    public static String buildSelectQuery(String entity, List<String> propertyNames, Map<String, String> conditions) throws IllegalArgumentException {
//        // validate params
//        if (entity == null || entity.isBlank()) {
//            throw new IllegalArgumentException("SELECT operation must have at least one entity");
//        }
//        // build query
//        StringBuilder builder = new StringBuilder();
//        if (propertyNames == null || propertyNames.isEmpty()) {
//            builder.append("SELECT * FROM ").append(entity);
//        } else {
//            builder.append("SELECT ").append(String.join(", ", propertyNames)).append(" FROM ").append(entity);
//        }
//        //TODO: доработать фильтрацию
//        applyFilter(builder, conditions);
//        return builder.append(";").toString();
//    }

//    //TODO: доработать метод
//    public static String buildSelectQuery(Set<String> entities, Map<String, List<String>> entityPropertyNames, Map<String, List<SQLCondition>> entityConditions) throws IllegalArgumentException {
//        // validate params
//        if (entities == null || entities.isEmpty()) {
//            throw new IllegalArgumentException("SELECT operation must have at least one entity");
//        }
//        // build query
//        Map<String, String> entityAlias = new HashMap<>();
//        int i = 1;
//        for (String entity : entities) {
//            entityAlias.put(entity, "e" + i);
//            i++;
//        }
//        StringBuilder selectClauseBuilder = new StringBuilder();
//        StringBuilder fromClauseBuilder = new StringBuilder();
//        StringBuilder whereClauseBuilder = new StringBuilder();
//        selectClauseBuilder.append("SELECT ");
//        fromClauseBuilder.append(" FROM ");
//        whereClauseBuilder.append(" WHERE ");
//        if (entityPropertyNames == null || entityPropertyNames.isEmpty()) {
//            selectClauseBuilder.append("*");
//            for (String entity : entityAlias.keySet()) {
//                fromClauseBuilder.append(entity).append(" ").append(entityAlias.get(entity)).append(", ");
//                if (entityConditions.get(entity) != null) {
//                    for(SQLCondition sqlCondition : entityConditions.get(entity)) {
//                        whereClauseBuilder
//                                .append(entityAlias.get(entity)).append(".").append(sqlCondition.getField())
//                                .append(" ").append(sqlCondition.getOperator());
//                        if (sqlCondition.getValue().getOwnerEntity() == null) {
//                            whereClauseBuilder.append(" '").append(sqlCondition.getValue().getValue()).append("' \nAND ");
//                        } else {
//                            whereClauseBuilder
//                                    .append(" ").append(entityAlias.get(sqlCondition.getValue().getOwnerEntity()))
//                                    .append(".").append(sqlCondition.getValue().getValue()).append(" \nAND ");
//                        }
//                    }
//                }
//            }
//        } else {
//            for (String entity : entityAlias.keySet()) {
//                for (String propertyName : entityPropertyNames.get(entity)) {
//                    selectClauseBuilder.append(entityAlias.get(entity)).append(".").append(propertyName).append(", ");
//                }
//                fromClauseBuilder.append(entity).append(" ").append(entityAlias.get(entity)).append(", ");
//                if (entityConditions.get(entity) != null) {
//                    for(SQLCondition sqlCondition : entityConditions.get(entity)) {
//                        whereClauseBuilder
//                                .append(entityAlias.get(entity)).append(".").append(sqlCondition.getField())
//                                .append(" ").append(sqlCondition.getOperator());
//                        if (sqlCondition.getValue().getOwnerEntity() == null) {
//                            whereClauseBuilder.append(" '").append(sqlCondition.getValue().getValue()).append("' \nAND ");
//                        } else {
//                            whereClauseBuilder
//                                    .append(" ").append(entityAlias.get(sqlCondition.getValue().getOwnerEntity()))
//                                    .append(".").append(sqlCondition.getValue().getValue()).append(" \nAND ");
//                        }
//                    }
//                }
//            }
//        }
//        int length = selectClauseBuilder.length();
//        selectClauseBuilder.delete(length - 2, length);
//        length = fromClauseBuilder.length();
//        fromClauseBuilder.delete(length - 2, length);
//        length = whereClauseBuilder.length();
//        whereClauseBuilder.delete(length - 6, length);
//        return selectClauseBuilder.append("\n").toString() + fromClauseBuilder.append("\n").toString() + whereClauseBuilder.append(";").toString();
//    }

//    //TODO: доработать метод
//    private static void applyFilter(StringBuilder builder, Map<String, String> conditions) {
//        if (conditions != null && !conditions.isEmpty()) {
//            builder.append(" WHERE ");
//            for(String conditionName : conditions.keySet()) {
//                builder.append(conditionName).append(" = '").append(conditions.get(conditionName)).append("', ");
//            }
//            int length = builder.length();
//            builder.delete(length - 2, length);
//        }
//    }

}
