package com.fantomath.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleHashMapTest {

    @Test
    void putAndGet() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();

        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));
        assertEquals(3, map.get("c"));
    }

    @Test
    void overwriteValue() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();

        map.put("key", 10);
        map.put("key", 99);

        assertEquals(99, map.get("key"));
    }

    @Test
    void removeKey() {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();

        map.put("x", 100);
        assertEquals(100, map.get("x"));

        map.remove("x");
        assertNull(map.get("x"));
    }

    @Test
    void manyInsertsShouldResize() {
        SimpleHashMap<Integer, Integer> map = new SimpleHashMap<>();

        for (int i = 0; i < 200; i++) {
            map.put(i, i * 2);
        }

        assertEquals(200, map.size());
        assertEquals(42 * 2, map.get(42));
        assertEquals(199 * 2, map.get(199));
    }

    @Test
    void getMissingKeyReturnsNull() {
        SimpleHashMap<String, String> map = new SimpleHashMap<>();

        map.put("hello", "world");

        assertNull(map.get("missing"));
        map.remove("hello");
        assertNull(map.get("hello"));
    }
}
