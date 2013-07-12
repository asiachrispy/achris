package com.chris.crawler.index.core.similary;

import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public interface IDoc {

    /**
     * 获取文档中说有词 在文档中出现的次数
     *
     * @return
     */
    public Map<String, Integer> getDF();
}
