###ueditor配置接口1

**简要描述：** 

-ueditor配置图片上传后台支持接口

**请求URL：** 

-`http://127.0.0.1:11010/config?action=config`

**请求方式：**

- GET 

**请求头:**


**参数：** 


**请求事例**

 **返回示例**
 
```
  成功
          {
      "data":  "{\"videoMaxSize\":102400000,\"videoActionName\":\"uploadvideo\",\"fileActionName\":\"uploadfile\",\"fileManagerListPath\":\"/ueditor/jsp/upload/file/\",\"imageCompressBorder\":1600,\"imageManagerAllowFiles\":[\".png\",\".jpg\",\".jpeg\",\".gif\",\".bmp\"],\"imageManagerListPath\":\"/images/upload/image/\"}",
       "code": 101000
    }
```

```
失败
          {
      "data": "异常错误",
       "code": 101006
    }
```

**返回参数说明**

| 参数名  |   类型     |说明|
| ------ | -------- |------|
| code | int |返回码|
|data|String|返回的数据|

**备注**
-

