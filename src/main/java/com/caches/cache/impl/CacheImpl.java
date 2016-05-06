package com.caches.cache.impl;

import com.caches.cache.Cache;
import com.caches.dao.CrudDao;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CacheImpl<KEY, VAL> implements Cache<KEY, VAL> {

    private CrudDao<KEY, VAL> dao;
    //without gererics Map<String, CacheEntity> cache...
    //witht gererics   Map<KEY, VALUE> cache...
    private Map<KEY, VAL> cache;
    private int size;

    public CacheImpl(int size, CrudDao<KEY, VAL> dao) {
        this.size = size;
        this.dao = dao;
        this.cache = new HashMap<>(size);
    }

    @Override
    public VAL get(KEY key) {
        VAL cacheEntity = cache.get(key);
        if (Objects.isNull(cacheEntity)) {
            cacheEntity = dao.read(key);
            cache.put(key, cacheEntity);
        }
        return cacheEntity;
    }

    @Override
    public void put(KEY key, VAL value) {

    }
//    public VAL get(KEY id) {
//        VAL cacheEntity = cache.get(id);
//        if(cacheEntity == null){
//            cacheEntity = dao.read(id);
//        }
//        return null;
//    }
//
//    public Object get(Object o) {
//        return null;
//    }
//
//    public void put(Object o, Object value) {
//    }
//
//    //static class
//    private static class CacheEntity {
//        private Entity entityStaticClazz;
//        private int frequency = 1;
//
//        public CacheEntity(Entity entityStaticClazz) {
//            this.entityStaticClazz = entityStaticClazz;
//        }
//    }
}
