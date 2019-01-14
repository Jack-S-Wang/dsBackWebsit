package com.dascom.website.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * 文件业务处理层，参数json便于扩展
 * @author DS-031
 *
 */
public interface EncloureService {
	
	/**
	 * 添加附件
	 * @param file
	 * @param json
	 * @return
	 */
	int addEncloure(MultipartFile[] file,JSONObject json);
	
	/**
	 * 更新附件
	 * @param file
	 * @param json
	 * @return
	 */
	int updateEncloure(MultipartFile[] file,JSONObject json);
	
	/**
	 * 查询附件
	 * @param json
	 * @return
	 */
	Object searchEncloure(JSONObject json);
	
	/**
	 * 删除附件
	 * @param json
	 * @return
	 */
	int delEncloure(JSONObject json);
}
