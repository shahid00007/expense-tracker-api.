package com.example.expense_tracker;

import com.example.expense_tracker.Expense;
import com.example.expense_tracker.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    // POST /api/expenses  — add a new expense
    @PostMapping
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody Expense expense) {
        Expense created = service.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/expenses  — view all, or filter with ?category=Food
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestParam(required = false) String category) {
        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(service.getExpensesByCategory(category));
        }
        return ResponseEntity.ok(service.getAllExpenses());
    }

    // GET /api/expenses/total  — overall total
    @GetMapping("/total")
    public ResponseEntity<Map<String, Double>> getTotal() {
        return ResponseEntity.ok(Map.of("total", service.getTotalExpenses()));
    }

    // GET /api/expenses/total-by-category  — breakdown per category
    @GetMapping("/total-by-category")
    public ResponseEntity<Map<String, Double>> getTotalByCategory() {
        return ResponseEntity.ok(service.getTotalByCategory());
    }

    // DELETE /api/expenses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        service.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}