package com.dascom.website.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection="collection_encloure")
public class CollectionEncloure {
	@Id
	private String id;
	private String name;
	private String url;
	private String version;
	private String updateContent;
	private String size;
	private String code;
	private String doc_Id;
	private String cata_Id;
	private int ishow;
	
}
