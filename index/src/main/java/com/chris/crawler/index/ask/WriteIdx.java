package com.chris.crawler.index.ask;

import com.chris.crawler.index.IdxBase;
import com.chris.crawler.index.IndexConstant;
import com.dajie.crawler.zhihu.model.ZHQuestion;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;

import java.io.IOException;

/**
 * User: zhong.huang
 * Date: 13-5-23
 */
public class WriteIdx extends IdxBase {

    public Document write(ZHQuestion question) {
        Document doc = new Document();
        try {
            Field fid = new Field(IndexConstant.F_ID, question.getId() + "", Field.Store.YES, Field.Index.NOT_ANALYZED); // 不分词，但建索引
            Field fqid = new Field(IndexConstant.F_QID, question.getQid() + "", Field.Store.YES, Field.Index.NO); // 不保存到文档，也不建索引
            Field ftags = new Field(IndexConstant.F_TAGS, question.getTags(), Field.Store.YES, Field.Index.ANALYZED); // 分词，并建索引
            Field fcontent = new Field(IndexConstant.F_TITLE, question.getTitle(), Field.Store.YES, Field.Index.ANALYZED);// 分词，并建索引
            doc.add(fid);
            doc.add(fqid);
            doc.add(ftags);
            doc.add(fcontent);

            getIndexWriter().addDocument(doc);
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
