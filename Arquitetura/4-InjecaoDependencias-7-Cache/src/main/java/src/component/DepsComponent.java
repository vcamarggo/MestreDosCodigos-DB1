package src.component;


import dagger.Component;
import src.Application;
import src.cache.CacheModule;
import src.client.ClientCurrencyRateController;
import src.client.ClientGithubController;
import src.client.ClientModule;
import src.service.WebModule;

import javax.inject.Singleton;

/**
 * Created by vinicius.camargo on 28/06/2018
 * <p>
 * Classe que une os modulos da aplicacao e torna claro quais classes podem fazer seu uso
 */
@Singleton
@Component(modules = {WebModule.class, ClientModule.class, CacheModule.class,})
public interface DepsComponent {

    void inject(ClientGithubController clientGithubController);

    void inject(ClientCurrencyRateController clientCurrencyRateController);

    void inject(Application application);
}