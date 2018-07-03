import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by vinicius.camargo on 28/06/2018
 */
public final class EvenFatorial {

    public static void main(String[] args) {
        // variavel final, ou seja, de referencia imutavel
        final List<Long> numbers = LongStream.iterate(1, n -> n + 1).limit(10).boxed().collect(Collectors.toList());

        // a funcao aninhada declarada em filter tem proposito de filtrar apenas os numeros pares
        // a funcao reduce utiliza acumulador para armazenar os valores parciais calculados pela funcao simpleFatorial
        System.out.println("Fatorial apenas dos numeros pares da lista: " +
                numbers
                        .stream()
                        .filter(x -> x % 2 == 0) 
                        .reduce(1L, EvenFatorial::simpleFatorial));
    }

    /**
     * Funcao pura escrita para calcular a multiplicacao parcial dos itens que irao compor o fatorial.
     * A funcao recebe dois longs imutaveis para indicar a quem extendera esta classes
     * que estes nao podem ser modificados dentro do metodo.
     * Ainda e importante notar que por ser uma funcao matematica, ela sempre retornara o mesmo valor
     * dadas as mesmas entradas, sendo assim, uma funcao pura.
     */
    private static long simpleFatorial(final long x, final long y) {
        return x * y;
    }

}
