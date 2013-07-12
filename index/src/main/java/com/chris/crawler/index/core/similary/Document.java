package com.chris.crawler.index.core.similary;

import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public interface Document {

    public Map<String, Integer> segment();

    public Map<String, Integer> documentFreq();
}
