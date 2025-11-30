package com.fantomath.lab1;

import java.util.Objects;

public class SimpleHashMap<K, V> {

    private static final float LOAD_FACTOR = 0.6f;

    private enum State { EMPTY, OCCUPIED, DELETED }

    private static class Entry<K, V> {
        K key;
        V value;
        State state;

        Entry() {
            state = State.EMPTY;
        }
    }

    private Entry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
        table = (Entry<K, V>[]) new Entry[16];
        for (int i = 0; i < table.length; i++) table[i] = new Entry<>();
    }

    private int h1(Object key) {
        int h = key == null ? 0 : key.hashCode();
        h ^= (h >>> 16);
        return Math.abs(h);
    }

    private int h2(Object key) {
        int h = key == null ? 1 : key.toString().length() | 1;
        return h;
    }

    private int index(Object key, int i) {
        return (h1(key) + i * h2(key)) % table.length;
    }

    public int size() {
        return size;
    }

    public void put(K key, V value) {
        if ((float) size / table.length > LOAD_FACTOR) resize();

        for (int i = 0; i < table.length; i++) {
            int idx = index(key, i);
            Entry<K, V> e = table[idx];

            if (e.state == State.EMPTY || e.state == State.DELETED) {
                e.key = key;
                e.value = value;
                e.state = State.OCCUPIED;
                size++;
                return;
            }

            if (e.state == State.OCCUPIED && Objects.equals(e.key, key)) {
                e.value = value;
                return;
            }
        }
    }

    public V get(K key) {
        for (int i = 0; i < table.length; i++) {
            int idx = index(key, i);
            Entry<K, V> e = table[idx];

            if (e.state == State.EMPTY) return null;
            if (e.state == State.OCCUPIED && Objects.equals(e.key, key)) return e.value;
        }
        return null;
    }

    public void remove(K key) {
        for (int i = 0; i < table.length; i++) {
            int idx = index(key, i);
            Entry<K, V> e = table[idx];

            if (e.state == State.EMPTY) return;
            if (e.state == State.OCCUPIED && Objects.equals(e.key, key)) {
                e.state = State.DELETED;
                e.key = null;
                e.value = null;
                size--;
                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] old = table;
        table = (Entry<K, V>[]) new Entry[old.length * 2];
        for (int i = 0; i < table.length; i++) table[i] = new Entry<>();

        size = 0;

        for (Entry<K, V> e : old) {
            if (e.state == State.OCCUPIED) put(e.key, e.value);
        }
    }
}
