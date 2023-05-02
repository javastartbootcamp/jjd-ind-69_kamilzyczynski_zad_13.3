package pl.javastart.task;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Shop shop = new Shop();
        try {
            List<Product> products = shop.getProducts("src/main/resources/products.csv");
            List<Currency> currencies = shop.getCurrencies("src/main/resources/currencies.csv");

            BigDecimal euroTotalValue = shop.getEuroTotalValue(products, currencies);
            System.out.println("Suma wszystkich produktów w EUR: " + euroTotalValue);

            BigDecimal euroAvgValue = shop.getEuroAvgValue(products, currencies);
            System.out.println("Średnia wartość produktu w EUR: " + euroAvgValue);

            Product mostExpensiveProduct = shop.getMostExpensiveProduct(products, currencies);
            System.out.println("Najdroższy produkt to: " + mostExpensiveProduct.getName() + ", kosztuje [EUR]: "
                    + shop.getProductValue(mostExpensiveProduct, shop.getCurrency(mostExpensiveProduct, currencies)));

            Product mostCheapProduct = shop.getMostCheapProduct(products, currencies);
            System.out.println("Najtańszy produkt to: " + mostCheapProduct.getName() + ", kosztuje [EUR]: "
                    + shop.getProductValue(mostCheapProduct, shop.getCurrency(mostCheapProduct, currencies)));

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (ArithmeticException e) {
            System.out.println("Divided by 0");
        }

    }
}
