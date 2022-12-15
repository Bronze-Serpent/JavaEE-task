package barabanov.service;

import barabanov.ORM.DAOCurrency;
import barabanov.entity.Currency;
import barabanov.entity.IDToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class CurrencyService
{

    private final DAOCurrency daoCurrency;


    public CurrencyService(DAOCurrency daoCurrency) { this.daoCurrency = daoCurrency; }

    public List<Currency> readAllWithPlId(long plId) throws SQLException { return daoCurrency.readAllWithPlId(plId); }


    public void writeTODB(List<Currency> currencies) throws SQLException
    {
        for (Currency currency : currencies)
            writeToDB(currency);
    }

    public void writeToDB(Currency c) throws SQLException { daoCurrency.create(c);}

    public Currency readFromDB(long id) throws SQLException { return daoCurrency.readById(id); }

    public void updateDB(Currency c) throws SQLException { daoCurrency.update(c);}

    public void deleteFromDB(long id) throws SQLException { daoCurrency.delete(id); }


    public static List<Currency> jsonToCurrencies(JSONArray jArrCurrency)
    {
        List<Currency> currencies = new LinkedList<>();

        for (Object o : jArrCurrency)
        {
            JSONObject jPrgs = (JSONObject) o;
            currencies.add(new Currency(new IDToken((long) jPrgs.get("id"), (long) jPrgs.get("playerId"),
                    (long) jPrgs.get("resourceId")), (String) jPrgs.get("name"), (long) jPrgs.get("count")));
        }

        return currencies;
    }


    public static JSONArray currenciesToJson(List<Currency> currencies)
    {
        JSONArray jsonCurrenciesArr = new JSONArray();
        for (Currency currency : currencies)
        {
            JSONObject jsonCurrency = new JSONObject();

            jsonCurrency.put("id", currency.getToken().getId());
            jsonCurrency.put("playerId", currency.getToken().getPlayerId());
            jsonCurrency.put("resourceId", currency.getToken().getResourceId());
            jsonCurrency.put("name", currency.getName());
            jsonCurrency.put("count", currency.getCount());

            jsonCurrenciesArr.add(jsonCurrency);
        }

        return jsonCurrenciesArr;
    }
}
