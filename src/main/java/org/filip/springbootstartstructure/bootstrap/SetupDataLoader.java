package org.filip.springbootstartstructure.bootstrap;

import org.filip.springbootstartstructure.domain.Privilege;
import org.filip.springbootstartstructure.domain.Role;
import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.persistence.repositories.PrivilegeRepository;
import org.filip.springbootstartstructure.persistence.repositories.RoleRepository;
import org.filip.springbootstartstructure.persistence.repositories.UserRepository;
import org.filip.springbootstartstructure.utils.SecurityPrivilege;
import org.filip.springbootstartstructure.utils.SecurityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    public SetupDataLoader(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        Privilege readPrivilege = createPrivilegeIfNotFound(SecurityPrivilege.READ_PRIVILEGE.getPrivilegeName());
        Privilege writePrivilege = createPrivilegeIfNotFound(SecurityPrivilege.WRITE_PRIVILEGE.getPrivilegeName());
        Privilege passwordPrivilege = createPrivilegeIfNotFound(SecurityPrivilege.CHANGE_PASSWORD_PRIVILEGE.getPrivilegeName());

        // == create initial roles
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege);
        List<Privilege> userPrivileges = Arrays.asList(readPrivilege, passwordPrivilege);
        createRoleIfNotFound(SecurityRole.ROLE_ADMIN.getRoleName(), adminPrivileges);
        createRoleIfNotFound(SecurityRole.ROLE_USER.getRoleName(), userPrivileges);

        Role adminRole = roleRepository.findByName(SecurityRole.ROLE_ADMIN.getRoleName());
        Role userRole = roleRepository.findByName(SecurityRole.ROLE_USER.getRoleName());

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("test"));
        //user.setPassword("test");
        user.setEmail("test@test.com");
        //user.setRoles(Arrays.asList(adminRole,userRole));
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        User normalUser = new User();
        normalUser.setFirstName("Normal user");
        normalUser.setLastName("normal user last");
        normalUser.setPassword(passwordEncoder.encode("test"));
        normalUser.setEmail("testnormal@test.com");
        normalUser.setRoles(Arrays.asList(userRole));
        normalUser.setEnabled(true);
        userRepository.save(normalUser);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }


}

