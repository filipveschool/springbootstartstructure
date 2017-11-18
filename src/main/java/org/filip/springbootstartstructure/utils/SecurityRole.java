package org.filip.springbootstartstructure.utils;

public enum SecurityRole {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String roleName;


    SecurityRole(String role) {
        setRoleName(role);
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
