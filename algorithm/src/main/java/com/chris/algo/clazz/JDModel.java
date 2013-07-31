package com.chris.algo.clazz;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-7-26
 */
public class JDModel {

    private String jid;
    private String clazz;
    private Map<String,Integer> words;

    public JDModel() {
    }

    public JDModel(String jid, String clazz, HashMap<String,Integer> words) {
        this.jid = jid;
        this.clazz = clazz;
        this.words = words;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Map<String,Integer> getWords() {
        return words;
    }

    public void setWords(Map<String,Integer> words) {
        this.words = words;
    }
}
