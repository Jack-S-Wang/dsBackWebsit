package com.dascom.website.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.dascom.website.dao.CatalogDao;
import com.dascom.website.entity.CollectionCatalog;
import com.mongodb.WriteResult;
import com.mongodb.client.model.DeleteManyModel;
import com.mongodb.client.result.DeleteResult;

/**
 * 
*@author leisenquan
*2018年12月20日
 */
@Repository
public class CatalogDaoImpl implements CatalogDao {
	
	@Autowired
	@Qualifier("websiteMongoTemplate")
	MongoTemplate websiteMongoTemplate;

	@Override
	public int addCatalog(CollectionCatalog cata) {
		websiteMongoTemplate.insert(cata);
		return 1;
	}

	@Override
	public Object searchCata(String id,String parentId) {
		Query query = new Query();
		if(id!="" && id != null) {
			//查询对应的某条数据
			query.addCriteria(Criteria.where("id").is(id));
			return websiteMongoTemplate.findOne(query, CollectionCatalog.class);
		}else {
			//查询所有外部展示的数据
		query.addCriteria(Criteria.where("parentId").is(parentId));
		List<CollectionCatalog> li=websiteMongoTemplate.find(query, CollectionCatalog.class);
		return li;
		}
	}

	@Override
	public int updateCata(CollectionCatalog cata) {
		websiteMongoTemplate.save(cata);
		return 0;
	}

	@Override
	public int delCata(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		DeleteResult result=websiteMongoTemplate.remove(query, CollectionCatalog.class);
		return (int)result.getDeletedCount();
	}
	
}
