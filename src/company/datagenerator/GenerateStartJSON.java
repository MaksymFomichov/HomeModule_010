package company.datagenerator;

import com.alibaba.fastjson.JSON;
import company.Fruit;
import company.filework.FileUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateStartJSON {
    private static String[] dateDelivery = {"20.08.2017", "21.08.2017", "23.08.2017"};
    private static Random random = new Random();
    private static final int countFruit = 1;

    private GenerateStartJSON() {
    }

    public static void startGenerate() {
        for (int i = 0; i < dateDelivery.length; i++) {
            List<Fruit> fruits = new ArrayList<>();
            for (int j = 0; j < countFruit; j++) {
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(100) + 50), dateDelivery[i], random.nextInt(20) + 10, Fruit.TypeFruit.апельсин));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(80) + 40), dateDelivery[i], random.nextInt(40) + 20, Fruit.TypeFruit.ананас));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(40) + 20), dateDelivery[i], random.nextInt(10) + 5, Fruit.TypeFruit.арбуз));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(125) + 100), dateDelivery[i], random.nextInt(15) + 4, Fruit.TypeFruit.банан));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(35) + 25), dateDelivery[i], random.nextInt(16) + 8, Fruit.TypeFruit.вишня));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(89) + 69), dateDelivery[i], random.nextInt(12) + 6, Fruit.TypeFruit.грейпфрут));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(57) + 47), dateDelivery[i], random.nextInt(35) +15, Fruit.TypeFruit.груша));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(43) + 23), dateDelivery[i], random.nextInt(40) + 20, Fruit.TypeFruit.дыня));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(24) + 12), dateDelivery[i], random.nextInt(28) + 13, Fruit.TypeFruit.клубника));
                fruits.add(new Fruit(BigDecimal.valueOf(random.nextInt(100) + 58), dateDelivery[i], random.nextInt(30) + 12, Fruit.TypeFruit.яблоко));

            }
            String json = JSON.toJSONString(fruits);
            try {
                FileUtils.writeToFile(json, "files/date_" + dateDelivery[i] + ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
