package com.example.ordering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ordering.dto.OrderRequest;
import com.example.ordering.dto.User;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public User seat(SeatRequest request) {
        Optional<User> existing = users.stream()
            .filter(u -> u.getUsername().equals(request.getUsername()))
            .findFirst();
        User user = existing.orElseGet(User::new);
        user.setUsername(request.getUsername());
        user.setTableNumber(request.getTableNumber());
        user.setState(1); // 1 = seated
        if (existing.isEmpty()) {
            users.add(user);
        }
        return user;
    }

    public User order(String username, OrderRequest orderRequest, int dishPrice) {
        Optional<User> existing = users.stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst();
        User user = existing.orElseGet(() -> {
            User u = new User();
            u.setUsername(username);
            users.add(u);
            return u;
        });
        int additional = dishPrice * Math.max(1, orderRequest.getQuantity());
        user.setConsumptionAmount(user.getConsumptionAmount() + additional);
        user.setState(2); // 2 = ordering / ordered
        return user;
    }

    public List<User> listAll() {
        return users;
    }
}


