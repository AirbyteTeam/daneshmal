package com.airbyte.daneshmal.security.permission;

public enum Permission {
    COMPANY_WRITE("company::write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
