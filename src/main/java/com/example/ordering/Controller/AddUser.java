package com.example.ordering.server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordering.dto.OrderRequest;
import com.example.ordering.dto.SeatRequest;
import com.example.ordering.dto.User;
import com.example.ordering.service.MenuService;
import com.example.ordering.service.UserService;

@RestController
@RequestMapping("/api/user")
public class AddUser {
    private final UserService userService;
    private final MenuService menuService;

    public AddUser(UserService userService, MenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    @PostMapping("/seat")
    public User seat(@RequestBody SeatRequest request) {
        return userService.seat(request);
    }

    @PostMapping("/{username}/order")
    public User order(@PathVariable String username, @RequestBody OrderRequest request) {
        int price = menuService.findById(request.getDishId())
            .map(d -> d.getPrice())
            .orElse(0);
        return userService.order(username, request, price);
    }

    @GetMapping
    public java.util.List<User> list() {
        return userService.listAll();
    }
}
