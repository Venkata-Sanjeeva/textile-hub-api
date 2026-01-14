package com.example.adminDashboardProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.adminDashboardProject.entity.User;
import com.example.adminDashboardProject.entity.UserType;
import com.example.adminDashboardProject.repository.UserRepository;

@SpringBootApplication
public class AdminDashboardProjectApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(AdminDashboardProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(userRepo.findByUsername("sanjeeva") == null) {
			User user = new User();
		
			user.setUsername("sanjeeva");
			user.setPassword("VS@86@kv");
			user.setUserType(UserType.ADMIN);
			
			userRepo.save(user);
		}
	}

}
