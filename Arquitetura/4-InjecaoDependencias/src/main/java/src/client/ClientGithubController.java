package src.client;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import src.Application;
import src.cache.CacheController;
import src.service.IGithubService;
import src.service.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Scanner;

/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class ClientGithubController {

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    IGithubService githubService;

    @Inject
    CacheController cacheController;

    ClientGithubController() {
        Application.getComponent().inject(this);
    }

    public void getAllRepositories() {
        if (cacheController.isCacheValid()) {
            System.out.println("CACHE USED");
            onSucess(cacheController.getRepositoriesFromCache());
        } else {
            System.out.println("SERVICE USED");
            disposable.add(githubService.getAll().observeOn(Schedulers.io()).subscribe(repositories -> {
                cacheController.updateCache(repositories);
                onSucess(repositories);
            }, this::onError));
        }
    }

    // Tratamento para quando houver algum tipo de erro de conexao, permite ao cliente tentar novamente
    private void onError(Throwable throwable) {
        if (throwable instanceof retrofit2.HttpException) {
            System.err.println("Ha um problema com sua conexao. Deseja tentar novamente? (S)im/(N)ao");
            if (new Scanner(System.in).next().charAt(0) == 'S') {
                getAllRepositories();
            }
        }
        System.err.println("Houve um erro desconhecido no programa. Tente novamente mais tarde.");
    }

    private void onSucess(List<Repository> repositories) {
        repositories.forEach(System.out::println);
    }
}
