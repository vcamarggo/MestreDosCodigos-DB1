package src;

import src.cache.CacheModule;
import src.client.ClientCurrencyRateController;
import src.client.ClientGithubController;
import src.client.ClientModule;
import src.component.DaggerDepsComponent;
import src.component.DepsComponent;
import src.service.WebModule;

import javax.inject.Inject;

/**
 * Created by vinicius.camargo on 29/06/2018
 */
public class Application {

    // Componente que gerencia as injecoes da aplicacao
    private static DepsComponent component;

    //Injeta os dois controladores (classes concretas) que controlam realizam as tarefas da aplicação
    @Inject
    ClientCurrencyRateController clientCurrencyRateController;
    @Inject
    ClientGithubController clientGithubController;


    void startApplication() {
        initializeComponent();
        getComponent().inject(this);

        //Requisicao feita em uma thread IO
        for (int i = 0; i < 4; i++) {
            clientGithubController.getAllRepositories();
        }

        //Requisicao feita em outra thread IO
        clientCurrencyRateController.getCurrencyRate();
    }

    // Inicializa a injecao de dependencia com modulo web e modulo cliente
    private void initializeComponent() {
        component = DaggerDepsComponent.builder()
                .webModule(new WebModule())
                .clientModule(new ClientModule())
                .cacheModule(new CacheModule())
                .build();
    }

    public static DepsComponent getComponent() {
        return component;
    }
}
