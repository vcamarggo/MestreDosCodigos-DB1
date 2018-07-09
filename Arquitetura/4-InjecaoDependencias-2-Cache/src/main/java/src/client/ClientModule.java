package src.client;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vinicius.camargo on 28/06/2018
 */
@Module
public class ClientModule {

    // Prove um implementacao para um controller de taxa de cambio para quando um @Inject for usado
    @Provides
    @SuppressWarnings("unused")
    ClientCurrencyRateController providesClientCurrencyRateController() {
        return new ClientCurrencyRateController();
    }

    // Prove um implementacao para um controller de respositorios github para quando um @Inject for usado
    @Provides
    @SuppressWarnings("unused")
    ClientGithubController providesClientGithubController() {
        return new ClientGithubController();
    }
}

