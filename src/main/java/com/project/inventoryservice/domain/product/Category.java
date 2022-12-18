package com.project.inventoryservice.domain.product;

public enum Category {
    DETERGENT("세제"),
    FRESHENER("방향제"),
    POLISH("광택제"),
    VAT("말통"),
    BOX("박스");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
