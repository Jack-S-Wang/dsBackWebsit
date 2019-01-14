package com.dascom.website.dao;

import java.util.List;

import com.dascom.website.entity.CollectionCatalog;

/**
*@author leisenquan
*2018年12月20日
 */
public interface CatalogDao {
	
	/**
	 * 新建目录
	 * @param cata
	 * @return void
	 */
	int addCatalog(CollectionCatalog cata);
	
	/**
	 * 查询所有目录,id为空parentId不为空，但外部查询默认查询parentId，不许查id
	 * @return Object
	 */
	Object searchCata(String id,String parentId);
	
	/**
	 * 修改目录信息
	 * @param cata
	 * @return void
	 */
	int updateCata(CollectionCatalog cata);
	
	/**
	 * 根据id删除目录
	 * @param id
	 * @return void
	 */
	int delCata(String id);

}
