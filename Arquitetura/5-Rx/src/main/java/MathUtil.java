/**
 * Created by vinicius.camargo on 31/10/2018
 */
public class MathUtil {
    private MathUtil() {
    }

    public static Integer operacaoLenta(int valor) {
        int a = 5945;
        int b = 5945;
        double c = 1;
        for (int i = 0; i < 1_000_000; i++) {
            c = valor / a + b;
        }
        return valor;
    }
}
