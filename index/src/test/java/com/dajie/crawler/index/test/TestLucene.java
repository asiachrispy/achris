package com.dajie.crawler.index.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;

/**
 * This class demonstrates the process of creating an index with Lucene for text
 * files in a directory.
 */
public class TestLucene {

    private static final String INDEX_PATH = "index";
    private static final String INDEX_FILE = "/home/asiachris/workspace/yoyoDemo/src/hey.txt";
    private static final String INDEX_FILE_PATH = "/home/asiachris/workspace/yoyoDemo/indexf";

    // 使用方法:: IndexFiles [索引输出目录] [索引的文件列表] ...
    public static void main(String[] args) throws Exception {
        // createIndex();
        // search();
        // deleteIndex();

        advancedTextFilterIndex();
        search();
    }

    public static void createIndex() throws IOException {
        Directory indexPath = FSDirectory.open(new File(INDEX_PATH));

        // 用指定的语言分析器构造一个新的写索引器(第 3 个参数表示是否为追加索引)
        IndexWriter writer = new IndexWriter(indexPath, new SimpleAnalyzer(),
            true, IndexWriter.MaxFieldLength.LIMITED);
        Document doc = new Document();
        // 文件名称，可查询，不分词
        Field fileName = new Field("fileName", INDEX_FILE, Field.Store.YES,
            Field.Index.NOT_ANALYZED);
        // 文件名称，可查询，不分词
        Field fileContent = new Field("fileContent", new FileReader(INDEX_FILE));

        doc.add(fileName);
        doc.add(fileContent);
        Field f = null;

        for (int i = 0; i < doc.getFields("fileContent").length; i++) {
            f = doc.getFields("fileContent")[i];
            System.out.println(i + "  add document : " + INDEX_FILE
                + "\n name:" + f.name() + "   value:" + f.stringValue());

        }
        // 将文档写入索引
        writer.addDocument(doc);
        // 合并段
        writer.optimize();
        // 关闭写索引器
        writer.close();
    }

    public static void search() throws IOException, ParseException {
        File indexDir = new File(INDEX_PATH);
        Directory dir = FSDirectory.open(indexDir);
        // 指向索引目录的搜索器
        Searcher searcher = new IndexSearcher(dir);
        // 查询解析器:使用和索引同样的语言分析器
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
        QueryParser parser = new QueryParser(Version.LUCENE_CURRENT,
            "fileContent", analyzer);
        Query query = parser.parse("java");
        // 搜索结果使用 Hits 存储
        TopDocs hits = searcher.search(query, 2);
        // 通过 hits 可以访问到相应字段的数据和查询的匹配度
        ScoreDoc[] scoreDocs = hits.scoreDocs;

        for (int i = 0; i < scoreDocs.length; i++) {
            System.out.println(scoreDocs[i].doc + "--"
                + scoreDocs[i].toString() + "--" + scoreDocs[i].score);
        }

    }

    /**
     * 值得注意的是虽然利用上述代码删除文档使得该文档不能被检索到， 但是并没有物理上删除该文档。 Lucene 只是通过一个后缀名为
     * .delete的文件来标记哪些文档已经被删除。 既然没有物理上删除， 我们可以方便的把这些标记为删除的文档恢复过来， 如清单 3
     * 所示，首先打开一个索引，然后调用方法 ir.undeleteAll() 来完成恢复工作。
     *
     * @throws java.io.IOException
     */
    public static void deleteIndex() throws IOException {
        Directory dir = FSDirectory.open(new File(INDEX_PATH));
        IndexReader reader = IndexReader.open(dir, false);
        Term term = new Term("fileContent", "test");
        int deleted = reader.deleteDocuments(term);
        System.out.println("deleted " + deleted + " documents containing "
            + term);
        reader.close();

        // reader.undeleteAll();
        dir.close();

    }

