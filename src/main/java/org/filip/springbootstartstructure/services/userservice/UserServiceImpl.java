package org.filip.springbootstartstructure.services.userservice;

import org.filip.springbootstartstructure.domain.PasswordResetToken;
import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.domain.VerificationToken;
import org.filip.springbootstartstructure.exceptions.UserAlreadyExistException;
import org.filip.springbootstartstructure.persistence.repositories.PasswordResetTokenRepository;
import org.filip.springbootstartstructure.persistence.repositories.PrivilegeRepository;
import org.filip.springbootstartstructure.persistence.repositories.RoleRepository;
import org.filip.springbootstartstructure.persistence.repositories.UserRepository;
import org.filip.springbootstartstructure.persistence.repositories.VerificationTokenRepository;
import org.filip.springbootstartstructure.utils.SecurityRole;
import org.filip.springbootstartstructure.utils.TokenConstants;
import org.filip.springbootstartstructure.web.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    /**
     * This is for making a new user by going through the registration form
     *
     * @param accountDto
     * @return A newly created user object
     * @throws UserAlreadyExistException
     */
    @Override
    public User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException {
        //Check for duplicated emails

        if (emailExist(accountDto.getEmail())) {
            log.info("registerNewUserAccount method in UserService class was executed.");
            log.info("Email address already exists: " + accountDto.getEmail());
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }

        User user = new User();
        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName(SecurityRole.ROLE_USER.getRoleName())));

        return userRepository.save(user);
    }

    /**
     * Save a newly created and registered user to the database
     *
     * @param user
     */
    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    /**
     * Create a passwordResetToken for an existing user so that he can reset his password
     *
     * @param user
     * @param token
     */
    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        passwordResetTokenRepository.save(new PasswordResetToken(token, user));
    }

    @Override
    public void createVerificationTokenForUser(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = verificationTokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = verificationTokenRepository.save(vToken);
        return vToken;
    }


    /**
     * Find an existing user by looking up his email address
     *
     * @param email
     * @return
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * @return Retrieve a list of all users in the database
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * @return a list of all enabled users in the applications
     */
    @Override
    public List<User> getUsersByEnabledTrue() {
        return userRepository.getUsersByEnabledTrue();
    }

    /**
     * @return a list of all disabled users in the applications
     */
    @Override
    public List<User> getUsersByEnabledFalse() {
        return userRepository.getUsersByEnabledFalse();
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    /**
     * @param token
     * @return A founded user Object that links to the token
     */
    @Override
    public User getUserByPasswordResetToken(String token) {
        return getPasswordResetToken(token).getUser();
    }

    @Override
    public User getUser(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }

        return null;
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }


    /**
     * Make A QR url for a user
     *
     * @param user
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
        String APP_NAME = "SpringRegistration";
        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?issuer=%s", APP_NAME, user.getEmail(), APP_NAME), "UTF-8");
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals().stream().filter((u) -> !sessionRegistry.getAllSessions(u, false).isEmpty()).map(Object::toString).collect(Collectors.toList());
    }

    /*************************************************
     *  UPDATE FUNCTIONS
     ************************************************

     /**
     *
     * @param user
     * @param password
     */
    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    /**
     * Delete an existing user from the databse
     *
     * @param user
     */
    @Override
    public void deleteUser(User user) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);
        if (passwordResetToken != null) {
            passwordResetTokenRepository.delete(passwordResetToken);
        }
        userRepository.delete(user);
    }

    /**
     * @param user
     * @param oldPassword
     * @return
     */
    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TokenConstants.TOKEN_INVALID.getTokenName();
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0)) {
            verificationTokenRepository.delete(verificationToken);
            return TokenConstants.TOKEN_EXPIRED.getTokenName();
        }

        user.setEnabled(true);
        //verificationTokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TokenConstants.TOKEN_VALID.getTokenName();
    }


    // Extra functions

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }


}
