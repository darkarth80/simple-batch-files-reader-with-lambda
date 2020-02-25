package com.darkarth.demo.util.enumeration;

public enum Type {

    SIMPLE ("Simple"),
    COMPLEX ("Complex");

    private final String type;

    private Type(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String getStringValue() {
        return Type.values().toString();
    }

}