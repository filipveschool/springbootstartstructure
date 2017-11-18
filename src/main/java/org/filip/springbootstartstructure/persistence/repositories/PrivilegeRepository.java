package org.filip.springbootstartstructure.persistence.repositories;

import org.filip.springbootstartstructure.domain.Privilege;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    /**
     *
     * @param name the name of a privilege for example "READ_PRIVILEGE"
     * @return the founded Privilege
     */
    Privilege findByName(String name);

    @Override
    void delete(Privilege privilegeToBeDeleted);

    /**
     * Get a list of all privileges back.
     *
     * @return
     */
    @Override
    List<Privilege> findAll();
}
