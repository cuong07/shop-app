package com.project.shopapp.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfogurations {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
