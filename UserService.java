package com.example.ordering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ordering.dto.OrderItem;
import com.example.ordering.dto.SeatRequest;
import com.example.ordering.dto.User;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final TableService tableService;
    private final OrderQueueService orderQueueService;

    public UserService(TableService tableService, OrderQueueService orderQueueService) {
        this.tableService = tableService;
        this.orderQueueService = orderQueueService;
    }

    public User seat(SeatRequest request) {
        Optional<User> existing = users.stream()
            .filter(u -> u.getUsername().equals(request.getUsername()))
            .findFirst();
        User user = existing.orElseGet(User::new);
        user.setUsername(request.getUsername());
        if (!tableService.occupy(request.getTableNumber())) {
            throw new IllegalStateException("Table not available");
        }
        user.setTableNumber(request.getTableNumber());
        user.setState(1); // 1 = seated
        if (existing.isEmpty()) {
            users.add(user);
        }
        return user;
    }

    public User order(String username, OrderItem orderItemInput, int dishPrice) {
        Optional<User> existing = users.stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst();
        User user = existing.orElseGet(() -> {
            User u = new User();
            u.setUsername(username);
            users.add(u);
            return u;
        });
        int additional = dishPrice * Math.max(1, orderItemInput.getQuantity());
        user.setConsumptionAmount(user.getConsumptionAmount() + additional);
        user.setState(2); // 2 = ordering / ordered
        return user;
    }

    public com.example.ordering.dto.Order submit(String username, java.util.List<OrderItem> itemsInput, java.util.function.LongFunction<Integer> priceProvider) {
        Optional<User> existing = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
        User user = existing.orElseThrow(() -> new IllegalStateException("User not seated"));
        java.util.List<com.example.ordering.dto.OrderItem> items = itemsInput.stream().map(r -> {
            com.example.ordering.dto.OrderItem item = new com.example.ordering.dto.OrderItem();
            item.setDishId(r.getDishId());
            int price = java.util.Objects.requireNonNullElse(priceProvider.apply(r.getDishId()), 0);
            item.setUnitPrice(price);
            item.setQuantity(Math.max(1, r.getQuantity()));
            return item;
        }).collect(Collectors.toList());
        int total = items.stream().mapToInt(i -> i.getUnitPrice() * i.getQuantity()).sum();
        user.setConsumptionAmount(user.getConsumptionAmount() + total);
        user.setState(2);
        return orderQueueService.enqueue(user.getUsername(), user.getTableNumber(), items);
    }

    public boolean checkout(String username) {
        Optional<User> existing = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
        if (existing.isEmpty()) return false;
        tableService.free(existing.get().getTableNumber());
        users.remove(existing.get());
        return true;
    }

    public List<User> listAll() {
        return users;
    }
}


