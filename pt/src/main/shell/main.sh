#!/bin/sh
EXEC_CLASS="com.dajie.crawler.yjs.pt.PTMain"
#PTReporter PTCrawler PTMain

case $1 in

start)
    server_home="/ROOT/project/crawler"
    mon_server_home="/ROOT/project/crawler/mon"

    LIB_DIR=/ROOT/project/crawler/lib
    LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

    day=`date '+%a'`
    if [ $day == "Mon" ]; then
        cd $mon_server_home
        LIB_JARS=$LIB_JARS:$mon_server_home/dajie_crawler.properties
    else
        LIB_JARS=$LIB_JARS:$server_home/dajie_crawler.properties
        cd $server_home
    fi

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