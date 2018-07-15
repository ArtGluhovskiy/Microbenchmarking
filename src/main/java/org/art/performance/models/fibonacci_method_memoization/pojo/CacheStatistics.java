package org.art.performance.models.fibonacci_method_memoization.pojo;

public class CacheStatistics {

    private int cacheSize;
    private int cacheHits;
    private int cacheMisses;

    public CacheStatistics(int cacheSize, int cacheHits, int cacheMisses) {
        this.cacheSize = cacheSize;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public int getCacheHits() {
        return cacheHits;
    }

    public int getCacheMisses() {
        return cacheMisses;
    }

    @Override
    public String toString() {
        return "CacheStatistics {" +
                "cacheSize = " + cacheSize +
                ", cacheHits = " + cacheHits +
                ", cacheMisses = " + cacheMisses +
                '}';
    }
}
