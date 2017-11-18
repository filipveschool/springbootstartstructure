package org.filip.springbootstartstructure.utils;

public enum  SecurityPrivilege {

    READ_PRIVILEGE("READ_PRIVILEGE"),
    WRITE_PRIVILEGE("WRITE_PRIVILEGE");

    private String privilegeName;


    SecurityPrivilege(String privilege) {
        setPrivilegeName(privilege);
    }

    public String getPrivilegeName() {
        return this.privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }
}
