package com.caches.cache;

import com.caches.dao.CrudDao;
import com.caches.dao.InMemoryEntityDao;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Oleg_Chorpita on 5/6/2016.
 */
public class CacheImplTest<KEY, VAL> implements Cache<KEY, VAL> {
    private Map<KEY, VAL> cache = new LinkedHashMap<>();
    private CrudDao dao = new InMemoryEntityDao();


    public VAL get(KEY key) {
        VAL entity = cache.get(key);
        if(entity==null){
            entity = (VAL) dao.read(key);
            cache.put(key, entity);
        }

        return null;
    }

    public void put(KEY key, VAL value) {

    }
}
