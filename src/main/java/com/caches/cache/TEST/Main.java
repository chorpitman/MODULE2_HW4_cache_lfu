package com.caches.cache.TEST;

import java.util.Arrays;

/**
 * Created by Oleg_Chorpita on 5/6/2016.
 */
public class Main {
    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(2);
        lfuCache.addCacheEntry(10, "A");
        lfuCache.addCacheEntry(11, "A");
        lfuCache.addCacheEntry(12, "A");
        lfuCache.addCacheEntry(10, "A");
        lfuCache.addCacheEntry(13, "C");
        lfuCache.addCacheEntry(10, "C");
    }
}
