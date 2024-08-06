package com.example.transactions.unit;

import git com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TelegramContactsConverter {
    public static void main(String[] args) {
        try {
            // Укажите путь к вашему HTML файлу
            File file = new File("contacts.html");

            // Загрузите HTML файл
            Document doc = Jsoup.parse(file, "UTF-8");

            // Найдите все записи контактов
            Elements entries = doc.select("div.entry");

            // Создайте CSV writer
            CSVWriter writer = new CSVWriter(new FileWriter("contacts.csv"));

            // Запишите заголовки
            String[] headers = {"Name", "Phone Number", "Date"};
            writer.writeNext(headers);

            // Запишите строки таблицы
            for (Element entry : entries) {
                String name = entry.select("div.name.bold").text();
                String phoneNumber = entry.select("div.details_entry.details").text();
                String date = entry.select("div.pull_right.info.details").text();

                // Форматирование номера телефона
                phoneNumber = formatPhoneNumber(phoneNumber);

                String[] data = {name, phoneNumber, date};
                writer.writeNext(data);
            }

            // Закройте writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для форматирования номера телефона
    private static String formatPhoneNumber(String phoneNumber) {
        // Удаление пробелов, тире и других символов
        phoneNumber = phoneNumber.replaceAll("[^0-9+]", "");

        // Если номер начинается с "007" и имеет длину 12 символов, заменяем на "+7"
        if (phoneNumber.startsWith("007") && phoneNumber.length() == 12) {
            phoneNumber = "+7" + phoneNumber.substring(3);
        }

        if (phoneNumber.startsWith("00") && phoneNumber.length() > 12) {
            phoneNumber = "+7" + phoneNumber.substring(3);
        }
        // Если номер начинается с "8" и длина номера 11 цифр, заменяем на "+7"
        else if (phoneNumber.startsWith("8") && phoneNumber.length() == 11) {
            phoneNumber = "+7" + phoneNumber.substring(1);
        }

        // Если номер начинается с "9" и длина номера 10 цифр, добавляем "+7"
        else if (phoneNumber.startsWith("9") && phoneNumber.length() == 10) {
            phoneNumber = "+7" + phoneNumber;
        }

        // Если номер начинается с "+" и длина номера корректна, оставляем его без изменений
        else if (phoneNumber.startsWith("+") && (phoneNumber.length() == 12 || phoneNumber.length() == 13)) {
            return phoneNumber;
        }

        return phoneNumber;
    }
}