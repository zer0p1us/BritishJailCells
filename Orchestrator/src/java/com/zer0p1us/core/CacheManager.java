package com.zer0p1us.core;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author zer0p1us
 * @param <Input> input type
 * @param <CachedData> cache data type
 */
public class CacheManager<Input, CachedData> {
    private final ConcurrentHashMap<String, CacheEntry<CachedData>> cache = new ConcurrentHashMap<>();
    private final long cacheLifeInMinutes;
    private static final Logger LOGGER = Logger.getLogger(CacheManager.class.getName());

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
        if (input == null) return "null";
        if (input.getClass().isArray()) {
            return Arrays.stream((Object[]) input)
                .map(item -> item == null ? "null" : item.toString())
                .collect(Collectors.joining("|"));
        }
        return input.toString();
    }

    public CachedData getOrCompute(Input input, Function<Input, CachedData> func) {
        String key = getKey(input);
        LOGGER.log(Level.INFO, "key: {0}", key);
        CacheEntry<CachedData> entry = cache.get(key);
        
        if (entry == null) {
            CacheEntry<CachedData> newEntry = new CacheEntry(func.apply(input));
            cache.put(getKey(input), newEntry);
            LOGGER.log(Level.INFO, "Created new cache entry with value: {0}", newEntry.getData().toString());
            return newEntry.getData();
        }
        
        // If entry exists and isn't expired, return it
        if (!entry.isExpired(this.cacheLifeInMinutes)) {
            LOGGER.log(Level.INFO, "Cache hit! Returning existing value: {0}", entry.getData().toString());
            return entry.getData();
        }
        
        // Compute new value
        CachedData value = func.apply(input);
        entry.updateCache(value);
        LOGGER.log(Level.INFO, "Updated existing cache entry with new value:{0}", value.toString());
        
        return value;
    }
}
