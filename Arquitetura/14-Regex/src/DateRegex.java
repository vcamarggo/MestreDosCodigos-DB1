/**
 * Created by vinicius.camargo on 28/06/2018
 */
public class DateRegex implements IRegexProvider {
    @Override
    public String getRegex() {
        return "((0[1-9]|[12]\\d|3[01])[\\/](01|03|05|07|08|10|12)|" +
                "(0[1-9]|[12]\\d|30)[\\/](04|06|09|11)|" +
                "(0[1-9]|1\\d|2[0-8])[\\/](02))[\\/]\\d{4}";
    }

    @Override
    public String getName() {
        return DateRegex.class.getName();
    }
}
