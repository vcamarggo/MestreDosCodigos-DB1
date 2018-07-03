/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class CpfRegex implements IRegexProvider {
    @Override
    public String getRegex() {
        return "\\d{3}.\\d{3}.\\d{3}-\\d{2}";
    }

    @Override
    public String getName() {
        return CpfRegex.class.getName();
    }
}
