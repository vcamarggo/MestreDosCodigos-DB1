/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class CurrencyRegex implements IRegexProvider {
    @Override
    public String getRegex() {
        return "(R\\$) (([0],)\\d+|\\d{1,3}(\\.[0-9]{3})*,\\d{2})";
    }

    @Override
    public String getName() {
        return CurrencyRegex.class.getName();
    }
}
