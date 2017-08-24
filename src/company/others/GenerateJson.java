package company.others;

import com.alibaba.fastjson.JSON;
import company.shop.Client;
import company.shop.Fruit;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateJson {
    private static String[] dateDelivery = {"20.08.2017", "21.08.2017", "23.08.2017"};
    private static String[] clients = {"Александр", "Ольга", "Злата", "Марина", "Иван"};
    private static Random random = new Random();

    private GenerateJson() {
    }

    public static void generateDelivery() {
        for (int i = 0; i < dateDelivery.length; i++) {
            List<Fruit> fruits = new ArrayList<>();
            int countFruit = random.nextInt(9) + 1;
            //int countFruit = 1;
            for (int j = 0; j < countFruit; j++) {
                fruits.add(new Fruit(BigDecimal.valueOf(50.25), dateDelivery[i], 10, Fruit.TypeFruit.апельсин));
                fruits.add(new Fruit(BigDecimal.valueOf(40.69), dateDelivery[i], 20, Fruit.TypeFruit.ананас));
                fruits.add(new Fruit(BigDecimal.valueOf(20.24), dateDelivery[i], 5, Fruit.TypeFruit.арбуз));
                fruits.add(new Fruit(BigDecimal.valueOf(99.99), dateDelivery[i], 4, Fruit.TypeFruit.банан));
                fruits.add(new Fruit(BigDecimal.valueOf(25.24), dateDelivery[i], 8, Fruit.TypeFruit.вишня));
                fruits.add(new Fruit(BigDecimal.valueOf(69.96), dateDelivery[i], 6, Fruit.TypeFruit.грейпфрут));
                fruits.add(new Fruit(BigDecimal.valueOf(47.74), dateDelivery[i], 15, Fruit.TypeFruit.груша));
                fruits.add(new Fruit(BigDecimal.valueOf(23.01), dateDelivery[i], 20, Fruit.TypeFruit.дыня));
                fruits.add(new Fruit(BigDecimal.valueOf(12.65), dateDelivery[i], 13, Fruit.TypeFruit.клубника));
                fruits.add(new Fruit(BigDecimal.valueOf(58.25), dateDelivery[i], 12, Fruit.TypeFruit.яблоко));

            }
            String json = JSON.toJSONString(fruits);
            try {
                FileUtils.writeToFile(json, "files/date_" + dateDelivery[i] + ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateOrder(){
        List<Client> clientsList = new ArrayList<>();
        for (int i = 0; i < clients.length; i++) {
            int countFruit = random.nextInt(18) + 1;
            int countTypeFruit = random.nextInt(Fruit.TypeFruit.values().length-1) + 1;
            clientsList.add(new Client(clients[i], countFruit, Fruit.TypeFruit.values()[countTypeFruit]));
        }

        String json = JSON.toJSONString(clientsList);
        try {
            FileUtils.writeToFile(json, "files/order.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

}
