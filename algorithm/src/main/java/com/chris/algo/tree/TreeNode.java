package com.chris.algo.tree;

/**
 * User: zhong.huang
 * Date: 13-8-21
 * 二叉树结点类
 *
 * Node类专门用于表示二叉树中的一个结点，它很简单，只有三个属性：Data表示结点中的数据；Left表示这个结点的左孩子，它是Node类型；Right表示这个结点的右孩子，它也是Node类型。
 */
public class TreeNode {
    //成员变量
    private Object data; //数据
    private TreeNode left; //左孩子
    private TreeNode right; //右孩子

    public TreeNode() {}

    public TreeNode(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
            "data=" + data +
            '}';
    }
}
