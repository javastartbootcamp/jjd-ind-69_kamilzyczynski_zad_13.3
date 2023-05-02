package pl.javastart.task;

import java.util.List;

public class Currencies {
    private List<Currency> currencies;

    public Currencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Currency findByName(String currencyName) {
        for (Currency currency : currencies) {
            if (currency.getName().equals(currencyName)) {
                return currency;
            }
        }
        return null;
    }
}
