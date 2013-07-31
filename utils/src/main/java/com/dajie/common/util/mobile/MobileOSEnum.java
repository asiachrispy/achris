package com.dajie.common.util.mobile;

import com.dajie.common.util.mobile.hook.*;

/**
 * User: huihui.wang
 * Date: 13-1-30
 * Time: 下午7:05
 */
public enum MobileOSEnum {
    ANDROID(1, "android", AndroidUAInfoHook.class),
    IPHONE_OS(2, "iphone os", IphoneUAInfoHook.class),
    SYMBIAN_OS(3, "symbianos", SymbianUAInfoHook.class),
    IPAD_OS(4, "cpu os", IpadUAInfoHook.class),
    BREW_OS(5, "brew", BrewUAInfoHook.class),
    BLACKBERRY_OS(6, "blackberry", BlackBerryUAInfoHook.class),
    MEEGO(7, "meego", MeegoUAInfoHook.class),
    WEB_OS(8, "webos", WebOSUAInfoHook.class);

    private MobileOSEnum(int value, String shortName, Class<? extends UABaseInfoHook> hookClass) {
        this.value = value;
        this.shortName = shortName;
        this.hookClass = hookClass;
    }

    private int value;
    private String shortName;
    private Class<? extends UABaseInfoHook> hookClass;

    public int getValue() {
        return value;
    }

    public String getShortName() {
        return shortName;
    }

    public UABaseInfoHook getHookInstance() {
        try {
            return hookClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
