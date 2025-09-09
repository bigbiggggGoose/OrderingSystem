package com.example.ordering.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordering.dto.OrderItem;
import jakarta.validation.Valid;
import com.example.ordering.dto.User;
import com.example.ordering.service.MenuService;
import com.example.ordering.service.UserService;
import com.example.ordering.dto.SeatRequest;
import com.example.ordering.service.TableService;

@RestController
@RequestMapping("/api/user")
public class AddUser {
    private final UserService userService;
    private final MenuService menuService;
    private final TableService tableService;

    public AddUser(UserService userService, MenuService menuService, TableService tableService) {
        this.userService = userService;
        this.menuService = menuService;
        this.tableService = tableService;
    }

    @PostMapping("/seat")
    public User seat(@Valid @RequestBody SeatRequest request) {
        return userService.seat(request);
    }

    @PostMapping("/{username}/order")
    public User order(@PathVariable String username, @Valid @RequestBody OrderItem request) {
        int price = menuService.findById(request.getDishId())
            .map(d -> d.getPrice())
            .orElse(0);
        return userService.order(username, request, price);
    }

    @GetMapping
    public java.util.List<User> list() {
        return userService.listAll();
    }

    @GetMapping("/tables/free")
    public java.util.List<Integer> freeTables() {
        return tableService.freeTables();
    }

    @PostMapping("/{username}/submit")
    public com.example.ordering.dto.Order submit(@PathVariable String username, @Valid @RequestBody java.util.List<OrderItem> requests) {
        return userService.submit(username, requests, id -> menuService.findById(id).map(d -> d.getPrice()).orElse(0));
    }

    @PostMapping("/{username}/checkout")
    public org.springframework.http.ResponseEntity<Void> checkout(@PathVariable String username) {
        boolean ok = userService.checkout(username);
        return ok ? org.springframework.http.ResponseEntity.noContent().build() : org.springframework.http.ResponseEntity.notFound().build();
    }
}
