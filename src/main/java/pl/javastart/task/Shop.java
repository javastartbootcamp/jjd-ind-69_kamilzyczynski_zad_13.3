package pl.javastart.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shop {

    public List<Product> getProducts(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        List<Product> products = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(";");
            products.add(new Product(split[0], new BigDecimal(split[1]), split[2]));
        }
        return products;
    }

    public List<Currency> getCurrencies(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        List<Currency> currencies = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(";");
            currencies.add(new Currency(split[0], new BigDecimal(split[1])));
        }
        return currencies;
    }

    public BigDecimal getEuroTotalValue(List<Product> products, List<Currency> currencies) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : products) {
            for (Currency currency : currencies) {
                if (product.getCurrency().equals(currency.getName())) {
                    sum = sum.add(getProductValue(product, currency));
                }
            }
        }
        return sum;
    }

    public BigDecimal getEuroAvgValue(List<Product> products, List<Currency> currencies) {
        if (products.size() > 0) {
            return getEuroTotalValue(products, currencies).divide(new BigDecimal(products.size()),
                     new MathContext(getEuroTotalValue(products, currencies).precision()));
        } else {
            throw new ArithmeticException("Brak produktów");
        }
    }

    public Product getMostExpensiveProduct(List<Product> products, List<Currency> currencies) {
        double max = 0;
        Product product = null;
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < currencies.size(); j++) {
                if (products.get(i).getCurrency().equals(currencies.get(j).getName())) {
                    BigDecimal cost = getProductValue(products.get(i), currencies.get(j));
                    double result = cost.doubleValue();
                    if (result > max) {
                        product = products.get(i);
                        max = result;
                    }
                }
            }
        }
        return product;
    }

    public Product getMostCheapProduct(List<Product> products, List<Currency> currencies) {
        double min = Double.MAX_VALUE;
        Product product = null;
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < currencies.size(); j++) {
                if (products.get(i).getCurrency().equals(currencies.get(j).getName())) {
                    BigDecimal cost = getProductValue(products.get(i), currencies.get(j));
                    double result = cost.doubleValue();
                    if (result < min) {
                        product = products.get(i);
                        min = result;
                    }
                }
            }
        }
        return product;
    }

    public BigDecimal getProductValue(Product product, Currency currency) {
        if (currency.getValue().compareTo(BigDecimal.ZERO) != 0) {
            return product.getPrice().divide(currency.getValue(), new MathContext(product.getPrice().precision()));
        } else {
            throw new ArithmeticException("Błędna wartość waluty");
        }
    }

    public Currency getCurrency(Product product, List<Currency> currencies) {
        Currency result = null;
        for (Currency currency : currencies) {
            if (currency.getName().equals(product.getCurrency())) {
                result = currency;
            }
        }
        return result;
    }
}



