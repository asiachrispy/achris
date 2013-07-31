package com.dajie.common.util.mobile;

/**
 * User: huihui.wang
 * Date: 13-1-30
 * Time: 下午7:05
 */
public class UAInfo {
    private MobileOSEnum osEnum;
    private String osVersion;
    private String UADetail;
    private boolean isMobileOs = false;

    public UAInfo(MobileOSEnum osEnum, String osVersion) {
        this.osEnum = osEnum;
        this.osVersion = osVersion;
    }

    public UAInfo(MobileOSEnum osEnum, String osVersion, String UADetail) {
        this.osEnum = osEnum;
        this.osVersion = osVersion;
        this.UADetail = UADetail;
    }

    public UAInfo() {
    }

    public MobileOSEnum getOsEnum() {
        return osEnum;
    }

    public void setOsEnum(MobileOSEnum osEnum) {
        this.osEnum = osEnum;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getUADetail() {
        return UADetail;
    }

    public void setUADetail(String UADetail) {
        this.UADetail = UADetail;
    }

    public boolean isMobileOs() {
        return isMobileOs;
    }

    public void setMobileOs(boolean mobileOs) {
        isMobileOs = mobileOs;
    }
}
