package com.caches.cache.impl;

import com.caches.cache.Cache;
import com.caches.dao.CrudDao;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class LFUCache<KEY, VAL> implements Cache<KEY, VAL> {
    private static final int DELAY = 5;
    private static final int PERIOD = 10;

    private int capacity;
    private CrudDao<KEY, VAL> dao;
    private Map<KEY, CacheEntity> cache = new HashMap<KEY, CacheEntity>();
    private Queue<CacheEntity> queue = new PriorityQueue<CacheEntity>();
    private Queue<VAL> entityQueue = new LinkedList<VAL>();

    public LFUCache(int maxSize, CrudDao<KEY, VAL> systemOfRecord) {
        capacity = maxSize;
        dao = systemOfRecord;
        runUpdateSORTask();
    }

    public VAL get(KEY key) {
        CacheEntity cacheEntity = cache.get(key);
        if (cacheEntity == null) {
            VAL entity = dao.read(key);
            updateCache(key, entity);
            return entity;
        }
        cacheEntity.frequency++;
        return cacheEntity.val;
    }

    public void put(KEY key, VAL value) {
        CacheEntity cacheEntity = cache.get(key);
        if (cacheEntity == null) {
            if (cache.size() == capacity) {
                KEY entityKey = queue.remove().key;
                cache.remove(entityKey);
            }
            entityQueue.add(value);
            updateCache(key, value);
        } else {
            cacheEntity.frequency++;
        }
    }

    private void updateCache(KEY key, VAL value) {
        CacheEntity newCacheEntity = new CacheEntity(key, value, 1);
        queue.add(newCacheEntity);
        cache.put(key, newCacheEntity);
    }

    private void runUpdateSORTask() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        ScheduledFuture scheduledFuture =
                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        if (!entityQueue.isEmpty()) {
                            dao.create(entityQueue.remove());
                        }
                    }
                }, DELAY, PERIOD, TimeUnit.SECONDS);

        if (scheduledFuture.isDone()) {
            scheduledExecutorService.shutdown();
        }
    }

    private class CacheEntity implements Comparable<CacheEntity> {
        private KEY key;
        private VAL val;
        private int frequency;

        public CacheEntity(KEY key, VAL val, int frequency) {
            this.val = val;
            this.key = key;
            this.frequency = frequency;
        }

        public int compareTo(CacheEntity o) {
            if (frequency < o.frequency) {
                return -1;
            } else if (frequency > o.frequency) {
                return 1;
            }
            return 0;
        }
    }
}