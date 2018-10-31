package src.client;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import src.Application;
import src.service.ICurrencyRateService;

import javax.inject.Inject;

/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class ClientCurrencyRateController {
    private final CompositeDisposable disposable = new CompositeDisposable();

    // Injecao de dependecia da interface de servicos de taxas de cambio
    @Inject
    ICurrencyRateService iCurrencyRateService;

    ClientCurrencyRateController() {
        Application.getComponent().inject(this);
    }

    // Requisicao com resposta bloqueante para que a thread principal nao morra antes desta obter seu resultado
    public void getCurrencyRate() {
        System.out.println(iCurrencyRateService.getCurrencyRate().observeOn(Schedulers.io()).blockingGet());
    }

}
