package org.filip.springbootstartstructure.config;

import org.filip.springbootstartstructure.security.CustomAuthenticationProvider;
import org.filip.springbootstartstructure.security.google2fa.CustomWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    public SecurityConfig() {
        super();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/resources/**", // Laat toegang tot alle resources to
                        "/webjars/**" // laat access tot de webjars toe
                        );
    }

    /**
     * This allows us to configure static resources, form authentication login and logut configuration
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Allow stuff

        http
                .csrf().disable()
                .authorizeRequests()
                // Any user can access a request if the URL starts with "/" or "/js/" or "/css" or "/img/" or "/webjars"
                .antMatchers("/","/js/**","/css/**","/img/**","/webjars/**").permitAll()
                .antMatchers("/users/index").permitAll()
                //.antMatchers("/api/books/**", "/api/users/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/users*").permitAll()
                .antMatchers("/users**").permitAll()
                .antMatchers("/users/edit/**").permitAll()
                .antMatchers("/users/update/**").permitAll()
                .antMatchers(
                        "/login*",
                        "/mail/sendtest",
                        "/mail/",


                        "/logout*",
                        "/console",
                        "/homepage",
                        "/homepage2",
                        "/signin/**",
                        "/signup/**",
                        "/customLogin",
                        "/user/registration*",
                        "/registrationConfirm*",
                        "/expiredAccount*",
                        "/registration*",
                        "/badUser*",
                        "/user/resendRegistrationToken*",
                        "/forgetPassword*",
                        "/user/resetPassword*",
                        "/user/changePassword*",
                        "/emailError*",
                        "/old/user/registration*",
                        "/successRegister*",
                        "/qrcode*",
                        "loggedUsers",
                        "/loggedUsersFromSessionRegistry").permitAll()
                .antMatchers("/invalidSession*").anonymous()
                .antMatchers(
                        "/user/updatePassword*",
                        "/user/savePassword*",
                        "/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")

                // Any URL that starts with "/user/" will be restricted to users who have the role
                // "USER". You will notice that since we are invoking the hasRole method we do not need to
                // specify the "ROLE_" prefix.
                //.antMatchers("/user/**").hasRole("USER")
                // Any URL that has not already been matched on only requires that the user be authenticated
                .anyRequest().authenticated()
                .and()
                // We must grant all users (i.e. unauthenticated users) access to our log in page.
                // The formLogin().permitAll() method allows granting access to all users for all URLs
                // associated with form based log in.
                .formLogin()
                .loginPage("/login")
                //Url to go to if login was successful
                //.defaultSuccessUrl("/homepage.html")
                .defaultSuccessUrl("/homepage")
                //url to go to if login failed
                .failureUrl("/login?error=true")
                //TO DO some actions after you are successfully loggedin
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                // TODO new stuff
                .authenticationDetailsSource(authenticationDetailsSource)
                .permitAll()
                .and()
                /*
                .sessionManagement()
                .invalidSessionUrl("/invalidSession")
                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
                .sessionFixation().none()
                .and()
                */
                //https://docs.spring.io/spring-security/site/docs/5.0.0.BUILD-SNAPSHOT/reference/htmlsingle/#jc-logout
                // Provides logout support. This is automatically applied when using WebSecurityConfigurerAdapter.
                .logout()
                //.invalidateHttpSession(true)
                .invalidateHttpSession(false)
                //.clearAuthentication(true)
                //The URL to redirect to after logout has occurred. The default is /login?logout.
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll();
        //.and()
        //.exceptionHandling();
        //.accessDeniedHandler(accessDeniedHandler);

    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    //BEANS

    /**
     * BCrypt, will internally generate a random salt instead.
     * This is important to understand because it means that each call will have a different result,
     * and so we need to only encode the password once.
     *
     * Gebruikt in:
     *      - service/UserService
     *      - setupDataLoader
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
