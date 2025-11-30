package com.fantomath.lab1;

public class Main {
    public static void main(String[] args) {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();

        map.put("apple", 10);
        map.put("banana", 20);
        map.put("orange", 30);

        System.out.println("apple: " + map.get("apple"));
        System.out.println("banana: " + map.get("banana"));
        System.out.println("orange: " + map.get("orange"));

        map.put("banana", 99);
        System.out.println("banana after update: " + map.get("banana"));

        System.out.println("contains grape before: " + map.get("grape"));

        map.put("grape", 777);
        System.out.println("contains grape after: " + map.get("grape"));

        map.remove("orange");
        System.out.println("orange after remove: " + map.get("orange"));

        System.out.println("size = " + map.size());

        for (int i = 0; i < 50; i++) {
            map.put("k" + i, i);
        }

        System.out.println("size after bulk insert = " + map.size());
        System.out.println("k42 = " + map.get("k42"));
    }
}
