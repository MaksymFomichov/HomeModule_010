package company;

import java.math.BigDecimal;

public class Fruit {
    private BigDecimal price;
    private String dateDelivery; // дата поставки
    private int dateExpiration;
    private TypeFruit fruit;

    public enum TypeFruit {клубника, яблоко, груша, апельсин, банан, ананас, грейпфрут, арбуз, дыня, вишня}

    public Fruit(BigDecimal price, String dateDelivery, int dateExpiration, TypeFruit fruit) {
        this.price = price;
        this.dateDelivery = dateDelivery;
        this.dateExpiration = dateExpiration;
        this.fruit = fruit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDateDelivery() {
        return dateDelivery;
    }

    public int getDateExpiration() {
        return dateExpiration;
    }

    public TypeFruit getFruit() {
        return fruit;
    }
}
