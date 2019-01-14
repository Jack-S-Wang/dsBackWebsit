package com.dascom.website.entity;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CollectionEnUrl {
	@Id
	private String id;
	private String name;
	private String url;
}
