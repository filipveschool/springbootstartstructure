package org.filip.springbootstartstructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

/**
 * This class is for when a user logs in to save in the ActiveUserStore or to be removed from it
 */
@Component
public class LoggedUser implements HttpSessionBindingListener {

    private static final Logger log = LoggerFactory.getLogger(LoggedUser.class);

    private String username;
    private ActiveUserStore activeUserStore;

    public LoggedUser(String username, ActiveUserStore activeUserStore) {
        this.username = username;
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {
    }


    /**
     * Als een user inlogged, wordt gekeken of de User al actief is in de userstore.
     * Zo nee, wordt hij toegevoegd
     *
     * This will be called when the user logs in
     *
     * @param event
     */
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println(activeUserStore);
        log.info("Class LoggedUser method Valuebound executed");
        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (!users.contains(user.getUsername())) {
            log.info("LoggedUser: " + user.getUsername() + " is not yet in activeuserstore.");
            users.add(user.getUsername());
        }
        log.info("LoggedUser: " + user.getUsername() + " is already known and added to activeuserstore.");
    }

    /**
     * Als een user inlogged, wordt gekeken of de User al actief is in de userstore.
     * Zo nee, wordt hij toegevoegd
     *
     * This will be called when the user logs out or the session expires.
     * @param event
     */
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        log.info("Class LoggedUser method ValueUNbound executed");

        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        if (users.contains(user.getUsername())) {
            log.info("LoggedUser: " + user.getUsername() + " is already known and will be removed from activeuserstore.");
            users.remove(user.getUsername());
        }

    }

    // Extra methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
