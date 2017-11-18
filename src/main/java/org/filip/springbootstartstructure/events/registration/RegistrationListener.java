package org.filip.springbootstartstructure.events.registration;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    //private IUserService userService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {

    }
}
