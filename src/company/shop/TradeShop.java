package company.shop;

import com.alibaba.fastjson.JSON;
import company.others.FileUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TradeShop {
    private List<Fruit> fruitsDB = new ArrayList<>();
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); // задаем формат даты
    private Calendar calendar = Calendar.getInstance(); // подключаем календарь

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
            Date dateExpiration = getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
            if (datesComparing(dateExpiration, date)) {
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
                Date dateExpiration = getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
                if (datesComparing(dateExpiration, date)) {
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
            Date dateExpiration = getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
            if (!datesComparing(dateExpiration, date)) {
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
                Date dateExpiration = getDateExpiration(value.getDateDelivery(), value.getDateExpiration()); // день окончания срока хранения
                if (!datesComparing(dateExpiration, date)) {
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
            Date dateDelivery = convertStringToDate(value.getDateDelivery());
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
            Date dateDelivery = convertStringToDate(value.getDateDelivery());
            if (dateDelivery.getTime() == date.getTime()) {
                if (value.getFruit().equals(typeFruit)) {
                    fruitList.add(value);
                }
            }
        }
        return fruitList;
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
            System.out.printf("%10s%15s%10s%10s%25s%n", "" + value.getDateDelivery() + "", "" + value.getFruit() + "", "" + value.getPrice(), "" + value.getDateExpiration(), "" + dateFormat.format(getDateExpiration(value.getDateDelivery(), value.getDateExpiration())));
        }
    }

    // получаем дату окончания срока годности продукта (к заданной дате прибавляем заданное количество дней)
    private Date getDateExpiration(String dateDelivery, int plusDay) throws ParseException {
        calendar.setTime(dateFormat.parse(dateDelivery)); // устанавливаем дату, с которой будем производить операции
        calendar.add(Calendar.DAY_OF_MONTH, plusDay);// прибавляем plusDay к установленной дате
        Date newDate = calendar.getTime(); // получаем измененную дату
        return newDate;
    }

    // сравнениваем две даты
    private Boolean datesComparing(Date dateExpiration, Date date) {
        if (dateExpiration.getTime() < date.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // конвентируем дату с текстового формата в обьект класса Date
    public Date convertStringToDate(String date) throws ParseException {
        calendar.setTime(dateFormat.parse(date)); //устанавливаем дату, с которой будем производить операции
        Date newDate = calendar.getTime(); // получаем дату
        return newDate;
    }


}
