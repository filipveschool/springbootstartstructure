package org.filip.springbootstartstructure.services.security;

import org.filip.springbootstartstructure.domain.PasswordResetToken;
import org.filip.springbootstartstructure.domain.User;
import org.filip.springbootstartstructure.persistence.repositories.PasswordResetTokenRepository;
import org.filip.springbootstartstructure.utils.SecurityPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;

@Service
@Transactional
public class UserSecurityServiceImpl implements ISecurityUserService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    // API

    /**
     * If the token is valid, the user will be authorized to change their password by
     * granting them a CHANGE_PASSWORD_PRIVILEGE, and direct them to a page to update their password.
     *
     * The interesting note here is – this new privilege will only
     * be usable to change the password (as the name implies) –
     * and so granting it programmatically to the user is safe.
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    public String validatePasswordResetToken(long id, String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if ((passwordResetToken == null) || (passwordResetToken.getUser().getId() != id)) {
            return "invalidToken";
        }

        final Calendar cal = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }

        User user = passwordResetToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(
                new SimpleGrantedAuthority(SecurityPrivilege.CHANGE_PASSWORD_PRIVILEGE.getPrivilegeName())
        ));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
}
