package com.example.adminDashboardProject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.adminDashboardProject.entity.User;
import com.example.adminDashboardProject.entity.UserType;
import com.example.adminDashboardProject.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private UserRepository userRepo;

	@PostMapping("/login")
	public ResponseEntity<?> verifyUser(@RequestBody User user) {
		String userPassword = user.getPassword();
		
		User availableUser = userRepo.findByUsername(user.getUsername());
		
		if(availableUser == null || !availableUser.getUserType().equals(UserType.ADMIN) || !userPassword.equals(availableUser.getPassword())) {
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		}
		
		Map<String, String> obj = new HashMap<String, String>();
		
		obj.put("username", user.getUsername());
		obj.put("password", user.getPassword());
		
		return ResponseEntity.ok(obj);
	}
}
