package com.dascom.website.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dascom.website.dao.UserDao;
import com.dascom.website.entity.CollectionUser;

@Component("userDao")
public class UserDaoImpl implements UserDao{

	@Autowired
	@Qualifier("websiteMongoTemplate")
	MongoTemplate template;
	
	@Override
	public void addUser(CollectionUser user) {
		// TODO Auto-generated method stub
		template.insert(user);
	}

	@Override
	public void updateUser(CollectionUser user) {
		// TODO Auto-generated method stub
		template.save(user);
	}

	@Override
	public Object searchUser(String id, String number) {
		// TODO Auto-generated method stub
		Query query=new Query();
		if(number=="" || number==null) {
			query.addCriteria(Criteria.where("id").is(id));
			return template.findOne(query, CollectionUser.class);
		}else {
			if("all".equals(number)) {
				return template.findAll(CollectionUser.class);
			}else if("list".equals(number)) {
				query.addCriteria(Criteria.where("parentId").is(id));
				return template.find(query, CollectionUser.class);
			}else {
				return null;
			}
			
		}
		
	}

	@Override
	public void delUser(String id) {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(new ObjectId(id)));
		template.remove(query,CollectionUser.class);
	}

}
