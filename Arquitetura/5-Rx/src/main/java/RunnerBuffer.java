import io.reactivex.BackpressureStrategy;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.Scanner;

/**
 * Created by vinicius.camargo on 31/10/2018
 */
public class RunnerBuffer {

    private static final int BUFFER_SIZE = 2;

    public static void main(String[] args) {

        CompositeDisposable disposable = new CompositeDisposable();

        disposable.add(Emitter1.geraSequencia()
                .mergeWith(Emitter2.geraSequencia())
                .toFlowable(BackpressureStrategy.MISSING)
                .onBackpressureBuffer(BUFFER_SIZE, () -> System.out.println("BufferOverflow"))
                .map(MathUtil::operacaoLenta)
                .observeOn(Schedulers.computation())
                .subscribe(System.out::println,
                        Throwable::printStackTrace,
                        () -> System.out.println("Terminei")));
        new Scanner(System.in).next();
    }

}
