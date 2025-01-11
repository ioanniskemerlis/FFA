package gr.aueb.cf.ffa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    /**
     * Example endpoint for fetching incomes.
     *
     * @return A list of mock incomes.
     */
    @GetMapping
    public List<Map<String, Object>> getIncomes() {
        return List.of(
                Map.of(
                        "id", "1",
                        "source", "Freelancing",
                        "amount", 500,
                        "date", "2025-01-06",
                        "category", "Work",
                        "notes", "Invoice #1001"
                ),
                Map.of(
                        "id", "2",
                        "source", "Investment",
                        "amount", 300,
                        "date", "2025-01-03",
                        "category", "Passive Income",
                        "notes", "Stock dividends"
                )
        );
    }
}