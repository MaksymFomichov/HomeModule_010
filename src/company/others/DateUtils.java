package company.others;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); // задаем формат даты
    private static Calendar calendar = Calendar.getInstance(); // подключаем календарь

    private DateUtils() {
    }

    // конвентируем дату с текстового формата в обьект класса Date
    public static Date convertStringToDate(String date) throws ParseException {
        calendar.setTime(dateFormat.parse(date)); //устанавливаем дату, с которой будем производить операции
        Date newDate = calendar.getTime(); // получаем дату
        return newDate;
    }

    // сравнениваем две даты
    public static Boolean datesComparing(Date dateExpiration, Date date) {
        if (dateExpiration.getTime() < date.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    // получаем дату окончания срока годности продукта (к заданной дате прибавляем заданное количество дней)
    public static Date getDateExpiration(String dateDelivery, int plusDay) throws ParseException {
        calendar.setTime(dateFormat.parse(dateDelivery)); // устанавливаем дату, с которой будем производить операции
        calendar.add(Calendar.DAY_OF_MONTH, plusDay);// прибавляем plusDay к установленной дате
        Date newDate = calendar.getTime(); // получаем измененную дату
        return newDate;
    }

}
