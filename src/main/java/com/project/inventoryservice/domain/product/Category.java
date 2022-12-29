package com.project.inventoryservice.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Category {
    DETERGENT("00", "세제"),
    FRESHENER("01","방향제"),
    POLISH("02","광택제"),
    VAT("03","말통"),
    BOX("04","박스");

    private final String key;
    private final String name;

    public static Category findByKey(String key) {
        return Arrays.stream(Category.values()).filter(c -> c.getKey().equals(key)).findAny().orElse(null);
    }

    public static Category findByName(String name){
        return Arrays.stream(Category.values()).filter(c -> c.getName().equals(name)).findAny().orElse(null);
    }

}
