package com.example.adminDashboardProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
<<<<<<< HEAD
        // This makes the files visible to the internet
        registry.addResourceHandler("/product-images/**")
                .addResourceLocations("file:///C:/my-app-uploads/");
=======
        registry.addResourceHandler("/api/product-images/**")
                // Use "file:" followed by the absolute path
                .addResourceLocations("file:C:/my-app-uploads/"); 
>>>>>>> fe3adef617b94a3e8f921b3ed93063dd91b654ee
    }
}