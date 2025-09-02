package com.example.ordering.server;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordering.dto.Dish;
import com.example.ordering.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class Menu {
    private final MenuService menuService;

    public Menu(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<Dish> list() {
        return menuService.listAll();
    }

    @PostMapping
    public Dish add(@RequestBody Dish dish) {
        return menuService.addDish(dish);
    }

    @PostMapping("/{id}/soldout/{flag}")
    public ResponseEntity<Void> setSoldOut(@PathVariable long id, @PathVariable boolean flag) {
        boolean ok = menuService.setSoldOut(id, flag);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
