package com.dascom.website.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * app业务处理层，参数用json便于扩展
 * @author DS-031
 *
 */
@Service
public interface AppService {
	/**
	 * 添加app文件信息
	 * @param files
	 * @param json
	 */
void addApp(MultipartFile[] files,JSONObject json);

/**
 * 更新app信息
 * @param files
 * @param josn
 */
void updateApp(MultipartFile[] files,JSONObject json);

/**
 * 查询app信息,外部查询到的以number值为准的App信息
 * @param json
 */
Object searchApp(JSONObject json);

/**
 * 删除app
 * @param json
 */
void delApp(JSONObject json);
}
