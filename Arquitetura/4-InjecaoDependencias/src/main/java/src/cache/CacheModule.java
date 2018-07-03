package src.cache;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by vinicius.camargo on 03/07/2018
 */
@Module
public class CacheModule {

    @Provides
    public CacheController providesCacheController() {
        return new CacheController();
    }
}
