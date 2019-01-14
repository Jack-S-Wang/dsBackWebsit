package com.dascom.website.dao;

import com.dascom.website.entity.CollectionUser;

public interface UserDao {
	/**
	 * 新增用户信息
	 * @param user
	 */
void addUser(CollectionUser user);
/**
 * 更新用户信息
 * @param user
 */
void updateUser(CollectionUser user);
/**
 * 查询用户信息,number为空则查询本身，否则根据number值查询不同数据
 * @param id
 * @param number
 * @return
 */
Object searchUser(String id,String number);
/**
 * 删除用户信息
 * @param id
 */
void delUser(String id);
}
