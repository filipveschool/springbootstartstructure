package org.filip.springbootstartstructure.security.logout;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Deze klasse wordt gebruikt nadat op de logout knop is geklikt als allereerste
 */
@Component("myLogoutSuccessHandler")
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * We will remove the user attribute from the session when the user logs out.
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if(session != null) {
            session.removeAttribute("user");
        }

        //response.sendRedirect("/logout.html?logSucc=true");
        //response.sendRedirect("/logout?logSucc=true");
        response.sendRedirect("/login?logout");

    }
}
