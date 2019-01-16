package com.dascom.website.dao.impl;

import java.util.Collection;
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

import com.dascom.website.dao.DocumentDao;
import com.dascom.website.entity.CollectionDocument;

@Component("documentDao")
public class DocumentDaoImp implements DocumentDao{

	@Autowired
	@Qualifier("websiteMongoTemplate")
	MongoTemplate template;
	
	@Override
	public void addDocument(CollectionDocument doc) {
		// TODO Auto-generated method stub
		template.insert(doc);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object searchDocument(String Document_Id,String id,String status) {
		// TODO Auto-generated method stub
		Query query=new Query();
		if(Document_Id!="" && Document_Id!=null) {
			//查询所有的历史数据
			query.addCriteria(Criteria.where("document_Id").is(Document_Id));
			//需要返回修改审核状态的数据
			if(status!=null && status!="") {
				query.addCriteria(Criteria.where("status").is(Integer.parseInt(status)));
				return template.findOne(query, CollectionDocument.class);
			}
			List<CollectionDocument> li=template.find(query, CollectionDocument.class);
			Collections.sort(li, new Comparator() {

				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					CollectionDocument doc1=(CollectionDocument)o1;
					CollectionDocument doc2=(CollectionDocument)o2;
					if(doc1.getModifyDate().compareTo(doc2.getModifyDate())>0) {
						return 1;
					}else if(doc1.getModifyDate().compareTo(doc2.getModifyDate())==0) {
						return 0;
					}else {
						return -1;
					}
				}
				
			});
			
			return li;
		}else if(id!="" && id != null) {
			//查询当前文档
			query.addCriteria(Criteria.where("id").is(id));
			return template.findOne(query, CollectionDocument.class);
		}
		return null;
	}
	
	

	@Override
	public void updateDocument(CollectionDocument doc) {
		// TODO Auto-generated method stub
		template.save(doc);
	}

	@Override
	public void delDocument(String Document_Id) {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("document_Id").is(Document_Id));
		Update update=Update.update("idel",1 );
		template.updateMulti(query, update, CollectionDocument.class);
	}


	@Override
	public void del() {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("idel").is(1));
		template.remove(query, CollectionDocument.class);
	}


	@Override
	public Object searchShow() {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("ishow").is(1).and("idel").is(0));
		return template.find(query, CollectionDocument.class);
	}


	@Override
	public void restore(String Document_Id) {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("document_Id").is(Document_Id));
		Update update=Update.update("idel", 0);
		template.updateMulti(query, update, CollectionDocument.class);
	}


	@Override
	public void updateDocument(String Document_Id) {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("document_Id").is(Document_Id));
		Update update=Update.update("ishow", 0);
		template.updateMulti(query, update, CollectionDocument.class);
	}


	@Override
	public void del(String id) {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(id));
		template.remove(query, CollectionDocument.class);
	}


	@Override
	public Object searchDel() {
		// TODO Auto-generated method stub
		Query query=new Query();
		query.addCriteria(Criteria.where("idel").is(1));
		return template.find(query, CollectionDocument.class);
	}




}
