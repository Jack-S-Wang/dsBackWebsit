package com.dascom.website.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 目录实体类 collection_catalog
*@author leisenquan
*2018年12月20日
 */

@Getter
@Setter
@ToString
@Document(collection="collection_catalog")
public class CollectionCatalog {

	@Id
	private String id;
	private String name;
	private String parentId;
	private Integer order;
	private String genre;
	private String url;
	private int ishow;

}
