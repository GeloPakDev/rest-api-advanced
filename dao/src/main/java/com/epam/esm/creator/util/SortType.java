package com.epam.esm.creator.util;

public enum SortType {

    ASC("ASC"),

    DESC("DESC");

    private final String sortTypeName;

    SortType(String sortTypeName) {
        this.sortTypeName = sortTypeName;
    }

    public String getSortTypeName() {
        return sortTypeName;
    }

    @Override
    public String toString() {
        return sortTypeName;
    }
}
