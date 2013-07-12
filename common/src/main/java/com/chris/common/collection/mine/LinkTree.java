package com.chris.common.collection.mine;
/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 12-8-23
 * Time: 下午1:16
 * To change this template use File | Settings | File Templates.
 */


/**
 * 链表树，基于数组的节点存储方式
 */
public class LinkTree {
    //私有成员变量，LinkNode类型的数组，固定长度
    private int len;//节点最大个数
    //私有成员变量，树节点个数
    private LinkTNode node_array[];
    //私有成员变量，树的根节点在数组中序号
    private int root;
    //树中当前节点的个数
    private int curr_num;

    //有参数的构造函数
    public LinkTree(int _len) {
        len = _len;
        node_array = new LinkTNode[_len];
        root = -1;
        //位置0处为树根前驱节点
        node_array[0] = new LinkTNode(
                new ElemItem<Integer>(0),
                -1, null, 0);
        for (int i = 1; i < _len; i++) {
            node_array[i] = new LinkTNode();
        }
    }

    //设置树的根节点
    public void setRoot(int r) {
        root = r;
        node_array[r].setParent(0);
        SingleLink2 slk = new SingleLink2();
        slk.insert(new ElemItem<Integer>(r));
        node_array[0].setChildLink(slk);
    }

    //清除数组中的元素项
    public void clear() {
        root = -1;
        for (int i = 0; i < len; i++) {
            node_array[i].setElem(null);
        }
        curr_num = 0;
    }

    //数组中第n_idx个节点添加子节点（类型的元素项），
    //n_idx为父节点号
    //自身的节点号为self_idx
    public boolean addChild(int p_idx,
                            int self_idx,
                            ElemItem e) {
        if (p_idx < 0 || p_idx >= len ||
                self_idx < 0 || self_idx >= len ||
                curr_num > len) {
            return false;
        }
        //如果节点数组在父节点号处没有元素项，
        //则直接将元素项e赋值到数组的p_idx处，self_idx无效用
        if (node_array[p_idx].getElem() == null) {
            node_array[p_idx].setElem(e);
            node_array[p_idx].setChildLink(null);
            node_array[p_idx].setParent(-1);
            node_array[p_idx].setIdx(p_idx);
            curr_num++;
            return true;
        }
        //如果节点数组在父节点号处有元素项了
        //则将e赋值到数组self_idx处，同时设置相关参数
        node_array[self_idx].setElem(e);
        node_array[self_idx].setIdx(self_idx);
        node_array[self_idx].setParent(p_idx);
        node_array[self_idx].setChildLink(null);
        //如果父节点的子树链表是空的，则新建链表并插入self_ode处的节点
        if (node_array[p_idx].getChildLink() == null) {
            node_array[p_idx].setChildLink(
                    new SingleLink2());
        }
        //在节点的子节点
        node_array[p_idx].getChildLink().insert(
                new ElemItem<Integer>(self_idx));
        node_array[p_idx].getChildLink().next();
        curr_num++;
        return true;
    }

    //删除pIdx父节点的子节点序号sIdx,即链表清除
    protected void removeSon(int pIdx, int sIdx) {
        node_array[pIdx].getChildLink().goFirst();
        //定位父节点的子节点链表中要删除的子节点的序号的位置
        while (((Integer) (node_array[pIdx].getChildLink(
        ).getCurrVal().elem)).intValue() != sIdx) {
            node_array[pIdx].getChildLink().next();
        }
        //删除该位置上的子节点的序号（链表节点）
        node_array[pIdx].getChildLink().remove();
        curr_num--;
    }

    //删除位置为nidx的节点的子节点
    public boolean removeChild(int nidx) {
        if (nidx < 0 ||
                node_array[nidx].getElem() == null) {
            return false;
        }
        //子节点不为空，则将子节点一一清空
        if (node_array[nidx].getChildLink() != null) {
            node_array[nidx].getChildLink().goFirst();
            while (node_array[nidx].getChildLink().getCurrVal()
                    != null) {
                int idx =
                        ((Integer) (node_array[nidx].getChildLink(
                        ).getCurrVal().getElem())).intValue();
                //递归调用
                removeChild(idx);
            }

        }

        //清除当前节点
        //首先清除
        removeSon(node_array[nidx].parent(), nidx);
        //链表和元素都清空
        node_array[nidx] = new LinkTNode();
        return true;
    }

    //在特定高度打印一个元素项
    protected void printnode(int pos, int h) {
        for (int i = 0; i < h; i++)
            System.out.print("\t");
        System.out.println(
                "|—— " + node_array[pos] + "(" + pos + ")");
    }

    //后序遍历树中元素项
    protected void posOrder(int pos, int h) {
        if (pos < 0 ||
                //pos > curr_num ||
                node_array[pos].getElem() == null)
            return;
        //访问左子节点
        SingleLink2 lftMostChild =
                node_array[pos].getChildLink();
        if (lftMostChild != null) {
            lftMostChild.goFirst();
            while (lftMostChild.getCurrVal() != null) {
                int idx = ((Integer) (
                        lftMostChild.getCurrVal().getElem())
                ).intValue();
                posOrder(idx, h + 1);
                lftMostChild.next();

            }
        }
        //访问父节点
        printnode(pos, h);
    }

    //前序遍历树中元素项
    protected void preOrder(int pos, int h) {
        if (pos < 0 ||
                //pos > curr_num ||
                node_array[pos].getElem() == null)
            return;
        //访问父节点
        printnode(pos, h);
        //访问左子节点
        SingleLink2 lftMostChild
                = node_array[pos].getChildLink();
        if (lftMostChild != null) {
            lftMostChild.goFirst();
            while (lftMostChild.getCurrVal() != null) {
                int idx = ((Integer) (
                        lftMostChild.getCurrVal().getElem())
                ).intValue();
                preOrder(idx, h + 1);
                lftMostChild.next();
            }
        }
    }

    //后序打印树
    public void post_print_tree() {
        if (curr_num == 0) {
            System.out.println("当前树中没有节点。");
            return;
        }
        System.out.println(curr_num + " 节点,后序遍历打印：");
        posOrder(1, 0);
    }

    //前序打印树
    public void ford_print_tree() {
        if (curr_num == 0) {
            System.out.println("当前树中没有节点。");
            return;
        }
        System.out.println(curr_num + " 节点,前序遍历打印：");
        preOrder(1, 0);
    }
}