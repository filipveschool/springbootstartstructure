package org.filip.springbootstartstructure;

import org.filip.springbootstartstructure.domain.Privilege;
import org.filip.springbootstartstructure.domain.Role;
import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.persistence.repositories.PrivilegeRepository;
import org.filip.springbootstartstructure.persistence.repositories.RoleRepository;
import org.filip.springbootstartstructure.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class SpringSecurityRolesIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    private User user;
    private Role role;
    private Privilege privilege;

    @Before
    public void setUp() throws Exception {
        privilege = new Privilege("TEST_PRIVILEGE");
        privilegeRepository.save(privilege);

        role = new Role("TEST_ROLE");
        role.setPrivileges(Arrays.asList(privilege));
        roleRepository.save(role);


        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        //user.setPassword(passwordEncoder.encode("123"));
        user.setPassword("123");
        user.setEmail("john@doe.com");
        user.setRoles(Arrays.asList(role));
        user.setEnabled(true);
        userRepository.save(user);

    }

    @Test
    public void testDeleteUser() {

        assertNotNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));
        user.setRoles(null);
        userRepository.delete(user);

        assertNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));
    }

    @Test
    public void testDeleteRole() {

        assertNotNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));

        user.setRoles(new ArrayList<Role>());
        role.setPrivileges(new ArrayList<Privilege>());
        roleRepository.delete(role);

        assertNull(roleRepository.findByName(role.getName()));
        assertNotNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(userRepository.findByEmail(user.getEmail()));
    }

    @Test
    public void testDeletePrivilege() {

        assertNotNull(roleRepository.findByName(role.getName()));
        assertNotNull(privilegeRepository.findByName(privilege.getName()));

        role.setPrivileges(new ArrayList<Privilege>());
        privilegeRepository.delete(privilege);

        assertNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(roleRepository.findByName(role.getName()));
    }

    @Test
    public void test_role_get_by_name_successful(){
        Role role = roleRepository.findByName("TEST_ROLE");
        assertNotNull(role.getName());
        assertEquals("TEST_ROLE", role.getName());
    }

}
