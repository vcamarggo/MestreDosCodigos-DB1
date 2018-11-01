import io.reactivex.BackpressureStrategy;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vinicius.camargo on 31/10/2018
 */
public class RunnerDrop {
    /**
     * Responsivo: O sistema utiliza o BackPressure Drop, onde ao não conseguir lidar com os objetos, o sistema "dropa" os excedentes.
     * Orientado a Mensagens: utiliza o subscribe, que se inscreve para ouvir, tratando cada tipo de mensagem de uma maneira
     */
    public static void main(String[] args) {

        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(Emitter1.geraSequencia()
                .mergeWith(Emitter2.geraSequencia())
                .toFlowable(BackpressureStrategy.MISSING)
                .onBackpressureDrop(integer -> System.out.println("Dropped " + integer))
                .map(MathUtil::operacaoLenta)
                .observeOn(Schedulers.computation())
                .subscribe(
                        //"mensagem" de sucesso de emissão individual doOnNext()
                        System.out::println,
                        //"mensagem" de erro de emissão doOnError()
                        System.out::println,
                        //"mensagem" de sucesso de emissão total doOnComplete()
                        () -> System.out.println("Terminei")));
    }

}
