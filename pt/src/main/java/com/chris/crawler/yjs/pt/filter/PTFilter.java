package com.chris.crawler.yjs.pt.filter;

import com.chris.crawler.yjs.pt.PTConstant;
import com.dajie.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */
public class PTFilter implements Filter{
    private static final Logger LOGGER = LoggerFactory.getLogger(PTFilter.class);

    public boolean filter(String title) {
        if (StringUtil.isEmpty(title)) {
           return false;
        }

        if (title.length() <= 8) {
           LOGGER.warn("[{}] title's length less 8 characters!", title);
           return false;
        }

        if (title.contains(PTConstant.FILTER_STR_PT) || title.contains(PTConstant.FILTER_STR_SOME)) {
           LOGGER.warn("[{}] title has filter characters!", title);
           return false;
        }

        return true;
    }
}
