package com.dascom.website.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.dascom.website.dao.EncloureDao;
import com.dascom.website.entity.CollectionEncloure;
import com.mongodb.WriteResult;
import com.mongodb.client.result.DeleteResult;

@Component("encloureDao")
public class EncloureDaoImpl implements EncloureDao {

	@Autowired
	@Qualifier("websiteMongoTemplate")
	MongoTemplate template;
	
	@Override
	public int addEncloure(CollectionEncloure encloure) {
		// TODO Auto-generated method stub
		template.insert(encloure);
		return 1;
	}

	@Override
	public int updateEncloure(CollectionEncloure encloure) {
		// TODO Auto-generated method stub
		template.save(encloure);
		return 1;
	}

	@Override
	public Object searchEncloure(String id) {
		// TODO Auto-generated method stub
		if(id!="" && id!=null) {
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return template.findOne(query, CollectionEncloure.class);
		}else {
			return template.findAll(CollectionEncloure.class);
		}
	}

	@Override
	public int delEncloure(String id) {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		DeleteResult result=template.remove(query, CollectionEncloure.class);
		return (int)result.getDeletedCount();
	}

}
