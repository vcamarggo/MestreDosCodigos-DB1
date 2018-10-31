import io.reactivex.Observable;
/**
 * Created by vinicius.camargo on 31/10/2018
 */
public class Emitter2 {
    private Emitter2() {
    }

    static Observable<Integer> geraSequencia() {
        return Observable.range(800, 100).onErrorReturnItem(-1);
    }
}
