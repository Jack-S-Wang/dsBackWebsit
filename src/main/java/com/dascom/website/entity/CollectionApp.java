package com.dascom.website.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * app实体类
*@author leisenquan
*2018年12月20日
 */
@Getter
@Setter
@ToString
@Document(collection="collection_app")
public class CollectionApp {
	@Id
	private String id;
	private String name;
	private String version;
	private String updateContent;
	private String url;
	private String code;
	private String size;
	private String genre;
	

}
