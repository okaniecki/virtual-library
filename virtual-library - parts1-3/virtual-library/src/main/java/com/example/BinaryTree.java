package com.example;

public class BinaryTree <Key extends Comparable<Key> ,Value> {
    private static final int M = 4;
    private Node root; //root of B-tree
    private int height; //height of B-tree
    private int n; // number of key-value pairs

    private static final class Node {
        private int m; // number of children
        private Entry[] children = new Entry[M]; //the array of children

        private Node(int k) { //create a node with k children
            m = k;
        }
    }

    private static class Entry {
        private Comparable key;
        private Object val;
        private Node next;
        
        public Entry(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public BinaryTree() {
        root = new Node(0);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public int height() {
        return height;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException();
        return search(root, key, height);
    }

    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        if(ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if(eq(key, children[j].key)) return (Value) children[j].val;
            }
        }

        else {
            for (int j = 0; j < x.m; j++) {
                if(j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node u = insert(root, key, val, height);
        n++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private Node insert(Node h, Key key, Value val, int ht) {
        int j;
        Entry t = new Entry(key, val, null);

        if(ht == 0) {
            for(j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.val = null;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--)
            h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j];
        return t;
    }

    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent + children[j].key + " " + children[j].val + "\n");
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s.append(indent);
                s.append(toString(children[j].next, ht-1, indent + "     "));
            }
        }
        return s.toString();
    }

    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    

}
