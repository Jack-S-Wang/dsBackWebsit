package com.dascom.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 
*@author leisenquan
*2018年12月20日
 */
@ComponentScan(basePackages="com.dascom.website")
@SpringBootApplication
@Configuration
public class DascomWebsiteApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(DascomWebsiteApplication.class, args);
    }

}
