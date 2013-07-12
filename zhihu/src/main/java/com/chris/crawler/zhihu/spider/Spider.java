package com.chris.crawler.zhihu.spider;

import com.chris.crawler.zhihu.ZHConstant;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.util.StringUtil;

import java.net.MalformedURLException;

/**
 * Spider just handler html and return with tag.
 * User: zhong.huang
 * Date: 13-4-24
 * Time: 上午10:24
 */
public abstract class Spider {
    private String proxyKV;

    protected Spider() {
    }

    protected Spider(String proxyKV) {
        this.proxyKV = proxyKV;
    }

    /**
     * get url content.
     *
     * @param url
     * @param encoding html content-encoding
     * @return
     */
    abstract public String getHtmlWithLogin(String url, String encoding) throws MalformedURLException, Exception;

    /**
     * get url content.
     *
     * @param url
     * @param encoding html content-encoding
     * @return
     */
    abstract public String getHtml(String url, String encoding) throws MalformedURLException, Exception;;

    /**
     * find <a href="regexp"/> .
     *
//     * @param html
//     * @param regexp eg:/question/123490
     * @return
     */
//    abstract public List<String> getLinks(String html, String regexp);
//
//    abstract public List<String> getTimes(String html, String regexp);

    public String getProxyKV() {
        return proxyKV;
    }

    protected void initProxy() {
        // for proxy
        if (StringUtil.isNotEmpty(proxyKV) && proxyKV.contains(":")) {
            String[] kv = proxyKV.split(":");
            System.setProperty("http.proxyHost", kv[0]);
            System.setProperty("http.proxyPort", kv[1]);
        } else { // for default proxy
            String value = AppConfigs.getInstance().getConfigs().get(ZHConstant.CONFIG_KEY_PROXY_IP);//
            if (StringUtil.isNotEmpty(value) && value.contains(":")) {
                String[] kv = value.split(":");
                System.setProperty("http.proxyHost", kv[0]);
                System.setProperty("http.proxyPort", kv[1]);
            } else {
                String[] kv = ZHConstant.DEFAULT_PROXY_IP.split(":");
                System.setProperty("http.proxyHost", kv[0]);
                System.setProperty("http.proxyPort", kv[1]);
            }
        }
    }


}


