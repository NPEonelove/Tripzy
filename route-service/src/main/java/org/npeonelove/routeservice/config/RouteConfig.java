package org.npeonelove.routeservice.config;

import org.modelmapper.ModelMapper;
import org.npeonelove.routeservice.service.RouteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
