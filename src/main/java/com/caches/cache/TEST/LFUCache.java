package com.caches.cache.TEST;

import java.util.LinkedHashMap;
import java.util.Map;

public class LFUCache {

    private class CacheEntry {
        private String data;
        private int frequency;

        private CacheEntry() {
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }
    }

    private static int initialCapacity = 2;

    private static LinkedHashMap<Integer, CacheEntry> cacheMap = new LinkedHashMap<Integer, CacheEntry>();
/* LinkedHashMap is used because it has features of both HashMap and LinkedList.
 * Thus, we can get an entry in O(1) and also, we can iterate over it easily.
 * */
//constructor
    public LFUCache(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }
//добавляем в кеш до заданного размеры мапы
    public void addCacheEntry(int key, String data) {
        if (cacheMap.size() < initialCapacity) {
            CacheEntry cacheEntry = new CacheEntry();
            cacheEntry.setData(data);
            cacheEntry.setFrequency(1);

            cacheMap.put(key, cacheEntry);
        } else {
            // проверяем присутствие в мапе данного элемента
            for (Integer integer : cacheMap.keySet()) {
                
            }
            int entryKeyToBeRemoved = getLFUKey();
            cacheMap.remove(entryKeyToBeRemoved);

            CacheEntry temp = new CacheEntry();
            temp.setData(data);
            temp.setFrequency(1);

            cacheMap.put(key, temp);
        }
    }

    public int getLFUKey() {
        int key = 0;
        int minFreq = Integer.MAX_VALUE;

        for (Map.Entry<Integer, CacheEntry> entry : cacheMap.entrySet()) {
            if (minFreq > entry.getValue().frequency) {
                key = entry.getKey();
                minFreq = entry.getValue().frequency;
            }
        }

        return key;
    }

    public String getCacheEntry(int key) {
        if (cacheMap.containsKey(key))  // cache hit
        {
            CacheEntry temp = cacheMap.get(key);
            temp.frequency++;
            cacheMap.put(key, temp);
            return temp.data;
        }
        return null; // cache miss
    }
}