package com.dajie.crawler.index.test;

import org.apache.lucene.analysis.*;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Set;

public class MyAnalyzer extends Analyzer {

    private Set stopWords = StopFilter.makeStopSet(new String[]{"a", "is", "an", "i", "we",
        "in"});


    public TokenStream tokenStream(String fieldName, Reader reader) {
        TokenStream result = new WhitespaceTokenizer(reader);
        result = new LowerCaseFilter(result);
        result = new StopFilter(false, result, stopWords);
        // result = new SnowballFilter(result, "Italian");
        return result;
    }

    public static void test() {
    }

    public static Document makeDocument(File f) throws FileNotFoundException {
        Document doc = new Document();
        doc.add(new Field("path", f.getPath(), Store.YES, Index.ANALYZED));

        doc.add(new Field("modified",
            DateTools.timeToString(f.lastModified(), Resolution.MINUTE),
            Store.YES, Index.NOT_ANALYZED));

        // Reader implies Store.NO and Index.TOKENIZED
        doc.add(new Field("contents", new FileReader(f)));

        return doc;
    }
}
