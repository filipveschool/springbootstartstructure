package org.filip.springbootstartstructure.events.registration;

import org.filip.springbootstartstructure.domain.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private User user;

    /**
     *
     * @param user
     * @param locale
     * @param appUrl
     */
    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    /**
     *
     * @return
     */
    public String getAppUrl() {
        return appUrl;
    }

    /**
     *
     * @return
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }
}

