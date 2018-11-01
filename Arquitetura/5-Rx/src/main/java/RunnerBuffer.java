import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vinicius.camargo on 31/10/2018
 */
public class RunnerBuffer {

    private static final int BUFFER_SIZE = 2;

    /**
     * Resiliente: O sistema continua operando mesmo sobre condições adversas
     */
    public static void main(String[] args) {

        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(Emitter1.geraSequencia()
                .mergeWith(Emitter2.geraSequencia())
                .toFlowable(BackpressureStrategy.MISSING)
                .onBackpressureBuffer(BUFFER_SIZE, () -> System.out.println("BufferOverflow"))
                .map(MathUtil::operacaoLenta)
                .map(integer -> {
                    if (integer % 100 == 0) {
                        return Observable.error(new ArithmeticException("Erro ao dividir multiplo de 100"));
                    } else {
                        return integer;
                    }
                })
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
