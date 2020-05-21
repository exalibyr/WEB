package com.excalibur.myBlog.utils.sql;

public class SQLCondition {

    public static class Value {
        private String value;                   //right parameter value, may be like constant, DB property or function
        private boolean isConstant;             //if value is constant literal then true, else false
        private String ownerEntity = null;      //if value is property, then it has owner entity name

        public Value(String value, boolean isConstant, String ownerEntity) {
            this.value = value;
            this.isConstant = isConstant;
            this.ownerEntity = ownerEntity;
        }

        public Value(String value, boolean isConstant) {
            this.value = value;
            this.isConstant = isConstant;
        }

        public String getValue() {
            return value;
        }

        public String getOwnerEntity() {
            return ownerEntity;
        }
    }

    private String field;                   //left parameter in condition like "id"
    private String operator;                //condition operator like "=", ">", etc
    private Value value;                    //right parameter

    public SQLCondition(String field, String operator, Value value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public String getField() {
        return field;
    }

    public String getOperator() {
        return operator;
    }

    public String getValueAsString() {
        if (this.value == null) {
            return null;
        } else {
            return this.value.isConstant ? "'" + this.value.value + "'" : this.value.value;
        }
    }

}
