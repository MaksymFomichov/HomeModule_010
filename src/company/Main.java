package company;

import company.others.GenerateStartJSON;
import company.shop.Fruit;
import company.shop.TradeShop;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static String DB_fruits = "files/data.txt";
    private static TradeShop tradeShop = new TradeShop();
    private static String date1 = "files/date_20.08.2017.txt";
    private static String date2 = "files/date_21.08.2017.txt";
    private static String date3 = "files/date_23.08.2017.txt";

    public static void main(String[] args) throws ParseException {
        // генерируем три файла постаки фруктов
        GenerateStartJSON.startGenerate();

        // делаем проверки по первой задаче
        firstTask();

        // делаем проверки по второй задаче
        secondTask();


    }

    private static void firstTask() throws ParseException {
        // делаем первую поставку
        tradeShop.addFruits(date1);
        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
        tradeShop.showCurrentStatus();
//        // длаем вторую поставку
//        tradeShop.addFruits(date2);
//        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
//        tradeShop.showCurrentStatus();
//        // длаем третью поставку
//        tradeShop.addFruits(date3);
//        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
//        tradeShop.showCurrentStatus();
//        // сохраняем информацию
//        tradeShop.save(DB_fruits);
//        // загружаем информацию из файла основной базы для лавки
//        tradeShop.load(DB_fruits);
//        // поверяем что все в окей методом, который выводит в консоль текущий склад магазина
//        tradeShop.showCurrentStatus();
    }

    private static void secondTask() throws ParseException {
        // создаем дату
        Date date = tradeShop.convertStringToDate("25.09.2017");
        // получаем продукты, которые испортятся к заданной дате
        List<Fruit> fruitsSpoiled = tradeShop.getSpoiledFruits(date);
        // выводим их
        System.out.println("\nСписок продуктов, которые испортятся к " + tradeShop.dateFormat.format(date));
        tradeShop.showCurrentStatus(fruitsSpoiled);
        // получаем продукты, которые не испортятся к заданной дате
        List<Fruit> fruitsAvailable = tradeShop.getAvailableFruits(date);
        // выводим их
        System.out.println("\nСписок продуктов, которые не испортятся к " + tradeShop.dateFormat.format(date));
        tradeShop.showCurrentStatus(fruitsAvailable);



    }
}
