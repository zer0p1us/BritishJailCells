package com.zer0p1us.core;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 *
 * @author zer0p1us
 */
public class CacheManager<Input, CachedData> {
    private ConcurrentHashMap<String, CacheEntry<CachedData>> cache = new ConcurrentHashMap<>();
    private long cacheLifeInMinutes;

    public CacheManager() {
        this.cacheLifeInMinutes = Duration.ofMinutes(30).getSeconds();
    }

    public CacheManager(int cacheLifeInMinutes) {
        this.cacheLifeInMinutes = Duration.ofMinutes(cacheLifeInMinutes).getSeconds();
    }


    public static class CacheEntry< CachedData> {
        public CachedData data;
        public Instant timestamp;
        
        public CacheEntry(CachedData data) {
            this.data = data;
            this.timestamp = Instant.now();
        }
        
        public boolean isExpired(long expiryTimeInSeconds) {
            return Instant.now().isAfter(timestamp.plusSeconds(expiryTimeInSeconds));
        }

        public CachedData getData() {
            return data;
        }

        public void updateCache(CachedData newData) {
            timestamp = Instant.now();
            data = newData;
        }
    }
    
    
    private String getKey(Input input) {
        return input != null ? input.toString() : "null";
    }

    public CachedData getOrCompute(Input input, Function<Input, CachedData> func) {
        CacheEntry<CachedData> entry = cache.get(getKey(input));
        
        if (entry == null) {
            CacheEntry<CachedData> newEntry = new CacheEntry(func.apply(input));
            cache.put(getKey(input), newEntry);
            return newEntry.getData();
        }
        
        // If entry exists and isn't expired, return it
        if (!entry.isExpired(this.cacheLifeInMinutes)) {
            return entry.getData();
        }
        
        // Compute new value
        CachedData value = func.apply(input);
        entry.updateCache(value);
        
        return value;
    }
}
