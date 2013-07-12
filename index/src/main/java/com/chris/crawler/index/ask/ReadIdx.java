package com.chris.crawler.index.ask;

import com.chris.crawler.index.IdxBase;
import com.chris.crawler.index.IndexConstant;
import com.dajie.crawler.zhihu.model.ZHQuestion;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-23
 * Time: 下午4:50
 */
public class ReadIdx extends IdxBase {
    public ZHQuestion read() {
        ZHQuestion question = new ZHQuestion();
        try {
            IndexReader reader = IndexReader.open(FSDirectory.open(idx_db), true);
            Document doc = null;
            for (int i = 0; i < reader.numDocs(); i++) {
                doc = reader.document(i);
                question.setId(Integer.valueOf(doc.getField(IndexConstant.F_ID).stringValue()));
                question.setTags(doc.getField(IndexConstant.F_TAGS).stringValue());
                question.setTitle(doc.getField(IndexConstant.F_TITLE).stringValue());
                System.out.println(">>>" + question.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return question;
    }

    public void search(String[] type, String keyword) throws ParseException {
        try {
            IndexSearcher searcher = getIndexSearcher();
            //三种类型： MUST : + and MUST_NOT : - not SHOULD : or
            //Occur[]的长度必须和Fields[]的长度一致。每个限制条件对应一个字段
            BooleanClause.Occur[] flags = new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD, BooleanClause.Occur.MUST};
            // 1
            //Query query = IKQueryParser.parseMultiField(type, keyword, flags);//  new TermQuery(new Term(type, keyword));

            // 2
            Map<String, Float> boosts = new HashMap<String, Float>();//（按相关度排序）某个属性的boost的值   （排序）
            boosts.put(type[0], 3f);
            QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30, type, new IKAnalyzer(), boosts);
            Query query = queryParser.parse(keyword);


            System.out.println(query);
            TopDocs docs = searcher.search(query, 200);
            System.out.println("HITS:" + docs.totalHits);
            ScoreDoc s_doc = null;
            Document doc = null;
            for (int i = 0; i < 200; i++) {
                s_doc = docs.scoreDocs[i];
                doc = searcher.doc(s_doc.doc);
                System.out.println(doc.getBoost() + "--" + doc.get(IndexConstant.F_QID) + "---" + doc.get(IndexConstant.F_TITLE));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
