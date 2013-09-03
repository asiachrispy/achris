package com.chris.algo.tree;

/**
 * User: zhong.huang
 * Date: 13-8-21
 */
public class TestBT {

    public static void main(String[] args) {   //使用字符串构造二叉树
        BinaryTree bTree = new BinaryTree("ABCDE#F");
        System.out.println("先序遍");
        bTree.preOrder(bTree.getHead()); //先序遍  ABD E C#F
        System.out.println("中序遍");
        bTree.midOrder(bTree.getHead()); //中序遍 DBE A #CF
        System.out.println("后序遍");
        bTree.afterOrder(bTree.getHead()); //后序遍 DEB #FC A
        System.out.println("宽度优先遍");
        bTree.levelOrder(); //ABCDE#F
    }
}
