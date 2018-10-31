import io.reactivex.Observable;
/**
 * Created by vinicius.camargo on 31/10/2018
 */
public class Emitter1 {
    private Emitter1() {
    }

    static Observable<Integer> geraSequencia() {
        return Observable.range(1, 500).onErrorReturnItem(-1);
    }
}
