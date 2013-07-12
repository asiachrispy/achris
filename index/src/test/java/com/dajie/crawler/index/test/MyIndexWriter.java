package com.dajie.crawler.index.test;

/*
 IndexWriter是一个非常重要的工具。建立索引必须从它开始。而且，从它的构造函数开始。

 Document和Field是Lucene中两个最重要的概念。在建立索引的时候，也就是实例化一个索引器IndexWriter的之前，必须通过已经建立好的Document逻辑文件，将Document的对象添加到 IndexWriter实例中，才能算是建立索引。

 Document汇集数据源，这个数据源是通过Field来构造的。构造好 Field之后，将每个Field对象加入到Document之中，可以通过Document来管理Field，然后将聚集的Document加入到 IndexWriter中，建立索引，写入指定的Directory，为检索做准备。

 写一个建立索引，然后读取索引的例子，代码如下：

 package org.shirdrn.lucene;
 */

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class MyIndexWriter {
    public static void main(String[] args) {
        String file = "/home/asiachris/lucene_work/index";
        File myFile = new File(file);

        try {
            Document myDoc = new Document();
            Field myFieldNo = new Field("myNo", "SN-BH-19830119", Field.Store.YES, Field.Index.ANALYZED);//.UN_TOKENIZED
            Field myFieldName = new Field("myName", "异域王者", Field.Store.YES, Field.Index.ANALYZED);
            Field myFieldGender = new Field("myGender", "男", Field.Store.YES, Field.Index.ANALYZED);
            Field myFieldDescb = new Field("myDescb", "2003年，数学系，信息与计算科学；2007年，计算机系，软件与理论", Field.Store.YES, Field.Index.ANALYZED);
            myDoc.add(myFieldNo);
            myDoc.add(myFieldName);
            myDoc.add(myFieldGender);
            myDoc.add(myFieldDescb);
            Field hisFieldNo = new Field("hisNo", "SN-BH-19860101", Field.Store.YES, Field.Index.ANALYZED);
            Field hisFieldName = new Field("hisName", "风平浪静", Field.Store.YES, Field.Index.ANALYZED);
            Field hisFieldGender = new Field("hisGender", "男", Field.Store.YES, Field.Index.ANALYZED);
            Field hisFieldDescb = new Field("hisDescb", "2003年，历史系，世界史；2007年，计算机系，人工智能", Field.Store.YES, Field.Index.ANALYZED);
            myDoc.add(hisFieldNo);
            myDoc.add(hisFieldName);
            myDoc.add(hisFieldGender);
            myDoc.add(hisFieldDescb);

            IndexWriter myWriter = new IndexWriter(FSDirectory.open(myFile), new StandardAnalyzer(Version.LUCENE_CURRENT), true, IndexWriter.MaxFieldLength.LIMITED);
//    IndexWriter myWriter = new IndexWriter(myFile,new StandardAnalyzer(),true);    //   构造一个索引器，true指定了：向已经存在的索引中追加索引
            myWriter.addDocument(myDoc);
            myWriter.close();    // 关闭索引器，将追加的索引文件写入到索引目录中

            IndexReader myReader = IndexReader.open(FSDirectory.open(myFile), true); // only searching, so read-only=true
//    IndexReader myReader = IndexReader.open(myFile);    // 读取索引
            for (int i = 0; i < myReader.numDocs(); i++) {
                System.out.println(//myReader.document(i));    //   输出索引文件的信息
                    myReader.document(i).getField("hisNo").name() + "<-->\n" +
                        myReader.document(i).getField("hisNo").stringValue() + "<-->\n" +
                        myReader.document(i).getField("hisNo").toString() + "<-->\n"

                );
            }
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
 * 这里，使用File myFile = new File("E:\\Lucene\\myindex");作为一个参数来构造一个索引器。
 * 
 * 运行程序，可以在目录E:\Lucene\myindex下看到生成的索引文件，一共有三个文件：
 * 
 * _0.cfs
 * 
 * segments.gen
 * 
 * segments_3
 * 
 * 第一个是.cfs格式的，成为复合索引格式；第三个segments_3是一个索引段。
 * 
 * 同时，可以在控制台上看到输出结果：
 * 
 * Document<stored/uncompressed,indexed<myNo:SN-BH-19830119>
 * stored/uncompressed,indexed<myName:异域王者>
 * stored/uncompressed,indexed<myGender:男>
 * stored/uncompressed,indexed<myDescb:2003年，数学系，信息与计算科学；2007年，计算机系，软件与理论>
 * stored/uncompressed,indexed<hisNo:SN-BH-19860101>
 * stored/uncompressed,indexed<hisName:风平浪静>
 * stored/uncompressed,indexed<hisGender:男>
 * stored/uncompressed,indexed<hisDescb:2003年，历史系，世界史；2007年，计算机系，人工智能>>
 * 
 * 因为上面程序中使用了IndexReader，先打开索引文件，然后通过 IndexReader的document()来读取索引文件的内容。
 */