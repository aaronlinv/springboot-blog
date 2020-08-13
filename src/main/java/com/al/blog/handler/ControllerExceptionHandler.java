package com.al.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 会拦截所有Controller
@ControllerAdvice
public class ControllerExceptionHandler {
    // slf4j
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @ExceptionHandler(Exception.class) 
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e){
        
        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        
        // 返回的页面
        mv.setViewName("error/error");
        return mv;
    }
    
}
