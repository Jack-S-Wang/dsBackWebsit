package com.dascom.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dascom.website.dao.UserDao;
import com.dascom.website.entity.CollectionUser;
import com.dascom.website.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	@Override
	public void addUser(JSONObject json) {
		// TODO Auto-generated method stub
		CollectionUser user=new CollectionUser();
		user.setName(json.getString("name"));
		user.setParentId(json.getString("parentId"));
		user.setPwd(json.getString("pwd"));
		if(user.getParentId()!=null ||user.getParentId()!="") {
		user.setGenre("guest");
		}else {
			user.setGenre("host");
		}
		userDao.addUser(user);
	}

	@Override
	public void updateUser(JSONObject json) {
		// TODO Auto-generated method stub
		CollectionUser user=new CollectionUser();
		user.setId(json.getString("id"));
		user.setName(json.getString("name"));
		user.setParentId(json.getString("parentId"));
		user.setPwd(json.getString("pwd"));
		if(user.getParentId()!=null ||user.getParentId()!="") {
		user.setGenre("guest");
		}else {
			user.setGenre("host");
		}
		userDao.updateUser(user);
	}

	@Override
	public Object searchUser(JSONObject json) {
		// TODO Auto-generated method stub
		return userDao.searchUser(json.getString("id"), json.getString("number"));
	}

	@Override
	public void delUser(JSONObject json) {
		// TODO Auto-generated method stub
		userDao.delUser(json.getString("id"));
	}

}
