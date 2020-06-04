package com.mycompany.myapp.domain.enumeration;

/**
 * The Country enumeration.
 */
public enum Country {
    BELGIUM("Belgium"),
    USA("UnitedStates"),
    FRANCE("France"),
    ITALY("Italy");

    private final String value;


    Country(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
