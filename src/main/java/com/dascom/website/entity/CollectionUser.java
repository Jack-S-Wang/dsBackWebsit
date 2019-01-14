package com.dascom.website.entity;

import org.springframework.data.annotation.Id;

/*import lombok.Getter;
import lombok.Setter;
import lombok.ToString;*/

/**
 * 用户类实体
*@author leisenquan
*2018年12月20日
 */
/*@Getter
@Setter
@ToString*/
public class CollectionUser {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	@Id
	private String id;
	private String name;
	private String pwd;
	private String parentId;
	private String genre;

}
