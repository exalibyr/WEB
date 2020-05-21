package com.excalibur.myBlog.utils.sql.metadata.entity;

import java.util.*;

public class Entity {

    private String name;                                                //entity name
    private Map<String, Property> propertyMap = new HashMap<>();        //map with property names and property objects
    private List<Reference> references = new ArrayList<>();             //list of references to other entities

    public Entity(String name) {
        this.name = name;
    }

    public Entity(String name, Map<String, Property> propertyMap) throws IllegalArgumentException {
        if (propertyMap == null) throw new IllegalArgumentException("propertyMap cannot be null");
        this.name = name;
        this.propertyMap = propertyMap;
    }

    public Property getProperty(String name) {
        return this.propertyMap.get(name);
    }

    public void addReference(Reference reference) {
        this.references.add(reference);
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public void addProperty(String name, String typeName, int type) {
        this.propertyMap.put(
                name,
                new Property(
                        this,
                        name,
                        typeName,
                        type
                )
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Property> getProperties() {
        return this.propertyMap.values();
    }

    public Map<String, Property> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, Property> propertyMap) {
        this.propertyMap = propertyMap;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for (Property property : propertyMap.values()) {
            builder.append("\t\t").append(property).append("\n");
        }
        StringBuilder builder1 = new StringBuilder();
        builder1.append("\n");
        for (Reference reference : references) {
            builder1.append("\t\t").append(reference).append("\n");
        }
        return "Entity{\n\t" +
                "name='" + name + "\'" +
                ", \n\tpropertyMap=" + builder.toString() +
                ", \n\treferences=" + builder1.toString() +
                '}';
    }
}
