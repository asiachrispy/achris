package com.chris.common.collection.mine;
/**
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:14
 */

/**
 * 基于链表的树结构（子节点表示法），
 * 这里的的链表是高效单链表
 */
public class LinkTNode{
    //节点自身的元素项
    private ElemItem elem;
    //节点父节点
    private int parentNode;
    //每个树节点中包含一个单链表,
    //链表中每个节点中的元素项是LinkTNode类型
    private SingleLink2 link_idx;
    //节点在节点数组中的位置idx
    private int idx;

    //无参数的构造函数
    public LinkTNode(){
        elem = null;
        parentNode = -1;
        link_idx = null;
        idx = -1;
    }

    //带参数的构造函数
    public LinkTNode(ElemItem e, int pn,
                SingleLink2 sk, int _idx){
        elem = e;
        parentNode = pn;
        link_idx = sk;
        idx = _idx;
    }

    //设置节点元素项
    public void setElem(ElemItem _elem) {
        elem = _elem;
    }
    //获取节点的元素项
    public ElemItem getElem(){
        return elem;
    }

    //判断是否是叶节点
    public boolean isLeaf() {
        //若节点中的链表是空，则是叶节点
        return link_idx == null;
    }

    //返回父节点
    public int parent() {
        return parentNode;
    }

    //设定父节点
    public void setParent(int pn) {
        parentNode = pn;
    }

    //返回所有子节点对应的链表
    public SingleLink2 getChildLink(){
        return link_idx;
    }

    //重新设置节点的子节点
    public void setChildLink(SingleLink2 slk){
        link_idx = slk;
    }

    //获取当前节点在节点数组中的位置
    public int getIdx(){
        return idx;
    }
    //设置当前节点在节点数组中的序号
    public void setIdx(int _idx){
        idx = _idx;
    }
    //返回元素项String形式
    public String toString(){
        return elem.getElem().toString();
    }

}