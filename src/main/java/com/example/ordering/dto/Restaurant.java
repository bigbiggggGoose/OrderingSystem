package com.example.ordering.dto;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private List<Dish> menu = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }
}
