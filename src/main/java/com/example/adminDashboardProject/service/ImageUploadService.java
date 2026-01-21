package com.example.adminDashboardProject.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageUploadService {
	
	private final Cloudinary cloudinary;
	
	public ImageUploadService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}
	
	public Map uploadImage(MultipartFile file) throws IOException {
		Map uploadResult = cloudinary.uploader().upload(
				file.getBytes(), 
				ObjectUtils.asMap("folder", "clothing-store/products")
		);
		return uploadResult;
	}
	
	public String deleteImage(String publicId) throws IOException {
	    if (publicId == null || publicId.isEmpty()) {
	        return "No publicId provided";
	    }
	    
	    Map result = cloudinary.uploader().destroy(
	    		publicId, ObjectUtils.asMap("invalidate", true)
		);
	    
	    // Cloudinary returns {"result": "ok"} if successful
	    // or {"result": "not found"} if the ID was wrong
	    return result.get("result").toString();
	}
}
