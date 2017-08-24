package company.shop;

import java.math.BigDecimal;

public class Purse {
    private BigDecimal purse = new BigDecimal(0);

    public BigDecimal getSum() {
        return purse.setScale(2);
    }

    public void addPurse(BigDecimal money) {
        purse = purse.add(money);
    }
}