    /**
     * 提高索引性能
     * <p/>
     * 利用 Lucene，在创建索引的工程中你可以充分利用机器的硬件资源来提高索引的效率。
     * 当你需要索引大量的文件时，你会注意到索引过程的瓶颈是在往磁盘上写索引文件的过程中。为了解决这个问题, Lucene
     * 在内存中持有一块缓冲区。但我们如何控制 Lucene的缓冲区呢？ 幸运的是，Lucene 的类 IndexWriter
     * 提供了三个参数用来调整缓冲区的大小以及往磁盘上写索引文件的频率。
     * <p/>
     * 1．合并因子（mergeFactor）
     * <p/>
     * 这个参数决定了在 Lucene 的一个索引块中可以存放多少文档以及把磁盘上的索引块合并成一个大的索引块的频率
     * 。比如，如果合并因子的值是10，那么当内存中的文档数达到 10 的时候所有的文档都必须写到磁盘上的一个
     * 新的索引块中。并且，如果磁盘上的索引块的隔数达到 10 的话，这 10个索引块会被合并成一个新的索引块。
     * 这个参数的默认值是10，如果需要索引的文档数非常多的话这个值将是非常不合适的。对批处理的索引来讲，
     * 为这个参数赋一个比较大的值会得到比较好的索引效果。
     * <p/>
     * 2．最小合并文档数
     * <p/>
     * 这个参数也会影响索引的性能。它决定了内存中的文档数至少达到多少才能将它们写回磁盘。 这个参数的默认值是10，如果你有足够的内存，
     * 那么将这个值尽量设的比较大一些将会显著的提高索引性能。
     * <p/>
     * 3．最大合并文档数
     * <p/>
     * 这个参数决定了一个索引块中的最大的文档数。它的默认值是 Integer.MAX_VALUE，将这个参数设置为比较大的值可以提高索引效率和检索速度，
     * 由于该参数的默认值是整型的最大值，所以我们一般不需要改动这个参数。
     * <p/>
     * 清单 5 列出了这个三个参数用法，清单 5 和清单 1 非常相似，除了清单 5 中会设置刚才提到的三个参数。
     *
     * @throws Exception
     */
    public static void advancedTextFilterIndex() throws Exception {
        File indp = new File(INDEX_PATH);
        Directory indexDir = FSDirectory.open(indp);
        File fileDir = new File(INDEX_FILE_PATH);

        Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
        File[] textFiles = fileDir.listFiles();
        long startTime = new Date().getTime();
        int mergeFactor = 10;
        int minMergeDocs = 10;
        int maxMergeDocs = Integer.MAX_VALUE;
        IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer,
            true, IndexWriter.MaxFieldLength.LIMITED);
        indexWriter.setMergeFactor(mergeFactor);
        indexWriter.setMaxMergeDocs(maxMergeDocs);
        // Add documents to the index
        for (int i = 0; i < textFiles.length; i++) {
            if (textFiles[i].isFile()
                && textFiles[i].getName().endsWith(".txt")) {
                Document document = new Document();
                Reader textReader = new FileReader(textFiles[i]);
                Field fileContent = new Field("fileContent", textReader);
                Field fileName = new Field("path", textFiles[i].getPath(),
                    Field.Store.YES, Field.Index.ANALYZED);

                document.add(fileContent);
                document.add(fileName);
                indexWriter.addDocument(document);
            }
        }
        indexWriter.optimize();
        indexWriter.close();

        long endTime = new Date().getTime();
        System.out.println("MergeFactor: " + indexWriter.getMaxMergeDocs());
        // System.out.println("MinMergeDocs: " + indexWriter.minMergeDocs);
        System.out.println("MaxMergeDocs: " + indexWriter.getMaxMergeDocs());
        System.out.println("Document number: " + textFiles.length);

        System.out.println("Time consumed: " + (endTime - startTime)
            + " milliseconds");
    }

    public static void test() throws IOException {

    }

}
