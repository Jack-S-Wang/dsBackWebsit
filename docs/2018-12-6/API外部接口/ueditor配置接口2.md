###ueditor配置接口2

**简要描述：** 

-ueditor配置图片在线管理后台支持接口，获取所有图片接口

**请求URL：** 

-`http://127.0.0.1:13100/config?action=listimage`

**请求方式：**

- GET 

**请求头:**


**参数：** 


**请求事例**

 **返回示例**
 
``` 
  成功
          {
      "data": "{"total": 2, "start": 0, "state": "SUCCESS","list": 				[{"alt":"images/upload/image/1537254857271.jpg", "state": "SUCCESS",
				"url": 				"https://test.dascomyun.cn/wifitools/images/upload/image/1537254857271.jp				g"
        		},
        		{
            	"alt": "images/upload/image/1537255826302.jpg",
            	"state": "SUCCESS",
            	"url": 				"https://test.dascomyun.cn/wifitools/images/upload/image/1537255826302.jpg"
        		}
    ]}",
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

