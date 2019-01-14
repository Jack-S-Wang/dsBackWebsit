package com.dascom.website.controller;

import org.apache.logging.log4j.Logger;


import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dascom.website.service.AppService;
import com.dascom.website.service.CatalogService;
import com.dascom.website.service.DocumentService;
import com.dascom.website.service.EncloureService;
import com.dascom.website.service.UserService;

/**
 * 官网管理的所有接口内容
 * 
 * @author DS-031
 *
 */
@RestController
public class ManageController {
	private static final Logger log = LogManager.getLogger(ManageController.class);

	@Autowired
	EncloureService encloure;

	@Autowired
	CatalogService catalog;
	
	@Autowired
	DocumentService document;
	
	@Autowired
	AppService appService;
	
	@Autowired
	UserService user;

	/**
	 * 文件相关接口,设置了多文件上传，但外部并没有说明，这是因为还没有明确业务文件上传也需要多文件上传,文件是没有查询功能的
	 * @param file
	 * @param id
	 * @param Document_Id
	 * @param cata_Id
	 * @param updateContent
	 * @param name
	 * @param version
	 * @param code
	 * @param type
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/v3.0/attachment", method = RequestMethod.POST)
	public Object attachmentIn(MultipartFile[] file, String id, String Doc_Id, String cata_Id,
			String updateContent, String name, String version, String code, String IShow,String type) {
		JSONObject rs = new JSONObject();
		if ((type == null || type == "")) {
			rs.put("code", 101001);
			rs.put("data", "必填参数为空了");
			return rs;
		}
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("Doc_Id", Doc_Id);
			json.put("cata_Id", cata_Id);
			json.put("updateContent", updateContent);
			json.put("name", name);
			json.put("version", version);
			json.put("code", code);
			json.put("IShow", IShow);
			switch (type) {
			case "add":
				if (encloure.addEncloure(file, json) > 0) {
					rs.put("code", 101000);
					rs.put("data", "执行成功");
				} else {
					rs.put("code", 101002);
					rs.put("data", "执行失败");
				}
				break;
			case "update":
				if (encloure.updateEncloure(file, json) > 0) {
					rs.put("code", 101000);
					rs.put("data", "执行成功");
				} else {
					rs.put("code", 101002);
					rs.put("data", "执行失败");
				}
				break;
			case "del":
				if (encloure.delEncloure(json) > 0) {
					rs.put("code", 101000);
					rs.put("data", "执行成功");
				} else {
					rs.put("code", 101002);
					rs.put("data", "执行失败");
				}
				break;
			case "search":
				Object ob=encloure.searchEncloure(json);
				rs.put("code", 101000);
				rs.put("data", ob);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace());
			rs.put("code", 101003);
			rs.put("data", "有异常" + ex.getMessage());
			return rs;
		}
		return rs;
	}
	
	/**
	 * app相关接口
	 * @param files
	 * @param id
	 * @param updateContent
	 * @param name
	 * @param version
	 * @param number
	 * @param code
	 * @param type
	 * @param genre
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/v3.0/application", method = RequestMethod.POST)
	public Object applicationIn(MultipartFile[] files, String id,
			String updateContent, String name, String version,String number, 
			String code, String type,String genre) {
		JSONObject rs=new JSONObject();
		if ((type == null || type == "")) {
			rs.put("code", 101001);
			rs.put("data", "必填参数为空了");
			return rs;
		}
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("updateContent", updateContent);
			json.put("name", name);
			json.put("version", version);
			json.put("number", number);
			json.put("code", code);
			json.put("genre", genre);
			switch (type) {
			case "add":
				appService.addApp(files, json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "update":
				appService.updateApp(files, json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "del":
				appService.delApp(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "search":
				Object reob=appService.searchApp(json);
				rs.put("code", 101000);
				rs.put("data", reob);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace());
			rs.put("code", 101003);
			rs.put("data", "有异常" + ex.getMessage());
			return rs;
		}
		return rs;
	}
	

	/**
	 * 目录相关接口
	 * 
	 * @param json
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/v3.0/catalgue", method = RequestMethod.POST)
	public Object catalgueIn(@RequestBody JSONObject json) {
		JSONObject rs = new JSONObject();
		if ((json.getString("type") == null || json.getString("type") == "")) {
			rs.put("code", 101001);
			rs.put("data", "必填参数为空了");
			return rs;
		}
		try {
			String type = json.getString("type");
			switch (type) {
			case "add":
				catalog.addCatalog(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "update":
				catalog.updateCata(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "search":
				Object ob = catalog.searchCata(json);
				rs.put("code", 101000);
				rs.put("data", ob);
				break;
			case "del":
				catalog.delCata(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			}
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace());
			rs.put("code", 101003);
			rs.put("data", "有异常错误");
			return rs;
		}
	}
	
	
	/**
	 * 文档接口
	 * @param json
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/v3.0/content", method = RequestMethod.POST)
	public Object documentIn(@RequestBody JSONObject json) {		
		JSONObject rs = new JSONObject();
		if ((json.getString("type") == null || json.getString("type") == "")) {
			rs.put("code", 101001);
			rs.put("data", "必填参数为空了");
			return rs;
		}
		try {
			String type = json.getString("type");
			switch (type) {
			case "add":
				document.addDocument(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "update":
				document.updateDocument(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "search":
				Object ob = document.searchDocument(json);
				rs.put("code", 101000);
				rs.put("data", ob);
				break;
			case "del":
				document.delDocument(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "searchShow":
				Object alo=document.searchShow();
				rs.put("code", 101000);
				rs.put("data", alo);
				break;
			case "clear":
				document.del();
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "restore":
				document.restore(json.getString("Document_Id"));
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "searchDel":
				Object dob=document.searchDel();
				rs.put("code", 101000);
				rs.put("data", dob);
				break;
			}
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace());
			rs.put("code", 101003);
			rs.put("data", "有异常错误");
			return rs;
		}
	}
	
	
	/**
	 * 管理员用户账号
	 * @param json
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/v3.0/admin", method = RequestMethod.POST)
	public Object userIn(@RequestBody JSONObject json) {
		JSONObject rs = new JSONObject();
		if ((json.getString("type") == null || json.getString("type") == "")) {
			rs.put("code", 101001);
			rs.put("data", "必填参数为空了");
			return rs;
		}
		try {
			String type = json.getString("type");
			switch (type) {
			case "add":
				user.addUser(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "update":
				user.updateUser(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			case "search":
				Object ob = user.searchUser(json);
				rs.put("code", 101000);
				rs.put("data", ob);
				break;
			case "del":
				user.delUser(json);
				rs.put("code", 101000);
				rs.put("data", "执行成功");
				break;
			}
			return rs;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex.getStackTrace());
			rs.put("code", 101003);
			rs.put("data", "有异常错误");
			return rs;
		}
	}

}
