package com.example.esa_spring.configuration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.jms.ConnectionFactory;

import javax.naming.NamingException;

@Configuration(proxyBeanMethods = false)
@EnableCaching
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {

    public static ConfigurableApplicationContext context;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlMapper xmlMapper() { return new XmlMapper(); }

}
