package org.filip.springbootstartstructure.web.dto;

import org.filip.springbootstartstructure.domain.Role;
import org.filip.springbootstartstructure.validation.annotations.PasswordMatches;
import org.filip.springbootstartstructure.validation.annotations.ValidEmail;
import org.filip.springbootstartstructure.validation.annotations.ValidPassword;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@PasswordMatches
public class UserDto {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String firstName;

    @NotNull
    @Size(min = 1)
    private String lastName;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    private boolean enabled;

    private String timezone;

    private Collection<Role> roles;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString(){
        return "UserDto met: Firstname: " + firstName + " - lastname: " + lastName + " - password: " +password
                + " - matchingPassword: " + matchingPassword + " - email: " + email;
    }
}

