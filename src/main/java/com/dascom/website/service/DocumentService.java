package com.dascom.website.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 文档业务处理层，参数json便于扩展
 * @author DS-031
 *
 */
@Service
public interface DocumentService {
	/**
	 * 添加文档信息
	 * @param json
	 * @return
	 */
void addDocument(JSONObject json);
/**
 * 更新文档信息
 * @param json
 * @return
 */
void updateDocument(JSONObject json);
/**
 * 查询文档信息
 * @param json
 * @return
 */
Object searchDocument(JSONObject json);
/**
 * 删除文档，但只是将字段Idel致为1
 * @param json
 * @return
 */
void delDocument(JSONObject json);

/**
 * 删除致为删除状态的数据
 */
void del();

/**
 * 查询所有的可以编辑的最新的文档
 */
Object searchShow();

/**
 * 回滚删除的数据。但只针对于置换状态的数据。
 * @param Document_Id
 */
void restore(String Document_Id);
/**
 * 查询所有删除状态为1的数据
 * @return
 */
Object searchDel();
}
