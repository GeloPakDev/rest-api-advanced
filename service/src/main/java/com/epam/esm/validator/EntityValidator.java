package com.epam.esm.validator;

public abstract class EntityValidator {
    public static boolean isNotString(String name) {
        if (name.isEmpty()) {
            return true;
        }
        final int sz = name.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(name.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
