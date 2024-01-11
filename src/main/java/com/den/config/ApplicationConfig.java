package com.den.config;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import com.cloudinary.Cloudinary;

@Configuration
@EnableWebSecurity
public class ApplicationConfig {

  @Value("${daden.cloudinary.secret}")
  String apiSecret;
  @Value("${daden.cloudinary.apiKey}")
  String apiKey;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Cloudinary getCloudinary() {
    Map<String, Object> config = new HashMap<>();
    config.put("cloud_name", "anhdaden");
    config.put("api_key", apiKey);
    config.put("api_secret", apiSecret);
    config.put("secure", true);
    return new Cloudinary(config);
  }

  @Bean
  public SimpleDateFormat getSimpleDateFormat() {
    return new SimpleDateFormat("dd/MM/yyyy");
  }

}