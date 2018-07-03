package src.service;


import io.reactivex.Single;
import retrofit2.http.GET;
/**
 * Created by vinicius.camargo on 29/06/2018
 *
 * Servico de conexao ao exchange rates API
 */
public interface ICurrencyRateService {

    @GET("https://exchangeratesapi.io/api/latest?base=BRL")
    Single<CurrencyRate> getCurrencyRate();

}


