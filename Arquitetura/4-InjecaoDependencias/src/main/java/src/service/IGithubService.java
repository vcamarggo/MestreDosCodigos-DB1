package src.service;


import io.reactivex.Observable;
import retrofit2.http.GET;

import java.util.List;
/**
 * Created by vinicius.camargo on 29/06/2018
 *
 * Servico de conexao ao github
 */
public interface IGithubService {

    @GET("https://api.github.com/repositories")
    Observable<List<Repository>> getAll();

}


