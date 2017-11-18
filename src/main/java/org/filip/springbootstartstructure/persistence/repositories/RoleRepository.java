package org.filip.springbootstartstructure.persistence.repositories;

import org.filip.springbootstartstructure.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role roleToBeDeleted);

    /**
     * Get a list of all roles back.
     *
     * @return
     */
    @Override
    List<Role> findAll();
}
