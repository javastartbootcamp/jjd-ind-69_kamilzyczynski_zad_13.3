package pl.javastart.task;

import java.math.BigDecimal;

public class ProductWithEuro extends Product {
    private final BigDecimal priceInEuro;

    public ProductWithEuro(Product product, BigDecimal priceInEuro) {
        super(product.getName(), product.getPrice(), product.getCurrency());
        this.priceInEuro = priceInEuro;
    }

    public BigDecimal getPriceInEuro() {
        return priceInEuro;
    }
}
