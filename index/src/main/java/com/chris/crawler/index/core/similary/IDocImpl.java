package com.chris.crawler.index.core.similary;

import com.dajie.common.util.StringUtil;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public class IDocImpl implements IDoc {
    private static final Logger LOGGER = LoggerFactory.getLogger(IDocImpl.class);

    private String content;
    private IKSegmentation ikSeg;
    private Map<String, Integer> dFreq;

    public IDocImpl(String cont) {
        this.content = cont;
        this.ikSeg = new IKSegmentation(new StringReader(content), true);
    }


    // 记录一片文档的词频
    public Map<String, Integer> segment() {
        if (StringUtil.isEmpty(content)) {
            LOGGER.warn("document content can not be empty");
            return Collections.emptyMap();
        }
        StopAnalyzer stop = new StopAnalyzer(Version.LUCENE_CURRENT);
        Map<String, Integer> mapFreq = new HashMap<String, Integer>();
        try {
            Lexeme lexeme = null;
            String txt = null;
            while ((lexeme = ikSeg.next()) != null) {
                txt = lexeme.getLexemeText();
                if (!mapFreq.containsKey(txt)) {
                    System.out.println(">>>" + txt);
                    mapFreq.put(txt, 1);
                    continue;
                }

                int freq = mapFreq.get(txt);
                mapFreq.put(txt, ++freq); // 记录每一个词的出现的次数
            }
        } catch (IOException e) {
            LOGGER.warn("", e);
            return Collections.emptyMap();
        }
        return mapFreq;
    }

    @Override
    public Map<String, Integer> getDF() {
        if (dFreq == null || dFreq.isEmpty()) {
            dFreq = segment();
            return dFreq;
        }
        return dFreq;
    }
}