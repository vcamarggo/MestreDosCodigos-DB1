import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class NotacaoAssintotica {

    private static final int NUM_ELEMENTOS = 50000;

    /**
     * Neste metodo, iniciam-se os vetores de numero,
     * contadores de tempo e chamam-se as execucoes de cada metodo de ordenacao
     **/
    public static void main(String[] args) {
        int[] numerosParaMergeSort = IntStream.generate(() -> new Random().nextInt()).limit(NUM_ELEMENTOS).toArray();
        int[] numerosParaBubbleSort = Arrays.copyOf(numerosParaMergeSort, numerosParaMergeSort.length);

        long inicio = System.currentTimeMillis();
        mergeSort(numerosParaMergeSort);
        System.out.println("Tempo do Merge Sort:" + (System.currentTimeMillis() - inicio));

        inicio = System.currentTimeMillis();
        bubbleSort(numerosParaBubbleSort);
        System.out.println("Tempo do Bubble:" + (System.currentTimeMillis() - inicio));
    }

    /**
     * Neste metodo, inicia-se a chamada ao mergeSort,
     * apenas serve como um wrapper dos parametros do mergeSort
     **/
    private static void mergeSort(int[] numeros) {
        mergeSort(numeros, 0, numeros.length - 1);
    }

    /**
     * Neste metodo, dado um vetor e seus indices,
     * ha a ordenacao deste em tempo O(n log n).
     * Sua analise e feita considerando que a arvore de recursao gerada possui altura de log n,
     * pois a mesma se divide ao meio a cada chamada recursiva,
     * e a chamada ao metodo de ordenacao em si implica em n,
     * onde ao final, as chamadas de ordenação para cada subarvore gerarao a equacao dada por O(n x log(n)),
     * ou resumidamente O(n log n)
     **/
    private static void mergeSort(int[] numeros, int indexInicial, int indexFinal) {
        if (indexInicial < indexFinal) {
            int indexMedio = (indexInicial + indexFinal) / 2;
            mergeSort(numeros, indexInicial, indexMedio);
            mergeSort(numeros, indexMedio + 1, indexFinal);
            merge(numeros, indexInicial, indexMedio, indexFinal);
        }
    }

    /**
     * Neste metodo, dado um vetor e seus indices,
     * ha a ordenacao deste em tempo O(n) e espaco O(n),
     * ja que apenas se percorre o vetor em no maximo n posicoes,
     * onde n e o tamanho do vetor
     **/
    private static void merge(int[] numeros, int indexInicial, int indexMedio, int indexFinal) {
        int i = indexMedio + 1;
        int j = indexMedio;
        int[] numerosAuxiliares = new int[numeros.length];

        for (; i > indexInicial; i--) {
            numerosAuxiliares[i - 1] = numeros[i - 1];
        }
        for (; j < indexFinal; j++) {
            numerosAuxiliares[indexFinal + indexMedio - j] = numeros[j + 1];
        }
        for (int k = indexInicial; k <= indexFinal; k++) {
            if (numerosAuxiliares[j] < numerosAuxiliares[i]) {
                numeros[k] = numerosAuxiliares[j--];
            } else {
                numeros[k] = numerosAuxiliares[i++];
            }
        }
    }

    /**
     * Neste metodo, dado um vetor,
     * ha a ordenacao deste em tempo O(n²),
     * ja que para cada elemento do for mais externo
     * o algoritmo pode iterar sobre os n-1 elementos,
     * que quando levado a notacao assintotica e tomado como n
     * e resulta em for interno x for externo = n x n = O(n²)
     **/
    private static void bubbleSort(int[] numeros) {
        for (int i = 0; i < numeros.length - 1; i++) {
            for (int j = i + 1; j < numeros.length; j++) {
                if (numeros[j] < numeros[i]) {
                    int auxiliar = numeros[i];
                    numeros[i] = numeros[j];
                    numeros[j] = auxiliar;
                }
            }
        }
    }

}
