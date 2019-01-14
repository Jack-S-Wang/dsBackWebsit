#!/bin/bash
#获取镜像信息
img_ID=`docker images | awk '{ print $1 }' | awk 'NR==2{print}'`
img_TAG=`docker images | awk '{ print $2 }' | awk 'NR==2{print}'`
img_IP="111.230.210.178:4960/dascomyunwebsite-backstage_1010"
img_MZ="backstage_1010"
#修改镜像名称
docker tag  ${img_ID}:${img_TAG}   ${img_IP}:${img_TAG}

#上传镜像
docker push ${img_IP}:${img_TAG}

#上传失败则在报错信息中输出
if [ $? -ne 0 ];then
  echo " ${img_IP}:${img_TAG} push失败! "

else
#上传成功则删除镜像
  docker  rmi  -f  ${img_IP}:${img_TAG}
 
fi
docker  rmi  -f  ${img_ID}:${img_TAG}
#执行本项目构建脚本
#指定云主机运行
USERHOST="ceshi@123.207.66.34"

#判断容器是否存在 
if  ssh  ${USERHOST}   docker ps -a | grep -i ${img_MZ}-server;then
    ssh  ${USERHOST}   docker stop  ${img_MZ}-server
    ssh  ${USERHOST}   docker rm -f   ${img_MZ}-server
fi

ssh  ${USERHOST}  docker pull ${img_IP}:${img_TAG}

ssh  ${USERHOST}  docker container create --name ${img_MZ}-server  -p 13100:13100    -v /home/docker/dswebsite/config/:/config -v /home/docker/dswebsite/images/:/images/    -v  /home/docker/dswebsite/logs/:/logs/  --restart=always  ${img_IP}:${img_TAG}

ssh  ${USERHOST}   docker container start ${img_MZ}-server



