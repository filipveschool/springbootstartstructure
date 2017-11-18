package org.filip.springbootstartstructure.interceptors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    /**
     * There are a couple of advantages of handling the exception through an interceptor
     * rather than in the controller itself. One is that we can apply the same exception handling
     * logic to multiple controllers.
     * Another is that we can create a method that targets only the exception
     * we want to handle, allowing the framework to delegate the exception handling
     * without our having to use instanceof to check what type of exception was thrown:
     *
     * @see: www.baeldung.com/spring-maxuploadsizeexceeded
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request,
                                               HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("file");
        modelAndView.getModel().put("message","File too large!");
        return modelAndView;
    }
}
