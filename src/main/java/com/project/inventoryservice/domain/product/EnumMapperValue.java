package com.project.inventoryservice.domain.product;

public class EnumMapperValue {
    private String key;

    private String name;

    public EnumMapperValue(EnumMapperType enumMapperType) {
        key = enumMapperType.getKey();
        name = enumMapperType.getName();
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' + '}';
    }
}
