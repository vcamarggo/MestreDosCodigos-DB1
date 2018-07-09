package src.cache;

import src.service.Repository;

import java.util.List;

/**
 * Created by vinicius.camargo on 03/07/2018
 * <p>
 * Classe de cache para manter um cache local de repositorios
 */
public class CacheController {
    private final long CACHE_LIFE = 3000;
    private long lastCacheSaved = 0;
    private List<Repository> repositories;

    public boolean isCacheValid() {
        return System.currentTimeMillis() - lastCacheSaved <= CACHE_LIFE;
    }

    private void resetCacheTimer() {
        lastCacheSaved = System.currentTimeMillis();
    }

    public List<Repository> getRepositoriesFromCache() {
        return repositories;
    }

    public void updateCache(List<Repository> repositories) {
        this.repositories = repositories;
        resetCacheTimer();
    }
}
