package com.example.expense_tracker;

import com.example.expense_tracker.ExpenseNotFoundException;
import com.example.expense_tracker.Expense;
import com.example.expense_tracker.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense addExpense(Expense expense) {
        return repository.save(new Expense(
                expense.getTitle(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDate()
        ));
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public List<Expense> getExpensesByCategory(String category) {
        return repository.findAll().stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public double getTotalExpenses() {
        return repository.findAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Map<String, Double> getTotalByCategory() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    public void deleteExpense(String id) {
        boolean deleted = repository.deleteById(id);
        if (!deleted) {
            throw new ExpenseNotFoundException(id);
        }
    }
}