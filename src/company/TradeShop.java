package company;

import com.alibaba.fastjson.JSON;
import company.filework.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TradeShop {
    private List<Fruit> fruits = new ArrayList<>();

    // метод принимает путь к файлу внутри которого находится json с фруктами и датой поставки
    public void addFruits(String pathToJsonFile) {
        String json = null;
        try {
            json = FileUtils.readFromFile(pathToJsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fruits.size() == 0) {
            fruits = JSON.parseArray(json, Fruit.class);
        } else {
            fruits.addAll(JSON.parseArray(json, Fruit.class));
        }
    }

    // метод который сохранит всю информацию со склада лавки в json файл
    public void save(String pathToJsonFile) {
        String json = JSON.toJSONString(fruits);
        try {
            FileUtils.writeToFile(json, pathToJsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод который удаляют текущую информацию из коллекции и загружает новую из сохраненной версии
    public void load(String pathToJsonFile) {
        fruits.clear();
        //showCurrentStatus(); // проверяем, что текущая база пуста
        String json = null;
        try {
            json = FileUtils.readFromFile(pathToJsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fruits = JSON.parseArray(json, Fruit.class);
    }

    // вывод текущей информации по лавке
    public void showCurrentStatus() {
        System.out.printf("\n%10s%15s%8s%15s%n", "дата поставки", "тип фрукта", "цена", "срок годности");
        System.out.println("-------------------------------------------------");
        for (Fruit value : fruits) {
            System.out.printf("%10s%15s%10s%10s%n", "" + value.getDateDelivery() + "", "" + value.getFruit() + "", "" + value.getPrice(), "" + value.getDateExpiration());
        }
    }


}
