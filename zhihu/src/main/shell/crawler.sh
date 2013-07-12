#!/bin/sh
EXEC_CLASS="com.dajie.crawler.zhihu.ZHCrawler"

case $1 in
start)
    server_home="/ROOT/project/crawler/zhihu"
    LIB_DIR=/ROOT/project/crawler/zhihu/lib
    LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`
    LIB_JARS=$LIB_JARS:$server_home/dajie_crawler_zhihu.properties

    nohup /ROOT/server/jdk/bin/java -server -Xms1024M -Xmx1024M -Xmn768M -Xss1024k -XX:PermSize=32M -XX:MaxPermSize=64M -XX:SurvivorRatio=6 -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=2 -XX:-CMSParallelRemarkEnabled -XX:+DisableExplicitGC -XX:CMSInitiatingOccupancyFraction=80 -XX:SoftRefLRUPolicyMSPerMB=0  -Xloggc:/ROOT/logs/crawler-zhihu/gc_crawler_zhihu.log  -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -classpath $LIB_JARS $EXEC_CLASS &
    ;;

status)
    echo `ps aux|grep "$EXEC_CLASS" |grep 'grep' -v|awk '{print $2}'`
    ;;

stop)
    pid=`ps aux|grep "$EXEC_CLASS" |grep 'grep' -v|awk '{print $2}'`
    kill -9 $pid
    ;;

esac