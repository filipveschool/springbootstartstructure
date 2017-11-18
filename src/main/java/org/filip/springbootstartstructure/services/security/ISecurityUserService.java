package org.filip.springbootstartstructure.services.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);
}
