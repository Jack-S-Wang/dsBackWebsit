package com.dascom.website.dao;

import com.dascom.website.entity.CollectionEncloure;

public interface EncloureDao {
	/**
	 * 添加附件
	 * @param encloure
	 * @return
	 */
int addEncloure(CollectionEncloure encloure);

/**
 * 
 * 更新附件信息
 * @param encloure
 * @return
 */
int updateEncloure(CollectionEncloure encloure);

/**
 * 查询附件信息,如果id为空值，则查询全部
 * @param id
 * @return
 */
Object searchEncloure(String id);

/**
 * 删除对应的附件
 * @param id
 * @return
 */
int delEncloure(String id);

}
