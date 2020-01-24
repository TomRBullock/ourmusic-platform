package com.ourmusic.platform.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.ourmusic.platform")
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {




}
