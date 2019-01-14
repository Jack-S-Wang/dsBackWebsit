package com.dascom.website.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dascom.website.entity.CollectionCatalog;

/**
 * 目录业务处理层，参数json便于扩展
*@author leisenquan
*2018年12月21日
 */
public interface CatalogService {
	
	/**
	 * 新建目录
	 * @param CollectionCatalog
	 * @return void
	 */
	void addCatalog(JSONObject json);
	
	/**
	 * 查询指定目录下的目录信息
	 * @return Object
	 */
	Object searchCata(JSONObject json);
	
	/**
	 * 修改目录信息
	 * @param cata
	 * @return void
	 */
	void updateCata(JSONObject json);
	
	/**
	 * 根据id删除目录
	 * @param id
	 * @return void
	 */
	void delCata(JSONObject json);

}
