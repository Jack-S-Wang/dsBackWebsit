package com.dascom.website.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dascom.website.dao.CatalogDao;
import com.dascom.website.entity.CollectionCatalog;
import com.dascom.website.service.CatalogService;

/**
 * 
*@author leisenquan
*2018年12月21日
 */
@Service
public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private CatalogDao catalogDao;


	@Override
	public void addCatalog(JSONObject json) {
		// TODO Auto-generated method stub
		String name=json.getString("name");
		String parentId=json.getString("parentId");
		String order=json.getString("order");
		CollectionCatalog catalog=new CollectionCatalog();
		catalog.setName(name);
		catalog.setParentId(parentId);
		catalog.setOrder(Integer.parseInt(order));
		catalog.setGenre("catal");
		catalog.setIshow(Integer.parseInt(json.getString("IShow")));
		catalogDao.addCatalog(catalog);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object searchCata(JSONObject json) {
		// TODO Auto-generated method stub
		//外部查询只需要对应的上级目录的全部
		List<CollectionCatalog> li=(List<CollectionCatalog>)catalogDao.searchCata("",json.getString("parentId"));
		//进行排序然后返回
		Collections.sort(li, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				CollectionCatalog cata1=(CollectionCatalog)o1;
				CollectionCatalog cata2=(CollectionCatalog)o2;
				if(cata1.getOrder()>cata2.getOrder()) {
					return 1;
				}else if(cata1.getOrder()==cata2.getOrder()) {
					return 0;
				}else {
					return -1;
				}
				
			}
			
		});
		return li;
	}

	@Override
	public void updateCata(JSONObject json) {
		// TODO Auto-generated method stub
		CollectionCatalog catalog=new CollectionCatalog();
		catalog.setId(json.getString("id"));
		catalog.setName(json.getString("name"));
		catalog.setParentId(json.getString("parentId"));
		catalog.setOrder(Integer.parseInt(json.getString("order")));
		catalog.setGenre("catal");
		catalog.setIshow(Integer.parseInt(json.getString("IShow")));
		catalogDao.updateCata(catalog);
	}

	@Override
	public void delCata(JSONObject json) {
		// TODO Auto-generated method stub
		catalogDao.delCata(json.getString("id"));
	}

}
