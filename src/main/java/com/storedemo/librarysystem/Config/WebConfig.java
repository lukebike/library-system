package com.storedemo.librarysystem.Config;
import org.springframework.context.annotation.Bean; // Allows you to define beans (objects managed by Spring).
import org.springframework.context.annotation.Configuration; // Marks this class as a configuration class for Spring.
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Used to configure CORS mappings.
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Interface for customizing

@Configuration
public class WebConfig {
 @Bean
    public WebMvcConfigurer crossOriginConfigurer(){
     return new WebMvcConfigurer(){
         @Override
         public void addCorsMappings(CorsRegistry registry) {
             registry.addMapping("/**").
                     allowedOrigins("http://localhost:5173").
                     allowedMethods("GET", "POST", "PUT", "DELETE").
                     allowedHeaders("*");
         }
     };
 }
}
