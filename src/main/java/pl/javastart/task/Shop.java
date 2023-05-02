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
        List<Product> products;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            products = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(";");
                products.add(new Product(split[0], new BigDecimal(split[1]), split[2]));
            }
        }
        return products;
    }

    public Currencies getCurrencies(String fileName) throws FileNotFoundException {
        List<Currency> currencies;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            currencies = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(";");
                currencies.add(new Currency(split[0], new BigDecimal(split[1])));
            }
        }
        return new Currencies(currencies);
    }

    public BigDecimal getEuroTotalValue(List<Product> products, Currencies currencies) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Product product : products) {
            Currency currency = currencies.findByName(product.getCurrency());
            sum = sum.add(getProductValue(product, currency));
        }
        return sum;
    }

    public BigDecimal getEuroAvgValue(List<Product> products, Currencies currencies) {
        if (products.size() > 0) {
            return getEuroTotalValue(products, currencies).divide(new BigDecimal(products.size()),
                     new MathContext(getEuroTotalValue(products, currencies).precision()));
        } else {
            throw new ArithmeticException("Brak produktów");
        }
    }

    public ProductWithEuro getMostExpensiveProduct(List<Product> products, Currencies currencies) {
        BigDecimal max = BigDecimal.ZERO;
        Product result = null;
        for (Product product : products) {
            Currency currency = currencies.findByName(product.getCurrency());
            BigDecimal cost = getProductValue(product, currency);
            if (cost.compareTo(max) > 0) {
                result = product;
                max = cost;
            }
        }
        return new ProductWithEuro(result, max);
    }

    public ProductWithEuro getMostCheapProduct(List<Product> products, Currencies currencies) {
        BigDecimal min = BigDecimal.valueOf(Double.MAX_VALUE);
        Product result = null;
        for (Product product : products) {
            Currency currency = currencies.findByName(product.getCurrency());
            BigDecimal cost = getProductValue(product, currency);
            if (cost.compareTo(min) < 0) {
                result = product;
                min = cost;
            }
        }
        return new ProductWithEuro(result, min);
    }

    public BigDecimal getProductValue(Product product, Currency currency) {
        if (currency.getValue().compareTo(BigDecimal.ZERO) != 0) {
            return product.getPrice().divide(currency.getValue(), new MathContext(product.getPrice().precision()));
        } else {
            throw new ArithmeticException("Błędna wartość waluty");
        }
    }
}



