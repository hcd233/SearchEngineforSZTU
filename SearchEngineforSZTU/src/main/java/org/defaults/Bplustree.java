package org.defaults;

/**
 * 建立b+树，将搜索字符生成树，把通配字符生成叶子节点，
 * 在倒排索引中查询结果并返回，同时返回模糊查询的相关结果
 */
public class Bplustree {
    private Node root;
    private Node head;
    private int height;
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



}
class Node{
    private Node parent;
    private Node pre;
    private Node next;
    public Boolean isRoot(Node t){
        return t.parent == null;
    }
    public Boolean isLeaf(Node t){
        return t.parent != null;
    }
}
