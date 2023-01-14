package ru.yandex.practicum.pojo;

public class OrderGenerator {
    public static final int DEFAULT_LIMIT = 5;

    public static Order getBlack() {
        return new Order("Adam", "Smith", "Paradise", "Garden",
                "+7 800 355 35 35", 5, "2023-06-06", "Hello world", new String[]{"BLACK"});
    }
    public static Order getBlackAndGrey() {
        return new Order("Adam", "Smith", "Paradise", "Garden",
                "+7 800 355 35 35", 5, "2023-06-06", "Hello world", new String[]{"BLACK", "GREY"});

    }public static Order getWithoutColour() {
        return new Order("Adam", "Smith", "Paradise", "Garden",
                "+7 800 355 35 35", 5, "2023-06-06", "Hello world", null);
    }

}
