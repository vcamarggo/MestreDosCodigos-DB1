import io.reactivex.BackpressureStrategy;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class RunnerElastic {
    /**
     * Elástico: O sistema continua responsivo mesmo sob variações de demanda.
     */
    public static void main(String[] args) {

        Emitter1.geraSequencia()
                .mergeWith(Emitter2.geraSequencia())
                .toFlowable(BackpressureStrategy.MISSING)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(Integer o) {
                        System.out.println(MathUtil.operacaoLenta(o));
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Terminei");
                    }
                });
    }


}
