package com.example.adminDashboardProject.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
	
	public Cloudinary cloudinary() {
		Map<String, String> config = new HashMap<String, String>();
		
		config.put("cloud_name", System.getenv("CLOUDINARY_CLOUD_NAME"));
		config.put("api_key", System.getenv("CLOUDINARY_API_KEY"));
		config.put("api_secret", System.getenv("CLOUDINARY_API_SECRET"));
		
		return new Cloudinary(config);
	}
}
