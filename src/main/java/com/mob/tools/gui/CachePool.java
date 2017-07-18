package com.mob.tools.gui;

public class CachePool<K, V> {
    private int capacity;
    private Node<K, V> head;
    private int size;
    private Node<K, V> tail;

    private static class Node<K, V> {
        private long cacheTime;
        public K key;
        public Node<K, V> next;
        public Node<K, V> previous;
        public V value;

        private Node() {
        }
    }

    public CachePool(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void clear() {
        this.tail = null;
        this.head = null;
        this.size = 0;
    }

    public synchronized V get(K key) {
        V v = null;
        synchronized (this) {
            Node<K, V> n = this.head;
            while (n != null && !n.key.equals(key)) {
                n = n.next;
            }
            if (n != null) {
                if (n.previous != null) {
                    if (n.next == null) {
                        n.previous.next = null;
                        this.tail = this.tail.previous;
                    } else {
                        n.previous.next = n.next;
                        n.next.previous = n.previous;
                    }
                    n.previous = null;
                    n.next = this.head;
                    this.head.previous = n;
                    this.head = n;
                }
                v = n.value;
            }
        }
        return v;
    }

    public synchronized boolean put(K key, V value) {
        boolean z;
        if (key != null) {
            if (this.capacity > 0) {
                Node<K, V> n = null;
                while (this.size >= this.capacity) {
                    n = this.tail;
                    this.tail = this.tail.previous;
                    this.tail.next = null;
                    this.size--;
                }
                if (n == null) {
                    n = new Node();
                }
                n.cacheTime = System.currentTimeMillis();
                n.key = key;
                n.value = value;
                n.previous = null;
                n.next = this.head;
                if (this.size == 0) {
                    this.tail = n;
                } else {
                    this.head.previous = n;
                }
                this.head = n;
                this.size++;
                z = true;
            }
        }
        z = false;
        return z;
    }

    public int size() {
        return this.size;
    }

    public synchronized void trimBeforeTime(long time) {
        if (this.capacity > 0) {
            for (Node<K, V> n = this.head; n != null; n = n.next) {
                if (n.cacheTime < time) {
                    if (n.previous != null) {
                        n.previous.next = n.next;
                    }
                    if (n.next != null) {
                        n.next.previous = n.previous;
                    }
                    if (n.equals(this.head)) {
                        this.head = this.head.next;
                    }
                    this.size--;
                }
            }
        }
    }
}
