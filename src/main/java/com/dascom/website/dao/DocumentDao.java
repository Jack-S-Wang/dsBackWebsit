package com.dascom.website.dao;

import java.util.List;

import com.dascom.website.entity.CollectionDocument;

/**
 * 文档dao类
*@author leisenquan
*2018年12月20日
 */
public interface DocumentDao {
	
	/**
	 * 新建文档
	 * @param doc
	 * @return void
	 */
	void addDocument(CollectionDocument doc);
	
	/**
	 * 查询当前文档或查询历史记录同文档的信息数据，外部输入值时两者id选其一,status则表示审核存在的状态
	 * @param Document_Id
	 * @param id
	 * @param status
	 * @return Object
	 */
	Object searchDocument(String Document_Id,String id,String status);
	
	
	/**
	 * 修改文档
	 * @param doc
	 * @return void
	 */
	void updateDocument(CollectionDocument doc);
	
	/**
	 * 根据文档id修改所有的显示状态
	 * @param Document_Id
	 */
	void updateDocument(String Document_Id);
	
	/**
	 * 根据id删除文档，但并不是删除而是置换字段
	 * @param id
	 * @return void
	 */
	void delDocument(String Document_Id);
	
	/**
	 * 根据删除状态为1的直接清理文档数据
	 */
	void del();
	
	/**
	 * 根据id删除对应的数据，是真删除
	 * @param id
	 */
	void del(String id);
	
	/**
	 * 根据显示字段为1的查询所有的文档
	 */
	Object searchShow();
	/**
	 * 回滚所有删除状态为1的数据
	 * @param Document_Id
	 */
	void restore(String Document_Id);
	/**
	 * 查询所有删除状态的数据
	 * @return
	 */
	Object searchDel();

}
