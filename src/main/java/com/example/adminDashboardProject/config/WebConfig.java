/*
package com.example.adminDashboardProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ensure the path starts with "file:" and ends with a "/"
        String location = uploadDir.startsWith("file:") ? uploadDir : "file:" + uploadDir;
        if (!location.endsWith("/")) {
            location += "/";
        }

        registry.addResourceHandler("/api/product-images/**")
                .addResourceLocations(location);
    }
}

*/