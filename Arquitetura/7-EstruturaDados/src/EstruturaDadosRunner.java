import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class EstruturaDadosRunner {

    private static final int MAX_ELEMENTS = 80000;
    private static final List<Integer> bulkData =
            IntStream.iterate(1, n -> n + 1)
                    .limit(MAX_ELEMENTS).boxed().collect(Collectors.toList());

    //ArrayList - armazena dados nao ordenados, porem cresce dinamicamente
    private static final List<Integer> integerList = new ArrayList<>();

    //Stack - armazena dados ordenados pela insercao e apresenta tempos de insercao e remocao otimo,
    // no entanto sofre limitacao de inserir e remover sempre na posicao 0
    private static final Stack<Integer> integerStack = new Stack<>();

    //TreeSet - estrutura de arvore que nao permite repeticao de elementos e utiliza a interface Comparable para manter
    // os elementos sempre ordenados e consulta-los em tempo O(log n) com busca binaria
    private static final TreeSet<Integer> integerTree = new TreeSet<>();

    public static void main(String[] args) {
        testInsercao();
        System.out.println();
        testPrintAll();
        System.out.println();
        testRemocao();
    }

    /**
     * Na insercao a estruturas sao avaliadas como segue:
     * lista: boa, insere rapidamente com tempo O(1)
     * pilha: otima, insere rapidamente com tempo O(1), mas ainda mais rapido que lista, pois insere sempre no indice 0 usando push
     * arvore: ruim, insere rapidamente com tempo O(log n) pois insere sempre ordenado (utilizando insercao de busca binaria)
     **/
    private static void testInsercao() {
        long inicio = System.currentTimeMillis();
        bulkData.forEach(integerList::add);
        System.out.println("Insercao na list: " + (System.currentTimeMillis() - inicio));

        inicio = System.currentTimeMillis();
        bulkData.forEach(integerStack::push);
        System.out.println("Insercao na stack: " + (System.currentTimeMillis() - inicio));

        inicio = System.currentTimeMillis();
        bulkData.forEach(integerTree::add);
        System.out.println("Insercao na tree: " + (System.currentTimeMillis() - inicio));
    }

    /**
     * Na consulta a estruturas sao avaliadas como segue:
     * lista: ruim, pode consumir ate O(n) operacoes para achar um elemento na lista
     * pilha: ruim, pode consumir ate O(n) operacoes para achar um elemento, se comportando como uma lista
     * arvore: boa, consulta em tempo O(log n) com busca binaria
     **/
    private static void testPrintAll() {
        long inicio = System.currentTimeMillis();
        bulkData.forEach(integerList::contains);
        System.out.println("Consulta na list: " + (System.currentTimeMillis() - inicio));

        inicio = System.currentTimeMillis();
        bulkData.forEach(integerStack::contains);
        System.out.println("Consulta na stack: " + (System.currentTimeMillis() - inicio));

        inicio = System.currentTimeMillis();
        bulkData.forEach(integerTree::contains);
        System.out.println("Consulta na tree: " + (System.currentTimeMillis() - inicio));
    }

    /**
     * Na remomcao a estruturas sao avaliadas como segue:
     * lista: ruim, pode consumir ate O(n) operacoes para achar um elemento na lista e então remove-lo
     * pilha: otima, consome O(1) pois sempre remove da primeira posicao usando pop
     * arvore: boa, remove em tempo O(log n) com busca binaria mais um pequeno tempo de reestruturacao da arvore
     **/
    private static void testRemocao() {
        long inicio = System.currentTimeMillis();
        bulkData.forEach(integerList::remove);
        System.out.println("Remocao na list: " + (System.currentTimeMillis() - inicio));

        inicio = System.currentTimeMillis();
        bulkData.forEach(integer -> integerStack.pop());
        System.out.println("Remocao na stack: " + (System.currentTimeMillis() - inicio));

        inicio = System.currentTimeMillis();
        bulkData.forEach(integerTree::remove);
        System.out.println("Remocao na tree: " + (System.currentTimeMillis() - inicio));
    }
}
