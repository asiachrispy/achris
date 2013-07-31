package com.dajie.common.util.mobile.hook;

/**
 * User: huihui.wang
 * Date: 13-2-1
 * Time: 下午4:51
 */
public class MeegoUAInfoHook extends UABaseInfoHook {
    @Override
    public String getVersion(String systemInfo) {
        return "0";
    }
}
