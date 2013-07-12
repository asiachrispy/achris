package com.chris.crawler.index.core.similary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public class DocumentImpl implements Document {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentImpl.class);

    private String content;
    private IKSegmentation ikSeg;
    private Map<String, Integer> dFreq;

    public DocumentImpl(String cont) {
        this.content = cont;
        this.ikSeg = new IKSegmentation(new StringReader(content), true);
    }

    public Map<String, Integer> documentFreq() {
        if (dFreq == null || dFreq.isEmpty()) {
            dFreq = segment();
            return dFreq;
        }
        return dFreq;
    }

    // 记录一片文档的词频
    public Map<String, Integer> segment() {
        if (this.content == null || content.isEmpty()) {
            LOGGER.warn("document content can not be empty");
            return null;
        }
        Map<String, Integer> mapFreq = new HashMap<String, Integer>();
        try {
            Lexeme lexeme = null;
            String txt = null;
            while ((lexeme = ikSeg.next()) != null) {
                txt = lexeme.getLexemeText();
                if (!mapFreq.containsKey(txt)) {
                    mapFreq.put(lexeme.getLexemeText(), 1);
                    continue;
                }

                int freq = mapFreq.get(txt);
                mapFreq.put(txt, ++freq);
            }
        } catch (IOException e) {
            LOGGER.info("", e);
            return null;
        }
        return mapFreq;
    }
}