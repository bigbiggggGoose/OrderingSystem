package com.example.ordering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.ordering.dto.Dish;

@Service
public class MenuService {
    private final List<Dish> dishes = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public MenuService() {
        Dish d1 = new Dish();
        d1.setId(idGenerator.getAndIncrement());
        d1.setName("Kung Pao Chicken");
        d1.setPrice(42);
        d1.setSoldOut(false);
        d1.setDescription("Spicy stir-fry with peanuts");
        d1.setImageUrl("/images/kungpao.jpg");
        dishes.add(d1);

        Dish d2 = new Dish();
        d2.setId(idGenerator.getAndIncrement());
        d2.setName("Mapo Tofu");
        d2.setPrice(28);
        d2.setSoldOut(false);
        d2.setDescription("Tofu in spicy bean sauce");
        d2.setImageUrl("/images/mapo.jpg");
        dishes.add(d2);
    }

    public List<Dish> listAll() {
        return dishes;
    }

    public Dish addDish(Dish dish) {
        dish.setId(idGenerator.getAndIncrement());
        dishes.add(dish);
        return dish;
    }

    public Optional<Dish> findById(long id) {
        return dishes.stream().filter(d -> d.getId() == id).findFirst();
    }

    public boolean setSoldOut(long id, boolean soldOut) {
        Optional<Dish> opt = findById(id);
        if (opt.isEmpty()) return false;
        opt.get().setSoldOut(soldOut);
        return true;
    }
}


