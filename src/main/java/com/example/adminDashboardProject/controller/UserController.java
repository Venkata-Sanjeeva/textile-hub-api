package com.example.adminDashboardProject.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminDashboardProject.entity.User;
import com.example.adminDashboardProject.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody Map<String, String> userDetails) {
		User user = new User();
		
		user.setUsername(userDetails.get("username"));
		user.setPassword(userDetails.get("password"));
		
		if(userRepo.findByUsername(user.getUsername()) != null) {
			return ResponseEntity.badRequest().body("Email Already Exists!!!");
		}
		
		return ResponseEntity.ok(userRepo.save(user));
	}
}
