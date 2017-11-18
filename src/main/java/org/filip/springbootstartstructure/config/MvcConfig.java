package org.filip.springbootstartstructure.config;

import org.filip.springbootstartstructure.interceptors.LoggerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
//@EnableWebMvc Auto added because Spring boot detects dependency spring-boot-starter-web by using spring-boot-starter-parent
//@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new LoggerInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        //registry.addViewController("/console.html");
    }

// BEANS

    // https://www.javadevjournal.com/spring-boot/spring-boot-internationalization/

    /**
     * To determine which local is currently being used
     */
    @Bean
    public LocaleResolver localeResolver() {
        //SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * By default, there is no limit on the size of files that can be uploaded.
     * In order to set a maximum upload size, you have to declare a bean of type MultipartResolver
     */
    /*
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        // This limits the file size to 5MB
        multipartResolver.setMaxUploadSize(5242880);
        return multipartResolver;
    }
    */


    /**
     * bean to hold all the time zones.
     *
     * @return the array of time zones
     */
    @Bean(name = "timezones")
    public String[] timezones() {
        return TimeZone.getAvailableIDs();
    }



}
