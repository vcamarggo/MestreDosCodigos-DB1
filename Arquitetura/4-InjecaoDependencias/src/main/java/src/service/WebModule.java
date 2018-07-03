package src.service;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

/**
 * Created by vinicius.camargo on 28/06/2018
 */
@Module
public class WebModule {

    private static Retrofit retrofit;

    // prove uma instancia do retrofit para quando os servicos injetados necessitarem
    @Provides
    @Singleton
    Retrofit provideCall() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://base.com")
                    .client(getHeader())
                    .addConverterFactory(GsonConverterFactory.create(JsonUtils.getGsonInstance()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // cria os cabecalhos das requisicoes HTTP(S)
    private static OkHttpClient getHeader() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        return new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)

                .addInterceptor(chain -> {
                    Request original = chain.request();
                    return chain.proceed(original);
                })

//                .addInterceptor(logging)
                .build();
    }


    // prove uma implementacao do GitHubService para quando houver um @Inject
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    IGithubService providesGithubService(
            Retrofit retrofit) {
        return retrofit.create(IGithubService.class);
    }

    // prove uma implementacao do CurrencyRateService para quando houver um @Inject
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    ICurrencyRateService providesCurrencyRateService(
            Retrofit retrofit) {
        return retrofit.create(ICurrencyRateService.class);
    }
}

