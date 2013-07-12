#!/bin/sh
## 导入历史抓取的qid到新库中，用于和排重使用
EXEC_CLASS="com.dajie.crawler.zhihu.convert.DumpOldQidToCrawler"

case $1 in

start)
    server_home="/ROOT/project/crawler/zhihu"
    LIB_DIR=/ROOT/project/crawler/zhihu/lib
    LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

    nohup /ROOT/server/jdk/bin/java -server -classpath $LIB_JARS $EXEC_CLASS &
    ;;

status)
    echo `ps aux|grep "$EXEC_CLASS" |grep 'grep' -v|awk '{print $2}'`
    ;;

stop)
    pid=`ps aux|grep "$EXEC_CLASS" |grep 'grep' -v|awk '{print $2}'`
    kill -9 $pid
    ;;

esac