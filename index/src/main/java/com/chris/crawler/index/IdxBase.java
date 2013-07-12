package com.chris.crawler.index;

import com.dajie.common.config.AppConfigs;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.lucene.IKSimilarity;

import java.io.File;
import java.io.IOException;

/**
 * User: zhong.huang
 * Date: 13-5-23
 * Time: 下午4:50
 */
public abstract class IdxBase {
    private static final String IDX_DB_S = AppConfigs.getInstance().get(IndexConstant.CONFIG_KEY_INDEX_DB);
    protected File idx_db = new File(IDX_DB_S);
    private IndexWriter writer = null;
    private IndexReader reader = null;
    private IndexSearcher searcher = null;

    public IdxBase() {
        try {
            writer = new IndexWriter(FSDirectory.open(idx_db), new IKAnalyzer(), false, IndexWriter.MaxFieldLength.LIMITED);
            reader = IndexReader.open(FSDirectory.open(idx_db), true);
            searcher = new IndexSearcher(reader);
            searcher.setSimilarity(new IKSimilarity());
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IndexSearcher getIndexSearcher(){
        return searcher;
    }

    public IndexWriter getIndexWriter(){
        return writer;
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
