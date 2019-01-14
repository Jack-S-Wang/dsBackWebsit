package com.dascom.website.config.template;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipartConfig {
	/**
	 * 配置上传文件大小的配置
	 * @return
	 */
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("200MB");
		factory.setMaxRequestSize("250MB");
		return factory.createMultipartConfig();
	}

}
