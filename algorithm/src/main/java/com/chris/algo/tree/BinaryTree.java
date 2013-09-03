package com.chris.algo.tree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * User: zhong.huang
 * Date: 13-8-21
 */
public class BinaryTree {
    //成员变量
    private TreeNode head; //头指针
    private String brStr; //用于构造二叉树的字符串

    public BinaryTree(String brStr) {
        this.brStr = brStr;
        head = new TreeNode(brStr.charAt(0)); //添加头结点
        add(head, 0); //给头结点添加孩子结点
    }

    private void add(TreeNode parent, int index) {
        int leftIndex = 2 * index + 1; //计算左孩子索引
        if (leftIndex < brStr.length()) { //如果索引没超过字符串长度
            //if (brStr.charAt(leftIndex) != '#') { //'#'表示空结点
            //添加左孩子
            parent.setLeft(new TreeNode(brStr.charAt(leftIndex)));
            //递归调用Add方法给左孩子添加孩子节点
            add(parent.getLeft(), leftIndex);
            //}
        }
        int rightIndex = 2 * index + 2;
        if (rightIndex < brStr.length()) {
            //if (brStr.charAt(rightIndex) != '#') {
            //添加右孩子
            parent.setRight(new TreeNode(brStr.charAt(rightIndex)));
            //递归调用Add方法给右孩子添加孩子节点
            add(parent.getRight(), rightIndex);
            //}
        }
    }

    public void preOrder(TreeNode node) //先序遍历
    {
        if (node != null) {
            System.out.println(node.toString());
            preOrder(node.getLeft()); //递归
            preOrder(node.getRight()); //递归
        }
    }

    public void midOrder(TreeNode node) //中序遍历
    {
        if (node != null) {
            midOrder(node.getLeft()); //递归
            System.out.println(node.toString());
            midOrder(node.getRight()); //递归
        }
    }

    public void afterOrder(TreeNode node) //后继遍历
    {
        if (node != null) {
            afterOrder(node.getLeft()); //递归
            afterOrder(node.getRight()); //递归
            System.out.println(node.toString());
        }
    }

    public TreeNode getHead() {
        return head;
    }

    public void setHead(TreeNode head) {
        this.head = head;
    }

    public String getBrStr() {
        return brStr;
    }

    public void setBrStr(String brStr) {
        this.brStr = brStr;
    }

    /**
     * 二叉树的宽度优先遍历
     * <p/>
     * 之前所讲述的二叉树的深度优先遍历的搜索路径是首先搜索一个结点的所有子孙结点，
     * 再搜索这个结点的兄弟结点。是否可以先搜索所有兄弟和堂兄弟结点再搜索子孙结点呢？
     * <p/>
     * 由于二叉树结点分属不同的层次，
     * 因此可以从上到下、从左到右依次按层访问每个结点。
     * 它的访问顺序正好和之前所述二叉树顺序存储结构中的结点在数组中的存放顺序相吻合。
     * 如图6.13中的二叉树使用宽度优先遍历访问的顺序为：ABCDEF。
     * <p/>
     * 这个搜索过程不再需要使用递归，但需要借助队列来完成。
     * <p/>
     * （1） 将根结点压入队列之中，开始执行步骤(2)。
     * <p/>
     * （2） 若队列为空，则结束遍历操作，否则取队头结点D。
     * <p/>
     * （3） 若结点D的左孩子结点存在，则将其左孩子结点压入队列。
     * <p/>
     * （4） 若结点D的右孩子结点存在，则将其右孩子结点压入队列，并重复步骤(2)。
     */
    public void levelOrder() {//宽度优先遍历
        Queue<TreeNode> queue = new ArrayDeque<TreeNode>(); //声明一个队例
        queue.offer(head); //把根结点压入队列
        TreeNode node = null;
        while (queue.size() > 0) //只要队列不为空
        {
            node = queue.poll(); //出队
            System.out.println(node.toString());//访问结点
            if (node.getLeft() != null) //如果结点左孩子不为空
            {   //把左孩子压入队列
                queue.offer(node.getLeft());
            }
            if (node.getRight() != null) //如果结点右孩子不为熔
            {   //把右孩子压入队列
                queue.offer(node.getRight());
            }
        }
    }
}
