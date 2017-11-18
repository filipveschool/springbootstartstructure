package org.filip.springbootstartstructure.persistence.repositories;

import org.filip.springbootstartstructure.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // All find functions

    /**
     * Gets used for authentication the logged in user in MyUserDetailsService "loadByUsername()" function
     * @param email
     * @return a founded User object
     */
    User findByEmail(String email);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    @Override
    Optional<User> findById(Long id);

    // All get functions

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    @Override
    List<User> findAll();

    /**
     *
     * @return a list of all enabled users in the applications
     */
    List<User> getUsersByEnabledTrue();

    /**
     *
     * @return a list of all disabled users in the applications
     */
    List<User> getUsersByEnabledFalse();



    // All delete functions

    /**
     * Delete a user based on his email-address.
     * @param email
     */
    void removeUserByEmail(String email);

    /**
     * Deletes a given entity.
     *
     * @param user
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    void delete(User user);
}
