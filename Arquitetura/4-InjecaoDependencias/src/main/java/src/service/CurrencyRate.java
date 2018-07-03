package src.service;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by vinicius.camargo on 29/06/2018
 * POJO para representar uma taxa de cambio
 */
public class CurrencyRate {
    @SerializedName("base")
    private String base;

    @SerializedName("date")
    private Date date;

    @SerializedName("rates")
    private HashMap<String, String> rates;

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "base='" + base + '\'' +
                ", date=" + date +
                ", rates=" + rates +
                '}';
    }
}
