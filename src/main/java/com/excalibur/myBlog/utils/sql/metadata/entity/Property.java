package com.excalibur.myBlog.utils.sql.metadata.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Property {

    private Entity entity;                                          //owner entity object
    private String name;                                            //property name
    private String typeName;                                        //property datatype string
    private int type;                                               //property datatype integer
    private List<Reference> references = new ArrayList<>();         //list of references to other entities


    public Property(Entity entity, String name, String typeName, int type) {
        this.entity = entity;
        this.name = name;
        this.typeName = typeName;
        this.type = type;
    }

    public Set<String> getReferencedProperties() {
        Set<String> referencedPropertiesString = new HashSet<>();
        for (Reference reference : this.references) {
            referencedPropertiesString.add(
                    reference.getProperty().getEntity()
                            + "." + reference.getProperty().getName());
        }
        return referencedPropertiesString;
    }

    public Property getReferencedProperty(String entity, String name) throws IllegalArgumentException {
        if (entity == null || name == null) throw new IllegalArgumentException("Property or entity names cannot be null");
        for (Reference reference : this.references) {
            Property referencedProperty = reference.getProperty();
            if (entity.equals(referencedProperty.getEntity()) && name.equals(referencedProperty.getName())) {
                return referencedProperty;
            }
        }
        return null;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public Property addReference(Reference reference) {
        this.references.add(reference);
        return this;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder builder1 = new StringBuilder();
        builder1.append("\n");
        for (Reference reference : references) {
            builder1.append("\t\t\t\t").append(reference).append("\n");
        }
        return "Property{" +
                "entity name=" + entity.getName() +
                ", name='" + name + '\'' +
                ", typeName='" + typeName + '\'' +
                ", type=" + type +
                ", \n\t\t\treferences=" + builder1.toString() +
                "\t\t}";
    }
}
