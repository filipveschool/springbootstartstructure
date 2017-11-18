package org.filip.springbootstartstructure.security;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is to get a list of all users who are active and logged in
 */
public class ActiveUserStore {

    public List<String> users;



    public ActiveUserStore() {
        users = new ArrayList<String>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

}
