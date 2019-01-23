package com.dascom.website.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dascom.website.dao.CatalogDao;
import com.dascom.website.dao.DocumentDao;
import com.dascom.website.entity.CollectionCatalog;
import com.dascom.website.entity.CollectionDocument;
import com.dascom.website.entity.CollectionEnUrl;
import com.dascom.website.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService{

	@Autowired
	DocumentDao documentDao;
	@Autowired
	CatalogDao catalogDao;
	
	@Override
	public void addDocument(JSONObject json) {
		// TODO Auto-generated method stub
		String Document_Id=(new Date()).getTime()+"";
		CollectionDocument document=new CollectionDocument();
		document.setDocument_Id(Document_Id);
		document.setAlias(json.getString("alias"));
		document.setCata_Id(json.getString("cata_Id"));
		document.setContent(json.getString("content"));
		Date ti=new Date();
		document.setCreateDate(ti);
		document.setModifyDate(ti);
		document.setName(json.getString("name"));
		document.setOrder(Integer.parseInt(json.getString("order")));
		document.setUser(json.getString("user"));
		document.setStatus(0);
		document.setIdel(0);
		document.setIshow(1);
		documentDao.addDocument(document);
	}

	@Override
	public void updateDocument(JSONObject json) {
		// TODO Auto-generated method stub
		String Document_Id=json.getString("Document_Id");
		//先查询该文档的历史记录有几条数据
		@SuppressWarnings("unchecked")
		List<CollectionDocument> d=(List<CollectionDocument>)documentDao.searchDocument(Document_Id,"","");
		//大于6条了说明已经记录了5条历史记录，需要删除最早的那一条不保存记录
		if(d.size()>=6) {
			if(d.get(0).getStatus()==1) {
				documentDao.del(d.get(1).getId());
			}else {
			documentDao.del(d.get(0).getId());
			}
		}
		//首先将原来的附件信息拷贝下来
		CollectionDocument doc=(CollectionDocument)documentDao.searchDocument("",json.getString("id"),"");
		//原来的数据需要更新时间，以免在同一条数据处理保存达到五次以上，出现删除的问题
		doc.setModifyDate(new Date());
		documentDao.updateDocument(doc);
		//置换原始数据的显示状态
		documentDao.updateDocument(Document_Id);
		//保存并新增一条新纪录
		//用时间戳来标识id值是为了添加的新数据在目录表中添加时直接可以获取到id值，不需要重新查询的繁琐
		String ti=(new Date()).getTime()+"";
		CollectionDocument document=new CollectionDocument();
		document.setId(ti);
		document.setDocument_Id(Document_Id);//属于同一个文档
		document.setAlias(json.getString("alias"));
		document.setCata_Id(json.getString("cata_Id"));
		document.setContent(json.getString("content"));
		document.setModifyDate(new Date());
		document.setName(json.getString("name"));
		document.setCreateDate(doc.getCreateDate());
		document.setOrder(Integer.parseInt(json.getString("order")));
		document.setUser(json.getString("user"));
		//要将原来的附件信息赋值回去，只有在附件处理的接口上才处理该附件信息
		document.setEncloures(doc.getEncloures());
		document.setStatus(Integer.parseInt(json.getString("status")));
		document.setIdel(doc.getIdel());
		document.setIshow(1);
		//判断是否审核了
		if(json.getString("status").equals("1")) {
			//先找到可能需要修改的数据
			CollectionDocument old=(CollectionDocument)documentDao.searchDocument(Document_Id,"","1");
			//将刚才找出来的老数据对生成的目录数据进行处理
			if(old!=null) {
				old.setStatus(0);
				documentDao.updateDocument(old);
			//删除原先的目录数据
			catalogDao.delCata(old.getId());
			}
			//生成或更新目录表
			CollectionCatalog catalog=new CollectionCatalog();
			catalog.setId(ti);
			catalog.setName(json.getString("name"));
			catalog.setParentId(json.getString("cata_Id"));
			catalog.setOrder(Integer.parseInt(json.getString("order")));
			catalog.setGenre("doc");
			catalog.setIshow(1);
			catalogDao.addCatalog(catalog);
		}else {
			//不删除，保留原始的审核数据在目录上。
		}
		documentDao.updateDocument(document);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object searchDocument(JSONObject json) {
		// TODO Auto-generated method stub
		Object ob=documentDao.searchDocument(json.getString("Document_Id"), json.getString("id"),"");
		if(json.getString("id")=="" || json.getString("id")==null) {
			//说明是返回的是集合
			List<CollectionDocument> li=(List<CollectionDocument>)ob;
			//查询到最新的历史数据
			if("new".equals(json.getString("number"))) {
				int count=li.size()-1;
				return li.get(count);
			}
			return li;
		}
		return ob;
	}
	
	

	@Override
	public void delDocument(JSONObject json) {
		// TODO Auto-generated method stub
		//先查询文档中谁是认证的
		CollectionDocument doc=(CollectionDocument)documentDao.searchDocument(json.getString("Document_Id"), "","1");
		if(doc!=null) {
		if(catalogDao.searchCata(doc.getId(), "")!=null) {
			catalogDao.delCata(doc.getId());
		}
		}
		documentDao.delDocument(json.getString("Document_Id"));
		
	}

	@Override
	public void del() {
		// TODO Auto-generated method stub
		documentDao.del();
	}

	@Override
	public Object searchShow() {
		// TODO Auto-generated method stub
		return documentDao.searchShow();
	}

	@Override
	public void restore(String Document_Id) {
		// TODO Auto-generated method stub
		documentDao.restore(Document_Id);
	}

	@Override
	public Object searchDel() {
		// TODO Auto-generated method stub
		return documentDao.searchDel();
	}

}
