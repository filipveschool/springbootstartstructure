package org.filip.springbootstartstructure.web.dto;

import org.filip.springbootstartstructure.validation.annotations.ValidPassword;

/**
 * Deze klasse wordt gebruikt nadat je op de pagina changePassword.html
 * het formulier ingevuld hebt en data doorstuurt naar de controller
 *
 * Al deze data wordt hier ingevuld
 */
public class PasswordDto {

    private String oldPassword;

    @ValidPassword
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
