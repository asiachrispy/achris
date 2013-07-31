package com.chris.algo.clazz;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;

/**
 * User: zhong.huang
 * Date: 13-7-26
 */
public class JDParser {
    private String jd;
    private IKSegmentation ikSeg;

    public JDParser() {
        this.jd = "target_jd.txt";
        this.ikSeg = this.ikSeg = new IKSegmentation(new StringReader(""), true);
    }

    public List<JDModel> parser() {
        List<JDModel> jds = new ArrayList<JDModel>();
        ClassLoader classLoader = (new com.dajie.algo.clazz.MajorParser()).getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(jd);
        Scanner scanner = new Scanner(stream);
        try {

            String line = null;
            String jdLine = null;
            String[] arr = null;
            String clazz = null;
            String jid = null;
            Lexeme lexeme = null;
            String word = null;
            JDModel model = null;
            Map<String, Integer> words = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line == null || line.trim().equals("")) {
                    continue;
                }

                if (line.startsWith("jobid:")) {
                    jdLine = line;
                } else if (line.startsWith("majorcalssify:")) {
                    clazz = line.split(":")[1].split(",")[0];
                    jid = jdLine.split(" ")[1];
                    model = new JDModel();
                    model.setJid(jid);
                    model.setClazz(clazz);
                    words = new HashMap<String, Integer>();
                    model.setWords(words);
                    jds.add(model);
                } else {
                    ikSeg.reset(new StringReader(line));
                    while ((lexeme = ikSeg.next()) != null) {
                        word = lexeme.getLexemeText();
                        if (words.containsKey(word)) {
                            words.put(word, words.get(word) + 1);
                        } else {
                            words.put(word, 1);
                        }
                    }
                }
            }

        } catch (IOException e) {
        }

        return jds;
    }
}
