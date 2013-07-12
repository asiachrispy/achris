package com.dajie.crawler.index.test;

/*
 Lucene-2.2.0 源代码阅读学习(4)

 建立索引，通过已经生成的索引文件，实现通过关键字检索。

 写了一个类MySearchEngine，根据上述思想实现，把Lucene 自带的递归建立索引的方法提取出来，加了一个搜索的方法：
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class MySearchEngine {
    private File file;
    private String indexPath;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public void createIndex(IndexWriter writer, File file) throws IOException {
        // file可以读取
        if (file.canRead()) {
            if (file.isDirectory()) { // 如果file是一个目录(该目录下面可能有文件、目录文件、空文件三种情况)
                String[] files = file.list(); // 获取file目录下的所有文件(包括目录文件)File对象，放到数组files里
                // 如果files!=null
                if (files != null) {
                    for (int i = 0; i < files.length; i++) { // 对files数组里面的File对象递归索引，通过广度遍历
                        createIndex(writer, new File(file, files[i]));
                    }
                }
            } else { // 到达叶节点时，说明是一个File，而不是目录，则为该文件建立索引
                try {
                    writer.addDocument(FileDocument.Document(file));
                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                }
            }
        }
    }

    public void searchContent(String type, String keyword) { // 根据指定的检索内容类型type，以及检索关键字keyword进行检索操作
        try {
            IndexReader myReader = IndexReader.open(FSDirectory.open(new File(
                this.indexPath)), true); // only searching, so
            // read-only=true

            IndexSearcher searcher = new IndexSearcher(myReader);// this.indexPath//
            // 根据指定路径，构造一个IndexSearcher检索器
            Analyzer an = new StandardAnalyzer(Version.LUCENE_CURRENT);
            QueryParser pa = new QueryParser(Version.LUCENE_CURRENT, keyword,
                an);
            Term term = new Term(type, keyword); // 创建词条
            Query query = pa.parse(type);//new TermQuery(term) = query.; // 创建查询
            Date startTime = new Date();
            TermDocs termDocs = searcher.getIndexReader().termDocs(term); // 执行检索操作
            while (termDocs.next()) { // 遍历输出根据指定词条检索的结果信息
                System.out.println("搜索的该关键字【" + keyword + "】在文件\n"
                    + searcher.getIndexReader().document(termDocs.doc()));
                System.out.println("中，出现过 " + termDocs.freq() + " 次");
            }
            Date finishTime = new Date();
            long timeOfSearch = finishTime.getTime() - startTime.getTime(); // 计算检索花费时间
            System.out.println("本次搜索所用的时间为 " + timeOfSearch + " ms");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MySearchEngine mySearcher = new MySearchEngine();
        String indexPath = "/home/asiachris/lucene_work/index";
        File file = new File("/home/asiachris/lucene_work/textfile");
        mySearcher.setIndexPath(indexPath);
        mySearcher.setFile(file);
        IndexWriter writer;
        try {
            writer = new IndexWriter(FSDirectory.open(new File(mySearcher
                .getIndexPath())), new StandardAnalyzer(
                Version.LUCENE_CURRENT), true,
                IndexWriter.MaxFieldLength.LIMITED);

            // writer = new IndexWriter(mySearcher.getIndexPath(),new
            // StandardAnalyzer(), true);
            mySearcher.createIndex(writer, mySearcher.getFile());
            mySearcher.searchContent("contents", "tail");
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

/*
 * 这里引用了import
 * org.apache.lucene.demo.FileDocument，在创建Field的时候，为每个Field都设置了三种属性：path、
 * modified、contents。在检索的时候，只要指定其中的一个就可以从索引中检索出来。 /*
 * 在构造IndexWriter的时候，选择分词器不同，对检索的结果有很大的影响。
 * 
 * 我感觉，这里自带的StandardAnalyzer和
 * SimpleAnalyzer对中文的支持不是很好，因为它是对像英文这样的，以空格作为分隔符的，中文不同英文的习惯，可能有时候根本检索不出中文。
 * 
 * 使用StandardAnalyzer和SimpleAnalyzer的时候，检索关键字“server“，结果是相同的，结果如下所示：
 */