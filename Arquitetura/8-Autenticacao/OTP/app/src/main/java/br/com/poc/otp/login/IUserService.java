package br.com.poc.otp.login;


import br.com.poc.otp.otp.SeedBody;
import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by vinicius.camargo on 02/10/2018
 */
public interface IUserService {

    @POST("http://192.168.0.100:8080/banking/acessar")
    Single<TokenDto> doLogin(@Body LoginDto loginDto);

    @GET("http://192.168.0.100:8080/banking/semente/existe-ativa")
    Completable hasToken(@Query("account") String accountId);

    @POST("http://192.168.0.100:8080/banking/semente")
    Completable sendSeed(@Body SeedBody seedBody);

}
