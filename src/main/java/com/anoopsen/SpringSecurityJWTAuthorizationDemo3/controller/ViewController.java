package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/api/v1/view")
public class ViewController {

	@GetMapping(value = "/register")
	public ModelAndView renderRegisterPage() {
		return new ModelAndView("/UserRegister.jsp");
	}
	
	@GetMapping(value = "/login")
	public ModelAndView renderLoginPage() {
		return new ModelAndView("/UserLogin.jsp");
	}
	
	@GetMapping(value = "/login/error")
	public ModelAndView renderLoginErrorPage() {
		return new ModelAndView("/User403LoginError.jsp");
	}
}
