package com.example.ordering.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TableService {
    private final int capacity = 10;
    private final Set<Integer> occupied = new HashSet<>();

    public int getCapacity() {
        return capacity;
    }

    public synchronized boolean occupy(int tableNumber) {
        if (tableNumber < 1 || tableNumber > capacity) return false;
        if (occupied.contains(tableNumber)) return false;
        occupied.add(tableNumber);
        return true;
    }

    public synchronized void free(int tableNumber) {
        occupied.remove(tableNumber);
    }

    public synchronized boolean isOccupied(int tableNumber) {
        return occupied.contains(tableNumber);
    }

    public synchronized java.util.List<Integer> freeTables() {
        java.util.List<Integer> free = new java.util.ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            if (!occupied.contains(i)) free.add(i);
        }
        return free;
    }
}


