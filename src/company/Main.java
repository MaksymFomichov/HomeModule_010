package company;

import company.datagenerator.GenerateStartJSON;

public class Main {
    private static String DB_fruits = "files/data.txt";
    private static TradeShop tradeShop = new TradeShop();
    private static String date1 = "files/date_20.08.2017.txt";
    private static String date2 = "files/date_21.08.2017.txt";
    private static String date3 = "files/date_23.08.2017.txt";

    public static void main(String[] args) {
        // генерируем три файла постаки фруктов
        GenerateStartJSON.startGenerate();

        // делаем проверки по первой задаче
        firstTask();


    }

    private static void firstTask() {
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
}
