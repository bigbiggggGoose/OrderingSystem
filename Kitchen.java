package com.example.ordering.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordering.dto.Order;
import com.example.ordering.service.OrderQueueService;

@RestController
@RequestMapping("/api/kitchen")
public class Kitchen {
    private final OrderQueueService queue;

    public Kitchen(OrderQueueService queue) {
        this.queue = queue;
    }

    @GetMapping("/queue")
    public java.util.List<Order> list() {
        return queue.list();
    }

    @GetMapping("/next")
    public Order next() {
        return queue.peek();
    }

    @PostMapping("/take")
    public Order take() {
        return queue.dequeue();
    }
}


