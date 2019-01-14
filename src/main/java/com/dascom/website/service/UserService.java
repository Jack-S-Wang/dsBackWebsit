package com.dascom.website.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户业务处理层，参数json便于扩展
 * @author DS-031
 *
 */
@Service
public interface UserService {
	/**
	 * 添加新用户
	 * @param json
	 */
void addUser(JSONObject json);

/**
 * 更新用户
 * @param json
 */
void updateUser(JSONObject json);

/**
 * 查询用户
 * @param json
 * @return
 */
Object searchUser(JSONObject json);

/**
 * 删除用户
 * @param json
 */
void delUser(JSONObject json);
}
