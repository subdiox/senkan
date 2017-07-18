package com.kayac.lobi.libnakamap.cache;

import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.value.FileCacheValue;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheManager {
    private static final long CACHE_LIMIT = 2097152;
    private static final int FETCH_COUNT = 100;
    private static final String TAG = "[cache]";

    public static void startSyncCache() {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(new Runnable() {
            public void run() {
                CacheManager.syncCacheImpl();
                service.shutdown();
            }
        });
    }

    private static void syncCacheImpl() {
        int deleteCount;
        long sum = TransactionDatastore.getFileCacheFileSizeSum();
        long t = System.currentTimeMillis();
        int total = TransactionDatastore.getFileCacheEntryCount();
        if (sum > CACHE_LIMIT) {
            deleteCount = (int) Math.max((((long) total) * CACHE_LIMIT) / sum, (long) (total / 2));
        } else {
            deleteCount = 0;
        }
        int i = 0;
        long fromDate = 0;
        for (int fetchIndex = 0; fetchIndex < total; fetchIndex += 100) {
            for (FileCacheValue cacheFile : TransactionDatastore.getFileCaches(100, fromDate)) {
                File file = new File(cacheFile.getPath());
                int i2 = i + 1;
                if (i < deleteCount) {
                    if (file.exists()) {
                        file.delete();
                        TransactionDatastore.deleteFileCache(cacheFile.getPath());
                    }
                } else if (!file.exists()) {
                    TransactionDatastore.deleteFileCache(cacheFile.getPath());
                }
                fromDate = cacheFile.getCreatedAt();
                i = i2;
            }
        }
        long newSum = TransactionDatastore.getFileCacheFileSizeSum();
        int newTotal = TransactionDatastore.getFileCacheEntryCount();
    }
}
