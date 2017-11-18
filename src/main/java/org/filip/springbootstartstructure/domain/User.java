package org.filip.springbootstartstructure.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

@Entity
@Table(name = "user_account")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "{NotEmpty.user.firstName}")
    private String firstName;

    @NotEmpty(message = "{NotEmpty.user.lastName}")
    private String lastName;

    @Email(message = "{Email.user.email}")
    @NotEmpty(message = "*Please provide an email")
    @Column(nullable = false, unique = true)
    private String email;

    //@Column(nullable = false, unique = true)
    //private String username;

    @Column(length = 60)
    private String password;

    private boolean enabled;

    // The timezone
    @Column(name = "TZONE", nullable = false, length = 128)
    private String timezone;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @CreationTimestamp
    private LocalDateTime createdDate;

    //

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User() {
        super();
        this.enabled = false;
        this.timezone = "Europe/Brussels";
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<Role> roles) {
        this.roles = roles;
    }

    public String printRoles() {

        Iterator iterator = getRoles().iterator();
        String out = "";
        while (iterator.hasNext()) {
            Role rr = (Role) iterator.next();
            out += rr.getName();
            out += " - ";
        }
        return out;

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /*
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User user = (User) obj;
        if (!email.equals(user.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "User met id= " + id + " en Voornaam= " + firstName + " en Familienaam= " + lastName + "\n\n"
                + " en password= " + password + " en is enabled= " + enabled + " en heeft de rollen= "
                + roles;

    }
}

