package org.filip.springbootstartstructure.persistence.repositories;

import org.filip.springbootstartstructure.domain.PasswordResetToken;
import org.filip.springbootstartstructure.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    // Implemented from CrudRepository

    /**
     * Deletes a given entity.
     *
     * @param passwordResetTokenToBeDeleted
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    @Override
    void delete(PasswordResetToken passwordResetTokenToBeDeleted);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    @Override
    Optional<PasswordResetToken> findById(Long id);

    // Implemented from JpaRepository




    // Custom implements

    PasswordResetToken findByToken(String token);

    /**
     *
     * @param user The User object that requested a new PasswordResetToken
     * @return passwordResetToken that belongs to a specific user
     */
    PasswordResetToken findByUser(User user);

    /**
     *
     * @param now
     * @return
     */
    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    /**
     *
     * @param now
     */
    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

}
