package org.filip.springbootstartstructure.services.userservice;

import org.filip.springbootstartstructure.domain.PasswordResetToken;
import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.exceptions.UserAlreadyExistException;
import org.filip.springbootstartstructure.web.dto.UserDto;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    /*************************************************
     * CREATE FUNCTIONS
     *************************************************/

    /**
     * This is for making a new user by going through the registration form
     *
     * @param accountDto
     * @return A newly created user object
     * @throws UserAlreadyExistException
     */
    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    /**
     * Save a newly created and registered user to the database
     * @param user
     */
    void saveRegisteredUser(User user);


    /**
     * Create a passwordResetToken for an existing user so that he can reset his password
     *
     * @param user
     * @param token
     */
    void createPasswordResetTokenForUser(User user, String token);

    /*************************************************
     * READ FUNCTIONS
     *************************************************/

    /**
     * Find an existing user by looking up his email address
     *
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Optional<User> findById(Long id);

    /**
     *
     * @return Retrieve a list of all users in the database
     */
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

    PasswordResetToken getPasswordResetToken(String token);

    /**
     *
     * @param token
     * @return A founded user Object that links to the token
     */
    User getUserByPasswordResetToken(String token);

    /**
     * Make A QR url for a user
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     */
    String generateQRUrl(User user) throws UnsupportedEncodingException;

    List<String> getUserFromSessionRegistry();

    /*************************************************
     * UPDATE FUNCTIONS
     *************************************************/


    void changeUserPassword(User user, String password);

    /*************************************************
     * DELETE FUNCTIONS
     *************************************************/

    /**
     * Delete an existing user from the databse
     *
     * @param user
     */
    void deleteUser(User user);

    /*************************************************
     * CUSTOM FUNCTIONS
     *************************************************/

    /**
     *
     * @param user
     * @param oldPassword
     * @return
     */
    boolean checkIfValidOldPassword(User user, String oldPassword);

}
