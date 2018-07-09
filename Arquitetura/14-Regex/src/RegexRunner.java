import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class RegexRunner {

    // Por ser uma concatenacao feita em uma variavel final,
    // nao ha porque usar StringBuilder,
    // ja que o compilador acaba por representar ambas da mesma forma no bytecode
    final static String LOREM_IPSUM = "Maria era amiga de Joao." +
            " Em uma terça - feira dia 20/04/2018," +
            " eles econtraram um documento do gnomo do codigo que tinha o cpf 012.215.632-28 e o " +
            "devolveram para receber a recompensa de R$ 12.000,50.";
    final static String CPF = "836.766.550-36";
    final static String CNPJ = "62.984.795/0001-77";
    final static String DATE = "30/06/2010";
    final static String CURRENCY_BRL = "R$ 4.500,00";

    public static void main(String[] args) {

		System.out.println("Lorem ipsum: "+LOREM_IPSUM);
		System.out.println();
		System.out.println("CPF: "+CPF);
		System.out.println();
		System.out.println("CPNJ: "+CNPJ);
		System.out.println();
		System.out.println("Date: "+DATE);
		System.out.println();
		System.out.println("Currency BRL: "+CURRENCY_BRL);
		System.out.println();
	
        Arrays.asList(new CpfRegex(), new CnpjRegex(), new DateRegex(), new CurrencyRegex())
                .forEach(RegexRunner::testRegex);
    }

    private static void testRegex(IRegexProvider regex) {
        Pattern r = Pattern.compile(regex.getRegex());
        Matcher matcher = r.matcher(LOREM_IPSUM);
        System.out.println("Lorem Ipsum contains " + regex.getName() + "? " + matcher.find());
        System.out.println("Cpf matches " + regex.getName() + "? " + CPF.matches(regex.getRegex()));
        System.out.println("Cnpj matches " + regex.getName() + "? " + CNPJ.matches(regex.getRegex()));
        System.out.println("Date matches " + regex.getName() + "? " + DATE.matches(regex.getRegex()));
        System.out.println("Currency BRL matches " + regex.getName() + "? " + CURRENCY_BRL.matches(regex.getRegex()));
        System.out.println();
    }
}
