package com.anoopsen.SpringSecurityJWTAuthorizationDemo3.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.anoopsen.SpringSecurityJWTAuthorizationDemo3.model.User;

import jakarta.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value = "/api/v1/resource")
public class ResourceController {
		
	@GetMapping(value = "/dashboard")
	//@PreAuthorize("hasRole('ROLE_USER')")
	//@RolesAllowed("USER")
	public ModelAndView index() {
		ModelAndView resourceDashboard = new ModelAndView("/Index.jsp");
		return resourceDashboard;
	}
	
}
