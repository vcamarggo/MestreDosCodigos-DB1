/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class CnpjRegex implements IRegexProvider {
    @Override
    public String getRegex() {
        return "\\d{2}.\\d{3}.\\d{3}\\/\\d{4}-\\d{2}";
    }

    @Override
    public String getName() {
        return CnpjRegex.class.getName();
    }
}
