package com.dascom.website.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dascom.website.dao.AppDao;
import com.dascom.website.dao.CatalogDao;
import com.dascom.website.dao.DocumentDao;
import com.dascom.website.dao.EncloureDao;
import com.dascom.website.dao.impl.AppDaoImpl;
import com.dascom.website.entity.CollectionApp;
import com.dascom.website.entity.CollectionCatalog;
import com.dascom.website.entity.CollectionDocument;
import com.dascom.website.entity.CollectionEnUrl;
import com.dascom.website.entity.CollectionEncloure;
import com.dascom.website.service.AppService;
import com.dascom.website.service.EncloureService;

@Service
public class EncloureServiceImpl implements EncloureService,AppService {

	@Autowired
	AppDao appDao;
	
	@Autowired
	CatalogDao catalogDao;

	@Autowired
	EncloureDao encloureDao;

	@Autowired
	DocumentDao documentDao;

	@Value("${filehead}")
	private String headLoad;

	@Value("${url}")
	private String returnUrl;

	@Override
	public int addEncloure(MultipartFile[] file, JSONObject json) {
		// TODO Auto-generated method stub
		int recode = 0;
		String prefix = "";
		for (MultipartFile fl : file) {
			String ti = new Date().getTime() + "";
				CollectionEncloure encloure = new CollectionEncloure();
				prefix = fl.getOriginalFilename().substring(fl.getOriginalFilename().lastIndexOf(".") + 1);
				SaveFile(fl, json, prefix, ti,"encloure");
				// 由于自动生成之后无法找到该对应的数据id，下面的文档或图片就不能正确插入相同id的附件信息
				String id = new Date().getTime() + "";
				encloure.setId(id);
				encloure.setCode(json.getString("code"));
				encloure.setSize(getM2((float)fl.getSize()/1024/1024)+"MB");
				encloure.setUpdateContent(json.getString("updateContent"));
				encloure.setVersion(json.getString("version"));
				encloure.setName(json.getString("name"));
				encloure.setUrl(returnUrl + "encloures/" + ti + "." + prefix);
				encloure.setCata_Id(json.getString("cata_Id"));
				encloure.setDoc_Id(json.getString("Doc_Id"));
				encloure.setIshow(Integer.parseInt(json.getString("IShow")));
				recode = encloureDao.addEncloure(encloure);

				// 再添加到对应的表中
				if (encloure.getCata_Id()!=null && !"".equals(encloure.getCata_Id())) {
					CollectionCatalog cata=new CollectionCatalog();
					cata.setId(id);
					cata.setGenre("encloure");
					cata.setName(encloure.getName());
					cata.setOrder(99);
					cata.setParentId(encloure.getCata_Id());
					cata.setUrl(encloure.getUrl());
					cata.setIshow(0);
					catalogDao.addCatalog(cata);
				} else {
					CollectionEnUrl enUrl=new CollectionEnUrl();
					enUrl.setId(encloure.getId());
					enUrl.setName(encloure.getName());
					enUrl.setUrl(encloure.getUrl());
					CollectionDocument document = (CollectionDocument) documentDao
							.searchDocument("",encloure.getDoc_Id(),"");
					if (document.getEncloures() != null) {
						document.getEncloures().add(enUrl);
					} else {
						List<CollectionEnUrl> li = new ArrayList<>();
						li.add(enUrl);
						document.setEncloures(li);
					}
					document.setModifyDate(new Date());
					documentDao.updateDocument(document);
				}
				break;
		}
		return recode;
	}

	/**
	 * 返回保留2为小数的值
	 * 
	 * @param number
	 * @return
	 */
	private String getM2(float number) {
//	DecimalFormat df = new DecimalFormat("#.00");
//	 return df.format(number);
		return String.format("%.2f", number);
	}

