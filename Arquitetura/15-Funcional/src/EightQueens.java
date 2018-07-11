import java.util.*;

/**
 * Created by vinicius.camargo on 11/07/2018
 */
public class EightQueens {

    private static int tamanho;
    private static final LinkedList<Integer> solucaoParcial = new LinkedList<>();
    private static Set<LinkedList<Integer>> solucao = new HashSet<>();
    private static boolean APENAS_UMA_SOLUCAO = false;

    public static void main(String[] args) {
        System.out.println("Insira qual tamanho n, que seu tabuleiro n x n terá: ");
//        tamanho = new Scanner(System.in).nextInt();
        tamanho = 4;
        int tentativas = 0;
        boolean visitouTodosFilhos = false;

        while (!solved()) {
            int ultimaLinhaTentada = 0;

            if (tentouTodasLinhas(tentativas) || visitouTodosFilhos) {
                if (solucaoParcial.isEmpty()) {
                    break;
                }
                ultimaLinhaTentada = solucaoParcial.getLast();
                ultimaLinhaTentada++;
                solucaoParcial.pollLast();
            }

            tentativas = 0;
            visitouTodosFilhos = false;

            while (!tentouTodasLinhas(tentativas)) {
                if (ultimaLinhaTentada == tamanho) {
                    visitouTodosFilhos = true;
                    break;
                }
                if (isSafe(solucaoParcial, ultimaLinhaTentada)) {
                    solucaoParcial.addLast(ultimaLinhaTentada);
                    break;
                } else {
                    tentativas++;
                    ultimaLinhaTentada++;
                }
            }
        }
        System.out.println("Solucao: " + solucao);
    }

    //Função pura
    private static boolean tentouTodasLinhas(final int tryCounter) {
        return tryCounter == tamanho;
    }

    private static boolean solved() {
        if (solucaoParcial.size() == tamanho) {
            LinkedList<Integer> solucaoEncontrada = new LinkedList<>();
            solucaoParcial.forEach(i -> solucaoEncontrada.add(++i));
            solucao.add(solucaoEncontrada);
            return APENAS_UMA_SOLUCAO;
        }
        return false;
    }

    /**
     * Funcao pura escrita para verificar se a insercao do item continua sendo segura.
     * A funcao recebe duas variaveis imutaveis para indicar a quem extendera esta classes
     * que estes nao podem ser modificados dentro do metodo.
     * Ainda e importante notar que por ser uma funcao de validacao sem efeito colateral,
     * ela sempre retornara o mesmo valor
     * dadas as mesmas entradas, sendo assim, uma funcao pura.
     */
    private static boolean isSafe(final LinkedList<Integer> solverMatrix, final int indexToAdd) {
        for (int i = 0; i < solverMatrix.size(); i++) {
            int deltaRow = Math.abs(solverMatrix.get(i) - indexToAdd);
            int deltaCol = Math.abs(i - solverMatrix.size());
            if (deltaRow == deltaCol) {
                return false;
            }
        }
        return solverMatrix.stream().noneMatch(integer -> integer == indexToAdd);
    }
}
