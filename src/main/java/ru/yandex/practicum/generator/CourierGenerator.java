package ru.yandex.practicum.generator;

import ru.yandex.practicum.pojo.Courier;

import java.util.UUID;

public class CourierGenerator {
    public static Courier getDefault() {
        return new Courier("samusa", "2345", "smuu");
    }

    public static Courier getInvalid() {
        return new Courier(null, "2345", "smuu");
    }

    public static Courier getCourierWithName(String name) {
        return new Courier("samusa", "2345", name);
    }

    public static Courier getDefaultIncorrectPassword(){
        Courier aDefault = getDefault();
        aDefault.setPassword("54321");
        return aDefault;
    }

    public static Courier getCourierRandomLogin() {
        return new Courier(UUID.randomUUID().toString(), "2345", "smuu");
    }
}
