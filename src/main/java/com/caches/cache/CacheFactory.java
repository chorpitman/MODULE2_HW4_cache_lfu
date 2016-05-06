package com.caches.cache;

import com.caches.cache.impl.CacheImpl;
import com.caches.dao.CrudDao;

public class CacheFactory {

    public <KEY, VAL> Cache<KEY, VAL> createEntityCache(int maxSize, CrudDao<KEY, VAL> systemOfRecord) {
        return new CacheImpl<>(maxSize, systemOfRecord); // TODO implement
    }

}
