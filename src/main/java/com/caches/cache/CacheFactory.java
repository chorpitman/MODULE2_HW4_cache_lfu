package com.caches.cache;

import com.caches.cache.impl.LFUCache;
import com.caches.dao.CrudDao;

public class CacheFactory {

    public <KEY, VAL> Cache<KEY, VAL> createEntityCache(int maxSize, CrudDao<KEY, VAL> systemOfRecord) {
        return new LFUCache<>(maxSize, systemOfRecord); // TODO implement
    }

}
