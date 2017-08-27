package company.shop;

import com.alibaba.fastjson.JSON;
import company.Main;
import company.utils.DateUtils;
import company.utils.FileUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

public class TradeShop {
    private List<Fruit> fruitsDB = new ArrayList<>();
    private List<Client> orderList = new ArrayList<>();

    private Purse moneyBalance = new Purse();

    public Purse getPurse() {
        return moneyBalance;
    }

    // метод принимает путь к файлу внутри которого находится json с фруктами и датой поставки
    public void addFruits(String pathToJsonFile) {
        String json = null;
        try {
            json = FileUtils.readFromFile(pathToJsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fruitsDB.size() == 0) {
            fruitsDB = JSON.parseArray(json, Fruit.class);
        } else {
            fruitsDB.addAll(JSON.parseArray(json, Fruit.class));
        }
    }

    // метод который сохранит всю информацию со склада лавки в json файл
    public void save(String pathToJsonFile) {
        String json = JSON.toJSONString(fruitsDB);
        try {
            FileUtils.writeToFile(json, pathToJsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод который удаляют текущую информацию из коллекции и загружает новую из сохраненной версии
    public void load(String pathToJsonFile) {
        fruitsDB.clear();
        //showCurrentStatus(); // проверяем, что текущая база пуста
        String json = null;
        try {
            json = FileUtils.readFromFile(pathToJsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fruitsDB = JSON.parseArray(json, Fruit.class);
    }

    // метод способный сказать какие продукты испортятся к заданной дате
    public List<Fruit> getSpoiledFruits(Date date) throws ParseException {
        List<Fruit> fruitList = new ArrayList<>();
        for (Fruit value : fruitsDB) {
            Date dateExpiration = DateUtils.getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
            if (DateUtils.datesComparing(dateExpiration, date)) {
                fruitList.add(value);
            }
        }
        return fruitList;
    }

    // метод способный сказать какие продукты испортятся к заданной дате (проверка на заданный фрукт)
    public List<Fruit> getSpoiledFruits(Date date, Fruit.TypeFruit typeFruit) throws ParseException {
        List<Fruit> fruitList = new ArrayList<>();
        for (Fruit value : fruitsDB) {
            if (value.getFruit().equals(typeFruit)) {
                Date dateExpiration = DateUtils.getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
                if (DateUtils.datesComparing(dateExpiration, date)) {
                    fruitList.add(value);
                }
            }
        }
        return fruitList;
    }

    // метод который возвращает список готовых к продаже продуктов
    public List<Fruit> getAvailableFruits(Date date) throws ParseException {
        List<Fruit> fruitList = new ArrayList<>();
        for (Fruit value : fruitsDB) {
            Date dateExpiration = DateUtils.getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
            if (!DateUtils.datesComparing(dateExpiration, date)) {
                fruitList.add(value);
            }
        }
        return fruitList;
    }

    // метод который возвращает список готовых к продаже продуктов (проверка на заданный фрукт)
    public List<Fruit> getAvailableFruits(Date date, Fruit.TypeFruit typeFruit) throws ParseException {
        List<Fruit> fruitList = new ArrayList<>();
        for (Fruit value : fruitsDB) {
            if (value.getFruit().equals(typeFruit)) {
                Date dateExpiration = DateUtils.getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
                if (!DateUtils.datesComparing(dateExpiration, date)) {
                    fruitList.add(value);
                }
            }
        }
        return fruitList;
    }

    // метод который возвращает продукты которые были доставлены в заданную дату
    public List<Fruit> getAddedFruits(Date date) throws ParseException {
        List<Fruit> fruitList = new ArrayList<>();
        for (Fruit value : fruitsDB) {
            Date dateDelivery = DateUtils.convertStringToDate(value.getDateDelivery());
            if (dateDelivery.getTime() == date.getTime()) {
                fruitList.add(value);
            }
        }
        return fruitList;
    }

    // метод который возвращает определенный тип пролукт который были доставлены в заданную дату
    public List<Fruit> getAddedFruits(Date date, Fruit.TypeFruit typeFruit) throws ParseException {
        List<Fruit> fruitList = new ArrayList<>();
        for (Fruit value : fruitsDB) {
            Date dateDelivery = DateUtils.convertStringToDate(value.getDateDelivery());
            if (dateDelivery.getTime() == date.getTime()) {
                if (value.getFruit().equals(typeFruit)) {
                    fruitList.add(value);
                }
            }
        }
        return fruitList;
    }

    // метод продажи
    public void sell(String pathToJsonFile) {
        String json = null;
        try {
            json = FileUtils.readFromFile(pathToJsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (orderList.size() == 0) {
            orderList = JSON.parseArray(json, Client.class);
        } else {
            orderList.addAll(JSON.parseArray(json, Client.class));
        }
        System.out.println("Список покупок:");
        showOrderList(); // показываем список покупок
        // если фруктв хватает на складе то продаем
        if (checkFruits()) {
            utilSave();
        }
    }

    // метод удаления проданных элементов и обновления кошелька
    private void utilSave(){
        // поэлементно удаляем из базы нужное значение
        for (int i = 0; i < orderList.size(); i++) {
            for (int j = 0; j < orderList.get(i).getCount(); j++) {
                for (int k = 0; k < fruitsDB.size(); k++) {
                    if (orderList.get(i).getTypeFruit().equals(fruitsDB.get(k).getFruit())) {
                        moneyBalance.addPurse(fruitsDB.get(k).getPrice()); // заполняем кошелек баблом
                        fruitsDB.remove(k);
                        break;
                    }

                }
            }
        }
        showCurrentStatusCountFruits(); // показываем склад в количкестве по кадлому фрукту
        save(Main.DB_fruits); // обновляем файл json
    }

    // проверка на достаточность на складе
    private Boolean checkFruits() {
        for (Client value : orderList) {
            if (getCountFruit(value.getTypeFruit()) <= getCountFruitOrder(value.getTypeFruit())) {
                System.out.println("\nНедостаточно фруктов на складе!");
                return false;
            }
        }
        return true;
    }

    // вывод списка покупок
    private void showOrderList() {
        System.out.printf("\n%10s%15s%15s%n", "имя", "фрукт", "количество");
        System.out.println("-------------------------------------------");
        for (Client value : orderList) {
            System.out.printf("%10s%15s%10s%n", "" + value.getName() + "", "" + value.getTypeFruit() + "", "" + value.getCount());
        }
    }

    // вывод текущей информации по лавке
    public void showCurrentStatus() throws ParseException {
        showCurrentStatus(fruitsDB);
    }

    // вывод текущей информации по полученной коллекции
    public void showCurrentStatus(List<Fruit> fruitList) throws ParseException {
        System.out.printf("\n%10s%15s%8s%15s%35s%n", "дата поставки", "тип фрукта", "цена", "срок годности", "дата окончания срока годности");
        System.out.println("---------------------------------------------------------------------------------------");
        for (Fruit value : fruitList) {
            StringJoiner sj = new StringJoiner("      ");
//            System.out.printf("%10s%15s%10s%10s%25s%n", "" + value.getDateDelivery()
//                    + "", "" + value.getFruit()
//                    + "", "" + value.getPrice(), ""
//                    + value.getDateExpiration(), ""
//                    + DateUtils.dateFormat.format(DateUtils.getDateExpiration(value.getDateDelivery(), value.getDateExpiration())));
            String str = sj.add(value.getDateDelivery()).add(String.valueOf(value.getFruit())).add(String.valueOf(value.getPrice())).add(String.valueOf(value.getDateExpiration())).toString();
            System.out.println(str);
        }
    }

    // получение  информации по складу по количеству искомого фрукта
    private int getCountFruit(Fruit.TypeFruit typeFruit) {
        int count = 0;
        for (Fruit value : fruitsDB) {
            if (typeFruit.equals(value.getFruit())) {
                count++;
            }
        }
        return count;
    }

    // получение  информации по заявке по количеству искомого фрукта
    private int getCountFruitOrder(Fruit.TypeFruit typeFruit) {
        int count = 0;
        for (Client value : orderList) {
            if (typeFruit.equals(value.getTypeFruit())) {
                count += value.getCount();
            }
        }
        return count;
    }

    // выводим в консоль количество каждого фрукта на складе
    public void showCurrentStatusCountFruits() {
        System.out.printf("\n%10s%15s%n", "тип фрукта", "количество");
        System.out.println("---------------------------------------");
        for (Fruit.TypeFruit value : Fruit.TypeFruit.values()) {
            System.out.printf("%10s%10s%n", "" + value, "" + getCountFruit(value));
        }
    }
}
