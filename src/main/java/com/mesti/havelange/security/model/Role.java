package com.mesti.havelange.security.model;

public enum Role {
    ROLE_ADMIN("ROLE_ADMIN");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
