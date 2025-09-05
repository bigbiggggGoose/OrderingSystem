package com.example.ordering.service;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.ordering.dto.OrderItem;
import com.example.ordering.dto.Order;

@Service
public class OrderQueueService {
    private final LinkedList<Order> queue = new LinkedList<>();
    private final AtomicLong orderId = new AtomicLong(1);

    public synchronized Order enqueue(String username, int tableNumber, List<OrderItem> items) {
        Order t = new Order();
        t.setOrderId(orderId.getAndIncrement());
        t.setUsername(username);
        t.setTableNumber(tableNumber);
        t.setItems(items);
        t.setCreatedAt(Instant.now());
        queue.addLast(t);
        return t;
    }

    public synchronized Order peek() {
        return queue.peekFirst();
    }

    public synchronized Order dequeue() {
        return queue.pollFirst();
    }

    public synchronized List<Order> list() {
        return new LinkedList<>(queue);
    }
}


