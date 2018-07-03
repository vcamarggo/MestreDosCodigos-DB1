package src.client;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import src.Application;
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

    ClientGithubController() {
        Application.getComponent().inject(this);
    }

    public void getAllRepositories() {
        disposable.add(githubService.getAll().observeOn(Schedulers.io()).subscribe(this::onSucess, this::onError));
    }

    // Tratamento para quando houver algum tipo de erro de conexao, permite ao cliente tentar novamente
    private void onError(Throwable throwable) {
        if (throwable instanceof retrofit2.HttpException) {
            System.out.println("Ha um problema com sua conexao. Deseja tentar novamente? (S)im/(N)ao");
            if (new Scanner(System.in).next().charAt(0) == 'S') {
                getAllRepositories();
            }
        }
        System.out.println("Houve um erro desconhecido no programa. Tente novamente mais tarde.");
    }

    private void onSucess(List<Repository> repositories) {
        repositories.forEach(System.out::println);
    }
}
