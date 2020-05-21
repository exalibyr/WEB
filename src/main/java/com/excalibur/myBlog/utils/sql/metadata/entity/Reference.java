package com.excalibur.myBlog.utils.sql.metadata.entity;

public class Reference {

    private Property property;                  //property that is referenced
    private Entity entity;                      //entity that is referenced
    private boolean isCollection;               //true if referenced property has foreign key, else false

    public Reference(Entity entity, boolean isCollection) throws IllegalArgumentException {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        this.entity = entity;
        this.isCollection = isCollection;
    }

    public Reference(Entity entity, Property property, boolean isCollection) throws IllegalArgumentException {
        if (property == null || entity == null) throw new IllegalArgumentException("Property and entity cannot be null");
        this.property = property;
        this.entity = property.getEntity();
        this.isCollection = isCollection;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public Entity getEntity() {
        return entity;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "property name=" + property.getName() +
                ", entity name=" + entity.getName() +
                ", isCollection=" + isCollection +
                '}';
    }
}
