package com.example.expense_tracker;

import com.example.expense_tracker.Expense;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ExpenseRepository {

    // ConcurrentHashMap so it's safe if multiple requests hit at once
    private final Map<String, Expense> store = new ConcurrentHashMap<>();

    public Expense save(Expense expense) {
        store.put(expense.getId(), expense);
        return expense;
    }

    public List<Expense> findAll() {
        return List.copyOf(store.values());
    }

    public java.util.Optional<Expense> findById(String id) {
        return java.util.Optional.ofNullable(store.get(id));
    }

    public boolean deleteById(String id) {
        return store.remove(id) != null;
    }
}