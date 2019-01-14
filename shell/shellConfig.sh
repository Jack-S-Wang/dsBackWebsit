imgPath="192.168.11.202:8887/dascomyunwebsite/backstage_1010"  
imgName="backstage_1010"
usehost="root@192.168.11.197"
dockerPort="-p 21010:21010"
mountPath="-v /home/docker/Dascomyunwebsite-backstage_1010/config/:/service/config/ -v /home/docker/Dascomyunwebsite-backstage_1010/logs/:/service/logs/ -v /home/docker/Dascomyunwebsite-backstage_1010/ts-www/:/service/ts-www/"

dir="./docs" 
source ../shell/jenkins.sh
source ../shell/writeFile.sh