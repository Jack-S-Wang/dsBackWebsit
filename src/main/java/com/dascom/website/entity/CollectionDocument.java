package com.dascom.website.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文档实体类
*@author leisenquan
*2018年12月20日
 */
@Getter
@Setter
@ToString
@Document(collection="collection_document")
public class CollectionDocument {
	
	@Id
	private String id;
	private String name;
	private String document_Id;
	private String cata_Id;
	private String content;
	private Date createDate;
	private String alias;
	private Date modifyDate;
	private String user;
	private Integer order;
	private Integer status;
	private List<CollectionEnUrl> encloures;
	private int idel;
	private int ishow;
	
}
