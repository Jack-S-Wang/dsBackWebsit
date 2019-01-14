package com.dascom.website.dao;

import com.dascom.website.entity.CollectionApp;

/**
 * app软件dao类
*@author leisenquan
*2018年12月20日
 */
public interface AppDao {
	
	/**
	 * 添加APP信息
	 * @param app
	 * @return
	 */
	int addApp(CollectionApp app);
	
	/**
	 * 更新app
	 * @param app
	 * @return
	 */
	void updateApp(CollectionApp app);
	
	/**
	 * 删除app
	 * @param app
	 * @return
	 */
	int delApp(String id);
	
	/**
	 * 查询app对应的id对象
	 * @param id
	 * @return
	 */
	CollectionApp searchApp(String id);
	
	/**
	 * 根据不同的模式查询app版本对象
	 * @param number
	 * @param genre
	 * @return
	 */
	Object searchAppN(String number,String genre);

}
