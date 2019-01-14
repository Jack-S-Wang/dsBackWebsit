###ueditor图片上传

**简要描述：** 

-ueditor图片上传

**请求URL：** 

-`http://127.0.0.1:13100/imgUpload`

**请求方式：**

- POST 

**请求头:**

-"application/x-www-form-urlencoded;charset=utf-8"

**参数：** 

| 参数名  | 必选 | 类型     |说明|
| ------ | -------- | -------- |------|
|file| 	是 | File| 	图片资源 |

**请求事例**

 **返回示例**
 
``` 
  成功
          {
      "data": {"original": "1537839092270.png",
				"title": "1537839092270.png",
    			"url": 				"https://test.dascomyun.cn/wifitools/images/upload/image/xxx.png"},
       "code": 101000
    }
```

```   
失败
          {
      "data": "执行失败",
       "code": 101004
    }
```

**返回参数说明**

| 参数名  |   类型     |说明|
| ------ | -------- |------|
| code | int |返回码|
|data|String|返回的数据|

**备注**
-

