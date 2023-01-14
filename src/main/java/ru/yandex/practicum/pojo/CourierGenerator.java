package ru.yandex.practicum.pojo;

import java.util.UUID;

public class CourierGenerator {
    public static Courier getDefault() {
        return new Courier("samu", "2345", "smuu");
    }

    public static Courier getInvalid() {
        return new Courier(null, "2345", "smuu");
    }

    public static Courier getCourierWithName(String name) {
        return new Courier("samu", "2345", name);
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
