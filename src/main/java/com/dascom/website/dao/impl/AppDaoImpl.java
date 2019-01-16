package com.dascom.website.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.dascom.website.dao.AppDao;
import com.dascom.website.entity.CollectionApp;
import com.mongodb.WriteResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component("appDao")
public class AppDaoImpl implements AppDao{

	@Autowired
	@Qualifier("websiteMongoTemplate")
	MongoTemplate template;
	
	@Override
	public int addApp(CollectionApp app) {
		// TODO Auto-generated method stub
		template.insert(app);
		return 1;
	}

	@Override
	public void updateApp(CollectionApp app) {
		// TODO Auto-generated method stub		
		template.save(app);
	}

	@Override
	public int delApp(String id) {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		DeleteResult result=template.remove(query,CollectionApp.class);
		return (int)result.getDeletedCount();
	}

	@Override
	public CollectionApp searchApp(String id) {
		// TODO Auto-generated method stub		
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		CollectionApp app=template.findOne(query, CollectionApp.class);
		return app;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object searchAppN(String number,String genre) {
		// TODO Auto-generated method stub
		Object rj=new Object();
		Query query=new Query();
		query.addCriteria(Criteria.where("genre").is(genre));
		List<CollectionApp> li=template.find(query, CollectionApp.class);
		switch(number) {
		case "all":
			rj=li;
			break;
		case "new":
			Collections.sort(li, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					CollectionApp no1 = (CollectionApp) o1;
					CollectionApp no2 = (CollectionApp) o2;
					if (no1.getVersion().compareTo(no2.getVersion())>0) {
						return -1;
					} else if (no1.getVersion().compareTo(no2.getVersion())==0) {
						return 0;
					} else {
						return 1;
					}
				}
			});
			rj=li.get(0);
			break;
		case "10":
			Collections.sort(li, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					CollectionApp no1 = (CollectionApp) o1;
					CollectionApp no2 = (CollectionApp) o2;
					if (no1.getVersion().compareTo(no2.getVersion())>0) {
						return -1;
					} else if (no1.getVersion().compareTo(no2.getVersion())==0) {
						return 0;
					} else {
						return 1;
					}
				}
			});
			List<CollectionApp> lin=new ArrayList<>();
			int size=10;
			if(li.size()<10) {
				size=li.size();
			}
			for(int i=0;i<size;i++) {
				
				lin.add(li.get(i));
			}
			rj=lin;
			break;
		}
		return rj;
	}

}
