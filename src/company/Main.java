package company;

import company.others.DateUtils;
import company.others.GenerateJson;
import company.shop.Fruit;
import company.shop.TradeShop;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Main {
    public static String DB_fruits = "files/data.txt";
    private static TradeShop tradeShop = new TradeShop();
    private static String date1 = "files/date_20.08.2017.txt";
    private static String date2 = "files/date_21.08.2017.txt";
    private static String date3 = "files/date_23.08.2017.txt";
    private static String order = "files/order.txt";

    public static void main(String[] args) throws ParseException {
        // генерируем три файла постаки фруктов
        GenerateJson.generateDelivery();

        // делаем проверки по первой задаче
        firstTask();

        // делаем проверки по второй задаче
        secondTask();

        // делаем проверки по третьей задаче
        thirdTask();

        // делаем проверки по четвертому заданию
        fourthTask();
    }

    private static void firstTask() throws ParseException {
        // делаем первую поставку
        tradeShop.addFruits(date1);
        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
        tradeShop.showCurrentStatus();
        // длаем вторую поставку
        tradeShop.addFruits(date2);
        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
        tradeShop.showCurrentStatus();
        // длаем третью поставку
        tradeShop.addFruits(date3);
        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
        tradeShop.showCurrentStatus();
        // сохраняем информацию
        tradeShop.save(DB_fruits);
        // загружаем информацию из файла основной базы для лавки
        tradeShop.load(DB_fruits);
        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
        tradeShop.showCurrentStatus();
    }

    private static void secondTask() throws ParseException {
        // создаем дату
        Date date = DateUtils.convertStringToDate("01.09.2017");

        // получаем продукты, которые испортятся к заданной дате
        List<Fruit> fruitsSpoiled = tradeShop.getSpoiledFruits(date);
        // выводим их
        System.out.println("\nСписок продуктов, которые испортятся к " + DateUtils.dateFormat.format(date));
        tradeShop.showCurrentStatus(fruitsSpoiled);

        // получаем продукты, которые не испортятся к заданной дате
        List<Fruit> fruitsAvailable = tradeShop.getAvailableFruits(date);
        // выводим их
        System.out.println("\nСписок продуктов, которые не испортятся к " + DateUtils.dateFormat.format(date));
        tradeShop.showCurrentStatus(fruitsAvailable);
    }

    private static void thirdTask() throws ParseException {
        // создаем дату
        Date date = DateUtils.convertStringToDate("01.09.2017");
        Date dateDelivery = DateUtils.convertStringToDate("20.08.2017");
        // создаем фрукт для поиска
        Fruit.TypeFruit typeFruit = Fruit.TypeFruit.апельсин;

        // получаем список по конкретному фрукту, которые испортятся к заданной дате
        List<Fruit> fruitSpoiled = tradeShop.getSpoiledFruits(date, typeFruit);
        // выводим его
        System.out.println("\nСписок " + typeFruit + ", которые испортятся к " + DateUtils.dateFormat.format(date));
        tradeShop.showCurrentStatus(fruitSpoiled);

        // получаем список по конкретному фрукту, которые не испортятся к заданной дате
        List<Fruit> fruitAvailable = tradeShop.getAvailableFruits(date, typeFruit);
        // выводим его
        System.out.println("\nСписок " + typeFruit + ", которые не испортятся к " + DateUtils.dateFormat.format(date));
        tradeShop.showCurrentStatus(fruitAvailable);

        // получаем список фруктов по конкретной дате поставки
        List<Fruit> addedFruits = tradeShop.getAddedFruits(dateDelivery);
        // выводим его
        System.out.println("\nСписок фрутов, которые поставлены " + DateUtils.dateFormat.format(dateDelivery));
        tradeShop.showCurrentStatus(addedFruits);

        // получаем список определленоого фрукта по конкретной дате поставки
        List<Fruit> addedFruitsType = tradeShop.getAddedFruits(dateDelivery, typeFruit);
        // выводим его
        System.out.println("\nСписок " + typeFruit + ", которые поставлены " + DateUtils.dateFormat.format(dateDelivery));
        tradeShop.showCurrentStatus(addedFruitsType);
    }


    private static void fourthTask() {
        // выводим количество каждого фрукта на данный момент на складе
        tradeShop.showCurrentStatusCountFruits();
        // генерируем список покупок
        GenerateJson.generateOrder();
        // показываем список покупок и продаем, если успешно выводим список склада в количестве, если нет то сообщение о неудаче
        tradeShop.sell(order);
        // состояние кошелька
        System.out.println("В кошельке " + tradeShop.getPurse().getSum());

    }
}