	@Override
	public int updateEncloure(MultipartFile[] file, JSONObject json) {
		// TODO Auto-generated method stub
		String prefix = "";
		int recode = 0;
			CollectionEncloure encloure = new CollectionEncloure();
			// 先删除原来的文件
			delFile(json,"encloure");
			// 说明需要更换文件
				String ti = new Date().getTime() + "";
				prefix = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf(".") + 1);
				// 保存现有文件
				SaveFile(file[0], json, prefix, ti,"encloure");
				encloure.setId(json.getString("id"));
				encloure.setName(json.getString("name"));
				encloure.setCata_Id(json.getString("cata_Id"));
				encloure.setCode(json.getString("code"));
				encloure.setDoc_Id(json.getString("Doc_Id"));
				encloure.setSize(getM2((float)file[0].getSize()/1024/1024)+"MB");
				encloure.setUpdateContent(json.getString("updateContent"));
				encloure.setVersion(json.getString("version"));
				encloure.setUrl(returnUrl + "encloures/" + ti + "." + prefix);
				encloure.setIshow(Integer.parseInt(json.getString("IShow")));
				recode = encloureDao.updateEncloure(encloure);
				// 再更新到对应的表中
				if (encloure.getCata_Id()!=null && !"".equals(encloure.getCata_Id())) {
					CollectionCatalog cata=new CollectionCatalog();
					cata.setId(encloure.getId());
					cata.setGenre("encloure");
					cata.setName(encloure.getName());
					cata.setUrl(encloure.getUrl());
					cata.setOrder(99);
					cata.setParentId(encloure.getCata_Id());
					cata.setIshow(encloure.getIshow());
					catalogDao.updateCata(cata);
				} else {
					//业务中文件与文档关联的是不会改变对应的文档id的，因此不担心文件更新到其他的文档中
					CollectionDocument document = (CollectionDocument) documentDao
							.searchDocument("",encloure.getDoc_Id(),"");
					if (document.getEncloures() != null) {
						for (CollectionEnUrl en : document.getEncloures()) {
							if (en.getId().equals(encloure.getId())) {
								en.setName(encloure.getName());
								en.setUrl(encloure.getUrl());
								break;
							}
						}
					}
					document.setModifyDate(new Date());
					documentDao.updateDocument(document);
				}
		return recode;
	}

	/**
	 * 删除原有文件
	 * 
	 * @param json
	 */
	private void delFile(JSONObject json,String who) {
		String url = "";
		if("app".equals(who)) {
			CollectionApp ap=(CollectionApp)appDao.searchApp(json.getString("id"));
			url=ap.getUrl();
			if("ios".equals(json.getString("genre"))) {
				String[] sl=url.split("/");
				url=url.substring(0, url.length()-sl[sl.length-1].length());
			}
		}else {
		CollectionEncloure encloure=(CollectionEncloure)encloureDao.searchEncloure(json.getString("id"));
		url=encloure.getUrl();
		}
		url=url.substring(returnUrl.length());
		File fl = new File(headLoad + url);
		//删除所有的文件
		delFile(fl);
		}
	
	private void delFile(File f) {
		// 删除
					try {
						if(f.isFile()) {
						f.delete();
						}else if(f.isDirectory()){
							 for (String fs:f.list()) {
						            String u=f.getPath()+"/"+fs;
						            File fl=new File(u);
						            delFile(fl);
						        }
							 f.delete();
						}
					} catch (Exception ex) {
						throw ex;
					}

	}

	/**
	 * 保存附件到本地
	 * 
	 * @param file
	 * @param json
	 * @throws IOException
	 */
	private void SaveFile(MultipartFile file, JSONObject json, String prefix, String ti,String who) {
		String path = "";
		switch (who) {
		case "app":
			String code = json.getString("code");
			String genre=json.getString("genre");
			String v="";
			if("ios".equals(genre)) {
				v=json.getString("version")+"/";
			}
			path = headLoad + "apps/" +genre+"/"+ code + "/" +v+ ti + "." + prefix;
			break;
		case "encloure":
			path = headLoad + "encloures/" + ti + "." + prefix;
			break;
		}

		File fl = new File(path);
		if (!fl.getParentFile().exists()) {
			// 不存在则创建父级文件夹
			fl.getParentFile().mkdirs();
		}
		try {
			InputStream ins;
			ins = file.getInputStream();

			OutputStream ops = new FileOutputStream(fl);
			byte[] b = new byte[1024];
			while (-1 != ins.read(b, 0, b.length)) {
				ops.write(b, 0, b.length);
			}
			ins.close();
			ops.close();
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}

	
	@Override
	public Object searchEncloure(JSONObject json) {
		// TODO Auto-generated method stub
			return encloureDao.searchEncloure(json.getString("id"));
	}

	@Override
	public int delEncloure(JSONObject json) {
		// TODO Auto-generated method stub
			// 删除文件
			delFile(json,"encloure");
			// 删除数据
			encloureDao.delEncloure(json.getString("id"));
			String cata_Id = json.getString("cata_Id");
			if (cata_Id!=null && !"".equals(cata_Id)) {
				catalogDao.delCata(json.getString("id"));
			} else {
				CollectionDocument document = (CollectionDocument) documentDao
						.searchDocument("",json.getString("Doc_Id"),"");
				final Iterator<CollectionEnUrl> each = document.getEncloures().iterator();
				while (each.hasNext()) {
					CollectionEnUrl en = each.next();
					if (en.getId().equals(json.getString("id"))) {
						each.remove();
						break;
					}
				}
				document.setModifyDate(new Date());
				documentDao.updateDocument(document);
			}
		return 1;
	}

	@Override
	public void addApp(MultipartFile[] files, JSONObject json) {
		// TODO Auto-generated method stub
		String genre=json.getString("genre");
		switch(genre) {
		case "ios":
			String size="0";
			for(MultipartFile fl:files) {
				//先保存图片
				String ti = new Date().getTime() + "";
				String prefix =fl.getOriginalFilename().substring(fl.getOriginalFilename().lastIndexOf(".") + 1);
				SaveFile(fl,json,prefix,ti,"app");
				//获取文件为ipa的大小
				if("ipa".equals(prefix)) {
				size=getM2((float)fl.getSize()/1024/1024);
				}
				//文件为plist是要传入数据库的，并返回返回路径
				if("plist".equals(prefix)) {
				CollectionApp app =new CollectionApp();
				app.setCode(json.getString("code"));
				app.setName(json.getString("name"));
				app.setSize(size+"Mb");
				app.setUpdateContent(json.getString("updateContent"));
				app.setUrl(returnUrl + "apps/" +genre+"/"+ json.getString("code") + "/" +json.getString("version")+"/"+ ti + "." + prefix);
				app.setVersion(json.getString("version"));
				app.setGenre(genre);
				appDao.addApp(app);
				}
			}
			break;
		case "android":
			String ti = new Date().getTime() + "";
			String prefix =files[0].getOriginalFilename().substring(files[0].getOriginalFilename().lastIndexOf(".") + 1);
			SaveFile(files[0],json,prefix,ti,"app");
			CollectionApp app =new CollectionApp();
			app.setCode(json.getString("code"));
			app.setName(json.getString("name"));
			app.setSize(getM2((float)files[0].getSize()/1024/1024)+"Mb");
			app.setUpdateContent(json.getString("updateContent"));
			app.setUrl(returnUrl + "apps/" +genre+"/"+ json.getString("code") + "/" + ti + "." + prefix);
			app.setVersion(json.getString("version"));
			app.setGenre(genre);
			appDao.addApp(app);
			break;
		}
	}

	@Override
	public void updateApp(MultipartFile[] files, JSONObject json) {
		// TODO Auto-generated method stub
		//先删除原始文件
		delFile(json,"app");
		String genre=json.getString("genre");
		switch(genre) {
		case "ios":
			String size="0";
			for(MultipartFile fl:files) {
				//先保存图片
				String ti = new Date().getTime() + "";
				String prefix =fl.getOriginalFilename().substring(fl.getOriginalFilename().lastIndexOf(".") + 1);
				SaveFile(fl,json,prefix,ti,"app");
				//获取文件为ipa的大小
				if("ipa".equals(prefix)) {
				size=getM2((float)fl.getSize()/1024/1024);
				}
				//文件为plist是要传入数据库的，并返回返回路径
				if("plist".equals(prefix)) {
				CollectionApp app =new CollectionApp();
				app.setId(json.getString("id"));
				app.setCode(json.getString("code"));
				app.setName(json.getString("name"));
				app.setSize(size+"Mb");
				app.setUpdateContent(json.getString("updateContent"));
				app.setUrl(returnUrl + "apps/" +genre+"/"+ json.getString("code") + "/" +json.getString("version")+"/"+ ti + "." + prefix);
				app.setVersion(json.getString("version"));
				app.setGenre(genre);
				appDao.updateApp(app);
				}
			}
			break;
		case "android":
			String ti = new Date().getTime() + "";
			String prefix =files[0].getOriginalFilename().substring(files[0].getOriginalFilename().lastIndexOf(".") + 1);
			SaveFile(files[0],json,prefix,ti,"app");
			CollectionApp app =new CollectionApp();
			app.setId(json.getString("id"));
			app.setCode(json.getString("code"));
			app.setName(json.getString("name"));
			app.setSize(getM2((float)files[0].getSize()/1024/1024)+"Mb");
			app.setUpdateContent(json.getString("updateContent"));
			app.setUrl(returnUrl + "apps/" +genre+"/"+ json.getString("code") + "/" + ti + "." + prefix);
			app.setVersion(json.getString("version"));
			app.setGenre(genre);
			appDao.updateApp(app);
			break;
		}
	}

	@Override
	public Object searchApp(JSONObject json) {
		// TODO Auto-generated method stub
		String genre=json.getString("genre");
		String number=json.getString("number");
		return appDao.searchAppN(number, genre);
	}

	@Override
	public void delApp(JSONObject json) {
		// TODO Auto-generated method stub
		//先删除文件
		delFile(json,"app");
		appDao.delApp(json.getString("id"));
		
	}

}
